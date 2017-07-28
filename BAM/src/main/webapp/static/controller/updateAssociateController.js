app.controller("updateAssociateCtrl", ['$scope', '$http', function($scope, $http){
	
	$scope.updateDisplay = false;	
	
	$scope.testMsg = 'test message from updateAssociateController.js';
	$scope.associate = {
			oldPassword : '',
			newPassword : '',
			confirmPassword : '',
			fname : '',
			mname : '',
			lname : '',
			phone : '',
			altPhone : '',
			skype : ''
	};
	
	updateAssociate = function(){
		$http({
			method: 'POST',
			url: 'Batches/update.do',
			data: $scope.associate
		}).then (function success(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'You updated the user successfully.';
		
		}, function error(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Update did not work.';
			
		});
	}
	
	

}])