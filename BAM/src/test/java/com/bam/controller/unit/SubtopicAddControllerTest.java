package com.bam.controller.unit;

import com.bam.bean.Batch;
import com.bam.bean.Subtopic;
import com.bam.bean.SubtopicStatus;
import com.bam.bean.SubtopicName;
import com.bam.controller.SubtopicAddController;
import com.bam.repository.SubtopicRepository;
import com.bam.service.SubtopicService;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.Timestamp;
import java.util.Calendar;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SubtopicAddControllerTest {

    @Mock
    SubtopicService mockSubtopicService;

    @Mock
    SubtopicRepository subtopicRepository;

    @InjectMocks
    SubtopicAddController subtopicAddController;

    /**
     * Declaration of variables.
     */
    private MockMvc mockMvc;
    private Calendar calendar = Calendar.getInstance();
    private Timestamp timestamp = new java.sql.Timestamp(calendar.getTime().getTime());

    /**
     * Initialization of variables.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(subtopicAddController).build();
        doNothing().when(mockSubtopicService).updateSubtopic(new Subtopic(1, new SubtopicName(), new Batch(),
                new SubtopicStatus(), timestamp));
    }

    /**
     * Checks if the request mapping, /rest/api/v1/Subtopic/addSubtopic, mapping.
     * @throws Exception
     */
    @Test
    public void addSubtopicTest() throws Exception {
        mockMvc.perform(get("/rest/api/v1/Subtopic/addSubtopic")
                .param("json", asJsonString(new Subtopic(1, new SubtopicName(), new Batch(),
                        new SubtopicStatus(), timestamp)))
                .contentType("application/json;charset=UTF-8"))
                .andExpect(status()
                .isOk());
    }

    /**
     * Converts an object to a string for use as a parameter.
     * @param obj
     * @return
     */
    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
