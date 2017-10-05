describe('curriculumController', function () {
    beforeEach(angular.mock.module('bam'));

    var $controller;
    
    beforeEach(angular.mock.inject(function(_$controller_){
      $controller = _$controller_;
    }));

    describe('sanitizeString', function () {
        it ('it should replace spaces with underscores in the string', function(){
            var $scope = {};
            var controller = $controller('curriculumController', {$scope: $scope });
            var string = $scope.sanitizeString("Hello World")
            expect(string).toBe("Hello_World")

        });


        it ('it should delete anything that is not a letter or number', function(){
            var $scope = {};
            var controller = $controller('curriculumController', {$scope: $scope });
            var string = $scope.sanitizeString("Hello&World")
            expect(string).toBe("HelloWorld")

        });
    })

    describe('get date', function () {
        it ('it should return the date', function(){
            var $scope = {};
            var controller = $controller('curriculumController', {$scope: $scope });
            var date = $scope.getDate();
            expect(date).toBe("Hello_World")

        });


    })
    
    
    describe('get topic color', function () {
        it ('it should return the topic color', function(){
            var $scope = {};
            topics = [
                {
                    name: "Java"
                }
            ];
            var controller = $controller('curriculumController', {$scope: $scope });
            $scope.setTopicColors(topics);
            var color = $scope.getTopicColor("Java");
            
            expect(color).toBe("Hello_World")
// this test will always fail because it gives a random color
        });

        


    })



})