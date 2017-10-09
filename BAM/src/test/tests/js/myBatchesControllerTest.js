describe('myBatchesControllerTest', function() {
    beforeEach(module('bam'));

    var mockSession = {
        currentUser: {
            email: 'theara_ya@my.uri.edu'
        }
    };

    beforeEach(angular.mock.module({
        'SessionService' : {
            get : function(key) {
                return eval("mockSession." + key);
            }
        }
    }));

    var $controller;

    beforeEach(inject(function(_$controller_) {
        $controller = _$controller_;
    }));

    describe('getmyBatches', function() {
        it('getmyBatches responding to 200', inject(function($httpBackend) {
            var $scope = {};
            var controller = $controller('myBatchesController', {$scope:$scope});

            $scope.getmyBatches();
            $httpBackend
                .when('GET', 'rest/api/v1/Batches/AllInProgress')
                .respond(400);
            $httpBackend.flush();
            expect($scope.message).toBe(true);
            expect($scope.msg).toBe('my current batches retrieved');
        }));
    });
});