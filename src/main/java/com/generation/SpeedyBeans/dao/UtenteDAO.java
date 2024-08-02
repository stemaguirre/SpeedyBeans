package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.GenericEntity;
import com.generation.SpeedyBeans.entities.Utente;

public class UtenteDAO implements IDAO<Utente> {

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    private final String insertPersona = "insert into persone (nome, cognome, username, password) values (?, ?, ?, ?)";
    private final String insertUtente = "insert into utenti (idPersona, ragioneSociale, partitaIva, codiceSdi, indirizzo, cap, citta, provincia, nazione, telefono, email) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String readAllUtenti = "select * from utenti u join persone p on u.idPersona = p.idPersona";
    private final String updatePersona = "update persone set nome = ?, cognome = ?, username = ?, password = ? where idPersona = ?";
    private final String updateUtente = "update utenti set ragioneSociale = ?, partitaIva = ?, codiceSdi = ?, indirizzo = ?, cap = ?, citta = ?, provincia = ?, nazione = ?, telefono = ?, email = ? where idPersona = ?";
    private final String deletePersona = "delete from persone where idPersona = ?";

    @Override
    public int create(Utente s) {
       
        int idPersona = database.executeUpdate(insertPersona, s.getNome(), s.getCognome(), s.getUsername(), s.getPassword());

        database.executeUpdate(insertUtente, String.valueOf(idPersona), s.getRagioneSociale(), s.getPartitaIva(), s.getCodiceSdi(), s.getIndirizzo(), String.valueOf(s.getCap()), s.getCitta(), s.getProvincia(), s.getNazione(), String.valueOf(s.getTelefono()), s.getEmail());

        return idPersona;
    }

    @Override
    public Map<Integer, GenericEntity> readAll() {
        Map<Integer, GenericEntity> ris = new LinkedHashMap<>();

        Map<Integer, Map<String, String>> result = database.executeQuery(readAllUtenti);

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Utente u = context.getBean(Utente.class, coppia.getValue());
            ris.put(u.getIdPersona(), u);
            
        }

        return ris;
        
    }

    @Override
    public void update(Utente u) {
        database.executeUpdate(updatePersona, u.getNome(), u.getCognome(), u.getUsername(), u.getPassword(), String.valueOf(u.getIdPersona()));

        database.executeUpdate(updateUtente, u.getRagioneSociale(), u.getPartitaIva(), u.getCodiceSdi(), u.getIndirizzo(), String.valueOf(u.getCap()), u.getCitta(), u.getProvincia(), u.getNazione(), String.valueOf(u.getTelefono()), u.getEmail(), String.valueOf(u.getIdPersona()));
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(deletePersona, String.valueOf(id));
    }
    
}
