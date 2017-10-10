describe('Subtopic Add Controller', function(){
    beforeEach(module('bam'));

    var mockSession = {
        currentBatch:{
            name:"test Batch",
            id: 1
        },
       trainerBatch:{
           name:"test trainer Batch",
           id: 2
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

    var subtopicAdd;
    beforeEach(
        inject(function($controller){
            subtopicAdd = $controller;
        }));

     it('should not be null', function(){
            var $scope = {};
            var controller = subtopicAdd('subTopicController', {$scope:$scope});
            expect(controller).toBeDefined();
     });

     it(': Pool should respond to a 200 response', inject(function($httpBackend){
         var $scope = {};
         var controller = subtopicAdd('subTopicController', {$scope:$scope});
         $scope.getSubtopicPool();
         $httpBackend
            .whenGET('rest/api/v1/Curriculum/TopicPool')
            .respond(function(){
                return {"status":200, "Subtopic":{
                    "subtopicId": "5", 
                    "subtopicName": {
                        "id":"1",
                        "name":"test",
                        "topic": {
                            "id": "1",
                            "name": "test topic"
                        },
                        "type":{
                           "id": "1",
                           "name": "test test" 
                        }
                    },
                    "batch": {
                        "id": "23298",
                        "name": "1611 Sep28 SDET", 
                        "startDate": "28-SEP-17 12.00.00.000000000 AM",
                        "endDate": "28-SEP-17 12.00.00.000000000 AM", 
                        "trainer": {
                            "userId": "11",
                            "fName": "test",
                            "lName": "name",
                            "email": "test@email.com",
                            "pwd": "hashcodePwd",
                            "role": "2",
                            "batch": "23298",
                            "phone": "1232355676",
                            "assignForceID": "2"
                        },
                        "type": "1"
                    },
                    "status":{
                        "id":"1",
                        "name":"tesst"
                    },
                    "subtopicDate": "28-SEP-17 12.00.00.000000000 AM"}
                };
            });
         $httpBackend.flush();
         expect($scope.topic_id).toBe("1");
     }));

});