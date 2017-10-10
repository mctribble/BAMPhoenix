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
    var $scope;
    beforeEach(
        inject(function($controller, $rootScope){
            subtopicAdd = $controller;
            $scope = $rootScope.$new(false);
        }));

     it('subtopicController should be define', function(){
            var controller = subtopicAdd('subTopicController', {$scope:$scope});
            expect(controller).toBeDefined();
     });

     it(': Pool should respond to a 200 response', inject(function($httpBackend){
         var jsonData =[ {
            "id": "5", 
            "name":"testName",
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
            "status":{
                "id":"1",
                "name":"tesst"
            },
            "subtopicDate": "28-SEP-17 12.00.00.000000000 AM"},
            {
                "id": "6", 
                "name":"testName2",
                "subtopicName": {
                    "id":"2",
                    "name":"test2",
                    "topic": {
                        "id": "2",
                        "name": "test topic2"
                    },
                    "type":{
                       "id": "2",
                       "name": "test test2" 
                    }
                },
                "status":{
                    "id":"1",
                    "name":"tesst"
                },
                "subtopicDate": "28-SEP-17 12.00.00.000000000 AM"}
        ];
         var controller = subtopicAdd('subTopicController', {$scope:$scope});
         $scope.getSubtopicPool();
         $httpBackend
            .whenGET('rest/api/v1/Curriculum/TopicPool')
            .respond(200, jsonData);
         $scope.$digest();
     }));

});