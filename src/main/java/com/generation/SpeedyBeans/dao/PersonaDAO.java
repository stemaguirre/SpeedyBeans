package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Utente;

public class PersonaDAO implements IDAO<Persona>
{
    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    private final String insertPersona = "insert into persone (nome, cognome, username, password) values (?, ?, ?, ?)";

    private final String readAllPersone = "select * from persone";

    private final String updatePersona = "update persone set nome = ?, cognome = ?, username = ?, password = ? where id_persona = ?";

    private final String deletePersona = "delete from persone where id_persona = ?";



    @Override
    public int create(Persona p) {
        int id = database.executeUpdate(insertPersona, p.getNome(), p.getCognome(), p.getUsername(), p.getPassword());
        return id;
    }

    @Override
    public Map<Integer, Entity> readAll() {
        Map<Integer, Entity> ris = new LinkedHashMap<>();

        Map<Integer, Map<String, String>> result = database.executeQuery(readAllPersone);

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Persona p = context.getBean(Persona.class, coppia.getValue());
            ris.put(p.getId(), p);
            
        }

        return ris;
    }

    @Override
    public void update(Persona p) {
        database.executeUpdate(updatePersona, p.getNome(), p.getCognome(), p.getUsername(), p.getPassword(), String.valueOf(p.getId()));
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(deletePersona, String.valueOf(id));
    }

    public Persona readByid(int id){
        Map<Integer, Entity> allPersone = readAll();
        return (Persona)allPersone.get(id);
    }
    
    
}
