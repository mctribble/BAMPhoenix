/**
 * subtopicService.js
 * 
 * For Subtopic Service needs.
 * 
 * @author Michael Garza, Gary LaMountain
 */

app.factory('SubtopicService', function($http){
	
	
	/**
	 * Gets the number of Subtopics by matching it with the batchId
	 * 
	 * @author Michael Garza, Gary LaMountain
	 */
	var getTotalNumberOfSubtopics = function(id){
		return $http.get('rest/api/v1/Calendar/GetNumberOfSubtopics?batchId='+ id).then(function(response){
			return response.data;
		});
	}
		
	return{ getTotalNumberOfSubtopics: getTotalNumberOfSubtopics}
});