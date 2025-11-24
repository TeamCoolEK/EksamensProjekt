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

    @GetMapping("/createProject")
    public String createProject(Model model) {
        model.addAttribute("project", new Project());
        return "createProject";
    }

    @PostMapping("/saveProject")
    public String saveProject(@ModelAttribute Project project, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        //sætter employee ID til 0 for test
        Employee employeeTest = new Employee();
        employeeTest.setEmployeeId(1);
        coolPlannerService.createProject(project, employeeTest);
        return "redirect:/XYZ";
    }

    @GetMapping("/createSubProject")
    public String createSubProject(Model model) {
        model.addAttribute("subProject", new SubProject());
        return "createSubProject";
    }

    @PostMapping("/saveSubProject")
    public String saveSubProject (@ModelAttribute SubProject subProject, Model model) {
        coolPlannerService.createSubProject(subProject);
        return "redirect:/XYZ";
    }

    @GetMapping("/createSubTask")
    public String createSubTask (Model model) {
        model.addAttribute("subTask", new SubTask());
        return "createSubTask";
    }

    @PostMapping("/saveSubTask")
    public String saveSubTask (@RequestParam int taskId, @ModelAttribute SubTask SubTask, Model model) {
        //Find userStory metode istedet for ny userStory her!!!!!!
        Task task = new Task(); //
        coolPlannerService.createSubTask(SubTask, task);
        return "redirect:/XYZ";
    }

    @GetMapping("/createTask")
    public String createTask(Model model) {
        model.addAttribute("task", new Task());
        return "createTask";
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
            model.addAttribute("prject", form);
            model.addAttribute("errorMessage", e.getMessage());
            return "editProject";
        }
    }

    @GetMapping("/subProject/{id}/edit")
    public String updateSubProject(@PathVariable int id,
                                   @ModelAttribute SubProject form,
                                   Model model){
        form.setSubProjectId(id);

        try{
            coolPlannerService.updateSubProject(form);
            return "redirect:";
        } catch (IllegalArgumentException e) {
            model.addAttribute("subProject", form);
            model.addAttribute("errorMessage", e.getMessage());
            return "editSubProject";
        }
    }


    @PostMapping("/saveTask")
    public String saveTask(@ModelAttribute Task task, Model model) {
        coolPlannerService.createTask(task);
        return "redirect:/XYZ";
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

}

