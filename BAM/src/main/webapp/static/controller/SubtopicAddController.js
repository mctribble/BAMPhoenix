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
	
	$scope.getTopicPool = function(){
		$http({
			url: "rest/api/v1/Curriculum/TopicPool",
			method: "GET", //Getting the tooics
			
		}).then(function(response){
			var topics = response.data;
			$scope.topics = topics;
			
			var totalTopics = 0;
			var unqiueTopics = new Set();
			var topicMap = new Map();
			
			var substring = ":null";

			
			for(var i in topics){
								
				if(JSON.stringify(topics[i]).includes(substring) == false)
					{
					
					if(!unqiueTopics.has(topics[i].topic.name)){
					unqiueTopics.add(topics[i].topic.name);
					var array = [];
					array.push(topics[i].name);
					topicMap.set(topics[i].topic.name, array);
					
					totalTopics++;
					}else{
						var array2 = topicMap.get(topics[i].topic.name);
						topicMap.delete(topics[i].topic.name);
						array2.push(topics[i].name);
						topicMap.set(topics[i].topic.name,array);
						totalTopics++;
					
					}
					}
		
			}
			
			var countT =0;
			for (var [key, value] of topicMap) {
				  countT += value.length;				  
				}

			//parse the response into the local (front end) json object format
			
					var eventz = angular.element( document.querySelector( '#events' ));
					var topicnameevent = angular.element( document.querySelector( '#topicname' ));

        			//topic
        			for(topic of unqiueTopics){
        				
        				var top = angular.element("<option>" + topic + "</option>");
        				var topic = $compile(top)($scope);
        				
        				topicnameevent.append(topic);      				
        				
        			}        			 

        			$scope.utopia = unqiueTopics;

        		$('#topicname').change(function () {
        			var selection = this.value;
        			$scope.selectedTopic = selection;        			       			
        			
        			eventz.empty();

        			
        			var evnts = 0;
        					//adding subtopics to the subtopics dropdown menu
        					for(var subtopic of Array.from(topicMap.get(selection))){
        	        			var ev = angular.element("<option id = events"+ evnts++ +">" + subtopic + "</option>");
        	    				var eve = $compile(ev)($scope);
        	    				eventz.append(eve);
        					}
        					
        					$scope.selectedSubtopic = angular.element( document.querySelector( '#events0'))[0].innerText;
        					
        					for(i in topics){
                    			if($scope.selectedSubtopic == topics[i].name){
                    				$scope.topic_id = topics[i].topic.id;
                    				$scope.subtopic_id = topics[i].id; 
                    			}
                    		}       		
        		});
        		
        		
        		
        		$('#events').change(function () {
        			var selection = this.value;
        			$scope.selectedSubtopic = selection;

            		for(i in topics){

            			if($scope.selectedSubtopic == topics[i].name){
            				$scope.topic_id = topics[i].topic.id;
            				$scope.subtopic_id = topics[i].id;

            			}
            		}
        			
        		});

        		$('#startDate').change(function () {
        			var selection = this.value;
        			$scope.date = selection;
        		});
				
			});
		}

	//load the topic pool on page load
	$scope.getTopicPool();

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
		
		var subtopicDate = $scope.date;

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

		/*Persisting the subtopics to the database by using the end points */
		$http({
			url: "rest/api/v1/Subtopic/addSubtopic",
			method: 'POST',
			data : subtopic
		}).then(function(response){
			 $scope.subtopic.batch = batchId; //reponse.data is a javascript object (automatically parsed from the JSON from the server)
			 $scope.subtopic.name = subtopicName.name;
			 $scope.subtopic.status = 1;
		},function(response) {
			$scope.message = true;
			$scope.msg = 'Failed to retrieve batch';	
		})
	}
});
