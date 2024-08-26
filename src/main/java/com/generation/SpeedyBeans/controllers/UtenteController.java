package com.generation.SpeedyBeans.controllers;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.LoginService;
import com.generation.SpeedyBeans.services.OrdineService;
import com.generation.SpeedyBeans.services.PersonaService;
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
    private PersonaService personaService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ApplicationContext context;

    @PostMapping("/insert")
    public String insertUtente(@RequestParam Map<String,String> params, HttpSession session) {
        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("A") && p != null){
            Utente u = context.getBean(Utente.class, params);
            List<Ordine> ordini = ordineService.findByIdPersona(Integer.parseInt(params.get("id")));
            u.setOrdini(ordini);
            utenteService.create(u);
            as.setMessage("Utente inserito correttamente");
            return "redirect:/area-admin";
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
            Utente u = context.getBean(Utente.class, params);
            List<Ordine> ordini = ordineService.findByIdPersona(Integer.parseInt(params.get("id")));
            u.setOrdini(ordini);
            utenteService.update(u);
            as.setMessage("Utente modificato correttamente");
            return "redirect:/area-admin";
        }
        as.setMessage("Errore richiesta non autorizzata");
        return "homepage.html";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam Map<String,String> params){
       
        String nome = params.get("nome");
        String cognome = params.get("cognome"); 
        String ragioneSociale = params.get("ragioneSociale");
        String partitaIva = params.get("partitaIva");
        String codiceSdi = params.get("codiceSdi");
        String indirizzo = params.get("indirizzo");
        int cap = Integer.parseInt(params.get("cap"));
        String citta = params.get("citta");
        String provincia = params.get("provincia");
        String nazione = params.get("nazione");
        int telefono = Integer.parseInt(params.get("telefono"));
        String email = params.get("email");
        String username = params.get("username");
        String password = params.get("password");
        String confermaPassword = params.get("confermaPassword");

        
        AppService as = context.getBean(AppService.class);
        
        if (personaService.usernameExists(username)) {
            as.setMessage("Username gia' in uso");
            return "homepage.html";
        }
        
        if (!password.equals(confermaPassword)) {
            as.setMessage("Le password non corrispondono");
            return "homepage.html";
        }

        Utente u = context.getBean(Utente.class);
        u.setNome(nome);
        u.setCognome(cognome);
        u.setRagioneSociale(ragioneSociale);
        u.setPartitaIva(partitaIva);
        u.setCodiceSdi(codiceSdi);
        u.setIndirizzo(indirizzo);
        u.setCap(cap);
        u.setCitta(citta);
        u.setProvincia(provincia);
        u.setNazione(nazione);
        u.setTelefono(telefono);
        u.setEmail(email);
        u.setUsername(username);
        u.setPassword(password);

        as.setMessage("User registrato correttamente");
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
            return "redirect:/area-admin";
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
        Logger logger = Logger.getLogger(UtenteController.class.getName());

        Persona p = (Persona)session.getAttribute("persona");
        String role = (String)session.getAttribute("role");
        AppService as = context.getBean(AppService.class);

        if(role != null && role.equals("U") && p != null){
            List<Ordine> ordini = ordineService.findByIdPersona(p.getId());
            logger.info("LEGGENDO: " + p.getId());
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
    
    
}
