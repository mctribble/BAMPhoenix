/**
 * @author Sarah Kummerfeldt 
 * @author Kosiba Oshodi-Glover
 */
app.controller('dashboardController', function($http, $scope, SessionService) {
	
	window.onload = function() {
	    if(!window.location.hash) {
	        window.location = window.location + '#loaded';
	        window.location.reload();
	    }
	}

	$(".navbar").show();
	$scope.user;
	var batchId;
	console.log(SessionService.get("currentUser").userId);
	/**
	 * Sets the batch id to retrieve batch info for current user or trainer.
	 */
	if(SessionService.get("currentUser").batch)
	{
		batchId = SessionService.get("currentUser").batch.id;
		SessionService.set("currentBatchName", SessionService.get("currentUser").batch.name);
		
	}else if(SessionService.get("trainerBatch"))
	{
		batchId = SessionService.get("trainerBatch").id;
		SessionService.set("currentBatchName", SessionService.get("trainerBatch").name);
		
	}else
	{
		batchId = 3;
	} 
	
	$scope.noBatch = SessionService.get("currentUser").userId;
	$scope.trainerHasBatch = SessionService.get("trainerBatch");
	$scope.userHasBatch = SessionService.get("currentUser").batch;
	
	/**
	 * function that will return dates, list of associates, status, and name of current batch
	 */
	$scope.getData = function() {
			
		if($scope.trainerHasBatch){
			var currentDate = new Date().getTime();
			
			if(SessionService.get("trainerBatch").endDate > currentDate){
				$scope.message = SessionService.get("trainerBatch").name;
				$scope.currentBatchStart1 = SessionService.get("trainerBatch").startDate;
				$scope.currentBatchEnd1 = SessionService.get("trainerBatch").endDate;
				
				function weeksBetween(d1, d2) {
				    return Math.round((d2 - d1) / (7 * 24 * 60 * 60 * 1000));
				}
				
				var dif = weeksBetween($scope.currentBatchStart1, currentDate);
				$scope.weekNum = dif;
				
		}else{
			$scope.message = 'You have no current batches';
			$scope.currentBatchStart1 = 'N/A';
			$scope.currentBatchEnd1 = 'N/A';
		}			
			$http({
				url: "rest/api/v1/Users/InBatch",
				method: 'GET',
				params: {
				batchId: batchId
				}
			}).then(function(response){
				$scope.usersInBatch = response.data
				
				$scope.listNames = [];
			    		firstNames= [];
						lastNames= [];
			    
				
				for(var i = 0; i < $scope.usersInBatch.length; i++) {
					$scope.batchUsers = $scope.usersInBatch[i];
				    
				    firstNames.push($scope.batchUsers.fName);
					lastNames.push($scope.batchUsers.lName);
					
					$scope.listNames[i] = {
				    		"firstName": firstNames[i],
				    		"lastName": lastNames[i]
				    };
				}
			})
		}else if($scope.userHasBatch){
			var currentDate = new Date().getTime();
			
			if(SessionService.get("currentUser").batch.endDate > currentDate){
				console.log(SessionService.get("currentUser").batch	.endDate);
				$scope.message = SessionService.get("currentUser").batch.name;
				$scope.currentBatchStart1 = SessionService.get("currentUser").batch.startDate;
				$scope.currentBatchEnd1 = SessionService.get("currentUser").batch.endDate;
				
				/**
				 * Counts the difference in weeks between the current batch start date and today's date
				 * @return current batch week number
				 */
				function weeksBetween(d1, d2) {
				    return Math.round((d2 - d1) / (7 * 24 * 60 * 60 * 1000));
				}
				
				var dif = weeksBetween($scope.currentBatchStart1, currentDate);
				$scope.weekNum = dif;
				console.log(dif);
						
			$http({
				url: "rest/api/v1/Users/InBatch",
				method: 'GET',
				params: {
				batchId: batchId
				}
			}).then(function(response){
				$scope.usersInBatch = response.data
				
				$scope.listNames = [];
			    		firstNames= [];
						lastNames= [];
			    
				
				for(var i = 0; i < $scope.usersInBatch.length; i++) {
					$scope.batchUsers = $scope.usersInBatch[i];
					
				    if(SessionService.get("currentUser").batch.endDate > currentDate){
					    firstNames.push($scope.batchUsers.fName);
						lastNames.push($scope.batchUsers.lName);
						
						$scope.listNames[i] = {
					    		"firstName": firstNames[i],
					    		"lastName": lastNames[i]
					    };
				    }else{
				    	$scope.listNames = 'N/A';
				    }
				}
			})
			}else{
				$scope.message = 'You have no current batches';
				$scope.currentBatchStart1 = 'N/A';
				$scope.currentBatchEnd1 = 'N/A';
				$scope.weekNum = 'N/A';
				$scope.listNames = 'N/A';
				$scope.percent = 'N/A';
			}
		}
	}
	

	/**
	 * Populates the day progress bar by days completed
	 */
		if ($scope.trainerHasBatch){
			
			var currentDate = new Date().getTime();
			var startDate = SessionService.get("trainerBatch").startDate;
			var endDate = SessionService.get("trainerBatch").endDate;
			
			var daysComplete = currentDate - startDate;
			var totalDays = endDate - startDate;
			
			$scope.percent = Math.round((daysComplete * 100) / totalDays) + "%";
			
		} else if ($scope.userHasBatch){
			
			var currentDate = new Date().getTime();
			var startDate = SessionService.get("currentUser").batch.startDate;
			var endDate = SessionService.get("currentUser").batch.endDate;
			
			var daysComplete = currentDate - startDate;
			var totalDays = endDate - startDate;
			
			$scope.percent = Math.round((daysComplete * 100) / totalDays) + "%";
		}
		
		
		
		
		/**
		 * Will return all of the missed subtopics out of the total subtopics
		 * Will also categorize those subtopics in their respective topic category 
		 * @return A list of missed subtopics/total subtopics
		 */
		$scope.returnMissed = function(){

			 	var url;
			 	 if(SessionService.get("currentUser").role == 1){
		            	url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("currentUser").batch.id;
		            }else if ((SessionService.get("currentUser").role == 3 || SessionService.get("currentUser").role == 2) && SessionService.get("currentUser").batch) {
		            	url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("trainerBatch").id;
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
             		
             		/**
             		 * Populates the subtopic progress bar by topics completed
             		 */
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
            		
            		/**
            		 * Populates dynamic list of missed subtopics and their respective topics
            		 */

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
             			var textNode = document.createTextNode($scope.topicArray[j] + ":");
             			
             			createLI.className += "listTitle";
             			createUL.id = $scope.topicArray[j];
             			
             			docElement.appendChild(createLI);
             			createLI.appendChild(textNode);
             			createLI.appendChild(createUL);
             		}
             		
             		
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
            		$scope.loading = false;
            	});}
                     	
});
