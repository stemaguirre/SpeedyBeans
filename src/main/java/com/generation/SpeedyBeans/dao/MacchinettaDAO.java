package com.generation.SpeedyBeans.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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


    private final String readAllMacchinette = "SELECT p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio FROM macchinette m JOIN prodotti p ON m.id_ean = p.id_ean";
    private final String readMacchinettaById = "SELECT p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio FROM macchinette m JOIN prodotti p ON m.id_ean = p.id_ean WHERE p.id_ean = ?";
    private final String readMacchinettaByIdOrdine = "SELECT p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio FROM macchinette m JOIN prodotti p ON m.id_ean = p.id_ean WHERE p.id_ean IN (SELECT id_ean FROM ordini_prodotti WHERE id_ordine = ?)";

    private final String updateProdotto = "update prodotti set genere = ?, brand = ?, prezzo = ?, disponibilita = ?, peso = ? where id_ean = ?";
    private final String updateMacchinetta = "UPDATE macchinette SET utilizzo = ?, colore = ?, modello = ?, serbatoio = ? WHERE id_ean = ?";
    private final String deleteMacchinetta = "DELETE FROM macchinette WHERE id_ean = ?";

    private final String findByUtilizzoLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean where p.utilizzo like(concat('%', ?, '%'))";

    private final String findByColoreLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean where m.colore like(concat('%', ?, '%'))";

    private final String findByFilters = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean where m.utilizzo like(concat('%', ?, '%')) AND m.colore like(concat('%', ?, '%'))";



    @Override
    public int create(Macchinetta m) {

        int id = database.executeUpdate(insertProdotto, 
                                        m.getGenere(), 
                                        m.getBrand(), 
                                        String.valueOf(m.getPrezzo()), 
                                        String.valueOf(m.getDisponibilita()), 
                                        String.valueOf(m.getPeso()) );

        database.executeUpdate(insertMacchinetta, 
                                String.valueOf(id), 
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

    public Map<Integer, Entity> findByFilters(String utilizzo, String colore, String brand) {
    Map<Integer, Entity> ris = new LinkedHashMap<>();
    Map<Integer, Map<String, String>> result = null;

    // Costruisci dinamicamente la query in base ai parametri presenti
    StringBuilder queryBuilder = new StringBuilder("select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean where 1=1");
    List<String> parameters = new ArrayList<>();


if (utilizzo != null && !utilizzo.isEmpty()) {
    queryBuilder.append(" and m.utilizzo like(concat('%', ?, '%'))");
    parameters.add(utilizzo);
}

if (colore != null && !colore.isEmpty()) {
    queryBuilder.append(" and m.colore like(concat('%', ?, '%'))");
    parameters.add(colore);
}



    // Esegui la query con i parametri raccolti
    result = database.executeQuery(queryBuilder.toString(), parameters.toArray(new String[0]));

    for (Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
        Macchinetta m = context.getBean(Macchinetta.class, coppia.getValue());
        ris.put(m.getId(), m);
    }

    return ris;
}


    @Override
    public Macchinetta readById(int id) {
        Map<Integer, Map<String, String>> result = database.executeQuery(readMacchinettaById, String.valueOf(id));
        Macchinetta m = null;

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            m = context.getBean(Macchinetta.class, coppia.getValue());
        }

        return m;
    }

    public Map<Integer, Entity> readByIdOrdine(int idOrdine) {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readMacchinettaByIdOrdine, String.valueOf(idOrdine));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Macchinetta m = context.getBean(Macchinetta.class, coppia.getValue());
            ris.put(m.getId(), m);
        }

        return ris;
    }

  
}
