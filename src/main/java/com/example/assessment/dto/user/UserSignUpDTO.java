package com.example.assessment.dto.user;

import io.swagger.annotations.ApiModelProperty;

public class UserSignUpDTO {

    @ApiModelProperty(position = 0)
    private String email;
    @ApiModelProperty(position = 1)
    private String name;
    @ApiModelProperty(position = 2)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

