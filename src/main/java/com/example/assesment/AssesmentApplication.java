package com.example.assesment;

import com.example.assesment.model.Stock;
import com.example.assesment.model.Transaction;
import com.example.assesment.model._enum.Role;
import com.example.assesment.model.User;
import com.example.assesment.model._enum.TxnType;
import com.example.assesment.repository.StockRepository;
import com.example.assesment.repository.TransactionRepository;
import com.example.assesment.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
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
	private UserRepository userRepository;

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	public static void main(String[] args) {
		SpringApplication.run(AssesmentApplication.class, args);
	}

	@Override
	public void run(String... params) {
		User user = new User();
		user.setName("Jane Doe");
		user.setPassword(passwordEncoder.encode("user"));
		user.setEmail("user@email.com");
		user.setBalance(BigDecimal.valueOf(5000.0));
		user.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_USER)));
		userRepository.save(user);


		User admin = new User();
		admin.setName("John Doe");
		admin.setPassword(passwordEncoder.encode("admin"));
		admin.setEmail("admin@email.com");
		admin.setBalance(BigDecimal.valueOf(5000.0));
		admin.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_USER, Role.ROLE_ADMIN)));
		userRepository.save(admin);


		Transaction txn = new Transaction();
		txn.setAmount(4);
		txn.setType(TxnType.TXN_BUY);
		txn.setDate(System.currentTimeMillis());
		txn.setOwner(user);
		txn.setTicker("FAC");
		txn.setPrice(BigDecimal.valueOf(199.3));
		transactionRepository.save(txn);

		Stock s = new Stock();
		s.setOwner(user);
		s.setAmount(4);
		s.setTicker("FAC");
		stockRepository.save(s);

		Transaction txn2 = new Transaction();
		txn2.setAmount(4);
		txn2.setType(TxnType.TXN_SELL);
		txn2.setDate(System.currentTimeMillis());
		txn2.setOwner(admin);
		txn2.setTicker("FAC");
		txn2.setPrice(BigDecimal.valueOf(199.3));
		transactionRepository.save(txn2);

		Stock s2 = new Stock();
		s2.setOwner(admin);
		s2.setAmount(900);
		s2.setTicker("FAC");
		stockRepository.save(s2);


	}

}
