package com.generation.SpeedyBeans.dao;

import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.entities.Entity;

@Service
public class OrdineDAO implements IDAO<Ordine> {

    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private PersonaDAO personaDAO;

    private final String insertOrdine = "INSERT INTO ordini(id_persona, quantita, iva, totale, data_ordine) VALUES (?, ?, ?, ?, ?)";
    private final String insertOrdineProdotti = "INSERT INTO Ordini_Prodotti (id_ordine, id_ean) VALUES (?, ?)";

    private final String readAllOrdini = "SELECT id_ordine as id, id_persona, quantita, iva, totale, data_ordine FROM ordini";

    private final String updateOrdine = "UPDATE ordini SET id_persona = ?, quantita = ?, iva = ?, totale = ?, data_ordine = ? WHERE id_ordine = ?";

    private final String deleteOrdine = "DELETE FROM ordini WHERE id_ordine = ?";

    private final String readByIdProdotto = "select o.id_ordine as id, o.id_persona, o.quantita, o.iva, o.totale from ordini o join ordini_prodotti op on o.id_ordine = od.id_ordine join prodotti p on od.id_ean = p.id_ean where p.id_ean = ?";

    private final String readByRangeTotale = "SELECT o.id_ordine as id, o.id_persona, o.quantita, o.iva, o.totale, o.data_ordine FROM ordini o WHERE totale BETWEEN ? AND ?";

    private final String readByTotaleMinimo = "SELECT o.id_ordine as id, o.id_persona, o.quantita, o.iva, o.totale, o.data_ordine FROM ordini o WHERE totale > ?";

    private final String readByTotaleMassimo = "SELECT o.id_ordine as id, o.id_persona, o.quantita, o.iva, o.totale, o.data_ordine FROM ordini o WHERE totale < ?";
    
    private final String findByNomeLike = "select o.id_ordine as id, o.id_persona, o.quantita, o.iva, o.totale, p.* from Ordini o join Persone p on o.id_persona = p.id_persona where p.nome like(concat('%', ?, '%'))";
    private final String findByCognomeLike = "select o.id_ordine as id, o.id_persona, o.quantita, o.iva, o.totale, p.* from Ordini o join Persone p on o.id_persona = p.id_persona where p.cognome like(concat('%', ?, '%'))";
    private final String findByNomeCognomeLike = "select o.id_ordine as id, o.id_persona, o.quantita, o.iva, o.totale, p.* from Ordini o join Persone p on o.id_persona = p.id_persona where p.nome like(concat('%', ?, '%')) AND p.cognome like(concat('%', ?, '%'))";

    private final String readByIdPersona = "select o.id_ordine as id, o.id_persona, o.quantita, o.iva, o.totale, o.data_ordine, p.* from Ordini o join Persone p on o.id_persona = p.id_persona where p.id_persona = ?";

    private final String findByDateRange = "SELECT o.id_ordine as id, o.id_persona, o.quantita, o.iva, o.totale, o.data_ordine FROM ordini o WHERE o.data_ordine BETWEEN ? AND ?";

    private final String findByDateInizio = "SELECT o.id_ordine as id, o.id_persona, o.quantita, o.iva, o.totale, o.data_ordine FROM ordini o WHERE o.data_ordine > ?";

    private final String findByDateFine = "SELECT o.id_ordine as id, o.id_persona, o.quantita, o.iva, o.totale, o.data_ordine FROM ordini o WHERE o.data_ordine < ?";


    @Override
    public int create(Ordine o) {
        int id = database.executeUpdate(insertOrdine, 
            String.valueOf(o.getPersona().getId()), 
            String.valueOf(o.getQuantita()), 
            o.isIva() ? "1" : "0", 
            String.valueOf(o.getTotale()),
            o.getDataOrdine().toString()
        );

        for (Prodotto p : o.getProdotti()){
            database.executeUpdate(insertOrdineProdotti, String.valueOf(id), String.valueOf(p.getId()));
        }
 
        return id;
    }


    @Override
    public Map<Integer, Entity> readAll() {

        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readAllOrdini);

        for (Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            if(coppia.getValue().get("id_persona") != null){
                Persona p = personaDAO.readById(Integer.parseInt(coppia.getValue().get("id_persona")));
                o.setPersona(p);
            }
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
            String.valueOf(o.getId()),
            o.getDataOrdine().toString()
        );

    }

    @Override
    public void delete(int id) {
        database.executeUpdate(deleteOrdine, String.valueOf(id));
    }

    public Map<Integer,Entity> readByIdProdotto(int idProdotto){
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readByIdProdotto, String.valueOf(idProdotto));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            ris.put(o.getId(), o);


        }
        return ris;
    }

    public Map<Integer,Entity> readByRangeTotale(int min, int max){
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readByRangeTotale, String.valueOf(min), String.valueOf(max));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            ris.put(o.getId(), o);
        }
        return ris;

    }

    public Map<Integer,Entity> readByTotaleMinimo(int min){
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readByTotaleMinimo, String.valueOf(min));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            ris.put(o.getId(), o);
        }
        return ris;

    }

    public Map<Integer,Entity> readByTotaleMassimo(int max){
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readByTotaleMassimo, String.valueOf(max));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            ris.put(o.getId(), o);
        }
        return ris;

    }

    public Map<Integer,Entity> readByNomeCognomePersona(String nome, String cognome){
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = new LinkedHashMap<>();

        if(nome.equals("")){
            result = database.executeQuery(findByCognomeLike, cognome);
        } else if(cognome.equals("")) {
            result = database.executeQuery(findByNomeLike, nome);
        } else {
            result = database.executeQuery(findByNomeCognomeLike, nome, cognome);
        }
        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            ris.put(o.getId(), o);
        }
        return ris;

    }

    public Map<Integer,Entity> readByIdPersona(int idPersona){

        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readByIdPersona, String.valueOf(idPersona));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            ris.put(o.getId(), o);
        }
        return ris;

    }

    public Map<Integer, Entity> findByDateRange(Date startDate, Date endDate) {

        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(findByDateRange, startDate.toString(), endDate.toString());
        
        for (Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            if (coppia.getValue().get("data_ordine") != null) {
                o.setDataOrdine(Date.valueOf(coppia.getValue().get("data_ordine")));
            }
            ris.put(o.getId(), o);
        }
        return ris;
    }

    public Map<Integer, Entity> findByDateInizio(Date startDate) {

        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(findByDateInizio, startDate.toString());
        
        for (Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            if (coppia.getValue().get("data_ordine") != null) {
                o.setDataOrdine(Date.valueOf(coppia.getValue().get("data_ordine")));
            }
            ris.put(o.getId(), o);
        }
        return ris;
    }

    public Map<Integer, Entity> findByDateFine(Date endDate) {

        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(findByDateFine, endDate.toString());
        
        for (Entry<Integer, Map<String, String>> coppia : result.entrySet()) {
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            if (coppia.getValue().get("data_ordine") != null) {
                o.setDataOrdine(Date.valueOf(coppia.getValue().get("data_ordine")));
            }
            ris.put(o.getId(), o);
        }
        return ris;
    }

    @Override
    public Ordine readById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readById'");
    }

    
    



}