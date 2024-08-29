package com.generation.SpeedyBeans.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.generation.SpeedyBeans.entities.Admin;
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




@Controller
public class LoginController {
    
    @Autowired
    private LoginService loginService;

    @Autowired
    private AppService appService;
    
    @Autowired
    private UtenteService utenteService;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private PersonaService personaService;

    @GetMapping("/loginpage")
    public String loginPage(Model model){
        if(appService.getMessage() != null){
            model.addAttribute("message", appService.getMessage());
            appService.setMessage(null);
        }
        return "loginpage.html";
    }
    
    @PostMapping("/login")
    public String login(@RequestParam Map<String,String> params, HttpSession session) {
        Persona p = loginService.login(params.get("username"),params.get("password"));
        
        if(p != null){
            if(p instanceof Utente){
                session.setAttribute("role", "U");
                session.setAttribute("persona", p);
                return "redirect:/area-utente";
            }
            else if(p instanceof Admin){
                session.setAttribute("role", "A");
                session.setAttribute("persona", p);
                return "redirect:/area-admin";
            }
        }

        appService.setMessage("Errore credenziali errate");
        return "redirect:/loginpage";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "homepage.html";
    }

    @GetMapping("/signuppage")
    public String signupPage(Model model){
        if(appService.getMessage() != null){
            model.addAttribute("message", appService.getMessage());
            appService.setMessage(null);
        }
        return "registrazione.html";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam Map<String,String> params){
       
        String capStr = params.get("cap");
        String nome = params.get("nome");
        String cognome = params.get("cognome");
        String telefonoStr = params.get("telefono"); 
        String ragioneSociale = params.get("ragione-sociale");
        String partitaIva = params.get("p-iva");
        String codiceSdi = params.get("codice-sdi");
        String indirizzo = params.get("indirizzo");
        int cap = (capStr != null && !capStr.isEmpty()) ? Integer.parseInt(capStr) : 0;
    
        String citta = params.get("citta");
        String provincia = params.get("provincia");
        String nazione = params.get("nazione");
        int telefono = (telefonoStr != null && !telefonoStr.isEmpty()) ? Integer.parseInt(telefonoStr) : 0;
        String email = params.get("email");
        String username = params.get("username");
        String password = params.get("password");
        String confermaPassword = params.get("conferma-password");

        
        AppService as = context.getBean(AppService.class);
        
        if (personaService.usernameExists(username)) {
            as.setMessage("Username gia' in uso");
            return "registrazione.html";
        }
        
        if (!password.equals(confermaPassword)) {
            as.setMessage("Le password non corrispondono");
            return "registrazione.html";
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

        utenteService.create(u);

        as.setMessage("User registrato correttamente");
        return "loginpage.html";
        
    }


    
}
