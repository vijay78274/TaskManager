package com.example.TaskManager.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.TaskManager.Models.Role;
import com.example.TaskManager.Security.Users;
import com.example.TaskManager.Security.UsersRepository;

@Service
public class UserService {
    @Autowired
    private UsersRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public Users createUser(Users user){
        return repository.save(user);
    }

    public void createUser(String emp_id, String name, String email, String rawPassword, Role role){
        Users user = new Users();
        user.setEmpId(emp_id);
        user.setEmail(email);
        user.setName(name);
        user.setRole(role);
        String password = passwordEncoder.encode(rawPassword);
        user.setPassword(password);
        repository.save(user);
        // System.out.println(user);
    }

    public void createTeamLead(String emp_id, String name, String email, String rawPassword, Role role, String manager_id){
        Users user = new Users();
        user.setEmpId(emp_id);
        user.setEmail(email);
        user.setName(name);
        user.setRole(role);
        user.setManagerId(manager_id);
        String password = passwordEncoder.encode(rawPassword);
        user.setPassword(password);
        repository.save(user);
        // System.out.println(user);
    }
    public void createTeamMember(String emp_id, String name, String email, String rawPassword, Role role, String manager_id, String team_lead_id){
        Users user = new Users();
        user.setEmpId(emp_id);
        user.setEmail(email);
        user.setName(name);
        user.setRole(role);
        user.setManagerId(manager_id);
        user.setTeamLeadId(team_lead_id);
        String password = passwordEncoder.encode(rawPassword);
        user.setPassword(password);
        repository.save(user);
        // System.out.println(user);
    }
    public List<Users> getAllUsers(){
        return repository.findAll();
    }
}