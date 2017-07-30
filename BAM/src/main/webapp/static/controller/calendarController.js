/*
*  AngularJs Fullcalendar Wrapper for the JQuery FullCalendar
*  API @ http://arshaw.com/fullcalendar/
*
*  Angular Calendar Directive that takes in the [eventSources] nested array object as the ng-model and watches it deeply changes.
*       Can also take in multiple event urls as a source object(s) and feed the events per view.
*       The calendar will watch any eventSource array and update itself when a change is made.
*
*/


  var app =  app.constant('uiCalendarConfig', {	//Angular ui-Calendar API used to create 
	  //AngularJS calendar.
        calendars : {}
    })
 
app.controller('uiCalendarCtrl',
   function($scope, $compile, $timeout, uiCalendarConfig, $http) {
    
	$scope.eventsF = function (start, end, timezone, callback) {
		var s = new Date(start).getTime() / 1000;
		var e = new Date(end).getTime() / 1000;
		var m = new Date(start).getMonth();
		var events = [{title: 'Feed Me ' + m,start: s + (50000),end: s + (100000),allDay: false, className: ['customFeed']}];
		callback(events);
		};

		var date = new Date();

		/*day ex 16*/
		var d = date.getDate();
		console.log("date: " + d);

		/*month ex 11*/
		var m = date.getMonth();
		console.log("month: " + m);

		/*year ex 2016*/
		var y = date.getFullYear();
		console.log("year: " + y);

		/* events that appear on calendar */
		/* event source that contains custom events on the scope */
		$scope.events = [];

		$http({
		method : "GET",
		url : "Calendar/Subtopics.do" //"getBatchTemplate.do"
		}).then(function successCallback(response) {
		console.log("getBatchTemplate2.do called successfully: " + response.data);


		/*  $http.post("getsubjectlist.do")
		.success(function(data, status, headers, config) {
		alert("start: data - topic - coverage - start - end");
			alert(data[0])
				alert(data[0].topic);
				alert(data[0].coverage);
				alert(data[0].start);
				alert(data[0].end);
			alert("end");
		}).error(function(data, status, headers, config) {
			$scope.status = status;	
		}); */

		var newEvents = response.data;

		////////////////////////////////////////////get subject coverages///////////in progress
		/* var data = $.param({});

		var config = {
		headers : {
			'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
		}
		} */
		/*  $http.post("getSubjectCoverage.do", data, config)
		.success(function(data, status, headers, config) {
				$scope.coverages = data; */
				
					
//			}).error(function(data, status, headers, config) {
//				$scope.status = status;	
		//});
		/////////////////////////////////////////////////////////////////////////////
		for(var i = 0; i < newEvents.length ; i++) {
		console.log("adding new data: " + newEvents[i]);  
		var newSubj;
		//for(var j = 0; j < newEvents[i].length; j++){
			
			var sub = newEvents[i];//.subjects[j];
			//for(var x=0; x < sub.length; x++){
				var baseStart = newEvents[i].start;
				var baseEnd = newEvents[i].end;
				baseStart = new Date(baseStart);
				baseStart.setDate(baseStart.getDate());//+j);
				baseStart.setHours(5);
				baseEnd = new Date(baseStart);
				baseEnd.setHours(23);
				newSubj = {"title":sub.topic/* newEvents[i].title +" - "+sub[x]*/,"length":"1","start":baseStart,"end":baseEnd,"stick":"true"/* , "subject":sub[x] */};
				
				var newSubjCoverage = false;
				
				//for(var xx = 0; xx < $scope.coverages.length; xx++){
				//	if(newSubj.topic/* .subject */ == $scope.coverages[xx].topic){
				//		newSubjCoverage = $scope.coverages[xx].coverage;
				//	}
				//}
				newSubjCoverage = sub.coverage;
				
				/* if(newSubj.subject == "subject 3"){ 
					newSubjCoverage = true;
				} */
				if(newSubjCoverage == false){
					newSubj.className = "uncovered";
					if(newSubj.end.getTime() < Date.now()){
						newSubj.className = "late";
					}
				} else if(newSubjCoverage == true){
					newSubj.className = "covered";
				}	
			
				
				$scope.events.push(newSubj);/////////////////////////////////////////////////////
				//$('#calandar').fullCalendar('refetchEvents');
				
				////////////////////////////////////////////////////////////////////////////////////////////////
			
				
			//}
		//}	
		}
		uiConfig.calendar['myCalendar'].fullcalendar('removeEventSources');
		uiConfig.calendar['myCalendar'].fullcalendar('addEventSource',$scope.events);

		/* }).error(function(data, status, headers, config) {
			$scope.status = status;	
		}); */
		//$scope.renderCalendar('myCalendar1');
		});
		$scope.initElement = function(newSubj){
		var data = $.param({
					topic: newSubj.subject
				});

				var config = {
		    		headers : {
		        		'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
		    		}
				}
				$http.post("SubTopic.do", data, config)
					.success(function(data, status, headers, config) {
							$scope.data = data;
							//alert("data");
							//alert($scope.data == false);
							if(newSubj.subject == "subtopic 3"){
							$scope.data = true;
						}
							if(newSubjCoverage == false){
								newSubj.className = "uncovered";
								if(newSubj.end.getTime() < Date.now()){
									newSubj.className = "late";
								}
							} else if(newSubjCoverage == true){
								newSubj.className = "covered";
							}	
			
				
						$scope.events.push(newSubj);
						$('#calandar').fullCalendar('refetchEvents')
					}).error(function(data, status, headers, config) {
						$scope.status = status;
						$scope.status = false;
						
				});
		};

		/* alert on eventClick */
		$scope.alertOnEventClick = function(event, date, jsEvent, view){
		$scope.alertMessage = (date.title + ' was clicked ');
		if(event.className == "covered"){
		event.className = "uncovered";
		var endDay = new Date(event.end).getTime();
		if(endDay < Date.now()){
			event.className = "late";
		}
		}else{
		event.className = "covered";
		}

		var endDay = new Date(event.end).getTime();
		var data = $.param({
		    topic: event.title/* subject */,
		    enddate: endDay
		});

		var config = {
		    headers : {
		        'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
		    }
		}
		$http.post("subjectcoverage.do", data, config)
			.success(function(data, status, headers, config) {
					$scope.data = data;
			}).error(function(data, status, headers, config) {
				$scope.status = status;
		});
		$('#calandar').fullCalendar('refetchEvents')
		};
		/* alert on Drop */
		$scope.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
		$scope.alertMessage = (event.title + ' Event Dropped to make dayDelta ' + delta);/*=========================================*/
		for(var i = 0; i<$scope.events.length; i++){
		var e = $scope.events[i];

		if(e.title === event.title){

			if(event.className != "covered"){
				event.className = "uncovered";
				var endDay = new Date(event.end).getTime();
				if(endDay < Date.now()){
					event.className = "late";
				}
			}else{
				event.className = "covered";
			}

			   	e.className = event.className;
		   var newDate = new Date(e.start)
		   newDate.setSeconds(newDate.getSeconds() + delta/1000);
		   e.start = newDate;
		   
		   var newDate = new Date(e.end)
		   newDate.setSeconds(newDate.getSeconds() + delta/1000);
		   e.end = newDate;
		   var newDate = new Date(e.start).getTime();
		    var data = $.param({
		    	topic: event.title,
		    	startdate: newDate
			});
			var config = {
		    	headers : {
		        	'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8;'
		    	}
			}
			$http.post("subjectdrag.do", data, config)
				.success(function(data, status, headers, config) {
						$scope.data = data;
				}).error(function(data, status, headers, config) {
					$scope.status = status;
			});
		}
		}
		};
		/* alert on Resize */
		$scope.alertOnResize = function(event, delta, revertFunc, jsEvent, ui, view ){
		$scope.alertMessage = (event.title + 'Event Resized to make dayDelta ' + delta);
		for(i = 0; i<$scope.events.length; i++){
		var e = $scope.events[i];
		if(e.title === event.title){   
		var newDate = new Date(e.end)
		newDate.setSeconds(newDate.getSeconds() + delta/1000);
		e.end = newDate;
		}
		}
		};
		/* remove event */
		$scope.remove = function(index) {
		$scope.events.splice(index,1);
		};
		/* Change View */
		$scope.changeView = function(view,calendar) {
		uiCalendarConfig.calendars[calendar].fullCalendar('changeView',view);
		};
		/* Change View */
		$scope.renderCalendar = function(calendar) {
		$timeout(function() {
		if(uiCalendarConfig.calendars[calendar]){
		uiCalendarConfig.calendars[calendar].fullCalendar('render');
		}
		});
		console.log(calendar + " rendered");
		};
		/* Render Tooltip */
		$scope.eventRender = function( event, element, view ) {
		element.attr({'tooltip': event.title,
		          'tooltip-append-to-body': true});
		$compile(element)($scope);

		};
		/* config object */
		$scope.uiConfig = {
		calendar:{
		height: 450,
		editable: true,

		header:{
		left: 'title',
		center: '',
		right: 'today prev,next'
		},

		eventClick: $scope.alertOnEventClick,
		eventDrop: $scope.alertOnDrop,
		eventResize: $scope.alertOnResize,
		eventRender: $scope.eventRender
		}
		};

		/* event sources array*/
		$scope.eventSources = [$scope.events, $scope.eventsF];
		});
  
  //========================controller for associates=================================
