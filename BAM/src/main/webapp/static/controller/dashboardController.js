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
	
		
		
		if ($scope.trainerBatch){
			
			var currentDate = new Date().getTime();
			var startDate = $scope.trainerBatch.startDate;
			var endDate = $scope.trainerBatch.endDate;
			
			var daysComplete = currentDate - startDate;
			var totalDays = endDate - startDate;
			
			$scope.percent = Math.round((daysComplete * 100) / totalDays) + "%";
			
		} else if ($scope.user.batch){
			
			var currentDate = new Date().getTime();
			var startDate = $scope.user.batch.startDate;
			var endDate = $scope.user.batch.endDate;
			
			var daysComplete = currentDate - startDate;
			var totalDays = endDate - startDate;
			
			$scope.percent = Math.round((daysComplete * 100) / totalDays) + "%";
		}
		
		
});
