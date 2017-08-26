
app.controller("batchEditController",function($rootScope, $scope, SessionService, $location, $http){
	
	$scope.updateDisplay = false;	
	
	//set batchId with the id of the currentBatch if it exists else use the trainerBatch
	var batchId;
	if(SessionService.get("currentBatch"))
	{
		batchId = SessionService.get("currentBatch").id;
		SessionService.set("currentBatchName", SessionService.get("currentBatch").name);
	}else
	{
		batchId = $rootScope.changedBatchId; //if currentBatch is not set use the trainerBatch's id
		SessionService.set("currentBatchName", SessionService.get("trainerBatch").name);
	}
	
	if(batchId) //Check if currentBatch is set before using it.
	{
		//get the batch from the server by the id.
		$http({
			url: "rest/api/v1/Batches/ById",
			method: "GET",
			params:{
				batchId: batchId
			}
		}).then(function(response){
			 $scope.batch = response.data //reponse.data is a javascript object (automatically parsed from the JSON from the server)
			 $scope.batch.startDate = new Date($scope.batch.startDate); //get the JavaScript date object from the data sent from the server
			 $scope.batch.endDate = new Date($scope.batch.endDate);
		},function(response) {
			$scope.message = true;
			$scope.msg = 'Failed to retrieve batch';
		});
		//get users in the batch by the batchId
		$http({
			url: "rest/api/v1/Users/InBatch",
			method: 'GET',
			params: {
				batchId: batchId
			}
		}).then(function(response) {
			$scope.batch.usersInBatch = response.data //the response.data is a javascript array (automatically pasred from the JSON from the server)
		},function (response) {
			$scope.message = true;
			$scope.msg = 'Failed to retrieve users in batch';
		});
		//get users who are not in a batch
		$http({
			url: "rest/api/v1/Users/NotInABatch",
			method: 'GET'
		}).then(function (response) {
			$scope.availUsers = response.data; //the response.data is a javascript array (automatically parsed from the JSON from the server)
		},function (response) {
			$scope.message = true;
			$scope.msg = 'Failed to retrieve users without a batch';
		});
	}	
	
	$scope.updateBatch = function(){
		$http({
			url: "rest/api/v1/Batches/UpdateBatch",
			method: 'POST',
			headers: {
		        'Content-Type': 'application/json', 
		        'Accept': 'application/json' 
		    },
			data: $scope.batch
		}).then (function success(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Update Successful';
			$scope.alertClass = 'alert alert-success';
			SessionService.set("currentBatch", $scope.batch);
		}, function error(response){
			$scope.updateDisplay = true;
			$scope.updateMsg = 'Update Failed';
			$scope.alertClass = 'alert alert-danger';
		});
	}
	
	//get all batch types and populate select box
	$http({
		url: "rest/api/v1/Batches/BatchTypes",
		method: "GET",
	}).then(function(response){
		$scope.batchTypes = response.data;
	})
	
	
	/* 
	 * Tabbing Functionality created from example: https://codepen.io/jasoncluck/pen/iDcbh
	 */
	
	//This sets the default tab to be active.
	$scope.tab = 1;
	
	//Each tab should have an ng-click directive which calls this function with the number of their tab. 
	//ie ng-click="setTab(2)"
	$scope.setTab = function(newTab){
		$scope.tab = newTab;
	};
	
	//This function is used to tell if the specified tab is the active one or not
	//Use this on the tab header with ng-class="{active:isSet(x)}"
	//Or for the content of the tab can be hidden or shown with ng-show="isSet(x)"
	$scope.isSet = function(tabNum){
		return $scope.tab === tabNum;
	}
	//turns off the entire users container by default. It is meant to be enabled by a button click
	jQuery("#users").toggle();
	
	
	//This function is meant to toggle the users container to display and hide it.
	$scope.addRemAssociate = function(){
		jQuery("#users").toggle();
	}
	//This function adds a user to the batch
	$scope.addAssociate = function(id){
		
		//tell the server
		$http({
			url: "rest/api/v1/Users/Add",
			method: "POST",
			params: {
				userId: id,
				batchId: batchId
			}
		}).then(function success(){
			//Find which spot in the array the selected user is in
			var recIndex;
			//iterate through the array
			jQuery.each($scope.availUsers, function(index, value){
				
				if(value.userId == id){ //check if we are on the userId we want
					recIndex = index; //store the current index
					return false; //breaks form the jQuery each method
				}
			});
			//add element to usersInBatch
			$scope.batch.usersInBatch.push($scope.availUsers[recIndex]);
			//remove element from availUsers
			$scope.availUsers.splice(recIndex,1); 
			//Set success method
			$scope.message = true;
			$scope.msg = "Associate added successfully";
		},function error(){
			$scope.message = true;
			$scope.msg = "Failed to Add associate";
		});
		
		
		
		
	}
	//This function removes a user from a batch
	$scope.remAssociate = function(id){
		
		//tell the server
		$http({
			url: "rest/api/v1/Users/Remove",
			method: "POST",
			params: {
				userId: id
			}
		}).then(function success(){
			//find the spot in the array which has the user we want
			var recIndex;
			//iterate through the array of users in the batch
			jQuery.each($scope.batch.usersInBatch, function(index,value)
			{
				// check if we are on the user we want
				if(value.userId == id)
				{
					recIndex = index; //store the index
					return false; //break from jQuery each
				}
			});
			//add the user to the not in batch array
			$scope.availUsers.push($scope.batch.usersInBatch[recIndex]);
			//remove the user from the in batch array
			$scope.batch.usersInBatch.splice(recIndex, 1);
			
		}, function error(){
			$scope.message = true;
			$scope.msg = "Failed to Remove Associate";
		})
		//On success message
		
		
	}
	//this function is used to drop an associate
	$scope.dropAssociate = function(id){
		//tell the server
		$http({
			url: "rest/api/v1/Users/Drop",
			method: "POST",
			params: {
				userId: id
			}
		}).then(function success(){
			/*
			 * Find user matching the id
			 * remove the user from the batch array
			 */
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
			$scope.msg = "Failed to Drop Associate";
		})
		
		
	}

});
