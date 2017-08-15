<<<<<<< HEAD
app.controller("bamUserRegisterController", ['$http', '$scope', '$location', '$timeout', function($http, $scope, $location, $timeout){
	
	$scope.updateDisplay = false;	
	
	$scope.testMsg = 'test message from registerController.js';
	$scope.pwd='empty'

	$scope.addUser = function(){}
		if($scope.pwd == $scope.confirm_password){
		$http({
			url: 'rest/api/v1/Users/Register',
			method: 'POST',
			headers: {
		        'Content-Type': 'application/json',
		        'Accept': 'application/json'
		    },
			data: $scope.user
		}).then (function success(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = "You updated the user successfully";
			$scope.updateMsg1 = "Redirecting to Login Page...";
			$scope.alertClass = 'alert alert-success';
			 $timeout(function() {
			     $location.path('/');
			     }, 3000);
		}, function error(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Email is Already in Use';
			$scope.alertClass = 'alert alert-danger';
		});
		}else{
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Passwords do not match';
			$scope.alertClass = 'alert alert-danger';
		}
	}
])
=======
app.controller("bamUserRegisterController", ['$http', '$scope', '$location', '$timeout', function($http, $scope, $location, $timeout){
	
	$scope.updateDisplay = false;	
	
	$scope.testMsg = 'test message from registerController.js';

	$scope.addUser = function(){	
		if($scope.user.pwd == $scope.confirm_password){
		$http({
			url: 'rest/api/v1/Users/Register',
			method: 'POST',
			headers: {
		        'Content-Type': 'application/json',
		        'Accept': 'application/json'
		    },
			data: $scope.user
		}).then (function success(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = "You updated the user successfully";
			$scope.updateMsg1 = "Redirecting to Login Page...";
			$scope.alertClass = 'alert alert-success';
			 $timeout(function() {
			     $location.path('/');
			     }, 3000);
		}, function error(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Email is Already in use or Invalid Inputs';
			$scope.alertClass = 'alert alert-danger';
		});
		}else{
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Passwords do not match';
			$scope.alertClass = 'alert alert-danger';
		}
	}
}])
>>>>>>> 5b98851782eac1c9046cbe660c408c8adbc8d0e8
