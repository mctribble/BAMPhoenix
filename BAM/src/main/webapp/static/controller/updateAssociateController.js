app.controller("updateAssociateCtrl", ['$rootScope', '$http', '$scope', function($rootScope, $http, $scope){
	
	$scope.updateDisplay = false;	
	
	$scope.testMsg = 'test message from updateAssociateController.js';
	$rootScope.currentBatch = null;
	
	$scope.updateAssociate = function(){
		$http({
			url: 'Users/Update.do',
			method: 'POST',
			headers: {
		        'Content-Type': 'application/json', 
		        'Accept': 'application/json' 
		    },
			data: $rootScope.user
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