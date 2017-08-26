/**
 * @author Kosiba Oshodi-Glover
 */
app.controller('myBatchesController', function($scope, SessionService, $rootScope, $location, $http)
{
	$scope.msg;
	
	var currentDate = new Date().getTime();
	
	$scope.getmyBatches = function(){
		$http({
			url: 'rest/api/v1/Batches/All',
			method: 'GET'
		})
		.then(function success(response){
			$scope.myBatches1 = response.data
			
			$scope.inMyBatch = [];
				batchName = [];
				trainerFName = [];
				trainerLName = [];
				type = [];
				batchStartDate = [];
				batchEndDate = [];
				batchId =[];
			
			for(var i=0; i < $scope.myBatches1.length; i++){
                if($scope.myBatches1[i].endDate > currentDate && $scope.myBatches1[i].startDate < currentDate && $scope.myBatches1[i].trainer.userId == SessionService.get("currentUser").userId){
                    	$scope.myBatches = $scope.myBatches1[i];
                    		
                    	batchName.push($scope.myBatches.name);
                    	trainerFName.push($scope.myBatches.trainer.fName);
                    	trainerLName.push($scope.myBatches.trainer.lName);
                    	type.push($scope.myBatches.type.name);
                    	batchStartDate.push($scope.myBatches.startDate);
                    	batchEndDate.push($scope.myBatches.endDate);
                    	batchId.push($scope.myBatches.id);
                    	
                    	
                    	$scope.inMyBatch.push({
            		    		"batchNames": batchName[0],
            		    		"trainerFNames": trainerFName[0],
            		    		"trainerLNames": trainerLName[0], 
            		    		"types": type[0],
            		    		"startDates": batchStartDate[0],
            		    		"endDates": batchEndDate[0],
            		    		"batchId": batchId[0]
            		    });
                    	
                    	batchName = [];
   						trainerFName = [];
   						trainerLName = [];
   						type = [];
   						batchStartDate = [];
   						batchEndDate = [];
   						batchId = [];
   						
   						console.log($scope.inMyBatch); 
                }
                }
		}); 
	}
	
	$scope.goToBatch = function(batch){
		var thisBatchId = $rootScope.changedBatchId;		
		$http({
			
			url: "rest/api/v1/Calendar/Topics?batchId=" + thisBatchId,
			method: 'GET'
			
		})
		.then(function success(response){
			SessionService.set("gotSubtopics", false);
			$location.path('/calendar');
			$scope.message = true;
			$scope.msg = 'batch retrieved';
			
		}, function error(response){
			SessionService.set("gotSubtopics", false);
			$location.path('/calendar');
			$scope.message = true;
			$scope.msg = 'batch not retrieved';
		});
	}
	
	$scope.getmyBatches();
});