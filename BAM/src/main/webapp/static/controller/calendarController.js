
/*
*  AngularJs Fullcalendar Wrapper for the JQuery FullCalendar
*  API @ http://arshaw.com/fullcalendar/
*
*  Angular Calendar Directive that takes in the [eventSources] nested array object as the ng-model and watches it deeply changes.
*       Can also take in multiple event urls as a source object(s) and feed the events per view.
*       The calendar will watch any eventSource array and update itself when a change is made.
*
*/

//angular.module('myCalendarApp', ['ngRoute'])

  app.constant('uiCalendarConfig', {	// Angular ui-Calendar API used to
										// create
	  // AngularJS calendar.
        calendars : {}
    })
  
	  		
	  		
  app.controller('calendarController', ['$rootScope','$scope','$http','$location', '$locale','$compile','uiCalendarConfig', 'SessionService',
        function ($rootScope,$scope,$http,$location, $locale,$compile,uiCalendarConfig, SessionService) {
	  	$(".navbar").show();
		  if(!SessionService.get("currentUser").batch && SessionService.get("currentUser").role == 1)
			{  
				$location.path('/noBatch');
			}
		  
	  	
	  	// Varibles set for the use of adding day,month,year,to the Date
		// attribute of a calendar.
		    var date = new Date();
		    var d = date.getDate();
		    var m = date.getMonth();
		    var y = date.getFullYear();
		    $scope.searchDate = new Date(); // Current Date
		    
            var sources = $scope.eventSources;	// variable sources to hold
												// different
            // events taking place on the calendar at any given time
            var extraEventSignature = $scope.calendarWatchEvent ? $scope.calendarWatchEvent : angular.noop;

            var wrapFunctionWithScopeApply = function (functionToWrap) {
                return function () {
                    // This may happen outside of angular context, so create one
					// if outside.
                    if ($scope.$root.$$phase) {
                        return functionToWrap.apply(this, arguments);
                    }

                    var args = arguments;
                    var that = this;
                    return $scope.$root.$apply(
                        function () {
                            return functionToWrap.apply(that, args);
                        }
                    );
                };
            };
            
          /*
			 * @Author: Tom Scheffer Calls the function changeDate if enter is
			 * pressed when in the scope of the date search bar
			 */
          $scope.changeDateKeyPress = function(keyEvent){
        	  if(keyEvent.which === 13){
        		  $scope.changeDate();
        	  }
          };
            
          /*
			 * @Author: Tom Scheffer Changes the view of the calendar so it
			 * shows the date inputed into the search bar
			 */
          $scope.changeDate = function(){
        	  uiCalendarConfig.calendars["myCalendar"].fullCalendar('gotoDate', $scope.searchDate);
        	  $scope.searchDate = new Date();
          };

          $scope.currentBatch = function(){
        	  /*SessionService.unset("currentBatch");
        	  if(SessionService.get("currentUser").role == 2 && SessionService.get("trainerBatch")){
	             	url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("trainerBatch").id;
        	  }
        	  if(!SessionService.get("gotSubtopics") && url){
            		SessionService.set("gotSubtopics", true); 
	             	$scope.loading = true;
              	    $scope.loadCalendar(url);
        	  }*/
          };
            var eventSerialId = 1;
            // @return {String} fingerprint of the event object and its
			// properties
            this.eventFingerprint = function (e) {
                if (!e._id) {
                    e._id = eventSerialId++;
                }

                var extraSignature = extraEventSignature({
                    event : e
                }) || '';
                var start = moment.isMoment(e.start) ? e.start.unix() : (e.start ? moment(e.start).unix() : '');
                var end = moment.isMoment(e.end) ? e.end.unix() : (e.end ? moment(e.end).unix() : '');

                // This extracts all the information we need from the event.
				// http://jsperf.com/angular-calendar-events-fingerprint/3
                return [e._id, e.id || '', e.title || '', e.url || '', start, end, e.allDay || '', e.className || '', extraSignature].join('');
            };

            var sourceSerialId = 1;
            var sourceEventsSerialId = 1;
            // @return {String} fingerprint of the source object and its events
			// array
            
            this.sourceFingerprint = function (source) {
                var fp = '' + (source.__id || (source.__id = sourceSerialId++));
                var events = angular.isObject(source) && source.events;

                if (events) {
                    fp = fp + '-' + (events.__id || (events.__id = sourceEventsSerialId++));
                }
                return fp;
            };

            // @return {Array} all events from all sources
            this.allEvents = function () {
                return Array.prototype.concat.apply(
                    [],
                    (sources || []).reduce(
                        function (previous, source) {
                            if (angular.isArray(source)) {
                                previous.push(source);
                            } else if (angular.isObject(source) && angular.isArray(source.events)) {
                                var extEvent = Object.keys(source).filter(
                                    function (key) {
                                        return (key !== '_id' && key !== 'events');
                                    }
                                );

                                source.events.forEach(
                                    function (event) {
                                        angular.extend(event, extEvent);
                                    }
                                );

                                previous.push(source.events);
                            }
                            return previous;
                        },
                        []
                    )
                );
            };

            // Track changes in array of objects by assigning id tokens to each
			// element and watching the scope for changes in the tokens
            // @param {Array|Function} arraySource array of objects to watch
            // @param tokenFn {Function} that returns the token for a given
			// object
            // @return {Object}
            // subscribe: function(scope, function(newTokens, oldTokens))
            // called when source has changed. return false to prevent
			// individual callbacks from firing
            // onAdded/Removed/Changed:
            // when set to a callback, called each item where a respective
			// change is detected
            this.changeWatcher = function (arraySource, tokenFn) {
                var self;

                var getTokens = function () {
                    return ((angular.isFunction(arraySource) ? arraySource() : arraySource) || []).reduce(
                        function (rslt, el) {
                            var token = tokenFn(el);
                            map[token] = el;
                            rslt.push(token);
                            return rslt;
                        },
                        []
                    );
                };

                // @param {Array} a
                // @param {Array} b
                // @return {Array} elements in that are in a but not in b
                // @example
                // subtractAsSets([6, 100, 4, 5], [4, 5, 7]) // [6, 100]
                var subtractAsSets = function (a, b) {
                    var obj = (b || []).reduce(
                        function (rslt, val) {
                            rslt[val] = true;
                            return rslt;
                        },
                        Object.create(null)
                    );
                    return (a || []).filter(
                        function (val) {
                            return !obj[val];
                        }
                    );
                };

                // Map objects to tokens and vice-versa
                var map = {};

                // Compare newTokens to oldTokens and call onAdded, onRemoved,
				// and onChanged handlers for each affected event respectively.
                var applyChanges = function (newTokens, oldTokens) {
                    var i;
                    var token;
                    var replacedTokens = {};
                    var removedTokens = subtractAsSets(oldTokens, newTokens);
                    for (i = 0; i < removedTokens.length; i++) {
                        var removedToken = removedTokens[i];
                        var el = map[removedToken];
                        delete map[removedToken];
                        var newToken = tokenFn(el);
                        // if the element wasn't removed but simply got a new
						// token, its old token will be different from the
						// current one
                        if (newToken === removedToken) {
                            self.onRemoved(el);
                        } else {
                            replacedTokens[newToken] = removedToken;
                            self.onChanged(el);
                        }
                    }

                    var addedTokens = subtractAsSets(newTokens, oldTokens);
                    for (i = 0; i < addedTokens.length; i++) {
                        token = addedTokens[i];
                        if (!replacedTokens[token]) {
                            self.onAdded(map[token]);
                        }
                    }
                };

                self = {
                    subscribe : function (scope, onArrayChanged) {
                        scope.$watch(getTokens, function (newTokens, oldTokens) {
                            var notify = !(onArrayChanged && onArrayChanged(newTokens, oldTokens) === false);
                            if (notify) {
                                applyChanges(newTokens, oldTokens);
                            }
                        }, true);
                    },
                    onAdded : angular.noop,
                    onChanged : angular.noop,
                    onRemoved : angular.noop
                };
                return self;
            };

            this.getFullCalendarConfig = function (calendarSettings, uiCalendarConfig) {
                var config = {};

                angular.extend(config, uiCalendarConfig);
                angular.extend(config, calendarSettings);

                angular.forEach(config, function (value, key) {
                    if (typeof value === 'function') {
                        config[key] = wrapFunctionWithScopeApply(config[key]);
                    }
                });

                return config;
            };

            this.getLocaleConfig = function (fullCalendarConfig) {
                if (!fullCalendarConfig.lang && !fullCalendarConfig.locale || fullCalendarConfig.useNgLocale) {
                    // Configure to use locale names by default
                    var tValues = function (data) {
                        // convert {0: "Jan", 1: "Feb", ...} to ["Jan", "Feb",
						// ...]
                        return Object.keys(data).reduce(
                            function (rslt, el) {
                                rslt.push(data[el]);
                                return rslt;
                            },
                            []
                        );
                    };

                    var dtf = $locale.DATETIME_FORMATS;
                    return {
                        monthNames : tValues(dtf.MONTH),
                        monthNamesShort : tValues(dtf.SHORTMONTH),
                        dayNames : tValues(dtf.DAY),
                        dayNamesShort : tValues(dtf.SHORTDAY)
                    };
                }

                return {};
            };
            
            	 var url;
	            if(SessionService.get("currentUser").role == 1){
	            	url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("currentUser").batch.id;
	            }else if ((SessionService.get("currentUser").role == 3 || SessionService.get("currentUser").role == 2 ) && SessionService.get("currentBatch")) {
	            	url ="rest/api/v1/Calendar/Subtopics?batchId="+SessionService.get("currentBatch").id;
	            }else if(SessionService.get("currentUser").role == 2 && SessionService.get("trainerBatch")){
	             	url ="rest/api/v1/Calendar/Subtopics?batchId="+ SessionService.get("trainerBatch").id;
	            }
            /* event source that contains custom events on the scope */
	            
            	$scope.events = [];
            	if(!SessionService.get("gotSubtopics") && url) {
            		SessionService.set("gotSubtopics", true); 
            		$scope.loading = true;
            		$http({
                		method : "GET",
                		url : url
                	}).then(function successCallback(response) {
                		var id=0;
                		for(var i = 0; i < response.data.length ; i++) {
                				$scope.eventSources = [{}];
                    			var title = response.data[i].subtopicName.name;
                        		var dates = response.data[i].subtopicDate;
                        		var status= response.data[i].status.id;
                        		var a = new Date(dates);  
                                var year = a.getUTCFullYear();
                                var month = a.getMonth();
                                var day = a.getDate();
                                var formattedTime = new Date(year, month, day);
                                var checkDuplicates = new Set();


                                
                                	if(status == 1 )
                                		var temp = {id: id, title: title, start: formattedTime, end: formattedTime};	
                                	if (status == 1  && new Date().getMonth() > formattedTime.getMonth() && new Date().getFullYear() >= formattedTime.getFullYear() )
                                    	var temp = {id: id, title: title, start: formattedTime, end: formattedTime, className:['topiccoloryellow']};
                                	if (status == 1  && new Date().getDate() > formattedTime.getDate() && new Date().getMonth() == formattedTime.getMonth() && new Date().getFullYear() == formattedTime.getFullYear() )
                                		var temp = {id: id, title: title, start: formattedTime, end: formattedTime, className:['topiccoloryellow']};
                                    if(status == 2 )
                                		var temp = {id: id, title: title, start: formattedTime, end: formattedTime, className:['topiccolorgreen']};
                                    if(status == 3 )
                                		var temp = {id: id, title: title, start: formattedTime, end: formattedTime, className:['topiccolorred']};
                                    if (status == 4)

                                    	var temp = {id: id, title: title, start: formattedTime, end: formattedTime, className:['topiccoloryellow']};
                                	if (status == 4  && new Date().getDate() == formattedTime.getDate() && new Date().getMonth() == formattedTime.getMonth() && new Date().getFullYear() == formattedTime.getFullYear() )
                                		var temp = {id: id, title: title, start: formattedTime, end: formattedTime};  
                                    

                    			$scope.events.push(temp);
                    			id++;
                    	
                		}
                			uiCalendarConfig.calendars['myCalendar'].fullCalendar('addEventSource',$scope.events);
         
                		// $scope.renderCalendar('myCalendar');
                	}).finally(function() {
                		// Turn off loading indicator whether success or
						// failure.
                		$scope.loading = false;
                		SessionService.set("gotSubtopics", false);
                	});
            	}
           // POST method to show subtopics on the calendar
            			// For showing and hiding the
											// loading gif.
            	
            		
            
            $scope.calEventsExt = {
            	       color: '#f00',
            	       textColor: 'yellow',
            	       events: [ 
            	          {type:'party',title: 'Lunch',start: new Date(y, m, d, 12, 0),end: new Date(y, m, d, 14, 0),allDay: false},
            	          {type:'party',title: 'Lunch 2',start: new Date(y, m, d, 12, 0),end: new Date(y, m, d, 14, 0),allDay: false},
            	          {type:'party',title: 'Click for Google',start: new Date(y, m, 28),end: new Date(y, m, 29),url: 'http://google.com/'}
            	        ]
            	    };
            if(SessionService.get("currentUser").role == 2 && SessionService.get("currentBatch") == null){
            /* alert on eventClick */

            $scope.alertOnEventClick = function( event, date, jsEvent, view){
            	var eventDate= new Date(event.start);
            	
            	//UTC offset
            	//&& ! (  new Date().setHours(0, 0, 0, 0).getDate() == eventDate.getDate() &&  new Date().setHours(0, 0, 0, 0).getMonth() == eventDate.getMonth() && new Date().setHours(0, 0, 0, 0).getFullYear() == eventDate.getFullYear() )	
            	if(event.start < new Date().setHours(0, 0, 0, 0) && ! (  new Date().getDate() == (eventDate.getDate() ) &&  new Date().getMonth() == eventDate.getMonth() && new Date().getFullYear() == eventDate.getFullYear() )	 ){
            		
            		if(event.className == 'topiccolorgreen'){
                  		event.className= 'topiccolorred';
                		 uiCalendarConfig.calendars['myCalendar'].fullCalendar( 'updateEvent', event);
                		 // http for green to red
                      $http({
                   		method : "GET",
                   		url : "rest/api/v1/Calendar/StatusUpdate?batchId="+SessionService.get("trainerBatch").id+"&subtopicId="+event.title+"&status=Canceled"
                   		
                   	 }).then(function successCallback(response) {
                 
                   	 });
                     
//                		var temp = {id: event.id, title: event.title, start: event.start, entd: event.end, className:['topiccolorred']};
//                		$scope.event1= [];
//                		uiCalendarConfig.calendars['myCalendar'].fullCalendar('removeEvents',event.id);
//                		uiCalendarConfig.calendars['myCalendar'].fullCalendar( 'addEventSource', source )
                	}else if(event.className == 'topiccolorred'){
                		event.className= 'topiccoloryellow';
                        uiCalendarConfig.calendars['myCalendar'].fullCalendar( 'updateEvent', event);

                		  // http for red to blue
                      $http({
                   		method : "GET",
                   		url : "rest/api/v1/Calendar/StatusUpdate?batchId="+SessionService.get("trainerBatch").id+"&subtopicId="+event.title+"&status=Missed"
                   	 }).then(function successCallback(response) {
                   		
                   	 });
                		
                	}else {
                		event.className= 'topiccolorgreen';
                        uiCalendarConfig.calendars['myCalendar'].fullCalendar( 'updateEvent', event);
                		  // http for blue to green
                      $http({
                   		method : "GET",
                   		url : "rest/api/v1/Calendar/StatusUpdate?batchId="+SessionService.get("trainerBatch").id+"&subtopicId="+event.title+"&status=Completed"
                   	 }).then(function successCallback(response) {
                   		

                   	 });
                     

                	}
            		
            		
            	}
            	
            	else{
      
            	if(event.className == 'topiccolorgreen'){
              		event.className= 'topiccolorred';
            		 uiCalendarConfig.calendars['myCalendar'].fullCalendar( 'updateEvent', event);
            		 // http for green to red
                  $http({
               		method : "GET",
               		url : "rest/api/v1/Calendar/StatusUpdate?batchId="+SessionService.get("trainerBatch").id+"&subtopicId="+event.title+"&status=Canceled"
               		
               	 }).then(function successCallback(response) {
             
               	 });
                 
//            		var temp = {id: event.id, title: event.title, start: event.start, entd: event.end, className:['topiccolorred']};
//            		$scope.event1= [];
//            		uiCalendarConfig.calendars['myCalendar'].fullCalendar('removeEvents',event.id);
//            		uiCalendarConfig.calendars['myCalendar'].fullCalendar( 'addEventSource', source )
            	}else if(event.className == 'topiccolorred'){
            		event.className= '';
                    uiCalendarConfig.calendars['myCalendar'].fullCalendar( 'updateEvent', event);

            		  // http for red to blue
                  $http({
               		method : "GET",
               		url : "rest/api/v1/Calendar/StatusUpdate?batchId="+SessionService.get("trainerBatch").id+"&subtopicId="+event.title+"&status=Pending"
               	 }).then(function successCallback(response) {
               		
               	 });
            		
            	}else {
            		event.className= 'topiccolorgreen';
                    uiCalendarConfig.calendars['myCalendar'].fullCalendar( 'updateEvent', event);
            		  // http for blue to green
                  $http({
               		method : "GET",
               		url : "rest/api/v1/Calendar/StatusUpdate?batchId="+SessionService.get("trainerBatch").id+"&subtopicId="+event.title+"&status=Completed"
               	 }).then(function successCallback(response) {
               		

               	 });
                 

            	}
            	
           	}//end of else	

            	
            };
            }
            /* alert on Drop */
             $scope.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
            	 console.log('dropped classname: '+ event.className);
            	 var eventDate= new Date(event.start);
             	console.log('Event->'+ event.id);
             	console.log('Date in event-> '+ eventDate.getDate() + ' Date now -day ' + new Date().getDate() + 'Date now - month ' + new Date().getMonth() + 'Date now -year ' + new Date().getFullYear());
             	console.log('Date in event-> '+ eventDate.getDate() + ' Date event -day ' + (eventDate.getDate() + 1)  + 'Date event - month ' + eventDate.getMonth() + 'Date event -year ' + eventDate.getFullYear() );
            	if(event.className == '' && event.start < new Date().setHours(0, 0, 0, 0) && ! (  new Date().getDate() == (eventDate.getDate() ) &&  new Date().getMonth() == eventDate.getMonth() && new Date().getFullYear() == eventDate.getFullYear() ) )
            	{
            		
                		event.className= 'topiccoloryellow';
                		console.log('topic blue dragged');
                        uiCalendarConfig.calendars['myCalendar'].fullCalendar( 'updateEvent', event);
                		  // http for blue to green
                      $http({
                   		method : "GET",
                   		url : "rest/api/v1/Calendar/StatusUpdate?batchId="+SessionService.get("trainerBatch").id+"&subtopicId="+event.title+"&status=Missed"
                   	 }).then(function successCallback(response) {
                   		

                   	 });
                     

               
            	}	
            	
              	if((event.className == 'topiccoloryellow' && event.start > new Date().setHours(0, 0, 0, 0) ) || ( event.className == 'topiccoloryellow' && new Date().getDate() == (eventDate.getDate() + 1) &&  new Date().getMonth() == eventDate.getMonth() && new Date().getFullYear() == eventDate.getFullYear() ) )
            	{
            		
                		event.className= '';
                		console.log('topic yellow dragged');
                        uiCalendarConfig.calendars['myCalendar'].fullCalendar( 'updateEvent', event);
                		  // http for blue to green
                      $http({
                   		method : "GET",
                   		url : "rest/api/v1/Calendar/StatusUpdate?batchId="+SessionService.get("trainerBatch").id+"&subtopicId="+event.title+"&status=Pending"
                   	 }).then(function successCallback(response) {
                   		

                   	 });
                     

               
            	}
            	
            	
             	
            	 $http({
             		method : "GET",
             		url : "rest/api/v1/Calendar/DateUpdate?batchId="+SessionService.get("trainerBatch").id+"&subtopicId="+event.title+"&date="+event.start
             	 }).then(function successCallback(response) {
             	 });
            };
            
            /* alert on Resize */
            $scope.alertOnResize = function(event, delta, revertFunc, jsEvent, ui, view ){
               $scope.alertMessage = ('Event Resized to make dayDelta ' + delta);
            };
       
            /* add and removes an event source of choice */
            $scope.addRemoveEventSource = function(sources,source) {
              var canAdd = 0;
              angular.forEach(sources,function(value, key){
                if(sources[key] === source){
                  sources.splice(key,1);
                  canAdd = 1;
                }
              });
              if(canAdd === 0){
                sources.push(source);
              }
            };
            
            /* add custom event */
            $scope.addEvent = function() {
              $scope.events.push({
                title: 'Open Sesame',
                start: new Date(y, m, 28),
                end: new Date(y, m, 29),
                className: ['openSesame']
              });
            };
            
            /* remove event */
            $scope.remove = function(index) {
              $scope.events.splice(index,1);
            };
            
            /* Render Tooltip */
//            $scope.eventRender = function( event, element, view ) { 
//            	
//            	     $(element).tooltip({title: event.title});
//            	 
//                $compile(element)($scope);
//            };
            
            /* Change View */
            $scope.changeView = function(view,calendar) {
              uiCalendarConfig.calendars[calendar].fullCalendar('changeView',view);
            };
            /* Change View */
            $scope.renderCalendar = function(calendar) {
              if(uiCalendarConfig.calendars[calendar]){
                uiCalendarConfig.calendars[calendar].fullCalendar('render');
              }
            };
            
            /*
             *  @Author: Tom Scheffer
             *  @param: currentWeek - week in calendar on screen
             *  @param: beginDate - first day of batch
             *  @param: finishDate - last day of batch
             *  Used to calculate week number of batch, assumes batch doesn't have set starting time
             */
            var calculateWeekNumber = function(currentWeek){
            	if(SessionService.get("currentUser").role == 1){
            		var beginDate = moment(SessionService.get("currentUser").batch.startDate);
            		beginDate.weekday(0).add(beginDate.utcOffset(), 'minutes');
            		var finishDate = moment(SessionService.get("currentUser").batch.endDate);
            		finishDate.add(finishDate.utcOffset(), 'minutes');
            		if(currentWeek >= beginDate && currentWeek < finishDate){
            			return currentWeek.diff(SessionService.get("currentUser").batch.startDate, 'weeks') + 1;
            		}
            	}else if(SessionService.get("currentUser").role >= 2 && !SessionService.get("currentBatch") && SessionService.get("trainerBatch")){
            		var beginDate = moment(SessionService.get("trainerBatch").startDate);
            		beginDate.weekday(0).add(beginDate.utcOffset(), 'minutes');
            		var finishDate = moment(SessionService.get("trainerBatch").endDate);
            		finishDate.add(finishDate.utcOffset(), 'minutes');
            		if(currentWeek >= beginDate && currentWeek < finishDate){
            			return currentWeek.diff(SessionService.get("trainerBatch").startDate, 'weeks') + 1;
            		}
            	}else if(SessionService.get("currentUser").role >= 2 && SessionService.get("currentBatch")){
            		var beginDate = moment(SessionService.get("currentBatch").startDate);
            		beginDate.weekday(0).add(beginDate.utcOffset(), 'minutes');
            		var finishDate = moment(SessionService.get("currentBatch").endDate);
            		finishDate.add(finishDate.utcOffset(), 'minutes');
            		if(currentWeek >= beginDate && currentWeek < finishDate){
            			return currentWeek.diff(beginDate, 'weeks') + 1;
            		}
            	}
            	return 0;
            };
            //var now = fullCalendar.moment
            
            if(SessionService.get("currentUser").role == 1 || SessionService.get("currentBatch") != null){
            /* config object */
            $scope.uiConfig = {
              calendar:{
                contentHeight: 'auto',
                editable: false,
                navLinks: true,
                weekNumbers: true,
                weekNumberTitle: "Week in Batch",
                weekNumberCalculation: calculateWeekNumber,
                views:{
                	month:{
                		eventLimit: 5
                	}
                },
                header:{
                  left: 'title',
                  center: 'month,basicWeek,basicDay',
                  right: 'today prev,next'
                },
                eventClick: $scope.alertOnEventClick,
                eventDrop: $scope.alertOnDrop,
                eventResize: $scope.alertOnResize,
//                eventMouseover: $scope.eventRender
              }
            
            };
            }else {
            /* config object */
            $scope.uiConfig = {
              calendar:{
                contentHeight: 'auto',
                editable: true,
                navLinks: true,
                weekNumbers: true,
                weekNumberTitle: "Week in Batch",
                weekNumberCalculation: calculateWeekNumber,
                views:{
                	month:{
                		eventLimit: 5
                	}
                },
                header:{
                  left: 'title',
                  center: 'month,basicWeek,basicDay',
                  right: 'today prev,next'
                },
                eventClick: $scope.alertOnEventClick,
                eventDrop: $scope.alertOnDrop,
                eventResize: $scope.alertOnResize,
                eventMouseover: $scope.eventRender
              		}
            	};
            };
            
            /* event sources array */
//            $scope.eventSources = [$scope.events];
            $scope.sources 			= "";
   			$scope.source 			= "";
	            }
  
  

	         
  ])
    .directive('uiCalendar', ['uiCalendarConfig',
        function (uiCalendarConfig) {

            return {
                restrict : 'A',
                scope : {
                    eventSources : '=ngModel',
                    calendarWatchEvent : '&'
                },
                controller : 'calendarController',
                link : function (scope, elm, attrs, controller) {
                    var sources = scope.eventSources;
                    var sourcesChanged = false;
                    var calendar;
                    var eventSourcesWatcher = controller.changeWatcher(sources, controller.sourceFingerprint);
                    var eventsWatcher = controller.changeWatcher(controller.allEvents, controller.eventFingerprint);
                    var options = null;

                    function getOptions () {
                        var calendarSettings = attrs.uiCalendar ? scope.$parent.$eval(attrs.uiCalendar) : {};
                        var fullCalendarConfig = controller.getFullCalendarConfig(calendarSettings, uiCalendarConfig);
                        var localeFullCalendarConfig = controller.getLocaleConfig(fullCalendarConfig);
                        angular.extend(localeFullCalendarConfig, fullCalendarConfig);
                        options = {
                            eventSources : sources
                        };
                        angular.extend(options, localeFullCalendarConfig);
                        // remove calendars from options
                        options.calendars = null;

                        var options2 = {};
                        for (var o in options) {
                            if (o !== 'eventSources') {
                                options2[o] = options[o];
                            }
                        }
                        return JSON.stringify(options2);
                    }
                    
                    scope.destroyCalendar = function () {
                        if (calendar && calendar.fullCalendar) {
                            calendar.fullCalendar('destroy');
                        }
                        if (attrs.calendar) {
                            calendar = uiCalendarConfig.calendars[attrs.calendar] = angular.element(elm).html('');
                        } else {
                            calendar = angular.element(elm).html('');
                        }
                    };

                    scope.initCalendar = function () {
                        if (!calendar) {
                            calendar = $(elm).html('');
                        }
                        calendar.fullCalendar(options);
                        if (attrs.calendar) {
                            uiCalendarConfig.calendars[attrs.calendar] = calendar;
                        }
                    };

                    scope.$on('$destroy', function () {
                        scope.destroyCalendar();
                    });

                    eventSourcesWatcher.onAdded = function (source) {
                        if (calendar && calendar.fullCalendar) {
                            calendar.fullCalendar(options);
                            if (attrs.calendar) {
                                uiCalendarConfig.calendars[attrs.calendar] = calendar;
                            }
                            calendar.fullCalendar('addEventSource', source);
                            sourcesChanged = true;
                        }
                    };

                    eventSourcesWatcher.onRemoved = function (source) {
                        if (calendar && calendar.fullCalendar) {
                            calendar.fullCalendar('removeEventSource', source);
                            sourcesChanged = true;
                        }
                    };

                    eventSourcesWatcher.onChanged = function () {
                        if (calendar && calendar.fullCalendar) {
                            calendar.fullCalendar('refetchEvents');
                            sourcesChanged = true;
                        }
                    };

                    eventsWatcher.onAdded = function (event) {
                        if (calendar && calendar.fullCalendar) {
                            calendar.fullCalendar('renderEvent', event, !!event.stick);
                        }
                    };

                    eventsWatcher.onRemoved = function (event) {
                        if (calendar && calendar.fullCalendar) {
                            calendar.fullCalendar('removeEvents', event._id);
                        }
                    };

                    eventsWatcher.onChanged = function (event) {
                        if (calendar && calendar.fullCalendar) {
                            var clientEvents = calendar.fullCalendar('clientEvents', event._id);
                            for (var i = 0; i < clientEvents.length; i++) {
                                var clientEvent = clientEvents[i];
                                clientEvent = angular.extend(clientEvent, event);
                                calendar.fullCalendar('updateEvent', clientEvent);
                            }
                        }
                    };

                    eventSourcesWatcher.subscribe(scope);
                    eventsWatcher.subscribe(scope, function () {
                        if (sourcesChanged === true) {
                            sourcesChanged = false;
                            // return false to prevent onAdded/Removed/Changed
							// handlers from firing in this case
                            return false;
                        }
                    });

                    scope.$watch(getOptions, function (newValue, oldValue) {
                        if (newValue !== oldValue) {
                            scope.destroyCalendar();
                            scope.initCalendar();
                        } else if ((newValue && angular.isUndefined(calendar))) {
                            scope.initCalendar();
                        }
                    });
                }
            };
        }
    ]


);
