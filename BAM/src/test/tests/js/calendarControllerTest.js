describe('calendarController', function()
{
    beforeEach(module('bam')); //load the app

    //sample data from BAMPhoenix wiki
    var testTrainer =
        {
            userId: 3,
            fName: "Ryan",
            mName: "R",
            lName: "Lessley",
            email: "rl@revature.com",
            pwd: "*****************************",
            role: 2,
            batch: null,
            phone: "1234567890",
            phone2: "8675309",
            skype: "rl@revature.com",
            pwd2: null,
            assignForceID: 9
        };
    var testBatchType = { id: 1, name: "Java", length: 10 };
    var testBatchCompleted =
        {
            id: 3,
            name: "test-batch-completed",
            startDate: moment().subtract(18, 'weeks'),
            endDate: moment().subtract(6, 'weeks'),
            trainer: testTrainer,
            type: testBatchType
        };
    var testBatchOngoing =
        {
            id: 5,
            name: "test-batch-future",
            startDate: moment().subtract(6, 'weeks'),
            endDate: moment().add(6, 'weeks'),
            trainer: testTrainer,
            type: testBatchType
        };
    var testBatchFuture =
        {
            "id": 18,
            "name": "test-batch-ongoing",
            "startDate": moment().add(6, 'weeks'),
            "endDate": moment().add(18, 'weeks'),
            "trainer": testTrainer,
            "type": testBatchType
        };

    //mock data for the SessionService
    var mockSessionCurrent = {};

    var mockSessionTrainer =
        {
            currentUser:Object.create(testTrainer),
            currentBatch:Object.create(testBatchOngoing)
        };

    var mockSessionAssociateWithoutBatch =
        {
            currentUser:
                {
                    role: 1
                }
        };

    var mockSessionAssociateWithBatch =
        {
            currentUser:
                {
                    role: 1,

                    batch: testBatchOngoing
                }
        };

    //mock data for the subtopic service
    var testSubtopics =
        [
            {},
            {},
            {}
        ];

    //stub methods for the mocked services
    beforeEach(angular.mock.module({
        'SessionService' :
            {
                //these treat objects as arrays, which allow
                set : function(key, value) {
                    mockSessionCurrent[key] = value;
                },
                get : function(key) {
                    return mockSessionCurrent[key];
                },
                unset : function(key) {
                    delete mockSessionCurrent[key];
                }
            },

        'SubtopicService' :
            {
                //the normal function returns a promise, so we have to fake a promise
                getTotalNumberOfSubtopics : function(batchId)
                {
                    return {then: function()
                        { return testSubtopics.length; }
                    }
                }
            }
    }));

    //get the service responsible for instantiating controllers
    var $controller;

    beforeEach(inject(function(_$controller_)
    {
        $controller = _$controller_;
    }));

    //get the location service
    var $location;
    beforeEach(inject(function(_$location_)
    {
        $location = _$location_;
    }));

    //variables used in multiple tests
    var $scope, $rootScope, controller, uiCalendarConfig;

    //perform tests
    describe("test:", function()
    {
        //declare these for every test
        beforeEach(function()
        {
            //use jasmine-ajax
            //(see https://github.com/jasmine/jasmine-ajax)
            jasmine.Ajax.install();

            //reset these objects
            $scope = {};
            $rootScope = {};
            uiCalendarConfig = { calendars : {myCalendar : {fullCalendar : function(){}}}}; //force this function to exist so we can stub it
        });

        //clean up jasmine-ajax
        afterEach(function()
        {
           jasmine.Ajax.uninstall();
        });

        //helper to instantiate in the same manner for every test
        var instantiateController = function()
        {
            controller = $controller('calendarController', {$scope:$scope, $rootScope:$rootScope, uiCalendarConfig:uiCalendarConfig});
        };

        describe("trainer", function()
        {
            //do this for every trainer test
            beforeEach(function()
            {
                mockSessionCurrent = Object.create(mockSessionTrainer);
            });

            it ("should not be redirected", function()
            {
                //immediately fail if location.path is called
                spyOn($location, 'path').and.throwError("fail: user was redirected");

                //instantiate the controller with the above objects
                instantiateController();
            });

            it ("should store the correct id in $rootScope and unset currentBatch", function()
            {
               instantiateController();

               expect($rootScope.changedBatchId).toBe(testBatchOngoing.id);
               expect(!mockSessionCurrent.currentBatch);
            });

            it ("with a batch that starts in the future should store the correct id in $rootScope and leave a copy of the batch in the session and unset currentBatch", function()
            {
               mockSessionCurrent.currentBatch = Object.create(testBatchFuture);

               instantiateController();

               expect($rootScope.changedBatchId).toBe(testBatchFuture.id);
               expect(mockSessionCurrent.futureBatch.id).toBe(testBatchFuture.id);
               expect(!mockSessionCurrent.currentBatch);
            });

            it ("changeDateKeyPress should call changeDate", function()
            {
               instantiateController();

               spyOn($scope, 'changeDate');
               $scope.changeDateKeyPress({which : 13});
               expect($scope.changeDate).toHaveBeenCalled();
            });

            it ("changeDate should change the date and reset the search box", function ()
            {
                instantiateController();

                var searchDate    = new Date(2015, 10, 5);
                var today         = new Date();
                $scope.searchDate = new Date(2015, 10, 5);

                spyOn(uiCalendarConfig.calendars["myCalendar"], "fullCalendar");
                $scope.changeDate();
                expect(uiCalendarConfig.calendars["myCalendar"].fullCalendar).toHaveBeenCalledWith('gotoDate', searchDate);
                expect($scope.searchDate).toEqual(today);
            });

            //alter this test to be more thorough if/when the currentBatch() function is actually used
            it ("unused and incomplete function currentBatch() should not throw exceptions", function()
            {
                instantiateController();
                $scope.currentBatch();
            });

            it ("eventFingerprint should handle dates as well as moments", function()
            {
                instantiateController();

                var testMoment = moment().subtract(1, 'days');
                var testDate = new Date();

                var result = controller.eventFingerprint(
                    {
                        _id : 7,
                        start : testMoment,
                        end : testDate,
                        title : "test event",
                        url : "some/test/url"
                    });

                expect(result).toContain(7);
                expect(result).toContain("test event");
                expect(result).toContain(testMoment.unix());
                expect(result).toContain(moment(testDate).unix());
                expect(result).toContain("some/test/url");
            });

            
        });

        describe("associate", function()
        {
            //do this for every associate test
            beforeEach(function()
            {
                mockSessionCurrent = mockSessionAssociateWithBatch;
            });

            it ("without batch should be redirected to '/noBatch'", function()
            {
                mockSessionCurrent = mockSessionAssociateWithoutBatch; //run as associate without a batch

                //raise a flag when this gets called
                var redirected = false;
                spyOn($location, 'path').and.callFake(function(){redirected = true;});

                //ignore exceptions if the user was redirected
                try
                {
                    instantiateController();
                }
                catch (err)
                {
                    if (!redirected)
                        throw err;
                }

                expect(redirected);
            });

            it ("should not be redirected", function()
            {
                //immediately fail if location.path is called
                spyOn($location, 'path').and.throwError("fail: user was redirected");

                //instantiate the controller with the above objects
                instantiateController();
            });
        });
    });
});