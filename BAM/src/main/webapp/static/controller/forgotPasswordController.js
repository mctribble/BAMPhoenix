/**
 * 
 */
app.controller('forgotPasswordController', function ($rootScope, $scope, SessionService, $location, $http){

	$scope.forgot = function() {
		var user = {		
 			email : $scope.email,	
 		};
		$http({
			url: 'rest/api/v1/Users/Recovery',
			method: 'POST',
			data: user.email,
	        params: {
	            username: user.email,
	        }
		})
		.then(function success(response){
			$location.path('/');
			$scope.message = true;
			$scope.msg = 'Email sent. Please check your inbox for account recovery option.';
			$scope.alertClass = 'alert alert-success';			
			},  
			function error(response){
				$location.path('/');
				$scope.message = true;
				$scope.msg = 'User does not exist in the system';
				$scope.alertClass = 'alert alert-danger';

			});
		}
});