//Controller for associate views
app.controller("associateController",function($rootScope, $scope, $compile, $timeout, uiCalendarConfig, $http){
  	$http({
  		method:"GET",
  		url:"getUserData.do"
  	}).then(function successCallback(response){
  		$scope.user = response.data;
  		$scope.daysCompleted = daysBetween($scope.user.batchId.startDate);
  		$scope.totalDays = $scope.user.batchId.typeId.batchDuration * 7;
  		$scope.completion = function (){
  			if($scope.daysCompleted > $scope.totalDays){
  				return 100;
  			}else{
  				return Math.round((daysBetween($scope.user.batchId.startDate)/($scope.user.batchId.typeId.batchDuration * 7))*100);
  			}
  		}
  		if($scope.users.userType == "ROLE_1"){
  			$rootScope.isAssociate=true;
  			console.log("associate")
  		}else{
  			$rootScope.isAssociate=false;
  		}
  	}, function failureCallback(response){
  		console.log("failed to fetch user data")
  	});
  	
  	$http({
  		method:"GET",
  		url:"fetchAssociateEvals.do"
  	}).then(function successCallback(response){
  		$scope.evaluations = response.data;
  		var evals = $scope.evaluations;
  		$scope.getAverage = function(){
  			var average = 0;
  			var count = 0;
  			var evals = $scope.evaluations;
  			for(var week in evals){
  				if(evals.hasOwnProperty(week)){
  					for(i=0;i<evals[week].length;i++){
  						if(evals[week][i].evalState == "Completed"){
  							average = average + Math.round((evals[week][i].score/evals[week][i].taskId.maxScore)*100);
  							count = count + 1;
  						}
  					}
  				}
  			}
  			if(average == 0){
  				return 0;
  			}else{
  				average = average/count;
  				return average;
  			}
  		};
  	},function failureCallback(response){
  		
  	});
  	
  	$scope.setEvalId = function(evalId){
  		$scope.evalId = evalId;
  	};
  	
  	$scope.completeTask = function(){
  		$http({
  			method:"POST",
  			url:"submitTask.do",
  			params:{evalId:$scope.evalId,password:$scope.pass}
  		}).then(function successCallback(response){
  			if(response.data.status == "failed"){
  				alert("Validation failed. Please check your password and try again.");
  			}else{
  				var evals = $scope.evaluations;
  				for(var week in evals){
  					if(evals.hasOwnProperty(week)){
  						for(i=0;i<evals[week].length;i++){
  							if(evals[week][i].evaluationId == $scope.evalId){
  								evals[week][i].evalState = response.data.status;
  								$scope.evalId = "";
  								$scope.pass = "";
  							}
  						}
  					}
  				}
  			}
  		},function failureCallback(response){
  			console.log("Failed");
  		})
  	};
  	
  	$scope.getWeeksAverage = function(evals){
  		var average = 0;
  		var count = 0;
  		for(i = 0; i<evals.length;i++){
  			if(evals[i].evalState == "Completed"){
  				average = average + Math.round((evals[i].score/evals[i].taskId.maxScore)*100);
  				count = count + 1;
  			}
  		}
  		if(average == 0){
  			return 0;
  		}else{
  			average = average/count;
  			return average;
  		}
  	};
  	
  	
  	
  	$scope.eventsF = function (start, end, timezone, callback) {
          var s = new Date(start).getTime() / 1000;
          var e = new Date(end).getTime() / 1000;
          var m = new Date(start).getMonth();
          var events = [{title: 'Feed Me ' + m,start: s + (50000),end: s + (100000),allDay: false, className: ['customFeed']}];
          callback(events);
        };
        
      var date = new Date();
      
   /*day ex 16*/
      var d = date.getDate();
      console.log("date: " + d);
      
   /*month ex 11*/
      var m = date.getMonth();
      console.log("month: " + m);
      
   /*year ex 2016*/
      var y = date.getFullYear();
      console.log("year: " + y);
      
  /* events that appear on calendar */
      /* event source that contains custom events on the scope */
      $scope.events = [];
      
      $http({
  		method : "POST",
  		url : "getSchedule.do"
  	}).then(function successCallback(response) {
  		console.log("getSchedule.do called successfully: " + response.data);
  		uiCalendarConfig.calendars['myCalendar1'].fullCalendar('removeEventSources');
  		/* Reason for doing this:
  		 * This will update the current events array instead of creating a new reference, 
  		 * can cause problems */
  		//$scope.events.splice(0, $scope.events.length);
  		
  		var newEvents = response.data;
  		
  		for(var i = 0; i < newEvents.length ; ++i) {
  			console.log("adding new data: " + newEvents[i]);  
  			
  			$scope.events.push(newEvents[i]);
  		}
  		uiCalendarConfig.calendars['myCalendar1'].fullCalendar('addEventSource',$scope.events);
  		//$scope.renderCalendar('myCalendar1');
  	});
      
      /* alert on eventClick */
      $scope.alertOnEventClick = function( date, jsEvent, view){
          $scope.alertMessage = (date.title + ' was clicked ');
      };
      /* alert on Drop */
       $scope.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
         $scope.alertMessage = (event.title + ' Event Dropped to make dayDelta ' + delta);
         for(var i = 0; i<$scope.events.length; i++){
      	   var e = $scope.events[i];
      	   
      	   if(e.title === event.title){
      		   var newDate = new Date(e.start)
      		   newDate.setSeconds(newDate.getSeconds() + delta/1000);
      		   e.start = newDate;
      		   
      		   var newDate = new Date(e.end)
      		   newDate.setSeconds(newDate.getSeconds() + delta/1000);
      		   e.end = newDate;
      	   }
         }
       };
      /* alert on Resize */
      $scope.alertOnResize = function(event, delta, revertFunc, jsEvent, ui, view ){
         $scope.alertMessage = (event.title + 'Event Resized to make dayDelta ' + delta);
         for(i = 0; i<$scope.events.length; i++){
      	   var e = $scope.events[i];
         if(e.title === event.title){   
      	   var newDate = new Date(e.end)
  		   newDate.setSeconds(newDate.getSeconds() + delta/1000);
  		   e.end = newDate;
         }
         }
      };
      /* remove event */
      $scope.remove = function(index) {
        $scope.events.splice(index,1);
      };
      /* Change View */
      $scope.changeView = function(view,calendar) {
        uiCalendarConfig.calendars[calendar].fullCalendar('changeView',view);
      };
      /* Change View */
      $scope.renderCalendar = function(calendar) {
        $timeout(function() {
          if(uiCalendarConfig.calendars[calendar]){
            uiCalendarConfig.calendars[calendar].fullCalendar('render');
          }
        });
        console.log(calendar + " rendered");
      };
       /* Render Tooltip */
      $scope.eventRender = function( event, element, view ) {
          element.attr({'tooltip': event.title,
                        'tooltip-append-to-body': true});
          $compile(element)($scope);
      };
      /* config object - non-interactive monthly view */
      $scope.uiConfig = {
        calendar:{
          height: 450,
          editable: false,

          header:{
            left: 'title',
            center: '',
            right: 'today prev,next'
          },

          eventClick: $scope.alertOnEventClick,
          eventDrop: $scope.alertOnDrop,
          eventResize: $scope.alertOnResize,
          eventRender: $scope.eventRender
        }
      
      };
      
      /* config object - non-interactive weekly view */
      $scope.uiConfig2 = {
      	      calendar:{
      	        height: 150,
      	        editable: false,
      	        defaultView: 'basicWeek',

      	        header:{
      	          left: 'title',
      	          center: '',
      	          right: 'today prev,next'
      	        },

      	        eventClick: $scope.alertOnEventClick,
      	        eventDrop: $scope.alertOnDrop,
      	        eventResize: $scope.alertOnResize,
      	        eventRender: $scope.eventRender
      	      }
      };
      /* event sources array*/
      $scope.eventSources = [$scope.events, $scope.eventsF];
  });
  
  
  //===========================controller to handle scope of user(Associate or Trainer)=======================
 app.controller('mainController', function($window,$scope, $http) {
	    // create a message to display in our view
	    $scope.message = 'Everyone come and see how good I look!';
	    $http({
	    	method:"GET",
	    	url:"getLoggedInType.do"
	    }).then(function successCallback(response){
	    	$scope.type = response.data.authority;
	    	/*if($scope.type == "Associate"){
	    		$window.location.href = "#associate-home";
	    		$scope.title = 'Associate Home';
	    		return;
	    	}
	    	if($scope.type == "Trainer"){
	    		$window.location.href = "#trainer-home";
	    		$scope.title = 'Trainer Home';
	    		return;
	    	}*/
	    	$scope.goHome();
	    },function failureCallback(response){
	    	console.log("could not retrieve user data")
	    });
	    $scope.goHome = function(){
	    	if($scope.type == "Associate"){
	    		$window.location.href = "#associate-home";
	    		$scope.title = 'Associate Home';
	    		console.log("associate")
	    		return;
	    	}
	    	if($scope.type == "Trainer"){
	    		$window.location.href = "#trainer-home";
	    		$scope.title = 'Trainer Home';
	    		console.log("trainer")
	    		return;
	    	}
	    }
	    $scope.logOut = function(){
	    	$scope.type = "";
	    }
	});
  
  
  
