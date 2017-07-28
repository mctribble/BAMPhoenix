var testJSONresponse='{"batch":{"batchId":1,"name":"Fake batch","startDate":"07/05/2017","endDate":"07/26/2017","trainer":{"firstName":"Ma Name","lastName":"Ma Last name"},"type":"fake type"},"usersArr":[{"fname":"bobbert","mname":"bobber","lname":"bobson","email":"bob@bob.bob","batchId":1,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"},{"fname":"bobbert2","mname":"bobber2","lname":"bobson2","email":"bob2@bob.bob","batchId":1,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"},{"fname":"bobbert3","mname":"bobbe3r","lname":"bobson3","email":"bob3@bob.bob","batchId":1,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"},{"fname":"bobbert4","mname":"bobber4","lname":"bobson4","email":"bob4@bob.bob","batchId":2,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"}]}';
app.controller("editBatchController",function($scope){
	console.log("Edit Batch Controller");
	//retrieve batch info from server
	//	batch info
	//	All users in batch
	//	Subtopics
	//	All users (to add existing users)
	var response = JSON.parse(testJSONresponse);
	var batch = response.batch;
	$scope.batch = response.batch;
	$scope.batch.startDate = new Date($scope.batch.startDate);
	$scope.batch.endDate = new Date($scope.batch.endDate);
	//split the users into two different arrays (in batch and not in batch)
	
	
	//https://codepen.io/jasoncluck/pen/iDcbh
	$scope.tab = 1;
	
	$scope.setTab = function(newTab){
		$scope.tab = newTab;
	};
	$scope.isSet = function(tabNum){
		return $scope.tab === tabNum;
	}
	jQuery("#users").toggle();
	
	
	//declare functions
	$scope.addRemAssociate = function(){
		console.log("toggle users div");
		jQuery("#users").toggle();
	}
	$scope.addAssociate = function(){
		//add associate to a batch
		//tell the server
	}
	$scope.remAssociate = function(){
		//remove associate from a batch
		//tell the server
	}
	
	$scope.addRemSubtopic = function(){
		//show subtopic list in batch
	}
	$scope.addSubtopic = function(){
		//add subtopic
		//tell the server
	}
	$scope.remSubtopic = function(){
		//remove subtopic
		//tell the server
	}
});