package org.example.coolplanner.service;

import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.CoolPlannerRepository;
import org.example.coolplanner.repository.CoolplannerRepositoryFind;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CoolPlannerService {

    private final CoolPlannerRepository repository;
    private final CoolplannerRepositoryFind repositoryFind;

    public CoolPlannerService(CoolPlannerRepository repository, CoolplannerRepositoryFind repositoryFind) {
        this.repository = repository;
        this.repositoryFind = repositoryFind;
    }

    public List<Project> getActiveProjects(int employeeId) {
        return repositoryFind.findActiveProjects(employeeId);
    }

    public Project createProject(Project project, Employee employee) {
        return repository.createProject(project, employee);
    }

    public SubProject createSubProject(SubProject subProject, Project project) {
        return repository.createSubProject(subProject, project);
    }

    public SubTask createSubTask(SubTask subTask, Task task) {
        return repository.createSubTask(subTask, task);
    }

    public SubTask getSubTaskById(int id) {
        return repositoryFind.findSubTaskById(id);
    }

    public void updateSubTask(SubTask subTask) {
        repository.updateSubTask(subTask);
    }

    public void createEmployee(Employee employee) {
        if (repository.emailExists(employee.getEmail())) {
            throw new IllegalArgumentException("Der findes allerede en bruger med den email");
        }
        repository.createEmployee(employee);
    }

    public Employee findEmployeeById(int id) {
        return repositoryFind.findEmployeeById(id);
    }

    public void updateEmployee(Employee employee) {
        repository.updateEmployee(employee);
    }

    public List<SubProject> getActiveSubProjects(int employeeId) {
        return repositoryFind.findActiveSubProjects(employeeId);
    }


    public List<Task> getActiveTasks(int employeeId) {
        return repositoryFind.findActiveTasks(employeeId);
    }

    public List<SubTask> getActiveSubTasks(int employeeId) {
        return repositoryFind.findActiveSubTasks(employeeId);
    }

    public void createTask(Task task, SubProject subProject) {
        repository.createTask(task, subProject);
    }

    public Employee login(String email, String password) {
        Employee employee = repositoryFind.findEmployeeByEmail(email);
        if (employee != null && employee.getPassword().equals(password)) {
            return employee;
        }
        return null;
    }

    public Project findProjectById(int id) {
        return repositoryFind.findProjectById(id);
    }

    public void updateProject(Project project) {
        repository.updateProject(project);
    }


    public Task getTaskById(int taskID) {
        return repositoryFind.findTaskById(taskID);
    }

    public void updateTask(Task task) {
        repository.updateTask(task);

    }

    public SubProject findSubProjectById(int id) {
        return repositoryFind.findSubProjectById(id);
    }

    public void updateSubProject(SubProject subProject) {
        repository.updateSubProject(subProject);
    }

    public void updateTaskTimeEstimateFromSubTasks(int taskId) {
        List<SubTask> subTasks = repositoryFind.findSubTasksByTaskId(taskId);

        int sum = 0;
        for (SubTask subTask : subTasks) {
            sum += subTask.getSubTaskTimeEstimate();
        }
        Task task = repositoryFind.findTaskById(taskId);
        task.setTaskTimeEstimate(sum);
        repository.updateTaskTimeEstimate(task);

        int subProjectId = task.getSubprojectID();
        updateSubProjectTimeEstimateFromTasks(subProjectId);
    }

    public void closeProject(int projectId) {
        repository.closeProject(projectId);
    }

    public List<Project> getClosedProjects(int employeeId) {
        return repositoryFind.findClosedProjects(employeeId);
    }


    public void updateSubProjectTimeEstimateFromTasks(int subProjectId) {
        List<Task> tasks = repositoryFind.findTasksBySubProjectId(subProjectId);
        int sum = 0;
        for (Task task : tasks) {
            sum += task.getTaskTimeEstimate();
        }
        SubProject subProject = repositoryFind.findSubProjectById(subProjectId);
        subProject.setSubProjectTimeEstimate(sum);
        repository.updateSubProjectTimeEstimate(subProject);

        int projectId = subProject.getProjectId();
        updateProjectTimeEstimateFromSubProjects(projectId);
    }

    public void updateProjectTimeEstimateFromSubProjects(int projectId) {
        List<SubProject> subProjects = repositoryFind.findSubProjectByProjectId(projectId);

        int sum = 0;
        for (SubProject sub : subProjects) {
            sum += sub.getSubProjectTimeEstimate();
        }
        Project project = repositoryFind.findProjectById(projectId);
        project.setProjectTimeEstimate(sum);
        repository.updateProjectTimeEstimate(project);
    }

    public double calculateDailyHours(Project project) {
        LocalDate today = LocalDate.now();
        LocalDate deadline = project.getProjectDeadLine();

        int remainingHours = 0;

        //Finder alle subprojecter for projectet //
        List<SubProject> subProjects = repositoryFind.findSubProjectByProjectId(project.getProjectId());

        //Finder tasks under hvert subProject //
        for (SubProject subProject : subProjects) {

            List<Task> tasks = repositoryFind.findTasksBySubProjectId(subProject.getSubProjectId());

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
        repository.completeSubTask(subTaskId, actualTime);
        SubTask subTask = repositoryFind.findSubTaskById(subTaskId);
        int taskId = subTask.getTaskId();

        updateTaskActualTimeFromSubTask(taskId);
    }

    public void updateTaskActualTimeFromSubTask(int taskId) {
        List<SubTask> subTasks = repositoryFind.findSubTasksByTaskId(taskId);

        int sum = 0;
        for (SubTask st : subTasks) {
            sum += st.getSubTaskActualTime();
        }

        Task task = repositoryFind.findTaskById(taskId);
        task.setTaskActualTime(sum);
        repository.updateTaskActualTime(task);

        int subProjectId = task.getSubprojectID();
        updateSubProjectActualTimeFromTasks(subProjectId);
        }

        public void updateSubProjectActualTimeFromTasks(int subProjectId){
        List<Task> tasks = repositoryFind.findTasksBySubProjectId(subProjectId);

        int sum = 0;
        for(Task t : tasks){
            sum += t.getTaskActualTime();
        }
        SubProject subProject = repositoryFind.findSubProjectById(subProjectId);
        subProject.setSubProjectActualTime(sum);
        repository.updateSubProjectActualTime(subProject);

    int projectId = subProject.getProjectId();
    updateProjectActualTimeFromSubProject(projectId);
    }

    public void updateProjectActualTimeFromSubProject(int projectId){
        List<SubProject> subProjects = repositoryFind.findSubProjectByProjectId(projectId);

        int sum = 0;
        for(SubProject sp : subProjects){
            sum += sp.getSubProjectActualTime();
        }

        Project project = repositoryFind.findProjectById(projectId);
        project.setProjectActualTime(sum);
        repository.updateProjectActualTime(project);
    }

    }


