package org.example.coolplanner.controller;

import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.EmployeeRole;
import org.example.coolplanner.model.Project;
import org.example.coolplanner.service.CoolPlannerReadService;
import org.example.coolplanner.service.CoolPlannerWriteService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(DashboardController.class)
public class DashboardControllerTest {

    @MockitoBean
    private CoolPlannerWriteService coolPlannerWriteService;

    @MockitoBean
    private CoolPlannerReadService coolPlannerReadService;

    @Autowired
    private MockMvc mockMvc;

    // Tester om dashboard vises når employee er i session
    @Test
    void showDashboardTest_ok_returnsDashboardViewAndModelAttributes() throws Exception {
        Employee employee = new Employee();
        employee.setEmployeeId(1);
        employee.role = EmployeeRole.Manager;

        when(coolPlannerReadService.getActiveProjects(1)).thenReturn(List.of(new Project()));

        mockMvc.perform(get("/dashboard/show").sessionAttr("employee", employee))
                .andExpect(status().isOk()).andExpect(view().name("Dashboard"))
                .andExpect(model().attribute("employee", instanceOf(Employee.class)))
                .andExpect(model().attributeExists("projects"));

        verify(coolPlannerReadService).getActiveProjects(1);
        verify(coolPlannerReadService, never()).findProjectsForTeamMember(anyInt());
    }

    // Tester redirect til login, hvis session er tom
    @Test
    void showDashboardTest_noSession_redirectsToLogin() throws Exception {
        mockMvc.perform(get("/dashboard/show"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/login"));
    }
}
