var app = angular.module('bam', ['ngRoute']);
app.config(function($routeProvider, $locationProvider){
	$locationProvider.html5Mode(false).hashPrefix('');
	$routeProvider.when("/",{
		templateUrl: "static/pages/login.html",
		controller: 'loginCtl'
	}).when("/allbatches",{
		templateUrl:"static/pages/allbatches.html"
	}).when("/register",{
		templateUrl: "static/pages/register.html"
	}).when("/home",{
		templateUrl: "static/pages/calendar.html",
		controller: 'uiCalendarCtrl'
	}).when("/associates",{
		templateUrl: "static/pages/ViewAssociates.html",
		controller: "associatesController"
	}).when("/update",{
		templateUrl: "static/pages/update.html"
	}).otherwise({redirectTo: '/'});
});
