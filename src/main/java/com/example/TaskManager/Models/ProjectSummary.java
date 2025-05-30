package com.example.TaskManager.Models;

import java.time.LocalDate;
import java.util.List;


import com.example.TaskManager.Security.Users;

public class ProjectSummary {
    public Long id;
    public String name;
    public String description;
    public LocalDate createdAt;
    public String createdBy;
    public LocalDate deadline;
    public String teamLeadId;
    public String teamLeadName;
    public String progress;
    public List<String> members;
    public List<Users> team;
    public String teamLeadEmail;
    public List<Users> getTeam() {
        return team;
    }
    public void setTeam(List<Users> team) {
        this.team = team;
    }
    public String getTeamLeadEmail() {
        return teamLeadEmail;
    }
    public void setTeamLeadEmail(String teamLeadEmail) {
        this.teamLeadEmail = teamLeadEmail;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDate getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public LocalDate getDeadline() {
        return deadline;
    }
    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
    public String getTeamLeadId() {
        return teamLeadId;
    }
    public void setTeamLeadId(String teamLeadId) {
        this.teamLeadId = teamLeadId;
    }
    public String getTeamLeadName() {
        return teamLeadName;
    }
    public void setTeamLeadName(String teamLeadName) {
        this.teamLeadName = teamLeadName;
    }
    public String getProgress() {
        return progress;
    }
    public void setProgress(String progress) {
        this.progress = progress;
    }
    public List<String> getMembers() {
        return members;
    }
    public void setMembers(List<String> members) {
        this.members = members;
    }
    
}
