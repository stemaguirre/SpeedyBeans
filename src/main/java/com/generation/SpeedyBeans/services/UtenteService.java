package com.generation.SpeedyBeans.services;

import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.dao.UtenteDAO;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.entities.Utente;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Segna questa classe come un componente di servizio di Spring
public class UtenteService extends GenericService<Utente, UserDAO> {


    // Recupera una lista di entità Ordine associate a un ID Utente specifico
    public List<Utente> getUtentiByUsername(String username) {
        List<Utente> ris = new ArrayList<>();

        return ris;
    }

}