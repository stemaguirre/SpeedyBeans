package com.generation.SpeedyBeans.dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Persona;
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

}
