package org.example.coolplanner.controller;

import org.example.coolplanner.model.Employee;
import org.example.coolplanner.repository.CoolPlannerRepository;
import org.example.coolplanner.service.CoolPlannerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final CoolPlannerService service;
    private final CoolPlannerRepository repository;

    public EmployeeController(CoolPlannerService service, CoolPlannerRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping("/createEmployee")
    public String createEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "createEmployee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee, Model model) {
        service.createEmployee(employee);
        return "redirect:/index";
    }

}
