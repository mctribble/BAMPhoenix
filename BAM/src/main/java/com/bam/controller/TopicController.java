package com.bam.controller;

import com.bam.bean.TopicName;
import com.bam.service.TopicService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/rest/api/v1/Topic/")
@Api(value="catalog", description = "Operations about topics", tags="Topic")
public class TopicController {
  @Autowired
  TopicService topicService;

  @RequestMapping(value = "Add", method = RequestMethod.POST)
  @ApiOperation(value = "Add a new topic to the system")
  public ResponseEntity addTopicName(HttpServletRequest request) {
    if(validTopicName(request.getParameter("name"))) {
      TopicName topic = new TopicName();
      topic.setName(request.getParameter("name"));
      topicService.addOrUpdateTopicName(topic);
      return ResponseEntity.status(HttpStatus.OK).body(null);
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
  }

  private boolean validTopicName(String topicInput) {

    boolean emptyString = true;
    boolean containsOnlyWhiteSpace = true;

    for(int counter = 0; counter < topicInput.length(); counter++) {
      emptyString = false;
      if(!topicInput.substring(counter,counter+1).equals(" ")) {
        containsOnlyWhiteSpace = false;
      }
    }
    if(emptyString || containsOnlyWhiteSpace) {
      return false;
    }
    return true;
  }

}
