package com.myapps.yodlee.financialapp.controllers;

import com.myapps.yodlee.financialapp.config.GlobalProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    private final GlobalProperties globalProperties;

    public HomeController(GlobalProperties globalProperties) {
        this.globalProperties = globalProperties;
    }

    @GetMapping
    public String home(Model model) {

        model.addAttribute("token", globalProperties.getProperties().get("token"));

        return "index";
    }
}
