/**
 * @Author: Duncan Hayward 
 * Adds google Analytics to site
 */
  app.controller('SampleCtrl', function ($rootScope, $location, $window, $analytics) {
	  $rootScope.$on('$routeChangeSuccess', function () {
		  $window.ga('send', {
		      'hitType': 'screenview',
		      'appName' : 'bam',
		      'screenName' : $location.url(),
		      'hitCallback': function() {
		    	  $analytics.pageTrack('/home');
		    	  $analytics.pageTrack('/reset');
		    	  $analytics.pageTrack('/calendar');
		    	  $analytics.eventTrack('eventName');
		    	  $analytics.eventTrack('eventName', {  category: 'category', label: 'label' });
		      }
		    }); 
	 
	  });
  });