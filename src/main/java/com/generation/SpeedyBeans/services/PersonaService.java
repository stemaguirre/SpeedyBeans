package com.generation.SpeedyBeans.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.dao.PersonaDAO;

@Service
public class PersonaService {

    @Autowired
    private PersonaDAO personaDAO;
    
    public boolean usernameExists(String username) {
        return personaDAO.readByUsername(username) != null;
    }
}
