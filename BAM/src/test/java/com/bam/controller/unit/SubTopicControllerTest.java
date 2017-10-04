package com.bam.controller.unit;

import com.bam.bean.Subtopic;
import com.bam.controller.SubTopicController;
import com.bam.service.SubtopicService;
import com.bam.service.TopicService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubTopicControllerTest {
    @Mock
    TopicService mockTopicService;

    @Mock
    SubtopicService mockSubtopicService;

    @InjectMocks
    SubTopicController mockSubtopicController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mockSubtopicController).build();
    }

    @Test
    public void addSubtopicNameTest() throws Exception {
        mockMvc.perform(post("/api/v1/Subtopic/Add").param("typeId", "1")
                .param("topicId", "1").param("subtopicName", ""))
                .andExpect(status().isOk());
    }

    @Test
    public void addSubtopicNameBreakTest() throws Exception{
        mockMvc.perform(post("/api/v1/Subtopic/Add").param("typeId", "e")
                .param("topicId", "1").param("subtopicName", ""))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void addSubtopicNameBreakTest2() throws Exception{
        mockMvc.perform(post("/api/v1/Subtopic/Add").param("typeId", "1")
                .param("topicId", "e").param("subtopicName", ""))
                .andExpect(status().is4xxClientError());
    }

}
