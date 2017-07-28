app.controller('batchesPast', function($scope, $rootScope, $location, $http)
{
	console.log("here");
	$scope.smg;
	$scope.batchesPast;
	
	$scope.getBatchesPast = function(){
		
		var emailer = $rootScope.user.email;
		
		$http({
			url: 'Batches/Past.do',
			method: 'GET',		
			params: {email: emailer}
		})
		.then(function success(response){
			console.log(response.data);
			$scope.message = true;
			$scope.msg = 'past batches retreived';
			$scope.batchesPast = response.data;
			
		}, function error(response){
			$scope.message = true;
			$scope.msg = 'past batches not retreived';
			console.log("past err");
		});
	}
	
	$scope.getBatchesPast();
});