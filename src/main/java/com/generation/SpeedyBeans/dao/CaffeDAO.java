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
    private final String insertCaffe = "insert into caffe (id_ean, tipologia, dataProduzione, dataScadenza, formato) values (?, ?, ?, ?, ?)";
    
    private final String readAllCaffes = "select p.*, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean";

    private final String updateProdotto = "update prodotti set genere = ?, brand = ?, prezzo = ?, disponibilita = ?, peso = ? where id_ean = ?";
    private final String updateCaffe = "update caffes set tipologia = ?, dataProduzione = ?, dataScadenza = ?, formato = ? where id_ean = ?";

    private final String deleteProdotto = "delete from prodotti where id_ean = ?";

    private final String findByFormatoLike = "select c.*, p.* from caffes c join prodotti p on c.id_ean = p.id_ean where p.formato like(concat('%', ?, '%'))";

    private final String findByTipologiaLike = "select c.*, p.* from caffes c join prodotti p on c.id_ean = p.id_ean where c.tipologia like(concat('%', ?, '%'))";

    private final String findByFilters = "select c.*, p.* from caffes c join prodotti p on c.id_ean = p.id_ean where p.formato like(concat('%', ?, '%')) AND c.tipologia like(concat('%', ?, '%'))";


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
        database.executeUpdate(updateProdotto, c.getGenere(), c.getBrand(), String.valueOf(c.getPrezzo()), String.valueOf(c.getDisponibilita()), String.valueOf(c.getPeso()), String.valueOf(c.getId()));

        database.executeUpdate(updateCaffe, c.getTipologia(), c.getDataProduzione().toString(), c.getDataScadenza().toString(), c.getFormato(), String.valueOf(c.getId()));
    }

    @Override
    public void delete(int id) {

        database.executeUpdate(deleteProdotto, String.valueOf(id));
    }

    
    public Map<Integer, Entity> findByFilters(String formato, String tipologia) {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = null;

        if(tipologia == null) {
            result = database.executeQuery(findByFormatoLike, formato);
        } else if (formato == null) {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readById'");
    }
}
