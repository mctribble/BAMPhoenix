package com.bam.controller.unit;

import java.util.ArrayList;
import java.util.List;

import com.bam.bean.*;
import com.bam.service.TopicService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bam.controller.CalendarController;
import com.bam.service.SubtopicService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class CalendarControllerTest {

	@Mock
	SubtopicService subtopicService;

	@Mock
	TopicService testTopicService;

	@InjectMocks
	CalendarController calendarController;

	/**
	 * Declaration of variables.
	 */
	private MockMvc mockMvc;
	private Subtopic testSubtopic1 = new Subtopic();
	private Subtopic testSubtopic2 = new Subtopic();
	private TopicName testTopicName = new TopicName();
	private TopicName testTopicName2 = new TopicName();
	private TopicWeek testTopicWeek = new TopicWeek();
	private TopicWeek testTopicWeek2 = new TopicWeek();
	private SubtopicStatus testSubtopicStatus = new SubtopicStatus();
	private List<Subtopic> testList = new ArrayList<>();
	private List<TopicWeek> testWeekList = new ArrayList<>();
	private List<TopicName> testNameList = new ArrayList<>();
	private Batch testBatch = new Batch();

	/**
	 * Initialization of variables.
	 */
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		this.setMockMvc(MockMvcBuilders.standaloneSetup(calendarController).build());
		testBatch.setName("batch1");
		testBatch.setId(1);
		testBatch.setTrainer(new BamUser());
		testList.add(testSubtopic1);
		testList.add(testSubtopic2);
		testWeekList.add(testTopicWeek);
		testWeekList.add(testTopicWeek2);
		testNameList.add(testTopicName);
		testNameList.add(testTopicName2);
	}

	/**
	 * Testing of the Calendar Controller - getSubtopicsByBatchPagination()
	 * Checks to see if method returns an empty list and it's not null
	 *
	 * @author Michael Garza
	 */
	@Test
	public void getSubtopicsByBatchPaginationTest(){
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.addParameter("batchId", "5");
		req.addParameter("pageNumber", "0");
		req.addParameter("pageSize", "20");

		List<Subtopic> returnResults = (List<Subtopic>) calendarController.getSubtopicsByBatchPagination(req);
		assertNotNull(returnResults);
		assertEquals(returnResults, new ArrayList<Subtopic>());
	}

	/**
	 * Checks if the request mapping, /rest/api/v1/Calendar/Subtopics, works
	 * and returns a list of subtopics based on the batchId.
	 * @throws Exception
	 */
	@Test
	public void getSubtopicsByBatchTest() throws Exception {
		when(subtopicService.getSubtopicByBatchId(1)).thenReturn(testList);
		MvcResult result = mockMvc.perform(get("/rest/api/v1/Calendar/Subtopics")
				.param("batchId","1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8")).andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println(content);
	}



	/**
	 * Testing of the Calendar Controller - getNumberOfSubTopicsByBatch()
	 * Checks to see if method returns an empty list and it's not null
	 *
	 * @author Michael Garza
	 */
	@Test
	public void getNumberOfSubTopicsByBatchTest(){
		MockHttpServletRequest req = new MockHttpServletRequest();
		req.addParameter("batchId", "5");
		
		Long returnValue = (Long) calendarController.getNumberOfSubTopicsByBatch(req);

		assertEquals(returnValue.longValue(), 0);
		assertNotNull(returnValue.longValue());
	}

	/**
	 * Checks if the request mapping, /rest/api/v1/Calendar/Topics, works
	 * and returns a list of topics based on the batchId.
	 * @throws Exception
	 */
	@Test
	public void getTopicsByBatchPagTest() throws Exception {
		when(testTopicService.getTopicByBatchId(1)).thenReturn(testWeekList);
		MvcResult result = mockMvc.perform(get("/rest/api/v1/Calendar/Topics")
				.param("batchId","1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8")).andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println(content);
	}

	/**
	 * Checks if the request mapping, /rest/api/v1/Calendar/DateUpdate, works
	 * and that the information is passed through the method correctly.
	 * @throws Exception
	 */
	@Test
	public void changeTopicDateTest() throws Exception {
		when(subtopicService.getSubtopicByBatchId(1)).thenReturn(testList);
		doNothing().when(subtopicService).updateSubtopic(testSubtopic1);
		mockMvc.perform(get("/rest/api/v1/Calendar/DateUpdate")
				.param("subtopicId","1").param("batchId", "1")
				.param("date", "2000")
				.contentType("application/json;charset=UTF-8")).andExpect(status().isOk());
	}

	/**
	 * Checks if the request mapping, /rest/api/v1/Calendar/StatusUpdate, works
	 * and checks if the information is passed correctly through
	 * the method.
	 * @throws Exception
	 */
	@Test
	public void updateTopicStatusTest() throws Exception {
		when(subtopicService.getSubtopicByBatchId(1)).thenReturn(testList);
		when(subtopicService.getStatus("Pending")).thenReturn(testSubtopicStatus);
		mockMvc.perform(get("/rest/api/v1/Calendar/StatusUpdate")
				.param("subtopicId","1").param("batchId", "1")
				.param("status", "Pending")
				.contentType("application/json;charset=UTF-8")).andExpect(status().isOk());
	}

	/**
	 * Returns the mockMvc variable.
	 * @return
	 */
	public MockMvc getMockMvc() {
		return mockMvc;
	}

	/**
	 * Sets the mockMvc variable.
	 * @param mockMvc
	 */
	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}
}
