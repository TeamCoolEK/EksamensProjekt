package org.example.coolplanner.repository;


import org.example.coolplanner.model.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

//Tester databasen (H2 test)
//Starter testen i SpringBoot Context
@SpringBootTest
//Konfigurere til test (til fx brug af in-memory database)
@ActiveProfiles("test")
//Bruger h2.sql script til oprettelse af h2 database
@Sql(scripts = "classpath:h2.sql", executionPhase = BEFORE_TEST_METHOD)
public class CoolPlannerReadRepositoryTest {

    //injecter repo dependencies
    @Autowired
    CoolPlannerReadRepository coolplannerReadRepository;

    //Finder employee med id fra h2.sql init data
    @Test
    void findEmployeeById() {
        Employee result = coolplannerReadRepository.findEmployeeById(2);

        assertEquals("Sebastian", result.getFirstName());
    }

    @Test
    void findProjectById () {
        Project project = coolplannerReadRepository.findProjectById(2);

        assertEquals("ProjectTest", project.getProjectName());
    }

    //Finder subproject med id fra h2.sql init data
    @Test
    void findSubProjectById () {
        SubProject subProject = coolplannerReadRepository.findSubProjectById(2);

        assertEquals("SubProjectTest", subProject.getSubProjectName());
    }

    //Finder task med id fra h2.sql init data
    @Test
    void findTaskById () {
        Task task = coolplannerReadRepository.findTaskById(2);

        assertEquals("TaskTest", task.getTaskName());
    }

    //Finder subTask med id fra h2.sql init data
    @Test
    void findSubTaskById () {
        SubTask subTask = coolplannerReadRepository.findSubTaskById(2);

        assertEquals("SubTaskTest", subTask.getSubTaskName());
    }

    //Test employee (Kan genbruges :D)
    Employee employee = new Employee(
            1,
            "John",
            "Doe",
            "john.doe@example.com",
            "password123",
            EmployeeRole.Manager
    );
}
