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

@Service 
public class UtenteService extends GenericService<Utente, UtenteDAO> {
    
    @Autowired
    private UtenteDAO utenteDAO; // Iniettare il DAO per interagire con il database

    public List<Utente> findByFilters(String partitaIva, String cognome) {
        List<Utente> ris = new ArrayList<>();

        Map<Integer, Entity> utenti = getRepository().findByFilters(partitaIva, cognome);

        for (Entity e : utenti.values()) {
            ris.add((Utente) e);
        }

        return ris;
    }

    public Utente getUtenteByUsername(String username) {   

        Utente u = getRepository().readByUsername(username);

        return u;
    }
}

