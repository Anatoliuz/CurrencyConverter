package com.currency.demo.dto;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Validated
public class HistoryDto {

    @NotNull
    private Long inputCurrencyId;
    @NotNull
    private Long outputCurrencyId;
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

}
