package com.bam.controller.unit;

import com.bam.bean.BamUser;
import com.bam.bean.Batch;
import com.bam.controller.UserController;
import com.bam.service.BamUserService;
import com.bam.service.BatchService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest {
    @Mock
    BamUserService mockBamUserService;

    @Mock
    BatchService mockBatchService;

    @InjectMocks
    UserController mockUserController;

    /**
     * Declaration of variables.
     */
    private MockMvc mockMvc;
    private List<BamUser> testList = new ArrayList<>();
    private List<BamUser> testTrainerList = new ArrayList<>();
    private List<BamUser> testAssociateList = new ArrayList<>();
    private Batch testBatch = new Batch();
    private BamUser testUser1 = new BamUser(1, "first", "","last", "test1@email.com", "password", 1,
    new Batch(), "1111111111", "1012223333", "", "" ,5);
    private BamUser testUser2 = new BamUser(2, "first", "","last", "test2@email.com", "password", 2,
            new Batch(), "1111111111", "1012223333", "", "" ,15);
    private BamUser testUser3 = new BamUser(3, "first", "","last", "test3@email.com", "password", 1,
            new Batch(), "1111111111", "1012223333", "", "" ,25);
    private BamUser testUser4 = new BamUser(4, "first", "","last", "test3@email.com", "password", 1,
            new Batch(), "1111111111", "1012223333", "", "" ,25);

    /**
     * Initialization of variables.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mockUserController).build();
        testBatch.setName("batch1");
        testBatch.setId(1);
        testBatch.setTrainer(new BamUser());

        testUser1.setBatch(testBatch);
        testUser4.setBatch(testBatch);
        testList.add(testUser1);
        testList.add(testUser2);
        testList.add(testUser3);

        testTrainerList.add(testUser2);
        testAssociateList.add(testUser1);
        testAssociateList.add(testUser3);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Users/All, works
     * and that the method returns a list.
     * @throws Exception
     */
    @Test
    public void getAllUsersTest() throws Exception {
        when(mockBamUserService.findAllUsers()).thenReturn(testList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Users/All"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Users/AllTrainers, works
     * and that the method returns a list.
     * @throws Exception
     */
    @Test
    public void getAllTrainersTest() throws Exception {
        when(mockBamUserService.findByRole(2)).thenReturn(testTrainerList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Users/AllTrainers"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Users/AllAssociates, works
     * and that the method returns a list.
     * @throws Exception
     */
    @Test
    public void getAllAssociatesTest() throws Exception {
        when(mockBamUserService.findByRole(1)).thenReturn(testAssociateList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Users/AllAssociates"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Users/InBatch, works
     * and that the method returns a list.
     * @throws Exception
     */
    @Test
    public void getUsersInBatchTest() throws Exception {
        when(mockBamUserService.findUsersInBatch(10)).thenReturn(testAssociateList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Users/InBatch").param("batchId", "10"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Users/Drop, works,
     * the information passes through the method correctly, and
     * that the method returns a list.
     * @throws Exception
     */
    @Test
    public void dropUserFromBatchTest() throws Exception {
        when(mockBamUserService.findUserById(1)).thenReturn(testUser1);
        doNothing().when(mockBamUserService).addOrUpdateUser(testUser1);
        when(mockBamUserService.findUsersInBatch(1)).thenReturn(testList);
        MvcResult result = mockMvc.perform(post("/rest/api/v1/Users/Drop").param("userId", "1"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Users/Update, works,
     * the information passes through the method correctly, and
     * that the method returns a list.
     * @throws Exception
     */
    @Test
    public void updateUserTest() throws Exception {
        when(mockBamUserService.findUserByEmail("test1@email.com")).thenReturn(testUser1);
        doNothing().when(mockBamUserService).addOrUpdateUser(testUser1);
        mockMvc.perform(post("/rest/api/v1/Users/Update").content(asJsonString(testUser1))
                .contentType("application/json;charset=utf8")).andExpect(status().isOk());
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Users/Remove, works,
     * the information passes through the method correctly, and
     * that the method returns a list.
     * @throws Exception
     */
    @Test
    public void removeUserTest() throws Exception {
        when(mockBamUserService.findUserById(1)).thenReturn(testUser1);
        doNothing().when(mockBamUserService).addOrUpdateUser(testUser1);
        when(mockBamUserService.findUsersInBatch(1)).thenReturn(testList);
        MvcResult result = mockMvc.perform(post("/rest/api/v1/Users/Remove").param("userId", "1"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Users/Add, works,
     * the information passes through the method correctly, and
     * that the method returns a list.
     * @throws Exception
     */
    @Test
    public void addUserToBatchTest() throws Exception {
        when(mockBamUserService.findUserById(4)).thenReturn(testUser4);
        when(mockBatchService.getBatchById(1)).thenReturn(testBatch);
        doNothing().when(mockBamUserService).addOrUpdateUser(testUser4);
        when(mockBamUserService.findUsersNotInBatch()).thenReturn(testList);
        MvcResult result = mockMvc.perform(post("/rest/api/v1/Users/Add").param("userId", "4")
                .param("batchId", "1")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Users/NotInABatch, works,
     * and that the method returns a list.
     * @throws Exception
     */
    @Test
    public void getUsersNotInBatchTest() throws Exception {
        when(mockBamUserService.findUsersNotInBatch()).thenReturn(testList);
        MvcResult result = mockMvc.perform(get("/rest/api/v1/Users/NotInABatch")).andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf8")).andReturn();
        String content = result.getResponse().getContentAsString();
        System.out.println(content);
    }


    /**
     * Converts an object to a string to be used as a parameter.
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
