/**
 * 
 */
app.controller('dashboardController', function($http, $scope, SessionService) {
	$scope.user;
	//set batchId with the id of the currentBatch if it exists else use the trainerBatch
	var batchId;
	if(SessionService.get("currentBatch"))
	{
		batchId = SessionService.get("currentBatch").id;
		SessionService.set("currentBatchName", SessionService.get("currentBatch").name);
		
	}else
	{
		batchId = SessionService.get("trainerBatch").id; //if currentBatch is not set use the trainerBatch's id
		SessionService.set("currentBatchName", SessionService.get("trainerBatch").name);
		console.log(batchId);
	}
	
	
	$scope.getData = function() {
			
		if($rootScope.user){
			var currentDate = new Date().getTime();
			
			if(SessionService.get("trainerBatch").endDate > currentDate){
				$scope.message = SessionService.get("trainerBatch").name;
				$scope.currentBatchStart1 = SessionService.get("trainerBatch").startDate;
				$scope.currentBatchEnd1 = SessionService.get("trainerBatch").endDate;
				
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
			$http({
				url: "rest/api/v1/Users/InBatch",
				method: 'GET',
				params: {
				batchId: batchId
				}
			}).then(function(response){
				console.log(response.data);
				$scope.usersInBatch = response.data
				for(var i = 0; i < $scope.usersInBatch.length; i++) {
					$scope.batchUsers = $scope.usersInBatch[i];
				    console.log($scope.batchUsers.fName);
				}
			})
		}
	}
	
	$scope.trainerHasBatch = SessionService.get("trainerBatch");
	$scope.userHasBatch = SessionService.get("currentBatch");
	
	//This populates the progress bar
		if ($scope.trainerHasBatch){
			
			var currentDate = new Date().getTime();
			var startDate = SessionService.get("trainerBatch").startDate;
			var endDate = SessionService.get("trainerBatch").endDate;
			
			var daysComplete = currentDate - startDate;
			var totalDays = endDate - startDate;
			
			$scope.percent = Math.round((daysComplete * 100) / totalDays) + "%";
			
		} else if ($scope.userHasBatch){
			
			var currentDate = new Date().getTime();
			var startDate = SessionService.get("currentBatch").startDate;
			var endDate = SessionService.get("currentBatch").endDate;
			
			var daysComplete = currentDate - startDate;
			var totalDays = endDate - startDate;
			
			$scope.percent = Math.round((daysComplete * 100) / totalDays) + "%";
		}
		
		
		
		
		
		$scope.returnMissed = function(){

			 	var url;
			 	 if(SessionService.get("currentUser").role == 1){
		            	url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("currentUser").batch.id;
		            }else if ((SessionService.get("currentUser").role == 3 || SessionService.get("currentUser").role == 2 ) && SessionService.get("currentBatch")) {
		            	url ="rest/api/v1/Calendar/Subtopics?batchId="+SessionService.get("currentBatch").id;
		            }else if(SessionService.get("currentUser").role == 2 && SessionService.get("trainerBatch")){
		             	url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("trainerBatch").id;
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
