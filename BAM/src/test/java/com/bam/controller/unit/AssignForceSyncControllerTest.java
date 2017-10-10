package com.bam.controller.unit;

import com.bam.controller.AssignForceSyncController;
import com.bam.service.AssignForceSyncService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AssignForceSyncControllerTest {
    @Mock
    AssignForceSyncService mockAssignForceSyncService;

    @InjectMocks
    AssignForceSyncController mockAssignForceSyncController;

    private MockMvc mockMvc;

    /**
     * Initialization of variables.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(mockAssignForceSyncController).build();
    }

    /**
     * Checks if request mapping, /refreshBatches, works.
     * @throws Exception
     */
    @Test
    public void refreshBatchesTest() throws Exception {
        doNothing().when(mockAssignForceSyncService).assignForceSync();
        mockMvc.perform(get("/refreshBatches")).andExpect(status().isOk());
    }
}
