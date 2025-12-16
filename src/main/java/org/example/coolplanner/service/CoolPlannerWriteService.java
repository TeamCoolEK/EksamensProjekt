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

    public void updateTaskTimeEstimateFromSubTasks(int taskId) {
        List<SubTask> subTasks = readRepository.findSubTasksByTaskId(taskId);

        int sum = 0;
        for (SubTask subTask : subTasks) {
            sum += subTask.getSubTaskTimeEstimate();
        }

        Task task = readRepository.findTaskById(taskId);
        task.setTaskTimeEstimate(sum);
        writeRepository.updateTaskTimeEstimate(task);

        int subProjectId = task.getSubprojectID();
        updateSubProjectTimeEstimateFromTasks(subProjectId);
    }

    public void closeProject(int projectId) {
        writeRepository.closeProject(projectId);
    }

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

    private int calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            return 0;
        }

        int workingDays = 0;
        LocalDate date = startDate;

        while (!date.isAfter(endDate)) {
            // Hvis dagen IKKE er lørdag eller søndag, tælles den som arbejdsdag
            if (date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            date = date.plusDays(1); // gå videre til næste dag
        }

        return workingDays;
    }

    public double calculateDailyHours(Project project) {
        LocalDate today = LocalDate.now();
        LocalDate deadline = project.getProjectDeadLine();

        // Hvis deadline er i fortiden, sæt den til i dag
        if (deadline.isBefore(today)) {
            deadline = today;
        }

        int remainingHours = calculateProjectRemainingTimeEstimateFromSubProjects(project.getProjectId());

        // Beregn antal arbejdsdage (man-fre) frem til deadline
        int workingDays = calculateWorkingDays(today, deadline);
        if (workingDays <= 0) {
            workingDays = 1; // undgå division med 0
        }

        // Timer pr. arbejdsdag
        return (double) remainingHours / workingDays;
    }

    public void completeSubTask(int subTaskId, int actualTime) {
        writeRepository.completeSubTask(subTaskId, actualTime);
        updateSubTaskStatus(subTaskId, Status.Lukket);

        SubTask subTask = readRepository.findSubTaskById(subTaskId);
        int taskId = subTask.getTaskId();

        updateTaskActualTimeFromSubTask(taskId);
    }

    public void updateTaskActualTimeFromSubTask(int taskId) {
        List<SubTask> subTasks = readRepository.findSubTasksByTaskId(taskId);

        int sum = 0;
        for (SubTask st : subTasks) {
            sum += st.getSubTaskActualTime();
        }

        Task task = readRepository.findTaskById(taskId);
        task.setTaskActualTime(sum);
        writeRepository.updateTaskActualTime(task);

        int subProjectId = task.getSubprojectID();
        updateSubProjectActualTimeFromTasks(subProjectId);
    }

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

    public int calculateTaskRemainingTimeEstimateFromSubTasks(int taskId) {
        List<SubTask> subTasks = readRepository.findSubTasksByTaskId(taskId);

        int sum = 0;
        for (SubTask subTask : subTasks) {
            if (!subTask.getSubTaskStatus().equals(Status.Lukket)) {
                sum += subTask.getSubTaskTimeEstimate();
            }
        }
        return sum;
    }

    public int calculateSubProjectRemainingTimeEstimateFromTasks(int subProjectId) {
        List<Task> tasks = readRepository.findTasksBySubProjectId(subProjectId);

        int sum = 0;
        for (Task task : tasks) {
            sum += calculateTaskRemainingTimeEstimateFromSubTasks(task.getTaskId());
        }
        return sum;
    }

    public int calculateProjectRemainingTimeEstimateFromSubProjects(int projectId) {
        List<SubProject> subProjects = readRepository.findSubProjectByProjectId(projectId);

        int sum = 0;
        for (SubProject subProject : subProjects) {
            sum += calculateSubProjectRemainingTimeEstimateFromTasks(subProject.getSubProjectId());
        }
        return sum;
    }

    public void updateSubTaskStatus(int subTaskId, Status newStatus) {
        writeRepository.updateSubTaskStatus(subTaskId, newStatus);

        SubTask subTask = readRepository.findSubTaskById(subTaskId);
        int taskId = subTask.getTaskId();

        updateTaskStatusFromSubTasks(taskId);
    }

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

    public void updateSubProjectStatusFromTasks(int subProjectId) {
        List<Task> tasks = readRepository.findTasksBySubProjectId(subProjectId);

        boolean allNotStarted = true;
        boolean allClosed = true;

        for (Task t : tasks) {
            Status s = t.getTaskStatus();
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
        SubProject subProject = readRepository.findSubProjectById(subProjectId);
        subProject.setStatus(newStatus);
        writeRepository.updateSubProjectStatus(subProjectId, newStatus);
        updateProjectStatusFromSubProjects(subProject.getProjectId());
    }

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



