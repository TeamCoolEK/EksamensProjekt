package org.example.coolplanner.controller;

import org.example.coolplanner.model.Employee;
import org.example.coolplanner.repository.CoolPlannerWriteRepository;
import org.example.coolplanner.repository.CoolPlannerReadRepository;
import org.example.coolplanner.service.CoolPlannerWriteService;
import org.example.coolplanner.service.CoolPlannerReadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @MockitoBean
    private CoolPlannerWriteService coolPlannerWriteService;

    @MockitoBean
    private CoolPlannerWriteRepository coolplannerWriteRepository;

    @MockitoBean
    private CoolPlannerReadRepository coolplannerReadRepository;

    @MockitoBean
    private CoolPlannerReadService coolPlannerReadService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createEmployeeTest () throws Exception {
        //mocker mvc view
        mockMvc.perform(get("/employee/createEmployee"))
                .andExpect(status().isOk())
                .andExpect(view().name("createEmployee"))
                .andExpect(model().attribute("employee", instanceOf(Employee.class)));
    }


    @Test
    void saveEmployeeTest () throws Exception {
        Employee testemployee = new Employee();

        mockMvc.perform(
                post("/employee/saveEmployee")
                    .param("firstName", "Maya")
                    .param("lastName", "Papaya")
                )
                .andExpect(status().is3xxRedirection());

        verify(coolPlannerWriteService).createEmployee(any(Employee.class));
    }

    @Test
    void editEmployeeTest () throws Exception {
        Employee test = new Employee();
        test.setEmployeeId(1);

        when(coolPlannerReadService.findEmployeeById(1)).thenReturn(test);

        mockMvc.perform(
                get("/employee/edit/{id}", test.getEmployeeId()))
                .andExpect(status().isOk())
                .andExpect(view().name("editEmployee"))
                .andExpect(model().attribute("employee", instanceOf(Employee.class))
                );
    }

    @Test
    void updateEmployeeTest () throws Exception{

        mockMvc.perform(
                post("/employee/update")
                        .param("employeeId", "1")
                        .param("firstName", "Maiken")
                        .param("lastName", "Swag")
                )
                .andExpect(status().is3xxRedirection());

        verify(coolPlannerWriteService).updateEmployee(
                argThat(emp ->
                        emp.getEmployeeId() == 1 &&
                        emp.getFirstName().equals("Maiken") &&
                        emp.getLastName().equals("Swag"))
        );
    }

    @Test
    void getLoginTest () throws Exception {

        mockMvc.perform(
                get("/employee/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    void postLoginTest () throws Exception {
        Employee test = new Employee();
        test.setEmployeeId(1);

        when(coolPlannerReadService.login("test@test.dk", "1234")).thenReturn(test);

        mockMvc.perform(
                post("/employee/login")
                        .sessionAttr("employee", test)
                        .param("email", "test@test.dk")
                        .param("password", "1234")
        )
                .andExpect(status().is3xxRedirection());

        verify(coolPlannerReadService).login(
                "test@test.dk", "1234"
        );
    }
}
