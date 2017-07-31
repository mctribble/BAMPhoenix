app.controller("associatesController", function($scope, $http){
	
	console.log("Associates Controller");
	$scope.associateList = JSON.parse(ascContTestJSON);
	$http({
		url: "Users/All.do",
		method: "GET"
	}).then(function(response){
		$scope.associateList = response.data;
	}, function(response){
		$scope.message = true;
		$scope.msg = "Failed to get users";
	})
});