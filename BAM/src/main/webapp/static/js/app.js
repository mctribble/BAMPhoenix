var app = angular.module('bam', ['ngRoute', 'dndLists']);
app.config(function($routeProvider, $locationProvider){
	$locationProvider.html5Mode(false).hashPrefix('');
	$routeProvider.when("/",{
		templateUrl: "static/pages/login.html",
		controller: 'loginController'
	}).when("/batchesAll",{
		templateUrl:"static/pages/batchesAll.html",
		controller: "batchesAllController"
	}).when("/register",{
		templateUrl: "static/pages/register.html",
		controller: "bamUserRegisterController"
	}).when("/home",{
		templateUrl: "static/pages/calendar.html",
		controller: "calendarController"
	}).when("/associates",{
		templateUrl: "static/pages/ViewAssociates.html",
		controller: "associateViewController"
	}).when("/update",{
		templateUrl: "static/pages/update.html",
		controller: "associateUpdateController"
	}).when("/reset",{
		templateUrl: "static/pages/reset.html",
		controller: "passwordResetController"
	}).when("/batchesPast",{
		templateUrl: "static/pages/batchesPast.html",
		controller: "batchesPastController"
	}).when("/batchesFuture",{
		templateUrl: "static/pages/batchesFuture.html",
		controller: "batchesFutureController"
	}).when("/editBatch", {
		templateUrl: "static/pages/EditBatch.html",
		controller: "batchEditController"
	}).when("/noBatch",{
		templateUrl: "static/pages/NoBatch.html"
	}).when("/curriculum",{
		templateUrl: "static/pages/curriculum.html",
		controller: "curriculumController"
	}).when("/logout", {
	    templateUrl: "static/pages/login.html", 
<<<<<<< HEAD
	    controller: "loginController"
=======
	    controller: 'loginController'
>>>>>>> 5b98851782eac1c9046cbe660c408c8adbc8d0e8
	}).otherwise({redirectTo: '/'});
});
