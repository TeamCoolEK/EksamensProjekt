package org.example.coolplanner.service;

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

    public Project createProject(Project project) {
        return repository.createProject(project);
    }

    public SubProject createSubProject (SubProject subProject) {
        return repository.createSubProject(subProject);
    }

    public Task createTask (Task task) {
        return repository.createTask(task);
    }

    public void createEmployee(Employee employee) {
        if (repository.emailExists(employee.getEmail())) {
            throw new IllegalArgumentException("Der findes allerede en bruger med den email");
        }
    }

        public List<SubProject> getActiveSubProjects ( int employeeId){
            return repository.findActiveSubProjects(employeeId);
        }


        public List<UserStory> getActiveUserStories ( int employeeId){
            return repository.findActiveUserStories(employeeId);
        }

        public List<Task> getActiveTasks ( int employeeId) {
            return repository.findActiveTasks(employeeId);
        }
            public void createUserStory (UserStory userStory){
            }
        }
