package com.currency.demo.config;

import com.currency.demo.model.Currency;
import com.currency.demo.model.CurrencyValue;
import com.currency.demo.repository.CurrencyRepository;
import com.currency.demo.service.CbrfService;
import com.currency.demo.service.CurrencyValueService;
import com.currency.demo.xml.ValCurs;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.stream.Collectors;

@Component
public class CurrenciesLoader {

    private final CbrfService cbrfService;
    private final CurrencyValueService currencyValueService;
    private final CurrencyRepository currencyRepository;

    public CurrenciesLoader(CbrfService cbrfService, CurrencyValueService currencyValueService,
                            CurrencyRepository currencyRepository) {
        this.cbrfService = cbrfService;
        this.currencyValueService = currencyValueService;
        this.currencyRepository = currencyRepository;
    }

    @PostConstruct
    public void init() {
        loadCurrencies(LocalDate.now());
    }

    @Transactional
    public void loadCurrencies(LocalDate date) {
        var currencies = cbrfService.getCurrencies(date);
        currencyValueService.merge(
                currencies.stream()
                        .map(this::transform)
                        .collect(Collectors.toList())
        );
    }

    @Transactional
    public CurrencyValue transform(ValCurs.Valute valute) {
        Currency currency = currencyRepository.findByExternalId(valute.getID())
                .orElse(new Currency());
        CurrencyValue currencyValue = new CurrencyValue();

        currency.setCharCode(valute.getCharCode());
        currency.setExternalId(valute.getID());
        currency.setName(valute.getName());
        currency.setNumCode(valute.getNumCode());

        currencyValue.setCurrency(
                currencyRepository.save(currency)
        );

        currencyValue.setNominal(valute.getNominal());
        currencyValue.setDate(LocalDate.now());
        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        try {
            Number number = format.parse(valute.getValue());
            currencyValue.setValue(number.doubleValue());
        } catch (ParseException parseException) {
            throw new RuntimeException("Невозможно распарсить значение курса");
        }
        return currencyValue;
    }

}
