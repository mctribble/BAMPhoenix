var app = angular.module('bam', ['ngRoute']);
app.config(function($routeProvider, $locationProvider){
	$locationProvider.html5Mode(false).hashPrefix('');
	$routeProvider.when("/",{
		templateUrl: "static/pages/login.html",
		controller: 'loginCtl'
	}).when("/batchesAll",{
		templateUrl:"static/pages/batchesAll.html",
		controller: "batchesAllCtr"
	}).when("/register",{
		templateUrl: "static/pages/register.html"
	}).when("/home",{
		templateUrl: "static/pages/calendar.html",
		controller: 'uiCalendarCtrl'
	}).when("/associates",{
		templateUrl: "static/pages/ViewAssociates.html",
		controller: "associatesController"
	}).when("/update",{
<<<<<<< HEAD
		templateUrl: "static/pages/update.html",
		controller: "updateAssociateCtrl"
=======
		templateUrl: "static/pages/update.html"
	}).when("/reset",{
		templateUrl: "static/pages/reset.html"
>>>>>>> d48f88cbabe20852c33354d1ae7243447fe0d59d
	}).when("/batchesPast",{
		templateUrl: "static/pages/batchesPast.html",
		controller: "batchesPast"
	}).when("/batchesFuture",{
		templateUrl: "static/pages/batchesFuture.html",
		controller: "batchesFuture"
	}).when("/editBatch", {
		templateUrl: "static/pages/EditBatch.html",
		controller: "editBatchController"
	}).otherwise({redirectTo: '/'});
});
