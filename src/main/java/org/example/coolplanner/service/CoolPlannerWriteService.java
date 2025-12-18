package org.example.coolplanner.service;

import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.CoolPlannerWriteRepository;
import org.example.coolplanner.repository.CoolPlannerReadRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Service
public class CoolPlannerWriteService {

    private final CoolPlannerWriteRepository writeRepository;
    private final CoolPlannerReadRepository readRepository;

    public CoolPlannerWriteService(CoolPlannerWriteRepository writeRepository, CoolPlannerReadRepository readRepository) {
        this.writeRepository = writeRepository;
        this.readRepository = readRepository;
    }

    public Project createProject(Project project, Employee employee) {
        return writeRepository.createProject(project, employee);
    }

    public SubProject createSubProject(SubProject subProject, Project project) {
        return writeRepository.createSubProject(subProject, project);
    }

    public SubTask createSubTask(SubTask subTask, Task task) {
        return writeRepository.createSubTask(subTask, task);
    }

    public void updateSubTask(SubTask subTask) {
        writeRepository.updateSubTask(subTask);
        updateTaskStatusFromSubTasks(subTask.getTaskId());
    }

    public void createEmployee(Employee employee) {
        if (writeRepository.emailExists(employee.getEmail())) {
            throw new IllegalArgumentException("Der findes allerede en bruger med den email");
        }
        writeRepository.createEmployee(employee);
    }

    public void updateEmployee(Employee employee) {
        writeRepository.updateEmployee(employee);
    }

    public void createTask(Task task, SubProject subProject) {
        writeRepository.createTask(task, subProject);
    }

    public void updateProject(Project project) {
        writeRepository.updateProject(project);
    }

    public void updateTask(Task task) {
        writeRepository.updateTask(task);
    }

    public void updateSubProject(SubProject subProject) {
        writeRepository.updateSubProject(subProject);
    }

    // Opdaterer en Tasks samlede tidsestimat ud fra alle dens SubTasks.
// Logikken er: summer alle SubTask.timeEstimate og gem resultatet på Task.
    public void updateTaskTimeEstimateFromSubTasks(int taskId) {

        // Henter alle subtasks, der hører til den valgte task
        List<SubTask> subTasks = readRepository.findSubTasksByTaskId(taskId);

        // Summerer tidsestimaterne fra alle subtasks
        int sum = 0;
        for (SubTask subTask : subTasks) {
            sum += subTask.getSubTaskTimeEstimate();
        }
        // Finder tasken og sætter dens tidsestimat til summen vi har beregnet
        Task task = readRepository.findTaskById(taskId);
        task.setTaskTimeEstimate(sum);

        // Gemmer det opdaterede tidsestimat i databasen
        writeRepository.updateTaskTimeEstimate(task);

        // Opdaterer også subprojectets tidsestimat (sum af tasks),
        // så ændringen bliver afspejlet højere oppe i hierarkiet
        int subProjectId = task.getSubprojectID();
        updateSubProjectTimeEstimateFromTasks(subProjectId);
    }

    public void closeProject(int projectId) {
        writeRepository.closeProject(projectId);
    }

    // Opdatere en SubProjects tidsestimat fra Task
    public void updateSubProjectTimeEstimateFromTasks(int subProjectId) {
        List<Task> tasks = readRepository.findTasksBySubProjectId(subProjectId);
        int sum = 0;
        for (Task task : tasks) {
            sum += task.getTaskTimeEstimate();
        }
        SubProject subProject = readRepository.findSubProjectById(subProjectId);
        subProject.setSubProjectTimeEstimate(sum);
        writeRepository.updateSubProjectTimeEstimate(subProject);

        int projectId = subProject.getProjectId();
        updateProjectTimeEstimateFromSubProjects(projectId);
    }

