/**
 * Controller for adding subtopics
 * @author: Samuel Louis-Pierre & Avant Mathur
 */
app.controller("subTopicController",function($scope, SessionService, $location, $compile, $http){
	
	//set batchId with the id of the currentBatch if it exists else use the trainerBatch
	var batchId;
	if(SessionService.get("currentBatch"))
	{
		batchId = SessionService.get("currentBatch").id;
		SessionService.set("currentBatchName", SessionService.get("currentBatch").name);
	}else
	{
		//if currentBatch is not set use the trainerBatch's id
		batchId = SessionService.get("trainerBatch").id; 
		SessionService.set("currentBatchName", SessionService.get("trainerBatch").name);
	}
	
	//Check if currentBatch is set before using it.
	if(batchId) 
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
		},function(response) {
			$scope.message = true;
			$scope.msg = 'Failed to retrieve batch';
		});
	}
	
	$scope.getSubtopicPool = function(){
		$http({
			url: "rest/api/v1/Curriculum/SubtopicPool",
			method: "GET", //Getting the subtopics
		}).then(function(response){
			var subtopics = response.data;
		
			var uniqueTopics = new Set();
			var topicMap = new Map();
			
			var subtopicArray = [];
			for(i in subtopics){
					if(!uniqueTopics.has(subtopics[i].subtopicName.topic.name)){
					uniqueTopics.add(subtopics[i].subtopicName.topic.name);

					var array = [];
					array.push(subtopics[i].subtopicName.name);
					topicMap.set(subtopics[i].subtopicName.topic.name, array);
					}else{
						var array = topicMap.get(subtopics[i].subtopicName.topic.name);
						topicMap.delete(subtopics[i].subtopicName.topic.name);
						array.push(subtopics[i].subtopicName.name);
						topicMap.set(subtopics[i].subtopicName.topic.name,array);
					}
			}

					var subTopicsMenu = angular.element( document.querySelector( '#subtopics' ));
					var topicnameevent = angular.element( document.querySelector( '#topicname' ));

        			//loading topics in the dropdown menu
        			for(topic of uniqueTopics){
        				
        				var top = angular.element("<option>" + topic + "</option>");
        				var topic = $compile(top)($scope);
        				
        				topicnameevent.append(topic);      				
        			}        			 

        		//based on the topic selected, load correlated subtopics
        		$('#topicname').change(function () {
        			var selection = this.value;
        			$scope.selectedTopic = selection;        			       			
        			
        			subTopicsMenu.empty();
        			
        			var subIDs = 0;
        					//adding subtopics to the subtopics dropdown menu
        					for(subtopic of Array.from(topicMap.get(selection))){
        	        			var ev = angular.element("<option id = events"+ subIDs++ +">" + subtopic + "</option>");
        	    				var sub = $compile(ev)($scope);
        	    				subTopicsMenu.append(sub);
        					}
        					
        					$scope.selectedSubtopic = angular.element( document.querySelector( '#events0'))[0].innerText;
        					
        					for(i in subtopics){
                    			if($scope.selectedSubtopic == subtopics[i].subtopicName.name){
                    				$scope.topic_id = subtopics[i].subtopicName.topic.id;
                    				$scope.subtopic_id = subtopics[i].subtopicId;
                    			}
                    		}       		
        		});
        		
        		//get topic and subtopic id of selected items
        		$('#subtopics').change(function () {
        			var selection = this.value;
        			$scope.selectedSubtopic = selection;

            		for(i in subtopics){
            			if($scope.selectedSubtopic == subtopics[i].subtopicName.name){
            				$scope.topic_id = subtopics[i].subtopicName.topic.id;
            				$scope.subtopic_id = subtopics[i].subtopicId;
            				$scope.prevDate = subtopics[i].subtopicDate;
            			}
            		}
        		});

        		//based on date selected and subtopic selected,
        		//check if subtopic already exist on that date
        		$('#startDate').change(function () {
        			var selection = this.value;
        			$scope.date = selection;
        			
        			var prevdate = new Date($scope.prevDate);
        			
        			var timestamp = new Date(selection).getTime() + 46800000;
        			var newDate = new Date(timestamp);
        			
        			$scope.stDate = prevdate;
        			$scope.selDate = newDate;
        			    
        			if($scope.stDate.getMonth() == $scope.selDate.getMonth() && $scope.stDate.getDate() == $scope.selDate.getDate()){
        			//EMPTY BECAUSE 
        			}
        		});
			});
		}

	//load the subtopic pool on page load
	$scope.getSubtopicPool();

	/*Creating the objects that are needed for subtopic object 
	by pulling the values from the corresponding html elements.*/ 
	$scope.addSubtopic = function(){

		var topicName = {
				id : $scope.topic_id,
				name: $scope.selectedTopic 
			}
		
		var subTopicType = {
				id : 1,
				name : "Lesson"
			}
		
		var subtopicName = {
				id : $scope.subtopic_id,
				name: $scope.selectedSubtopic,
				topic: topicName,
				type: subTopicType
		}
		
		var subtopicDate = $scope.selDate;

		var status = {
			id : 1,
			name : "Pending"
		}
		
		var subtopic = {
				subtopicName : subtopicName,
				batch : $scope.batch,
				status : status,
				subtopicDate : subtopicDate				
		}

		/*Persisting the subtopics to the database by using the end points
		 * checking if dates match and denying request if equal */
		if($scope.stDate.getMonth() == $scope.selDate.getMonth() && $scope.stDate.getDate() == $scope.selDate.getDate()){
			$scope.message = true;
			$scope.msg = 'Subtopic already exists on that day.';
			$scope.alertClass = 'alert alert-danger';
		}else{
			$scope.stDate = $scope.selDate;
			
			$http({
			url: "rest/api/v1/Subtopic/addSubtopic",
			method: 'POST',
			data : subtopic
			
		}).then(function success(response){
			$scope.message = true;
			$scope.msg = 'Successfully added!';
			$scope.alertClass = 'alert alert-success';
		},function error(response) {
			$scope.message = true;
			$scope.msg = 'Failed to add Subtopic';	
		});
	}
	}
});
