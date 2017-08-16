/**
 * 
 */
app.controller('dashboardController', function($http, $scope, $rootScope) {
	
	$scope.getData = function() {
		if($rootScope.user.role == 2){
			console.log('hitting function');
		$http.get(
				'http://assignforce.revaturelabs.com/api/v2/trainer/8'
				).then(function(info) {

			$scope.trainerId = info.data.trainerId;
			$scope.firstName = info.data.firstName;
			$scope.lastName = info.data.lastName;
			$scope.currentBatchStart = info.data.unavailabilities[3].startDate;
			$scope.currentBatchEnd = info.data.unavailabilities[3].endDate;


		})
		}else{
			$scope.message = 'no user info to display.';
		}
	}

});
