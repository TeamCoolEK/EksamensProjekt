package org.example.coolplanner.controller;

import org.example.coolplanner.service.CoolPlannerWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DashboardController.class)
public class DashboardControllerTest {

    @MockitoBean
    private CoolPlannerWriteService coolPlannerWriteService;

    @Autowired
    private MockMvc mockMvc;

}
