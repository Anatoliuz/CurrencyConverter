package com.currency.demo.repository;

import com.currency.demo.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    Optional<Currency> findByExternalId(String externalId);

}
