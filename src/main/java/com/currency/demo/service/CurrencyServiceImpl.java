package com.currency.demo.service;

import com.currency.demo.exception.CurrencyNotFoundException;
import com.currency.demo.model.Currency;
import com.currency.demo.model.HistoryEntry;
import com.currency.demo.repository.CurrencyRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CurrencyServiceImpl implements CurrencyService {

    private CurrencyRepository repository;
    private HistoryService historyService;

    public CurrencyServiceImpl(CurrencyRepository repository, HistoryService historyService) {
        this.repository = repository;
        this.historyService = historyService;
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
        BigDecimal in = BigDecimal.valueOf(inCur.getValue());
        BigDecimal out = BigDecimal.valueOf(outCur.getValue());
        BigDecimal amountBD = BigDecimal.valueOf(amount);
        BigDecimal inNominal = BigDecimal.valueOf(inCur.getNominal());
        BigDecimal outNominal = BigDecimal.valueOf(outCur.getNominal());
        double res = in.multiply(amountBD)
                .divide(inNominal, 4, RoundingMode.HALF_DOWN)
                .multiply(outNominal)
                .divide(out, 4, RoundingMode.HALF_DOWN)
                .doubleValue();
        HistoryEntry historyEntry = new HistoryEntry(inCur.getId(), outCur.getId(), amount, res,
                LocalDateTime.now());
        historyService.create(historyEntry);
        return historyEntry;
    }


}
