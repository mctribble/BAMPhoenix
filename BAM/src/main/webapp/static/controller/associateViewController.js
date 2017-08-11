app.controller("associateViewController", function($scope, $rootScope, $http){
	
	console.log("Associates Controller");
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
	
	console.log("batch id" + bId);
	
	$http({
		url: "rest/api/v1/Users/InBatch",
		method: "GET",
		params: {batchId: bId}
	}).then(function(response){
		console.log("users:" + response.data);
		$scope.associateList = response.data;
	}, function(response){
		$scope.message = true;
		$scope.msg = "Failed to get users";
	})
});