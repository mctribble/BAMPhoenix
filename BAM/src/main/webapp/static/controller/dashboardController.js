/**
 * 
 */
app.controller('dashboardController', function($http, $scope, SessionService, $rootScope) {
	$scope.user;
	//set batchId with the id of the currentBatch if it exists else use the trainerBatch
	var batchId;
	if(SessionService.get("currentBatch"))
	{
		batchId = SessionService.get("currentBatch").id;
		SessionService.set("currentBatchName", SessionService.get("currentBatch").name);
		
	}else
	{
		batchId = SessionService.get("trainerBatch").id; //if currentBatch is not set use the trainerBatch's id
		SessionService.set("currentBatchName", SessionService.get("trainerBatch").name);
		console.log(batchId);
	}
	
	
	$scope.getData = function() {
			
		if($scope.user){
			var currentDate = new Date().getTime();
			
			if(SessionService.get("trainerBatch").endDate > currentDate){
				console.log(SessionService.get("trainerBatch").endDate);
				$scope.message = SessionService.get("trainerBatch").name;
				$scope.currentBatchStart1 = SessionService.get("trainerBatch").startDate;
				$scope.currentBatchEnd1 = SessionService.get("trainerBatch").endDate;
				
				//Count difference between start date and currentDate
				function weeksBetween(d1, d2) {
				    return Math.round((d2 - d1) / (7 * 24 * 60 * 60 * 1000));
				}
				
				var dif = weeksBetween($scope.currentBatchStart1, currentDate);
				//Current week number
				$scope.weekNum = dif;
				
		}else{
			$scope.message = 'You have no current batches';
		}			
			$http({
				url: "rest/api/v1/Users/InBatch",
				method: 'GET',
				params: {
					batchId: batchId
				}
			}).then(function(response){
				console.log(response.data);
				$scope.usersInBatch = response.data
				for(var i = 0; i < $scope.usersInBatch.length; i++) {
					$scope.batchUsers = $scope.usersInBatch[i];
				    console.log($scope.batchUsers.fName);
				}
			})
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
