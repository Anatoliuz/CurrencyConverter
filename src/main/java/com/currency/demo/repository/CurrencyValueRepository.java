package com.currency.demo.repository;

import com.currency.demo.model.CurrencyValue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface CurrencyValueRepository extends JpaRepository<CurrencyValue, Long> {
    Optional<CurrencyValue> findByCurrencyIdAndDate(Long currencyId, LocalDate date);
}
