app.controller('loginController', function($rootScope, $window, $scope, $location, $http, SessionService) {
	$(document).ready(function(){
		$(".navbar").hide();
	});
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
			
		
			
			SessionService.set("currentUser", response.data);
			$rootScope.user = SessionService.get("currentUser");
			if(SessionService.get("currentUser").role == 3){
				SessionService.set("userRole", '(Quality Control)');

				$rootScope.userRole = SessionService.get("userRole");
				$location.path('/home');
			} else if(SessionService.get("currentUser").role == 2){
				SessionService.set("userRole", '(Trainer)');
				$rootScope.userRole = SessionService.get("userRole");

				$http({
					url: 'rest/api/v1/Batches/InProgress',
					method: 'GET',
					params: {email : SessionService.get("currentUser").email}
				}).then(function success (progResponse){
					SessionService.set("trainerBatch", progResponse.data);
					SessionService.set("gotSubtopics", false);
					$location.path('/home');
				}, function error(progResponse){
					$scope.msg = 'Batch Acquisition failed';
				});
			} else if(SessionService.get("currentUser").role == 1) {
				SessionService.set("userRole", '(Associate)');

				$rootScope.userRole = SessionService.get("userRole");

				if(!SessionService.get("currentUser").batch){
					$location.path('/noBatch');
				}else{
					SessionService.set("gotSubtopics", false);
					$location.path('/home');
					
					//sets the number of subtopics
					$http({
		            	method: 'GET',
		            	url: 'rest/api/v1/Calendar/GetNumberOfSubtopics?batchId='+ SessionService.get("currentUser").batch.id
		            })
		            .then(function successCallback(response){
		            	SessionService.set("numberOfSubtopics", response.data);
		            });
					
				}
				
				
			}else{
				$location.path('/batchesAll');
			}
			
		}, function error(response){
			$location.path('/');
			$scope.message = true;
			$scope.msg = 'Wrong Credentials';
			$scope.msg2 = 'Enter Valid Email';
		});
	}
	
	
});