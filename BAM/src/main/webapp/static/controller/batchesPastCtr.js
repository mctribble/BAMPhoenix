app.controller('batchesPastCtr', function($scope, $rootScope, $location, $http)
{
	$scope.smg;
	$scope.batchesPast;
	$scope.getBatchesPast = function(){
		
		var email = $rootScope.user.email;
		
		$http({
			url: 'Batches/Past.do',
			method: 'GET',	
			data: email,	
			params: email
		})
		.then(function success(response){
			$scope.message = true;
			$scope.msg = 'past batches retreived';
			$scope.batchesPast = response.data;
			console.log(response.data);
		}, function error(response){
			$scope.message = true;
			$scope.msg = 'past batches not retreived';
		});
	}
	
	$scope.getBatchesPast();
});