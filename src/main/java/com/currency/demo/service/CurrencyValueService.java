package com.currency.demo.service;

import com.currency.demo.model.CurrencyValue;
import com.currency.demo.repository.CurrencyValueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyValueService {

    private final CurrencyValueRepository currencyValueRepository;

    public CurrencyValueService(CurrencyValueRepository currencyValueRepository) {
        this.currencyValueRepository = currencyValueRepository;
    }

    @Transactional
    public List<CurrencyValue> merge(List<CurrencyValue> currencyValueList) {
        List<CurrencyValue> list = new ArrayList<>();
        for (CurrencyValue c : currencyValueList) {

            boolean notFound = currencyValueRepository
                    .findByCurrencyIdAndDate(c.getCurrency().getId(), c.getDate())
                    .isEmpty();
            if (notFound) {
                list.add(currencyValueRepository.save(c));
                continue;
            }
            list.add(c);
        }
        return list;
    }

}
