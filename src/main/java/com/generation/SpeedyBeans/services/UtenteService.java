package com.generation.SpeedyBeans.services;


import com.generation.SpeedyBeans.dao.UtenteDAO;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Utente;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Segna questa classe come un componente di servizio di Spring
public class UtenteService extends GenericService<Utente, UtenteDAO> {

    @Autowired
    private UtenteDAO utenteDAO; // Iniettare il DAO per interagire con il database

    public List<Utente> getUtentiByUsername(String username) {   

        Map<Integer, Entity> utenti = getRepository().readByUsername(username);
        
        return utenti.values().stream().map(e -> (Utente) e).collect(Collectors.toList());
    }
}

