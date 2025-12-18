package org.example.coolplanner.service;

import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.CoolPlannerReadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// Service-lag der håndterer al læselogik (READ) i systemet.
// Klassen fungerer som bindeled mellem controller og repository
@Service
public class CoolPlannerReadService {

    private final CoolPlannerReadRepository readRepository;
    private final CoolPlannerReadRepository coolPlannerReadRepository;

    // Dependency Injection af repositories via constructor
    public CoolPlannerReadService(CoolPlannerReadRepository readRepository,
                                  CoolPlannerReadRepository coolPlannerReadRepository) {
        this.readRepository = readRepository;
        this.coolPlannerReadRepository = coolPlannerReadRepository;
    }

    // Henter alle aktive (ikke-lukkede) projekter for en given medarbejder
    public List<Project> getActiveProjects(int employeeId) {
        return readRepository.findActiveProjects(employeeId);
    }

    // Finder en specifik SubTask ud fra dens ID
    public SubTask getSubTaskById(int id) {
        return readRepository.findSubTaskById(id);
    }

    // Finder en medarbejder ud fra employeeId
    public Employee findEmployeeById(int id) {
        return readRepository.findEmployeeById(id);
    }

    // Henter alle aktive SubProjects for en medarbejder
    public List<SubProject> getActiveSubProjects(int employeeId) {
        return readRepository.findActiveSubProjects(employeeId);
    }

    // Henter alle aktive Tasks for en medarbejder
    public List<Task> getActiveTasks(int employeeId) {
        return readRepository.findActiveTasks(employeeId);
    }

    // Henter alle aktive SubTasks for en medarbejder
    public List<SubTask> getActiveSubTasks(int employeeId) {
        return readRepository.findActiveSubTasks(employeeId);
    }

    // Login-metode:
    // Finder medarbejderen ud fra email og tjekker om password matcher
    public Employee login(String email, String password) {
        Employee employee = readRepository.findEmployeeByEmail(email);

        // Returnerer employee hvis credentials matcher, ellers null
        if (employee != null && employee.getPassword().equals(password)) {
            return employee;
        }
        return null;
    }

    // Finder et projekt ud fra projektId
    public Project findProjectById(int id) {
        return readRepository.findProjectById(id);
    }

    // Finder en task ud fra taskId
    public Task getTaskById(int taskID) {
        return readRepository.findTaskById(taskID);
    }

    // Finder et SubProject ud fra subProjectId
    public SubProject findSubProjectById(int id) {
        return readRepository.findSubProjectById(id);
    }

    // Henter alle afsluttede (lukkede) projekter for en medarbejder
    public List<Project> getClosedProjects(int employeeId) {
        return readRepository.findClosedProjects(employeeId);
    }

    // Henter alle medarbejdere i systemet
    public List<Employee> getAllEmployees(){
        return readRepository.findAllEmployees();
    }

    public List<Project> findProjectsForTeamMember(int employeeId) {
        return coolPlannerReadRepository.findProjectsForTeamMember(employeeId);
    }
}