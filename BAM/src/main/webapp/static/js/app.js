var app = angular.module('bam', ['ngRoute', 'dndLists','angulartics', 'angulartics.google.analytics']);
app.config(function($routeProvider, $locationProvider){
	$locationProvider.html5Mode(false).hashPrefix('');
	$routeProvider.when("/",{
		templateUrl: "static/pages/login.html",
		controller: 'loginController'
	}).when("/batchesAll",{
		templateUrl:"static/pages/batchesAll.html",
		controller: "batchesAllController"
	}).when("/myBatches",{
		templateUrl:"static/pages/myBatches.html",
		controller: "myBatchesController"
	}).when("/register",{
		templateUrl: "static/pages/register.html",
		controller: "bamUserRegisterController"
	}).when("/calendar",{
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
	}).when("/addSubtopic", {
		templateUrl: "static/pages/addSubtopic.html",
//		controller: "addSubtopicController"
	}).when("/noBatch",{
		templateUrl: "static/pages/NoBatch.html",
		controller: "noBatchController"
	}).when("/curriculum",{
		templateUrl: "static/pages/curriculum.html",
		controller: "curriculumController"
	}).when("/logout", {
	    templateUrl: "static/pages/login.html", 
	    controller: 'loginController'
	}).when("/home", {
	    templateUrl: "static/pages/dashboard.html", 
	    controller: 'dashboardController'
	}).otherwise({redirectTo: '/'});
});
