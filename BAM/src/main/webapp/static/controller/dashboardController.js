/**
 * 
 */
app.controller('dashboardController', function($http, $scope, SessionService, $rootScope) {
	$rootScope.user;
	//set batchId with the id of the currentBatch if it exists else use the trainerBatch
	var batchId;
	$scope.currentBatchName;
	if(SessionService.get("currentBatch"))
	{
		batchId = SessionService.get("currentBatch").id;
		SessionService.set("currentBatchName",SessionService.get("currentBatch").name)
	}else
	{
		batchId = SessionService.get("trainerBatch").id; //if currentBatch is not set use the trainerBatch's id
		SessionService.set("currentBatchName",SessionService.get("trainerBatch").name);
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
				for(var i = 0; i < $scope.batch.usersInBatch.length; i++) {
					$scope.batch.users = $scope.batch.usersInBatch[i];
				}
				
			})
		}
	}
	
	$scope.trainerHasBatch = SessionService.get("trainerBatch");
	$scope.userHasBatch = SessionService.get("currentBatch");
	
	//This populates the day progress bar
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
             		
             		//This populates the subtopic progress bar
            		$scope.completed = 0;
            		for(var i = 0; i < $scope.subTopics.length ; i++){
            				
            			if($scope.trainerHasBatch){
            			var status= response.data[i].status.id;
            				
            				if (status == 2 || status == 3){
            					$scope.completed += 1;
            				};
            			}else if($scope.userHasBatch){
            				var status= response.data[i].status.id;
                				
            					if (status == 2 || status == 3){
            						$scope.completed += 1;
            					};
            				}
            			}
            		$scope.subPercent = Math.round(($scope.completed * 100) / $scope.totalSub) + "%";
             		
            		
            		
            		$scope.topicArray = []; 

             		for(var i = 0; i < $scope.subTopics.length ; i++) {
                		var status= response.data[i].status.id;
                 		var title = response.data[i].subtopicName.name
                 		
                 		
                     		if(status == 4){
                     			if(response.data[i].subtopicName.topic){
                        			var topicName = response.data[i].subtopicName.topic.name;
                     				var topicNameExists = false;
                     				
                     				for(k in $scope.topicArray){
                     					if($scope.topicArray[k] == topicName){
                     						topicNameExists = true;
                     					}
                     				}
                     				if(!topicNameExists){
                     					$scope.topicArray.push(topicName);
                     				}
                     			
                     			}else if(!topicName){
                     				var topicNameExists = false;
                                 	
                                 	for(k in $scope.topicArray){
                     					if($scope.topicArray[k] == "Other"){
                     						topicNameExists = true;
                     					}
                     				}
                     				if(!topicNameExists){
                     					$scope.topicArray.push("Other");
                     				}
                     			}
                     		}
             		}
             		
             		for(var j = 0; j < $scope.topicArray.length ; j++){
             			var docElement = document.getElementById("mainList");
             			var createLI = document.createElement("LI");
             			var createUL = document.createElement("UL");
             			var textNode = document.createTextNode($scope.topicArray[j]);
             			
             			createLI.className += "listTitle";
             			createUL.id = $scope.topicArray[j];
             			
             			docElement.appendChild(createLI);
             			createLI.appendChild(textNode);
             			createLI.appendChild(createUL);
             		}
             		
             		//Append subtopics to related topics
             		for(var k = 0; k < $scope.subTopics.length ; k++) {
                		var status= response.data[k].status.id;
                 		var title = response.data[k].subtopicName.name
                 		
                     		if(status == 4){
                     			if(response.data[k].subtopicName.topic){
                     				var topicName = response.data[k].subtopicName.topic.name;
                     				
                     					for(var l = 0; l < $scope.topicArray.length ; l++){
                     						if(topicName == $scope.topicArray[l]){
                     	     					var docElement = document.getElementById(topicName);
                     	     					var createLI = document.createElement("LI");
                     	                 		var textNode = document.createTextNode(title);
                     	                 		
                     	                 		createLI.className += "listItem";
                     	     					
                     	                 		createLI.appendChild(textNode);
                     	     					docElement.appendChild(createLI);
                     	     					
                     	     					$scope.count += 1;
                     						}
                     					}
                     					
                     			}else if(!response.data[k].subtopicName.topic){
                 	     			var docElement = document.getElementById("Other");
                 	     			var createLI = document.createElement("LI");
                 	     			var textNode = document.createTextNode(title);
                 	                 		
                 	     			createLI.className += "listItem";
                 	     					
                 	     			createLI.appendChild(textNode);
                 	     			docElement.appendChild(createLI);
                 	     					
                 	     			$scope.count += 1;
                     			}
                     		}
             		}
             	}).finally(function() {
            		// Turn off loading indicator
            		$scope.loading = false;
            	});}
                     	
});
