app.controller("resetCtrl", ['$rootScope', '$http', '$scope', function($rootScope, $http, $scope){
	
	$scope.updateDisplay = false;	
	
	$scope.testMsg = 'test message from resetController.js';
	$rootScope.currentBatch = null;
	
	$scope.updateAssociate = function(){
		$http({
			url: 'rest/api/v1/Users/Reset',
			method: 'POST',
			headers: {
		        'Content-Type': 'application/json', 
		        'Accept': 'application/json' 
		    },
			data: $rootScope.user
		}).then (function success(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Password Reset Successful';
			$scope.alertClass = 'alert alert-success';
		
		}, function error(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Password Reset Failed';
			$scope.alertClass = 'alert alert-danger';
		});
	}
	
	

}])