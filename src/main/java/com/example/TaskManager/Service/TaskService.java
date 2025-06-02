package com.example.TaskManager.Service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TaskManager.Models.Priority;
import com.example.TaskManager.Models.Projects;
import com.example.TaskManager.Models.Status;
import com.example.TaskManager.Models.Tasks;
import com.example.TaskManager.Repository.ProjectRepository;
import com.example.TaskManager.Repository.TaskRepository;
import com.example.TaskManager.Security.Users;
import com.example.TaskManager.Security.UsersRepository;

@Service
public class TaskService {
    @Autowired 
    UsersRepository usersRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProjectRepository projectRepository;
    public void createTask(String teamLead, String employee, String description, String title, Status status, LocalDate dueDate, Priority priority, Long projectId){
        Optional<Projects> projectOpt = projectRepository.findById(projectId);
        Projects project = projectOpt.orElseThrow(() -> new RuntimeException("Project not found"));
        Users assignedBy = usersRepository.findByEmpId(teamLead);
        Users assignedTo = usersRepository.findByEmpId(employee);
        Tasks task = new Tasks();
        task.setAssignedBy(assignedBy);
        task.setAssignedTo(assignedTo);
        task.setDescription(description);
        task.setDueDate(dueDate);
        task.setPriority(priority);
        task.setStatus(status);
        task.setTitle(title);
        task.setProject(project);
        taskRepository.save(task);
        System.out.println(task);
    }
}
