package com.example.TaskManager.Controllers;

import java.net.Authenticator;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.TaskManager.Models.ProjectSummary;
import com.example.TaskManager.Models.Projects;
import com.example.TaskManager.Repository.ProjectRepository;
import com.example.TaskManager.Security.MyUserDetails;
import com.example.TaskManager.Security.Users;
import com.example.TaskManager.Security.UsersRepository;
import com.example.TaskManager.Service.ProjectService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/manager")
public class ManagerController {
    @Autowired
    private ProjectRepository repository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProjectService projectService;

    @GetMapping("/dashboard")
    public String dashboard(Authentication authenticate, Model model) {
        MyUserDetails userDetails = (MyUserDetails)authenticate.getPrincipal();
        String manager = userDetails.getUsername();
        List<ProjectSummary> summaries = projectService.projectsSummary(manager);
        model.addAttribute("summaries", summaries);
        return "manager-dashboard";
    }
    @GetMapping("/projects")
    public String getMethodName(Authentication authentication, Model model) {
        MyUserDetails myUserDetails = (MyUserDetails)authentication.getPrincipal();
        String userName = myUserDetails.getUsername();
        List<Projects> projects = repository.findByCreatedBy(userName);
        model.addAttribute("projects", projects);
        return "manager-projects";
    }
    @GetMapping("/project-detail")
    public String projectDetails(@RequestParam Long id, Model model) {
        ProjectSummary projectSummary = projectService.projectsDetails(id);
        List<Users> teamMember = projectSummary.getTeam();
        model.addAttribute("summary", projectSummary);
        model.addAttribute("teams", teamMember);
        System.out.println(teamMember);
        return "project-details";
    }
}
