var k;
function formatDate(inputStr) {
    var timestamp = parseInt(inputStr, 10);
    var date = new Date(timestamp);
    return date.toISOString().substr(0, 10);
}
var fixeded=[];
var fixedstart=[];

app.controller('batchesAllController', function($scope, SessionService, $rootScope, $location, $http,$filter)
{	
	
	$scope.syncBatchesWithAssignForce = function(){
		
		$http({
			url: 'rest/refreshBatches',
			method: 'GET'
		}).then(function success(response){
			console.log("batches have been updated");
		}, function error(response){
			console.log("batches failed to update");
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
		
			
		
			
			for(var i=0;i<response.data.length;i++){
				response.data[i].startDate=formatDate(response.data[i].startDate)
				response.data[i].endDate=formatDate(response.data[i].endDate)
			}
			//SessionService.set("batchesAll", response.data);
			
			$scope.batchesAll=response.data;			

		}, function error(response){
			$scope.message = true;
			$scope.msg = 'all batches not retrieved';
		});
	}
	
	$scope.goToBatch = function(batch){
		SessionService.set("currentBatch", batch);
		$http({
			
			url: 'rest/api/v1/Calendar/Topics',
			method: 'GET',
			params: {batchId: batch.id}
		})
		.then(function success(response){
			SessionService.set("gotSubtopics", false);
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch retrieved';
			
		}, function error(response){
			SessionService.set("gotSubtopics", false);
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch not retrieved';
		});
	}
	
	$scope.getBatchesAll();
	
});
