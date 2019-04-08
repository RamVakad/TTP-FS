package com.example.assesment.controller;

import com.example.assesment.dto.MyDetailsDTO;
import com.example.assesment.dto.UserSignUpDTP;
import com.example.assesment.model.User;
import com.example.assesment.service.UserService;
import io.swagger.annotations.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/users")
@Api(tags = "users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/signIn")
    @ApiOperation(value = "Authenticates user and returns a JWT.")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Invalid username/password supplied")})
    public String signIn(
                        @ApiParam("Email") @RequestParam String email, //
                        @ApiParam("Password") @RequestParam String password) {
        return userService.signIn(email, password);
    }

    @PostMapping("/signUp")
    @ApiOperation(value = "Creates user and returns a JWT token")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 422, message = "Email is already in use"),
            @ApiResponse(code = 422, message = "Invalid email provided")})
    public String newUser(@ApiParam("New User") @RequestBody UserSignUpDTP user) {
        return userService.signUp(modelMapper.map(user, User.class));
    }

    @DeleteMapping(value = "/{email}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "Deletes a user account by email and returns the email of the account deleted.")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"),
            @ApiResponse(code = 403, message = "Access denied"),
            @ApiResponse(code = 404, message = "The user doesn't exist"),
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public String delete(@ApiParam("email") @PathVariable String email) {
        userService.delete(email);
        return email;
    }

    @GetMapping(value = "/me")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ApiOperation(value = "Return's the current user details.")
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 403, message = "Expired or invalid JWT token")})
    public MyDetailsDTO getMyDetails(HttpServletRequest req) {
        return modelMapper.map(userService.whoIs(req), MyDetailsDTO.class);
    }

    /*
    @GetMapping(value = "/{username}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ApiOperation(value = "${UserController.search}", response = UserFullDTO.class)
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Something went wrong"), //
            @ApiResponse(code = 403, message = "Access denied"), //
            @ApiResponse(code = 404, message = "The user doesn't exist"), //
            @ApiResponse(code = 500, message = "Expired or invalid JWT token")})
    public UserFullDTO search(@ApiParam("Email") @PathVariable String email) {
        return modelMapper.map(userService.search(email), UserFullDTO.class);
    }
    */
}