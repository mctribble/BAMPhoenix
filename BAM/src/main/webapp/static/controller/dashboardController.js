/**
 * 
 */
app.controller('dashboardController', function($http, $scope) {
	$scope.user;
	
	$scope.getData = function() {
			
		if($scope.user){
			var currentDate = new Date().getTime();
			
			if($scope.trainerBatch.endDate > currentDate){
				$scope.message = $scope.trainerBatch.name;
				$scope.currentBatchStart1 = $scope.trainerBatch.startDate;
				$scope.currentBatchEnd1 = $scope.trainerBatch.endDate;
				
				//Count difference between start date and currentDate
				function weeksBetween(d1, d2) {
				    return Math.round((d2 - d1) / (7 * 24 * 60 * 60 * 1000));
				}
				
				var dif = weeksBetween($scope.currentBatchStart1, currentDate);
				console.log('weeeks: ' + dif);
				//Current week number
				$scope.weekNum = dif;
				
		}else{
			$scope.message = 'You have no current batches';
		}
			
			$scope.batchmates = $scope.batch.usersInBatch;
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
