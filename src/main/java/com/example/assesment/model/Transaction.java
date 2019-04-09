package com.example.assesment.model;


import com.example.assesment.converter.TxnTypeConverter;
import com.example.assesment.model._enum.TxnType;

import javax.persistence.*;

@Entity
@Table(name = "USER_TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="TXN_ID")
    private Long transactionId;

    @Convert( converter = TxnTypeConverter.class )
    @Column(name="TXN_TYPE", nullable = false)
    private TxnType type;

    @Column(name="TXN_DATE", nullable = false)
    private Long date;

    @Column(name="TICKER", nullable = false)
    private String ticker;

    @Column(name="SHARES", nullable = false)
    private Integer amount;

    @Column(name="PRICE", nullable = false)
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID")
    private User owner;

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public TxnType getType() {
        return type;
    }

    public void setType(TxnType type) {
        this.type = type;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
