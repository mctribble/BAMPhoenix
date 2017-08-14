/**
 * 
 */
app.service('SessionSevice', function($window){
	var service = this;
	var sessionStorage = $window.sessionStorage;
	
	service.getUser = function(currentUser){
		return sessionStorage.getItem(key);
	};
	
	service.set = function(key, value){
		sessionStorage.setItem(key, value);
	};
	service.unset = function(key){
		sessionStorage.removeItem(key);
	};
});