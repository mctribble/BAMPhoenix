describe('Dashboard Controller', function(){
    beforeEach(module('bam'));
    var dashboard;

    var mockSession = {
        currentUser:{
            name:"test User",
            userId:'11'
        }
    }

    beforeEach(angular.mock.module({
        'SessionService' : {
            set : function(key, value) {
                mockSession[key] = value;
            },
            get : function(key) {
                return mockSession[key];
            }
        }
    }));

    beforeEach(
        inject(function($controller){
            dashboard = $controller;
        }));

     it('dashboardController should be defined', function(){
            var $scope = {};
            var controller = dashboard('dashboardController', {$scope:$scope});
            expect(controller).toBeDefined();
     });


     describe('Current Batch:', function(){
        it('Current Batch should respond to 200 response', inject(function($httpBackend){
            var jsonData = {
                "id": "23298",
                "name": "1611 Sep28 SDET", 
                "startDate": "28-SEP-17 12.00.00.000000000 AM",
                "endDate": "28-SEP-17 12.00.00.000000000 AM", 
                "trainer": {
                    "userId": "11",
                    "fName": "test",
                    "lName": "name",
                    "email": "test@email.com",
                    "pwd": "hashcodePwd",
                    "role": "2",
                    "batch": "23298",
                    "phone": "1232355676",
                    "assignForceID": "2"
                },
                "type": "1"
            };
            var $scope = {};
            var controller = dashboard('dashboardController', {$scope:$scope});
            $scope.currentBatch();
            $httpBackend
                .whenGET('rest/api/v1/Batches/All')
                .respond(200, jsonData);
            $httpBackend.flush();
            expect($scope.batchCount).not.toBeCalled(0);
        }));
        it('Current Batch should respond to a 400 response', inject(function($httpBackend){
            var $scope = {};
            var controller = dashboard('dashboardController', {$scope:$scope});
            $scope.currentBatch();
            $httpBackend
                .whenGET('rest/api/v1/Batches/All')
                .respond(400);
            $httpBackend.flush();
            expect($scope.currentBatch()).toBe(null);    
         }));
     });
});