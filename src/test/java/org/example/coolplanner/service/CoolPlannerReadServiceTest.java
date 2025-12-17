package org.example.coolplanner.service;

import org.example.coolplanner.model.Employee;
import org.example.coolplanner.model.EmployeeRole;
import org.example.coolplanner.repository.CoolPlannerReadRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


//Unit Tests (Bruges til at teste logik/metoderne som sættes i service)
//Struktur bør være Repo = Database håndtering -> service = logik(resten af metoden) -> controller = weblag (Benytte metoden)
@ExtendWith(MockitoExtension.class)
public class CoolPlannerReadServiceTest {

    //Mocker repo's til brug i test
    @Mock
    CoolPlannerReadRepository coolplannerReadRepository;

    //injecter service klassens dependencies
    @InjectMocks
    CoolPlannerReadService coolPlannerReadService;

    //Test employee (Kan genbruges :D)
    Employee employee = new Employee(
            1,
            "John",
            "Doe",
            "john.doe@example.com",
            "password123",
            EmployeeRole.Manager
    );

    //Tester login success
    @Test
    void login_success () {
        //setter employee med test employee (bliver sat øverst i klassen)
        Employee e = employee;
        //Når findEmployeeByEmail får john.doe@example.com, skal den retunere employee e (sat ovenover)
        when(coolplannerReadRepository.findEmployeeByEmail("john.doe@example.com"))
                .thenReturn(e);
        //Employee bliver sat ud fra email og password (som er rigtig)
        Employee result = coolPlannerReadService.login("john.doe@example.com", "password123");
        //employee skal ikke være null
        assertNotNull(result);
    }

    @Test
    void login_wrongPassword () {
        //samme som login
        Employee e = employee;

        when(coolplannerReadRepository.findEmployeeByEmail("john.doe@example.com"))
                .thenReturn(e);
        //Her bruger vi et password, der ikke matcher e
        Employee result = coolPlannerReadService.login("john.doe@example.com", "wrongPassword");
        //metoden skal retunere null
        assertNull(result);
    }

    @Test
    void login_employeeNotFound () {
        //sætter email til null (employee ikke fundet)
        when(coolplannerReadRepository.findEmployeeByEmail("dont@exist.com"))
                .thenReturn(null);
        //Bruger email
        Employee result = coolPlannerReadService.login("dont@exist.com", "password123");
        //metoden skal retunere null
        assertNull(result);
    }
}
