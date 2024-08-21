package com.generation.SpeedyBeans.dao;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.database.Database;

@Service
public class UserDAO{

    @Autowired
    private Database database;

    private final String readByUsernameAndPassword = "select idPersona from persone where username = ? and password = ?";
   
    public int readByUsernameAndPassword(String username, String password) {
        Map<Integer, Map<String, String>>  result = database.executeQuery(readByUsernameAndPassword, username, password);
        int idPersona = -1;
        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            idPersona = coppia.getKey();
        }
        return idPersona;
    @Autowired
    private Database database;

    private final String readByUsernameAndPassword = "select idPersona from persone where username = ? and password = ?";
   
    public int readByUsernameAndPassword(String username, String password) {
        Map<Integer, Map<String, String>>  result = database.executeQuery(readByUsernameAndPassword, username, password);
        int idPersona = -1;
        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            idPersona = coppia.getKey();
        }
        return idPersona;
    }


    
}
