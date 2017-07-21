app.controller('loginCtl', function($scope)
{
	console.log("here");
	$scope.logIn = function()
	{
		var user = 
		{
				email: $scope.email,
				password: $scope.password
		};
		
		console.log(user.email + " " + user.password);
		console.log();
	}
});