    // Opdatere en Projects tidsestimat fra SubProjects
    public void updateProjectTimeEstimateFromSubProjects(int projectId) {
        List<SubProject> subProjects = readRepository.findSubProjectByProjectId(projectId);

        int sum = 0;
        for (SubProject sub : subProjects) {
            sum += sub.getSubProjectTimeEstimate();
        }
        Project project = readRepository.findProjectById(projectId);
        project.setProjectTimeEstimate(sum);
        writeRepository.updateProjectTimeEstimate(project);
    }

    // Beregner antal arbejdsdage (man–fre) i et datointerval (inklusiv start- og slutdato).
    // Weekender (lørdag og søndag) tælles ikke med.
    private int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {

        // Hvis slutdato ligger før startdato, giver intervallet ingen arbejdsdage
        if (endDate.isBefore(startDate)) {
            return 0;
        }

        // Tæller hvor mange arbejdsdage der findes i perioden
        int workingDays = 0;

        // Starter ved startdatoen og itererer dag for dag frem til endDate
        LocalDate date = startDate;

        // Kører så længe vi ikke er gået forbi endDate (inkluderer endDate)
        while (!date.isAfter(endDate)) {

            // Hvis dagen IKKE er lørdag eller søndag, tælles den som en arbejdsdag
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }

            // Gå videre til næste dato
            date = date.plusDays(1);
        }

