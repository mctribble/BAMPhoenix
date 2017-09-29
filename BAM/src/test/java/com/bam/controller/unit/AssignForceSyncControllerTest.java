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

public class AssignForceSyncControllerTest {
    @Mock
    AssignForceSyncController service;

    @Mock
    AssignForceSyncService assignForceSyncService;

    @InjectMocks
    AssignForceSyncController assignForceSyncController;
    private MockMvc mockMvc;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.setMockMvc(MockMvcBuilders.standaloneSetup(AssignForceSyncController.class).build());
    }
    @Test
    public void runSync() {

        assignForceSyncService.assignForceSync();
    }
    public MockMvc getMockMvc() {
        return mockMvc;
    }
    public void setMockMvc(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
}
