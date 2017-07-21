var app = angular.module('bam', ['ngRoute']);
app.config(function($routeProvider, $locationProvider){
	$locationProvider.html5Mode(false).hashPrefix('');
	$routeProvider.when("/",{
		templateUrl: "static/pages/login.html"
	}).when("/register",{
		templateUrl: "static/pages/register.html"
	}).otherwise({redirectTo: '/'})
});