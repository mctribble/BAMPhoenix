describe('batchEditController', function() {
    beforeEach(module('bam'));
    var mockBatch = {
        id: 20,
        name: 'test',
        startDate: null,
        endDate: null,
        trainer: {
            
        },
        type: {

        }  
    }
    var mockSession = {
        currentBatch:{
            updateDisplay: false,
        }
    }
    

    // beforeEach(function() {
    //     jasmine.Ajax.install();
    // });
    // afterEach(function() {
    //     jasmine.Ajax.uninstall();
    // });
    beforeEach(angular.mock.module({
        'SessionService' : {
            set: function(key, value) {
                mockSession[key] = value;
            },
            get: function(key) {
                return mockSession[key];
            }
        }
    }));
    var $controller;
    beforeEach(inject(function(_$controller_){
        $controller = _$controller_;
    }));
    describe('testing batchEditController', function() {
        it('checking if controller starts', function() {
            var $scope = {};
            var controller = $controller('batchEditController', {$scope: $scope});
            expect($scope.updateDisplay).toBe(mockSession.currentBatch.updateDisplay);
        });
    });
});