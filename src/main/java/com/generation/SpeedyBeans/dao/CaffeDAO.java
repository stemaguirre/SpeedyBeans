package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.Entity;

@Service
public class CaffeDAO implements IDAO<Caffe>{

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    
    private final String insertProdotto = "insert into prodotti (genere, brand, prezzo, disponibilita, peso) values (?, ?, ?, ?, ?)";
    private final String insertCaffe = "insert into caffes (id_ean, tipologia, data_produzione, data_scadenza, formato) values (?, ?, ?, ?, ?)";
    
    private final String readAllCaffes = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean";
    private final String readCaffeById = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where p.id_ean = ?";
    private final String readCaffesByIdOrdine = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where p.id_ean in (select id_ean from ordini_prodotti where id_ordine = ?)";

    private final String updateProdotto = "update prodotti set genere = ?, brand = ?, prezzo = ?, disponibilita = ?, peso = ? where id_ean = ?";
    private final String updateCaffe = "update caffes set tipologia = ?, data_produzione = ?, data_scadenza = ?, formato = ? where id_ean = ?";

    private final String deleteProdotto = "delete from prodotti where id_ean = ?";

    private final String findByFormatoLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where c.formato like(concat('%', ?, '%'))";

    private final String findByTipologiaLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where c.tipologia like(concat('%', ?, '%'))";

    private final String findByFilters = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where c.formato like(concat('%', ?, '%')) AND c.tipologia like(concat('%', ?, '%'))";


    @Override
    public int create(Caffe c) {
        int id = database.executeUpdate(insertProdotto, c.getGenere(), c.getBrand(), String.valueOf(c.getPrezzo()), String.valueOf(c.getDisponibilita()), String.valueOf(c.getPeso()));

        database.executeUpdate(insertCaffe, String.valueOf(id), c.getTipologia(), c.getDataProduzione().toString(), c.getDataScadenza().toString(), c.getFormato());

        return id;
    }

    @Override
    public Map<Integer, Entity> readAll() {

        Map<Integer, Entity> ris = new LinkedHashMap<>();

        Map<Integer, Map<String, String>> result = database.executeQuery(readAllCaffes);

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Caffe c = context.getBean(Caffe.class, coppia.getValue());
            ris.put(coppia.getKey(), c);
        }

        return ris;
    }

    @Override
    public void update(Caffe c) {
        // Prima esegui l'update del prodotto
        database.executeUpdate(updateProdotto, c.getGenere(), c.getBrand(), String.valueOf(c.getPrezzo()), 
                               String.valueOf(c.getDisponibilita()), String.valueOf(c.getPeso()), String.valueOf(c.getId()));
    
        // Poi esegui l'update del caffè, gestendo le date che potrebbero essere null
        String dataProduzione = (c.getDataProduzione() != null) ? c.getDataProduzione().toString() : null;
        String dataScadenza = (c.getDataScadenza() != null) ? c.getDataScadenza().toString() : null;
    
        database.executeUpdate(updateCaffe, c.getTipologia(), dataProduzione, dataScadenza, 
                               c.getFormato(), String.valueOf(c.getId()));
    }

    @Override
    public void delete(int id) {

        database.executeUpdate(deleteProdotto, String.valueOf(id));
    }

    
    public Map<Integer, Entity> findByFilters(String formato, String tipologia) {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = null;
    
        // Considera anche stringhe vuote come null
        if((formato == null || formato.isEmpty()) && (tipologia == null || tipologia.isEmpty())) {
            result = database.executeQuery(readAllCaffes);
        } else if (tipologia == null || tipologia.isEmpty()) {
            result = database.executeQuery(findByFormatoLike, formato);
        } else if (formato == null || formato.isEmpty()) {
            result = database.executeQuery(findByTipologiaLike, tipologia);
        } else {
            result = database.executeQuery(findByFilters, formato, tipologia);
        }
    
        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Caffe c = context.getBean(Caffe.class, coppia.getValue());
            ris.put(c.getId(), c);
        }
    
        return ris;
    }
    

    @Override
    public Caffe readById(int id) {
        Map<Integer, Map<String, String>> result = database.executeQuery(readCaffeById, String.valueOf(id));
        Caffe c = null;

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            c = context.getBean(Caffe.class, coppia.getValue());
        }

        return c;
    }

    public Map<Integer, Entity> readByIdOrdine(int idOrdine) {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readCaffesByIdOrdine, String.valueOf(idOrdine));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Caffe c = context.getBean(Caffe.class, coppia.getValue());
            ris.put(c.getId(), c);
        }

        return ris;
    }
}
