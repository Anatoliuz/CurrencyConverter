package com.currency.demo.service;

import com.currency.demo.model.HistoryEntry;

import java.time.LocalDate;
import java.util.List;

public interface HistoryService {

    HistoryEntry create(HistoryEntry historyEntry);

    List<HistoryEntry> getByDate(LocalDate date, Long inCurId, Long outCurId);

}
