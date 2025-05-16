package com.example.TaskManager.Security;

import com.example.TaskManager.Models.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getManager_id() {
        return manager_id;
    }
    public void setManager_id(String manager_id) {
        this.manager_id = manager_id;
    }
    public String getTeam_lead_id() {
        return team_lead_id;
    }
    public void setTeam_lead_id(String team_lead_id) {
        this.team_lead_id = team_lead_id;
    }
    private String manager_id;
    private String team_lead_id;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    
}
