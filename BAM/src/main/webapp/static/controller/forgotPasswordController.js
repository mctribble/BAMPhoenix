/**
 * 
 */
app.controller('forgotPasswordController', function ($rootScope, $scope, $location, $http){
$rootScope.userRole;
	
	$rootScope.user;
	$scope.msg;
	$scope.forgot = function() {
		console.log('forgot pass fn');
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
			
			},  
			function error(response){
				$location.path('/');
				$scope.message = true;
				$scope.msg = 'User does not exist in the system';
			});
		}
});