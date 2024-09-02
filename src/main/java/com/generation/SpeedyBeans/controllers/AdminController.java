package com.generation.SpeedyBeans.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import com.generation.SpeedyBeans.services.UtenteService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    ApplicationContext context;

    @Autowired
    UtenteService utenteService;
    
    // @GetMapping("/utenti-registrati")
    // public String userUtenti(HttpSession session, Model model) {

    //     Persona p = (Persona)session.getAttribute("persona");
    //     String role = (String)session.getAttribute("role");
    //     AppService as = context.getBean(AppService.class);

    //     if(role != null && role.equals("A") && p != null){
    //         List<Utente> utentList = utenteService.utentiRegistrati();
    //         if(utentList.isEmpty()){
    //             as.setMessage("Nessun utente trovato");
    //             return "redirect:/area-admin";
    //         }
    //         model.addAttribute("listaUtenti", utentList);
    //         return "listaUtentiRegistrati.html";
    //     }
    //     as.setMessage("Errore: richiesta non autorizzata");
    //     session.invalidate();
    //     return "loginpage.html";

    // }
}