//================================================= controller for trainer home page calendar =============================
app.controller("trainerController",function($rootScope, $scope, $compile, $timeout, uiCalendarConfig, $http){
  	/* events that appear on calendar */
      /* event source that contains custom events on the scope */
  	$scope.events = [];
  	$http({
  		method:"GET",
  		url:"getUserData.do"
  	}).then(function successCallback(response){
  		console.log(response.data.batchId.batchId);
  		//$scope.activeBatchId = response.data.batchId.batchId;
  		 $http({
  				method : "GET",
  				url : "getActiveBatch.do",
  				params : {batchId : response.data.batchId.batchId}
  			}).then(function successCallback(response) {
  				console.log("getActiveBatch returned normally");
  				
  				/* Reason for doing this:
  				 * This will update the current events array instead of creating a new reference, 
  				 * can cause problems */
  				//$scope.events.splice(0, $scope.events.length);
  				
  				var newEvents = response.data;
  				
  				for(var i = 0; i < newEvents.length ; ++i) {
  					console.log("adding new data: " + newEvents[i]);  
  					
  					$scope.events.push(newEvents[i]);
  				}
  				
  				uiCalendarConfig.calendars['myCalendar'].fullCalendar('removeEventSources'); 
  				uiCalendarConfig.calendars['myCalendar'].fullCalendar('addEventSource',$scope.events);
  			}, function failureCallback(response){
  				console.log("getActiveBatch unsuccessful " + response.data);
  			});
  	}, function failureCallback(response){
  		console.log("failed to fetch user data")
  	});
  	
  	$scope.eventsF = function (start, end, timezone, callback) {
          var s = new Date(start).getTime() / 1000;
          var e = new Date(end).getTime() / 1000;
          var m = new Date(start).getMonth();
          var events = [{title: 'Feed Me ' + m,start: s + (50000),end: s + (100000),allDay: false, className: ['customFeed']}];
          callback(events);
        };
        
      var date = new Date();
      
   /*day ex 16*/
      var d = date.getDate();
      console.log("date: " + d);
      
   /*month ex 11*/
      var m = date.getMonth();
      console.log("month: " + m);
      
   /*year ex 2016*/
      var y = date.getFullYear();
      console.log("year: " + y);
      
         
      /* alert on eventClick */
      $scope.alertOnEventClick = function( date, jsEvent, view){
          $scope.alertMessage = (date.title + ' was clicked ');
      };
      /* alert on Drop */
       $scope.alertOnDrop = function(event, delta, revertFunc, jsEvent, ui, view){
         $scope.alertMessage = (event.title + ' Event Dropped to make dayDelta ' + delta);
         for(var i = 0; i<$scope.events.length; i++){
      	   var e = $scope.events[i];
      	   
      	   if(e.title === event.title){
      		   var newDate = new Date(e.start)
      		   newDate.setSeconds(newDate.getSeconds() + delta/1000);
      		   e.start = newDate;
      		   
      		   var newDate = new Date(e.end)
      		   newDate.setSeconds(newDate.getSeconds() + delta/1000);
      		   e.end = newDate;
      	   }
         }
       };
      /* alert on Resize */
      $scope.alertOnResize = function(event, delta, revertFunc, jsEvent, ui, view ){
         $scope.alertMessage = (event.title + 'Event Resized to make dayDelta ' + delta);
         for(i = 0; i<$scope.events.length; i++){
      	   var e = $scope.events[i];
         if(e.title === event.title){   
      	   var newDate = new Date(e.end)
  		   newDate.setSeconds(newDate.getSeconds() + delta/1000);
  		   e.end = newDate;
         }
         }
      };
      /* remove event */
      $scope.remove = function(index) {
        $scope.events.splice(index,1);
      };
      /* Change View */
      $scope.changeView = function(view,calendar) {
        uiCalendarConfig.calendars[calendar].fullCalendar('changeView',view);
      };
      /* Change View */
      $scope.renderCalendar = function(calendar) {
        $timeout(function() {
          if(uiCalendarConfig.calendars[calendar]){
            uiCalendarConfig.calendars[calendar].fullCalendar('render');
          }
        });
        console.log(calendar + " rendered");
      };
       /* Render Tooltip */
      $scope.eventRender = function( event, element, view ) {
          element.attr({'tooltip': event.title,
                        'tooltip-append-to-body': true});
          $compile(element)($scope);
      };
      /* config object */
      $scope.uiConfig = {
        calendar:{
          height: 450,
          editable: false,
          draggable: false,
          
          header:{
            left: 'title',
            center: '',
            right: 'today prev,next'
          },

          eventClick: $scope.alertOnEventClick,
          eventDrop: $scope.alertOnDrop,
          eventResize: $scope.alertOnResize,
          eventRender: $scope.eventRender
        }
      };
      
      /* event sources array*/
      $scope.eventSources = [$scope.events, $scope.eventsF];
  });

