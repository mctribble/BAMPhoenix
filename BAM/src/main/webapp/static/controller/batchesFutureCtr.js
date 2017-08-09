app.controller('batchesFuture', function($scope, $rootScope, $location, $http)
{
	$scope.smg;
	$scope.batchesFuture;
	$scope.getBatchesFuture = function(){
		
		var emailer = $rootScope.user.email;
		console.log(emailer);
		$http({
			url: 'rest/api/v1/Batches/Future',
			method: 'GET',	
			params: {email: emailer}
		})
		.then(function success(response){
			console.log("future batches: " + response.data)
			$scope.message = true;
			$scope.msg = 'future batches retreived';
			$scope.batchesFuture = response.data;
		}, function error(response){
			$scope.message = true;
			$scope.msg = 'future batches not retreived';
		});
	}
	
	$scope.goToBatch = function(batch){
		$rootScope.currentBatch = batch;
		$http({
			
			url: "rest/api/v1/Calendar/Topics?batchId=" + batch.id,
			method: 'GET'
			
		})
		.then(function success(response){
			$rootScope.gotSubtopics = false;
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch retreived';
			
		}, function error(response){
			$rootScope.gotSubtopics = false;
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch not retreived';
		});
	}
	
	$scope.getBatchesFuture();
});