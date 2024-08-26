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
public class ProdottoService extends GenericService<Prodotto, ProdottoDAO> {

    @Autowired
    private OrdineDAO ordineDAO;

    public List<Prodotto> findByIdOrdine(int idOrdine) {
        Map<Integer, Entity> prodotti = getRepository().readByIdOrdine(idOrdine);

        return prodotti.values().stream().map(e -> (Prodotto)e).toList();
    }

    public List<Prodotto> findyByRangePrezzo(double min, double max) {
        Map<Integer, Entity> prodotti = getRepository().readByRangePrezzo(min, max);
        List<Prodotto> listaProdotti = new ArrayList<>();
        for (Entity e : prodotti.values()) {
            Prodotto p = (Prodotto) e;
            // Map<Integer, Entity> ordini = ordineDAO.readByIdProdotto(p.getId());
            // List<Ordine> listaOrdini = new ArrayList<>();
            // for (Entity o : ordini.values()) {
            //     listaOrdini.add((Ordine) o);
            // }
            // p.setOrdini(listaOrdini);
            listaProdotti.add(p);
        }
        return listaProdotti;
    }

    public List<Prodotto> findByFilters(String genere, String brand) {
        Map<Integer, Entity> prodotti = getRepository().findByFilters(genere, brand);
        List<Prodotto> listaProdotti = new ArrayList<>();
        for (Entity e : prodotti.values()) {
            Prodotto p = (Prodotto) e;
            listaProdotti.add(p);
        }
        return listaProdotti;
    }
    
}
