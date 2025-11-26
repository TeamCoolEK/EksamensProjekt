package org.example.coolplanner.controller;

import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.Project;
import org.example.coolplanner.service.CoolPlannerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.instanceOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Web Layer Tests, til at teste endpoints (Kun weblager, ingen database
// eller metode kald, derfor mockes service)
@WebMvcTest(CoolPlannerController.class)
public class CoolPlannerControllerTest {

    //Mocker service klassen
    @MockitoBean
    private CoolPlannerService coolPlannerService;

    //Bruger mockMvc klasse, til at kalde mock metoder på web lageret
    @Autowired
    private MockMvc mockMvc;

    //tester om get endpoint retunere kode 200 (success) og siden kan vises
    //hvis der retuneres andet (404, 500, osv), fejler testen
    @Test
    void createProjectTest() throws Exception { //mockMvc thrower exception
        //mocker HTTP request på /createProject endpoint
        mockMvc.perform(get("/createProject"))
                //Forventer 200 kode (success)
                .andExpect(status().isOk())
                //forventer HTML createProject
                .andExpect(view().name("createProject"))
                //forventer project klasse som thymeleaf attribute
                .andExpect(model().attribute("project", instanceOf(Project.class)));
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
                .andExpect(redirectedUrl("/XYZ")); //til /XYZ
        //Verificere at createProject blev kaldt med employeeSession
        verify(coolPlannerService).createProject(
                //vi sender et project objekt igennem, og et employee objekt
                // med employeeId == 1 som vores employeeSession test objekt
                // læs evt kommentar på updateProjectTest for argThat forklaring...
                any(Project.class),
                argThat(emp -> emp.getEmployeeId() == 1)
        );
    }

    //tester editProject Get request
    @Test
    void editProjectTest () throws Exception {
        mockMvc.perform(
                get("/project/{id}/edit"))
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
        verify(coolPlannerService).updateProject(
                //argThat bruger lampda expression til at samligne specifikke værdier i et objekt,
                // istedet for at sende hele objektet igennem... Her samlignes at getProjectId
                // fra test form, er == 1, og at det kan sendes igennem til service kaldet
                // ellers fejler den.
                argThat( f -> f.getProjectId() == 1)
        );
    }
}
