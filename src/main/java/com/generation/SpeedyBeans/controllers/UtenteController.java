package com.generation.SpeedyBeans.controllers;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.UtenteService;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/utente")  
public class UtenteController {
    
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private ApplicationContext context;

    @PostMapping("/insert")
    public String insertUtente(@RequestParam Map<String,String> params, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            Utente u = context.getBean(Utente.class, params);
            utenteService.create(u);
            as.setMessage("Utente inserito correttamente");
            return "redirect:/area-admin";
        }
        as.setMessage("Errore richiesta non autorizzata");
        return "homepage.html";
    }
    
}
