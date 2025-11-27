package org.example.coolplanner.controller;

import org.example.coolplanner.model.*;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.example.coolplanner.service.CoolPlannerWriteService;
import org.example.coolplanner.service.CoolPlannerReadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("")
public class DashboardController {

    private final CoolPlannerWriteService coolPlannerWriteService;
    private final CoolPlannerReadService coolPlannerReadService;

    public DashboardController(CoolPlannerWriteService coolPlannerWriteService, CoolPlannerReadService coolPlannerReadService) {
        this.coolPlannerWriteService = coolPlannerWriteService;
        this.coolPlannerReadService = coolPlannerReadService;// dependencies injection
    }

    //Hoveddashboard til at vise knapper videre til projects,sub-projects osv.
    //Bruger HttpSession og session.getAttribute "employee" for at sikre det er den rigtige bruger, hvis oplysninger vi henter.
    //Model model bruges til at sende data til HTML-siden.
    @GetMapping("/dashboard")
    public String showDashboard(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee"); // Henter bruger fra session
        if (employee == null) {
            return "redirect:/employee/login"; // Sender bruger videre til login-side, hvis bruger ikke er logget ind
        }
        //Tjekker om der rent faktisk ligger en bruger i sessionen (altså at brugeren er logget ind).
        int employeeId = employee.getEmployeeId(); // Henter brugerens ID fra employee-objektet.

        // Henter alle aktive objekter for den medarbejder, der er logget ind
        List<Project> projects = coolPlannerReadService.getActiveProjects(employeeId);
        List<SubProject> subProjects = coolPlannerReadService.getActiveSubProjects(employeeId);
        List<Task> userStories = coolPlannerReadService.getActiveTasks(employeeId);
        List<SubTask> tasks = coolPlannerReadService.getActiveSubTasks(employeeId);

        /* Lægger employee og alle lister på modellen, så vi i HTML kan
         sige "Velkommen 'firstname'" ved at skrive ${employee.firstname}
         samt vise et overblik af projekter, delprojekter, user stories og tasks */
        model.addAttribute("employee", employee);
        model.addAttribute("projects", projects);
        model.addAttribute("subProjects", subProjects);
        model.addAttribute("userStories", userStories);
        model.addAttribute("tasks", tasks);
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
        List<Project> projects = coolPlannerReadService.getActiveProjects(employeeId);

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
        List<SubProject> subProjects = coolPlannerReadService.getActiveSubProjects(employeeId);

        model.addAttribute("employee", employee);
        model.addAttribute("subProjects", subProjects);

        // viser filen: src/main/resources/template/dashboardSubProjects.html
        return "DashboardSubProjects";
    }

    // 3) Side med task
    @GetMapping("/dashboard/task")
    public String showTask(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }

        int employeeId = employee.getEmployeeId();
        List<Task> tasks = coolPlannerReadService.getActiveTasks(employeeId);

        model.addAttribute("employee", employee);
        model.addAttribute("tasks", tasks);

        // viser filen: src/main/resources/template/dashboardTasks.html
        return "DashboardTask";
    }

    // 4) Side med tasks
    @GetMapping("/dashboard/subTasks")
    public String showTasks(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/login";
        }

        int employeeId = employee.getEmployeeId();
        List<SubTask> subTasks = coolPlannerReadService.getActiveSubTasks(employeeId);

        model.addAttribute("employee", employee);
        model.addAttribute("subTasks", subTasks);

        return "DashboardSubTasks";
    }

    //Side med lukkede projekter
    @GetMapping("/projects/closedProjects")
    public String showClosedProjects(HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) return "redirect:/login";

        List<Project> closedProjects = coolPlannerReadService.getClosedProjects(employee.getEmployeeId());
        model.addAttribute("employee", employee);
        model.addAttribute("closedProjects", closedProjects);
        return "closedProjects";
    }

    //Side til ét projekt ved at bruge navn.
    @GetMapping("/projects/{projectId}")
    public String showProject(@PathVariable int projectId, Model model) {
        Project project = coolPlannerReadService.findProjectById(projectId);
        model.addAttribute("project", project);
        return "projectDetails";
    }

    @PostMapping("/projects/{projectId}/close")
    public String closeProject(@PathVariable int projectId, @RequestParam(required = false) String confirm) {

        // Checkbox sender fx value="on"
        if (confirm != null) {
            coolPlannerWriteService.closeProject(projectId); // sætter status til LUKKET
        }
        return "redirect:/projects/" + projectId;
    }
}