//===============================trainer confirm controller========================================
//controller for trainer confirming a batch 
	app.controller('confirmBatchCtrl',
		function($scope, $compile, $timeout,uiCalendarConfig, $http,$location) {
		$scope.showCalendar = true;
		$scope.reshowCalenadar=false;
		$scope.ShowLoadingIcon = false;
		$scope.events=[];
		
		//put window title into scope
		$scope.windowTitle = "Confirm Batch";
	
		console.log("calling retreiveCalendar.do");
		$http({
			method : "GET",
			url : "retreiveCalendar.do"
		}).then(function successCallback(response) {
			console.log("retreiveCalendar.do called successfully: "+ response.data);
			$scope.bName = response.data[0];
			$scope.topic = response.data[1];
			$scope.sDate = new Date(response.data[2]);
			
			console.log("New Date: " + $scope.sDate.toString());
			
			$http({
				method : "GET",
				//For testing
				/* url : "getBatchTemplate.do" */
				// From REST service
				//url: "http://35.163.234.219:8080/BAM-REST/Java",
				url:"generateTemplate.do",
				params : { startDate : $scope.sDate }
			}).then(function successCallback(response) {
				console.log("getBatchTemplate2.do called successfully: "+ response.data[0]);
				/* Reason for doing this:
				* This will update the current events array instead of creating a new reference, 
				* can cause problems */
				//$scope.events.splice(0, $scope.events.length);
				var newEvents = response.data;
				for (var i = 0; i < newEvents.length; ++i) {
					//add 5 hours to time(To account for timezone difference)
					newEvents[i].start = newEvents[i].start.substr(0, 12) + '5' + newEvents[i].start.substr(12 + 1);
					
					console.log("adding new data: "+ newEvents[i].start);
					
					$scope.events.push(newEvents[i]);
				}
			});
	
		});
			$scope.eventsF = function(start, end,timezone, callback) {
			var s = new Date(start).getTime() / 1000;
			var e = new Date(end).getTime() / 1000;
			var m = new Date(start).getMonth();
			var events = [ {
				title : 'Feed Me ' + m,
				start : s + (50000),
				end : s + (100000),
				allDay : false,
				className : [ 'customFeed' ]
			} ];
				callback(events);
			};
			/* alert on eventClick */
			$scope.alertOnEventClick = function(date,jsEvent, view) {
				$scope.alertMessage = (date.title + ' was clicked ');
			};
			/* alert on Drop */
			$scope.alertOnDrop = function(event, delta,revertFunc, jsEvent, ui, view) {
				$scope.alertMessage = (event.title + ' Event Dropped to make dayDelta ' + delta);/*=========================================*/
				for (var i = 0; i < $scope.events.length; i++) {
					var e = $scope.events[i];
					if (e.title === event.title) {
						var newDate = new Date(e.start);
						newDate.setSeconds(newDate.getSeconds()+ delta / 1000);
						e.start = newDate;
	
						var newDate = new Date(e.end);
						newDate.setSeconds(newDate.getSeconds()+ delta / 1000);
						e.end = newDate;
					}
				}
			};
			/* alert on Resize */
			$scope.alertOnResize = function(event,delta, revertFunc, jsEvent, ui,view) {
				$scope.alertMessage = (event.title+ 'Event Resized to make dayDelta ' + delta);
				for (i = 0; i < $scope.events.length; i++) {
					var e = $scope.events[i];
					if (e.title === event.title) {
						var newDate = new Date(e.end);
						newDate.setSeconds(newDate.getSeconds()+ delta / 1000);
						e.end = newDate;
					}
				}
			};
			/* remove event */
			$scope.remove = function(index) {
				$scope.events.splice(index, 1);
			};
			/* Change View */
			$scope.changeView = function(view, calendar) {
				uiCalendarConfig.calendars[calendar].fullCalendar('changeView',view);
			};
			/* Change View */
			$scope.renderCalendar = function(calendar) {
				$timeout(function() {
					if (uiCalendarConfig.calendars[calendar]) {
						uiCalendarConfig.calendars[calendar].fullCalendar('render');
					}
				});
				console.log(calendar + " rendered");
			};
			/* Render Tooltip */
			$scope.eventRender = function(event,element, view) {
				element.attr({
					'tooltip' : event.title,
					'tooltip-append-to-body' : true
				});
				$compile(element)($scope);
			};
			/* config object */
			$scope.uiConfig = {
				calendar : {
					height : 450,
					editable : true,
					header : {
							left : 'title',
							center : '',
							right : 'today prev,next'
					},
					eventClick : $scope.alertOnEventClick,
					eventDrop : $scope.alertOnDrop,
					eventResize : $scope.alertOnResize,
					eventRender : $scope.eventRender
				}	
			};
			/* event sources array*/
			$scope.eventSources = [ $scope.events,$scope.eventsF ];
			
			/* on submit button clicked */
			$scope.submit = function() {
				$scope.showCalendar = false;
				$scope.ShowLoadingIcon = true;
				$http({
						method : "POST",
						data : {
							batch : $scope.events
						},
						url : "createBatch.do"
				}).then(function successCallback(response) {
					var result = response.data.result;
					console.log("Create Batch Validation response: "+ result);
					if (result == "VALID") {
						$scope.addDataMessage = "Submission successful";
						$scope.ShowLoadingIcon = false;
					} else {
						$scope.addDataMessage = "Batch Creation failed";
						$scope.reshowCalenadar=true;
						$scope.ShowLoadingIcon = false;
					}
				});
			};
			
			$scope.cancelBatch = function() {
				$location.path("/trainer-home");
			}
			
			$scope.showCalendarBtn = function(){
				$scope.showCalendar = true;
				$scope.reshowCalenadar=false;
			}
		});

