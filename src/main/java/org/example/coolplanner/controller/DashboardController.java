package org.example.coolplanner.controller;

import org.example.coolplanner.model.*;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.example.coolplanner.service.CoolPlannerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DashboardController {

    private final CoolPlannerService coolPlannerService;

    public DashboardController(CoolPlannerService coolPlannerService) {
        this.coolPlannerService = coolPlannerService;
    }

    //Hoveddashboard til at vise knapper videre til projects,sub-projects osv.
    //Bruger HttpSession og session.getAttribute "employee" for at sikre det er den rigtige bruger, hvis oplysninger vi henter.
    //Model model bruges til at sende data til HTML-siden.
    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee"); // Henter bruger fra session
        if (employee == null) {
            return "redirect:/login"; // Sender bruger videre til login-side, hvis bruger ikke er logget ind
        }
        //Tjekker om der rent faktisk ligger en bruger i sessionen (altså at brugeren er logget ind).
        int employeeId = employee.getEmployeeId(); //Henter brugerens ID fra employee-objektet.
        return "Dashboard";
    }

    // 1) Side med projekter
    @GetMapping("/dashboard/projects")
    public String showProjects(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }

        int employeeId = employee.getEmployeeId();
        List<Project> projects = coolPlannerService.getActiveProjects(employeeId);

        model.addAttribute("employee", employee);
        model.addAttribute("projects", projects);

        return "DashboardProjects";
    }

    // 2) Side med delprojekter
    @GetMapping("/dashboard/subprojects")
    public String showSubProjects(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }

        int employeeId = employee.getEmployeeId();
        List<SubProject> subProjects = coolPlannerService.getActiveSubProjects(employeeId);

        model.addAttribute("employee", employee);
        model.addAttribute("subProjects", subProjects);

        // viser filen: src/main/resources/template/dashboardSubProjects.html
        return "DashboardSubProjects";
    }

    // 3) Side med user stories
    @GetMapping("/dashboard/userstories")
    public String showUserStories(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }

        int employeeId = employee.getEmployeeId();
        List<UserStory> userStories = coolPlannerService.getActiveUserStories(employeeId);

        model.addAttribute("employee", employee);
        model.addAttribute("userStories", userStories);

        // viser filen: src/main/resources/template/dashboardUserStories.html
        return "DashboardUserStories";
    }

    // 4) Side med tasks
//    @GetMapping("/dashboard/tasks")
//    public String showTasks(HttpSession session, Model model) {
//        Employee employee = (Employee) session.getAttribute("employee");
//        if (employee == null) {
//            return "redirect:/login";
//        }
//
//        int employeeId = employee.getEmployeeId();
//        List<Task> tasks = coolPlannerService.getActiveTasks(employeeId);
//
//        model.addAttribute("employee", employee);
//        model.addAttribute("tasks", tasks);
//
//        return "DashboardTasks";
//    }
    }


