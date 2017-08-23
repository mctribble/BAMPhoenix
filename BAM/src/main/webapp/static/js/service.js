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
});

