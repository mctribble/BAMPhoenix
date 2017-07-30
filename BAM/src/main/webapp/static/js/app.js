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
	}).when("/calendar",{
		templateUrl: "static/pages/calendar.html",
		controller: 'uiCalendarCtrl'
	}).when("/associates",{
		templateUrl: "static/pages/ViewAssociates.html",
		controller: "associatesController"
	}).when("/update",{
		templateUrl: "static/pages/update.html"
	}).when("/batchesPast",{
		templateUrl: "static/pages/batchesPast.html",
		controller: "batchesPast"
	}).when("/batchesFuture",{
		templateUrl: "static/pages/batchesFuture.html",
		controller: "batchesFuture"
	}).when("/editBatch", {
		templateUrl: "static/pages/EditBatch.html",
		controller: "editBatchController"
	})
	
	// route for the home page
    	.when('/home',{
    		templateUrl : 'static/pages/home.html',
    		controller  : 'mainController'
    	})  
	
	// route for associate homepage
        .when('/associate-home', {
        	templateUrl : 'static/pages/associate-home.html',
        	controller  : 'associateCalCtrl'
        })
        
        // route for associate batch view
        .when('/view-batch', {
        	templateUrl : 'static/pages/view-batch.html',
        	controller  : 'associateCalController'
        })
        
        // route for associate task view
        .when('/tasks', {
        	templateUrl : 'static/pages/view-tasks.html',
        	controller  : 'associateCalController'
        })
        
        // route for associate evaluation view
        .when('/evaluations', {
        	templateUrl : 'static/pages/associate-evaluations.html',
        	controller  : 'associateCalController'
        })
        
       // route for associate batch view
        .when('/modify-batch', {
        	templateUrl : 'static/pages/trainer-modify-batch.html',
        	controller  : 'CalendarCtr'
        })
        
        .when('/trainer-home', {
        	templateUrl	: 'static/pages/trainer-homepage.html',
        	controller : 'trainerController'
        })
        
        .when('/create-report', {
        	templateUrl	: 'static/pages/trainer-reports.html',
        	controller : 'reportCtrl'
        })
        
        //route for trainer creating a batch
        .when('/trainer-create-batch',{
        	templateUrl : 'static/pages/trainer-create-batch.html',
        	controller : 'CreateBatchCtrl'
        })
        
        //route for trainer confirming a batch
        .when('/trainer-confirm-batch', {
        	templateUrl : 'static/pages/trainer-confirm-batch.html',
        	controller : 'confirmBatchCtrl' 
        	
        })
        
        .when('/trainer-new-task', {
        	templateUrl : 'static/pages/trainer-task.html',
        	controller : 'trainerTaskController' 
        })
        
        .when('/trainer-evaluate-tasks', {
        	templateUrl : 'static/pages/trainer-evaluate-tasks.html',
        	controller : 'evaluateTaskCtrl'
        })
	
	.otherwise({redirectTo: '/'});
});
