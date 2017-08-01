app.controller("registerCtrl", ['$http', '$scope', '$location', '$timeout', function($http, $scope, $location, $timeout){
	
	$scope.updateDisplay = false;	
	
	$scope.testMsg = 'test message from registerController.js';

	$scope.addUser = function(){	
		console.log("IN the FUNC")
		console.log($scope.user)
		if($scope.user.pwd == $scope.confirm_password){
		$http({
			url: 'Users/Register.do',
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
			console.log("Didnt work");
		});
		console.log("Last")
		}else{
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Passwords do not match';
			$scope.alertClass = 'alert alert-danger';
		}
		
	}
	
	

}])