package org.example.coolplanner.service;

import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.CoolPlannerWriteRepository;
import org.example.coolplanner.repository.CoolPlannerReadRepository;
import org.springframework.stereotype.Service;

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

    public double calculateDailyHours(Project project) {
        LocalDate today = LocalDate.now();
        LocalDate deadline = project.getProjectDeadLine();

        int remainingHours = 0;

        //Finder alle subprojecter for projectet //
        List<SubProject> subProjects = readRepository.findSubProjectByProjectId(project.getProjectId());

        //Finder tasks under hvert subProject //
        for (SubProject subProject : subProjects) {

            List<Task> tasks = readRepository.findTasksBySubProjectId(subProject.getSubProjectId());

            // udregner kun ud fra åbne tasks //
            for (Task task : tasks) {
                if (!task.getTaskStatus().equals(Status.Lukket)) {
                    remainingHours += task.getTaskTimeEstimate();
                }
            }
        }
        // beregner antal dage til deadline //
        int daysToDeadline = (int) (deadline.toEpochDay() - today.toEpochDay());
        if (daysToDeadline <= 0) {
            daysToDeadline = 1;
        }

        return (double) remainingHours / daysToDeadline;
    }

    public void completeSubTask(int subTaskId, int actualTime) {
        writeRepository.completeSubTask(subTaskId, actualTime);
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

}


