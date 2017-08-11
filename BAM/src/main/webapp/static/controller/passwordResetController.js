app.controller("passwordResetController", ['$rootScope', '$http', '$scope', function($rootScope, $http, $scope){
	
	$scope.resetDisplay = false;	
	
	$scope.testMsg = 'test message from resetController.js';
	$rootScope.currentBatch = null;
	
	$scope.resetPassword = function(){
		if($scope.pwdNew == $scope.confirmPwd){
		$http({
			url: 'rest/api/v1/Users/Reset',
			method: 'POST',
			data: JSON.stringify({email : $rootScope.user.email,
		        	 pwd : $scope.pwdOld,
		        	 pwd2 : $scope.pwdNew
		    })
		}).then (function success(response){
			$scope.resetDisplay = true;
			$scope.resetMsg = 'Password Reset Successful';
			$scope.alertClass = 'alert alert-success';
			$scope.pwdNew = "";
			$scope.pwdOld = "";
			$scope.confirmPwd = "";		
		}, function error(response){
			$scope.resetDisplay = true;
			$scope.resetMsg = 'Password Reset Failed';
			$scope.alertClass = 'alert alert-danger';
			$scope.pwdNew = "";
			$scope.pwdOld = "";
			$scope.confirmPwd = "";
		});
		}else{
			$scope.resetDisplay = true;
			$scope.resetMsg = 'Passwords do not match';
			$scope.alertClass = 'alert alert-danger';
			$scope.pwdNew = "";
			$scope.pwdOld = "";
			$scope.confirmPwd = "";
		}
	}
	
	

}])