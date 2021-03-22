package com.currency.demo.service;

import com.currency.demo.xml.ValCurs;
import com.currency.demo.xml.ValCursRecord;

import java.time.LocalDate;
import java.util.List;

public interface CbrfService {

    List<ValCurs.Valute> getCurrencies(LocalDate date);

    ValCursRecord.Record getOneCurrency(LocalDate date, String valuteId);

}
