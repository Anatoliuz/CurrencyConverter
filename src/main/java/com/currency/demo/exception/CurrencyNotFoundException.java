package com.currency.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencyNotFoundException extends RuntimeException {

    private final Long id;

    public CurrencyNotFoundException(Long id) {
        super("Currency not found");
        this.id = id;
    }

}
