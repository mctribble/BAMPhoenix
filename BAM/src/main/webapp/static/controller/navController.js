app.controller('navCtl', function($rootScope, $scope, $location, $http) {
	
	function redirect()
    {
        var somePath = $location.path();
        if ($rootScope.user != null){
        	$location.path('/');
        }
        if($rootScope.user < 2){
            if (somePath == "/batchesAll"
            	|| somePath == "/batchesFuture"
            	|| somePath == "/batchesPast"){
            	$location.path('#/home');
            }
        }
    }
});