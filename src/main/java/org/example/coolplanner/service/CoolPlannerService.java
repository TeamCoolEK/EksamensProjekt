package org.example.coolplanner.service;

import org.apache.catalina.User;
import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.CoolPlannerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoolPlannerService {

    private final CoolPlannerRepository repository;

    public CoolPlannerService(CoolPlannerRepository repository) {
        this.repository = repository;
    }

    public List<Project> getActiveProjects(int employeeId) {
        return repository.findActiveProjects(employeeId);
    }

    public Project createProject(Project project, Employee employee) {
        return repository.createProject(project, employee);
    }

    public SubProject createSubProject(SubProject subProject) {
        return repository.createSubProject(subProject);
    }

    public SubTask createSubTask(SubTask subTask, UserStory userStory) {
        return repository.createSubTask(subTask, userStory);
    }

    public void createEmployee(Employee employee) {
        if (repository.emailExists(employee.getEmail())) {
            throw new IllegalArgumentException("Der findes allerede en bruger med den email");
        }
        repository.createEmployee(employee);
    }

    public Employee findEmployeeById(int id) {
        return repository.findEmployeeById(id);
    }

    public void updateEmployee(Employee employee) {
        repository.updateEmployee(employee);
    }

    public List<SubProject> getActiveSubProjects(int employeeId) {
        return repository.findActiveSubProjects(employeeId);
    }


    public List<UserStory> getActiveUserStories(int employeeId) {
        return repository.findActiveUserStories(employeeId);
    }

    public List<SubTask> getActiveSubTasks(int employeeId) {
        return repository.findActiveSubTasks(employeeId);
    }

    public void createUserStory(UserStory userStory) {
    }

    public Employee login(String email, String password) {
        Employee employee = repository.findEmployeeByEmail(email);
        if (employee != null && employee.getPassword().equals(password)) {
            return employee;
        }
        return null;
    }

    public Project findProjectById(int id) {
        return repository.findProjectById(id);
    }

    public void updateProject(Project project) {
        repository.updateProject(project);
    }


    public UserStory getUserStoryById(int userStoryID) {
        return repository.findUserStoryById(userStoryID);
    }

    public void updateUserStory(UserStory userStory) {

    }


    public SubProject findSubProjectById(int id) {
        return repository.findSubProjectById(id);
    }

    public void updateSubProject(SubProject subProject) {
        repository.updateSubProject(subProject);
    }
}
