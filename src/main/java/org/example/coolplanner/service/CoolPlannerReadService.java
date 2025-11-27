package org.example.coolplanner.service;

import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.CoolPlannerReadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoolPlannerReadService {

    private final CoolPlannerReadRepository readRepository;

    public CoolPlannerReadService(CoolPlannerReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    public List<Project> getActiveProjects(int employeeId) {
        return readRepository.findActiveProjects(employeeId);
    }

    public SubTask getSubTaskById(int id) {
        return readRepository.findSubTaskById(id);
    }

    public Employee findEmployeeById(int id) {
        return readRepository.findEmployeeById(id);
    }

    public List<SubProject> getActiveSubProjects(int employeeId) {
        return readRepository.findActiveSubProjects(employeeId);
    }

    public List<Task> getActiveTasks(int employeeId) {
        return readRepository.findActiveTasks(employeeId);
    }

    public List<SubTask> getActiveSubTasks(int employeeId) {
        return readRepository.findActiveSubTasks(employeeId);
    }

    public Employee login(String email, String password) {
        Employee employee = readRepository.findEmployeeByEmail(email);
        if (employee != null && employee.getPassword().equals(password)) {
            return employee;
        }
        return null;
    }

    public Project findProjectById(int id) {
        return readRepository.findProjectById(id);
    }

    public Task getTaskById(int taskID) {
        return readRepository.findTaskById(taskID);
    }

    public SubProject findSubProjectById(int id) {
        return readRepository.findSubProjectById(id);
    }

    public List<Project> getClosedProjects(int employeeId) {
        return readRepository.findClosedProjects(employeeId);
    }

}
