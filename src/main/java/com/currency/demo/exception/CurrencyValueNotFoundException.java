package com.currency.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CurrencyValueNotFoundException extends RuntimeException {

    private final Long id;

    public CurrencyValueNotFoundException(Long id) {
        super("Currency value not found");
        this.id = id;
    }

}
