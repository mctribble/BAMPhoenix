app.controller('batchesPastCtr', function($scope, $rootScope, $location, $http)
{
	$scope.batchesPast;
	$scope.getBatchesPast = function(){
		
		$http({
			url: 'Batches/Past.do',
			method: 'GET'	
		})
		.then(function success(response){
			
		}, function error(response){
			
		});
	}
	
	$scope.getBatchesPast();
});