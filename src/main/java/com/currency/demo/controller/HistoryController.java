package com.currency.demo.controller;

import com.currency.demo.dto.HistoryDto;
import com.currency.demo.service.CurrencyService;
import com.currency.demo.service.HistoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/history")
public class HistoryController {

    private final CurrencyService currencyService;
    private final HistoryService historyService;

    public HistoryController(CurrencyService currencyService, HistoryService historyService) {
        this.currencyService = currencyService;
        this.historyService = historyService;
    }

    @GetMapping
    public ModelAndView history() {
        Map<String, Object> map = new HashMap<>();
        map.put("currencies", currencyService.getList());
        map.put("historyList", Collections.emptyList());
        return new ModelAndView("history", map);
    }

    @PostMapping
    public ModelAndView post(HistoryDto dto) {
        Map<String, Object> map = new HashMap<>();

        map.put("currencies", currencyService.getList());
        map.put("inputCurrencyId", dto.getInputCurrencyId());
        map.put("outputCurrencyId", dto.getOutputCurrencyId());
        map.put("date", dto.getDate());
        map.put("historyList",
                historyService.getByDate(dto.getDate(), dto.getInputCurrencyId(), dto.getOutputCurrencyId()));
        return new ModelAndView("history", map);
    }

}
