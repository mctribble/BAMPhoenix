package com.bam.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bam.bean.Subtopic;
import com.bam.bean.SubtopicStatus;
import com.bam.bean.TopicName;
import com.bam.bean.TopicWeek;
import com.bam.service.SubtopicService;
import com.bam.service.TopicService;

@RestController
@RequestMapping(value = "/api/v1/Calendar/")
public class CalendarController {

  private static final String BATCHID = "batchId";
  private static final String PAGENUMBER = "pageNumber";
  private static final String PAGESIZE = "pageSize";

  @Autowired
  SubtopicService subtopicService;

  @Autowired
  TopicService topicService;

  /**
   * 
   * This uses pagination.
   * Will return a list of subtopics depending on what page and how many
   * per page of subtopics you want. The page number and size is determined
   * by the parameters.
   * 
   * Depending on how the FullCalendar API is setup to take pages of json
   * data, this method may need to change.
   * 
   * @param request
   *          - HttpServletRequest
   * @return
   *         List<Stubtopic>
   * 
   *         Authors: Michael Garza
   *         Gary LaMountain
   * 
   *         note: It will be better to sort by subtopicDate because it will
   *         load the most
   *         recent subtopics. However, since the subtopics have the sames
   *         dates, it's
   *         causing duplications on the calendar.
   *         return subtopicService.findByBatchId(batchId, new
   *         PageRequest(pageNum,pageSiz, Direction.DESC, "subtopicDate"));
   */
  @RequestMapping(value = "SubtopicsPagination", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getSubtopicsByBatchPagination(HttpServletRequest request) {
    int batchId = Integer.parseInt(request.getParameter(BATCHID));
    int pageNum = Integer.parseInt(request.getParameter(PAGENUMBER));
    int pageSiz = Integer.parseInt(request.getParameter(PAGESIZE));

    return subtopicService.findByBatchId(batchId, new PageRequest(pageNum, pageSiz));
  }

  @RequestMapping(value = "Subtopics", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<Subtopic> getSubtopicsByBatch(HttpServletRequest request) {

    // Get the batch id from the request
    int batchId = Integer.parseInt(request.getParameter(BATCHID));

    // Retrieve and return users in a batch from the database

    return subtopicService.getSubtopicByBatchId(batchId);
  }

  /**
   * Counts the number of Subtopics by matching their ids with the batchId.
   * 
   * @param HttpServletRequest
   *          object
   * @return number(Long) of Subtopics
   * 
   * @author Michael Garza, Gary LaMountain
   */
  @RequestMapping(value = "GetNumberOfSubtopics", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public Long getNumberOfSubTopicsByBatch(HttpServletRequest request) {
    int batchId = Integer.parseInt(request.getParameter(BATCHID));

    return subtopicService.getNumberOfSubtopics(batchId);
  }

  @RequestMapping(value = "Topics", method = RequestMethod.GET, produces = "application/json")
  @ResponseBody
  public List<TopicWeek> getTopicsByBatchPag(HttpServletRequest request) {

    // Get the batch id from the request
    int batchId = Integer.parseInt(request.getParameter(BATCHID));

    // Retrieve and return users in a batch from the database
    return topicService.getTopicByBatchId(batchId);
  }

  @RequestMapping(value = "DateUpdate", method = RequestMethod.GET, produces = "application/json")
  public void changeTopicDate(HttpServletRequest request) throws ParseException {
    // Get the batch id from the request
    int subtopicId = Integer.parseInt(request.getParameter("subtopicId"));

    int batchId = Integer.parseInt(request.getParameter(BATCHID));
    List<Subtopic> topics = subtopicService.getSubtopicByBatchId(batchId);
    Subtopic sub;
    for (int i = 0; i < topics.size(); i++) {
      if (topics.get(i).getSubtopicId() == subtopicId) {
        sub = topics.get(i);
        Long newDate = Long.valueOf(request.getParameter("date")) + 46800000;
        sub.setSubtopicDate(new Timestamp(newDate));

        // Update topic in the database
        subtopicService.updateSubtopic(sub);
        break;
      }
    }
  }

  @RequestMapping(value = "StatusUpdate", method = RequestMethod.GET, produces = "application/json")
  public void updateTopicStatus(HttpServletRequest request) throws ParseException {
    // Get the batch id from the request
    int subtopicId = Integer.parseInt(request.getParameter("subtopicId"));

    int batchId = Integer.parseInt(request.getParameter(BATCHID));
    List<Subtopic> topics = subtopicService.getSubtopicByBatchId(batchId);
    Subtopic sub;
    SubtopicStatus status = subtopicService.getStatus(request.getParameter("status"));
    for (int i = 0; i < topics.size(); i++) {
      if (topics.get(i).getSubtopicId() == subtopicId) {
        sub = topics.get(i);
        sub.setStatus(status);
        // Update topic in the database
        subtopicService.updateSubtopic(sub);
        break;
      }
    }
  }

  @RequestMapping(value = "AddTopics", method = RequestMethod.POST, produces = "application/json")
  public void addTopics(@RequestBody String jsonObject, HttpSession session) {
    List<TopicName> topicsFromStub = null;

    ObjectMapper mapper = new ObjectMapper();
    try {

      topicsFromStub = mapper.readValue(jsonObject,
          mapper.getTypeFactory().constructCollectionType(List.class, TopicName.class));
    } catch (IOException e) {
      LogManager.getRootLogger().error(e);
    }
    List<TopicName> allTopicsInBAM = topicService.getTopics();
    for (int i = 0; i < topicsFromStub.size(); i++) {
      boolean found = false;
      for (int j = 0; j < allTopicsInBAM.size(); j++) {
        if (topicsFromStub.get(i).getName().equals(allTopicsInBAM.get(j).getName())) {
          found = true;
          break;
        }
      }
      if (!found) {
        topicService.addOrUpdateTopicName(topicsFromStub.get(i));
      }
    }
  }

}
