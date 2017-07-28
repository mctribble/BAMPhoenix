app.controller("updateAssociateCtrl", ['$rootScope', '$http', '$scope', function($rootScope, $http, $scope){
	
	$scope.updateDisplay = false;	
	
	$scope.testMsg = 'test message from updateAssociateController.js';
	$rootScope.user = {
			/*oldPassword : '',*/
			/*confirmPassword : '',*/
			userId : '',
			fName : '',
			mName : '',
			lName : '',
			email : '',
			pwd : '',
			role : '',
			phone : '',
			phone2 : '',
			/*altPhone : '',*/
			skype : '',
			pwd2 : ''
	};
	
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
			$scope.updateMsg = 'You updated the user successfully.';
			$scope.alertClass = 'alert alert-success';
		
		}, function error(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Update did not work.';
			$scope.alertClass = 'alert alert-danger';
		});
	}
	
	

}])