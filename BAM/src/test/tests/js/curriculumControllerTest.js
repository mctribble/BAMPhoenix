

describe('curriculumController', function () {
    beforeEach(module('bam'));
    var $controller;
    
    var mockSession = {
        trainerBatch:{
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
    
    beforeEach(inject(function(_$controller_){
        $controller = _$controller_;
        spyOn(window, 'confirm').and.returnValue(true);
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
            var today = new Date();
            var dd = today.getDate();
            var mm = today.getMonth() + 1;
            var yyyy = today.getFullYear();
            if (dd < 10) dd = '0' + dd;
            if (mm < 10) mm = '0' + mm;
            var format = mm + '/' + dd + '/' + yyyy;
            expect(date).toBe(format);

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
            
            expect(color).not.toBeNull();
// this test will always fail because it gives a random color
        });

    })

        describe('add a week', function () {
            it ('checks if the curriculumNumber is updated', function(){
                var $scope = {};
                var controller = $controller('curriculumController', {$scope: $scope });
                $scope.displayedCurriculum.meta.curriculumNumberOfWeeks = 0;
                $scope.addWeek();
                
                expect($scope.displayedCurriculum.meta.curriculumNumberOfWeeks).toBe(1);
            });   
        })
        

        describe('delete a week', function () {
            it ('checks if the curriculumNumber is updated', function(){
                var $scope = {};
                var controller = $controller('curriculumController', {$scope: $scope});
                $scope.displayedCurriculum.meta.curriculumNumberOfWeeks = 1;
                
                $scope.deleteWeek(1);
                
                expect($scope.displayedCurriculum.meta.curriculumNumberOfWeeks).toBe(0);
            });   
        })

        
        
        
        
     describe('set master', function () {
        it ('checks if the currriculum master has been updated', inject(function($httpBackend) {
            var $scope = {};
            var controller = $controller('curriculumController', {$scope: $scope});
            var curriculum = {
                meta:{
                    curriculumId : "1",
                    curriculumVersion : "1",
                    isMaster : "false",
                    bid : "1"
                    }
                }
            $scope.setMaster(curriculum);
            $httpBackend
                .expectPOST('rest/api/v1/Curriculum/MakeMaster?curriculumId=1');
                expect(curriculum.meta.isMaster).toBe(true);
        }));   
    })

});