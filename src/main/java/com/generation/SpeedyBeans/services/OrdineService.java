package com.generation.SpeedyBeans.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.dao.OrdineDAO;
import com.generation.SpeedyBeans.dao.ProdottoDAO;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Prodotto;

@Service
public class OrdineService extends GenericService<Ordine, OrdineDAO> {

    @Autowired
    private ProdottoDAO prodottoDAO;

    @Override
    public List<Ordine> readAll() {

        Map<Integer, Entity> ordini = getRepository().readAll();

        List<Ordine> listaOrdini = new ArrayList<>();

        for(Entity e : ordini.values()){
            Ordine o = (Ordine)e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for(Entity p : prodotti.values()){
                listaProdotti.add((Prodotto)p);
            }
            o.setProdotti(listaProdotti);

            listaOrdini.add(o);
        }

        return listaOrdini;
    }

    @Override
    public Ordine readById(int id) {
        
        Map<Integer, Entity> ordini = getRepository().readAll();
        Ordine o = (Ordine)ordini.get(id);

        if(o != null){
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for(Entity p : prodotti.values()){
                listaProdotti.add((Prodotto)p);
            }
            o.setProdotti(listaProdotti);
        }

        return o;

    }

    public List<Ordine> readByIdProdotto(int idProdotto) {
        Map<Integer, Entity> ordini = getRepository().readByIdProdotto(idProdotto);

        List<Ordine> listaOrdini = new ArrayList<>();

        for(Entity e : ordini.values()){
            Ordine o = (Ordine)e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for(Entity p : prodotti.values()){
                listaProdotti.add((Prodotto)p);
            }
            o.setProdotti(listaProdotti);

            listaOrdini.add(o);
        }

        return listaOrdini;
    }

    




    
   
}
