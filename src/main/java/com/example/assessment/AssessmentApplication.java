package com.example.assessment;

import com.example.assessment.service.StockService;
import com.example.assessment.service.UserService;
import org.hibernate.SessionFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import java.util.*;

@SpringBootApplication
public class AssessmentApplication implements CommandLineRunner {

	/*
		Set the default timezone to UTC for the entire server.
	 */
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	/*
		Bean: Model Mapper
		http://modelmapper.org/
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public static void main(String[] args) {
		SpringApplication.run(AssessmentApplication.class, args);
	}

	/*
		Test data injection.
	 */

	@Autowired
	private UserService userService;

	@Autowired
	private StockService stockService;


	@Override
	public void run(String... params) {
		/*
		//Initiate DB with two test users, one admin and one user.
		User user = new User();
		user.setName("Jane Doe");
		user.setEmail("user@email.com");
		user.setPassword("password");
		userService.signUp(user);


		User user2 = new User();
		user2.setName("John Doe");
		user2.setEmail("user2@email.com");
		user2.setPassword("password");
		userService.signUp(user2);
		userService.makeAdmin(user2); //Make user2 an admin.


		//Buy order for Apple
		OrderDTO aaplOrder = new OrderDTO();
		aaplOrder.setTicker("AAPL");
		aaplOrder.setAmount(3);

		//Buy order for Facebook
		OrderDTO fbOrder = new OrderDTO();
		fbOrder.setTicker("FB");
		fbOrder.setAmount(3);

		//Make user 1 buy the apple stock.
		stockService.buyStock(user, aaplOrder);

		//Make user 2 buy the facebook stock.
		stockService.buyStock(user2, fbOrder);

		 */
	}

}
