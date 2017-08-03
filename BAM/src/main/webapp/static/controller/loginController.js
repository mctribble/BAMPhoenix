app.controller('loginCtl', function($rootScope, $scope, $location, $http) {
	
	$scope.msg;
	$rootScope.user;
	$rootScope.trainerBatch;
	$scope.logIn = function() {
		var user = {
			email : $scope.email,
			password : $scope.password
		};
		
		$http({
			url: 'authenticate',
			method: 'POST',
			data: user,
	        params: {
	            username: user.email,
	            password: user.password,
	        }
		})
		.then(function success(response){
			$rootScope.user = response.data;
			console.log($rootScope.user)
			$rootScope.gotSubtopics = false;
			if($rootScope.user.role == 2){
				$http({
					url: 'Batches/InProgress.do',
					method: 'GET',
					params: {email : $rootScope.user.email}
				}).then(function success (progResponse){
					$rootScope.trainerBatch = progResponse.data;
					$location.path('/home');
				}, function error(progResponse){
					$scope.msg = 'Batch Acquisition failed';
				});
			} else if($rootScope.user.role == 1) {
				if(!$rootScope.user.batch)
				{
					$location.path('/noBatch');
				}else{
					$location.path('/home');
				}
				
			}else{
				$location.path('/batchesAll');
			}
			
		}, function error(response){
			$location.path('/');
			$scope.message = true;
			$scope.msg = 'Wrong Credentials';
		});
	}
	
	
});