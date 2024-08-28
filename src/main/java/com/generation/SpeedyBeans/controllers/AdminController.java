package com.generation.SpeedyBeans.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.UtenteService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ApplicationContext context;

    @Autowired
    UtenteService utenteService;
    
    @GetMapping("/utenti-registrati")
    public String userUtenti(HttpSession session, Model model) {

        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            List<Utente> utentList = utenteService.utentiRegistrati();
            if(utentList.isEmpty()){
                as.setMessage("Nessun utente trovato");
                return "redirect:/area-admin";
            }
            model.addAttribute("listaUtenti", utentList);
            return "listaUtentiRegistrati.html";
        }
        as.setMessage("Errore: richiesta non autorizzata");
        session.invalidate();
        return "loginpage.html";

    }
}
