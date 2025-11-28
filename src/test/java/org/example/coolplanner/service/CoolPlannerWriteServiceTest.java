package org.example.coolplanner.service;

import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.EmployeeRole;
import org.example.coolplanner.repository.CoolPlannerWriteRepository;
import org.example.coolplanner.repository.CoolPlannerReadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


//Unit Tests (Bruges til at teste logik/metoderne som sættes i service)
//Struktur bør være Repo = Database håndtering -> service = logik(resten af metoden) -> controller = weblag (Benytte metoden)
@ExtendWith(MockitoExtension.class)
public class CoolPlannerWriteServiceTest {
    //Mocker repo's til brug i test
    @Mock
    CoolPlannerWriteRepository coolPlannerWriteRepository;

    @Mock
    CoolPlannerReadRepository coolplannerReadRepository;
    //injecter service klassens dependencies

    @InjectMocks
    CoolPlannerWriteService coolPlannerWriteService;

    //Tester metode til at finde EmployeeById
    @Test
    void findEmployeeById_returnsEmployee() {
        //Fortæller at repo metoden skal retunere test employee, når den får employeeId 1 (Mocker databasen)
        when(coolplannerReadRepository.findEmployeeById(1)).thenReturn(employee);
        //Henter test employee med service metoden
        Employee result = coolPlannerWriteService.findEmployeeById(1);
        //Forventer "John", ud fra den employee vi har hentet med service metoden.
        assertEquals("John", result.getFirstName());
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
