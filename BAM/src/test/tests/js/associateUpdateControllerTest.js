beforeEach(module('bam'));
describe('associateUpdateControllerTest', function() {
    var $controller;

    beforeEach(inject(function(_$controller_) {
        $controller = _$controller_;
    }));

    describe('updateAssociate', function() {
        it('updateAssociate responding to 200', inject(function($httpBackend) {
            var $scope = {};
            var controller = $controller('associateUpdateController', {$scope:$scope});

            $scope.updateAssociate();
            $httpBackend
                .when('POST', 'rest/api/v1/Users/Update')
                .respond(200);
            $httpBackend.flush();
            expect($scope.updateDisplay).toBe(true);
            expect($scope.updateMsg).toBe('Update Successful');
            expect($scope.alertClass).toBe('alert alert-success');
        }));
        it('updateAssociate responding to 400', inject(function($httpBackend) {
            var $scope = {};
            var controller = $controller('associateUpdateController', {$scope:$scope});

            $scope.updateAssociate();
            $httpBackend
                .when('POST', 'rest/api/v1/Users/Update')
                .respond(400);
            $httpBackend.flush();
            expect($scope.updateDisplay).toBe(true);
            expect($scope.updateMsg).toBe('Update Failed');
            expect($scope.alertClass).toBe('alert alert-danger');
        }));
        it('updateAssociate responding to 500', inject(function($httpBackend) {
             var $scope = {};
             var controller = $controller('associateUpdateController', {$scope:$scope});

             $scope.updateAssociate();
             $httpBackend
                .when('POST', 'rest/api/v1/Users/Update')
                .respond(500);
             $httpBackend.flush();
             expect($scope.updateDisplay).toBe(true);
             expect($scope.updateMsg).toBe('Update Failed');
             expect($scope.alertClass).toBe('alert alert-danger');
        }));
    });
});