function daysBetween(dateSt) {
	  var date1 = new Date(dateSt);
	  var date2 = new Date();
	  
	  //Get 1 day in milliseconds
	  var one_day=1000*60*60*24;

	  // Convert both dates to milliseconds
	  var date1_ms = date1.getTime();
	  var date2_ms = date2.getTime();

	  // Calculate the difference in milliseconds
	  var difference_ms = date2_ms - date1_ms;
	    
	  // Convert back to days and return
	  return Math.round(difference_ms/one_day); 
};


//Login page javascript
var loginApp = angular.module("loginApp", [ "ngRoute" ]);
loginApp.config(function($routeProvider) {
	$routeProvider.when("/login", {
		templateUrl : "loginBody.html",
		controller : "loginCTRL"
	}).when("/register", {
		templateUrl : "register.html",
		controller : "registerCTRL"
	}).otherwise({
		redirectTo : "/login"
	});

});
loginApp.controller("navCTRL", function($scope, $location) {
	$scope.registerSelected = false;
	$scope.loginSelected = true;
	$scope.clearButton = function() {
		$scope.registerSelected = false;
		$scope.loginSelected = false;

	}
});

loginApp.controller("loginCTRL", function($scope, $location){
		var url = $location.absUrl();
		$scope.loginResult = url.includes("?error");
	});

