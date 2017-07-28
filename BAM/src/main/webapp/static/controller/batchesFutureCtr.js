app.controller('batchesFuture', function($scope, $rootScope, $location, $http)
{
	$scope.smg;
	$scope.batchesFuture;
	$scope.getBatchesFuture = function(){
		
		var emailer = $rootScope.user.email;
		console.log(emailer);
		$http({
			url: 'Batches/Future.do',
			method: 'GET',	
			params: {email: emailer}
		})
		.then(function success(response){
			$scope.message = true;
			$scope.msg = 'future batches retreived';
			$scope.batchesFuture = response.data;
			console.log(response.data);
		}, function error(response){
			$scope.message = true;
			$scope.msg = 'future batches not retreived';
		});
	}
	
	$scope.goToBatch = function(id){
		console.log("going to batch" + id);
		
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
	
	$scope.getBatchesFuture();
});