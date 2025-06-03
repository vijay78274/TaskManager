package com.example.TaskManager.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TaskManager.Models.Progress;
import com.example.TaskManager.Models.Projects;
import com.example.TaskManager.Models.Tasks;
import com.example.TaskManager.Repository.ProgressRepository;
import com.example.TaskManager.Repository.ProjectRepository;
import com.example.TaskManager.Repository.TaskRepository;
import com.example.TaskManager.Security.Users;
import com.example.TaskManager.Security.UsersRepository;

@Service
public class ProgressService {

    @Autowired
    ProjectRepository projectRepository;
    @Autowired 
    UsersRepository usersRepository;
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    ProgressRepository progressRepository;

    public void createProgress(int percentDone, String summary, Long task_id, String user_id, LocalDate date){
        Users users = usersRepository.findByEmpId(user_id);
        Optional<Tasks> tasksOps = taskRepository.findById(task_id);
        Tasks task = tasksOps.orElseThrow(() -> new RuntimeException("Task not found"));
        Progress progress = new Progress();
        progress.setPercentDone(percentDone);
        progress.setSummary(summary);
        progress.setTask(task);
        progress.setUser(users);
        progress.setWeekStart(date);
        progressRepository.save(progress);
    }

    public List<Progress> getProgressForManager(Long id){
        Optional<Projects> projectOpt = projectRepository.findById(id);
        Projects project = projectOpt.orElseThrow(() -> new RuntimeException("Project not found"));
        List<Tasks> tasks = taskRepository.findByProject(project);
        List<Progress> progresses = new ArrayList<>();
        for(Tasks task : tasks){
            Progress progress = progressRepository.findByTask(task);
            progresses.add(progress);
        }
        return progresses;
    }
}
