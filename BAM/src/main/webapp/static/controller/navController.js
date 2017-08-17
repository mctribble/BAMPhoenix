
app.controller('navController', function($rootScope, SessionService, $scope, $location, $http) {


	$rootScope.user = SessionService.get("currentUser");
	console.log($scope.user);
	$rootScope.userRole = SessionService.get("userRole");

	$scope.$on('routeChangeStart', function(next, current) {
		console.log('in the routeChangeStart');
		SessionService.set("gotSubtopics", false);
		var somePath = $location.path();

		if (SessionService.get("currentUser").role < 2) {
			console.log('if the curret user is less than 1' + SessionService.get("currentUser").role);
			if (somePath == "/batchesAll" || somePath == "/batchesFuture"
					|| somePath == "/batchesPast" || somePath == "/register"
					|| somePath == "/associates" || somePath == "/editBatch") {
				$location.path('/home');
			}
		}
	});
	
	
	$scope.redirect = function (){
		SessionService.set("gotSubtopics", false);
		if(!SessionService.get("currentUser")){
			$location.path('/');
		}
	}
	
	$scope.redirect();
	
	$scope.hideNav = function (){
		
        SessionService.remove();
    }
});