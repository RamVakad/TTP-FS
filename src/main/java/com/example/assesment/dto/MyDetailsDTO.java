package com.example.assesment.dto;

import io.swagger.annotations.ApiModelProperty;

public class MyDetailsDTO {

    @ApiModelProperty(position = 0)
    private String email;
    @ApiModelProperty(position = 1)
    private String name;
    @ApiModelProperty(position = 2)
    private Double balance;

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

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

}
