var testJSONresponse='{"batch":{"batchId":1,"batchName":"Fake batch","startDate":"07/05/2017","endDate":"07/26/2017","trainer":{"firstName":"Ma Name","lastName":"Ma Last name"},"type":"fake type","usersInBatch":[{"id":1,"fname":"bobbert","mname":"bobber","lname":"bobson","email":"bob@bob.bob","batchId":1,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"},{"id":2,"fname":"bobbert2","mname":"bobber2","lname":"bobson2","email":"bob2@bob.bob","batchId":1,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"},{"id":3,"fname":"bobbert3","mname":"bobbe3r","lname":"bobson3","email":"bob3@bob.bob","batchId":1,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"}]},"availUsers":[{"id":4,"fname":"bobbert4","mname":"bobber4","lname":"bobson4","email":"bob4@bob.bob","batchId":2,"mPhone":"555-555-5555","sPhone":"1234567890","skype":"bobbobbob"}]}';
app.controller("editBatchController",function($rootScope, $scope, $location, $http){
	console.log("Edit Batch Controller");
	//retrieve batch info from server
	var responseObj = JSON.parse(testJSONresponse);
	$scope.batch = responseObj.batch;
	$scope.availUsers = responseObj.availUsers;
	//get the batch
	$http({
		url: "Batches/ById.do",
		method: "GET",
		params:{
			/*
			 * Not yet sure how we pass the batchId to this page
			 * Putting it in the rootScope is an easy option.
			 */
			batchId: $rootScope.selectedBatchId
		}
	}).then(function(response){
		 //for now assume that resposneObj is the batch object
		 $scope.batch = response.data
	},function(response) {
		$scope.message = true;
		$scope.msg = 'Failed to retrieve batch';
		console.log($scope.msg);		
	});
	//get users in batch
	$http({
		url: "Users/InBatch.do",
		method: 'GET',
		params: {
			batchId: $rootScope.selectedBatchId
		}
	}).then(function(response) {
		//for now assume that the response is the array of users
		$scope.batch.usersInBatch = response.data
	},function (response) {
		$scope.message = true;
		$scope.msg = 'Failed to retrieve users in batch';
		console.log($scope.msg);
	});
	//get users without a batch
	$http({
		url: "Users/NotInABatch.do",
		method: 'GET'
	}).then(function (response) {
		//for now assume that the response is the array of users
		$scope.availUsers = response.data
	},function (response) {
		$scope.message = true;
		$scope.msg = 'Failed to retrieve users without a batch';
		console.log($scope.msg);
	});
	
	//	batch info
	//	All users in batch
	//	Subtopics
	//	All users (to add existing users)
	
	
	$scope.batch.startDate = new Date($scope.batch.startDate);
	$scope.batch.endDate = new Date($scope.batch.endDate);
	
	
	
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
	$scope.addAssociate = function(id){
		//add associate to a batch
		console.log("id : " + id);
		//tell the server
		/*$http({
			url: "User/Add.do",
			method: "POST",
			data: {}
		}).then(function success(){
			//remove associate from out of batch
			//add associate to in batch
			var recIndex;
			jQuery.each($scope.availUsers, function(index, value){
				
				if(value.id == id){
					recIndex = index;
					return false; //breaks form the jQuery each method
				}
			});
			//add element to usersInBatch
			$scope.batch.usersInBatch.push($scope.availUsers[recIndex]);
			//remove element from availUsers
			$scope.availUsers.splice(recIndex,1); 
			$scope.message = true;
			$scope.msg = "Associate added successfully";
		},function error(){
			$scope.message = true;
			$scope.msg = "Failed to Add associate";
		});*/
		
		
		
		
	}
	$scope.remAssociate = function(id){
		//remove associate from a batch
		console.log(id);
		//tell the server
		
		//On success message
		success();
		function success(){
			//remove associate from in batch
			//add associate from out batch
			var recIndex;
			jQuery.each($scope.batch.usersInBatch, function(index,value)
			{
				if(value.id == id)
				{
					recIndex = index;
					return false;
				}
			});
			$scope.availUsers.push($scope.batch.usersInBatch[recIndex]);
			$scope.batch.usersInBatch.splice(recIndex, 1);
			
		}
	}
	$scope.dropAssociate = function(id){
		//drop associate
		console.log(id);
		//tell the server
		
		//On success message
		success();
		function success(){
			//remove user from usersInBatch
			var recIndex;
			jQuery.each($scope.batch.usersInBatch, function(index,value){
				if(value.id == id)
				{
					recIndex = index;
					return false;
				}
			});
			$scope.batch.usersInBatch.splice(recIndex,1);
		}
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