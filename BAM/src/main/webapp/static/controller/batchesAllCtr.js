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
			//$location.path('/batchesAll');
			$scope.message = true;
			$scope.msg = 'all batches retreived';
			$rootScope.batchesAll = response.data;
			console.log(response.data);
		}, function error(response){
			//$location.path('/batchesAll');
			$scope.message = true;
			$scope.msg = 'all batches not retreived';
		});
	}
	
	$scope.getBatchesAll();
});
