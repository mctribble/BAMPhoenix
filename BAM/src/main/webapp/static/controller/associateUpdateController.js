app.controller("associateUpdateController", ['$rootScope', 'SessionService','$http', '$scope', function($rootScope, SessionService, $http, $scope){
	
	$scope.updateDisplay = false;	
	
	$scope.testMsg = 'test message from updateAssociateController.js';

	SessionService.set("currentBatch", null);
	
	$scope.updateAssociate = function(){

		$http({
			url: 'rest/api/v1/Users/Update',
			method: 'POST',
			headers: {
		        'Content-Type': 'application/json', 
		        'Accept': 'application/json' 
		    },
			data: SessionService.get("currentUser")
		}).then (function success(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Update Successful';
			$scope.alertClass = 'alert alert-success';
		
		}, function error(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Update Failed';
			$scope.alertClass = 'alert alert-danger';
		});
	}
}])