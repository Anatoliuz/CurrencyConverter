package com.currency.demo.repository;

import com.currency.demo.model.HistoryEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HistoryRepository extends JpaRepository<HistoryEntry, Long> {

    List<HistoryEntry> findAllByDateBetweenAndInCurIdAndOutCurId(
            LocalDateTime dateStart,
            LocalDateTime dateEnd,
            Long inCurId,
            Long outCurId
    );

}


