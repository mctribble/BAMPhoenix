beforeEach(module('bam'));
beforeEach(angular.mock.module({
    'SessionService' : {
        set: function() {
        },
        get : function() {
            return null;
        }
    }
}));
describe('associateViewControllerTest', function() {
    var $controller;
    beforeEach(inject(function($_controller_) {
        $controller = $_controller_;
    }));
    describe('testing the view', function() {
        it('name should be loaded', function() {
            var $scope = {};
            var controller = $controller('associateViewController', {$scope:$scope});
        })
    });
});