package com.generation.SpeedyBeans.controllers;

import java.util.ArrayList;
import java.util.List;

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
    public String tuttiProdotti(HttpSession session, Model model){
        
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
        as.setMessage("Accedi per vedere più dettagli");
        model.addAttribute("message");
        as.setMessage(null);
        return "listaProdottiHomepage.html";
        
    }

    @GetMapping("/tutti-i-prodotti/caffes")
    public String tuttiCaffes(HttpSession session, Model model){
        
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        List<Prodotto> prodotti = new ArrayList<>();
        List<Caffe> caffes = caffeService.readAll();
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
        return "listaProdottiHomepage.html";
    }

    @GetMapping("/tutti-i-prodotti/macchinette")
    public String tutteMacchinette(HttpSession session, Model model){
        
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        List<Prodotto> prodotti = new ArrayList<>();
        List<Macchinetta> macchinette = macchinettaService.readAll();
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
            return "redirect:/prodotto/tutti-i-prodotti";
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
            }
            else if(m != null){
                model.addAttribute("prodotto", m);
            }
            if(role.equals("A")){
                return "dettaglioProdottoAdmin.html";
            } else if(role.equals("U")){
                return "dettaglioProdottoUtente.html";
            }
            as.setMessage("Prodotto non trovato");
        }
        as.setMessage("Fai il login per visualizzare i dettagli");
        return "redirect:/loginpage";
    }

    @GetMapping("/cerca-prodotti")
    public String cercaProdotti(Model model,
        @RequestParam(name = "brand", defaultValue = "") String brand,
        HttpSession session
        ){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);
        
        List<Prodotto> prodotti = new ArrayList<>();
        List<Caffe> caffes = caffeService.findByFilters(brand);
        List<Macchinetta> macchinette = macchinettaService.findByFilters(brand);
        prodotti.addAll(caffes);
        prodotti.addAll(macchinette);

        if(prodotti.isEmpty()){
            as.setMessage("Nessun prodotto trovato");
            model.addAttribute("message", as.getMessage());
            return "redirect:/prodotto/tutti-i-prodotti";
        }

        model.addAttribute("listaProdotti", prodotti);

        if(role != null && role.equals("A") && p != null){
            return "listaProdottiAdmin.html";
        } else if(role != null && role.equals("U") && p != null){
            return "listaProdottiUtente.html";
        } else{
            return "listaProdottiHomepage.html";
        }
    }
    
    @GetMapping("/cerca-prodotti/caffes")
    public String cercaCaffes(Model model,
        @RequestParam(name = "brand", defaultValue = "") String brand,
        @RequestParam(name = "formato", defaultValue = "") String formato,
        @RequestParam(name = "tipologia", defaultValue = "") String tipologia,
        HttpSession session
        ){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);
        
        List<Prodotto> prodotti = new ArrayList<>();
        List<Caffe> caffes = caffeService.findByFilters(brand, formato, tipologia);
        prodotti.addAll(caffes);

        if(prodotti.isEmpty()){
            as.setMessage("Nessun prodotto trovato");
            model.addAttribute("message", as.getMessage());
            return "redirect:/prodotto/tutti-i-prodotti";
        }

        model.addAttribute("listaProdotti", prodotti);

        if(role != null && role.equals("A") && p != null){
            return "listaProdottiAdmin.html";
        } else if(role != null && role.equals("U") && p != null){
            return "listaProdottiUtente.html";
        } else{
            return "listaProdottiHomepage.html";
        }
    }

    @GetMapping("/cerca-prodotti/macchinette")
    public String cercaMacchinette(Model model,
        @RequestParam(name = "brand", defaultValue = "") String brand,
        @RequestParam(name = "utilizzo", defaultValue = "") String utilizzo,
        @RequestParam(name = "colore", defaultValue = "") String colore,
        HttpSession session
        ){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);
        
        List<Prodotto> prodotti = new ArrayList<>();
        List<Macchinetta> macchinette = macchinettaService.findByFilters(brand, utilizzo, colore);
        prodotti.addAll(macchinette);

        if(prodotti.isEmpty()){
            as.setMessage("Nessun prodotto trovato");
            model.addAttribute("message", as.getMessage());
            return "redirect:/prodotto/tutti-i-prodotti";
        }

        model.addAttribute("listaProdotti", prodotti);

        if(role != null && role.equals("A") && p != null){
            return "listaProdottiAdmin.html";
        } else if(role != null && role.equals("U") && p != null){
            return "listaProdottiUtente.html";
        } else{
            return "listaProdottiHomepage.html";
        }
    }

    @GetMapping("/ordina")
    public String ordinaProdotti(HttpSession session, Model model){
        
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        List<Prodotto> prodotti = new ArrayList<>();
        List<Caffe> caffes = caffeService.orderByPrezzo();
        List<Macchinetta> macchinette = macchinettaService.orderByPrezzo();
        
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
        as.setMessage("Accedi per vedere più dettagli");
        model.addAttribute("message");
        as.setMessage(null);
        return "listaProdottiHomepage.html";
        
    }


}
