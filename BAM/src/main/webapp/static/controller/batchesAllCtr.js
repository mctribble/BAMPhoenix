app.controller('batchesAllCtr', function($scope, $location, $http)
{
	console.log("in all controller");
	
	$scope.batches;
	
	$scope.getBatchesAll = function(){
		
		$http({
			url: 'Batches/All.do',
			method: 'GET'
		})
		.then(function success(response){
			console.log("in success all controller");
			$location.path('/batchesAll');
		}, function error(response){
			console.log("in err all controller");
			$location.path('/batchesAll');
		});
	}
	
	$scope.getBatchesAll();
});
