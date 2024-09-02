package com.generation.SpeedyBeans.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.dao.UserDAO;
import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Utente;

@Service
public class LoginService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AdminService adminService;

    public Persona login(String username, String password) {
        
        int id = userDAO.readByUsernameAndPassword(username, password);
       
        if(id > 0) 
        {
            Utente u = utenteService.readById(id);
            if (u != null){
                return u;
            }

            Admin a = adminService.readById(id);
            if (a != null) {
                return a;
            }
        }

        return null;
    }
}