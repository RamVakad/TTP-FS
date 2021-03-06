package com.example.assessment.service;

import com.example.assessment.exception.CustomException;
import com.example.assessment.model._enum.Role;
import com.example.assessment.model.User;
import com.example.assessment.repository.UserRepository;
import com.example.assessment.security.jwt.JWTProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    /*
        Authenticate User & Return Token
     */
    public String signIn(String email, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return jwtProvider.createToken(email, userRepository.findByEmail(email).getRoles());
        } catch (AuthenticationException e) {
            throw new CustomException("Invalid username/password supplied", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /*
        Create User & Return Token
     */
    public String signUp(User user) {
        //Check for valid email.
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-z A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (!pat.matcher(user.getEmail()).matches()) {
            throw new CustomException("Invalid email provided", HttpStatus.UNPROCESSABLE_ENTITY);
        }


        if (!userRepository.existsByEmail(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setBalance(BigDecimal.valueOf(5000.0));
            user.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_USER)));
            userRepository.save(user);
            return jwtProvider.createToken(user.getEmail(), user.getRoles());
        } else {
            throw new CustomException("Email is already in use", HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    /*
        Make a user an admin.
     */
    public void makeAdmin(User user) {
        user.getRoles().add(Role.ROLE_ADMIN);
        userRepository.save(user);
    }

    /*
        Delete a user.
     */
    public void delete(String email) {
        userRepository.deleteByEmail(email);
    }

    /*
        Search for a user using email
     */
    public User search(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("The user doesn't exist", HttpStatus.NOT_FOUND);
        }
        return user;
    }

    public void deductBalance(User user, BigDecimal x) {
        user.setBalance(user.getBalance().subtract(x));
        userRepository.save(user);
    }

    public void addBalance(User user, BigDecimal x) {
        user.setBalance(user.getBalance().add(x));
        userRepository.save(user);
    }

    /*
        Resolve user using the HTTP request.
     */
    public User whoIs(HttpServletRequest req) {
        return this.search(jwtProvider.getEmail(jwtProvider.resolveToken(req)));
    }

}