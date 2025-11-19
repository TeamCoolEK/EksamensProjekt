package org.example.coolplanner.controller;

import org.example.coolplanner.model.Project;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class CoolPlannerController {
    @GetMapping("/createProject")
    public String createProject (Model model) {
        model.addAttribute("project", new Project());
        return "createProject";
    }
}
