package com.example.TaskManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.TaskManager.Models.Role;
import com.example.TaskManager.Security.Users;
import com.example.TaskManager.Security.UsersRepository;
import com.example.TaskManager.Service.UserService;

@SpringBootTest
class TaskManagerApplicationTests {

	@Autowired 
	UserService service;
	@Autowired
	UsersRepository repository;
	@Test
	void contextLoads() {
	}
	@Test
	void creatUser(){
		service.createUser("Vijay Singh", "vijaynayal328@gmail.com", "vijay@123", Role.MANAGER);
	}
	@Test 
	void findUser(){
		Users user = repository.findByEmail("vijaynayal328@gmail.com");
		System.out.println(user);
	}
}
