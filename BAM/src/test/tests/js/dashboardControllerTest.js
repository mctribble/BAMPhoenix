describe("Dashboard Controller", function(){
    var scope, SessionService, rootScope, dashboard;

    beforeEach(function(){
        // module(function($provide){
        //     $provide.service('SessionService', function(){
        //         $provide.service( function(){
        //             this.set= jasmine.createSpy('set');
        //         });

        //     });
        SessionService = jasmine.createSpyObj('SessionService', [
            'get',
            'set',
            'unset',
            'remove'
        ]);

//        module(function($provide){
//            $provide.value('SessionService', SessionService);
//        });

        inject(function($rootScope, $controller, _$rootScope_){
        scope = $rootScope.$new();
        rootScope = _$rootScope_;
            dashboard = function(){
                return $controller('dashboardController', {
                '$scope': scope
                });
            };
        });
    });

     it('should not be null', function(){
            var controller = dashboard();
            expect(controller).toBeDefined();
     });

     it(': Current Batch should reponde to 200 response', inject(function($httpBackend){
        var controller = dashboard();
        scope.currentBatch();
        $httpBackend
        .whenGET('rest/api/v1/Batches/All')
        .respond(function(){
            return [200,[{id: '23298'}, {name: '1611 Sep28 SDET'}, {startDate: '28-SEP-17 12.00.00.000000000 AM'},
                         {endDate: '28-SEP-17 12.00.00.000000000 AM'}, {trainer: {userId: '11'}}, {type: '1'}]];
        });
        $httpBackend.flush();
        expect(scope.getData).toBeCalled();
     }));

//    it(': get data function should be inhabited and output the correct batch', function(){
//        expect(scope.getData).not.toBe('N/A');
//        SessionService.set("user", "jaydeep");
//         expect(scope.getData.message).toBe('You have no current batches');
//
//    });
});