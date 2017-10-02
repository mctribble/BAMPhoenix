package com.bam.controller.unit;

import com.bam.bean.Batch;
import com.bam.controller.BatchController;
import com.bam.service.BamUserService;
import com.bam.service.BatchService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.Mockito.*;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:beans.xml")
//@WebAppConfiguration
public class BatchControllerTest {

    @Mock
    private BatchService batchService;

    @Mock
    private BamUserService bamUserService;

    @InjectMocks
    private BatchController batchController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(new BatchController(batchService, bamUserService)).build();
    }

    @Test
    public void getAllBatchesTest() throws Exception {
        mockMvc.perform(get("/api/v1/Batches/All"))
                .andExpect(status().isOk()).andExpect(content().contentType("application/json"));
    }

    @Test
    public void getPastBatchesTest() throws Exception {
//        List<Batch> testBatchList = new ArrayList<>();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("email", "jc@revature.com");
        mockMvc.perform(get("api/v1/Batches/Past"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

}
