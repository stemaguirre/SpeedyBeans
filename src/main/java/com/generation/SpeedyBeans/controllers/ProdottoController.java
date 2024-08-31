package com.generation.SpeedyBeans.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.CaffeService;
import com.generation.SpeedyBeans.services.MacchinettaService;
import com.generation.SpeedyBeans.services.ProdottoService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

     @PostMapping("/insert")
    public String insertProdotto(@RequestParam Map<String, String> params, HttpSession session) {
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
    
    
    @GetMapping("/tutti-i-prodotti")
    public String tuttiProdotti(HttpSession session, Model model,
    @RequestParam(name = "caf", defaultValue = "") String caf,
    @RequestParam(name = "mac", defaultValue = "") String mac){
        
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        List<Prodotto> prodotti = new ArrayList<>();
        List<Caffe> caffes = caffeService.readAll();
        List<Macchinetta> macchinette = macchinettaService.readAll();
        
        prodotti.addAll(caffes);
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
        if(caf.equalsIgnoreCase("caffè")){
            prodotti.removeAll(macchinette);
        }
        if(mac.equalsIgnoreCase("macchinette")){
            prodotti.removeAll(caffes);
        }
        as.setMessage("Accedi per vedere più dettagli");
        model.addAttribute("message");
        as.setMessage(null);
        return "listaProdottiHomepage.html";
        
    }

    @GetMapping("/delete/{idProdotto}")
    public String eliminaProdotto (@PathVariable("idProdotto") int idProdotto, HttpSession session){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            prodottoService.delete(idProdotto);
            as.setMessage("Prodotto eliminato correttamente");
            return "redirect:/area-admin";
        }
        as.setMessage("Errore richiesta non autorizzata");
        return "loginpage.html";
    }

    @GetMapping("/dettaglio")
    public String dettaglioProdotto(@RequestParam(name = "id", defaultValue = "0") int idProdotto, HttpSession session, Model model){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && (role.equals("A") || role.equals("U")) && p != null){
            Prodotto c = caffeService.readById(idProdotto);
            Prodotto m = macchinettaService.readById(idProdotto);
            System.out.println(c);
            System.out.println(m);
            if(c != null){
                model.addAttribute("prodotto", c);
                return "dettaglioProdotto.html";
            }
            else if(m != null){
                model.addAttribute("prodotto", m);
                return "dettaglioProdotto.html";
            }
            as.setMessage("Prodotto non trovato");
            
        }
        as.setMessage("Fai il login per visualizzare i dettagli");
        return "redirect:/loginpage";
    }


    @GetMapping("/cerca-prodotti")
    public String cercaProdotto(Model model,
        @RequestParam(name = "genere", defaultValue = "") String genere,
        @RequestParam(name = "formato", defaultValue = "") String formato,
        @RequestParam(name = "tipologia", defaultValue = "") String tipologia,
        @RequestParam(name = "utilizzo", defaultValue = "") String utilizzo,
        @RequestParam(name = "colore", defaultValue = "") String colore,
        @RequestParam(name = "brand", defaultValue = "") String brand,

        HttpSession session) {

    Persona p = (Persona) session.getAttribute("persona");
    String role = (String) session.getAttribute("role");
    AppService as = context.getBean(AppService.class);
    String activeSection = ""; // Variabile per la sezione attiva

    List<Prodotto> prodotti = new ArrayList<>();

    if (genere.equalsIgnoreCase("Caffè") || genere.equalsIgnoreCase("Caffe")) {
        List<Caffe> caffes = caffeService.findByFilters(formato, tipologia);
        prodotti.addAll(caffes);
        activeSection = "caffe";
    } else if (genere.equalsIgnoreCase("Macchinette") || genere.equalsIgnoreCase("Macchinetta")) {
        List<Macchinetta> macchinette = macchinettaService.findByFilters(utilizzo, colore, brand);
        prodotti.addAll(macchinette);
        activeSection = "macchinette";
    }

    if (prodotti.isEmpty()) {
        as.setMessage("Nessun prodotto trovato");
    }

    model.addAttribute("listaProdotti", prodotti);
    model.addAttribute("activeSection", activeSection);

    if (role != null && role.equals("A") && p != null) {
        return "listaProdottiAdmin.html";
    } else if (role != null && role.equals("U") && p != null) {
        return "listaProdottiUtente.html";
    } else {
        return "listaProdottiHomepage.html";
    }
}




    }

