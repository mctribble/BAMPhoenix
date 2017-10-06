describe('associateViewController', function() {
    beforeEach(module('bam')); //load the app

    //mock data for the SessionService
    var mockSession = {
        currentBatch:{
            name:"test batch",
            id:1
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

    var $controller;

    beforeEach(inject(function(_$controller_) {
        $controller = _$controller_;
    }));

    describe('testing the view', function() {
        it('name should be loaded', function() {
            var $scope = {};
            var controller = $controller('associateViewController', {$scope:$scope});

            expect($scope.currentBatchName).toBe(mockSession.currentBatch.name);
        })
    });

});