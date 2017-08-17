/**
 * @Author: Duncan Hayward, Jaydeep Bhatia 
 * Creating a service method that creates a Session
 * call the get function for getting a session object
 * call the set method for setting a session object
 */
app.service('SessionService', function($window){
	var service = this;
	var sessionStorage = $window.sessionStorage;
	
	service.get = function(key){
		return JSON.parse(sessionStorage.getItem(key));
	};
	
	service.set = function(key, value){
		sessionStorage.setItem(key, angular.toJson(value));
	};
	service.unset = function(key){
		sessionStorage.removeItem(key);
	};
	service.remove = function(){
		sessionStorage.clear();
	};
	
	service.loadCalendar = function(url){
		
	}
});


//app.service('GetSubtopics', ['SessionService', function($scope, $http, SessionService){
//	this.getPageOfSubtopics = function(pageNumber){
//		var url;
//		var returnValue = [];
//		var pageSize = 20;
//		var morePages = false;
//		
//	    if(SessionService.get("currentUser").role == 1){
//	    	url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("currentUser").batch.id;
//	    }else if ((SessionService.get("currentUser").role == 3 || SessionService.get("currentUser").role == 2 ) && SessionService.get("currentBatch")) {
//	    	url ="rest/api/v1/Calendar/Subtopics?batchId="+SessionService.get("currentBatch").id;
//	    }else if(SessionService.get("currentUser").role == 2 && SessionService.get("trainerBatch")){
//	    	url ="rest/api/v1/Calendar/SubtopicsPagination?batchId="+ SessionService.get("trainerBatch").id + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
//	    }else{
//	    }
//	/* event source that contains custom events on the scope */
//	    
//		$scope.events = [];
//		if(!SessionService.get("gotSubtopics") && url) {
//			SessionService.set("gotSubtopics", true); 
//			$scope.loading = true;
//			$http({
//	    		method : "GET",
//	    		url : url
//	    	}).then(function successCallback(response) {
//	    		for(var i = 0; i < response.data.length ; i++) {
//	        			var title = response.data[i].subtopicName.name;
//	            		var dates = response.data[i].subtopicDate;
//	            		var status= response.data[i].status.id;
//	            		var a = new Date(dates);  
//	                    var year = a.getUTCFullYear();
//	                    var month = a.getMonth();
//	                    var day = a.getDate();
//	                    var formattedTime = new Date(year, month, day);
//	                    if(status == 1 ){
//	                		var temp = {title: title, start: formattedTime, end: formattedTime};
//	                    }else if(status == 2 ){
//	                    	var temp = {title: title, start: formattedTime, end: formattedTime, className:['topiccolorgreen']};
//	                    }else if(status == 3 ){
//	                    	var temp = {title: title, start: formattedTime, end: formattedTime, className:['topiccolorred']};
//	                    }else if(status == 4){
//	                    	var temp = {title: title, start: formattedTime, end: formattedTime, className:['topiccoloryellow']};
//	                    }   
//	        			$scope.events.push(temp);
//	    		}
//	    		
//	    		if(response.data.length < pageSize){
//	    			morePages = true;
//	    		}
//	    	}).finally(function() {
//	    		// Turn off loading indicator whether success or
//				// failure.
//	    		$scope.loading = false;
//	//    		SessionService.set("gotSubtopics", false);
//	    	});
//		}
//		
//		returnValue.push($scope.events);
//		returnValue.push(morePages);
//		
//		return returnValue;
//	}
//}]);