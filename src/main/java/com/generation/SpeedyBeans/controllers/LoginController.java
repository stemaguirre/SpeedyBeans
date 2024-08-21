package com.generation.SpeedyBeans.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.services.AppService;
import com.generation.SpeedyBeans.services.LoginService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class LoginController {
    
    @Autowired
    private LoginService loginService;

    @Autowired
    private AppService appService;
    // @GetMapping("/loginpage")
    // public String loginPage(Model model) {
    //     return "loginpage.html";
    // }
    //NON ABBIAMO PAGINA DI LOGIN MA POP UP
    
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
        return "homepage.html";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "homepage.html";
    }


    
}
