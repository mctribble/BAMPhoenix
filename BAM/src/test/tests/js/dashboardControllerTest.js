describe('Dashboard Controller', function(){
    beforeEach(module('bam'));
    var dashboard;

    var mockSession = {
        currentUser:{
            name:"test User",
            userId:11
        }
    }

    beforeEach(angular.mock.module({
        'SessionService' : {
            set: function(key, value) {
                eval("mockSession." + key + " = " + value);
            },
            get : function(key) {
                return eval("mockSession." + key);
            }
        }
    }));

    beforeEach(
        inject(function($controller){
            dashboard = $controller;
        }));

     it('should not be null', function(){
            var $scope = {};
            var controller = dashboard('dashboardController', {$scope:$scope});
            expect(controller).toBeDefined();
     });


     describe('Current Batch:', function(){
        it('Current Batch should reponde to 200 response', inject(function($httpBackend){
            var $scope = {};
            var controller = dashboard('dashboardController', {$scope:$scope});
            $scope.currentBatch();
            $httpBackend
                .whenGET('rest/api/v1/Batches/All')
                .respond(function(){
                    return [200,[{id: '23298'}, {name: '1611 Sep28 SDET'}, {startDate: '28-SEP-17 12.00.00.000000000 AM'},
                         {endDate: '28-SEP-17 12.00.00.000000000 AM'}, {trainer: {userId: '11'}}, {type: '1'}]];
                });
            $httpBackend.flush();
            expect($scope.getData).toBeCalled();
        }));
     });
});