        // Returnerer det samlede antal arbejdsdage i perioden
        return workingDays;
    }

    // Beregner hvor mange timer man i gennemsnit skal arbejde pr. arbejdsdag for at nå projektets deadline.
   // Den tager udgangspunkt i "resterende estimeret tid" og fordeler den på antal arbejdsdage (man–fre) frem til deadline.
    public double calculateDailyHours(Project project) {

        // Dagens dato bruges som startpunkt for beregningen
        LocalDate today = LocalDate.now();

        // Henter projektets deadline
        LocalDate deadline = project.getProjectDeadLine();

        // Hvis deadline er i fortiden, sættes deadline til i dag
        // (så vi undgår negative dato-intervaller)
        if (deadline.isBefore(today)) {
            deadline = today;
        }

        // Finder hvor mange estimerede timer der mangler på projektet
        // (summeret fra SubProjects -> Tasks -> SubTasks, hvor "Lukket" ikke tælles med)
        int remainingHours = calculateProjectRemainingTimeEstimateFromSubProjects(project.getProjectId());

        // Beregner hvor mange arbejdsdage (man–fre) der er fra i dag til deadline (inkl. begge datoer)
        int workingDays = calculateWorkingDays(today, deadline);

        // Sikkerhed: hvis der mod forventning kommer 0 arbejdsdage, sæt til 1 for at undgå division med 0
        if (workingDays <= 0) {
            workingDays = 1;
        }

        // Returnerer gennemsnitlige timer pr. arbejdsdag
        return (double) remainingHours / workingDays;
    }

    //Metode til at kunne lukke en SubTask
    public void completeSubTask(int subTaskId, int actualTime) {
        writeRepository.completeSubTask(subTaskId, actualTime);
        updateSubTaskStatus(subTaskId, Status.Lukket);

        SubTask subTask = readRepository.findSubTaskById(subTaskId);
        int taskId = subTask.getTaskId();

        updateTaskActualTimeFromSubTask(taskId);
    }

    // Opdaterer en Tasks samlede faktiske tid ud fra alle dens SubTasks.
    // Logikken er: summer SubTask.actualTime og gem resultatet på Task.
   // Til sidst opdateres også parent SubProject, så den faktiske tid “bobler op” i hierarkiet.
    public void updateTaskActualTimeFromSubTask(int taskId) {

        // Henter alle subtasks, der hører til den valgte task
        List<SubTask> subTasks = readRepository.findSubTasksByTaskId(taskId);

        // Summerer faktisk tid fra alle subtasks
        int sum = 0;
        for (SubTask st : subTasks) {
            sum += st.getSubTaskActualTime();
        }

        // Finder tasken og sætter dens faktiske tid til summen vi har beregnet
        Task task = readRepository.findTaskById(taskId);
        task.setTaskActualTime(sum);

        // Gemmer den opdaterede faktiske tid i databasen
        writeRepository.updateTaskActualTime(task);

        // Opdaterer også subprojectets faktiske tid (sum af tasks),
        // så ændringen bliver afspejlet højere oppe i hierarkiet
        int subProjectId = task.getSubprojectID();
        updateSubProjectActualTimeFromTasks(subProjectId);
    }

    // Beregner SubProjects faktiske tid, far Tasks faktiske tid.
    public void updateSubProjectActualTimeFromTasks(int subProjectId) {
        List<Task> tasks = readRepository.findTasksBySubProjectId(subProjectId);

        int sum = 0;
        for (Task t : tasks) {
            sum += t.getTaskActualTime();
        }
        SubProject subProject = readRepository.findSubProjectById(subProjectId);
        subProject.setSubProjectActualTime(sum);
        writeRepository.updateSubProjectActualTime(subProject);

        int projectId = subProject.getProjectId();
        updateProjectActualTimeFromSubProject(projectId);
    }

    // Beregner Projects faktiske tid, far SubProjects faktiske tid.
    public void updateProjectActualTimeFromSubProject(int projectId) {
        List<SubProject> subProjects = readRepository.findSubProjectByProjectId(projectId);

        int sum = 0;
        for (SubProject sp : subProjects) {
            sum += sp.getSubProjectActualTime();
        }

        Project project = readRepository.findProjectById(projectId);
        project.setProjectActualTime(sum);
        writeRepository.updateProjectActualTime(project);
    }

    // Beregner hvor meget estimeret tid der er tilbage på en Task ud fra dens SubTasks.
   // Kun SubTasks der IKKE er "Lukket" tælles med (dvs. åbne/igangværende opgaver bidrager til resttid).
    public int calculateTaskRemainingTimeEstimateFromSubTasks(int taskId) {

        // Henter alle subtasks, der hører til den task vi vil beregne resttid for
        List<SubTask> subTasks = readRepository.findSubTasksByTaskId(taskId);

        // Summerer kun tidsestimater for subtasks, som endnu ikke er afsluttet
        int sum = 0;
        for (SubTask subTask : subTasks) {

            // Hvis subtask ikke er lukket, så er dens estimerede tid stadig "tilbage"
            if (!subTask.getSubTaskStatus().equals(Status.Lukket)) {
                sum += subTask.getSubTaskTimeEstimate();
            }
        }

        // Returnerer samlet resterende estimeret tid for tasken
        return sum;
    }

    // Beregner hvor meget estimeret tid der er tilbage på en SubProject ud fra dens Tasks
    public int calculateSubProjectRemainingTimeEstimateFromTasks(int subProjectId) {
        List<Task> tasks = readRepository.findTasksBySubProjectId(subProjectId);

        int sum = 0;
        for (Task task : tasks) {
            sum += calculateTaskRemainingTimeEstimateFromSubTasks(task.getTaskId());
        }
        return sum;
    }

    // Beregner hvor meget estimeret tid der er tilbage på en Project ud fra dens SubProject
    public int calculateProjectRemainingTimeEstimateFromSubProjects(int projectId) {
        List<SubProject> subProjects = readRepository.findSubProjectByProjectId(projectId);

        int sum = 0;
        for (SubProject subProject : subProjects) {
            sum += calculateSubProjectRemainingTimeEstimateFromTasks(subProject.getSubProjectId());
        }
        return sum;
    }

    // Opdatere SubTasks status
    public void updateSubTaskStatus(int subTaskId, Status newStatus) {
        writeRepository.updateSubTaskStatus(subTaskId, newStatus);

        SubTask subTask = readRepository.findSubTaskById(subTaskId);
        int taskId = subTask.getTaskId();

        updateTaskStatusFromSubTasks(taskId);
    }

    // Opdatere Tasks status ud fra SubTasks
    public void updateTaskStatusFromSubTasks(int taskId) {
        List<SubTask> subTasks = readRepository.findSubTasksByTaskId(taskId);

        boolean allNotStarted = true;
        boolean allClosed = true;

        for (SubTask st : subTasks) {
            Status s = st.getSubTaskStatus();

            if (s != Status.Ikke_startet) {
                allNotStarted = false;
            }
            if (s != Status.Lukket) {
                allClosed = false;
            }
        }

        Status newStatus;
        if (allNotStarted) {
            newStatus = Status.Ikke_startet;
        } else if (allClosed) {
            newStatus = Status.Lukket;
        } else {
            newStatus = Status.I_gang;
        }

        Task task = readRepository.findTaskById(taskId);
        task.setTaskStatus(newStatus);

        writeRepository.updateTaskStatus(taskId, newStatus);
        updateSubProjectStatusFromTasks(task.getSubprojectID());
    }

    // Opdaterer et SubProjects status ud fra status på alle Tasks i subprojectet.
