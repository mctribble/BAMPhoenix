
app.controller('navController', function($rootScope, $window, $scope, $location, $http) {


	$scope.$on('$routeChangeStart', function(next, current) {
		$rootScope.gotSubtopics = false;
		var somePath = $location.path();

		//if ($rootScope.user.role < 2) {
			if (somePath == "/batchesAll" || somePath == "/batchesFuture"
					|| somePath == "/batchesPast" || somePath == "/register"
					|| somePath == "/associates" || somePath == "/editBatch") {
				$location.path('/home');
			}
		//}
	});
	
	$scope.hideNav = function (){
		delete $rootScope.user;
	}
	
	
	$scope.redirect = function (){
		$rootScope.gotSubtopics = false;
		if(!angular.fromJson($window.sessionStorage.getItem('sessionUser'))){
			$location.path('/');
		}
	}
	
	$scope.redirect();
	
	$scope.hideNav = function (){
        delete $rootScope.user;
    }
});