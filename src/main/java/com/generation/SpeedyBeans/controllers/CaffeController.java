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

import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Persona;
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
            caffeService.create(c);
            as.setMessage("Caffe inserito correttamente");
            return "redirect:/area-admin";
        }
        as.setMessage("Richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    }

    @GetMapping("/delete/{idCaffe}")
    public String deleteCaffe(@PathVariable ("idCaffe") int idCaffe, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            caffeService.delete(idCaffe);
            as.setMessage("Caffe eliminato correttamente");
            return "redirect:/area-admin";
        }
        as.setMessage("Richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
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
            return "redirect:/area-admin";
        }
        as.setMessage("Richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    }

    @GetMapping("/dettaglio")
    public String dettaglioCaffe(@RequestParam(name = "id", defaultValue = "0") int idCaffe, HttpSession session, Model model){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && (role.equals("A") || role.equals("U")) && p != null){
            Caffe u = caffeService.readById(idCaffe);
            model.addAttribute("caffe", u);
            return "dettaglioCaffe.html";
            
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    }
}
