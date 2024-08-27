package com.generation.SpeedyBeans.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.CaffeService;
import com.generation.SpeedyBeans.services.MacchinettaService;
import com.generation.SpeedyBeans.services.ProdottoService;

import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/prodotto")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private CaffeService caffeService;

    @Autowired
    private MacchinettaService macchinettaService;

    @Autowired
    private ApplicationContext context;
    
    
    @GetMapping("/tutti-i-prodotti")
    public String tuttiProdotti(HttpSession session, Model model) {
        
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            List<Prodotto> prodotti = new ArrayList<>();
            List<Caffe> caffes = caffeService.readAll();
            List<Macchinetta> macchinette = macchinettaService.readAll();
           
            prodotti.addAll(caffes);
            prodotti.addAll(macchinette);

            prodotti.forEach(System.out::println);

            model.addAttribute("listaProdotti", prodotti);

            return "listaProdottiAdmin.html";
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "loginpage.html";
        
    }
}
