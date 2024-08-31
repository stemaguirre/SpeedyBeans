package com.generation.SpeedyBeans.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.CaffeService;
import com.generation.SpeedyBeans.services.MacchinettaService;
import com.generation.SpeedyBeans.services.OrdineService;
import com.generation.SpeedyBeans.services.ProdottoService;
import com.generation.SpeedyBeans.services.UtenteService;

import jakarta.servlet.http.HttpSession;


@Controller
public class PersonaController {
    

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private OrdineService ordineService;

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private CaffeService caffeService;

    @Autowired
    private MacchinettaService macchinettaService;

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
                model.addAttribute("message", as.getMessage());
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
            model.addAttribute("listaUtenti", utenteService.readAll());
            // model.addAttribute("utentiRegistrati", utenteService.utentiRegistrati());
            model.addAttribute("listaOrdini", ordineService.readAll());
            model.addAttribute("listaProdotti", prodottoService.readAll());

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

  
    @GetMapping("/cerca-prodotti")
public String cercaProdotto(Model model,
    @RequestParam(name = "genere", defaultValue = "") String genere,
    @RequestParam(name = "formato", defaultValue = "") String formato,
    @RequestParam(name = "tipologia", defaultValue = "") String tipologia,
    @RequestParam(name = "utilizzo", defaultValue = "") String utilizzo,
    @RequestParam(name = "colore", defaultValue = "") String colore,
    @RequestParam(name = "brand", defaultValue = "") String brand, // aggiungi il parametro brand
    HttpSession session) {

    Persona p = (Persona) session.getAttribute("persona");
    String role = (String) session.getAttribute("role");
    AppService as = context.getBean(AppService.class);
    String activeSection = ""; // Variabile per la sezione attiva

    List<Prodotto> prodotti = new ArrayList<>();

    if (genere.equalsIgnoreCase("Caffè") || genere.equalsIgnoreCase("Caffe")) {
        List<Caffe> caffes = caffeService.findByFilters(formato, tipologia); // aggiungi il brand
        prodotti.addAll(caffes);
        activeSection = "caffe";
    } else if (genere.equalsIgnoreCase("Macchinette") || genere.equalsIgnoreCase("Macchinetta")) {
        List<Macchinetta> macchinette = macchinettaService.findByFilters(utilizzo, colore, brand); // aggiungi il brand
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


    @GetMapping("area-utente-search")
    public String areaUtente(@RequestParam(name = "genere", defaultValue = "") String genere,
                            @RequestParam(name = "brand", defaultValue = "") String brand,
                            @RequestParam(name = "minProdotto", defaultValue = "0") double minProdotto,
                            @RequestParam(name = "maxProdotto", defaultValue = "0") double maxProdotto,
                            @RequestParam(name = "formato", defaultValue = "") String formato,
                            @RequestParam(name = "tipologia", defaultValue = "") String tipologia,
                            @RequestParam(name = "utilizzo", defaultValue = "") String utilizzo,
                            @RequestParam(name = "colore", defaultValue = "") String colore,
                            HttpSession session, Model model) {

        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");

        if(role != null && role.equals("U") && p instanceof Utente){

            model.addAttribute("persona", (Utente)p);
            model.addAttribute("prodottiFiltri", prodottoService.findByFilters(genere, brand));
            model.addAttribute("prodottiRange", prodottoService.findyByRangePrezzo(minProdotto, maxProdotto));
            model.addAttribute("caffeFiltri", caffeService.findByFilters(formato, tipologia));
            model.addAttribute("macchinettaFiltri", macchinettaService.findByFilters(utilizzo, colore, brand));
            
            AppService as = context.getBean(AppService.class);
            if(as.getMessage() != null){
                model.addAttribute("message", as.getMessage());
                as.setMessage(null);
            }
            return "areaUtente.html";
        }
        session.invalidate();
        return "homepage.html";
    }
}
