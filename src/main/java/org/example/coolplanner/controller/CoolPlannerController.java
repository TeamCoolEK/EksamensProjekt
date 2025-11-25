package org.example.coolplanner.controller;

import jakarta.servlet.http.HttpSession;
import org.example.coolplanner.model.*;
import org.example.coolplanner.repository.Rowmapper.ProjectRowMapper;
import org.example.coolplanner.model.Project;
import org.example.coolplanner.model.SubProject;
import org.example.coolplanner.model.SubTask;
import org.example.coolplanner.model.Task;
import org.example.coolplanner.service.CoolPlannerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class CoolPlannerController {

    private final CoolPlannerService coolPlannerService;

    public CoolPlannerController(CoolPlannerService coolPlannerService) {
        this.coolPlannerService = coolPlannerService;
    }
    //ENDPOINT til createProject
    @GetMapping("/createProject")
    public String createProject(Model model) {
        model.addAttribute("project", new Project());
        return "createProject";
    }
    //ENDPOINT til at gemme project i databasen
    @PostMapping("/saveProject")
    public String saveProject(@ModelAttribute Project project, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        //sætter employee ID til 0 for test
        Employee employeeTest = new Employee();
        employeeTest.setEmployeeId(1);
        coolPlannerService.createProject(project, employeeTest);
        return "redirect:/XYZ";
    }
    //ENDPOINT til createSubProject
    @GetMapping("/createSubProject/{id}") //{id bruges til at tildele projectId}
    public String createSubProject(@PathVariable int id, Model model) {
        model.addAttribute("subProject", new SubProject());
        model.addAttribute("projectId", id);
        return "createSubProject";
    }
    //ENDPOINT til at gemme subProject i databasen
    @PostMapping("/saveSubProject/{id}")
    public String saveSubProject(@PathVariable int id, @ModelAttribute SubProject subProject) {
        Project project = coolPlannerService.findProjectById(id);
        coolPlannerService.createSubProject(subProject, project);
        coolPlannerService.updateProjectTimeEstimateFromSubProjects(id);
        return "redirect:/dashboard";
    }
    //ENDPOINT til createSubTask
    @GetMapping("/createSubTask/{id}")
    public String createSubTask(@PathVariable int id, Model model) {
        model.addAttribute("subTask", new SubTask());
        model.addAttribute("taskId", id);
        return "createSubTask";
    }
    //ENDPOINT til at gemme subTask i databasen
    @PostMapping("/saveSubTask/{id}")
    public String saveSubTask(@PathVariable int id, @ModelAttribute SubTask SubTask, Model model) {
        //Find task metode i stedet for ny task her!!!!!!
        Task task = coolPlannerService.getTaskById(id);
        coolPlannerService.createSubTask(SubTask, task);
        coolPlannerService.updateTaskTimeEstimateFromSubTasks(id);
        return "redirect:/dashboard";
    }
    //ENDPOINT til createTask
    @GetMapping("/createTask/{id}")
    public String createTask(@PathVariable int id, Model model) {
        model.addAttribute("task", new Task());
        return "createTask";
    }
    //ENDPOINT til at gemme task i databasen
    @PostMapping("/saveTask/{id}")
    public String saveTask(@PathVariable int id, @ModelAttribute Task task) {
        SubProject subProject = coolPlannerService.findSubProjectById(id);
        coolPlannerService.createTask(task, subProject);
        coolPlannerService.updateSubProjectTimeEstimateFromTasks(id);

        return "redirect:/XYZ";
    }

    @GetMapping("/project/{id}/edit")
    public String editProject(@PathVariable int id, Model model) {
        Project project = coolPlannerService.findProjectById(id);
        if (project == null) {
            return "redirect:/";
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
            coolPlannerService.updateProject(form);
            return "redirect:";
        } catch (IllegalArgumentException e) {
            model.addAttribute("project", form);
            model.addAttribute("errorMessage", e.getMessage());
            return "editProject";
        }
    }

    @GetMapping("/subProject/{id}/edit")
    public String updateSubProject(@PathVariable int id,
                                   @ModelAttribute SubProject form,
                                   Model model) {
        form.setSubProjectId(id);

        try {
            coolPlannerService.updateSubProject(form);
            return "redirect:";
        } catch (IllegalArgumentException e) {
            model.addAttribute("subProject", form);
            model.addAttribute("errorMessage", e.getMessage());
            return "editSubProject";
        }
    }

    @GetMapping("/editTask/{id}")
    public String editTask(@PathVariable int id, Model model) {
        Task task = coolPlannerService.getTaskById(id);
        model.addAttribute("task", task);
        return "editTask";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        coolPlannerService.updateTask(task);
        return "redirect:/XYZ";
    }

    @GetMapping("/editSubTask/{id}")
    public String editSubTask(@PathVariable("id") int id, Model model) {
        SubTask subTask = coolPlannerService.getSubTaskById(id);
        if (subTask == null) {
            return "redirect:/";
        }
        model.addAttribute("subTask", subTask);
        return "editSubTask";
    }

    @PostMapping("/updateSubTask")
    public String updateSubTask(@ModelAttribute("subTask") SubTask subTask, Model model) {
        try {
            coolPlannerService.updateSubTask(subTask);
            return "redirect:/task/" + subTask.getTaskId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("subTask", subTask);
            model.addAttribute("errorMessage", e.getMessage());
            return "editSubTask";
        }
    }
}

