package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Entity;

@Repository
public class AdminDAO implements IDAO<Admin> {

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    private final String insertAdmin = "INSERT INTO admin(nomeUtente, password) VALUES (?, ?)";
    private final String readAllAdmins = "SELECT * FROM admin";
    private final String readAdminById = "SELECT * FROM admin WHERE id = ?";
    private final String updateAdmin = "UPDATE admin SET nomeUtente = ?, password = ? WHERE id = ?";
    private final String deleteAdmin = "DELETE FROM admin WHERE id = ?";

    @Override
    public int create(Admin admin) {
        return database.executeUpdate(insertAdmin, admin.getNomeUtente(), admin.getPassword());
    }

    @Override
    public Map<Integer, Entity> readAll() {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readAllAdmins);

        for (Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Admin admin = context.getBean(Admin.class);
            admin.fromMap(coppia.getValue());
            ris.put(admin.getIdPersona(), admin);
        }
        return ris;
    }

    @Override
    public Admin readById(int id) {
        Map<Integer, Map<String, String>> result = database.executeQuery(readAdminById, String.valueOf(id));
        if (result.isEmpty()) {
            return null;
        }
        Map<String, String> data = result.values().iterator().next();
        Admin admin = context.getBean(Admin.class);
        admin.fromMap(data);
        return admin;
    }

    @Override
    public void update(Admin admin) {
        database.executeUpdate(updateAdmin, admin.getNomeUtente(), admin.getPassword(), String.valueOf(admin.getIdPersona()));
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(deleteAdmin, String.valueOf(id));
    }

  
}