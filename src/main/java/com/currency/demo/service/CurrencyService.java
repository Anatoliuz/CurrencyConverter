package com.currency.demo.service;

import com.currency.demo.model.Currency;
import com.currency.demo.model.HistoryEntry;

import java.util.List;

public interface CurrencyService {

    List<Currency> getList();

    List<Currency> saveAll(List<Currency> currencies);

    HistoryEntry convert(Long inId, Long outId, Double amount);

}
