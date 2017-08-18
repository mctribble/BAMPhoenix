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
})

//app.service('$q', 'GetSubtopics', ['$http', 'SessionService', 
//	function($q, $http, SessionService){
//	
//	
//	this.getPageOfSubtopics = function(pageNumber){
//		var events = [];
//		var morePages = false;
//		console.log('Inside service getPageOfSubtopics');
//		var url;
//		
//		var pageSize = 20;
//		
//	    if(SessionService.get("currentUser").role == 1){
//	    	url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("currentUser").batch.id;
//	    }else if ((SessionService.get("currentUser").role == 3 || SessionService.get("currentUser").role == 2 ) && SessionService.get("currentBatch")) {
//	    	url ="rest/api/v1/Calendar/Subtopics?batchId="+SessionService.get("currentBatch").id;
//	    }else if(SessionService.get("currentUser").role == 2 && SessionService.get("trainerBatch")){
//	    	url ="rest/api/v1/Calendar/SubtopicsPagination?batchId="+ SessionService.get("trainerBatch").id + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize;
//	    }else{
//	    }
//	    console.log('url is : ' + url);
//	/* event source that contains custom events on the scope */
//	    
//			var promise = $http.get(url).then(function successCallback(response) {
//	    		console.log('success');
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
//	        			events.push(temp);
//	    		}
//	    		
//	    		if(response.data.length == pageSize){
//	    			console.log('There are more pages to get\npageSize: ' + pageSize + '\nresponse.data.length: ' + response.data.length);
//	    			morePages = true;
//	    			console.log('service: morePage: ' + morePages);
//	    		}
////	    		console.log('Promise: ', promise);
////	    		return promise = "";
//	    	});
//			console.log('service about to return: morePage: ' + morePages);
//			var returnValue = [];
//			returnValue.push(events);
//			returnValue.push(morePages);
//			console.log('Service returnValue: ', returnValue);
//			
//			 $q.all(promise).then();
//			return returnValue;
//		}
//	}
//]);