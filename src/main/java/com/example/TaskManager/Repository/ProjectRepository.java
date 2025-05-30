package com.example.TaskManager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TaskManager.Models.Projects;
import java.util.List;


public interface ProjectRepository extends JpaRepository<Projects,Long>{
    List<Projects> findByCreatedBy(String createdBy);
    // Projects findById(Long id);
}
