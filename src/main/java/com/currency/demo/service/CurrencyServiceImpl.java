package com.currency.demo.service;

import com.currency.demo.config.CurrenciesLoader;
import com.currency.demo.exception.CurrencyNotFoundException;
import com.currency.demo.exception.CurrencyValueNotFoundException;
import com.currency.demo.model.Currency;
import com.currency.demo.model.CurrencyValue;
import com.currency.demo.model.HistoryEntry;
import com.currency.demo.repository.CurrencyRepository;
import com.currency.demo.repository.CurrencyValueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyRepository repository;
    private final CurrencyValueRepository currencyValueRepository;
    private final HistoryService historyService;
    private final CurrenciesLoader currenciesLoader;

    public CurrencyServiceImpl(CurrencyRepository repository, CurrencyValueRepository currencyValueRepository,
                               HistoryService historyService, CurrenciesLoader currenciesLoader) {
        this.repository = repository;
        this.currencyValueRepository = currencyValueRepository;
        this.historyService = historyService;
        this.currenciesLoader = currenciesLoader;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Currency> getList() {
        return repository.findAll();
    }

    @Transactional
    @Override
    public List<Currency> saveAll(List<Currency> currencies) {
        return repository.saveAll(currencies);
    }

    @Override
    public HistoryEntry convert(Long inId, Long outId, Double amount) {
        var inCur = repository.findById(inId)
                .orElseThrow(() -> new CurrencyNotFoundException(inId));
        var outCur = repository.findById(outId)
                .orElseThrow(() -> new CurrencyNotFoundException(outId));

        var now = LocalDate.now();

        var inVal = currencyValueRepository.findByCurrencyIdAndDate(inId, now)
                .orElseGet(() -> getCurrencyValueFromCbrf(inCur, now));
        var outVal = currencyValueRepository.findByCurrencyIdAndDate(outId, now)
                .orElseGet(() -> getCurrencyValueFromCbrf(outCur, now));

        BigDecimal in = BigDecimal.valueOf(inVal.getValue());
        BigDecimal out = BigDecimal.valueOf(outVal.getValue());
        BigDecimal amountBD = BigDecimal.valueOf(amount);
        BigDecimal inNominal = BigDecimal.valueOf(inVal.getNominal());
        BigDecimal outNominal = BigDecimal.valueOf(outVal.getNominal());

        double res = in.multiply(amountBD)
                .divide(inNominal, 4, RoundingMode.HALF_DOWN)
                .multiply(outNominal)
                .divide(out, 4, RoundingMode.HALF_DOWN)
                .doubleValue();

        return historyService.create(
                new HistoryEntry(inCur, outCur, amount, res, LocalDateTime.now())
        );


    }

    private CurrencyValue getCurrencyValueFromCbrf(Currency currency, LocalDate date) {
        currenciesLoader.loadCurrencies(date);

        return currencyValueRepository.findByCurrencyIdAndDate(currency.getId(), date)
                .orElseThrow(() -> new CurrencyValueNotFoundException(currency.getId()));
    }

}
