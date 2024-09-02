package com.generation.SpeedyBeans.controllers;

import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;
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
import com.generation.SpeedyBeans.services.AdminService;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.CaffeService;
import com.generation.SpeedyBeans.services.MacchinettaService;
import com.generation.SpeedyBeans.services.OrdineService;
import com.generation.SpeedyBeans.services.ProdottoService;
import com.generation.SpeedyBeans.services.UtenteService;

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

    @Autowired
    UtenteService utenteService;
    
    @Autowired
    AdminService adminService;
    
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
                return "redirect:/ordine/tutti-gli-ordini";
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
            return "redirect:/ordine/tutti-gli-ordini";
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
            return "redirect:/ordine/tutti-gli-ordini";
        }
        as.setMessage("Errore richiesta non autorizzata");
        return "loginpage.html";
    }

    @GetMapping("/dettaglio")
    public String dettaglioOrdine(@RequestParam(name = "id", defaultValue = "0") int idOrdine, HttpSession session, Model model){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){

            List<Prodotto> prodotti = new ArrayList<>();
            List<Caffe> caffes = caffeService.findByIdOrdine(idOrdine);
            List<Macchinetta> macchinette = macchinettaService.findByIdOrdine(idOrdine);
           
            prodotti.addAll(caffes);
            prodotti.addAll(macchinette);

            Ordine o = ordineService.readById(idOrdine);
            Persona u = utenteService.readById(o.getIdPersona());
            Persona a = adminService.readById(o.getIdPersona());
            if(u != null){
                o.setPersona(u);
            }
            if(a != null){
                o.setPersona(a);
            }
            o.setProdotti(prodotti);
           
            model.addAttribute("listaProdotti", prodotti);
            model.addAttribute("ordine", o);
            return "dettaglioOrdineAdmin.html";
            
        }
        if(role != null && role.equals("U") && p != null){

            List<Prodotto> prodotti = new ArrayList<>();
            List<Caffe> caffes = caffeService.findByIdOrdine(idOrdine);
            List<Macchinetta> macchinette = macchinettaService.findByIdOrdine(idOrdine);
           
            prodotti.addAll(caffes);
            prodotti.addAll(macchinette);

            Ordine o = ordineService.readById(idOrdine);
            o.setProdotti(prodotti);

            model.addAttribute("listaProdotti", prodotti);
            model.addAttribute("ordine", o);
            return "dettaglioOrdineUtente.html";
            
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
            for(Ordine o : ordini){
                Persona u = utenteService.readById(o.getIdPersona());
                Persona a = adminService.readById(o.getIdPersona());
                if(u != null){
                    o.setPersona(u);
                }
                if(a != null){
                    o.setPersona(a);
                }
            }
            model.addAttribute("listaOrdini", ordini);
            model.addAttribute("message", as.getMessage());
            return "listaOrdiniAdmin.html";
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "loginpage";
    }

    @GetMapping("/rimuovi/{idProdotto}")
    public String rimuoviProdotto(@PathVariable ("idProdotto") int idProdotto, HttpSession session, Model model) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && (role.equals("U") || role.equals("A")) && p != null){
            
            List<Prodotto> carrello = new ArrayList<>();
            Ordine o = new Ordine();
            carrello = (List<Prodotto>)session.getAttribute("carrello");
            if(carrello == null){
                model.addAttribute("message", as.getMessage());
                return "carrello.html";
            }

            Iterator<Prodotto> iterator = carrello.iterator();
            while (iterator.hasNext()) {
            Prodotto pr = iterator.next();
            if (pr.getId() == idProdotto) {
                iterator.remove(); // Rimuove l'elemento in modo sicuro
                break;
                }
            }

            for(Prodotto prodotto : carrello){
                o.setTotale(o.getTotale() + prodotto.getPrezzo());
            }
            o.setTotale(Math.round(o.getTotale() * 100.0) / 100.0);

            model.addAttribute("carrello", carrello);
            model.addAttribute("ordine", o);
            model.addAttribute("message", as.getMessage());
            session.setAttribute("carrello", carrello);
            session.setAttribute("ordine", o);
            return "carrello.html";
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "redirect:/loginpage";
    }

    @GetMapping("/cerca-ordini")
    public String cercaOrdini(Model model,
        @RequestParam(name = "nome", defaultValue = "") String nome,
        @RequestParam(name = "cognome", defaultValue = "") String cognome,
        @RequestParam(name = "minTotale", defaultValue = "0") int minTotale,
        @RequestParam(name = "maxTotale", defaultValue = "0") int maxTotale,
        @RequestParam(name = "data_inizio", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInizio,
        @RequestParam(name = "data_fine", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFine,
        HttpSession session
    ) {
        Persona p = (Persona) session.getAttribute("persona");
        String role = (String) session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        List<Ordine> ordini = new ArrayList<>();

        // Logica per l'admin: filtra per nome, cognome, totale e date
        if ("A".equals(role)) {
            if (!nome.isEmpty() || !cognome.isEmpty()) {
                ordini = ordineService.findByNomeCognomePersona(nome, cognome);
            }

            if (minTotale > 0 && maxTotale > 0) {
                ordini.addAll(ordineService.findByRangeTotale(minTotale, maxTotale));
            } else if (minTotale > 0 && maxTotale == 0){
                ordini.addAll(ordineService.findByTotaleMinimo(minTotale));
            } else if(minTotale == 0 && maxTotale > 0){
                ordini.addAll(ordineService.findByTotaleMassimo(maxTotale));
            }

            if (dataInizio != null && dataFine != null) {
                java.sql.Date startDate = java.sql.Date.valueOf(dataInizio);
                java.sql.Date endDate = java.sql.Date.valueOf(dataFine);
                ordini.addAll(ordineService.findByDateRange(startDate, endDate));
            }else if(dataInizio != null && dataFine == null){
                java.sql.Date startDate = java.sql.Date.valueOf(dataInizio);
                ordini.addAll(ordineService.findByDateInizio(startDate));
            }else if(dataInizio == null && dataFine != null){
                java.sql.Date endDate = java.sql.Date.valueOf(dataFine);
                ordini.addAll(ordineService.findByDateFine(endDate));
            }

            // Se nessun filtro è applicato, carica tutti gli ordini
            if (ordini.isEmpty()) {
                ordini = ordineService.readAll();
            }
        } 
        // Logica per l'utente normale: carica solo i suoi ordini
        else if ("U".equals(role)) {
            ordini = ordineService.findByIdPersona(p.getId());

            // Filtro per range di totale
            if (minTotale > 0 || maxTotale > 0) {
                ordini = ordini.stream()
                    .filter(o -> o.getTotale() >= minTotale && (maxTotale == 0 || o.getTotale() <= maxTotale))
                    .collect(Collectors.toList());
            }

            // Filtro per range di date
            if (dataInizio != null && dataFine != null) {
                ordini = ordini.stream()
                    .filter(o -> !o.getDataOrdine().toLocalDate().isBefore(dataInizio) && !o.getDataOrdine().toLocalDate().isAfter(dataFine))
                    .collect(Collectors.toList());
            }
        }

        // Evitare duplicati nella lista ordini
        ordini = ordini.stream().distinct().collect(Collectors.toList());

        // Associa di nuovo la persona a ciascun ordine dopo il filtraggio
        for (Ordine o : ordini) {
            Persona u = utenteService.readById(o.getIdPersona());
            Persona a = adminService.readById(o.getIdPersona());
            if (u != null) {
                o.setPersona(u);
            } else if (a != null) {
                o.setPersona(a);
            }
        }

        if (ordini.isEmpty()) {
            as.setMessage("Nessun ordine trovato");
        }

        model.addAttribute("listaOrdini", ordini);

        if ("A".equals(role)) {
            return "listaOrdiniAdmin.html";
        } else if ("U".equals(role)) {
            return "listaOrdiniUtente.html";
        } else {
            as.setMessage("Accesso non autorizzato");
            return "listaProdottiHomepage.html";
        }
    }


    
}
