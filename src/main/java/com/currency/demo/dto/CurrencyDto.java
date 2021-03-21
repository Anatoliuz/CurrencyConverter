package com.currency.demo.dto;

import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Validated
public class CurrencyDto {

    @NotNull
    private Long inputCurrencyId;
    @NotNull
    private Long outputCurrencyId;
    @Positive
    private Double amount;

    public Long getInputCurrencyId() {
        return inputCurrencyId;
    }

    public void setInputCurrencyId(Long inputCurrencyId) {
        this.inputCurrencyId = inputCurrencyId;
    }

    public Long getOutputCurrencyId() {
        return outputCurrencyId;
    }

    public void setOutputCurrencyId(Long outputCurrencyId) {
        this.outputCurrencyId = outputCurrencyId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

}
