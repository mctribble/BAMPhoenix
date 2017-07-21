var app = angular.module('bam', ['ngRoute','ui.calendar']);
app.config(function($routeProvider, $locationProvider){
<<<<<<< HEAD
	
	$locationProvider.html5Mode(false).hashPrefix('');
	$routeProvider
	.when("/",{
		templateUrl: "static/pages/login.html"})
	.when("/home",{
		templateUrl: "static/pages/calendar.html",
		controller: "CalendarController"
=======
	$locationProvider.html5Mode(false).hashPrefix('');
	$routeProvider.when("/",{
		templateUrl: "static/pages/login.html",
		controller: 'loginCtl'
	}).when("/register",{
		templateUrl: "static/pages/register.html"
>>>>>>> master
	}).otherwise({redirectTo: '/'})
});