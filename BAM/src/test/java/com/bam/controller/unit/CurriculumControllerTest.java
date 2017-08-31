package com.bam.controller.unit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.bam.bean.Curriculum;
import com.bam.controller.CurriculumController;
import com.bam.service.CurriculumService;
import com.bam.service.CurriculumSubtopicService;
import com.bam.service.SubtopicService;

public class CurriculumControllerTest {

	@Mock
	CurriculumService curriculumService;
	
	@Mock
	CurriculumSubtopicService curriculumSubtopicService;
	
	@Mock
	SubtopicService subtopicService;
	
	@InjectMocks
	CurriculumController curriculumController;
	
	List<Curriculum> curriculumList = null;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(new CurriculumController(curriculumService, curriculumSubtopicService, subtopicService)).build();
	}
	
	/***
	 * @author Nam Mai
	 * These following methods test if the endpoint is reached correctly and json is returned.
	 */
	@Test
	public void getAllCurriculumTest() throws Exception{
		mockMvc.perform(get("/api/v1/Curriculum/All")).andExpect(status().isOk()).andExpect(content().contentType("application/json"));
	}
	
	@Test
	public void getTopicPoolTest() throws Exception{
		mockMvc.perform(get("/api/v1/Curriculum/TopicPool")).andExpect(status().isOk()).andExpect(content().contentType("application/json"));
	}
	
	@Test
	public void getSubtopicPoolTest() throws Exception{
		mockMvc.perform(get("/api/v1/Curriculum/SubtopicPool")).andExpect(status().isOk()).andExpect(content().contentType("application/json"));
	}

	/***
	 * @author Nam Mai
	 * Note: id can be any number; only have to verify the endpoint is reached correctly
	 */
	@Test
	public void getScheduleTest() throws Exception{
		mockMvc.perform(get("/api/v1/Curriculum/Schedule?curriculumId=" + 1)).andExpect(status().isOk());
	}
	
}
