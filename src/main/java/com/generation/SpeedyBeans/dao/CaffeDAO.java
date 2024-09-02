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
    private final String updateCaffe = "update caffes set tipologia = ?, dataProduzione = ?, dataScadenza = ?, formato = ? where id_ean = ?";

    private final String deleteProdotto = "delete from prodotti where id_ean = ?";

    private final String findByFormatoLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where c.formato like(concat('%', ?, '%'))";

    private final String findByTipologiaLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where c.tipologia like(concat('%', ?, '%'))";

    private final String findByBrandLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where p.brand like(concat('%', ?, '%'))";

    private final String findByFormatoTipologiaLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where c.formato like(concat('%', ?, '%')) AND c.tipologia like(concat('%', ?, '%'))";

    private final String findByFormatoBrandLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where c.formato like(concat('%', ?, '%')) AND p.brand like(concat('%', ?, '%'))";

    private final String findByTipologiaBrandLike = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where c.tipologia like(concat('%', ?, '%')) AND p.brand like(concat('%', ?, '%'))";

    private final String findByFilters = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean where c.formato like(concat('%', ?, '%')) AND c.tipologia like(concat('%', ?, '%')) AND p.brand like(concat('%', ?, '%'))";

    private final String orderByPrezzo = "select p.id_ean as id, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, c.tipologia, c.data_produzione, c.data_scadenza, c.formato from caffes c join prodotti p on c.id_ean = p.id_ean order by p.prezzo";


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

    
    public Map<Integer, Entity> findByFilters(String brand, String formato, String tipologia) {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = null;

        if(formato.equals("") && tipologia.equals("") && brand.equals("")) {
            result = database.executeQuery(readAllCaffes);
        } else if (tipologia.equals("") && !formato.equals("")  && brand.equals("")) {
            result = database.executeQuery(findByFormatoLike, formato);
        } else if (formato.equals("") && !tipologia.equals("") && brand.equals("")) {
            result = database.executeQuery(findByTipologiaLike, tipologia);
        } else if (formato.equals("") && tipologia.equals("") && !brand.equals("")) {
            result = database.executeQuery(findByBrandLike, brand);
        } else if (!formato.equals("") && !tipologia.equals("") && brand.equals("")) {
            result = database.executeQuery(findByFormatoTipologiaLike, formato, tipologia);
        } else if (!formato.equals("") && tipologia.equals("") && !brand.equals("")) {
            result = database.executeQuery(findByFormatoBrandLike, formato, brand);
        } else if (formato.equals("") && !tipologia.equals("") && !brand.equals("")) {
            result = database.executeQuery(findByTipologiaBrandLike, tipologia, brand);
        } else if(!formato.equals("") && !tipologia.equals("") && !brand.equals("")){
            result = database.executeQuery(findByFilters, formato, tipologia, brand);
        }

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Caffe c = context.getBean(Caffe.class, coppia.getValue());
            ris.put(c.getId(), c);
        }

        return ris;

    }

    public Map<Integer, Entity> findByFilters(String brand) {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = null;

        result = database.executeQuery(findByBrandLike, brand);
        
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

    public Map<Integer, Entity> orderByPrezzo() {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(orderByPrezzo);

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Caffe c = context.getBean(Caffe.class, coppia.getValue());
            ris.put(c.getId(), c);
        }

        return ris;
    }
}
