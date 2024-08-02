package com.generation.SpeedyBeans.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.entities.GenericEntity;
import com.generation.SpeedyBeans.database.Database;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UtenteDAO {

    @Autowired
    private Database database;

    private final String readByUsernameAndPassword = "select id from persone where username=? and password=?";

    private final String updateUsernameAndPassword = "update persone set username=?, password=? where id=?";

    public Long readByUsernameAndPassword(String username, String password){
        Map<Long, Map<String, String>> result = database.executeQuery(readByUsernameAndPassword, username, password);
        Long id = -1L;
        for(Entry<Long, Map<String, String>> coppia : result.entrySet()){
            id = coppia.getKey();
        }

        return id;
    }

    //DML che permette l'aggiornamento delle colonne username e password sulla tabella persone
    public void updateUsernameAndPassword(Long idPersona, String username, String password){
        database.executeUpdate(updateUsernameAndPassword, username, password, String.valueOf(idPersona));
    }

}
