package org.example.coolplanner.controller;

import org.example.coolplanner.model.Project;
import org.example.coolplanner.model.UserStory;
import org.example.coolplanner.service.CoolPlannerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/createUserStory")
    public String createUserStory(Model model) {
        model.addAttribute("userStory", new UserStory());
        return "createUserStory";
    }

    @PostMapping("/saveUserStory")
    public String saveUserStory(@ModelAttribute UserStory userStory, Model model) {
        coolPlannerService.createUserStory(userStory);
        return "redirect:/XYZ";
    }
}
