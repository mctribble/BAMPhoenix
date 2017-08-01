
app.controller("editBatchController",function($rootScope, $scope, $location, $http){
	console.log("Edit Batch Controller");
	//retrieve batch info from server
	var batchId; //set the batchId
	//$scope.currentBatchName;
	if($rootScope.currentBatch)
	{
		//$scope.currentBatchName= true;
		//$scope.currentBatchName = $rootScope.currentBatch.name;
		batchId = $rootScope.currentBatch.id;
	}else
	{
		//$scope.currentBatchName= true;
		//$scope.currentBatchName = $rootScope.trainerBatch.name;
		batchId = $rootScope.trainerBatch.id;
	}
	
	if(batchId) //Check if currentBatch is set before using it.
	{
		//get the batch
		$http({
			url: "Batches/ById.do",
			method: "GET",
			params:{
				/*
				 * Not yet sure how we pass the batchId to this page
				 * Putting it in the rootScope is an easy option.
				 */
				batchId: batchId
			}
		}).then(function(response){
			 //for now assume that resposneObj is the batch object
			 $scope.batch = response.data
			 $scope.batch.startDate = new Date($scope.batch.startDate);
			 $scope.batch.endDate = new Date($scope.batch.endDate);
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
				batchId: batchId
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
	}
	
	
	//	batch info
	//	All users in batch
	//	Subtopics
	//	All users (to add existing users)
	
	
	
	
	
	
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
		$http({
			url: "Users/Add.do",
			method: "POST",
			params: {
				userId: id,
				batchId: batchId
			}
		}).then(function success(){
			//remove associate from out of batch
			//add associate to in batch
			var recIndex;
			jQuery.each($scope.availUsers, function(index, value){
				
				if(value.userId == id){
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
		});
		
		
		
		
	}
	$scope.remAssociate = function(id){
		//remove associate from a batch
		console.log(id);
		//tell the server
		$http({
			url: "Users/Remove.do",
			method: "POST",
			params: {
				userId: id
			}
		}).then(function success(){
			//remove associate from in batch
			//add associate from out batch
			var recIndex;
			jQuery.each($scope.batch.usersInBatch, function(index,value)
			{
				if(value.userId == id)
				{
					recIndex = index;
					return false;
				}
			});
			$scope.availUsers.push($scope.batch.usersInBatch[recIndex]);
			$scope.batch.usersInBatch.splice(recIndex, 1);
			
		}, function error(){
			$scope.message = true;
			$scope.msg = "Failed to Remove Associate";
		})
		//On success message
		
		
	}
	$scope.dropAssociate = function(id){
		//drop associate
		console.log(id);
		//tell the server
		$http({
			url: "Users/Drop.do",
			userId: id
		}).then(function success(){
			//remove user from usersInBatch
			var recIndex;
			jQuery.each($scope.batch.usersInBatch, function(index,value){
				if(value.userId == id)
				{
					recIndex = index;
					return false;
				}
			});
			$scope.batch.usersInBatch.splice(recIndex,1);
		}, function error(){
			$scope.message = true;
			$scope.msg = "Faild to Drop Associate";
		})
		//On success message
		success();
		
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