var k;
function formatDate(inputStr) {
    var timestamp = parseInt(inputStr, 10);
    var date = new Date(timestamp);
    return date.toISOString().substr(0, 10);
}
var fixeded=[];
var fixedstart=[];

app.controller('batchesAllController', function($scope, SessionService, $rootScope, $analytics, $location, $http,$filter)
{	
	$analytics.pageTrack('/batchesAll');
	$scope.syncBatchesWithAssignForce = function(){
		
		$scope.currentlyLoading = true;
		
		$http({
			url: 'rest/refreshBatches',
			method: 'GET'
		}).then(function success(response){
			$scope.currentlyLoading = false;
		}, function error(response){
			$scope.currentlyLoading = false;
		})
	}
	
	$scope.msg;
	$scope.getBatchesAll = function(){
		
		$http({
			url: 'rest/api/v1/Batches/All',
			method: 'GET'
		})
		.then(function success(response){
			$scope.message = true;
			$scope.msg = 'all batches retrieved';
			
			$scope.batchesAll=response.data;

		}, function error(response){
			$scope.message = true;
			$scope.msg = 'all batches not retrieved';
		});
	}
	
	$scope.goToBatch = function(batch){
		SessionService.set("currentBatch", batch);
		SessionService.unset("futureBatch");
		$http({
			
			url: 'rest/api/v1/Calendar/Topics',
			method: 'GET',
			params: {batchId: batch.id}
		})
		.then(function success(response){
			SessionService.set("gotSubtopics", false);
			$location.path('/calendar');
			$scope.message = true;
			$scope.msg = 'batch retrieved';
			
		}, function error(response){
			SessionService.set("gotSubtopics", false);
			$location.path('/calendar');
			$scope.message = true;
			$scope.msg = 'batch not retrieved';
		});
	}
	
	$scope.getBatchesAll();
	
});
