/**
 * Defines a controller to handle DOM manipulation of the Curriculum HTML page
 */
app.controller("curriculumController", 
	function($rootScope, $scope, $location, $http){
		$scope.topicFilter = "";
		
		console.log("In Curr controllers");
		$scope.topics = [];
		
		$scope.topics.push({'name':"Java"});
		$scope.topics.push({'name':"SQL"});
		$scope.topics.push({'name':".NET"});
		
		console.log($scope.topics);
		
		$scope.subtopics = [];
		
		$scope.subtopics.push({'id':1, 'name':'core'});
		$scope.subtopics.push({'id':1, 'name':'spring'});
		$scope.subtopics.push({'id':1, 'name':'hibernate'});
	}
);