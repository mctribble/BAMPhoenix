app.controller("associateUpdateController", ['SessionService','$http', '$scope', '$rootScope', function(SessionService, $http, $scope, $rootScope){
	
	$scope.updateDisplay = false;	
	
	$scope.users = SessionService.get("currentUser");
	
	SessionService.set("currentBatch", null);
	
	$scope.updateAssociate = function(){
		$http({
			url: 'rest/api/v1/Users/Update',
			method: 'POST',
			headers: {
		        'Content-Type': 'application/json', 
		        'Accept': 'application/json' 
		    },
			data: $scope.users
		}).then (function success(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Update Successful';
			$scope.alertClass = 'alert alert-success';
			SessionService.set("currentUser", $scope.users);
			$rootScope.user = SessionService.get("currentUser");
			
		
		}, function error(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Update Failed';
			$scope.alertClass = 'alert alert-danger';
		});
	}
}])