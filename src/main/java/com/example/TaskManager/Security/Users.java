package com.example.TaskManager.Security;

import java.time.LocalDateTime;

import com.example.TaskManager.Models.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="users")
public class Users{
    @Id
    @Column(name = "emp_id")
    private String empId; 
   
    public String getEmpId() {
        return empId;
    }
    public void setEmpId(String empId) {
        this.empId = empId;
    }
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
    
    public String getManagerId() {
        return managerId;
    }
    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    public String getTeamLeadId() {
        return teamLeadId;
    }
    public void setTeamLeadId(String teamLeadId) {
        this.teamLeadId = teamLeadId;
    }
    @Column(name = "manager_id")
    private String managerId;
    @Column(name = "team_lead_id")
    private String teamLeadId;
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
    @Column(name = "otp")
    private String otp;
    @Column(name = "otp_generated_time")
    private LocalDateTime otpGeneratedTime;

    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
    public LocalDateTime getOtpGeneratedTime() {
        return otpGeneratedTime;
    }
    public void setOtpGeneratedTime(LocalDateTime otpGeneratedTime) {
        this.otpGeneratedTime = otpGeneratedTime;
    }
    public Users(String empId, String password){
        this.empId=empId;
        this.password=password;
    }
    public Users(){}
}