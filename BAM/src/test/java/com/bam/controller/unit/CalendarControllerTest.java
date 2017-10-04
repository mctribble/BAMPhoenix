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

	@Test
	public void getSubtopicsByBatchTest() throws Exception {
		when(subtopicService.getSubtopicByBatchId(1)).thenReturn(testList);
		MvcResult result = mockMvc.perform(get("/api/v1/Calendar/Subtopics")
				.param("batchId","1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andReturn();
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

	@Test
	public void getTopicsByBatchPagTest() throws Exception {
		when(testTopicService.getTopicByBatchId(1)).thenReturn(testWeekList);
		MvcResult result = mockMvc.perform(get("/api/v1/Calendar/Topics")
				.param("batchId","1")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json")).andReturn();
		String content = result.getResponse().getContentAsString();
		System.out.println(content);
	}

	@Test
	public void changeTopicDateTest() throws Exception {
		when(subtopicService.getSubtopicByBatchId(1)).thenReturn(testList);
		doNothing().when(subtopicService).updateSubtopic(testSubtopic1);
		mockMvc.perform(get("/api/v1/Calendar/DateUpdate")
				.param("subtopicId","1").param("batchId", "1")
				.param("date", "2000")
				.contentType("application/json")).andExpect(status().isOk());
	}

	@Test
	public void updateTopicStatusTest() throws Exception {
		when(subtopicService.getSubtopicByBatchId(1)).thenReturn(testList);
		when(subtopicService.getStatus("Pending")).thenReturn(testSubtopicStatus);
		mockMvc.perform(get("/api/v1/Calendar/StatusUpdate")
				.param("subtopicId","1").param("batchId", "1")
				.param("status", "Pending")
				.contentType("application/json")).andExpect(status().isOk());
	}

//	@Test
//	public void addTopicsTest() throws Exception {
//		when(testTopicService.getTopics()).thenReturn(testNameList);
//		doNothing().when(testTopicService).addOrUpdateTopicName(new TopicName());
//		mockMvc.perform(post("/api/v1/Calendar/AddTopics").content(asJsonString(testNameList))
//				.contentType("application/json")).andExpect(status().isOk());
//	}

	public MockMvc getMockMvc() {
		return mockMvc;
	}

	public void setMockMvc(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	public static String asJsonString(Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonContent = mapper.writeValueAsString(obj);
			return jsonContent;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
