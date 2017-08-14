var k;
function formatDate(inputStr) {
    var timestamp = parseInt(inputStr, 10);
    var date = new Date(timestamp);
    return date.toISOString().substr(0, 10);
}
var fixeded=[];
var fixedstart=[];

app.controller('batchesAllController', function($scope, $rootScope, $location, $http,$filter)
{	
	$scope.msg;
	$rootScope.batchesAll;
	$scope.getBatchesAll = function(){
		
		$http({
			url: 'rest/api/v1/Batches/All',
			method: 'GET'
		})
		.then(function success(response){
			$scope.message = true;
			$scope.msg = 'all batches retreived';
		
			
		
			
			for(var i=0;i<response.data.length;i++){
				response.data[i].startDate=formatDate(response.data[i].startDate)
				response.data[i].endDate=formatDate(response.data[i].endDate)
			}
			
			$rootScope.batchesAll = response.data
			
		
			
			
			

		}, function error(response){
			$scope.message = true;
			$scope.msg = 'all batches not retreived';
		});
	}
	
	$scope.goToBatch = function(batch){
		$rootScope.currentBatch = batch;
		$http({
			
			url: 'rest/api/v1/Calendar/Topics',
			method: 'GET',
			params: {batchId: batch.id}
		})
		.then(function success(response){
			$rootScope.gotSubtopics = false;
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch retreived';
			
		}, function error(response){
			$rootScope.gotSubtopics = false;
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch not retreived';
		});
	}
	
	$scope.getBatchesAll();
	
});
