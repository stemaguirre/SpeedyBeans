package com.generation.SpeedyBeans.dao;

import java.util.logging.Logger;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Persona;
import com.generation.SpeedyBeans.services.OrdineService;
import com.generation.SpeedyBeans.entities.Entity;

@Service
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

    private final String readByRange = "SELECT * FROM ordini WHERE totale BETWEEN ? AND ?";
    
    private final String findByNomeLike = "select o.*, p.* from Ordini o join Persone p on o.id_persona = p.id_persona where p.nome like(concat('%', ?, '%'))";
    private final String findByCognomeLike = "select o.*, p.* from Ordini o join Persone p on o.id_persona = p.id_persona where p.cognome like(concat('%', ?, '%'))";
    private final String findByNomeOrCognomeLike = "select o.*, p.* from Ordini o join Persone p on o.id_persona = p.id_persona where p.nome like(concat('%', ?, '%')) or p.cognome like(concat('%', ?, '%'))";

    private final String readByIdPersona = "select o.*, p.* from Ordini o join Persone p on o.id_persona = p.id_persona where p.id_persona = ?";

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
            String.valueOf(o.getId())
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

    public Map<Integer,Entity> readByRangeTotale(double min, double max){
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readByRange, String.valueOf(min), String.valueOf(max));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            ris.put(o.getId(), o);
        }
        return ris;

    }

    public Map<Integer,Entity> readByNomeCognomePersona(String nome, String cognome){
        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = new LinkedHashMap<>();

        if(nome != null && cognome != null){
            result = database.executeQuery(findByNomeOrCognomeLike, nome, cognome);
        } else if(nome == null){
            result = database.executeQuery(findByCognomeLike, cognome);
        } else if(cognome == null){
            result = database.executeQuery(findByNomeLike, nome);
        }
        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
            ris.put(o.getId(), o);
        }
        return ris;

    }

    public Map<Integer,Entity> readByIdPersona(int idPersona){
        Logger.getLogger(OrdineDAO.class.getName()).info("readByIdPersona called with idPersona: " + idPersona);

        Map<Integer, Entity> ris = new LinkedHashMap<>();
        Map<Integer, Map<String, String>> result = database.executeQuery(readByIdPersona, String.valueOf(idPersona));

        for(Entry<Integer, Map<String, String>> coppia : result.entrySet()){
            Ordine o = context.getBean(Ordine.class, coppia.getValue());
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