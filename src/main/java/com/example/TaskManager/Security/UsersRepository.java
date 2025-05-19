package com.example.TaskManager.Security;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,String> {
    Optional<Users> findByEmail(String email);
    Users findByEmpId(String empId);
    List<Users> findAll();
}
