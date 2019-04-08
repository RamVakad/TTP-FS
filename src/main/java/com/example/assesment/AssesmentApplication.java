package com.example.assesment;

import com.example.assesment.model.Role;
import com.example.assesment.model.Stock;
import com.example.assesment.model.User;
import com.example.assesment.repository.UserRepository;
import com.example.assesment.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.*;

@SpringBootApplication
public class AssesmentApplication implements CommandLineRunner {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(AssesmentApplication.class, args);
	}

	@Override
	public void run(String... params) throws Exception {
		User user = new User();
		user.setName("Jane Doe");
		user.setPassword(passwordEncoder.encode("user"));
		user.setEmail("user@email.com");
		user.setBalance(5000.0);
		user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_USER)));

		userRepository.save(user);


		User admin = new User();
		admin.setName("John Doe");
		admin.setPassword(passwordEncoder.encode("admin"));
		admin.setEmail("admin@email.com");
		admin.setBalance(5000.0);
		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_USER, Role.ROLE_ADMIN)));

		userRepository.save(admin);
	}

}
