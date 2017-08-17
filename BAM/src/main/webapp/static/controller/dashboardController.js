/**
 * 
 */
app.controller('dashboardController', function($http, $scope, $rootScope) {
	$scope.user;
	//set batchId with the id of the currentBatch if it exists else use the trainerBatch
	var batchId;
	$rootScope.currentBatchName;
	if($rootScope.currentBatch)
	{
		batchId = $rootScope.currentBatch.id;
		$rootScope.currentBatchName = $rootScope.currentBatch.name;
	}else
	{
		batchId = $rootScope.trainerBatch.id; //if currentBatch is not set use the trainerBatch's id
		$rootScope.currentBatchName = $rootScope.trainerBatch.name;
	}
	
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
				//Current week number
				$scope.weekNum = dif;
				
		}else{
			$scope.message = 'You have no current batches';
		}
			if(batchId) //Check if currentBatch is set before using it.
			{
				//get the batch from the server by the id.
				$http({
					url: "rest/api/v1/Batches/ById",
					method: "GET",
					params:{
						batchId: batchId
					}
				}).then(function(response){
					 $scope.batch = response.data //reponse.data is a javascript object (automatically parsed from the JSON from the server)
					 $scope.batch.startDate = new Date($scope.batch.startDate); //get the JavaScript date object from the data sent from the server
					 $scope.batch.endDate = new Date($scope.batch.endDate);
				});
			}
			$http({
				url: "rest/api/v1/Users/InBatch",
				method: 'GET',
				params: {
					batchId: batchId
				}
			}).then(function(response) {
				$scope.batch.usersInBatch = response.data 
				console.log($scope.batch.usersInBatch);
				for(var i = 0; i < $scope.batch.usersInBatch.length; i++) {
					$scope.batch.users = $scope.batch.usersInBatch[i];
				    console.log($scope.batch.users.fName);
				    
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
