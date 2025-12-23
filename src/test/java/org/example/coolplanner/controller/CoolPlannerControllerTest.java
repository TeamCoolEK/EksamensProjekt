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

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Web Layer Tests, til at teste endpoints (Kun weblager, ingen database
// eller metode kald, derfor mockes service)
@WebMvcTest(CoolPlannerController.class)
public class CoolPlannerControllerTest {

    //Mocker service klassen
    @MockitoBean
    private CoolPlannerWriteService coolPlannerWriteService;

    @MockitoBean
    private CoolPlannerReadService coolPlannerReadService;

    //Bruger mockMvc klasse, til at kalde mock metoder på web lageret
    @Autowired
    private MockMvc mockMvc;

    //tester om get endpoint retunere kode 200 (success) og siden kan vises
    //hvis der retuneres andet (404, 500, osv), fejler testen
    @Test
    void createProject_isOk () throws Exception { //mockMvc thrower exception
        //mocker employee til session
        Employee employee = new Employee();
        //mocker HTTP request på /createProject endpoint og mocked employee session
        mockMvc.perform(get("/createProject").sessionAttr("employee", employee))
                //Forventer 200 kode (success)
                .andExpect(status().isOk())
                //forventer HTML createProject
                .andExpect(view().name("createProject"))
                //forventer project klasse som thymeleaf attribute
                .andExpect(model().attribute("project", instanceOf(Project.class)));
    }

    //tester redirect til login, hvis brugeren ikke er logget ind
    @Test
    void createProject_redirectLogin () throws Exception {

        mockMvc.perform(get("/createProject"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/login"));
    }

    //tester redirect til dashboard, hvis brugeren er en team member
    @Test
    void createProject_redirectDashboardTeamMember () throws Exception {

        Employee employee = new Employee();
        employee.setRole(EmployeeRole.Team_Member);

        mockMvc.perform(get("/createProject").sessionAttr("employee", employee))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/show"));
    }

    //tester POST metoden
    @Test
    void saveProjectTest() throws Exception {
        //laver ny employee
        Employee employeeSession = new Employee();
        employeeSession.setEmployeeId(1);

        //Starter en mocked HTTPS request med post metoden
        mockMvc.perform(
                post("/saveProject") //Mapping
                                .sessionAttr("employee", employeeSession) //sætter session med test employee
                                .param("projectName", "test") //sætter projectName
                                .param("projectDetails", "details") //sætter projectDetails
                )
                .andExpect(status().is3xxRedirection()) //forventer redirect
                .andExpect(redirectedUrl("/dashboard/show")); //til /XYZ
        //Verificere at createProject blev kaldt med employeeSession
        verify(coolPlannerWriteService).createProject(
                //vi sender et project objekt igennem, og et employee objekt
                // med employeeId == 1 som vores employeeSession test objekt
                // læs evt kommentar på updateProjectTest for argThat forklaring...
                any(Project.class),
                argThat(emp -> emp.getEmployeeId() == 1)
        );
    }

    //
    @Test
    void saveProject_employeeNull () throws Exception {

        mockMvc.perform(
                post("/saveProject")
                        .param("projectName", "test")
                        .param("projectDetails", "test")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/login"));
    }

    //Tester redirect til login hvis employee = null
    @Test
    void editProject_redirectLogin () throws Exception {

        mockMvc.perform(get("/project/{id}/edit", 1))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/employee/login"));
    }

    //tester redirect til dashboard, hvis team member er logget ind
    @Test
    void editProject_redirectDashboardTeamMember () throws Exception {

        Employee employee = new Employee();
        employee.setRole(EmployeeRole.Team_Member);

        mockMvc.perform(get("/project/{id}/edit", 1).sessionAttr("employee", employee))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/show"));
    }

    //tester redirect til dashboard, hvis project = null
    @Test
    void editProject_redirectDashboardProject () throws Exception {

        Employee employee = new Employee();
        employee.setRole(EmployeeRole.Manager);

        when(coolPlannerReadService.findProjectById(1)).thenReturn(null);

        mockMvc.perform(get("/project/{id}/edit", 1).sessionAttr("employee", employee))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/dashboard/show"));
    }

    //tester editProject Get request
    @Test
    void editProject_isOk () throws Exception {
        Employee employee = new Employee();
        Project test = new Project();
        test.setProjectId(1);

        //mocker findProject metode og binder den til test objektet
        when(coolPlannerReadService.findProjectById(1)).thenReturn(test);

        mockMvc.perform(
                get("/project/{id}/edit", test.getProjectId()).sessionAttr("employee", employee))
                .andExpect(status().isOk())
                .andExpect(view().name("editProject"))
                .andExpect(model().attribute("project", instanceOf(Project.class)));
    }

    //tester post request til updateProject
    @Test
    void updateProjectTest () throws Exception {
        //test form objekt og sætter projectId
        Project form = new Project();
        form.setProjectId(1);

        mockMvc.perform(
                post("/project/update/{id}", form.getProjectId())
                        .param("projectId", "1")
                        .param("projectName", "test")
                        .param("projectDetails", "beskrivelse")
                )
                .andExpect(status().is3xxRedirection());

        //verificere at updateProject bruger path variablen som sættes på test form
        verify(coolPlannerWriteService).updateProject(
                //argThat bruger lampda expression til at samligne specifikke værdier i et objekt,
                // istedet for at sende hele objektet igennem... Her samlignes at getProjectId
                // fra test form, er == 1, og at det kan sendes igennem til service kaldet
                // ellers fejler den.
                argThat( f -> f.getProjectId() == 1)
        );
    }

    //tester at trycatch bliver ramt, når man skriver ulovlig argument
    @Test
    void updateProject_illegalArgument () throws Exception {
        //Service kaster exception
        doThrow(new IllegalArgumentException("Fejl i input"))
                .when(coolPlannerWriteService)
                .updateProject(any(Project.class));

        //Forcenter at editProject view bliver retuneret, med error message.
        mockMvc.perform(
                post("/project/update/{id}", 1)
                    .param("projectName", "test")
                    .param("projectDetails", "test")
                )
                .andExpect(status().isOk())
                .andExpect(view().name("editProject"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attributeExists("project"));
    }
}
