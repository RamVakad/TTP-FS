package com.example.assesment.dto.transaction;

import com.example.assesment.model._enum.TxnType;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class TransactionDTO {

    @ApiModelProperty(position = 0)
    private Long transactionId;

    @ApiModelProperty(position = 1)
    private TxnType type;

    @ApiModelProperty(position = 2)
    private Long date;

    @ApiModelProperty(position = 3)
    private String ticker;

    @ApiModelProperty(position = 4)
    private Integer amount;

    @ApiModelProperty(position = 5)
    private BigDecimal price;

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
