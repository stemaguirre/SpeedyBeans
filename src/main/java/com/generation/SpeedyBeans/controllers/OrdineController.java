package com.generation.SpeedyBeans.controllers;

import java.util.List;
import java.util.ArrayList;
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
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.CaffeService;
import com.generation.SpeedyBeans.services.MacchinettaService;
import com.generation.SpeedyBeans.services.OrdineService;
import com.generation.SpeedyBeans.services.ProdottoService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/ordine") 
public class OrdineController {

    @Autowired
    ApplicationContext context;

    @Autowired
    private ProdottoService prodottoService;

    @Autowired
    private OrdineService ordineService;

    @Autowired
    private CaffeService caffeService;

    @Autowired
    private MacchinettaService macchinettaService;
    
    @PostMapping("/insert")
    public String insertOrdine(@RequestParam Map<String,String> params, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && (role.equals("A") || role.equals("U")) && p != null){
            Ordine o = context.getBean(Ordine.class, params);
            List<Prodotto> prodotti = prodottoService.findByIdOrdine(Integer.parseInt(params.get("id")));
            o.setProdotti(prodotti);
            ordineService.create(o);
            as.setMessage("Ordine inserito correttamente");
            if(role.equals("A")){
                return "redirect:/area-admin";
            }
            if(role.equals("U")){
                return "redirect:/area-utente";
            }
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    
    }

    @GetMapping("/delete/{idOrdine}")
    public String delete(@PathVariable ("idOrdine") int idOrdine, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            ordineService.delete(idOrdine);
            as.setMessage("Ordine eliminato correttamente");
            return "redirect:/area-admin";
        }
        as.setMessage("Errore richiesta non autorizzata");
        return "homepage.html";
    }

    @PostMapping("/update")
    public String update(@RequestParam Map<String,String> params, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            Ordine o = context.getBean(Ordine.class, params);
            List<Prodotto> prodotti = prodottoService.findByIdOrdine(Integer.parseInt(params.get("id")));
            o.setProdotti(prodotti);
            ordineService.update(o);
            as.setMessage("Ordine modificato correttamente");
            return "redirect:/area-admin";
        }
        as.setMessage("Errore richiesta non autorizzata");
        return "loginpage.html";
    }

    @GetMapping("/dettaglio")
    public String dettaglioOrdine(@RequestParam(name = "id", defaultValue = "0") int idOrdine, HttpSession session, Model model){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);
    
        if (p != null && idOrdine > 0) {
            Ordine o = ordineService.readById(idOrdine);
            List<Prodotto> prodotti = prodottoService.findByIdOrdine(idOrdine);
    
            model.addAttribute("ordine", o);
            model.addAttribute("listaProdotti", prodotti);
    
            if ("A".equals(role)) {
                return "dettaglioOrdineAdmin.html"; // Pagina di dettaglio per l'admin
            } else if ("U".equals(role) && o.getPersona().getId() == p.getId()) {
                return "dettaglioOrdineUtente.html"; // Pagina di dettaglio per l'utente
            }
        }
    
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    }
    

    @GetMapping("/tutti-gli-ordini")
    public String tuttiGliOrdini(HttpSession session, Model model) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            List<Ordine> ordini = ordineService.readAll();
            model.addAttribute("listaOrdini", ordini);
            return "listaOrdiniAdmin.html";
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "loginpage";
    }

    @GetMapping("/i-miei-ordini")
    public String iMieiOrdini(HttpSession session, Model model) {
    Persona p = (Persona)session.getAttribute("persona");
    String role = (String)session.getAttribute("role");
    AppService as = context.getBean(AppService.class);

    if (role != null && role.equals("U") && p != null) {
        List<Ordine> ordini = ordineService.findByIdPersona(p.getId());
        model.addAttribute("listaOrdini", ordini);
        return "listaOrdiniUtente.html";
    }
    as.setMessage("Errore richiesta non autorizzata");
    session.invalidate();
    return "loginpage.html";
}

    
    
}
