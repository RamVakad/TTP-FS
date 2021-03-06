package com.example.assessment.model;

import com.example.assessment.converter.RoleConverter;
import com.example.assessment.model._enum.Role;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="USER_ID")
    private Long userId;

    @Column(name="NAME", nullable = false)
    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    private String name;

    @Column(name="EMAIL", unique = true, nullable = false)
    @Size(min = 5, max = 255, message = "Minimum email length: 4 characters")
    private String email;

    @Size(min = 8, message = "Minimum password length: 8 characters")
    @Column(name="PASSWORD", nullable = false)
    private String password;

    @Column(name="BALANCE", nullable = false)
    private BigDecimal balance;

    @ElementCollection(fetch = FetchType.EAGER)
    @Convert( converter = RoleConverter.class )
    private List<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private Set<Stock> portfolio;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")
    private Set<Transaction> transactions;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
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

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }
}