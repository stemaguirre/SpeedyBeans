package com.generation.SpeedyBeans.services;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.dao.UserDAO;
import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Utente;

@Service
public class LoginService {

    private static final Logger logger = Logger.getLogger(LoginService.class.getName());

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AdminService adminService;

    public Persona login(String username, String password) {
        logger.info("Attempting login with username: " + username);
        int id = userDAO.readByUsernameAndPassword(username, password);
        System.out.println("id da userDAO: " + id);
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
        
        System.out.println("id: " + id);

        return null;
    }
}