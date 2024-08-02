package com.generation.SpeedyBeans.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AppController {
    
    @GetMapping("/")
    public String root() {
        return "index.html";
    }
    
}
