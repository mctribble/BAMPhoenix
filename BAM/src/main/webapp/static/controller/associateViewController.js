app.controller("associateViewController", function($scope, SessionService, $rootScope, $http){
	
	var bId;
	
	if(SessionService.get("currentBatch") != null)
	{
		$scope.currentBatchName= true;
		$scope.currentBatchName = SessionService.get("currentBatch").name;
		bId = SessionService.get("currentBatch").id;
	}
	else
	{
		$scope.currentBatchName= true;
		$scope.currentBatchName = SessionService.get("trainerBatch").name;
		bId = SessionService.get("trainerBatch").id;
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