app.controller('batchesFutureController', function($scope, SessionService, $rootScope, $location, $http)
{
	$scope.msg;
	$scope.batchesFuture;
	$scope.getBatchesFuture = function(){
		
		var emailer = SessionService.get("currentUser").email;
		$http({
			url: 'rest/api/v1/Batches/Future',
			method: 'GET',	
			params: {email: emailer}
		})
		.then(function success(response){
			$scope.message = true;
			$scope.msg = 'future batches retrieved';
			for(var i=0;i<response.data.length;i++){
				response.data[i].startDate=formatDate(response.data[i].startDate)
				response.data[i].endDate=formatDate(response.data[i].endDate)
			}
			$scope.batchesFuture = response.data;
		}, function error(response){
			$scope.message = true;
			$scope.msg = 'future batches not retrieved';
		});
	}
	
	$scope.goToBatch = function(batch){
		SessionService.set("currentBatch", batch);
		$http({
			
			url: "rest/api/v1/Calendar/Topics?batchId=" + batch.id,
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
	
	$scope.getBatchesFuture();
});
