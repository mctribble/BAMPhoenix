/**
 * Defines a controller to handle DOM manipulation of the Curriculum HTML page
 */
app.controller(
	"curriculumController",
	
	function($rootScope, $scope, $http) {
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
		
		//constant array defining valid days of the week 
		$scope.weekdays = [ "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" ];

		//weeks which contain days which contain subtopics for the curriculum. in the future this will be loaded from the selected curriculum
		$scope.weeks = 
			[
			
				{
					days : 
						[
							{subtopics : [ 'Subtopic 1', 'Subtopic 2', 'subtopic 3' ]},
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
				},
			
			];
		$scope.deleteWeek = function(index){
			$scope.weeks.splice(index, 1);
		}
	}
);