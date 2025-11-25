package org.example.coolplanner.repository;


import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.EmployeeRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

//Tester databasen (H2 test)
//Starter testen i SpringBoot Context
@SpringBootTest
//Konfigurere til test (til fx brug af in-memory database)
@ActiveProfiles("test")
//Bruger h2.sql script til oprettelse af h2 database
@Sql(scripts = "classpath:h2.sql", executionPhase = BEFORE_TEST_METHOD)
public class CoolPlannerRepositoryFindTest {

    //injecter repo dependencies
    @Autowired
    CoolplannerRepositoryFind coolplannerRepositoryFind;

    //Finder employee med id fra h2.sql init data
    @Test
    void findEmployeeById() {
        Employee result = coolplannerRepositoryFind.findEmployeeById(2);

        assertEquals("Sebastian", result.getFirstName());
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
