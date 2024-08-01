package com.generation.SpeedyBeans.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AppController {
    
    @GetMapping("/")
    public String root() {
        return "index.html";
    }
    
}
