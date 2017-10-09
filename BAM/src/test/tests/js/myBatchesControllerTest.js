describe('myBatchesControllerTest', function() {
    beforeEach(module('bam'));

    var mockSession = {
        currentUser: {
            email: 'theara_ya@my.uri.edu'
        },
        currentBatch: {
            batch : {
                id : '13'
            }
        }
    };

    beforeEach(angular.mock.module({
        'SessionService' : {
            set: function(key, value) {
                mockSession[key] = value;
            },
            get : function(key) {
                return mockSession[key];
            },
            unset : function(key) {
                delete mockSession[key];
            }
        }
    }));

    var $controller;

    beforeEach(inject(function(_$controller_) {
        $controller = _$controller_;
    }));

    describe('getmyBatches', function() {
        var $scope, controller;

        beforeEach(function() {
            $scope = {};
            controller = $controller('myBatchesController', {$scope : $scope});
        });
        it('getmyBatches responding to 200', inject(function($httpBackend) {

            $scope.getmyBatches();

            $httpBackend
                .when('GET', 'rest/api/v1/Batches/AllInProgress')
                .respond(200, 'response');

            expect($scope.message).toBe(true);
        }));
    });
});