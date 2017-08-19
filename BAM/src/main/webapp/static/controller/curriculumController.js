/**
 * Defines a controller to handle DOM manipulation of the Curriculum HTML page
 * @Author Brian McKalip
 */
app.controller(
	"curriculumController",
	
	function($scope, $http) {
		/* UTILITY FUNCTIONS */
		$scope.sanitizeString = function(str){

			if(str){
				//replace spaces with underscores
				str = str.replace(' ', '_');
				//remove all non alphanumeric characters
				str = str.replace(/\W/g, '');
			}
//			console.log("new string: " + str);
			return str;
		}
		
		$scope.deselectItems = function(){
			var activeItems = document.getElementsByClassName("active");
			for (var i = 0; i < activeItems.length; i++) {
				   activeItems[i].classList.remove('active');
			}
		}

		var CLONE = {
				  'Array': function(clone) {
				    return Array.prototype.map.call(this, clone);
				  },
				  'Date': function() {
				    return new Date(this.valueOf());
				  },
				  'String': String.prototype.valueOf,
				  'Number': Number.prototype.valueOf,
				  'Boolean': Boolean.prototype.valueOf
				};
		
		$scope.clone = function(obj) {
			  if (Object(obj) !== obj) return obj;
			  if (typeof obj.toJSON == 'function') {
			    return obj.toJSON();
			  }
			  var type = toString.call(obj).slice(8, -1);
			  if (type in CLONE) {
			    return CLONE[type].call(obj, clone);
			  }
			  var copy = {};
			  var keys = Object.keys(obj);
			  for (var i = 0, len = keys.length; i < len; i++) {
			    var key = keys[i];
			    copy[key] = clone(obj[key]);
			  }
			  return copy;
		}
		
		//constant array defining valid days of the week 
		$scope.weekdays = [ "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" ];
		
		//list of available topics in the topic pool. this will eventually be loaded through  a get request
		$scope.topics = [];
		
		//template object that will be used to create new curriculums. Populated based on selection in curricula left side panel
		$scope.template = {
				meta: {
					type: '',
					version: ''
				},
				weeks: []
		}
		
		//curriculum that is currently displayed in the main curriculum view of the page. This will be loaded from the curricula left side panel
		$scope.displayedCurriculum = {
			meta: {
				type: '',
				version: ''
			},
			//weeks which contain days which contain subtopics for the curriculum. in the future this will be loaded from the selected curriculum
			weeks: []
		}
		
		//this object holds all curricula, which is an array of curriculum. Each has a type (eg java) and an array of versions. This will eventually be loaded via HTTP Get
		$scope.curricula = [];
		
		$scope.addWeek = function(){
			var week = {
				days:[
						{subtopics : []},
						{subtopics : []},
						{subtopics : []},
						{subtopics : []}, 
						{subtopics : []}
					]
				};
			$scope.displayedCurriculum.weeks.push(week);
			$scope.displayedCurriculum.meta.curriculumNumberOf_Weeks += 1;
			console.log($scope.displayedCurriculum);
		}
		
		$scope.deleteWeek = function(index){
			if(confirm("Are you sure you want to delete week #" + index + "?")){
				$scope.displayedCurriculum.weeks.splice(index-1, 1);
				$scope.displayedCurriculum.meta.curriculumNumberOf_Weeks -= 1;
			}
		}
		
		//when an existing curriculum is selected, it will be loaded into the template
		$scope.setTemplate = function(curriculum){
			console.log("setting template")
			if(curriculum){
				//attempt to look for curr in curricula object before doing http req (caching)
				console.log($scope.curricula );
				for(i in $scope.curricula){
					if( $scope.curricula[i].type == curriculum.meta.curriculumName && 
						$scope.curricula[i].versions[curriculum.meta.curriculumVersion - 1].weeks.length > 0){
						
						console.log("already loaded, canceling request");
						console.log($scope.curricula[i].versions[curriculum.meta.curriculumVersion - 1].weeks);
						$scope.template = $scope.curricula[i].versions[curriculum.meta.curriculumVersion - 1];
						return;
					}else{
						console.log("not loaded yet, requesting curriculum");
					}
				}
				
				//do request
				console.log("curriculum in setTemplate")
				console.log(curriculum);
				$http({
					url: "rest/api/v1/Curriculum/Schedule",
					method: "GET",
					params: {curriculumId: curriculum.meta.curriculumVersion}
					
				}).then(function(response){
					var newCurriculum = curriculum;
					
					//add the (empty) weeks:
						for(var j = 0; j < newCurriculum.meta.curriculumNumberOf_Weeks; j++){
							newCurriculum.weeks.push({
								days:[
									{subtopics:[]},
									{subtopics:[]},
									{subtopics:[]},
									{subtopics:[]},
									{subtopics:[]}
								]
							});
					}
//					console.log("weeks: ");
//					console.log(newCurriculum);
					//loop through array of response objects adding subtopics to the correct week and day arrays.
					for(i in response.data){
						var topic = response.data[i];
//						console.log("Topics in loop");
//						console.log(topic);
						newCurriculum.weeks[topic.curriculumSubtopicWeek - 1].days[topic.curriculumSubtopicDay - 1].subtopics.push(topic.curriculumSubtopic_Name_Id);
					}
					
					//TODO: make this a unique object (not a reference to the old one)
//					newCurriculum = Object.create(newCurriculum);
//					console.log("newCurr after uniquification: ")
//					console.log(newCurriculum);
					//add newCurriculum as a version to the curricula type:
					for(j in $scope.curricula){
						if($scope.curricula[j].type == curriculum.curriculumName){
							$scope.curricula[j].versions[newCurriculum.meta.curriculumVersion - 1] = newCurriculum;
						}
					}
					
					//set the newCurriculum object as the $scope.template
					//TODO: need to make this unique instead of reference in the future.
					$scope.template = newCurriculum;
//					console.log("adding unique newCurriculum object as a version: ")
//					console.log(newCurriculum);
				});
			}else{
				//TODO: modal to create new topic type
				console.log("new type");
			}
		}
			
		//create a new curriculum with the template, if the template is null, a new curriculum will be created
		$scope.newCurriculum = function(){
			console.log("--------------------- IN NEW CURRICULUM ---------------------");
			
			//create a unique object from the template (not a reference to template)
			var curriculum = jQuery.extend(true, {}, $scope.template);
			
			//loop through the curricula looking for the curriculum type, when found count number of versions and set this curr. object's version to it
			for(item in $scope.curricula){
				if($scope.curricula[item].type == $scope.template.meta.curriculumName){
					console.log("creating version " + ($scope.curricula[item].versions.length + 1) + " of : " + curriculum.meta.curriculumName + " curriculum.");
					curriculum.meta.curriculumVersion = $scope.curricula[item].versions.length + 1;
				}
			}
			
			$scope.displayedCurriculum = curriculum;
			console.log($scope.displayedCurriculum);
		}
		
		$scope.saveCurriculum = function(){
			console.log("********** entering saving curriculum ********** ");
			console.log($scope.displayedCurriculum);
			if($scope.displayedCurriculum.meta.curriculumName){
				console.log("type: " + $scope.displayedCurriculum.meta.curriculumName);
				for(item in $scope.curricula){
					console.log("checking type: " + $scope.curricula[item].type);
					if($scope.curricula[item].type == $scope.displayedCurriculum.meta.curriculumName){
						console.log("found match - adding curriculum")
//						$scope.curricula[item].versions.push({meta:$scope.displayedCurriculum.meta , weeks: $scope.displayedCurriculum.weeks});
						$scope.curricula[item].versions.push($scope.displayedCurriculum);
						console.log("updated curricula - after pushign new version");
						console.log($scope.curricula);
						
					}
				}
			}else{
//				var curriculum = {
//					type:'',
//					versions: []
//				};
//				//TODO: modal
//				curriculum.type = "foo";
//				curriculum.versions.push({weeks:$scope.displayedCurriculum.weeks});
//				$scope.curricula.push(JSON.parse(JSON.stringify(curriculum)));
			}
		}
		
		$scope.getCurricula = function(){
			$http({
				url: "rest/api/v1/Curriculum/All",
				method: "GET",
				
			}).then(function(response){
				var curricula = response.data;
				console.log(curricula);
				//parse the response into the local (front end) json object format
				
				//for each curriculum in curricula:
				for(i in curricula){
					var curriculum = curricula[i];

					//raise flag if there exists a a curriculum of this type already
					var curriculumTypeExists = false;
					//determine if $scope.curricula has a type of curriculum.Name already. If so add it as an additional version of the type
					for(j in $scope.curricula){
						var localCurricula = $scope.curricula[j];
						//perform the check mentioned above
						if(localCurricula.type == curriculum.curriculumName){
							//raise the flag
							curriculumTypeExists = true;
							//add an empty weeks array to the curriculum
							curriculum.weeks = [];
							
							//insert the curriculum into the existing curr type as a version of that type (as specified by the received object) 

							
							var metaData = curriculum;
							delete metaData.weeks;
							$scope.curricula[j].versions.splice(curriculum.curriculumVersion - 1, 0, {meta:metaData, weeks:[]});
							console.log("curriculum in for of getCurricula ");
							console.log({meta:metaData, weeks:[]});
							$scope.curricula[j].versions[curriculum.curriculumVersion - 1].meta = curriculum;
							break;
						}
					}
					
					//if a curriculum of type curriculum.curriculumName does not exist, add it as a new base curriculum type
					if(!curriculumTypeExists){
						console.log("new curr type")
						console.log(curriculum)
						
						var metaData = curriculum;
						delete metaData.weeks;
						console.log("curriculum after delete");
//						console.log(curriculum.curriculumName);
						var newCurriculum = {
								type: curriculum.curriculumName,
								versions: [
									{
										meta: metaData,
										weeks: []
									}
								]
						};
						console.log("new curr: ")
						console.log(newCurriculum);
						$scope.curricula.push(newCurriculum);
					}
				}
				
			});
		}
		
		$scope.getTopicPool = function(){
			console.log("Getting all topics");
			$http({
				url: "rest/api/v1/Curriculum/TopicPool",
				method: "GET",
				
			}).then(function(response){
				var topics = response.data;
				//parse the response into the local (front end) json object format
				
				//for each topic in topics:
				for(i in topics){
					var topic = topics[i];

					//raise flag if there exists a a topic of this type already
					var topicExists = false;
					//determine if $scope.topics has a type of topic.Name already. If so add the subtopic to the existing list
					for(j in $scope.topics){
						//perform the check mentioned above
						if($scope.topics[j] && topic.topic && $scope.topics[j].name == topic.topic.name){
							//raise the flag
							topicExists = true;
							$scope.topics[j].subtopics.push(topic);
							break;
						}
					}
					
					//if a curriculum of type curriculum.curriculumName does not exist, add it as a new base curriculum type
					if(!topicExists){
						var newTopic = {
								id: topic.topic.id,
								name: topic.topic.name,
								subtopics: []
						};
						newTopic.subtopics.push(topic);
						$scope.topics.push(newTopic);
					}
				}
			});
		};	
		
		//get the curricula meta data on page load
		$scope.getCurricula();
		//load the topic pool on page load
		$scope.getTopicPool();
	}
);