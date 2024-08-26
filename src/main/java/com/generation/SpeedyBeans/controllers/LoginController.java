package com.generation.SpeedyBeans.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.LoginService;
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

    @GetMapping("/signinpage")
    public String signinPage(Model model){
        if(appService.getMessage() != null){
            model.addAttribute("message", appService.getMessage());
            appService.setMessage(null);
        }
        return "registrazione.html";
    }

    @PostMapping("/signin")
    public String signin(@RequestParam Map<String,String> params){
        Utente u = new Utente();
        u.fromMap(params);
        utenteService.create(u);
        return "redirect:/loginpage";
    }


    
}
