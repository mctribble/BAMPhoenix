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
			data: $scope.email,
	        params: {
	            username: $scope.email,
	        }
		})
		.then(function success(response){
			$location.path('/');
			$scope.message = true;
			$scope.msg2 = 'Email sent. Please check your inbox for account recovery option.';
			
			},  
			function error(response){
				$location.path('/');
				$scope.message = true;
				$scope.msg2 = 'User does not exist in the system';
			});
		}
});
