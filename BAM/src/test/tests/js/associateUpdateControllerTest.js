describe('associateUpdateControllerTest', function() {
    beforeEach(module('bam'));

    var $controller;
    beforeEach(inject(function(_$controller_) {
        $controller = _$controller_;
    }));

    describe('test some function', function() {
        it('this should produce this', function() {
            var $scope = {};
            var controller = $controller('associateUpdateController', {$scope:$scope});
            //now we can call the controller's scopes
//            $scope.someFunction();
            //perform the assertion test
//            expect("result").toBe("expected");
        });
    });
})