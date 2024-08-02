package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Utente;

public class UtenteDAO implements IDAO<Utente> {

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    private final String insertPersona = "insert into persone (nome, cognome, username, password) values (?, ?, ?, ?)";
    private final String insertUtente = "insert into utenti (id_persona, ragioneSociale, partitaIva, codiceSdi, indirizzo, cap, citta, provincia, nazione, telefono, email) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String readAllUtenti = "select * from utenti u join persone p on u.id_persona = p.id_persona";
    
    private final String updatePersona = "update persone set nome = ?, cognome = ?, username = ?, password = ? where id_persona = ?";
    private final String updateUtente = "update utenti set ragioneSociale = ?, partitaIva = ?, codiceSdi = ?, indirizzo = ?, cap = ?, citta = ?, provincia = ?, nazione = ?, telefono = ?, email = ? where id_persona = ?";
    private final String deletePersona = "delete from persone where id_persona = ?";

    @Override
    public int create(Utente s) {
       
        int id = database.executeUpdate(insertPersona, s.getNome(), s.getCognome(), s.getUsername(), s.getPassword());

        database.executeUpdate(insertUtente, String.valueOf(id), s.getRagioneSociale(), s.getPartitaIva(), s.getCodiceSdi(), s.getIndirizzo(), String.valueOf(s.getCap()), s.getCitta(), s.getProvincia(), s.getNazione(), String.valueOf(s.getTelefono()), s.getEmail());

        return id;
    }

    @Override
    public Map<Integer, Entity> readAll() {
        Map<Integer, Entity> ris = new LinkedHashMap<>();

        Map<Integer, Map<String, String>> result = database.executeQuery(readAllUtenti);

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Utente u = context.getBean(Utente.class, coppia.getValue());
            ris.put(u.getId(), u);
            
        }

    public void update(Utente u) {
<<<<<<< HEAD
        database.executeUpdate(updatePersona, u.getNome(), u.getCognome(), u.getUsername(), u.getPassword(), String.valueOf(u.getIdPersona()));

        database.executeUpdate(updateUtente, u.getRagioneSociale(), u.getPartitaIva(), u.getCodiceSdi(), u.getIndirizzo(), String.valueOf(u.getCap()), u.getCitta(), u.getProvincia(), u.getNazione(), String.valueOf(u.getTelefono()), u.getEmail(), String.valueOf(u.getIdPersona()));
    }

    @Override
        database.executeUpdate(deletePersona, String.valueOf(id));
<<<<<<< HEAD
    }

    @Override
    public Utente readById(int id) {
        return null;
=======
>>>>>>> stefano
    }
    
}
