package org.example.coolplanner.service;

import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.CoolPlannerReadRepository;
import org.example.coolplanner.repository.CoolPlannerWriteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
//Unit test
@ExtendWith(MockitoExtension.class)
public class CoolPlannerWriteServiceTest {

    @Mock
    CoolPlannerReadRepository coolPlannerReadRepository;

    @InjectMocks
    CoolPlannerWriteService coolPlannerWriteService;

    @Test
    void calculateProjectRemainingTimeEstimateFromSubProject_sumsOnlyNonClosedSubTasks(){
        int projectId = 1;

        // to SubProjects under projektet
        SubProject sp1 = new SubProject();
        sp1.setSubProjectId(10);

        SubProject sp2 = new SubProject();
        sp2.setSubProjectId(20);

        when(coolPlannerReadRepository.findSubProjectByProjectId(projectId))
                .thenReturn(List.of(sp1, sp2));

        // Tasks under SubProjectsne
        Task t1 = new Task();
        t1.setTaskId(100);

        Task t2 = new Task();
        t2.setTaskId(200);

        when(coolPlannerReadRepository.findTasksBySubProjectId(10))
                .thenReturn(List.of(t1));
        when(coolPlannerReadRepository.findTasksBySubProjectId(20))
                .thenReturn(List.of(t2));

        //SubTasks under t1
        SubTask st1 = new SubTask();
        st1.setSubTaskTimeEstimate(5);
        st1.setSubTaskStatus(Status.Ikke_startet); // Skal være med i regnestykket


        SubTask st2 = new SubTask();
        st2.setSubTaskTimeEstimate(3);
        st2.setSubTaskStatus(Status.Lukket); // skal ikke være med

        when(coolPlannerReadRepository.findSubTasksByTaskId(100))
                .thenReturn(List.of(st1, st2));

        //SubTasks under t2
        SubTask st3 = new SubTask();
        st3.setSubTaskTimeEstimate(2);
        st3.setSubTaskStatus(Status.Ikke_startet);

        when(coolPlannerReadRepository.findSubTasksByTaskId(200))
                .thenReturn(List.of(st3));

    int result = coolPlannerWriteService.
            calculateProjectRemainingTimeEstimateFromSubProjects(projectId);

    //Skal forvente 7 = 5 + 2 (result = st1 + st3)
    assertEquals(7, result);
    }

    @Test
    void calculateDailyHours_divideRemainingHoursByWorkingDays(){

        int projectId = 1;

        Project project = new Project();
        project.setProjectId(projectId);
        // Deadline sættes til i dag, så det bør give 1 arbejdsdag
        project.setProjectDeadLine(LocalDate.now());

        //Mock SubProjects
        SubProject sp = new SubProject();
        sp.setSubProjectId(10);
        when(coolPlannerReadRepository.findSubProjectByProjectId(projectId))
                .thenReturn(List.of(sp));

        //Mock Tasks
        Task t = new Task();
        t.setTaskId(100);
        when(coolPlannerReadRepository.findTasksBySubProjectId(10))
                .thenReturn(List.of(t));

        //Mock SubTasks (ikke lukkede)
        SubTask sub1 = new SubTask();
        sub1.setSubTaskTimeEstimate(4);
        sub1.setSubTaskStatus(Status.Ikke_startet);

        SubTask sub2 = new SubTask();
        sub2.setSubTaskTimeEstimate(6);
        sub2.setSubTaskStatus(Status.Ikke_startet);

        when(coolPlannerReadRepository.findSubTasksByTaskId(100))
                .thenReturn(List.of(sub1, sub2));

        double result = coolPlannerWriteService.calculateDailyHours(project);

        assertEquals(10.0, result, 0.01);
    }
}
