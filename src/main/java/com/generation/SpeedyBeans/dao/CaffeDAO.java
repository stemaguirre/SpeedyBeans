package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.GenericEntity;
import com.generation.SpeedyBeans.entities.Ordine;

@Repository
public class CaffeDAO implements IDAO<Caffe> {

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    // Definizione delle query SQL
    private final String insertCaffe = "INSERT INTO caffe(id_ean, tipologia, data_produzione, data_scadenza, formato, prezzo, disponibilita, peso, genere, brand) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String readAllCaffe = "SELECT * FROM caffe";
    private final String readCaffeById = "SELECT * FROM caffe WHERE id_ean = ?";
    private final String updateCaffe = "UPDATE caffe SET tipologia = ?, data_produzione = ?, data_scadenza = ?, formato = ?, prezzo = ?, disponibilita = ?, peso = ?, genere = ?, brand = ? WHERE id_ean = ?";
    private final String deleteCaffe = "DELETE FROM caffe WHERE id_ean = ?";

    @Override
    public int create(Caffe caffe) {
        return database.executeUpdate(insertCaffe, 
            caffe.getIdEAN(),
            caffe.getTipologia(),
            caffe.getDataProduzione().toString(),
            caffe.getDataScadenza().toString(),
            caffe.getFormato(),
            String.valueOf(caffe.getPrezzo()),
            String.valueOf(caffe.getDisponibilita()),
            String.valueOf(caffe.getPeso()),
            caffe.getGenere(),
            caffe.getBrand()
        );
    }

    @Override
    public Map<Integer, GenericEntity> readAll() {
        Map<Integer, GenericEntity> resultMap = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readAllCaffe);

        for (Entry<Integer, Map<String, String>> entry : result.entrySet()) {
            Map<String, String> data = entry.getValue();
            Caffe caffe = context.getBean(Caffe.class);
            caffe.fromMap(data);
            resultMap.put(caffe.getIdEAN().hashCode(), caffe);
        }

        return resultMap;
    }

    @Override
    public Caffe readById(int id) {
        Map<Integer, Map<String, String>> result = database.executeQuery(readCaffeById, String.valueOf(id));
        Map<String, String> data = result.values().iterator().next();
        Caffe caffe = context.getBean(Caffe.class);
        caffe.fromMap(data);
        return caffe;
    }

    @Override
    public void update(Caffe caffe) {
        database.executeUpdate(updateCaffe, 
            caffe.getTipologia(),
            caffe.getDataProduzione().toString(),
            caffe.getDataScadenza().toString(),
            caffe.getFormato(),
            String.valueOf(caffe.getPrezzo()),
            String.valueOf(caffe.getDisponibilita()),
            String.valueOf(caffe.getPeso()),
            caffe.getGenere(),
            caffe.getBrand(),
            caffe.getIdEAN()
        );
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(deleteCaffe, String.valueOf(id));
    }


}