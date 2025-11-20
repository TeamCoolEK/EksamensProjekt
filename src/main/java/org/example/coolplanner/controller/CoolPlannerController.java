package org.example.coolplanner.controller;

import org.example.coolplanner.model.Project;
import org.example.coolplanner.model.SubProject;
import org.example.coolplanner.model.Task;
import org.example.coolplanner.model.UserStory;
import org.example.coolplanner.repository.Rowmapper.ProjectRowMapper;
import org.example.coolplanner.service.CoolPlannerService;
import org.springframework.jdbc.core.JdbcTemplate;
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
    public String saveProject(@ModelAttribute Project project, Model model) {
        coolPlannerService.createProject(project);
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

    @GetMapping("/createTask")
    public String createTask (Model model) {
        model.addAttribute("task", new Task());
        return "createTask";
    }

    @PostMapping("/saveTask")
    public String saveTask (@RequestParam int userStoryId, @ModelAttribute Task task, Model model) {
        //Find userStory metode istedet for ny userStory her!!!!!!
        UserStory userStory = new UserStory(); //
        coolPlannerService.createTask(task, userStory);
        return "redirect:/XYZ";
    }

    @GetMapping("/createUserStory")
    public String createUserStory(Model model) {
        model.addAttribute("userStory", new UserStory());
        return "createUserStory";
    }

}
