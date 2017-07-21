var app = angular.module('bam', ['ngRoute']);
app.config(function($routeProvider, $locationProvider){
	
	$routeProvider.when("/",{
		templateUrl: "static/pages/login.html"
	}).otherwise({redirectTo: '/'})
});