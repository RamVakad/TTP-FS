package com.example.assessment.dto.stock;

import io.swagger.annotations.ApiModelProperty;

public class StockDTO {

    @ApiModelProperty(position = 0)
    private Long stockId;

    @ApiModelProperty(position = 1)
    private String ticker;

    @ApiModelProperty(position = 2)
    private Integer amount;

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
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
}
