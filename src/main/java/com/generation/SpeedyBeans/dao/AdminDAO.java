package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Entity;

@Service
public class AdminDAO implements IDAO<Admin>{

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    private final String insertPersona = "insert into persone (nome, cognome, username, password) values (?, ?, ?, ?)";
    private final String insertAdmin = "insert into admins (id_persona) values (?)";

    private final String readAllAdmins = "select p.* from admins u join persone p on u.id_persona = p.id_persona";

    private final String readAdminById = "select u.*, p.* from admins u join persone p on u.id_persona = p.id_persona where u.id_persona = ?";
    
    private final String updatePersona = "update persone set nome = ?, cognome = ?, username = ?, password = ? where id_persona = ?";
    private final String updateAdmin = "";

    private final String deletePersona = "delete from persone where id_persona = ?";

    @Override
    public int create(Admin e) {
        int id = database.executeUpdate(insertPersona, e.getNome(), e.getCognome(), e.getUsername(), e.getPassword());

        database.executeUpdate(insertAdmin, String.valueOf(id));

        return id;
    }

    @Override
    public Map<Integer, Entity> readAll() {
        Map<Integer, Entity> ris = new LinkedHashMap<>();

        Map<Integer, Map<String, String>> result = database.executeQuery(readAllAdmins);

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Admin a = context.getBean(Admin.class, coppia.getValue());
            ris.put(a.getId(), a);
            
        }

        return ris;
    }

    public Admin readById(int id) {
        Map<Integer, Map<String, String>> result = database.executeQuery(readAdminById, String.valueOf(id));
        Admin a = null;

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            a = context.getBean(Admin.class, coppia.getValue());
        }

        return a;
    }

    @Override
    public void update(Admin a) {
        database.executeUpdate(updatePersona, a.getNome(), a.getCognome(), a.getUsername(), a.getPassword(), String.valueOf(a.getId()));

        database.executeUpdate(updateAdmin);
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(deletePersona, String.valueOf(id));
    }

  
}


