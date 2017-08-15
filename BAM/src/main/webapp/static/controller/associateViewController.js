app.controller("associateViewController", function($scope, $rootScope, $http){
	
	var bId;
	$scope.currentBatchName;
	
	if($rootScope.currentBatch != null)
	{
		$scope.currentBatchName= true;
		$scope.currentBatchName = $rootScope.currentBatch.name;
		bId = $rootScope.currentBatch.id;
	}
	else
	{
		$scope.currentBatchName= true;
		$scope.currentBatchName = $rootScope.trainerBatch.name;
		bId = $rootScope.trainerBatch.id;
	}
	
	
	$http({
		url: "rest/api/v1/Users/InBatch",
		method: "GET",
		params: {batchId: bId}
	}).then(function(response){
		$scope.associateList = response.data;
	}, function(response){
		$scope.message = true;
		$scope.msg = "Failed to get users";
	})
});