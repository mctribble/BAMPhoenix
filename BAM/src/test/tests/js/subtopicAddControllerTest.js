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
            .whenGET('rest/api/v1/Curriculum/SubtopicPool')
            respond(function(){
                return [200,[{subtopicId: '5'}, {subtopicName: [{name:''}, {topic: {name: ''}}]}, {subtopicDate: '28-SEP-17 12.00.00.000000000 AM'}]];
            });
         $httpBackend.flush();
         expect($scope.message).toBe(true);
     }));


//     describe('getSubtopicPool:', function(){
//        it('Current Batch should respond to 200 response', inject(function($httpBackend){
//            var $scope = {};
//            var controller = dashboard('subtopicAddController', {$scope:$scope});
//            $scope.getSubtopicPool();
//            $httpBackend
//                .whenGET('rest/api/v1/Curriculum/SubtopicPool')
//                .respond(function(){
//                    return [200,[{id: '23298'}, {name: '1611 Sep28 SDET'}, {startDate: '28-SEP-17 12.00.00.000000000 AM'},
//                         {endDate: '28-SEP-17 12.00.00.000000000 AM'}, {trainer: {userId: '11'}}, {type: '1'}]];
//                });
//            $httpBackend.flush();
//            expect($scope.batchCount).not.toBeCalled(0);
//        }));
//     });
});