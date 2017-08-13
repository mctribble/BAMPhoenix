/**
 * Defines a controller to handle DOM manipulation of the Curriculum HTML page
 * @Author Brian McKalip
 */
app.controller(
	"curriculumController",
	
	function($rootScope, $scope, $http) {
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
		$scope.curricula = 
			[
				{
					type: 'java',
					versions:[
						{
							weeks: [
								{
									days : 
										[
											{subtopics : [ 'Java', 'Subtopic 2', 'subtopic 3' ]},
											{subtopics : [ 'Subtopic 1', 'Subtopic 2' ]},
											{subtopics : [ 'Subtopic 1', 'Subtopic 2', 'subtopic 3', 'Subtopic 4' ]},
											{subtopics : []}, 
											{subtopics : [ 'Subtopic 1' ]}
										]
								},
							
								{
									days : 
										[
											{subtopics : [ 'SQL', 'Subtopic 2', 'subtopic 3' ]},
											{subtopics : [ 'Subtopic 1', 'Subtopic 2' ]},
											{subtopics : [ 'Subtopic 1', 'Subtopic 2', 'subtopic 3', 'Subtopic 4' ]},
											{subtopics : []}, 
											{subtopics : [ 'Subtopic 1' ]}
										]
								},
								
								{
									days : 
										[
											{subtopics : [ '.net', 'Subtopic 2', 'subtopic 3' ]},
											{subtopics : [ 'Subtopic 1', 'Subtopic 2' ]},
											{subtopics : [ 'Subtopic 1', 'Subtopic 2', 'subtopic 3', 'Subtopic 4' ]},
											{subtopics : []}, 
											{subtopics : [ 'Subtopic 1' ]}
										]
								},
								
								{
									days : 
										[
											{subtopics : [ 'Subtopic 1', 'Subtopic 2', 'subtopic 3' ]},
											{subtopics : [ 'Subtopic 1', 'Subtopic 2' ]},
											{subtopics : [ 'Subtopic 1', 'Subtopic 2', 'subtopic 3', 'Subtopic 4' ]},
											{subtopics : []}, 
											{subtopics : [ 'Subtopic 1' ]}
										]
								}
							]
						}
						//END OF FIRST VERSION
					] //END OF VERSIONS
				}, //END OF CURRICULUM
			] //END OF CURRICULA ARRAY
		
		$scope.addWeek = function(){
			$scope.displayedCurriculum.weeks.push({days:[]});
		}
		
		$scope.deleteWeek = function(index){
			if(confirm("Are you sure you want to delete week #" + index + "?")){
				$scope.displayedCurriculum.weeks.splice(index-1, 1);
			}
		}
		
		//when an existing curriculum is selected, it will be loaded into the template
		$scope.setTemplate = function(type, version){
			$scope.template.meta.type = $scope.curricula[type].type;
			$scope.template.meta.version = type;
			$scope.template.weeks = $scope.curricula[type].versions[version].weeks;
		}
		
		//create a new curriculum with the template, if the template is null, a new curriculum will be created
		$scope.newCurriculum = function(){
			$scope.displayedCurriculum.meta.type = $scope.template.meta.type;
			$scope.displayedCurriculum.meta.version = $scope.template.meta.version;
			$scope.displayedCurriculum.weeks = $scope.template.weeks;
			
			
		}
	}
);