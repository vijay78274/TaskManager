package com.example.TaskManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

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
	@Autowired
	JavaMailSender mailSender;
	@Test
	void contextLoads() {
	}
	@Test
	void creatUser(){
		service.createUser("211340101042","Vijay Singh", "vijaynayal328@gmail.com", "2001", Role.MANAGER);
	}
	@Test 
	void findUser(){
		Users user = repository.findByEmpId("211340101042");
		System.out.println(user);
	}
	@Test
	public void sendTestEmail() {
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setTo("vijaynayal328@gmail.com");
    	message.setSubject("Test Mail");
    	message.setText("This is a test email.");
    	message.setFrom("vijaysingh7827407179@gmail.com");
		mailSender.send(message);
	}
}
