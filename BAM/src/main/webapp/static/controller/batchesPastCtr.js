app.controller('batchesPast', function($scope, $rootScope, $location, $http)
{
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
		
		$rootScope.currentBatch = batch;
		console.log("current batch" + batch.name);
		//var batchId = id;
		
		$http({
			
			url: 'Calendar/Topics.do',
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
	
	$scope.getBatchesPast();
});