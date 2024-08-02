package com.generation.SpeedyBeans.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;




@Controller
public class LoginController {
    
    // @GetMapping("/loginpage")
    // public String loginPage(Model model) {
    //     return "loginpage.html";
    // }
    //NON ABBIAMO PAGINA DI LOGIN MA POP UP
    
    @PostMapping("/login")
    public String login(@RequestParam Map<String,String> params, HttpSession session) {
        //TODO: process POST request
        int x = 2;
        if(x > 0){
            return "redirect:/area-utente";
        }
        else if(x < 2){
            return "redirect:/area-admin";
        }
        
        return "redirect:/loginpage";
    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginpage";
    }


    
}
