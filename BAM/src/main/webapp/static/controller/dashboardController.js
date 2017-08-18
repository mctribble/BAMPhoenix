/**
 * 
 */
app.controller('dashboardController', function($http, $scope, $rootScope) {
	$scope.user;
	//set batchId with the id of the currentBatch if it exists else use the trainerBatch
	var batchId;
	$scope.currentBatchName;
	if($scope.currentBatch)
	{
		batchId = $scope.currentBatch.id;
		$scope.currentBatchName = $scope.currentBatch.name;
	}else
	{
		batchId = $scope.trainerBatch.id; //if currentBatch is not set use the trainerBatch's id
		$scope.currentBatchName = $scope.trainerBatch.name;
	}
	
	$scope.getData = function() {
			
		if($scope.user){
			var currentDate = new Date().getTime();
			
			if($scope.trainerBatch.endDate > currentDate){
				$scope.message = $scope.trainerBatch.name;
				$scope.currentBatchStart1 = $scope.trainerBatch.startDate;
				$scope.currentBatchEnd1 = $scope.trainerBatch.endDate;
				
				//Count difference between start date and currentDate
				function weeksBetween(d1, d2) {
				    return Math.round((d2 - d1) / (7 * 24 * 60 * 60 * 1000));
				}
				
				var dif = weeksBetween($scope.currentBatchStart1, currentDate);
				//Current week number
				$scope.weekNum = dif;
				
		}else{
			$scope.message = 'You have no current batches';
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
				});
			}
			$http({
				url: "rest/api/v1/Users/InBatch",
				method: 'GET',
				params: {
				batchId: batchId
				}
			}).then(function(response) {
				$scope.batch.usersInBatch = response.data 
				console.log($scope.batch.usersInBatch);
				for(var i = 0; i < $scope.batch.usersInBatch.length; i++) {
					$scope.batch.users = $scope.batch.usersInBatch[i];
				    console.log($scope.batch.users.fName);
				    
				}
				
			})
		}
	}
	
		
	
	//This populates the progress bar
		if ($scope.trainerBatch){
			
			var currentDate = new Date().getTime();
			var startDate = $scope.trainerBatch.startDate;
			var endDate = $scope.trainerBatch.endDate;
			
			var daysComplete = currentDate - startDate;
			var totalDays = endDate - startDate;
			
			$scope.percent = Math.round((daysComplete * 100) / totalDays) + "%";
			
		} else if ($scope.user.batch){
			
			var currentDate = new Date().getTime();
			var startDate = $scope.user.batch.startDate;
			var endDate = $scope.user.batch.endDate;
			
			var daysComplete = currentDate - startDate;
			var totalDays = endDate - startDate;
			
			$scope.percent = Math.round((daysComplete * 100) / totalDays) + "%";
		}
		
		
		
		
		
		$scope.returnMissed = function(){

			 	var url;
	            if($scope.user.role == 1){
	            	url ="rest/api/v1/Calendar/Subtopics?batchId="+$scope.user.batch.id;
	            }else if($scope.user.role == 2 && $scope.trainerBatch){
	             	url ="rest/api/v1/Calendar/Subtopics?batchId="+$scope.trainerBatch.id;
	            }else if (($scope.user.role == 3) && $scope.currentBatch) {
	            	url ="rest/api/v1/Calendar/Subtopics?batchId="+$scope.currentBatch.id;
	            }else{
	            }
	            
	            $scope.loading = true;
	            
         		$http({
             		method : "GET",
             		url : url
             	}).then(function(response) {
             		$scope.subTopics = response.data;
             		$scope.count = 0;
             		$scope.totalSub = $scope.subTopics.length;
             		
             		for(var i = 0; i < $scope.subTopics.length ; i++) {
                     		var status= response.data[i].status.id;
                     		var title = response.data[i].subtopicName.name
                     		
                     		
                     		if(status == 3){
                     			
                     			if(response.data[i].subtopicName.topic){
                     				var topicName = response.data[i].subtopicName.topic.name;
                     				
                     				if(topicName == "Java"){
                     					var docElement = document.getElementById("java");
                     					var createLI = document.createElement("LI");
                                 		var textNode = document.createTextNode(title);
                                 		
                                 		createLI.className += "listItem";
                     					
                                 		createLI.appendChild(textNode);
                     					docElement.appendChild(createLI);
                     					
                     					$scope.count += 1;
                     					
                     					
                     				}else if(topicName == "Web Services"){
                     					var docElement = document.getElementById("webServices");
                     					var createLI = document.createElement("LI")
                                 		var textNode = document.createTextNode(title);
                     					
                                 		createLI.className += "listItem";
                     					
                                 		createLI.appendChild(textNode);
                     					docElement.appendChild(createLI);
                     					$scope.count += 1;
                     					
                     				}else if(topicName == "SQL/JDBC"){
                     					var docElement = document.getElementById("sqlJDBC");
                     					var createLI = document.createElement("LI")
                                 		var textNode = document.createTextNode(title);
                     					
                                 		createLI.className += "listItem";
                     					
                                 		createLI.appendChild(textNode);
                     					docElement.appendChild(createLI);
                     					$scope.count += 1;
                     					
                     				}else if(topicName == "HTML/CSS/Bootstrap"){
                     					var docElement = document.getElementById("html");
                     					var createLI = document.createElement("LI")
                                 		var textNode = document.createTextNode(title);
                     					
                                 		createLI.className += "listItem";
                     					
                                 		createLI.appendChild(textNode);
                     					docElement.appendChild(createLI);
                     					$scope.count += 1;
                     					
                     				}else if(topicName == "Servlets/JSPs"){
                     					var docElement = document.getElementById("servlets");
                     					var createLI = document.createElement("LI")
                                 		var textNode = document.createTextNode(title);
                     					
                                 		createLI.className += "listItem";
                     					
                                 		createLI.appendChild(textNode);
                     					docElement.appendChild(createLI);
                     					$scope.count += 1;
                     					
                     				}else if(topicName == "Javascript/jQuery/AJAX"){
                     					var docElement = document.getElementById("js");
                     					var createLI = document.createElement("LI")
                                 		var textNode = document.createTextNode(title);
                     					
                                 		createLI.className += "listItem";
                     					
                                 		createLI.appendChild(textNode);
                     					docElement.appendChild(createLI);
                     					$scope.count += 1;
                     					
                     				}else if(topicName == "DevOps"){
                     					var docElement = document.getElementById("devops");
                     					var createLI = document.createElement("LI");                                 		var textNode = document.createTextNode(title);

                                 		createLI.className += "listItem";
                     					
                                 		createLI.appendChild(textNode);
                     					docElement.appendChild(createLI);
                     					$scope.count += 1;
                     					
                     				}else if(topicName == "Hibernate"){
                     					var docElement = document.getElementById("hibernate");
                     					var createLI = document.createElement("LI")
                     					var textNode = document.createTextNode(title);
                     					
                                 		createLI.className += "listItem";
                     					
                                 		createLI.appendChild(textNode);
                     					docElement.appendChild(createLI);
                     					$scope.count += 1;
                     					
                     				}else if(topicName == "Spring"){
                     					var docElement = document.getElementById("spring");
                     					var createLI = document.createElement("LI")
                                 		var textNode = document.createTextNode(title);
                     					
                                 		createLI.className += "listItem";
                     					
                                 		createLI.appendChild(textNode);
                     					docElement.appendChild(createLI);
                     					$scope.count += 1;
                     					
                     				}else if(topicName == "Angular"){
                     					var docElement = document.getElementById("angular");
                     					var createLI = document.createElement("LI")
                     					var textNode = document.createTextNode(title);
                     					
                                 		createLI.className += "listItem";
                     					
                                 		createLI.appendChild(textNode);
                     					docElement.appendChild(createLI);
                     					$scope.count += 1;
                     					
                     				}
                     			}else if(!topicName){
                     				var docElement = document.getElementById("other");
                     				var createLI = document.createElement("LI")
                                 	var textNode = document.createTextNode(title);
                     				
                                 	createLI.className += "listItem";
                     					
                                 	createLI.appendChild(textNode);
                 					docElement.appendChild(createLI);
                                 	$scope.count += 1;
                     				
                     			}

                     		}
                     	}
             		}
             	).finally(function() {
            		// Turn off loading indicator
            		$scope.loading = false;
            	});}
                     	
});
