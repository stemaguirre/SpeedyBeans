package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Entity;

@Service
public class MacchinettaDAO implements IDAO<Macchinetta> {

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    private final String insertProdotto = "INSERT INTO prodotti (genere, brand, prezzo, disponibilita, peso) VALUES (?, ?, ?, ?, ?)";
    private final String insertMacchinetta = "INSERT INTO macchinette(id_ean, utilizzo, colore, modello, serbatoio) VALUES (?, ?, ?, ?, ?)";


    private final String readAllMacchinette = "SELECT p.*, m.utilizzo, m.colore, m.modello, m.serbatoio FROM macchinette m JOIN prodotti p ON m.id_ean = p.id_ean";

    private final String updateProdotto = "update prodotti set genere = ?, brand = ?, prezzo = ?, disponibilita = ?, peso = ? where id_ean = ?";
    private final String updateMacchinetta = "UPDATE macchinette SET utilizzo = ?, colore = ?, modello = ?, serbatoio = ? WHERE id_ean = ?";
    private final String deleteMacchinetta = "DELETE FROM macchinette WHERE id_ean = ?";

    private final String findByUtilizzoLike = "select m.*, p.* from macchinette m join prodotti p on m.id_ean = p.id_ean where p.utilizzo like(concat('%', ?, '%'))";

    private final String findByColoreLike = "select m.*, p.* from macchinette m join prodotti p on m.id_ean = p.id_ean where m.colore like(concat('%', ?, '%'))";

    private final String findByFilters = "select m.*, p.* from macchinette m join prodotti p on m.id_ean = p.id_ean where m.utilizzo like(concat('%', ?, '%')) AND m.colore like(concat('%', ?, '%'))";



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
        ris.put(coppia.getKey(), m); 
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

    public Map<Integer, Entity> findByFilters(String utilizzo, String colore) {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = null;

        if(colore == null) {
            result = database.executeQuery(findByUtilizzoLike, utilizzo);
        } else if (utilizzo == null) {
            result = database.executeQuery(findByColoreLike, colore); 
        }else {
            result = database.executeQuery(findByFilters, utilizzo, colore);
        }

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Macchinetta m = context.getBean(Macchinetta.class, coppia.getValue());
            ris.put(m.getId(), m);
        }

        return ris;

    }

    @Override
    public Macchinetta readById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readById'");
    }

  
}
