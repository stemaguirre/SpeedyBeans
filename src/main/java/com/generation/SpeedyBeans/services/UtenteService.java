package com.generation.SpeedyBeans.services;

import com.generation.SpeedyBeans.dao.UtenteDAO;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Utente;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service 
public class UtenteService extends GenericService<Utente, UtenteDAO> {

    public List<Utente> findByFilters(String partitaIva, String cognome) {
        List<Utente> ris = new ArrayList<>();

        Map<Integer, Entity> utenti = getRepository().findByFilters(partitaIva, cognome);

        for (Entity e : utenti.values()) {
            ris.add((Utente) e);
        }

        return ris;
    }

}