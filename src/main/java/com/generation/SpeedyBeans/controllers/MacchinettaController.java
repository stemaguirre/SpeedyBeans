package com.generation.SpeedyBeans.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.MacchinettaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/macchinetta")
public class MacchinettaController {

    @Autowired
    ApplicationContext context;

    @Autowired
    MacchinettaService macchinettaService;
    
    @PostMapping("/insert")
    public String insertMacchinetta(@RequestParam Map<String, String> params, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            Macchinetta m = context.getBean(Macchinetta.class, params);
            macchinettaService.create(m);
            as.setMessage("Macchinetta inserita correttamente");
            return "redirect:/area-admin";
        }
        as.setMessage("Richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    }

    @GetMapping("/delete/{idMacchinetta}")
    public String deleteMacchinetta(@PathVariable ("idMacchinetta") int idMacchinetta, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            macchinettaService.delete(idMacchinetta);
            as.setMessage("Macchinetta eliminata correttamente");
            return "redirect:/area-admin";
        }
        as.setMessage("Richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    }

    @PostMapping("/update")
    public String updateMacchinetta(@RequestParam Map<String, String> params, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            Macchinetta m = context.getBean(Macchinetta.class, params);
            macchinettaService.update(m);
            as.setMessage("Macchinetta modificata correttamente");
            return "redirect:/area-admin";
        }
        as.setMessage("Richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    }

    @GetMapping("/dettaglio")
    public String dettaglioMacchinetta(@RequestParam(name = "id", defaultValue = "0") int idMacchinetta, HttpSession session, Model model){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && (role.equals("A") || role.equals("U")) && p != null){
            Macchinetta u = macchinettaService.readById(idMacchinetta);
            model.addAttribute("macchinetta", u);
            return "dettaglioMacchinetta.html";
            
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    }
}
