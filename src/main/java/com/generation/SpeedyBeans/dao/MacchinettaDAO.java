package com.generation.SpeedyBeans.dao;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.database.Database;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Prodotto;

import lombok.Data;

@Service
public class MacchinettaDAO implements IDAO<Macchinetta>
{
    @Autowired
    private Database database;

    @Autowired
    private ApplicationContext context;

    private final String readAllMacchinette = "select * from macchinette m inner join prodotti p on m.id = p.id";

    private final String insertProdotto = "insert into prodotti(genere, brand, prezzo, disponibilita, peso) values (?, ?, ?, ?, ?)";
    private final String insertMacchinetta = "insert into macchinette(id_EAN, utilizzo, colore, modello, serbatoio) values (?, ?, ?, ?, ?)";
    private final String updateProdotto = "update prodotti= set genere=?, brand=?, prezzo=?, disponibilita=?, peso=? where id_EAN=?";
    private final String updateMacchinetta = "update macchinette= set utilizzo=?, colore=?, modello=?, serbatoio=? where id_EAN=?";
    private final String deletePersona = "delete from persone where id=?";
    //private final String readMacchinetteByIdEan = "select p.* from macchinette m join prodotti p on m.id = p.id join prodotti_macchinette pm on m.id = pm.id_ean where pm.id_ean = ?";

    @Override
    public int create(Macchinetta p) {
        int idEAN = database.executeUpdate(insertProdotto,
                                String.valueOf(p.getIdEAN()), 
                                p.getGenere(), 
                                p.getBrand(), 
                                String.valueOf(p.getPrezzo()),
                                String.valueOf(p.getDisponibilita()),
                                String.valueOf(p.getPeso())
                               
        );
    
        database.executeUpdate(insertProdotto, String.valueOf(idEAN));

        return idEAN;

    }
}
