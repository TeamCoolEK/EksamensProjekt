package org.example.coolplanner.controller;

import org.example.coolplanner.model.*;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;
import org.example.coolplanner.service.CoolPlannerWriteService;
import org.example.coolplanner.service.CoolPlannerReadService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final CoolPlannerWriteService coolPlannerWriteService;
    private final CoolPlannerReadService coolPlannerReadService;

    public DashboardController(CoolPlannerWriteService coolPlannerWriteService, CoolPlannerReadService coolPlannerReadService) {
        this.coolPlannerWriteService = coolPlannerWriteService;
        this.coolPlannerReadService = coolPlannerReadService;// dependencies injection
    }

    //Hoved dashboard til at vise knapper videre til projects, sub-projects osv.
    //Bruger HttpSession og session.getAttribute "employee" for at sikre det er den rigtige bruger, hvis oplysninger vi henter.
    //Model, model bruges til at sende data til HTML-siden.
    @GetMapping("/show")
    public String showDashboard(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee"); // Henter bruger fra session
        if (employee == null) {
            return "redirect:/employee/login"; // Sender bruger videre til login-side, hvis bruger ikke er logget ind
        }
        //Tjekker om der rent faktisk ligger en bruger i sessionen (altså at brugeren er logget ind).
        int employeeId = employee.getEmployeeId(); // Henter brugerens ID fra employee-objektet.

        List<Project> projects = new ArrayList<>();

        if (employee.role == EmployeeRole.Manager) {
            // Henter alle aktive objekter for den medarbejder, der er logget ind
            projects = coolPlannerReadService.getActiveProjects(employeeId);
        } else if (employee.role == EmployeeRole.Team_Member) {
            projects = coolPlannerReadService.findProjectsForTeamMember(employeeId);
        }
        /* Lægger employee og alle lister på modellen, så vi i HTML kan
         sige "Velkommen 'firstname'" ved at skrive ${employee.firstname}
         samt vise et overblik af projekter */
        model.addAttribute("employee", employee);
        model.addAttribute("projects", projects);

        return "Dashboard";
    }

    // 1) Side med projekter
    @GetMapping("/projects/{id}")
    public String showProjects(@PathVariable int id, HttpSession session, Model model) {
        //henter employee session og tildeler dens id
        Employee employee = (Employee) session.getAttribute("employee");

        //Redirect til login hvis sessionen er tom/udløbet
        if (employee == null) {
            return "redirect:/employee/login";
        }
        //Henter projektet
        Project project = coolPlannerReadService.findProjectById(id);
        //Henter liste af projektets delprojekter
        List<SubProject> subProjects = coolPlannerReadService.getActiveSubProjects(project.getProjectId());

        double dailyHours = coolPlannerWriteService.calculateDailyHours(project);

        int remainingEstimate = coolPlannerWriteService.
                calculateProjectRemainingTimeEstimateFromSubProjects(project.getProjectId());


        model.addAttribute("employee", employee);
        model.addAttribute("project", project);
        model.addAttribute("subProjects", subProjects);
        model.addAttribute("dailyHours", dailyHours);
        model.addAttribute("remainingEstimate", remainingEstimate);

        return "DashboardProjects";
    }

    // 2) Side med delprojekter
    @GetMapping("/subprojects/{id}")
    public String showSubProjects(@PathVariable int id, HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }

        SubProject subProject = coolPlannerReadService.findSubProjectById(id);
        List<Task> tasks = coolPlannerReadService.getActiveTasks(id);

        int remainingEstimate = coolPlannerWriteService.
                calculateSubProjectRemainingTimeEstimateFromTasks(subProject.getSubProjectId());

        model.addAttribute("employee", employee);
        model.addAttribute("subProject", subProject);
        model.addAttribute("tasks", tasks);
        model.addAttribute("remainingEstimate", remainingEstimate);

        // viser filen: src/main/resources/template/dashboardSubProjects.html
        return "DashboardSubProjects";
    }

    // 3) Side med task
    @GetMapping("/tasks/{id}")
    public String showTask(@PathVariable int id, HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }

        Task task = coolPlannerReadService.getTaskById(id);
        List<SubTask> subTasks = coolPlannerReadService.getActiveSubTasks(id);

        int remainingEstimate = coolPlannerWriteService.
                calculateTaskRemainingTimeEstimateFromSubTasks(task.getTaskId());

        model.addAttribute("employee", employee);
        model.addAttribute("task", task);
        model.addAttribute("subTasks", subTasks);
        model.addAttribute("remainingEstimate", remainingEstimate);

        // viser filen: src/main/resources/template/dashboardTasks.html
        return "DashboardTask";
    }

    // 4) Side med subtasks
    @GetMapping("/subTasks/{id}")
    public String showTasks(@PathVariable int id, HttpSession session, Model model) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }

        SubTask subTask = coolPlannerReadService.getSubTaskById(id);

        Employee responsibleEmployee = null;
        if(subTask.getEmployeeId() != 0) {
            responsibleEmployee = coolPlannerReadService.findEmployeeById(subTask.getEmployeeId());
        }

        model.addAttribute("employee", employee);
        model.addAttribute("subTask", subTask);
        model.addAttribute("responsibleEmployee", responsibleEmployee);

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

    @PostMapping("/projects/{id}/close")
    public String closeProject(@PathVariable int id, @RequestParam(required = false) String confirm) {

            coolPlannerWriteService.closeProject(id); // sætter status til LUKKET
            return "redirect:/dashboard/show";
        }

    }


