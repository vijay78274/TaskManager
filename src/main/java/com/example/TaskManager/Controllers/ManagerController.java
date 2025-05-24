package com.example.TaskManager.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/manager")
public class ManagerController {
    @GetMapping("/dashboard")
    public String dashboard() {
        return "manager-dashboard";
    }
    
}
