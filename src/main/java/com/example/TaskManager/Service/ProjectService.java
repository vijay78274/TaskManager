package com.example.TaskManager.Service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.TaskManager.Models.Projects;
import com.example.TaskManager.Repository.ProjectRepository;
import com.example.TaskManager.Security.Users;
@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    public Projects createProject(String name, String desc, String createdBy){
        Projects projects = new Projects();
        projects.setName(name);
        projects.setCreatedBy(createdBy);
        projects.setDescription(desc);
        projects.setCreatedAt(LocalDate.now());
        return projectRepository.save(projects);
    }
}
