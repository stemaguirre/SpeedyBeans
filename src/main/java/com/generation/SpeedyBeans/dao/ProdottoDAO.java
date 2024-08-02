package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Prodotto;

public class ProdottoDAO implements IDAO<Prodotto>
{

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    private final String insertProdotto = "insert into prodotti(id_EAN, genere, brand, prezzo, disponibilità, peso) values(?,?,?,?,?,?)";

    private final String readAllProdotto = "SELECT * FROM prodotti";

    private final String updateProdotto = "UPDATE prodotto SET  genere = ?, brand = ?, prezzo = ?, disponibilita = ?, peso = ? WHERE id_EAN = ?";

    private final String deleteProdotto = "DELETE FROM prodotti WHERE id_EAN = ?";



    @Override
    public int create(Prodotto e) {
        return database.executeUpdate(insertProdotto, e.getIdEAN(), e.getGenere(), e.getBrand(), e.getPrezzo(), e.getDisponibilita(), e.getPeso());
    }

    @Override
    public Map<Integer, Entity> readAll() {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readAllProdotto);

        for (Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Prodotto prodotto = context.getBean(Prodotto.class);
            prodotto.fromMap(coppia.getValue());
            ris.put(prodotto.getIdEAN(), prodotto);
        }
    }

    @Override
    public void update(Prodotto e) {
        database.executeUpdate(updateProdotto, e.getGenere(), e.getBrand(), e.getPrezzo(), e.getDisponibilita(), e.getPeso(), e.getIdEAN());
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(deleteProdotto, String.valueOf(id));
    }

    public Prodotto readById(int id) {
        Map<Integer, Entity>  allProdotti = readAll();
        return (Prodotto)allProdotti.get(id);
    }
}
    

