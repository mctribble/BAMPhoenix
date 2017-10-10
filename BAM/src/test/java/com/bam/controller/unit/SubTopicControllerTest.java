package com.bam.controller.unit;

import com.bam.bean.Batch;
import com.bam.bean.Subtopic;
import com.bam.bean.SubtopicStatus;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    /**
     * Declaration of variable.
     */
    private MockMvc mockMvc;
    private List<Subtopic> mockList = new ArrayList<>();
    private Subtopic mockSubTopic = new Subtopic();
    private Subtopic mockSubTopic2 = new Subtopic();
    private Subtopic mockSubTopic3 = new Subtopic();
    private Subtopic mockSubTopic4 = new Subtopic();
    private Batch mockBatch = new Batch();
    private Batch mockBatch2 = new Batch();
    private Batch mockBatch3 = new Batch();
    private Batch mockBatch4 = new Batch();
    private SubtopicStatus mockStatus = new SubtopicStatus(0,"Completed");
    private SubtopicStatus mockStatus2 = new SubtopicStatus(1,"Missed");
    private SubtopicStatus mockStatus3= new SubtopicStatus(2,"Cancelled");
    private SubtopicStatus mockStatus4 = new SubtopicStatus(3,"InProgress");

    /**
     * Initialization of variables.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mockSubtopicController).build();
        mockBatch.setId(0);
        mockBatch2.setId(1);
        mockBatch3.setId(2);
        mockBatch4.setId(3);
        mockSubTopic.setStatus(mockStatus);
        mockSubTopic2.setStatus(mockStatus2);
        mockSubTopic3.setStatus(mockStatus3);
        mockSubTopic4.setStatus(mockStatus4);
        mockSubTopic.setBatch(mockBatch);
        mockSubTopic2.setBatch(mockBatch2);
        mockSubTopic3.setBatch(mockBatch3);
        mockSubTopic4.setBatch(mockBatch4);
        mockList.add(mockSubTopic);
        mockList.add(mockSubTopic2);
        mockList.add(mockSubTopic3);
        mockList.add(mockSubTopic4);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/Add, works.
     * @throws Exception
     */
    @Test
    public void addSubtopicNameTest() throws Exception {
        mockMvc.perform(post("/rest/api/v1/Subtopic/Add").param("typeId", "1")
                .param("topicId", "1").param("subtopicName", ""))
                .andExpect(status().isOk());
    }

    /**
     * Checks if an exception is thrown from an invalid input for the first parse int.
     * @throws Exception
     */
    @Test
    public void addSubtopicNameBreakTest() throws Exception{
        mockMvc.perform(post("/rest/api/v1/Subtopic/Add").param("typeId", "e")
                .param("topicId", "1").param("subtopicName", ""))
                .andExpect(status().is4xxClientError());
    }

    /**
     * Checks if an exception is thrown from an invalid input for the second parse int.
     * @throws Exception
     */
    @Test
    public void addSubtopicNameBreakTest2() throws Exception{
        mockMvc.perform(post("/rest/api/v1/Subtopic/Add").param("typeId", "1")
                .param("topicId", "e").param("subtopicName", ""))
                .andExpect(status().is4xxClientError());
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/All, works
     * and returns a list.
     * @throws Exception
     */
    @Test
    public void getAllSubTopicsTest() throws Exception {
        when(mockSubtopicService.getSubtopics()).thenReturn(mockList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Subtopic/All"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/Completed, works
     * and returns a list.
     * @throws Exception
     */
    @Test
    public void getAllCompletedSubtopicsTest() throws Exception {
        when(mockSubtopicService.getSubtopicsByStatus("Completed")).thenReturn(mockList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Subtopic/AllCompleted"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/Pending, works
     * and returns a list.
     * @throws Exception
     */
    @Test
    public void getAllPendingSubTopicsTest() throws Exception {
        when(mockSubtopicService.getSubtopicsByStatus("Pending")).thenReturn(mockList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Subtopic/AllPending"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/AllMissed, works
     * and returns a list.
     * @throws Exception
     */
    @Test
    public void getAllMissedSubTopicsTest() throws Exception {
        when(mockSubtopicService.getSubtopicsByStatus("Missed")).thenReturn(mockList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Subtopic/AllMissed"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/AllCanceled, works
     * and returns a list.
     * @throws Exception
     */
    @Test
    public void getAllCancelledSubTopics() throws Exception {
        when(mockSubtopicService.getSubtopicsByStatus("Canceled")).thenReturn(mockList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Subtopic/AllCanceled"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/ByBatchId, works
     * and returns a list.
     * @throws Exception
     */
    @Test
    public void getAllSubtopicsByStatusTest() throws Exception {
        when(mockSubtopicService.getSubtopicsByBatchId(0)).thenReturn(mockList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Subtopic/ByBatchId")
                .param("batchId", "0")).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/ByBatchCompleted, works
     * and returns a list.
     * @throws Exception
     */
    @Test
    public void getAllSubtopicsByBatchCompletedTest() throws Exception {
        when(mockSubtopicService.getSubtopicsByBatchAndStatus(0, "Completed")).thenReturn(mockList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Subtopic/ByBatchCompleted")
                .param("batchId", "0").param("status", "Completed"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/ByBatchPending, works
     * and returns a list.
     * @throws Exception
     */
    @Test
    public void getAllSubtopicsByBatchPendingTest() throws Exception {
        when(mockSubtopicService.getSubtopicsByBatchAndStatus(0, "Pending")).thenReturn(mockList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Subtopic/ByBatchPending")
                .param("batchId", "0").param("status", "Pending"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/ByBatchMissed, works
     * and returns a list.
     * @throws Exception
     */
    @Test
    public void getAllSubtopicsByBatchMissedTest() throws Exception {
        when(mockSubtopicService.getSubtopicsByBatchAndStatus(0, "Missed")).thenReturn(mockList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Subtopic/ByBatchMissed")
                .param("batchId", "0").param("status", "Missed"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/ByBatchCanceled, works
     * and returns a list.
     * @throws Exception
     */
    @Test
    public void getAllSubtopicsByBatchCancelledTest() throws Exception {
        when(mockSubtopicService.getSubtopicsByBatchAndStatus(0, "Canceled")).thenReturn(mockList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Subtopic/ByBatchCanceled")
                .param("batchId", "0").param("status", "Canceled"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }
}
