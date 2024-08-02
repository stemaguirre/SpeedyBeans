package com.generation.SpeedyBeans.dao;

<<<<<<< HEAD
import java.util.Map;

import com.generation.SpeedyBeans.entities.GenericEntity;
import com.generation.SpeedyBeans.entities.Macchinetta;

public class MacchinettaDAO implements IDAO<Macchinetta>{

    @Override
    public int create(Macchinetta e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
=======
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.GenericEntity;

@Repository
public class MacchinettaDAO implements IDAO<Macchinetta> {

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    private final String insertMacchinetta = "INSERT INTO macchinette(id_ean, utilizzo, colore, modello, serbatoio) VALUES (?, ?, ?, ?, ?)";
    private final String readAllMacchinette = "SELECT * FROM macchinette";
    private final String readMacchinettaById = "SELECT * FROM macchinette WHERE id_ean = ?";
    private final String updateMacchinetta = "UPDATE macchinette SET utilizzo = ?, colore = ?, modello = ?, serbatoio = ? WHERE id_ean = ?";
    private final String deleteMacchinetta = "DELETE FROM macchinette WHERE id_ean = ?";

    @Override
    public int create(Macchinetta macchinetta) {
        return database.executeUpdate(insertMacchinetta, 
                                      macchinetta.getIdEAN(), 
                                      macchinetta.getUtilizzo(), 
                                      macchinetta.getColore(), 
                                      macchinetta.getModello(), 
                                      String.valueOf(macchinetta.getSerbatoio()));
>>>>>>> sergio
    }

    @Override
    public Map<Integer, GenericEntity> readAll() {
<<<<<<< HEAD
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAll'");
    }

    @Override
    public void update(Macchinetta e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
=======
    Map<Integer, GenericEntity> ris = new LinkedHashMap<>();
    Map<Integer, Map<String, String>> result = database.executeQuery(readAllMacchinette);

    for (Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
        Macchinetta macchinetta = context.getBean(Macchinetta.class);
        macchinetta.fromMap(coppia.getValue());
        ris.put(Integer.parseInt(macchinetta.getIdEAN()), macchinetta); 
    }
    return ris;
}

    @Override
    public Macchinetta readById(int id) {
        Map<Integer, Map<String, String>> result = database.executeQuery(readMacchinettaById, String.valueOf(id));
        if (result.isEmpty()) {
            return null;
        }
        Map<String, String> data = result.values().iterator().next();
        Macchinetta macchinetta = context.getBean(Macchinetta.class);
        macchinetta.fromMap(data);
        return macchinetta;
    }

    @Override
    public void update(Macchinetta macchinetta) {
        database.executeUpdate(updateMacchinetta, 
                               macchinetta.getUtilizzo(), 
                               macchinetta.getColore(), 
                               macchinetta.getModello(), 
                               String.valueOf(macchinetta.getSerbatoio()), 
                               macchinetta.getIdEAN());
>>>>>>> sergio
    }

    @Override
    public void delete(int id) {
<<<<<<< HEAD
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
=======
        database.executeUpdate(deleteMacchinetta, String.valueOf(id));
    }

  
}
>>>>>>> sergio
