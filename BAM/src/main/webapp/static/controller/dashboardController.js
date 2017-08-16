/**
 * 
 */
app.controller('dashboardController', function($http, $scope) {
	
	$scope.getData = function() {
		if($rootScope.user.role == 2){
		$http.get(
				'http://assignforce.revaturelabs.com/api/v2/trainer/8'
						+ $scope.keyword).then(function(info) {

			$scope.trainerId = info.data.trainerId;
			$scope.firstName = info.data.firstName;
			$scope.lastName = info.data.lastName;

		})
		}else{
			$scope.message = 'no user info to display.';
		}
	}

});
