package com.example.TaskManager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TaskManager.Models.Progress;
import com.example.TaskManager.Models.Tasks;

public interface ProgressRepository extends JpaRepository<Progress,Long>{
    Progress findByTask(Tasks task);
}
