package org.example.coolplanner.repository;

import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.EmployeeRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_METHOD;

//Tester databasen (H2 test)
//Starter testen i SpringBoot Context
@SpringBootTest
//Konfigurere til test (til fx brug af in-memory database)
@ActiveProfiles("test")
//Bruger h2.sql script til oprettelse af h2 database
@Sql(scripts = "classpath:h2.sql", executionPhase = BEFORE_TEST_METHOD)
public class CoolPlannerWriteRepositoryTest {

    //injecter repo dependencies
    @Autowired
    CoolPlannerWriteRepository coolPlannerWriteRepository;

    @Autowired
    CoolPlannerReadRepository coolplannerReadRepository;

    //tilføjer employee til databasen, og henter den igen
//    @Test
//    void createAndFindEmployee () {
//        coolPlannerRepository.createEmployee(employee);
//
//        Employee result = coolPlannerRepository.findEmployeeByEmail("john.doe@example.com");
//
//        assertEquals("John", result.getFirstName());
//    }
    //Tester om en email existere
    @Test
    void emailExists_ReturnsTrue () {
        //laver employee midlertidligt i h2 databasen
        coolPlannerWriteRepository.createEmployee(employee);
        //Boolean bliver true, da "john.doe@example.com" er i h2 databasen
        boolean exists = coolPlannerWriteRepository.emailExists("john.doe@example.com");
        //Forventer at boolean er true
        assertTrue(exists);
    }

    //Tester om email ikke existere
    @Test
    void emailExists_returnsFalse () {
        //boolean bliver false, da "dont@exist.com" ikke existere i h2 databasen
        boolean exists = coolPlannerWriteRepository.emailExists("dont@exist.com");
        //forventer at boolean er false
        assertFalse(exists);
    }

    @Test
    void CreateUpdateAndFindEmployee () {
        coolPlannerWriteRepository.createEmployee(employee);

        coolPlannerWriteRepository.updateEmployee(updateEmployee);

        Employee result = coolplannerReadRepository.findEmployeeById(1);

        assertEquals("password", result.getPassword());
    }

    //Test objekter (Kan genbruges :D)
    Employee employee = new Employee(
            1,
            "John",
            "Doe",
            "john.doe@example.com",
            "password123",
            EmployeeRole.Manager
    );

    Employee updateEmployee = new Employee(
            1,
            "Johnny",
            "Doeson",
            "john.doe@example.com",
            "password",
            EmployeeRole.Manager
    );
}
