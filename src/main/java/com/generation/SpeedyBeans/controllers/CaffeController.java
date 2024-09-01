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

import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.CaffeService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/caffe")
public class CaffeController {
    
    @Autowired
    ApplicationContext context;

    @Autowired
    CaffeService caffeService;
    
    @PostMapping("/insert")
    public String insertCaffe(@RequestParam Map<String, String> params, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            Caffe c = context.getBean(Caffe.class, params);
            c.setGenere("C");
            caffeService.create(c);
            as.setMessage("Caffe inserito correttamente");
            return "redirect:/prodotto/tutti-i-prodotti";
        }
        as.setMessage("Richiesta non autorizzata");
        session.invalidate();
        return "redirect:/loginpage";
    }

    @GetMapping("/delete/{idCaffe}")
    public String deleteCaffe(@PathVariable ("idCaffe") int idCaffe, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            caffeService.delete(idCaffe);
            as.setMessage("Caffe eliminato correttamente");
            return "redirect:/prodotto/tutti-i-prodotti";
        }
        as.setMessage("Richiesta non autorizzata");
        session.invalidate();
        return "redirect:/loginpage";
    }

    @PostMapping("/update")
    public String updateCaffe(@RequestParam Map<String, String> params, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            Caffe m = context.getBean(Caffe.class, params);
            caffeService.update(m);
            as.setMessage("Caffe modificato correttamente");
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
        List<Caffe> caffes = caffeService.orderByPrezzo();
        prodotti.addAll(caffes);

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
