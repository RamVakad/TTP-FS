package com.example.assesment.dto.user;

import com.example.assesment.model.Stock;
import com.example.assesment.model._enum.Role;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Set;

public class FullDetailsDTO {

    @ApiModelProperty(position = 0)
    private Long userId;
    @ApiModelProperty(position = 1)
    private String email;
    @ApiModelProperty(position = 2)
    private String name;
    @ApiModelProperty(position = 3)
    private Double balance;
    @ApiModelProperty(position = 4)
    private List<Role> roles;
    @ApiModelProperty(position = 5)
    private Set<Stock> portfolio;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

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

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Set<Stock> getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Set<Stock> portfolio) {
        this.portfolio = portfolio;
    }
}
