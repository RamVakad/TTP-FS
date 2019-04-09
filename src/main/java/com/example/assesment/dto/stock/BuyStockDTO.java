package com.example.assesment.dto.stock;

import io.swagger.annotations.ApiModelProperty;

public class BuyStockDTO {
    @ApiModelProperty(position = 0)
    private String ticker;
    @ApiModelProperty(position = 1)
    private Integer amount;

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
}
