app.controller('navCtl', function($rootScope, $scope, $location, $http) {

	$scope.$on('$routeChangeStart', function(next, current) {
		$rootScope.gotSubtopics = false;
		var somePath = $location.path();

		if ($rootScope.user.role < 2) {
			if (somePath == "/batchesAll" || somePath == "/batchesFuture"
					|| somePath == "/batchesPast" || somePath == "/register"
					|| somePath == "/associates" || somePath == "/editBatch") {
				$location.path('/home');
			}
		}
	});
	
	$scope.redirect = function (){
		$rootScope.gotSubtopics = false;
		if(!$rootScope.user){
			$location.path('/');
		}
	}
	
	$scope.redirect();
});