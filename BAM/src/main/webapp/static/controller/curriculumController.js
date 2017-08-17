/**
 * Defines a controller to handle DOM manipulation of the Curriculum HTML page
 * @Author Brian McKalip
 */
app.controller(
	"curriculumController",
	
	function($scope, $http) {
		$scope.sanitizeString = function(str){
			//replace spaces with underscores
			str = str.replace(' ', '_');
			//remove all non alphanumeric characters
			str = str.replace(/\W/g, '');
//			console.log("new string: " + str);
			return str;
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
			console.log($scope.displayedCurriculum);
			$scope.displayedCurriculum.weeks.push(week);
		}
		
		$scope.deleteWeek = function(index){
			if(confirm("Are you sure you want to delete week #" + index + "?")){
				$scope.displayedCurriculum.weeks.splice(index-1, 1);
			}
		}
		
		//when an existing curriculum is selected, it will be loaded into the template
		$scope.setTemplate = function(curriculum){
			//TODO: attempt to look for curr in curricula object before doing http req (caching)
//			console.log("curriculum");
//			console.log(curriculum);
			if(curriculum){
				//do request
				$http({
					url: "rest/api/v1/Curriculum/Schedule",
					method: "GET",
					params: {curriculumId: curriculum.curriculum_Version}
					
				}).then(function(response){
					var newCurriculum = {
							meta: curriculum,
							weeks:[]
					}
					
					//add the (empty) weeks:
					//warning hacky workaround: add one extra weeks object then splice out the first one because for some reason
					//the first one added doesn't add the days to the weeks object before pushing it on. I'm sorry
					for(var j = 0; j < newCurriculum.meta.curriculum_Number_Of_Weeks + 1; j++){
						newCurriculum.weeks.push({
							days:[
								{subtopics:[]},
								{subtopics:[]},
								{subtopics:[]},
								{subtopics:[]},
								{subtopics:[]},
							]
						});
					}
					//splice out the messed up first day object. week array should then contain the correct # of weeks each with 5 days.
					newCurriculum.weeks = newCurriculum.weeks.splice(1);
					
					//loop through array of response objects adding subtopics to the correct week and day arrays.
					for(i in response.data){
						var topic = response.data[i];
						newCurriculum.weeks[topic.curriculumSubtopic_Week - 1].days[topic.curriculumSubtopic_Day - 1].subtopics.push(topic.curriculumSubtopic_Name_Id);
					}
					
					//TODO: make this a unique object (not a reference to the old one)
//					newCurriculum = Object.create(newCurriculum);
//					console.log("newCurr after uniquification: ")
//					console.log(newCurriculum);
					//add newCurriculum as a version to the curricula type:
					for(j in $scope.curricula){
						if($scope.curricula[j].type == curriculum.curriculum_Name){
							$scope.curricula[j].versions[newCurriculum.meta.curriculum_Version - 1] = newCurriculum;
						}
					}
					
					//set the newCurriculum object as the $scope.template
					//TODO: need to make this unique instead of reference in the future.
					$scope.template = newCurriculum;
				});
			}else{
				//TODO: modal to create new topic type
			}
		}
			
		//create a new curriculum with the template, if the template is null, a new curriculum will be created
		$scope.newCurriculum = function(){
//			$scope.displayedCurriculum.meta.type = $scope.template.meta.type;
//			$scope.displayedCurriculum.meta.version = $scope.template.meta.version;
//			$scope.displayedCurriculum.weeks = $scope.template.weeks;

			console.log("creating next version of " + $scope.template.meta.type);
			
			var curriculum = $scope.template;
			
			//loop through the curricula looking for the curriculum type, when found count number of versions and set this curr. object's version to it
			for(item in $scope.curricula){
				if($scope.curricula[item].type == $scope.template.meta.curriculum_Name){
					curriculum.meta.version = $scope.curricula[item].versions.length;
				}
			}
			
//			$scope.displayedCurriculum = curriculum;
			$scope.displayedCurriculum = JSON.parse(JSON.stringify(curriculum));
			console.log($scope.displayedCurriculum);
		}
		
		$scope.delectItems = function(){
			var activeItems = document.getElementsByClassName("active");
			for (var i = 0; i < activeItems.length; i++) {
				   activeItems[i].classList.remove('active');
			}
		}
		
		$scope.saveCurriculum = function(){
			console.log("saving curriculum");
			if($scope.displayedCurriculum.meta.curriculum_Name){
				console.log("type: " + $scope.displayedCurriculum.meta.curriculum_Name);
				for(item in $scope.curricula){
					console.log("checking type: " + $scope.curricula[item]);
					if($scope.curricula[item].type == $scope.displayedCurriculum.meta.curriculum_Name){
						console.log("found match - adding curriculum")
						$scope.curricula[item].versions.push({weeks: $scope.displayedCurriculum.weeks});
					}
				}
			}else{
				var curriculum = {
					type:'',
					versions: []
				};
				//TODO: modal
				curriculum.type = "foo";
				curriculum.versions.push({weeks:$scope.displayedCurriculum.weeks});
				$scope.curricula.push(curriculum);
			}
		}
		
		$scope.getCurricula = function(){
			$http({
				url: "rest/api/v1/Curriculum/All",
				method: "GET",
				
			}).then(function(response){
				var curricula = response.data;
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
						if(localCurricula.type == curriculum.curriculum_Name){
							//raise the flag
							curriculumTypeExists = true;
							//add an empty weeks array to the curriculum
							curriculum.weeks = [];
							
							//insert the curriculum into the existing curr type as a version of that type (as specified by the received object) 
							console.log("Version: ");
							console.log(curriculum.curriculum_Version);
							$scope.curricula[j].versions.splice(curriculum.curriculum_Version, 0, curriculum);
							break;
						}
					}
					
					//if a curriculum of type curriculum.curriculum_Name does not exist, add it as a new base curriculum type
					if(!curriculumTypeExists){
						var newCurriculum = {
								type: curriculum.curriculum_Name,
								versions: []
						};
						curriculum.weeks = [
							{
								days : []
							}
						];
						newCurriculum.versions.splice(curriculum.curriculum_Version, 0, curriculum);
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
						if($scope.topics[j].name == topic.topic.name){
							//raise the flag
							topicExists = true;
							$scope.topics[j].subtopics.push(topic);
							break;
						}
					}
					
					//if a curriculum of type curriculum.curriculum_Name does not exist, add it as a new base curriculum type
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