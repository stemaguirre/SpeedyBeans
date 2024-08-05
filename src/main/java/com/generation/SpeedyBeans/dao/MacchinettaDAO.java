package com.generation.SpeedyBeans.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Entity;

@Repository
public class MacchinettaDAO implements IDAO<Macchinetta> {

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    private final String insertProdotto = "INSERT INTO prodotti (genere, brand, prezzo, disponibilita, peso) VALUES (?, ?, ?, ?, ?)";
    private final String insertMacchinetta = "INSERT INTO macchinette(id_ean, utilizzo, colore, modello, serbatoio) VALUES (?, ?, ?, ?, ?)";


    private final String readAllMacchinette = "SELECT * FROM macchinette";

    private final String updateProdotto = "update prodotti set genere = ?, brand = ?, prezzo = ?, disponibilita = ?, peso = ? where id_ean = ?";
    private final String updateMacchinetta = "UPDATE macchinette SET utilizzo = ?, colore = ?, modello = ?, serbatoio = ? WHERE id_ean = ?";
    private final String deleteMacchinetta = "DELETE FROM macchinette WHERE id_ean = ?";


    @Override
    public int create(Macchinetta m) {

        int id = database.executeUpdate(insertProdotto, 
                                        m.getGenere(), 
                                        m.getBrand(), 
                                        String.valueOf(m.getPrezzo()), 
                                        String.valueOf(m.getDisponibilita()), 
                                        String.valueOf(m.getPeso()) );

        database.executeUpdate(insertMacchinetta, 
                                String.valueOf(m.getId()), 
                                m.getUtilizzo(), 
                                m.getColore(), 
                                m.getModello(), 
                                String.valueOf(m.getSerbatoio()) );

        return id;
    }

    @Override
    public Map<Integer, Entity> readAll() {

    Map<Integer, Entity> ris = new LinkedHashMap<>();
    Map<Integer, Map<String, String>> result = database.executeQuery(readAllMacchinette);

    for (Entry<Integer, Map<String, String>> coppia : result.entrySet()){
        Macchinetta m = context.getBean(Macchinetta.class, coppia.getValue());
        ris.put(m.getId(), m); 
    }
    return ris;
}

    @Override
    public void update(Macchinetta m) {

        database.executeUpdate(updateProdotto, 
                               m.getGenere(), 
                               m.getBrand(), 
                               String.valueOf(m.getPrezzo()), 
                               String.valueOf(m.getDisponibilita()), 
                               String.valueOf(m.getPeso()), 
                               String.valueOf(m.getId()));
                               
        database.executeUpdate(updateMacchinetta, 
                               m.getUtilizzo(), 
                               m.getColore(), 
                               m.getModello(), 
                               String.valueOf(m.getSerbatoio()), 
                               String.valueOf(m.getId()));
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(deleteMacchinetta, String.valueOf(id));
    }

  
}
