package org.example.coolplanner.controller;

import org.example.coolplanner.model.Project;
import org.example.coolplanner.service.CoolPlannerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.instanceOf;
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
    void createProject_returns200() throws Exception { //mockMvc thrower exception
        //mocker HTTP request på /createProject endpoint
        mockMvc.perform(get("/createProject"))
                //Forventer 200 kode (success)
                .andExpect(status().isOk())
                //forventer HTML createProject
                .andExpect(view().name("createProject"))
                //forventer project klasse som thymeleaf attribute
                .andExpect(model().attribute("project", instanceOf(Project.class)));
    }

}
