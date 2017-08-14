
app.controller('loginController', ['SessionService', function($rootScope, $window, $scope, $location, $http, SessionSevice) {

	$rootScope.userRole;
	$scope.msg;

	$rootScope.user;
	$scope.msg;
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

			SessionSevice.set("currentUser", response.data);
			if(SessionSevice.get("currentUser").role == 3){
				SessionService.set("userRole", '(Quality Control)');

				$location.path('/home');
			} else if(SessionSevice.get("currentUser").role == 2){
				SessionService.set("userRole", '(Trainer)');
				$http({
					url: 'rest/api/v1/Batches/InProgress',
					method: 'GET',
					params: {email : SessionSevice.get("currentUser").email}
				}).then(function success (progResponse){
					SessionService.set("trainerBatch", progResponse.data);
					SessionService.set("gotSubtopics", false);
					$location.path('/home');
				}, function error(progResponse){
					$scope.msg = 'Batch Acquisition failed';
				});
			} else if(SessionSevice.get("currentUser").role == 1) {
				SessionService.set("userRole", '(Associate)');
				if(!SessionSevice.get("currentUser").batch){
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
	
	
}]);