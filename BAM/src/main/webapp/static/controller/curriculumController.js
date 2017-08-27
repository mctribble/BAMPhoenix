//local functions
var download = function (content, filename, contentType) {
    if (!contentType) contentType = 'application/octet-stream';
    var a = document.getElementById('xlsDownload');
    var blob = new Blob([content], {
        'type': contentType
    });
    a.href = window.URL.createObjectURL(blob);
    a.download = filename;
};

/**
 * Defines a controller to handle DOM manipulation of the Curriculum HTML page
 * @Author Brian McKalip
 */
app.controller("curriculumController",
	
	function($scope, $http, $q, SessionService) {
		/* BEGIN OBJECT SCOPE BOUND VARIABLE DEFINITIONS */
		
		$scope.showBtn = false;
		
		//constant array defining valid days of the week 
		$scope.weekdays = [ "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" ];
		
		//default type to load on page load
		$scope.DEFAULT_TYPE = "Java";
		
		//list of available topics in the topic pool.
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
			meta: {},
			//weeks which contain days which contain subtopics for the curriculum. in the future this will be loaded from the selected curriculum
			weeks: []
		}
		
		//this object holds all curricula, which is an array of curriculum. Each has a type (eg java) and an array of versions. This will eventually be loaded via HTTP Get
		$scope.curricula = [];
		
		//holds a list of the randomly generated colors of each parent topic type from the pool (displayed in progress bars)
		$scope.topicColors = [];
		
		/* END OBJECT SCOPE BOUND VARIABLE DEFINITIONS */
		
		/* BEGIN JSON TO XLS CONVERSION AND DOWNLOAD */
		
		$scope.downloadXLS = function(){
			var xlsArray = [];
			
			for(var i = 0; i < $scope.displayedCurriculum.weeks.length; i++){
				for(var j = 0; j < 5; j++){
					for(var k = 0; k < $scope.displayedCurriculum.weeks[i].days[j].subtopics.length; k++){
						var subtopic = $scope.displayedCurriculum.weeks[i].days[j].subtopics[k];
						var xlsObject = {
								Week : i+1,
								Day : j+1,
								Subtopic : subtopic.name 
						};
						xlsArray.push(xlsObject);
					}
				}
			}
			download(jsonToSsXml(angular.toJson(xlsArray)), 'Curriculum.xls', 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet');
		}
		
		/* END XLS FUNCTION */
		
		
		/* BEGIN UTILITY FUNCTIONS */
		$scope.sanitizeString = function(str){
			if(str){
				//replace spaces with underscores
				str = str.replace(' ', '_');
				//remove all non alphanumeric characters
				str = str.replace(/\W/g, '');
			}
			return str;
		}
		
		$scope.deselectItems = function(){
			var activeItems = document.getElementsByClassName("active");
			for (var i = 0; i < activeItems.length; i++) {
				   activeItems[i].classList.remove('active');
			}
		}
		
		$scope.getDate = function(){
			var today = new Date();
			var dd = today.getDate();
			var mm = today.getMonth() + 1;
			var yyyy = today.getFullYear();
			if(dd < 10) dd = '0' + dd;
			if(mm < 10) mm = '0' + mm;
			return mm + '/' + dd + '/' + yyyy;
		}
		
		$scope.getTopicColor = function(topicName){
			for(i in $scope.topicColors){
				var topic = $scope.topicColors[i];
				if(topic.type == topicName){
					return topic.color;
				}
			}
		}
		
		$scope.setTopicColors = function(topics){
			for(i in topics){
				var topic = topics[i];
				
				//generate random color (hex)
				var color = '#' + Math.floor(Math.random() * 16777215).toString(16);
				$scope.topicColors.push({'type': topic.name, 'color': color});
				
			}
		}
		
		$scope.setWeekProgressBars = function(){
			
			if($scope.topicColors.length <= 0) return;
			if($scope.displayedCurriculum.meta.curriculumNumberOfWeeks){
				var bars = document.getElementsByClassName("progress");
				//clear the bars of their children
				for(x in bars){
					bars[x].innerHTML = '';
				}
				for(i in $scope.displayedCurriculum.weeks){
					var week = $scope.displayedCurriculum.weeks[i];
					
					//algorithm: sum the number of subtopics of each topic and calc their percentages
					var topicCounts = [];
					var total = 0;
					for(j in week.days){
						var day = week.days[j];
						for(k in day.subtopics){
							var subtopic = day.subtopics[k];
							//search topicCounts for existing topic key
							var typeExists = false;
							for(l in topicCounts){
								if(topicCounts[l].name == subtopic.topic.name) {
									topicCounts[l].count += 1;
									total += 1;
									typeExists = true;
									break;
								}
							}
							if(!typeExists){
								topicCounts.push({name:subtopic.topic.name, count:1});
								total += 1;
							}
						}
					}
					//calculate percentages and apply then to the progress bars:
					var bar = bars[i];
					//template: <div class="progress-bar" role="progressbar" style="width: 0%;"></div>
					if(!total){
						var progress = document.createElement("div");
						progress.classList.add("progress-bar");
						progress.setAttribute("role", "progressbar");
						progress.setAttribute("style", "padding:6px; width:100%; background-color:red");
						progress.innerText = "No Topics";
						bar.appendChild(progress);
					}else{
						//add a progress section for each parent topic:
						for(m in topicCounts){
							var topic = topicCounts[m];
							var percent = (topic.count / total) * 100;
							var progress = document.createElement("div");
							var color = $scope.getTopicColor(topic.name);
							progress.classList.add("progress-bar");
							progress.setAttribute("role", "progressbar");
							progress.setAttribute("style", "padding:6px; width:" + percent + "%; background-color:" + color + ";");
							progress.innerText = topic.name;
							bar.appendChild(progress);
						}
					}
					
				}
			}
		}
		
		$scope.requestCurriculum = function(curriculum){
			//do request
			return $http({
				url: "rest/api/v1/Curriculum/Schedule",
				method: "GET",
				params: {curriculumId: curriculum.meta.curriculumId}
				
			})
			.then(function(response){
				var newCurriculum = curriculum;
				//add the (empty) weeks:
					for(var j = 0; j < newCurriculum.meta.curriculumNumberOfWeeks; j++){
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
				
				//loop through array of response objects adding subtopics to the correct week and day arrays.
				for(var i in response.data){
					var topic = response.data[i];
					newCurriculum.weeks[topic.curriculumSubtopicWeek - 1].days[topic.curriculumSubtopicDay - 1].subtopics.push(topic.curriculumSubtopicNameId);
				}
				return newCurriculum;
			})
		}
		
		
		/* END UTILITY FUNCTIONS */
		
		/* BEGIN CURRICULUM MANIPULATION FUNCTION DEFINITIONS */
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
			$scope.displayedCurriculum.meta.curriculumNumberOfWeeks += 1;
			$scope.setWeekProgressBars();
		}
		
		$scope.deleteWeek = function(index){
			if(confirm("Are you sure you want to delete week #" + index + "?")){
				$scope.displayedCurriculum.weeks.splice(index-1, 1);
				$scope.displayedCurriculum.meta.curriculumNumberOfWeeks -= 1;
				$scope.setWeekProgressBars();
			}
		}
		
		$scope.setMaster = function(curriculum){
			if(confirm("Change master version to version #" + curriculum.meta.curriculumVersion + "?")){
				//unset master in type:
				for(i in $scope.curricula){
					if($scope.curricula[i].type == curriculum.meta.curriculumName){
						for(j in $scope.curricula[i].versions){
							var version = $scope.curricula[i].versions[j];
							version.meta.isMaster = false;
						}
					}
				}
				
				//set the new master
				curriculum.meta.isMaster = true;
				
				$http({
					url: "rest/api/v1/Curriculum/MakeMaster?curriculumId=" + curriculum.meta.curriculumId,
					method: "GET",
				})
			}
		}
		
		//when an existing curriculum is selected, it will be loaded into the template
		$scope.setTemplate = function(curriculum){
			if($scope.displayedCurriculum && $scope.isEditable){
				if(!confirm("There are unsaved changes on the template you are working on. Are you sure you want to overwrite this template?")){
					//return a valid promise
					return $q.resolve(true);
				}
			}
			if(curriculum){
				//attempt to look for curr in curricula object before doing http req (caching)
				for(var i in $scope.curricula){
					if( $scope.curricula[i].type == curriculum.meta.curriculumName && $scope.curricula[i].versions[curriculum.meta.curriculumVersion - 1].weeks.length > 0){
						$scope.template = $scope.curricula[i].versions[curriculum.meta.curriculumVersion - 1];
						//return a valid promise
						return $q.resolve(true); 
					}
				}
				
				return $scope.requestCurriculum(curriculum)
				.then(function(newCurriculum){
					//set the newCurriculum object as the $scope.template
					$scope.template = newCurriculum;
					//add newCurriculum as a version to the curricula type:
					for(var j in $scope.curricula){
						if($scope.curricula[j].type == curriculum.curriculumName){
							$scope.curricula[j].versions[newCurriculum.meta.curriculumVersion - 1] = newCurriculum;
						}
					}
				});

			}else{
				//show the modal
				$('#newCurriculumType').modal('show');
				return $q.resolve(true)
			}
		}
		
		$scope.viewCurriculum = function(version){
			$scope.setTemplate(version)
			.then(function(){
				$scope.displayedCurriculum = $scope.template;
				$scope.isEditable = false;
				$scope.showBtn = true;
				$scope.downloadXLS();
				
			}).then(function(){
				setTimeout($scope.setWeekProgressBars, 500);
			});
			
		}
			
		//create a new curriculum with the template, if the template is null, a new curriculum will be created
		$scope.newCurriculum = function(type){
			var typeExists = false;
			//if the user provides a type, either they are creating a new version with the latest version of an existing type, or creating a new type
			if(type && ($scope.template.meta && $scope.template.meta.curriculumName != type)){
				//set the template to the latest version of the curriculum if it exists. otherwise create a new type
				for(var i in $scope.curricula){
					if($scope.curricula[i].type == type){
						//get the master version and set it equal to the template
						for(j in $scope.curricula[i].versions){
							if($scope.curricula[i].versions[j].meta.isMaster){
								$scope.setTemplate($scope.curricula[i].versions[j]);
							}
							typeExists = true;
						}
					}
				}
				if(!typeExists){
					//define a new type
					var newType = {
							type: type,
							versions: [
								{
									meta:{
										curriculumId: null,
										curriculumModifier: null,
										curriculumName: type,
										curriculumNumberOfWeeks: 0,
										curriculumVersion: 1,
										curriculumCreator: SessionService.get("currentUser"),
										curriculumdateCreated: $scope.getDate(), //set this to the current date as mm/dd/yy eventually
										isMaster: 1
									},
									weeks:[]
								}
							]
					};
					
					//set the template to the new type's first version
					$scope.template = newType.versions[0];
				}
			}
			
			//we can now guarantee that the template is set appropriately, and can load it into the displayedCurriculum
			//create a unique object from the template (not a reference to template)
			var curriculum = jQuery.extend(true, {}, $scope.template);
			curriculum.meta.curriculumCreator = SessionService.get("currentUser");
			curriculum.meta.curriculumdateCreated = $scope.getDate();
			//loop through the curricula looking for the curriculum type, if found, count the number of versions and set this curr. object's version to it + 1
			for(var item in $scope.curricula){
				if($scope.curricula[item].type == $scope.template.meta.curriculumName){
					curriculum.meta.curriculumVersion = $scope.curricula[item].versions.length + 1;
					//also, set isMaster to false, since the type exists already
					curriculum.meta.isMaster = 0;
				}
			}
			$scope.displayedCurriculum = curriculum;
			$scope.isEditable = true;
			$scope.setWeekProgressBars();
			//clear the modal box if it's got a value in it
			document.getElementById("newCurriculumTypeNameInput").value = "";
		}
		
		$scope.saveCurriculum = function(){
			//loop through curricula looking for an existing type. if found, append to versions.
			var typeExists = false;
			for(var item in $scope.curricula){
				if($scope.curricula[item].type == $scope.displayedCurriculum.meta.curriculumName){
					$scope.curricula[item].versions.push($scope.displayedCurriculum);
					
					typeExists = true;
				}
			}
			
			//if type doesn't exist, add a new type and append the curriculum as the first version of it.
			if(!typeExists){
				var newType = {
						type: $scope.displayedCurriculum.meta.curriculumName,
						versions: [$scope.displayedCurriculum]
				};
				$scope.curricula.push(newType);
			}
			
			//format the displayed curriculum in preparation for persistence
			var curriculumDTO = {
					meta:{
						curriculum: $scope.displayedCurriculum.meta
					},
					weeks:$scope.displayedCurriculum.weeks
			}
			//persist to the DB
			$http({
				method: 'POST',
				url: 'rest/api/v1/Curriculum/AddCurriculum',
				headers: {
			        'Content-Type': 'application/json', 
			        'Accept': 'application/json' 
			    },
				data: curriculumDTO
			}).then(function(){
			});
			
//			$scope.displayedCurriculum = null;
			$scope.template = null;
		}
		
		$scope.getCurricula = function(){
			return $http({
				url: "rest/api/v1/Curriculum/All",
				method: "GET",
				
			}).then(function(response){
				var curricula = response.data;
				//parse the response into the local (front end) json object format
				
				for(var i in curricula){
					var curriculum = curricula[i];

					//raise flag if there exists a a curriculum of this type already
					var curriculumTypeExists = false;
					//determine if $scope.curricula has a type of curriculum.Name already. If so add it as an additional version of the type
					for(var j in $scope.curricula){
						//perform the check mentioned above
						if($scope.curricula[j].type == curriculum.curriculumName){
							//ensure the object at the required index exists before trying to overwrite it
							while($scope.curricula[j].versions.length < (curriculum.curriculumVersion - 1)){
								$scope.curricula[j].versions.push({});
							}
							
							//raise the flag
							curriculumTypeExists = true;
							
							//insert the curriculum into the existing curr type as a version of that type (as specified by the received object) 
							delete curriculum.weeks;
							var newCurriculum = {meta:curriculum, weeks:[]};
							$scope.curricula[j].versions.splice(curriculum.curriculumVersion - 1, 1, newCurriculum);
							break;
						}
					}
					
					//if a curriculum of type curriculum.curriculumName does not exist, add it as a new base curriculum type
					if(!curriculumTypeExists){
						var metaData = curriculum;
						delete metaData.weeks;
						var newCurriculum2 = {
								type: curriculum.curriculumName,
								versions: []
						};
						
						//ensure the object at the required index exists before trying to overwrite it
						for(var m = 0; m < curriculum.curriculumVersion - 1; m++){
							newCurriculum2.versions.push({});
						}
						
						newCurriculum2.versions.splice(curriculum.curriculumVersion - 1, 1, {meta: metaData, weeks:[]});
						$scope.curricula.push(newCurriculum2);
					}
				}
			}
			);
		}
		
		$scope.clearCurriculumView = function(){
			if(confirm("Are you sure you want to clear the current template?")){
				for(i in $scope.curricula){
					if($scope.displayedCurriculum.meta.curriculumName == $scope.curricula[i].type){
						for(j in $scope.curricula[i].versions){
							var version = $scope.curricula[i].versions[j];
							if(version.meta.isMaster){
								$scope.displayedCurriculum = version;
								$scope.isEditable = false;
								break;								
							}
						}
					}
				}
//				$scope.displayedCurriculum = null;
//				$scope.template = null;
			}
		}
		
		/* END CURRICULUM MANIPULATION FUNCTION DEFINITIONS */
		
		/* BEGIN TOPIC POOL FUNCTION DEFINITIONS */
		
		$scope.getTopicPool = function(){
			return $http({
				url: "rest/api/v1/Curriculum/TopicPool",
				method: "GET",
				
			}).then(function(response){
				var topics = response.data;
				//parse the response into the local (front end) json object format
				for(i in topics){
					var topic = topics[i];

					//raise flag if there exists a a topic of this type already
					var topicExists = false;
					//determine if $scope.topics has a type of topic.Name already. If so add the subtopic to the existing list
					for(var j in $scope.topics){
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
				return $scope.topics;
			});
		};	
		
		$scope.newTopic = function(input, parentTopic){
			if(parentTopic){
				//search topics for the matching type passed
				for(i in $scope.topics){
					if($scope.topics[i].name == parentTopic){
						var newTopic = {
								id: $scope.topics[i].subtopics.length,
								name: input,
								topic: {id:$scope.topics[i].id, name:parentTopic},
								type: {id:1, name:"Lesson"}
							}
						//addthe subtopic locally
						$scope.topics[i].subtopics.push(newTopic);
						
						//generate random color (hex) for the new topic
						var color = '#' + Math.floor(Math.random() * 16777215).toString(16);
						$scope.topicColors.push({'type': newTopic.name, 'color': color});

						//persist the subtopic to the db
						$http({
							method:'POST',
							url: 'rest/api/v1/Subtopic/Add',
							params:{
								subtopicName: input,
								topicId: $scope.topics[i].id + 1,
								typeId: 1
							}
						});
					}
				}
			}else{
				var newTopic2 = {
						id: $scope.topics.length,
						name: input,
						subtopics: []
				}
				$scope.topics.push(newTopic2);
				$http({
					method: 'POST',
					url: 'rest/api/v1/Topic/Add',
					params: {
						name:newTopic2.name
					}
				});
			}
		};
		
		/* END  TOPIC POOL FUNCTION DEFINITIONS */
		
		/* BEGIN CONTROLLER BODY - EXECUTED ON PAGE LOAD */

		//load the topic pool on page load
		$scope.getTopicPool()
		.then(function(topics){$scope.setTopicColors(topics)});

		//get the curricula meta data on page load
		$scope.getCurricula()
		//then load the master versions of each curriculum type
		.then(function(){
			for(i in $scope.curricula){
				var curriculumType = $scope.curricula[i];
				var masterVersion = {};
				for(j in curriculumType.versions){
					if(curriculumType.versions[j].meta.isMaster){
						masterVersion = curriculumType.versions[j];
					}
				}
				$scope.requestCurriculum(masterVersion)
				.then(function(newCurriculum){
					//add newCurriculum as a version to the curricula type:
					for(j in $scope.curricula){
						if($scope.curricula[j].type == newCurriculum.meta.curriculumName){
							$scope.curricula[j].versions[newCurriculum.meta.curriculumVersion - 1] = newCurriculum;
							//load default master curriculum version on page load
							if(newCurriculum.meta.curriculumName == $scope.DEFAULT_TYPE && newCurriculum.meta.isMaster){
								$scope.displayedCurriculum = newCurriculum;
								setTimeout($scope.setWeekProgressBars, 500);
							}
						}
						
					}
					
				});
			}
		});
		/* END CONTROLLER BODY - EXECUTED ON PAGE LOAD */
	}
);
