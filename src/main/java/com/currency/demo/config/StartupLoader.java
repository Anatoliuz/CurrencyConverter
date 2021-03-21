package com.currency.demo.config;

import com.currency.demo.model.Currency;
import com.currency.demo.service.CbrfService;
import com.currency.demo.service.CurrencyService;
import com.currency.demo.xml.ValCurs;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class StartupLoader {

    private final CbrfService cbrfService;
    private final CurrencyService currencyService;

    public StartupLoader(CbrfService cbrfService, CurrencyService currencyService) {
        this.cbrfService = cbrfService;
        this.currencyService = currencyService;
    }

    private static Currency transform(ValCurs.Valute valute) {
        Currency currency = new Currency();
        currency.setCharCode(valute.getCharCode());
        currency.setExternalId(valute.getID());
        currency.setName(valute.getName());
        currency.setNominal(valute.getNominal());
        currency.setNumCode(valute.getNumCode());
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        try {
            Number number = format.parse(valute.getValue());
            currency.setValue(number.doubleValue());
        } catch (ParseException parseException) {
            throw new RuntimeException("Невозможно распарсить значение курса");
        }
        return currency;
    }

    @PostConstruct
    public void init() {
        var currencies = cbrfService.getCurrencies();
        currencyService.saveAll(
                currencies.stream()
                        .map(StartupLoader::transform)
                        .collect(Collectors.toList())
        );
        return;
    }

}
