app.controller('batchesPast', function($scope, $rootScope, $location, $http)
{
	$scope.smg;
	$scope.batchesPast;
	
	$scope.getBatchesPast = function(){
		$rootScope.currentBatch = batch;
		var emailer = $rootScope.user.email;
		
		$http({
			url: 'Batches/Past.do',
			method: 'GET',		
			params: {email: emailer}
		})
		.then(function success(response){
			console.log("past batches: " + response.data);
			$scope.message = true;
			$scope.msg = 'past batches retreived';
			$scope.batchesPast = response.data;
			
		}, function error(response){
			$scope.message = true;
			$scope.msg = 'past batches not retreived';
		});
	}
	
	$scope.goToBatch = function(batch){
				console.log(batch.id);
		$http({
			
			url: "Calendar/Topics.do?batchId=" + batch.id,
			method: 'GET'
			
		})
		.then(function success(response){
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch retreived';
			
		}, function error(response){
			$location.path('/home');
			$scope.message = true;
			$scope.msg = 'batch not retreived';
		});
	}
	
	$scope.getBatchesPast();
});