package com.example.TaskManager.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.TaskManager.Models.Tasks;
import java.util.List;
import com.example.TaskManager.Security.Users;


public interface TaskRepository extends JpaRepository<Tasks,Long>{
    List<Tasks> findByAssignedBy(Users assignedBy);
}   
