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

<<<<<<< HEAD
    private final String readAllProdotto = "SELECT * FROM prodotti";
=======
    private final String readAllProdotti = "SELECT * FROM prodotti";
>>>>>>> stefano

    private final String updateProdotto = "UPDATE prodotto SET  genere = ?, brand = ?, prezzo = ?, disponibilita = ?, peso = ? WHERE id_EAN = ?";

    private final String deleteProdotto = "DELETE FROM prodotti WHERE id_EAN = ?";

<<<<<<< HEAD
=======
    private final String readByIdOrdine = "select p.* from prodotti p join ordini_prodotti op on p.id_ean = od.id_ean join ordini o on od.id_ordine = p.id_ordine where p.id_ordine = ?";
>>>>>>> stefano


    @Override
    public int create(Prodotto e) {
<<<<<<< HEAD
        return database.executeUpdate(insertProdotto, e.getIdEAN(), e.getGenere(), e.getBrand(), e.getPrezzo(), e.getDisponibilita(), e.getPeso());
=======
        int id = database.executeUpdate(insertProdotto, String.valueOf(e.getId()), e.getGenere(), e.getBrand(), String.valueOf(e.getPrezzo()), String.valueOf(e.getDisponibilita()), String.valueOf(e.getPeso()));

        return id;
>>>>>>> stefano
    }

    @Override
    public Map<Integer, Entity> readAll() {
        Map<Integer, Entity> ris = new LinkedHashMap<>();
<<<<<<< HEAD
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
=======
        Map<Integer, Map<String, String>> result = database.executeQuery(readAllProdotti);

        for (Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Prodotto p = context.getBean(Prodotto.class, coppia.getValue());
            
            ris.put(p.getId(), p);
        }

        return ris;
    }

    @Override
    public void update(Prodotto p) {
        database.executeUpdate(updateProdotto, p.getGenere(), p.getBrand(), String.valueOf(p.getPrezzo()), String.valueOf(p.getDisponibilita()), String.valueOf(p.getPeso()), String.valueOf(p.getId()));
>>>>>>> stefano
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(deleteProdotto, String.valueOf(id));
    }

    public Prodotto readById(int id) {
<<<<<<< HEAD
        Map<Integer, Entity>  allProdotti = readAll();
        return (Prodotto)allProdotti.get(id);
    }
}
    

=======
        Map<Integer, Entity> allProdotti = readAll();
        return (Prodotto)allProdotti.get(id);
    }

    public Map<Integer,Entity> readByIdOrdine(int idOrdine){
        Map<Integer, Entity> ris = new LinkedHashMap<>();

        Map<Integer, Map<String, String>> result = database.executeQuery(readByIdOrdine, String.valueOf(idOrdine));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Prodotto p = context.getBean(Prodotto.class, coppia.getValue());
            ris.put(p.getId(), p);


        }
        return ris;
    }
}
>>>>>>> stefano
