app.controller('batchesAllCtr', function($scope, $rootScope, $location, $http)
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
			$rootScope.batchesAll = response.data;
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
