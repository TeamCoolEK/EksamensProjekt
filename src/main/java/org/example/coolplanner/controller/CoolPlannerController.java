package org.example.coolplanner.controller;

import jakarta.servlet.http.HttpSession;
import org.example.coolplanner.model.*;
import org.example.coolplanner.service.CoolPlannerReadService;
import org.example.coolplanner.service.CoolPlannerWriteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class CoolPlannerController {

    private final CoolPlannerWriteService coolPlannerWriteService;
    private final CoolPlannerReadService coolPlannerReadService;

    public CoolPlannerController(CoolPlannerWriteService coolPlannerWriteService, CoolPlannerReadService coolPlannerReadService) {
        this.coolPlannerWriteService = coolPlannerWriteService; // dependencies injection
        this.coolPlannerReadService = coolPlannerReadService;
    }

    //ENDPOINT til createProject
    @GetMapping("/createProject")
    public String createProject(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }
        if (employee.role == EmployeeRole.Team_Member) {
            return "redirect:/dashboard/show";
        }
        model.addAttribute("project", new Project());
        return "createProject";
    }

    //ENDPOINT til at gemme project i databasen
    @PostMapping("/saveProject")
    public String saveProject(@ModelAttribute Project project, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }
        coolPlannerWriteService.createProject(project, employee);
        return "redirect:/dashboard/show";
    }

    //ENDPOINT til createSubProject
    @GetMapping("/createSubProject/{id}") //{id bruges til at tildele projectId}
    public String createSubProject(@PathVariable int id, Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }
        if (employee.role == EmployeeRole.Team_Member) {
            return "redirect:/dashboard/show";
        }
        model.addAttribute("subProject", new SubProject());
        model.addAttribute("projectId", id);
        return "createSubProject";
    }

    //ENDPOINT til at gemme subProject i databasen
    @PostMapping("/saveSubProject/{id}")
    public String saveSubProject(@PathVariable int id, @ModelAttribute SubProject subProject) {
        Project project = coolPlannerReadService.findProjectById(id);
        coolPlannerWriteService.createSubProject(subProject, project);
        coolPlannerWriteService.updateProjectTimeEstimateFromSubProjects(id);
        return "redirect:/dashboard/projects/" + project.getProjectId();
    }

    //ENDPOINT til createSubTask
    @GetMapping("/createSubTask/{id}")
    public String createSubTask(@PathVariable int id, Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }
        if (employee.role == EmployeeRole.Team_Member) {
            return "redirect:/dashboard/show";
        }
        model.addAttribute("subTask", new SubTask());
        model.addAttribute("taskId", id);
        model.addAttribute("employees",coolPlannerReadService.getAllEmployees());
        return "createSubTask";
    }

    //ENDPOINT til at gemme subTask i databasen
    @PostMapping("/saveSubTask/{id}")
    public String saveSubTask(@PathVariable int id, @ModelAttribute SubTask subTask, Model model) {
        //Find task metode i stedet for ny task her!!!!!!
        Task task = coolPlannerReadService.getTaskById(id);
        coolPlannerWriteService.createSubTask(subTask, task);
        coolPlannerWriteService.updateTaskTimeEstimateFromSubTasks(id);
        return "redirect:/dashboard/tasks/" + task.getTaskId();
    }

    //ENDPOINT til createTask
    @GetMapping("/createTask/{id}")
    public String createTask(@PathVariable int id, Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }
        if (employee.role == EmployeeRole.Team_Member) {
            return "redirect:/dashboard/show";
        }
        model.addAttribute("task", new Task());
        model.addAttribute("subProjectId", id);
        return "createTask";
    }

    //ENDPOINT til at gemme task i databasen
    @PostMapping("/saveTask/{id}")
    public String saveTask(@PathVariable int id, @ModelAttribute Task task) {
        SubProject subProject = coolPlannerReadService.findSubProjectById(id);
        coolPlannerWriteService.createTask(task, subProject);
        coolPlannerWriteService.updateSubProjectTimeEstimateFromTasks(id);
        return "redirect:/dashboard/subprojects/" + subProject.getSubProjectId();
    }

    @GetMapping("/project/{id}/edit")
    public String editProject(@PathVariable int id, Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }
        if (employee.role == EmployeeRole.Team_Member) {
            return "redirect:/dashboard/show";
        }
        Project project = coolPlannerReadService.findProjectById(id);
        if (project == null) {
            return "redirect:/dashboard/show";
        }
        model.addAttribute("project", project);
        return "editProject";
    }

    @PostMapping("/project/update/{id}")
    public String updateProject(@PathVariable int id,
                                @ModelAttribute Project form,
                                Model model) {
        form.setProjectId(id);

        try {
            coolPlannerWriteService.updateProject(form);
            return "redirect:/dashboard/projects/" + form.getProjectId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("project", form);
            model.addAttribute("errorMessage", e.getMessage());
            return "editProject";
        }
    }

    @GetMapping("/subProject/{id}/edit")
    public String editSubProject(@PathVariable int id, Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }
        if (employee.role == EmployeeRole.Team_Member) {
            return "redirect:/dashboard/show";
        }
        SubProject subProject = coolPlannerReadService.findSubProjectById(id);
        if (subProject == null) {
            return "redirect:/dashboard/show";
        }
        model.addAttribute("subProject", subProject);
        return "editSubProject";
    }

    @PostMapping("/subproject/update/{id}")
    public String updateSubProject(@PathVariable int id,
                                   @ModelAttribute SubProject form,
                                   Model model) {
        form.setSubProjectId(id);

        try {
            coolPlannerWriteService.updateSubProject(form);
            return "redirect:/dashboard/subprojects/" + form.getSubProjectId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("subProject", form);
            model.addAttribute("errorMessage", e.getMessage());
            return "editSubProject";
        }
    }

    @GetMapping("/task/{id}/edit")
    public String editTask(@PathVariable int id, Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }
        if (employee.role == EmployeeRole.Team_Member) {
            return "redirect:/dashboard/show";
        }
        Task task = coolPlannerReadService.getTaskById(id);
        if (task == null) {
            return "redirect:/dashboard/show";
        }
        model.addAttribute("task", task);
        return "editTask";
    }
    // Denne metode håndterer POST-request fra editTask.html //
    // Formularen i editTask.html sender data til /updateTask via th:action //
    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) { //@ModelAttribute binder felterne fra editTask.html til et Task-objekt //
        coolPlannerWriteService.updateTask(task); // Task-objektet sendes videre til service-laget, som håndterer opdateringen af task i databasen //
        return "redirect:/dashboard/tasks/" + task.getTaskId(); //Efter opdatering redirectes bruger til tasks //
    }

    @GetMapping("/subTask/{id}/edit")
    public String editSubTask(@PathVariable int id, Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }
        if (employee.role == EmployeeRole.Team_Member) {
            return "redirect:/dashboard/show";
        }
        SubTask subTask = coolPlannerReadService.getSubTaskById(id);
        if (subTask == null) {
            return "redirect:/dashboard/show";
        }
        model.addAttribute("subTask", subTask);
        model.addAttribute("employees", coolPlannerReadService.getAllEmployees());
        return "editSubTask";
    }

    @PostMapping("/updateSubTask")
    public String updateSubTask(@ModelAttribute SubTask subTask) {
        coolPlannerWriteService.updateSubTask(subTask);

        SubTask updatedSubTask = coolPlannerReadService.getSubTaskById(subTask.getSubTaskId());
        coolPlannerWriteService.updateTaskTimeEstimateFromSubTasks(updatedSubTask.getTaskId());

        return "redirect:/dashboard/subTasks/" + subTask.getSubTaskId();
    }


    @GetMapping("/subTask/{id}/complete")
    public String showCompleteSubTaskFrom(@PathVariable int id, Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }
        if (employee.role == EmployeeRole.Team_Member) {
            return "redirect:/dashboard/show";
        }
        SubTask subTask = coolPlannerReadService.getSubTaskById(id);
        if (subTask == null) {
            return "redirect:/dashboard/show";
        }
        model.addAttribute("subTask", subTask);
        return "completeSubTask";
    }

    @PostMapping("/subTask/{id}/complete")
    public String completeSubTask(@PathVariable int id,
                                  @RequestParam("actualTime")
                                  int actualTime) {
        coolPlannerWriteService.completeSubTask(id, actualTime);
        SubTask subTask = coolPlannerReadService.getSubTaskById(id);
        return "redirect:/dashboard/subTasks/" + id;
    }
}