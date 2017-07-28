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
	
	$scope.goToBatch = function(id){
		console.log("going to batch" + id);
		
		var batchId = id;
		
		$http({
			
			url: 'Calendar/Weeks.do',
			method: 'GET'
			
		})
		.then(function success(response){
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch retreived';
			
		}, function error(response){
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch not retreived';
		});
	}
	
	$scope.getBatchesAll();
});
