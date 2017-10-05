describe("Dashboard Controller", function(){
    var $http, scope, $analytics, SessionService, rootScope, dashboard;

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

        inject(function($rootScope, $controller, _$http_, _$analytics_, _$rootScope_){
        $http = _$http_;
        $analytics = _$analytics_;
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
            expect(SessionService.get).toHaveBeenCalled();
            expect(SessionService.set).toHaveBeenCalled();
        });

//    it(': get data function should be inhabited and output the correct batch', function(){
//        expect(scope.getData).not.toBe('N/A');
//        SessionService.set("user", "jaydeep");
//         expect(scope.getData.message).toBe('You have no current batches');
//
//    });
});