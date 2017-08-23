/**
 * @Author: Duncan Hayward 
 * Adds google Analytics to site
 */
  app.controller('SampleCtrl', function ($analytics) {
	  $analytics.pageTrack('/my/url');
	  $analytics.eventTrack('eventName');
	  $analytics.eventTrack('eventName', {  category: 'category', label: 'label' });
	  
  });