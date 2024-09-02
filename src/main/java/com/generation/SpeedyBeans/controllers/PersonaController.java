package com.generation.SpeedyBeans.controllers;

import java.util.Map;

import javax.crypto.Mac;

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

  
    @GetMapping("area-admin-search")
    public String areaAdmin(@RequestParam(name = "username", defaultValue = "") String username,
                            @RequestParam(name = "partitaIva", defaultValue = "") String partitaIva,        
                            @RequestParam(name = "cognome", defaultValue = "") String cognome,
                            @RequestParam(name = "minOrdine", defaultValue = "0") int minOrdine,
                            @RequestParam(name = "maxOrdine", defaultValue = "0") int maxOrdine,
                            @RequestParam(name = "nome", defaultValue = "") String nome,
                            @RequestParam(name = "genere", defaultValue = "") String genere,
                            @RequestParam(name = "brand", defaultValue = "") String brand,
                            @RequestParam(name = "minProdotto", defaultValue = "0") double minProdotto,
                            @RequestParam(name = "max", defaultValue = "0") double maxProdotto,
                            @RequestParam(name = "formato", defaultValue = "") String formato,
                            @RequestParam(name = "tipologia", defaultValue = "") String tipologia,
                            @RequestParam(name = "utilizzo", defaultValue = "") String utilizzo,
                            @RequestParam(name = "colore", defaultValue = "") String colore,
                            HttpSession session, Model model) {

        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        if(role != null && role.equals("A") && p instanceof Admin){
            model.addAttribute("persona", (Admin)p);
            model.addAttribute("utentiFiltri", utenteService.findByFilters(partitaIva, cognome));
            model.addAttribute("utenteUsername", utenteService.findByUsername(username));
            model.addAttribute("ordiniRange", ordineService.findByRangeTotale(minOrdine, maxOrdine));
            model.addAttribute("ordiniPersona", ordineService.findByNomeCognomePersona(nome, cognome));
            model.addAttribute("prodottiFiltri", prodottoService.findByFilters(genere, brand));
            model.addAttribute("prodottiRange", prodottoService.findyByRangePrezzo(minProdotto, maxProdotto));
            model.addAttribute("caffeFiltri", caffeService.findByFilters(brand, formato, tipologia));
            model.addAttribute("macchinettaFiltri", macchinettaService.findByFilters(brand, utilizzo, colore));
            
            
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
            model.addAttribute("caffeFiltri", caffeService.findByFilters(brand, formato, tipologia));
            model.addAttribute("macchinettaFiltri", macchinettaService.findByFilters(brand, utilizzo, colore));
            
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
