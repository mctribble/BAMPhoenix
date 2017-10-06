describe("Dashboard Controller", function(){
    beforeEach(module('bam'));
    var dashboard;

    beforeEach(function(){
        // module(function($provide){
        //     $provide.service('SessionService', function(){
        //         $provide.service( function(){
        //             this.set= jasmine.createSpy('set');
        //         });

        //     });

        //Mocked Session Service
        SessionService = jasmine.createSpyObj('SessionService', [
            'get',
            'set',
            'unset',
            'remove'
        ]);

//        module(function($provide){
//            $provide.value('SessionService', SessionService);
//        });


        inject(function($controller){
            dashboard = $controller;

        });
    });

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