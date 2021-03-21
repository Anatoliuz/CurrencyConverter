package com.currency.demo.service;

import com.currency.demo.xml.ValCurs;

import java.time.LocalDate;
import java.util.List;

public interface CbrfService {

    List<ValCurs.Valute> getCurrencies();

    List<ValCurs.Valute> getOneCurrency(LocalDate date, String valuteId);

}
