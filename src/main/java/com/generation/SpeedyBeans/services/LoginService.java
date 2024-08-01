package com.generation.SpeedyBeans.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.dao.UserDAO;
import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.entities.Persone;

@Service
public class LoginService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private AdminService adminService;

    public Persone login(String username, String password) {
        Long id = userDAO.readByUsernameAndPassword(username, password);
        if (id > 0) {
            Utente utente = utenteService.readById(id);
            if (utente != null) {
                return utente;
            }

            Admin admin = adminService.readById(id);
            if (admin != null) {
                return admin;
            }
        }

        return null;
    }
}