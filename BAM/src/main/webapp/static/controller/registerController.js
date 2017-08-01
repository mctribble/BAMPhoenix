app.controller("registerCtrl", ['$http', '$scope', function($http, $scope){
	
	$scope.updateDisplay = false;	
	
	$scope.testMsg = 'test message from registerController.js';

	$scope.addUser = function(){	
		console.log("IN the FUNC")
		console.log($scope.user)
		$http({
			url: 'Users/Update.do',
			method: 'POST',
			headers: {
		        'Content-Type': 'application/json', 
		        'Accept': 'application/json' 
		    },
			data: $scope.user
		}).then (function success(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'You updated the user successfully.';
			$scope.alertClass = 'alert alert-success';
			console.log("in success");
		
		}, function error(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Update did not work.';
			$scope.alertClass = 'alert alert-danger';
			console.log("Didnt work");
		});
		console.log("Last")
	}
	
	

}])