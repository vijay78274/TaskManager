package com.example.TaskManager;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.TaskManager.Models.Priority;
import com.example.TaskManager.Models.Status;
import com.example.TaskManager.Security.Users;
import com.example.TaskManager.Security.UsersRepository;
import com.example.TaskManager.Service.ProgressService;
import com.example.TaskManager.Service.ProjectService;
import com.example.TaskManager.Service.TaskService;
import com.example.TaskManager.Service.UserService;

@SpringBootTest
class TaskManagerApplicationTests {

	@Autowired 
	UserService service;
	@Autowired
	UsersRepository repository;
	@Autowired
	JavaMailSender mailSender;
	@Autowired
	ProjectService service2;
	@Autowired 
	TaskService taskService;
	@Autowired
	ProgressService progressService;
	@Test
	void contextLoads() {
	}
	@Test
	void creatUser(){
		// service.createUser("211340101030","Rahul Pandey", "rahul2003@gmail.com", "2003", Role.MANAGER);
		// service.createTeamLead("211340101008","Anshul Deoli", "anshul2004@gmail.com", "2004", Role.TEAM_LEAD, "211340101042");
		// service.createTeamLead("211340101058","Sumit Bahuguna", "sumit123@gmail.com", "sumit123", Role.TEAM_LEAD,"211340101030");
		// service.createTeamLead("211340101018","Mukul Thapliyal", "mukul123@gmail.com", "mukul123", Role.TEAM_LEAD,"211340101042");
		// service.createTeamMember("211340101019","Akash Negi", "akash123@gmail.com", "akash123", Role.EMPLOYEE,"211340101042","211340101008");
		// service.createTeamMember("211340101012","Nitin Rana", "nitin123@gmail.com", "nitin123", Role.EMPLOYEE,"211340101042","211340101008");
		// service.createTeamMember("211340101003","Ritik Sharma", "ritik123@gmail.com", "ritik123", Role.EMPLOYEE,"211340101042","211340101008");
		// service.createTeamMember("211340101007","Bipin Rawat", "bipin123@gmail.com", "bipin123", Role.EMPLOYEE,"211340101042","211340101008");
		// service.createTeamMember("211340101019","Disha Pathak", "disha123@gmail.com", "disha123", Role.EMPLOYEE,"211340101042","211340101008");

		// service.createTeamMember("211340101001","Ravi Negi", "ravi123@gmail.com", "ravi123", Role.EMPLOYEE,"211340101030","211340101058");
		// service.createTeamMember("211340101002","Sarthak Rana", "sarthak123@gmail.com", "sarthak123", Role.EMPLOYEE,"211340101030","211340101058");
		// service.createTeamMember("211340101009","Shreya Sharma", "shreya123@gmail.com", "shreya123", Role.EMPLOYEE,"211340101030","211340101058");
		// service.createTeamMember("211340101010","Lucky Rawat", "lucky123@gmail.com", "lucky123", Role.EMPLOYEE,"211340101030","211340101058");
		// service.createTeamMember("211340101011","Vedika Pathak", "vedika123@gmail.com", "vedika123", Role.EMPLOYEE,"211340101030","211340101058");

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
	@Test
	public void insertProject(){
		service2.createProject("BookCab App", "Cab booking android application for employees", "211340101042","211340101008");
		service2.createProject("Ignite DashBoard", "Company career website for students", "211340101042","211340101058");
		service2.createProject("AI data analyzer", "Tool to enhace analysis process", "211340101030","211340101018");

	}
	@Test
	public void insertTask(){
		taskService.createTask("211340101008","211340101012","Create Models and Service Logic","Backend",Status.PENDING,LocalDate.parse("2025-06-05"),Priority.HIGH,1L);
	}
	@Test
	public void insertProgress(){
		progressService.createProgress(10, "Build Security and Login Logic", 4L, "211340101012", LocalDate.parse("2025-06-03"));
	}
}
