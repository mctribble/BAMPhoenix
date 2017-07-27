app.controller('loginCtl', function($rootScope, $scope, $location, $http) {
	
	$scope.msg;
	$rootScope.user;
	$scope.logIn = function() {
		var user = {
			email : $scope.email,
			password : $scope.password
		};
		
		$http({
			url: 'authenticate',
			method: 'POST',
			data: user,
	        params: {
	            username: user.email,
	            password: user.password,
	        }
		})
		.then(function success(response){
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'Login successful';
			$rootScope.user = response.data;
			console.log(response.data);
		}, function error(response){
			$location.path('/');
			$scope.message = true;
			$scope.msg = 'Login failed';
		});
	}
	
	
});