package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Prodotto;

@Service
public class ProdottoDAO implements IDAO<Prodotto>
{

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    private final String insertProdotto = "insert into prodotti(id_EAN, genere, brand, prezzo, disponibilità, peso) values(?,?,?,?,?,?)";

    private final String readAllProdotti = "select id_ean as id, genere, brand, prezzo, disponibilita, peso from prodotti";

    private final String updateProdotto = "UPDATE prodotto SET  genere = ?, brand = ?, prezzo = ?, disponibilita = ?, peso = ? WHERE id_EAN = ?";

    private final String deleteProdotto = "DELETE FROM prodotti WHERE id_EAN = ?";

    private final String readByIdOrdine = "select p.id_ean, p.genere, p.brand, p.prezzo, p.disponibilita, p.peso, o.id_ordine, o.id_persona, o.quantita, o.iva, o.totale from prodotti p join ordini_prodotti op on p.id_ean = op.id_ean join ordini o on op.id_ordine = o.id_ordine where o.id_ordine = ?";

    private final String readByRange = "SELECT * FROM prodotti WHERE prezzo BETWEEN ? AND ?";

    private final String findByGenereLike = "SELECT * FROM prodotti WHERE genere LIKE CONCAT('%', ?, '%')";
    private final String findByBrandLike = "SELECT * FROM prodotti WHERE brand LIKE CONCAT('%', ?, '%')";
    private final String findByFilters = "SELECT * FROM prodotti WHERE genere LIKE CONCAT('%', ?, '%') AND brand LIKE CONCAT('%', ?, '%')";



    @Override
    public int create(Prodotto e) {
        int id = database.executeUpdate(insertProdotto, String.valueOf(e.getId()), e.getGenere(), e.getBrand(), String.valueOf(e.getPrezzo()), String.valueOf(e.getDisponibilita()), String.valueOf(e.getPeso()));

        return id;
    }

    @Override
    public Map<Integer, Entity> readAll() {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readAllProdotti);

        for (Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Prodotto p = (Prodotto)context.getBean("newProdotto", coppia.getValue());

            ris.put(p.getId(), p);
        }

        return ris;
    }

    @Override
    public void update(Prodotto p) {
        database.executeUpdate(updateProdotto, p.getGenere(), p.getBrand(), String.valueOf(p.getPrezzo()), String.valueOf(p.getDisponibilita()), String.valueOf(p.getPeso()), String.valueOf(p.getId()));
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(deleteProdotto, String.valueOf(id));
    }

    public Prodotto readById(int id) {
        Map<Integer, Entity> allProdotti = readAll();
        return (Prodotto)allProdotti.get(id);
    }

    public Map<Integer,Entity> readByIdOrdine(int idOrdine){
        Map<Integer, Entity> ris = new LinkedHashMap<>();

        Map<Integer, Map<String, String>> result = database.executeQuery(readByIdOrdine, String.valueOf(idOrdine));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Prodotto p = (Prodotto)context.getBean("newProdotto", coppia.getValue());
            ris.put(p.getId(), p);


        }
        return ris;
    }

    public Map<Integer,Entity> readByRangePrezzo(double min, double max){
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readByRange, String.valueOf(min), String.valueOf(max));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Prodotto p = (Prodotto)context.getBean("newProdotto", coppia.getValue());
            ris.put(p.getId(), p);
        }
        return ris;

    }

    public Map<Integer, Entity> findByFilters(String genere, String brand) {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = null;

        if(brand == null) {
            result = database.executeQuery(findByGenereLike, genere);
        } else if (genere == null) {
            result = database.executeQuery(findByBrandLike, brand); 
        }else {
            result = database.executeQuery(findByFilters, genere, brand);
        }

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Prodotto p = (Prodotto)context.getBean("newProdotto", coppia.getValue());
            ris.put(p.getId(), p);
        }

        return ris;

    }
}