// Hvis ALLE tasks er "Ikke_startet" bliver subproject "Ikke_startet"
// Hvis ALLE tasks er "Lukket" bliver subproject "Lukket"
// Ellers (blandet / nogen i gang) bliver subproject "I_gang"
    public void updateSubProjectStatusFromTasks(int subProjectId) {

        // Henter alle tasks, der hører til det subproject vi vil opdatere
        List<Task> tasks = readRepository.findTasksBySubProjectId(subProjectId);

        // Flags der antager at alle tasks starter som "Ikke_startet" og "Lukket"
        // (de bliver sat til false, så snart vi finder en task der bryder reglen)
        boolean allNotStarted = true;
        boolean allClosed = true;

        // Gennemgår alle tasks og tjekker deres status
        for (Task t : tasks) {
            Status s = t.getTaskStatus();

            // Hvis bare én task ikke er "Ikke_startet", kan vi ikke sige "alle er ikke startet"
            if (s != Status.Ikke_startet) {
                allNotStarted = false;
            }

            // Hvis bare én task ikke er "Lukket", kan vi ikke sige "alle er lukket"
            if (s != Status.Lukket) {
                allClosed = false;
            }
        }

        // Bestemmer den nye status ud fra flags:
        // allNotStarted = Ikke_startet
        // allClosed = Lukket
        // ellers = I_gang
        Status newStatus;
        if (allNotStarted) {
            newStatus = Status.Ikke_startet;
        } else if (allClosed) {
            newStatus = Status.Lukket;
        } else {
            newStatus = Status.I_gang;
        }

        // Henter subprojectet og opdaterer status i objektet
        SubProject subProject = readRepository.findSubProjectById(subProjectId);
        subProject.setStatus(newStatus);

        // Gemmer status-ændringen i databasen
        writeRepository.updateSubProjectStatus(subProjectId, newStatus);

        // Efter subproject-status er opdateret, opdateres også parent Project status,
        // så status "bobler op" helt til toppen.
        updateProjectStatusFromSubProjects(subProject.getProjectId());
    }

    // Opdatere Projects status ud fra SubProjects status
    public void updateProjectStatusFromSubProjects(int projectId) {
        List<SubProject> subProjects = readRepository.findSubProjectByProjectId(projectId);

        boolean allNotStarted = true;
        boolean allClosed = true;

        for (SubProject sp : subProjects) {
            Status s = sp.getStatus();
            if (s != Status.Ikke_startet) {
                allNotStarted = false;
            }
            if (s != Status.Lukket) {
                allClosed = false;
            }
        }

        Status newStatus;
        if (allNotStarted) {
            newStatus = Status.Ikke_startet;
        } else if (allClosed) {
            newStatus = Status.Lukket;
        } else {
            newStatus = Status.I_gang;
        }

        Project project = readRepository.findProjectById(projectId);
        project.setStatus(newStatus);
        writeRepository.updateProjectStatus(projectId, newStatus);
    }


}



