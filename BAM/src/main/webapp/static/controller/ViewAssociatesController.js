app.controller("associatesController", function($scope, $rootScope, $http){
	
	console.log("Associates Controller");
	var batchId;
	$scope.currentBatchName;
	
	if($rootScope.currentBatch != null)
	{
		$scope.currentBatchName= true;
		$scope.currentBatchName = $rootScope.currentBatch.name;
		batchId = $rootScope.currentBatch.id;
	}
	else
	{
		$scope.currentBatchName= true;
		$scope.currentBatchName = $rootScope.trainerBatch.name;
		batchId = $rootScope.trainerBatch.id;
	}
	
	$http({
		url: "Users/ById.do",
		method: "GET"
	}).then(function(response){
		$scope.associateList = response.data;
	}, function(response){
		$scope.message = true;
		$scope.msg = "Failed to get users";
	})
});