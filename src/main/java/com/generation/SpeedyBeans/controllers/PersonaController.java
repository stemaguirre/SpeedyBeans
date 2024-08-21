package com.generation.SpeedyBeans.controllers;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.UtenteService;

import jakarta.servlet.http.HttpSession;


@Controller
public class PersonaController {
    

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private ApplicationContext context;

    @GetMapping("/area-utente")
    public String areaUtente(HttpSession session, Model model) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        if(role != null && role.equals("U") && p instanceof Utente){
            model.addAttribute("persona", (Utente)p);

            AppService as = context.getBean(AppService.class);
            if(as.getMessage() != null){
                model.addAttribute("messaget", as.getMessage());
                as.setMessage(null);
            }
            return "areaUtente.html";
        }
        session.invalidate();
        return "homepage.html";
    }

    @GetMapping("/area-admin")
    public String areaAdmin(HttpSession session, Model model) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        if(role != null && role.equals("A") && p instanceof Admin){
            model.addAttribute("persona", (Admin)p);

            AppService as = context.getBean(AppService.class);
            if(as.getMessage() != null){
                model.addAttribute("message", as.getMessage());
                as.setMessage(null);
            }
            return "areaAdmin.html";
        }
        session.invalidate();
        return "homepage.html";
    }

    //Da sistemare
    @GetMapping("area-admin-search")
    public String areaAdmin(@RequestParam(name = "username", defaultValue = "") String username,
                            @RequestParam(name = "partitaIva", defaultValue = "") String partitaIva,        
                            @RequestParam(name = "cognome", defaultValue = "") String cognome,
                            HttpSession session, Model model) {

        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        if(role != null && role.equals("A") && p instanceof Admin){
            model.addAttribute("persona", (Admin)p);
            model.addAttribute("listaUtenti", utenteService.findByFilters(partitaIva, cognome));
            model.addAttribute("utente", utenteService.getUtenteByUsername(username));
            
            AppService as = context.getBean(AppService.class);
            if(as.getMessage() != null){
                model.addAttribute("message", as.getMessage());
                as.setMessage(null);
            }
            return "areaAdmin.html";
        }
        session.invalidate();
        return "homepage.html";
    }
}
