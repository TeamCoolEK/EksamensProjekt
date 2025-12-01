package org.example.coolplanner.controller;

import jakarta.servlet.http.HttpSession;
import org.example.coolplanner.model.*;
import org.example.coolplanner.model.Project;
import org.example.coolplanner.model.SubProject;
import org.example.coolplanner.model.SubTask;
import org.example.coolplanner.model.Task;
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
        this.coolPlannerWriteService = coolPlannerWriteService; // dependencies injection !!
        this.coolPlannerReadService = coolPlannerReadService;
    }

    //ENDPOINT til createProject
    @GetMapping("/createProject")
    public String createProject(Model model, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return "redirect:/employee/login";
        }
        model.addAttribute("project", new Project());
        return "createProject";
    }

    //ENDPOINT til at gemme project i databasen
    @PostMapping("/saveProject")
    public String saveProject(@ModelAttribute Project project, HttpSession session) {
        Employee employee = (Employee) session.getAttribute("employee");
        coolPlannerWriteService.createProject(project, employee);
        return "redirect:/dashboard";
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
        Project project = coolPlannerReadService.findProjectById(id);
        coolPlannerWriteService.createSubProject(subProject, project);
        coolPlannerWriteService.updateProjectTimeEstimateFromSubProjects(id);
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
    public String saveSubTask(@PathVariable int id, @ModelAttribute SubTask subTask, Model model) {
        //Find task metode i stedet for ny task her!!!!!!
        Task task = coolPlannerReadService.getTaskById(id);
        coolPlannerWriteService.createSubTask(subTask, task);
        coolPlannerWriteService.updateTaskTimeEstimateFromSubTasks(id);
        return "redirect:/dashboard";
    }

    //ENDPOINT til createTask
    @GetMapping("/createTask/{id}")
    public String createTask(@PathVariable int id, Model model) {
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
        return "redirect:/dashboard";
    }

    @GetMapping("/project/{id}/edit")
    public String editProject(@PathVariable int id, Model model) {
        Project project = coolPlannerReadService.findProjectById(id);
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
            coolPlannerWriteService.updateProject(form);
            return "redirect:";
        } catch (IllegalArgumentException e) {
            model.addAttribute("project", form);
            model.addAttribute("errorMessage", e.getMessage());
            return "editProject";
        }
    }

    @GetMapping("/subProject/{id}/edit")
    public String editSubProject(@PathVariable int id, Model model) {
        SubProject subProject = coolPlannerReadService.findSubProjectById(id);
        if (subProject == null) {
            return "redirect:/";
        }
        model.addAttribute("subProject", subProject);
        return "editSubProject";
    }

    @GetMapping("/subProject/{id}/update")
    public String updateSubProject(@PathVariable int id,
                                   @ModelAttribute SubProject form,
                                   Model model) {
        form.setSubProjectId(id);

        try {
            coolPlannerWriteService.updateSubProject(form);
            return "redirect:";
        } catch (IllegalArgumentException e) {
            model.addAttribute("subProject", form);
            model.addAttribute("errorMessage", e.getMessage());
            return "editSubProject";
        }
    }

    @GetMapping("/task/{id}/edit")
    public String editTask(@PathVariable int id, Model model) {
        Task task = coolPlannerReadService.getTaskById(id);
        model.addAttribute("task", task);
        return "editTask";
    }

    @PostMapping("/updateTask")
    public String updateTask(@ModelAttribute Task task) {
        coolPlannerWriteService.updateTask(task);
        return "redirect:/dashboard";
    }

    @GetMapping("/subTask/{id}/edit")
    public String editSubTask(@PathVariable("id") int id, Model model) {
        SubTask subTask = coolPlannerReadService.getSubTaskById(id);
        if (subTask == null) {
            return "redirect:/";
        }
        model.addAttribute("subTask", subTask);
        return "editSubTask";
    }

    @PostMapping("/updateSubTask/{id}")
    public String updateSubTask(@PathVariable int id,
                                @ModelAttribute("subTask") SubTask subTask,
                                Model model) {
        try {
            coolPlannerWriteService.updateSubTask(subTask);
            return "redirect:/dashboard/subTask/" + subTask.getTaskId();
        } catch (IllegalArgumentException e) {
            model.addAttribute("subTask", subTask);
            model.addAttribute("errorMessage", e.getMessage());
            return "editSubTask";
        }
    }

    @GetMapping("/subTask/{id}/complete")
    public String showCompleteSubTaskFrom(@PathVariable int id, Model model) {
        SubTask subTask = coolPlannerReadService.getSubTaskById(id);
        if (subTask == null) {
            return "redirect:/dashboard";
        }
        model.addAttribute("subTask", subTask);
        return "completeSubTask";
    }

    @PostMapping("/subTask/{id}/complete")
    public String completeSubTask(@PathVariable int id,
                                  @RequestParam("actualTime")
                                  int actualTime) {
        coolPlannerWriteService.completeSubTask(id, actualTime);
        return "redirect:/dashboard";
    }
}