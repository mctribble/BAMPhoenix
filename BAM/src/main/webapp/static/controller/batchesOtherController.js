app.controller('batchesPastCtr', function($scope, $location, $http) {
	$scope.batches;

	$scope.getBatchesAll = function() {

		$http({
			url : 'Batches/All.do',
			method : 'GET'
		}).then(function success(response) {
			//$location.path('/batchesPast');
		}, function error(response) {
			//$location.path('/batchesPast');
		});
	}

	$scope.getBatchesAll();
});

app.controller('batchesFutureCtr', function($scope, $location, $http) {
	$scope.batches;

	$scope.getBatchesAll = function() {

		$http({
			url : 'Batches/All.do',
			method : 'GET'
		}).then(function success(response) {
			//$location.path('/batchesFuture');
		}, function error(response) {
			//$location.path('/batchesFuture');
		});
	}

	$scope.getBatchesAll();
});
