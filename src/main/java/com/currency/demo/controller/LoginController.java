package com.currency.demo.controller;

import com.currency.demo.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ModelAndView login(@RequestParam(value = "error", required = false) String error) {
        Map<String, Object> map = new HashMap<>();
        if (error != null) {
            map.put("error", error);
        }
        return new ModelAndView("login", map);
    }

}
