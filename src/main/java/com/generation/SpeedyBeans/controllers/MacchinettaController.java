package com.generation.SpeedyBeans.controllers;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

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
import com.generation.SpeedyBeans.entities.Prodotto;
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
            m.setGenere("M");
            macchinettaService.create(m);
            as.setMessage("Macchinetta inserita correttamente");
            return "redirect:/prodotto/tutti-i-prodotti";
        }
        as.setMessage("Richiesta non autorizzata");
        session.invalidate();
        return "redirect:/loginpage";
    }

    @GetMapping("/delete/{idMacchinetta}")
    public String deleteMacchinetta(@PathVariable ("idMacchinetta") int idMacchinetta, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            macchinettaService.delete(idMacchinetta);
            as.setMessage("Macchinetta eliminata correttamente");
            return "redirect:/prodotto/tutti-i-prodotti";
        }
        as.setMessage("Richiesta non autorizzata");
        session.invalidate();
        return "redirect:/loginpage";
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
            return "redirect:/prodotto/tutti-i-prodotti";
        }
        as.setMessage("Richiesta non autorizzata");
        session.invalidate();
        return "redirect:/loginpage";
    }

    @GetMapping("/ordina")
    public String ordina(HttpSession session, Model model) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        List<Prodotto> prodotti = new ArrayList<>();
        List<Macchinetta> macchinette = macchinettaService.orderByPrezzo();
        prodotti.addAll(macchinette);

        model.addAttribute("listaProdotti", prodotti);

        if (role != null && role.equals("A") && p != null) {
            if(as.getMessage() != null){
                model.addAttribute("message", as.getMessage());
                as.setMessage(null);
            }
            return "listaProdottiAdmin.html";
        } else if (role != null && role.equals("U") && p != null) {
            if(as.getMessage() != null){
                model.addAttribute("message", as.getMessage());
                as.setMessage(null);
            }
            return "listaProdottiUtente.html"; 
        }
        as.setMessage("Accedi per vedere più dettagli");
        model.addAttribute("message");
        as.setMessage(null);
        return "redirect:/loginpage";
    }
    
    
}
