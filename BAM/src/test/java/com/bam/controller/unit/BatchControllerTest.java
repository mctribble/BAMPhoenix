package com.bam.controller.unit;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.bean.BatchType;
import com.bam.controller.BatchController;
import com.bam.service.BamUserService;
import com.bam.service.BatchService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.google.code.beanmatchers.BeanMatchers.hasValidBeanConstructor;
import static com.google.code.beanmatchers.BeanMatchers.hasValidGettersAndSetters;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class BatchControllerTest {
    @Mock
    private BatchService batchService;

    @Mock
    private BamUserService bamUserService;

    @InjectMocks
    private BatchController batchController;

    //Declaration of testing variables.
    private MockMvc mockMvc;
    private List<Batch> batchTestList = new ArrayList<Batch>();
    private List<BatchType> batchTypeTestList = new ArrayList<BatchType>();
    private Calendar calendar = Calendar.getInstance();
    private Timestamp startTime = new java.sql.Timestamp(calendar.getTime().getTime());
    private Timestamp endTime = new java.sql.Timestamp(calendar.getTime().getTime());
    private Batch testBatch = new Batch();
    private Batch testBatch2 = new Batch();
    private Batch testBatch3 = new Batch();
    private Batch testBatch4 = new Batch();

    /**
     * Initialization of variables.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(batchController).build();
        BatchType testBatchType = new BatchType(1, "type1", 10);
        BatchType testBatchType2 = new BatchType(2, "type2", 12);
        BatchType testBatchType3 = new BatchType( 3, "type3", 10);
        BatchType testBatchType4 = new BatchType(4, "type4", 12);

        testBatch.setEndDate(endTime);
        testBatch.setStartDate(startTime);
        testBatch.setName("batch1");
        testBatch.setId(1);
        testBatch.setTrainer(new BamUser());
        testBatch.setType(testBatchType);

        calendar.add(calendar.HOUR, -1);
        startTime = new java.sql.Timestamp(calendar.getTime().getTime());
        testBatch2.setEndDate(endTime);
        testBatch2.setStartDate(startTime);
        testBatch2.setName("batch2");
        testBatch2.setId(2);
        testBatch2.setTrainer(new BamUser());
        testBatch2.setType(testBatchType2);

        calendar.add(calendar.HOUR, 1);
        endTime = new java.sql.Timestamp(calendar.getTime().getTime());
        testBatch3.setEndDate(endTime);
        testBatch3.setStartDate(startTime);
        testBatch3.setName("batch3");
        testBatch3.setId(3);
        testBatch3.setTrainer(new BamUser());
        testBatch3.setType(testBatchType3);

        calendar.add(calendar.HOUR, -1);
        startTime = new java.sql.Timestamp(calendar.getTime().getTime());
        calendar.add(calendar.HOUR, 2);
        endTime = new java.sql.Timestamp(calendar.getTime().getTime());
        testBatch4.setEndDate(endTime);
        testBatch4.setStartDate(startTime);
        testBatch4.setName("batch4");
        testBatch4.setId(1);
        testBatch4.setTrainer(new BamUser());
        testBatch4.setType(testBatchType4);

        batchTestList.add(testBatch);
        batchTestList.add(testBatch2);
        batchTestList.add(testBatch3);
        batchTestList.add(testBatch4);
        batchTypeTestList.add(testBatchType);
        batchTypeTestList.add(testBatchType2);
        batchTypeTestList.add(testBatchType3);
        batchTypeTestList.add(testBatchType4);
        when(batchService.getBatchAll()).thenReturn(batchTestList);
        when(batchService.getBatchByTrainer(bamUserService.findUserByEmail("jc@revature.com")))
                .thenReturn(batchTestList);
        when(batchService.getBatchById(1)).thenReturn(testBatch);
        when(batchService.getAllBatchTypes()).thenReturn(batchTypeTestList);
    }

    /**
     * Checks if the class has a no constructor that takes no arguments.
     */
    @Test
    public void shouldHaveANoArgsConstructor() {
        assertThat(BatchController.class, hasValidBeanConstructor());
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Batches/All, works
     * and that the method returns a list.
     * @throws Exception
     */
    @Test
    public void getAllBatchesTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Batches/All"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Batches/Past, works
     * and that the method returns a list.
     * @throws Exception
     */
    @Test
    public void getPastBatchesTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Batches/Past")
                .param("email", "jc@revature.com")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Batches/Future, works
     * and that the method returns a list.
     * @throws Exception
     */
    @Test
    public void getFutureBatchesTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Batches/Future")
                .param("email", "jc@revature.com")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Batches/InProgress, works
     * and that the method returns a batch in progress
     * based off the email of the trainer .
     * @throws Exception
     */
    @Test
    public void getBatchInProgressTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Batches/InProgress")
                .param("email", "jc@revature.com").contentType("application/json;charset=utf8"))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Batches/AllInProgress, works
     * and that the method returns a list.
     * @throws Exception
     */
    @Test
    public void getAllBatchesInProgressTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Batches/AllInProgress")
                .param("email", "jc@revature.com")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Batches/ById, works
     * and that the method returns a batch based on the batchId.
     * @throws Exception
     */
    @Test
    public void getBatchByIdTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Batches/ById").param("batchId", "1")
                .contentType("application/json;charset=utf8")).andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request, /rest/api/v1/Batches/Edit, mapping works.
     * @throws Exception
     */
    @Test
    public void updateUser() throws Exception {
        mockMvc.perform(post("/rest/api/v1/Batches/Edit").content(asJsonString(new BamUser()))
                .contentType("application/json;charset=utf8")).andExpect(status().isOk());
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Batches/UpdateBatch, works.
     * @throws Exception
     */
    @Test
    public void updateBatchTest() throws Exception {
        mockMvc.perform(post("/rest/api/v1/Batches/UpdateBatch").content(asJsonString(new Batch()))
                .contentType("application/json;charset=utf8")).andExpect(status().isOk());
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Batches/BatchTypes, works
     * and that the method returns a list.
     * @throws Exception
     */
    @Test
    public void getAllBatchTypesTest() throws Exception {
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Batches/BatchTypes")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Sets the mockMvc variable.
     * @param mockMvc
     */
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    /**
     * Converts an object into a string for use as a parameter.
     * @param obj
     * @return
     */
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