loginApp.controller("registerCTRL",
				function($scope, $http, $location,
						$httpParamSerializerJQLike) {
					$scope.userType = "Associate";
					$scope.email = "";
					$scope.firstname = "";
					$scope.lastname = "";
					$scope.middlename = "";
					$scope.passwd = "";
					$scope.outcome = "";
					$scope.registerSubmitted = false;
					$scope.regSuccess = false;

					$scope.submit = function() {
						//event.preventDefault();
						var data = $httpParamSerializerJQLike({
							'email' : $scope.email,
							'firstname' : $scope.firstname,
							'lastname' : $scope.lastname,
							'middlename' : $scope.middlename,
							'passwd' : $scope.passwd,
							'userType' : $scope.userType
						});
						
						var config = {
							headers : {
								'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8;'
							}
						}

						$http.post('register.do', data, config)
							.then(function(response) {
								$scope.outcome = response.data;
								if ($scope.outcome == "Registration Succeeded!"){
									$scope.regSuccess = true;
								}
								else{
									$scope.regSuccess = false;
								}
								$scope.registerSubmitted = true;
								$scope.userType = "Associate";
								$scope.email = "";
								$scope.firstname = "";
								$scope.lastname = "";
								$scope.middlename = "";
								$scope.passwd = "";
								
							});
					}
				});



