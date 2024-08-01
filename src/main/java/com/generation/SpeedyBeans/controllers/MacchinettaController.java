package com.generation.SpeedyBeans.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/macchinetta")
public class MacchinettaController {
    
    @PostMapping("/insert")
    public String insertMacchinetta(@RequestParam Map<String, String> params, HttpSession sesison) {
        //TODO: process POST request
        
        return "redirect:/area-admin";
    }

    @GetMapping("/delete/{idMacchinetta}")
    public String deleteMacchinetta(@RequestParam String param) {
        return "redirect:/area-admin";
    }

    @PostMapping("/update")
    public String updateMacchinetta(@RequestParam Map<String, String> params, HttpSession session) {
        return "redirect:/area-admin";
    }
}
