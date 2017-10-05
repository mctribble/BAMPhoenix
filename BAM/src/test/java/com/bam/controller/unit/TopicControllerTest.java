package com.bam.controller.unit;

import com.bam.controller.TopicController;
import com.bam.service.TopicService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TopicControllerTest {
    @Mock
    TopicService mockTopicService;

    @InjectMocks
    TopicController mockTopicController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mockTopicController).build();
    }

    @Test
    public void addTopicNameTest() throws Exception {
        mockMvc.perform(post("/rest/api/v1/Topic/Add").param("name", "test"))
                .andExpect(status().isOk());
    }

    @Test
    public void addTopicNameBreakTest() throws Exception {
        mockMvc.perform(post("/rest/api/v1/Topic/Add").param("name", ""))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addTopicNameBreakTest2() throws Exception {
        mockMvc.perform(post("/rest/api/v1/Topic/Add").param("name", "5"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addTopicNameBreakTest3() throws Exception {
        mockMvc.perform(post("/rest/api/v1/Topic/Add").param("name", "            "))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addTopicNameBreakTest4() throws Exception {
        mockMvc.perform(post("/rest/api/v1/Topic/Add").param("name", ":^"))
                .andExpect(status().is4xxClientError());
    }

}
