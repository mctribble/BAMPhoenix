app.controller('loginCtl', function($scope, $location, $http) {
	
	console.log("here");
	$scope.msg;
	$scope.logIn = function() {
		var user = {
			email : $scope.email,
			password : $scope.password
		};

		console.log(user.email + " " + user.password);
		console.log();
		
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
			$scope.msg = 'you logged in';
		}, function error(response){
			$location.path('/');
			$scope.message = true;
			$scope.msg = 'you did not login';
		});
	}
	
	
});