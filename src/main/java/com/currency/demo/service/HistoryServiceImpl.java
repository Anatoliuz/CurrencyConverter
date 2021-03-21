package com.currency.demo.service;

import com.currency.demo.exception.CurrencyNotFoundException;
import com.currency.demo.model.Currency;
import com.currency.demo.model.HistoryEntry;
import com.currency.demo.repository.CurrencyRepository;
import com.currency.demo.repository.HistoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class HistoryServiceImpl implements HistoryService {

    private final HistoryRepository historyRepository;
    private final CurrencyRepository currencyRepository;

    public HistoryServiceImpl(HistoryRepository historyRepository, CurrencyRepository currencyRepository) {
        this.historyRepository = historyRepository;
        this.currencyRepository = currencyRepository;
    }

    @Transactional
    @Override
    public HistoryEntry create(HistoryEntry historyEntry) {
        return historyRepository.save(historyEntry);
    }

    @Transactional(readOnly = true)
    @Override
    public List<HistoryEntry> getByDate(LocalDate date, Long inCurId, Long outCurId) {
        Currency inCur = currencyRepository.findById(inCurId)
                .orElseThrow(() -> new CurrencyNotFoundException(inCurId));
        Currency outCur = currencyRepository.findById(outCurId)
                .orElseThrow(() -> new CurrencyNotFoundException(outCurId));

        return historyRepository.findAllByDateBetweenAndInCurAndOutCur(
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay(),
                inCur.getId(),
                outCur.getId()
        );
    }

}
