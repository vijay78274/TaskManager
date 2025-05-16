package com.example.TaskManager.Models;

import java.time.LocalDate;

import com.example.TaskManager.Security.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tasks")
public class Tasks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate dueDate;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate due_date) {
        this.dueDate = due_date;
    }
    @ManyToOne
    private Projects project;

    @ManyToOne
    private Users assignedBy;

    @ManyToOne
    private Users assignedTo;

    @ManyToOne
    private Tasks parentTask;

    public Priority getPriority() {
        return priority;
    }
    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    public Projects getProject() {
        return project;
    }
    public void setProject(Projects project) {
        this.project = project;
    }
    public Users getAssignedBy() {
        return assignedBy;
    }
    public void setAssignedBy(Users assignedBy) {
        this.assignedBy = assignedBy;
    }
    public Users getAssignedTo() {
        return assignedTo;
    }
    public void setAssignedTo(Users assignedTo) {
        this.assignedTo = assignedTo;
    }
    public Tasks getParentTask() {
        return parentTask;
    }
    public void setParentTask(Tasks parentTask) {
        this.parentTask = parentTask;
    }
    
}
