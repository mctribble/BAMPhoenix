var app = angular.module('bam', ['ngRoute','ui.calendar']);
app.config(function($routeProvider, $locationProvider){
	
	$locationProvider.html5Mode(false).hashPrefix('');
	$routeProvider
	.when("/",{
		templateUrl: "static/pages/login.html"})
	.when("/home",{
		templateUrl: "static/pages/calendar.html",
		controller: "CalendarController"
	}).otherwise({redirectTo: '/'})
});