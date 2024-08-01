package com.generation.SpeedyBeans.dao;

import java.util.Map;

import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.GenericEntity;

public class CaffeDAO implements IDAO<Caffe>{

    
    private final String insertProdotto = "insert into prodotti (genere, brand, prezzo, disponibilita, peso) values (?, ?, ?, ?, ?)";
    private final String insertCaffe = "insert into caffe (idProdotto, nome, descrizione, prezzo, tipologia, dataProduzione, dataScadenza, formato) values (?, ?, ?, ?, ?, ?, ?, ?)";
    
    private final String readAllCaffes = "select * from caffe c join prodotto p on c.idProdotto = p.idProdotto";

    private final String updateProdotto = "update prodotti set genere = ?, brand = ?, prezzo = ?, disponibilita = ?, peso = ? where idProdotto = ?";
    private final String updateCaffe = "update caffes set nome = ?, descrizione = ?, prezzo = ?, tipologia = ?, dataProduzione = ?, dataScadenza = ?, formato = ? where idProdotto = ?";

    private final String deleteProdotto = "delete from prodotti where idProdotto = ?";


    @Override
    public int create(Caffe c) {
        
    }

    @Override
    public Map<Integer, GenericEntity> readAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'readAll'");
    }

    @Override
    public void update(Caffe e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }
    
}
