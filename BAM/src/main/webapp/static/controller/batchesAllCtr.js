app.controller('batchesAllCtr', function($scope, $rootScope, $location, $http)
{	
	$scope.smg;
	$rootScope.batchesAll;
	$scope.getBatchesAll = function(){
		
		$http({
			url: 'Batches/All.do',
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
		console.log("current batch" + batch.name);
		//var batch = batch;
		
		$http({
			
			url: 'Calendar/Topics.do',
			method: 'GET'
			
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
