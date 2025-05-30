package com.example.TaskManager.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.example.TaskManager.Models.ProjectSummary;
import com.example.TaskManager.Models.Projects;
import com.example.TaskManager.Repository.ProjectRepository;
import com.example.TaskManager.Security.Users;
import com.example.TaskManager.Security.UsersRepository;
@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UsersRepository usersRepository;
    public Projects createProject(String name, String desc, String createdBy, String team_lead_id){
        Projects projects = new Projects();
        projects.setName(name);
        projects.setCreatedBy(createdBy);
        projects.setDescription(desc);
        projects.setTeamLead(team_lead_id);
        projects.setCreatedAt(LocalDate.now());
        return projectRepository.save(projects);
    }
    public List<ProjectSummary> projectsSummary(String manager){
        List<ProjectSummary> list = new ArrayList<>();
        List<Projects> project = projectRepository.findByCreatedBy(manager);
        for(Projects items : project){
            ProjectSummary summary = new ProjectSummary();
            summary.setId(items.id);
            summary.setCreatedAt(items.createdAt);
            summary.setCreatedBy(items.createdBy);
            summary.setName(items.name);
            summary.setDeadline(items.deadline);
            summary.setTeamLeadId(items.teamLeadId);
            Users user = usersRepository.findByEmpId(items.getTeamLead());
            String teamLeadName = user.getName();
            summary.setTeamLeadName(teamLeadName);
            List<Users> team = usersRepository.findByTeamLeadId(items.teamLeadId);
            List<String> teamMember = new ArrayList<>();
            for(int i=0;i<team.size();i++){
                teamMember.add(team.get(i).getName());
            }
            summary.setMembers(teamMember);
            list.add(summary);
        }
        return list;
    }
    public ProjectSummary projectsDetails(Long id){
        Optional<Projects> projectOpt = projectRepository.findById(id);
        Projects project = projectOpt.orElseThrow(() -> new RuntimeException("Project not found"));
        ProjectSummary summary = new ProjectSummary();
        summary.setName(project.getName());
        summary.setDescription(project.getDescription());
        summary.setCreatedAt(project.getCreatedAt());
        summary.setDeadline(project.getDeadline());
        String teamLead = project.getTeamLead();
        summary.setTeamLeadId(teamLead);
        Users user = usersRepository.findByEmpId(teamLead);
        summary.setTeamLeadName(user.getName());
        summary.setTeamLeadEmail(user.getEmail());
        List<Users> members = usersRepository.findByTeamLeadId(teamLead);
        summary.setTeam(members);
        return summary;
    }
}
