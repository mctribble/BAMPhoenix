var ascContTestJSON = '[{"fname":"bobbert","mname":"bobber","lname":"bobson","email":"bob@bob.bob","batchId":1,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"},{"fname":"bobbert2","mname":"bobber2","lname":"bobson2","email":"bob2@bob.bob","batchId":1,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"},{"fname":"bobbert3","mname":"bobbe3r","lname":"bobson3","email":"bob3@bob.bob","batchId":1,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"},{"fname":"bobbert4","mname":"bobber4","lname":"bobson4","email":"bob4@bob.bob","batchId":1,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"}]';
app.controller("associatesController", function($scope, $http){
	
	console.log("Associates Controller");
	$scope.associateList = JSON.parse(ascContTestJSON);
	$http({
		url: "Users/All.do",
		method: "GET"
	}).then(function(response){
		$scope.associateList = response.data;
	}, function(response){
		$scope.message = true;
		$scope.msg = "Failed to get users";
	})
	//parse json string and set it to
	
	/*
	 * {
	 * 	fname,
	 * 	mname,
	 * 	lname,
	 * 	email,
	 * 	batchId,
	 * 	mPhone,
	 * 	sPhone,
	 * 	skype
	 * }
	 */
});