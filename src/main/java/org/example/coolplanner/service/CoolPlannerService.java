package org.example.coolplanner.service;

import org.example.coolplanner.model.Project;
import org.example.coolplanner.model.SubProject;
import org.example.coolplanner.model.Task;
import org.example.coolplanner.model.UserStory;
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

    public List<SubProject> getActiveSubProjects(int employeeId) {
        return repository.findActiveSubProjects(employeeId);
    }

    public List<UserStory> getActiveUserStories(int employeeId) {
        return repository.findActiveUserStories(employeeId);
    }

    public List<Task> getActiveTasks(int employeeId) {
        return repository.findActiveTasks(employeeId);
    }
}
