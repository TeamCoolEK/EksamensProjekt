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

//    public List<Project> getActiveProjects() {
//        return repository.findActiveProjects();
//    }
//
//    public List<SubProject> getActiveSubProjects() {
//        return repository.findActiveSubProjects();
//    }
//
//    public List<UserStory> getActiveUserStories() {
//        return repository.findActiveUserStories();
//    }
//
//    public List<Task> getActiveTasks() {
//        return repository.findActiveTasks();
//    }

    public Project createProject (Project project) {
        return repository.createProject(project);
    }

    public void createEmployee(Employee employee){
        repository.createEmployee(employee);
    }

    public void createUserStory(UserStory userStory) {
    }
}
