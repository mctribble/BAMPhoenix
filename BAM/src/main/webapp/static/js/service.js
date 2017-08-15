/**
 * 
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
});