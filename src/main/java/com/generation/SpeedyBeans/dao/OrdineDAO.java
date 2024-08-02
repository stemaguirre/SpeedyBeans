package com.generation.SpeedyBeans.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Entity;

@Repository
public class OrdineDAO implements IDAO<Ordine> {

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private PersonaDAO personaDAO;

    private final String insertOrdine = "INSERT INTO ordini(id_persona, quantita, iva, totale) VALUES (?, ?, ?, ?)";

    private final String readAllOrdini = "SELECT * FROM ordini";

    private final String updateOrdine = "UPDATE ordini SET id_persona = ?, quantita = ?, iva = ?, totale = ? WHERE id_ordine = ?";

    private final String deleteOrdine = "DELETE FROM ordini WHERE id_ordine = ?";

    private final String readByIdProdotto = "select o.* from ordini d join ordini_prodotti op on o.id_ordine = od.id_ordine join prodotti p on od.id_ean = p.id_ean where p.id_ean = ?";

  
    

    @Override
    public int create(Ordine o) {
        int id = database.executeUpdate(insertOrdine, 
            String.valueOf(o.getPersona().getId()), 
            String.valueOf(o.getQuantita()), 
            o.isIva() ? "1" : "0", 
            String.valueOf(o.getTotale())
        );

        return id;
    }

    @Override
    public Map<Integer, Entity> readAll() {
<<<<<<< HEAD
        Map<Integer, Entity> resultMap = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readAllOrdini);

        for (Entry<Integer, Map<String, String>> entry : result.entrySet()) {
            Map<String, String> data = entry.getValue();
            Ordine ordine = context.getBean(Ordine.class, data);

            // Recupera e imposta le macchine e i caffè associati all'ordine
            readProdottiByOrdineId(ordine.getIdOrdine(), ordine);

            resultMap.put(ordine.getIdOrdine(), ordine);
        }

        return resultMap;
    }

    @Override
    public Ordine readById(int id) {
        Map<Integer, Map<String, String>> result = database.executeQuery(readOrdineById, String.valueOf(id));
        if (result.isEmpty()) {
            return null;
        }
        Map<String, String> data = result.values().iterator().next();
        Ordine ordine = context.getBean(Ordine.class, data);
        return ordine;
    }

    @Override
    public void update(Ordine ordine) {
        database.executeUpdate(updateOrdine, 
            String.valueOf(ordine.getPersona().getIdPersona()),
            String.valueOf(ordine.getQuantita()),
            ordine.isIva() ? "1" : "0", 
            String.valueOf(ordine.getTotale()),
            String.valueOf(ordine.getIdOrdine())
        );

        // Aggiorna i prodotti associati
        database.executeUpdate("DELETE FROM ordine_prodotti WHERE id_ordine = ?", String.valueOf(ordine.getIdOrdine()));

        for (Macchinetta macchina : ordine.getMacchine()) {
            database.executeUpdate(insertOrdineProdotti,
                String.valueOf(ordine.getIdOrdine()),
                macchina.getIdEAN()
            );
        }

        for (Caffe caffe : ordine.getCaffe()) {
            database.executeUpdate(insertOrdineProdotti,
                String.valueOf(ordine.getIdOrdine()),
                caffe.getIdEAN()
            );
        }
=======

        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readAllOrdini);

        for (Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            Persona p = personaDAO.readByid(Integer.parseInt(coppia.getValue().get("id_persona")));
            o.setPersona(p);
            ris.put(o.getId(), o);
        }

        return ris;
    }


    @Override
    public void update(Ordine o) {
        database.executeUpdate(updateOrdine, 
            String.valueOf(o.getIdPersona()),
            String.valueOf(o.getQuantita()),
            o.isIva() ? "1" : "0", 
            String.valueOf(o.getTotale()),
            String.valueOf(o.getId())
        );

>>>>>>> stefano
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(deleteOrdine, String.valueOf(id));
    }

<<<<<<< HEAD
    //readByUtenteId: Recupera tutti gli ordini per un utente specifico.
    public List<Ordine> readByUtenteId(int utenteId) {
        List<Ordine> ordini = new ArrayList<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readOrdiniByUtenteId, String.valueOf(utenteId));
        for (Entry<Integer, Map<String, String>> entry : result.entrySet()) {
            Map<String, String> data = entry.getValue();
            Ordine ordine = context.getBean(Ordine.class, data);
            ordini.add(ordine);
        }
        return ordini;
    }

    //readMacchinetteByOrdineId: Recupera tutte le macchine per un ordine specifico
    public List<Macchinetta> readMacchinetteByOrdineId(int ordineId) {
        List<Macchinetta> macchinette = new ArrayList<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readMacchinetteByOrdineId, String.valueOf(ordineId));
        for (Entry<Integer, Map<String, String>> entry : result.entrySet()) {
            Map<String, String> data = entry.getValue();
            Macchinetta macchinetta = context.getBean(Macchinetta.class, data);
            macchinette.add(macchinetta);
        }
        return macchinette;
    }

    //readCaffeByOrdineId: Recupera tutti i caffè per un ordine specifico.
    public List<Caffe> readCaffeByOrdineId(int ordineId) {
        List<Caffe> caffeList = new ArrayList<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readCaffeByOrdineId, String.valueOf(ordineId));
        for (Entry<Integer, Map<String, String>> entry : result.entrySet()) {
            Map<String, String> data = entry.getValue();
            Caffe caffe = context.getBean(Caffe.class, data);
            caffeList.add(caffe);
        }
        return caffeList;
    }

    //readProdottiByOrdineId: Recupera sia macchine che caffè 
    //per un ordine specifico e aggiorna direttamente l'oggetto Ordine.
    public void readProdottiByOrdineId(int idOrdine, Ordine ordine) {
        List<Macchinetta> macchine = new ArrayList<>();
        List<Caffe> caffeList = new ArrayList<>();

        // Leggi le macchine
        Map<Integer, Map<String, String>> macchineResult = database.executeQuery("SELECT * FROM macchinette WHERE id_EAN IN (SELECT id_EAN FROM ordine_prodotti WHERE id_ordine = ?)", String.valueOf(idOrdine));
        for (Entry<Integer, Map<String, String>> entry : macchineResult.entrySet()) {
            Map<String, String> data = entry.getValue();
            Macchinetta macchina = context.getBean(Macchinetta.class, data);
            macchine.add(macchina);
        }

        // Leggi i caffè
        Map<Integer, Map<String, String>> caffeResult = database.executeQuery("SELECT * FROM caffe WHERE id_EAN IN (SELECT id_EAN FROM ordine_prodotti WHERE id_ordine = ?)", String.valueOf(idOrdine));
        for (Entry<Integer, Map<String, String>> entry : caffeResult.entrySet()) {
            Map<String, String> data = entry.getValue();
            Caffe caffe = context.getBean(Caffe.class, data);
            caffeList.add(caffe);
        }

        ordine.setMacchine(macchine);
        ordine.setCaffe(caffeList);
    }
}
=======
    public Map<Integer,Entity> readByIdProdotto(int idProdotto){
        Map<Integer, Entity> ris = new LinkedHashMap<>();

        Map<Integer, Map<String, String>> result = database.executeQuery(readByIdProdotto, String.valueOf(idProdotto));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            ris.put(o.getId(), o);


        }
        return ris;
    }

}
>>>>>>> stefano
