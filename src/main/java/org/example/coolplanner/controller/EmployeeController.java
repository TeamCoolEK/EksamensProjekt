package org.example.coolplanner.controller;

import jakarta.servlet.http.HttpSession;
import org.example.coolplanner.model.Employee;
import org.example.coolplanner.service.CoolPlannerReadService;
import org.example.coolplanner.service.CoolPlannerWriteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    private final CoolPlannerWriteService service;
    private final CoolPlannerReadService readService;

    public EmployeeController(CoolPlannerWriteService service, CoolPlannerReadService readService) {
        this.service = service; // dependencies injection
        this.readService = readService;
    }

    @GetMapping("/createEmployee")
    public String createEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        return "createEmployee";
    }

    @PostMapping("/saveEmployee")
    public String saveEmployee(@ModelAttribute Employee employee, Model model) {
        try {
            service.createEmployee(employee);
            return "redirect:/";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("employee", employee);
            return "createEmployee";
        }
    }

    @GetMapping("/edit/{id}")
    public String editEmployee(@PathVariable int id, Model model) {
        Employee employee = readService.findEmployeeById(id);
        model.addAttribute("employee", employee);
        return "editEmployee";
    }

    @PostMapping("/update")
    public String updateEmployee(@ModelAttribute Employee employee) {
        service.updateEmployee(employee);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, HttpSession session, Model model){
        Employee employee = readService.login(email, password);

        if (employee != null){
            session.setAttribute("employee", employee);
            session.setMaxInactiveInterval(900);
            return "redirect:/dashboard/show";
        }

        model.addAttribute("wrongCredentials", true);
        return "login";
    }
}
