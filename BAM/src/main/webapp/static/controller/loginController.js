app.controller('loginCtl', function($scope, $http) {
	console.log("here");
	$scope.logIn = function() {
		var user = {
			email : $scope.email,
			password : $scope.password
		};

		console.log(user.email + " " + user.password);
		console.log();

		$http.post('authenticate', {
			params : {
				username : user.email,
				password : user.password,
			}
		}).then(function() {
			console.log("Success");
		}, function() {
			console.log("Fail");
		});
	}
});