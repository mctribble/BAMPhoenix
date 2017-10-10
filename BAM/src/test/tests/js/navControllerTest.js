describe('navController', function() {
    beforeEach(module('bam'));
    var mockCurrentSession = {};
    var mockUserSession = {
        currentUser:{
            role:1
        },
        changedBatchId:2
    }
    var mockEmptyUserSession = {
    }
    var mockUserRoleSession = {
        userRole:1
    }
    beforeEach(function() {
        mockServiceSession = {
            remove: function(){},
            get: function(){},
            set: function(){}
        };
    }); 
    beforeEach(angular.mock.module({
        'SessionService':{
            set : function(key, value) {
                mockCurrentSession[key] = value;
            },
            get : function(key) {
                return mockCurrentSession[key];
            },
            unset : function(key) {
                delete mockCurrentSession[key];
            }
        }
    }));

    var controllerService;
    var location;
    beforeEach(inject(function($controller, $rootScope, $location){
        $scope = $rootScope.$new();
        root = $rootScope;
        controllerService = $controller;
        location = $location;
    }));

    var instantiateController = function() {
        controllerService('navController', {$scope:$scope, $rootScope:root});
    };

    var instantiateLocation = function() {
        controllerService('navController', {$scope:$scope, $rootScope:root, $location:location});
    };

    var instantiateSession = function() {
        controllerService('navController', {$scope:$scope, $rootScope:root, SessionService:mockServiceSession});
    };

    describe('Testing variables', function() {
        it('testing user variable', function() {
            mockCurrentSession = Object.create(mockUserSession);
            instantiateController();
            expect(root.user.role).toBe(1);
        });
        it('testing userRole variable', function() {
            mockCurrentSession = Object.create(mockUserRoleSession);
            instantiateController();
            expect(root.userRole).toBe(1);
        });
    });
    describe('Testing $on', function() {
        it('testing location with batchesAll', function() {
            mockCurrentSession = Object.create(mockUserSession);
            instantiateLocation();
            spyOn(location, 'path').and.returnValue("/batchesAll");
            $scope.$broadcast('routeChangeStart');
            expect(location.path).toHaveBeenCalledWith("/home");
        });
        it('testing location with batchesFuture', function() {
            mockCurrentSession = Object.create(mockUserSession);
            instantiateLocation();
            spyOn(location, 'path').and.returnValue("/batchesFuture");
            $scope.$broadcast('routeChangeStart');
            expect(location.path).toHaveBeenCalledWith("/home");
        });
        it('testing location with batchesPast', function() {
            mockCurrentSession = Object.create(mockUserSession);
            instantiateLocation();
            spyOn(location, 'path').and.returnValue("/batchesPast");
            $scope.$broadcast('routeChangeStart');
            expect(location.path).toHaveBeenCalledWith("/home");
        });
        it('testing location with register', function() {
            mockCurrentSession = Object.create(mockUserSession);
            instantiateLocation();
            spyOn(location, 'path').and.returnValue("/register");
            $scope.$broadcast('routeChangeStart');
            expect(location.path).toHaveBeenCalledWith("/home");
        });
        it('testing location with associates', function() {
            mockCurrentSession = Object.create(mockUserSession);
            instantiateLocation();
            spyOn(location, 'path').and.returnValue("/associates");
            $scope.$broadcast('routeChangeStart');
            expect(location.path).toHaveBeenCalledWith("/home");
        });
        it('testing location with editBatch', function() {
            mockCurrentSession = Object.create(mockUserSession);
            instantiateLocation();
            spyOn(location, 'path').and.returnValue("/editBatch");
            $scope.$broadcast('routeChangeStart');
            expect(location.path).toHaveBeenCalledWith("/home");
        });
    });
    describe('testing redirect', function() {
        it('testing the redirect', function() {
            mockCurrentSession = Object.create(mockEmptyUserSession);
            instantiateLocation();
            spyOn(location, 'path');
            $scope.redirect();
            expect(location.path).toHaveBeenCalledWith("/");
        });
    });
    describe('testing hideNav', function() {
        it('testing the delete', function() {
            mockCurrentSession = Object.create(mockUserSession);
            instantiateSession();
            spyOn(mockServiceSession, 'remove')();
            $scope.hideNav();
            expect(mockServiceSession.remove).toHaveBeenCalled();
        });
    });
});