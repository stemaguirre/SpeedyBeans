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

    private final String readByUsernameAndPassword = "select id_persona from persone where username = ? and password = ?";

    private final String updateUsernameAndPassword = "update persone set username = ?, password = ? where id_persona = ?";


   
    public int readByUsernameAndPassword(String username, String password) {
        Map<Integer, Map<String, String>>  result = database.executeQuery(readByUsernameAndPassword, username, password);
        int idPersona = -1;
        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            idPersona = coppia.getKey();
        }

        return idPersona;
    }
    
    public void updateUsernameAndPassword(int idPersona, String username, String password){
        database.executeUpdate(updateUsernameAndPassword, username, password, String.valueOf(idPersona));
    }

    

}
