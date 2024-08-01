package com.generation.SpeedyBeans.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;


@Controller
public class PersonaController {
    

    @GetMapping("/area-utente")
    public String areaUtente(HttpSession session, Model model) {
        //Ho messo una condizione fittizia per simulare il login
        int x = 0;
        if(x > 2){
            return "areaUtente.html";
        }
        session.invalidate();
        return "redirect:/loginpage";
    }

    @GetMapping("/area-admin")
    public String areaAdmin(HttpSession session, Model model) {
        int x = 0;
        if(x < 2){
            return "areaAdmin.html";
        }
        session.invalidate();
        return "redirect:/loginpage";
    }

    @GetMapping("area-admin-search")
    public String areaAdmin(@RequestParam Map<String, String> params, HttpSession session, Model model) {
        int x = 0;
        if(x < 2){
            return "areaAdmin.html";
        }
        session.invalidate();
        return "redirect:/loginpage";
    }
}
