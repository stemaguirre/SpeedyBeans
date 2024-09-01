package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Caffe;
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

    private final String findByUtilizzoLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean where m.utilizzo like(concat('%', ?, '%'))";

    private final String findByColoreLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean where m.colore like(concat('%', ?, '%'))";

    private final String findByBrandLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean where p.brand like(concat('%', ?, '%'))";

    private final String findByUtilizzoColoreLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean where m.utilizzo like(concat('%', ?, '%')) AND m.colore like(concat('%', ?, '%'))";

    private final String findByBrandUtilizzoLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean where p.brand like(concat('%', ?, '%')) AND m.utilizzo like(concat('%', ?, '%'))";;

    private final String findByBrandColoreLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean where p.brand like(concat('%', ?, '%')) AND m.colore like(concat('%', ?, '%'))";;

    private final String findByFilters = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean where m.utilizzo like(concat('%', ?, '%')) AND m.colore like(concat('%', ?, '%')) AND p.brand like(concat('%', ?, '%'))";

    private final String orderByPrezzo = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, m.utilizzo, m.colore, m.modello, m.serbatoio from macchinette m join prodotti p on m.id_ean = p.id_ean order by p.prezzo";



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

    public Map<Integer, Entity> findByFilters(String brand, String utilizzo, String colore) {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = null;

        if(utilizzo.equals("") && colore.equals("") && brand.equals("")) {
            result = database.executeQuery(readAllMacchinette);
        } else if(colore.equals("") & !utilizzo.equals("") & brand.equals("")) {
            result = database.executeQuery(findByUtilizzoLike, utilizzo);
        } else if (utilizzo.equals("") & !colore.equals("") & brand.equals("")) {
            result = database.executeQuery(findByColoreLike, colore);
        } else if (utilizzo.equals("") & colore.equals("") & !brand.equals("")) {
            result = database.executeQuery(findByBrandLike, brand);
        } else if(utilizzo.equals("") && !colore.equals("") && !brand.equals("")){
            result = database.executeQuery(findByBrandColoreLike, brand, colore);
        } else if(!utilizzo.equals("") && !colore.equals("") && brand.equals("")){
            result = database.executeQuery(findByUtilizzoColoreLike, utilizzo, colore);
        } else if(!utilizzo.equals("") && colore.equals("") && !brand.equals("")){
            result = database.executeQuery(findByBrandUtilizzoLike, brand, utilizzo);
        } else if(!utilizzo.equals("") && !colore.equals("") && !brand.equals("")){
            result = database.executeQuery(findByFilters, utilizzo, colore, brand);
        }

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Macchinetta m = context.getBean(Macchinetta.class, coppia.getValue());
            ris.put(m.getId(), m);
        }

        return ris;

    }

    public Map<Integer, Entity> findByFilters(String brand) {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = null;
        
        result = database.executeQuery(findByBrandLike, brand);
        
        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
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

    public Map<Integer, Entity> orderByPrezzo() {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(orderByPrezzo);

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Macchinetta c = context.getBean(Macchinetta.class, coppia.getValue());
            ris.put(c.getId(), c);
        }

        return ris;
    }

  
}
