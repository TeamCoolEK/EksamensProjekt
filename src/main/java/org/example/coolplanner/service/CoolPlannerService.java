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

    public List<Project> getActiveProjects() {
        return repository.findActiveProjects();
    }

    public List<SubProject> getActiveSubProjects() {
        return repository.findActiveSubProjects();
    }

    public List<UserStory> getActiveUserStories() {
        return repository.findActiveUserStories();
    }

    public List<Task> getActiveTasks() {
        return repository.findActiveTasks();
    }
}
