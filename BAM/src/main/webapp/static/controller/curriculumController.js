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
			console.log("new string: " + str);
			return str;
		}
		
		//constant array defining valid days of the week 
		$scope.weekdays = [ "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" ];
		
		//list of available topics in the topic pool. this will eventually be loaded through  a get request
		$scope.topics = [ {
			name : "Java",
			subtopics : [ 'core', 'spring', 'hibernate' ]
		},

		{
			name : "SQL",
			subtopics : [ 'DML', 'DDL', 'TCL' ]
		},

		{
			name : ".NET",
			subtopics : [ 'C', 'C++', "C#" ]
		} ];
		
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
			//attempt to look for curr in curricula object before doing http req (caching)
			console.log("in setTemplate - Curriculum: ")
			console.log(curriculum);
			
			if(curriculum){
				var newCurriculum;
				//do request
				
				//add curriculum object to the version
				$scope.template.meta = curriculum
				$scope.template.weeks; //r
				//add the curriculum to the curricula object
			}else{
				
			}
			
//			if(type == -1){
//				//default template
//				console.log("blank template");
//				$scope.template.meta.type =  null;
//				$scope.template.meta.version = null;
//				$scope.template.weeks = [];
//
//			}else{
//				$scope.template.meta.type = $scope.curricula[type].type;
//				$scope.template.meta.version = version;
//				$scope.template.weeks = $scope.curricula[type].versions[version].weeks;	
//			}
		}
			
		//create a new curriculum with the template, if the template is null, a new curriculum will be created
		$scope.newCurriculum = function(){
//			$scope.displayedCurriculum.meta.type = $scope.template.meta.type;
//			$scope.displayedCurriculum.meta.version = $scope.template.meta.version;
//			$scope.displayedCurriculum.weeks = $scope.template.weeks;

			console.log("creating next version of " + $scope.template.meta.type);
			
			var curriculum = {
				meta: {
					type: $scope.template.meta.type,
					version: '0'
				},
				weeks: $scope.template.weeks
			}
			
			//loop through the curricula looking for the curriculum type, when found count number of versions and set this curr. object's version to it
			for(item in $scope.curricula){
				if($scope.curricula[item].type == $scope.template.meta.type){
					curriculum.meta.version = $scope.curricula[item].versions.length;
				}
			}
			
			$scope.displayedCurriculum = curriculum;
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
			if($scope.displayedCurriculum.meta.type){
				console.log("type: " + $scope.displayedCurriculum.meta.type);
				for(item in $scope.curricula){
					console.log("checking type: " + $scope.curricula[item]);
					if($scope.curricula[item].type == $scope.displayedCurriculum.meta.type){
						console.log("found match - adding curriculum")
						$scope.curricula[item].versions.push({weeks: $scope.displayedCurriculum.weeks});
					}
				}
			}else{
				var curriculum = {
					type:'',
					versions: [
					]
				};
				
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
				for(i in curricula){
					var curriculum = curricula[i];
//					console.log(curriculum);
					//determine if $scope.curricula has a type of curriculum.Name already. If so add it as an additional version of the type
					var curriculumTypeExists = false;
					for(j in $scope.curricula){
						var localCurricula = $scope.curricula[j];
						if(localCurricula.curriculum_Name == curriculum.curriculum_Name){
							console.log(localCurricula.curriculum_Name + " == " +  curriculum.curriculum_Name);
							console.log("adding version " + curriculum.curriculum_Version + " of " + localCurricula.curriculum_Name + "")
							curriculumTypeExists = true;
							curriculum.weeks = [
								{
									days : []
								}
							]; //add a temp empty weeks array
							//insert the curriculum into the existing curr type as a version of that type (as specified by the received object) 
							$scope.curricula[j].versions.splice(curriculum.curriculum_Version, 0, curriculum);
							break;
						}
					}
					
					//if a curriculum of type curriculum.curriculum_Name does not exist, add it as a new base curriculum type
					if(!curriculumTypeExists){
						console.log("adding curr type:");
						console.log(curriculum);
						var newCurriculum = {
								type: curriculum.curriculum_Name,
								versions: []
						};
						console.log("Version: " + curriculum.curriculum_Version);
						curriculum.weeks = [
							{
								days : []
							}
						]; //add a temp empty weeks array
						newCurriculum.versions.splice(curriculum.curriculum_Version, 0, curriculum);
						console.log("New curriculum: ");
						console.log(newCurriculum);
						$scope.curricula.push(newCurriculum);
					}
				}
				
			});
		}
		//call the getCurriclua() function when the page is loaded
		$scope.getCurricula();
	}
);