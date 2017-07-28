app.controller('batchesPast', function($scope, $rootScope, $location, $http)
{
	console.log("here");
	$scope.smg;
	$scope.batchesPast;
	
	$scope.getBatchesPast = function(){
		
		var emailer = $rootScope.user.email;
		
		$http({
			url: 'Batches/Past.do',
			method: 'GET',		
			params: {email: emailer}
		})
		.then(function success(response){
			console.log(response.data);
			$scope.message = true;
			$scope.msg = 'past batches retreived';
			$scope.batchesPast = response.data;
			
		}, function error(response){
			$scope.message = true;
			$scope.msg = 'past batches not retreived';
			console.log("past err");
		});
	}
	
	$scope.goToBatch = function(id){
		
		$rootScope.currentBatch = id;
		
		var batchId = id;
		
		$http({
			
			url: 'Calendar/Topics.do',
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