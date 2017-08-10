app.controller('loginCtl', function($rootScope, $scope, $location, $http) {
	$rootScope.userRole;
	
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
			if($rootScope.user.role == 3){
				$rootScope.userRole = '(Quality Control)';
			} else if($rootScope.user.role == 2){
				$rootScope.userRole = '(Trainer)';
				$http({
					url: 'rest/api/v1/Batches/InProgress',
					method: 'GET',
					params: {email : $rootScope.user.email}
				}).then(function success (progResponse){
					$rootScope.trainerBatch = progResponse.data;
					$rootScope.gotSubtopics = false;
					$location.path('/home');
				}, function error(progResponse){
					$scope.msg = 'Batch Acquisition failed';
				});
			} else if($rootScope.user.role == 1) {
				$rootScope.userRole = '(Associate)';
				if(!$rootScope.user.batch)
				{
					$location.path('/noBatch');
				}else{
					$rootScope.gotSubtopics = false;
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