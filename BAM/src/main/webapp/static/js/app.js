var app = angular.module('bam', ['ngRoute']);
app.config(function($routeProvider, $locationProvider){
	$locationProvider.html5Mode(false).hashPrefix('');
	$routeProvider.when("/",{
		templateUrl: "static/pages/login.html",
		controller: 'loginCtl'
	}).when("/register",{
		templateUrl: "static/pages/register.html"

	})
	.when("/home",{
		templateUrl: "static/pages/calendar.html",
<<<<<<< HEAD
		controller: "CalendarController"
=======
		controller: 'uiCalendarCtrl'

>>>>>>> master
	}).otherwise({redirectTo: '/'})
});