package com.currency.demo.controller;

import com.currency.demo.dto.CurrencyDto;
import com.currency.demo.model.HistoryEntry;
import com.currency.demo.service.CurrencyService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/converter")
public class ConverterController {

    private final CurrencyService service;

    public ConverterController(CurrencyService service) {
        this.service = service;
    }

    @GetMapping
    public ModelAndView converter() {
        Map<String, Object> map = new HashMap<>();
        map.put("currencies", service.getList());
        return new ModelAndView("converter", map);
    }

    @PostMapping
    public ModelAndView post(CurrencyDto dto) {
        HistoryEntry convert = service.convert(dto.getInputCurrencyId(), dto.getOutputCurrencyId(), dto.getAmount());
        Map<String, Object> map = new HashMap<>();

        map.put("currencies", service.getList());
        map.put("inputCurrencyId", dto.getInputCurrencyId());
        map.put("outputCurrencyId", dto.getOutputCurrencyId());
        map.put("amount", dto.getAmount());
        map.put("result", convert.getOutSum());
        return new ModelAndView("converter", map);
    }

}
