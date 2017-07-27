app.controller('batchesAllCtr', function($scope, $location, $http)
{	
	$scope.batches;
	
	$scope.getBatchesAll = function(){
		
		$http({
			url: 'Batches/All.do',
			method: 'GET'
		})
		.then(function success(response){
			$location.path('/batchesAll');
		}, function error(response){
			$location.path('/batchesAll');
		});
	}
	
	$scope.getBatchesAll();
});
