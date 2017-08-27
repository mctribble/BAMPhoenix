	/**
 * @author Sarah Kummerfeldt 
 * @author Kosiba Oshodi-Glover
 */

app.controller('dashboardController', function($http, $scope, $analytics, SessionService, $rootScope) {
	 $analytics.pageTrack('/home');
	window.onload = function() {
	    if(!window.location.hash) {
	        window.location = window.location + '#loaded';
	        window.location.reload();
	    }
	}
	
	/**
	 * rootScope used to pass a variable between controllers
	 */
	$rootScope.changedBatchId;
	
	$(".navbar").show();
	$scope.user;
	var batchId;
	/**
	 * function that will return dates, list of associates, status, and name of current batch
	 * @param getData
	 * @return weekNum returns an int 
	 */
	$scope.getData = function(start, end, idOfBatch, numberOfHits) {
		/**
		 * Sets the batch id to retrieve batch info for current user or trainer.
		 */
		if(SessionService.get("currentUser").batch){
			batchId = SessionService.get("currentUser").batch.id;
			SessionService.set("currentBatchName", SessionService.get("currentUser").batch.name);
			
		}else if(SessionService.get("trainerBatch")){
			batchId = SessionService.get("trainerBatch").id;
			SessionService.set("currentBatchName", SessionService.get("trainerBatch").name);
			
		}else{
			batchId = 3;
		} 
		
		if(idOfBatch){
			batchId = idOfBatch;
		}
		
		$scope.noBatch = SessionService.get("currentUser").userId;
		$scope.trainerHasBatch = SessionService.get("trainerBatch");
		$scope.userHasBatch = SessionService.get("currentUser").batch;
				
		if($scope.trainerHasBatch){
			var currentDate = new Date().getTime();
			
			/**
			 * Populates the day progress bar by days completed
			 */
			if(!numberOfHits){
			var startDate = SessionService.get("trainerBatch").startDate;
			var endDate = SessionService.get("trainerBatch").endDate;
			
			var daysComplete = currentDate - startDate;
			var totalDays = endDate - startDate;
			
			$scope.percent = Math.round((daysComplete * 100) / totalDays) + "%";
			}
			
			
			if(SessionService.get("trainerBatch").endDate > currentDate){
				$scope.message = SessionService.get("trainerBatch").name;
				if(start && end){
					$scope.currentBatchStart1 = start;
					$scope.currentBatchEnd1 = end;
				}else {
					$scope.currentBatchStart1 = SessionService.get("trainerBatch").startDate;
					$scope.currentBatchEnd1 = SessionService.get("trainerBatch").endDate;
				}
				
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

			    		var firstNames= [];
						var lastNames= [];
				$scope.numOfAssociates = 0;
				for(var i = 0; i < $scope.usersInBatch.length; i++) {
					$scope.batchUsers = $scope.usersInBatch[i];
									    
				    firstNames.push($scope.batchUsers.fName);
					lastNames.push($scope.batchUsers.lName);
					
					$scope.numOfAssociates += 1;
					
					$scope.listNames[i] = {
				    		"firstName": firstNames[i],
				    		"lastName": lastNames[i]
				    };
					
				}
			})
		}else if($scope.userHasBatch){
			 var currentDate2 = new Date().getTime();
			
			/**
			 * Populates the day progress bar by days completed
			 */
			 var startDate2 = SessionService.get("currentUser").batch.startDate;
			 var endDate2 = SessionService.get("currentUser").batch.endDate;
			
			 var daysCompleted = currentDate2 - startDate2;
			 var totalDaysCompleted = endDate2 - startDate2;
			
			$scope.percent = Math.round((daysCompleted * 100) / totalDaysCompleted) + "%";
			
			
			if(SessionService.get("currentUser").batch.endDate > currentDate2){
				$scope.message = SessionService.get("currentUser").batch.name;
				$scope.currentBatchStart1 = SessionService.get("currentUser").batch.startDate;
				$scope.currentBatchEnd1 = SessionService.get("currentUser").batch.endDate;
				
				/**
				 * Counts the difference in weeks between the current batch start date and today's date
				 * @return current batch week number
				 */
				function weeksBetween2(d1, d2) {
				    return Math.round((d2 - d1) / (7 * 24 * 60 * 60 * 1000));
				}
				
				var difference = weeksBetween2($scope.currentBatchStart1, currentDate2);

				$scope.weekNum = difference;
						
			$http({
				url: "rest/api/v1/Users/InBatch",
				method: 'GET',
				params: {
				batchId: batchId
				}
			}).then(function(response){
				$scope.usersInBatch = response.data
				
				$scope.listNames = [];

			    		var firstNames= [];
						var lastNames= [];

				$scope.numOfAssociates = 0;
				
				for(var i = 0; i < $scope.usersInBatch.length; i++) {
					$scope.batchUsers = $scope.usersInBatch[i];
					
				    if(SessionService.get("currentUser").batch.endDate > currentDate){
					    firstNames.push($scope.batchUsers.fName);
						lastNames.push($scope.batchUsers.lName);
						
						$scope.numOfAssociates += 1;
						
						$scope.listNames[i] = {
					    		"firstName": firstNames[i],
					    		"lastName": lastNames[i]
					    };
				    }else{
				    	$scope.listNames = 'N/A';
				    }
				    $scope.trainerInBatch = SessionService.get("currentUser").batch.trainer.fName;
				    $scope.trainerInBatchLast = SessionService.get("currentUser").batch.trainer.lName;
				    
				}
			})
			} else {
				$scope.message = 'You have no current batches';
				$scope.currentBatchStart1 = 'N/A';
				$scope.currentBatchEnd1 = 'N/A';
				$scope.weekNum = 'N/A';
				$scope.listNames = 'N/A';
				$scope.percent = 'N/A';
			}
		} else if (!$scope.trainerHasBatch || !$scope.userHasBatch){
			$scope.message = 'You have no current batches';
			$scope.currentBatchStart1 = 'N/A';
			$scope.currentBatchEnd1 = 'N/A';
			$scope.weekNum = 'N/A';
			$scope.listNames = 'N/A';
			$scope.percent = 'N/A';
		}
	}
	
	
		
	
	
		var currentDate = new Date().getTime();
		
		
		
		/**
		 * Will return all of the missed subtopics out of the total subtopics
		 * Will also categorize those subtopics in their respective topic category 
		 * @return A list of missed subtopics/total subtopics
		 */
		$scope.returnMissed = function(selectedInfo){
			 	var url;
			 	 if(SessionService.get("currentUser").role == 1){
		            	url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("currentUser").batch.id;
		            }else if ((SessionService.get("currentUser").role == 3 || SessionService.get("currentUser").role == 2) && SessionService.get("currentUser").batch) {
		            	url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("trainerBatch").id;
		            }else if(SessionService.get("currentUser").role == 2 && SessionService.get("trainerBatch")){
		             	if(selectedInfo == undefined && $scope.hitCount == 0){
		             		url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("trainerBatch").id;
		             		$rootScope.changedBatchId = SessionService.get("trainerBatch").id;
		             	} else if(selectedInfo != undefined){
		             		url ="rest/api/v1/Calendar/Subtopics?batchId="+ selectedInfo;
		             		$rootScope.changedBatchId = selectedInfo;
		             	}
		             	
		            }
			 	

			 	if($scope.trainerHasBatch || $scope.userHasBatch){
			 		$scope.loading = true;
			 	} else {
			 		$scope.loading = false;
			 	};
	            
			 	if($scope.loading){
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
            				var status = response.data[i].status.id;
                				
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


             		for(var j = 0; j < $scope.subTopics.length ; j++) {
                		var stat= response.data[j].status.id;
                 		
                 		
                     		if(stat == 4){
                     			if(response.data[j].subtopicName.topic){
                        			var topicName = response.data[j].subtopicName.topic.name;
                     				var topicNameExists = false;
                     				
                     				for(k in $scope.topicArray){
                     					if($scope.topicArray[k] == topicName){
                     						topicNameExists = true;
                     					}
                     				}
                     				if(!topicNameExists){
                     					$scope.topicArray.push(topicName);
                     				}
                     			
                     			}else {
                     				var iftopicNameExists = false;
                                 	for(k in $scope.topicArray){
                     					if($scope.topicArray[k] == "Other"){
                     						iftopicNameExists = true;
                     					}
                     				}
                     				if(!iftopicNameExists){
                     					$scope.topicArray.push("Other");
                     				}
                     			}
                     		}
             		}
             		
             		for(var z = 0; z < $scope.topicArray.length ; z++){
             			var docElement = document.getElementById("mainList");
             			var createLI = document.createElement("LI");
             			var createUL = document.createElement("UL");
             			var textNode = document.createTextNode($scope.topicArray[z] + ":");
             			
             			createLI.className += "listTitle";
             			createUL.id = $scope.topicArray[z];
             			
             			docElement.appendChild(createLI);
             			createLI.appendChild(textNode);
             			createLI.appendChild(createUL);
             		}
             		
             		
             		for(var k = 0; k < $scope.subTopics.length ; k++) {
                		 	var st= response.data[k].status.id;
                 			var title = response.data[k].subtopicName.name
                 		
                     		if(st == 4){
                     			if(response.data[k].subtopicName.topic){
                     				var tName = response.data[k].subtopicName.topic.name;
                     				
                     					for(var l = 0; l < $scope.topicArray.length ; l++){
                     						if(tName == $scope.topicArray[l]){
                     							var dElement = document.getElementById(tName);
                     							var cLI = document.createElement("LI");
                     							var tNode = document.createTextNode(title);
                     	                 		
                     	                 		cLI.className += "listItem";
                     	     					
                     	                 		cLI.appendChild(tNode);
                     	     					dElement.appendChild(cLI);
                     	     					
                     	     					$scope.count += 1;
                     						}
                     					}
                     					
                     			}else if(!response.data[k].subtopicName.topic && document.getElementById("Other")){
                     				var docEle = document.getElementById("Other");
                     				var creLI = document.createElement("LI");
                     				var txtNode = document.createTextNode(title);
                 	                 		
                 	     			creLI.className += "listItem";
                 	     					
                 	     			creLI.appendChild(txtNode);
                 	     			docEle.appendChild(creLI);
                 	     					
                 	     			$scope.count += 1;
                     			}
                     		}
             		}
            		
             	}).finally(function() {
            		$scope.loading = false;
            	});
			 }
		}
		
			 
		
		/**
		 * this function controls what the user sees when they have multiple current batches, updating the currently loaded script
		 */
		$scope.hitCount = 0;
		var startDate;
		var endDate;
		
		$scope.currentBatch = function(){
			
			$http({
				url: 'rest/api/v1/Batches/All',
				method: 'GET'
			})
			.then(function success(response){
				$scope.batchCount = 0;

				for(var m = 0; m < response.data.length; m++){

					if(response.data[m].trainer.userId == SessionService.get("currentUser").userId){
						if(currentDate < response.data[m].endDate && currentDate > response.data[m].startDate){
							var batchDropdown = document.getElementById("batchDropdown");
							var dropdownItem = document.createElement("OPTION");
							var createText = document.createTextNode(response.data[m].name);
							
							dropdownItem.setAttribute("value", response.data[m].name);
							dropdownItem.id = response.data[m].id
							
							dropdownItem.appendChild(createText);
							batchDropdown.appendChild(dropdownItem);
							
							$scope.batchCount += 1;
						}
					}
				}
				
				document.getElementById("batchDropdown").addEventListener("change", function(){
					$scope.hitCount += 1;
					var selectedElement = document.getElementById("batchDropdown");
					$scope.changeInfo = selectedElement[selectedElement.selectedIndex].id;
					
					$scope.$apply(function(){ 
						
						for(var n = 0; n < response.data.length; n++){

							if(response.data[n].id == $scope.changeInfo){
						/**
	            		 * Populates the day progress bar by days completed when new option selected
	            		 */
	            		
	            			if ($scope.trainerHasBatch){
	            				startDate = response.data[n].startDate;
	            				endDate = response.data[n].endDate;
	            				
	            				var daysComplete = currentDate - startDate;
	            				var totalDays = endDate - startDate;
	            				
	            				$scope.percent = Math.round((daysComplete * 100) / totalDays) + "%";
	            				
	            			} else if ($scope.userHasBatch){
	            				
	            				startDate = SessionService.get("currentUser").batch.startDate;
	            				endDate = SessionService.get("currentUser").batch.endDate;
	            				
	            				 var dayscomplete = currentDate - startDate;
	            				 var totaldays = endDate - startDate;
	            				
	            				$scope.percent = Math.round((dayscomplete * 100) / totaldays) + "%";
	            			}
							}
						}
					});
					$rootScope.changedBatchId = $scope.changeInfo;
					$scope.returnMissed($scope.changeInfo);
					$scope.getData(startDate, endDate, $scope.changeInfo, $scope.hitCount);
				});
				
			});
			}
                     	
});
