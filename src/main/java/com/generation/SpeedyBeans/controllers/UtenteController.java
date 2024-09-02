package com.generation.SpeedyBeans.controllers;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.CaffeService;
import com.generation.SpeedyBeans.services.LoginService;
import com.generation.SpeedyBeans.services.MacchinettaService;
import com.generation.SpeedyBeans.services.OrdineService;
import com.generation.SpeedyBeans.services.UtenteService;

import jakarta.servlet.http.HttpSession;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@Controller
@RequestMapping("/utente")  
public class UtenteController {
    
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private OrdineService ordineService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private CaffeService caffeService;

    @Autowired
    private MacchinettaService macchinettaService;

    @Autowired
    private AppService as;

    @PostMapping("/insert")
    public String insertUtente(@RequestParam Map<String,String> params, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            Utente u = context.getBean(Utente.class, params);
            List<Ordine> ordini = ordineService.findByIdPersona(u.getId());
            u.setOrdini(ordini);
            utenteService.create(u);
            as.setMessage("Utente inserito correttamente");
            return "redirect:/utente/tutti-gli-utenti";
        }
        as.setMessage("Errore richiesta non autorizzata");
        return "homepage.html";
    }

    @GetMapping("/delete/{idUtente}")
    public String delete(@PathVariable ("idUtente") int idUtente, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            utenteService.delete(idUtente);
            as.setMessage("Utente eliminato correttamente");
            return "redirect:/utente/tutti-gli-utenti";
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
            Utente u = context.getBean(Utente.class, params);
            List<Ordine> ordini = ordineService.findByIdPersona(Integer.parseInt(params.get("id")));
            u.setOrdini(ordini);
            utenteService.update(u);
            as.setMessage("Utente modificato correttamente");
            return "redirect:/utente/tutti-gli-utenti";
        }
        as.setMessage("Errore richiesta non autorizzata");
        return "homepage.html";
    }

    @GetMapping("/createUser/{idUtente}")
    public String createUser(@PathVariable("idUtente") int idUtente, HttpSession session){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            Utente u = utenteService.readById(idUtente);
            String username = (u.getNome() + "." + u.getCognome()).toLowerCase() + String.valueOf(u.getId());
            String password = "1234";
            utenteService.createOrUpdateUser(idUtente, username, password);
            as.setMessage("User creato correttamente");
            return "redirect:/utente/tutti-gli-utenti";
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam Map<String,String> params, HttpSession session){ 
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("U") && p != null){
            String username = params.get("username");
            String oldPassword = params.get("old-password");
            String newPassword = params.get("new-password");
            int id = Integer.parseInt(params.get("id"));

            Persona checkPerson = loginService.login(username, oldPassword);
            if(checkPerson != null && checkPerson.getId() == p.getId()){
                utenteService.createOrUpdateUser(id, username, newPassword);
                as.setMessage("Password modificata correttamente");
                return "redirect:/area-utente";
            }
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    }

    @GetMapping("/profile")
    public String userProfile(HttpSession session, Model model) {

        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("U") && p != null){
            Utente u = (Utente)p;
            model.addAttribute("utente", u);
            return "iMieiDatiUtente.html";
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";

    }

    @GetMapping("/ordini")
    public String userOrdini(HttpSession session, Model model) {

        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("U") && p != null){
            List<Ordine> ordini = ordineService.findByIdPersona(p.getId());
            if(ordini.isEmpty()){
                as.setMessage("Nessun ordine effettuato");
                return "redirect:/area-utente";
            }
            model.addAttribute("listaOrdini", ordini);
            return "listaOrdiniUtente.html";
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";

    }
    
    @GetMapping("/dettaglio")
    public String dettaglioUtente(@RequestParam(name = "id", defaultValue = "0") int idUtente, HttpSession session, Model model){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            Utente u = utenteService.readById(idUtente);
            model.addAttribute("utente", u);
            return "dettaglioUtente.html";
            
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "homepage.html";
    }

    @GetMapping("/tutti-gli-utenti")
    public String tuttiGliUtenti(HttpSession session, Model model) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);  

        if(role != null && role.equals("A") && p != null){
            List<Persona> persone = new ArrayList<>();
            List<Utente> utenti = utenteService.readAll();
            persone.addAll(utenti);
            model.addAttribute("listaUtenti", persone);
            if(as.getMessage() != null){
                model.addAttribute("message", as.getMessage());
                as.setMessage(null);
            }
            return "listaUtenti.html";
        }
        session.invalidate();
        return "redirect:/loginpage";
    }

    @GetMapping("/cerca-utenti")
    public String cercaProdotto(Model model,
        @RequestParam(name = "cognome", defaultValue = "") String cognome,
        @RequestParam(name = "partitaIva", defaultValue = "") String partitaIva,
        @RequestParam(name = "username", defaultValue = "") String username,
        HttpSession session
        ){
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);
        
        List<Utente> utenti = new ArrayList<>();
        Utente u = null;

        if(cognome != "" || partitaIva != "" || username != ""){
            utenti = utenteService.findByFilters(partitaIva, cognome);
            u = utenteService.findByUsername(username);
            if(u != null){
                utenti.add(u);
            }
        }
       
        if(utenti.isEmpty()){
            as.setMessage("Nessun prodotto trovato");
        }

        model.addAttribute("listaUtenti", utenti);

        if(role != null && role.equals("A") && p != null){
            return "listaUtenti.html";
        } else {
            as.setMessage("Errore richiesta non autorizzata");
            session.invalidate();
            return "loginpage.html";
        }
    }

    @GetMapping("/aggiungi-al-carrello")
    public String aggiungiCarrello(HttpSession session, 
    Model model,
    @RequestParam(name = "id", defaultValue = "0") int id
    ) 
    {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && (role.equals("U") || role.equals("A")) && p != null){
            List<Prodotto> carrello = (List<Prodotto>)session.getAttribute("carrello");
            if(carrello == null){
                carrello = new ArrayList<>();
            }
            Prodotto c = caffeService.readById(id);
            Prodotto m = macchinettaService.readById(id);

            if(c != null){
                carrello.add(c);
                as.setMessage("Prodotto aggiunto al carrello");
            }
            else if(m != null){
                carrello.add(m);
                as.setMessage("Prodotto aggiunto al carrello");
            }
            
            System.out.println(as.getMessage());
            session.setAttribute("carrello", carrello);
            return "redirect:/prodotto/tutti-i-prodotti";
        }
        return "redirect:/loginpage";
    }

    @GetMapping("/vai-al-carrello")
    public String vaiCarrello(HttpSession session, Model model) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && (role.equals("U") || role.equals("A")) && p != null){
            
            List<Prodotto> carrello = new ArrayList<>();
            Ordine o = new Ordine();
            carrello = (List<Prodotto>)session.getAttribute("carrello");
            if(carrello == null){
                as.setMessage("Carrello vuoto");
                return "redirect:prodotto/tutti-i-prodotti";
            }
            for(Prodotto prodotto : carrello){
                o.setTotale(o.getTotale() + prodotto.getPrezzo());
            }
            o.setTotale(Math.round(o.getTotale() * 100.0) / 100.0);

            model.addAttribute("carrello", carrello);
            model.addAttribute("ordine", o);
            session.setAttribute("carrello", carrello);
            session.setAttribute("ordine", o);
            return "carrello.html";
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "redirect:/loginpage";
    }


    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && (role.equals("U") || role.equals("A")) && p != null){
            List<Prodotto> carrello = (List<Prodotto>)session.getAttribute("carrello");
            Ordine o = (Ordine)session.getAttribute("ordine");
            o.setTotale(o.getTotale() + (o.getTotale() > 500 ? 0 : 49.00));

            model.addAttribute("ordine", o);
            return "checkout.html";
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "redirect:/loginpage";
    }

    @GetMapping("/pagamento")
    public String pagamento(HttpSession session, Model model) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && (role.equals("U") || role.equals("A")) && p != null){
            List<Prodotto> carrello = (List<Prodotto>)session.getAttribute("carrello");
            Ordine o = (Ordine)session.getAttribute("ordine");
            model.addAttribute("ordine", o);
            model.addAttribute("carrello", carrello);
            o.setPersona((Utente)p);
            ordineService.create(o);
            as.setMessage("Pagamento effettuato con successo");
            return "confermaAcquisto.html";
        }
        as.setMessage("Errore richiesta non autorizzata");
        session.invalidate();
        return "redirect:/loginpage";
    }
    
    
    
    
}
