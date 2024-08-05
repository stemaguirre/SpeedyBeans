package com.generation.SpeedyBeans.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.generation.SpeedyBeans.dao.OrdineDAO;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Ordine;

public class OrdineService extends GenericService<Ordine, OrdineDAO> {

    public List<Ordine> readyByRange(double min, double max) {
        Map<Integer, Entity> ordini = getRepository().readAll();
        List<Ordine> listaOrdini = new ArrayList<>();
        for (Entity e : ordini.values()) {
            Ordine o = (Ordine) e;
            if (o.getTotale()>= min && o.getTotale() <= max) {
                listaOrdini.add(o);
            }
        }
        return listaOrdini;
    }

    public List<Ordine> readByPersona(String nome, String cognome) {
        Map<Integer, Entity> ordini = getRepository().readByPersona(nome, cognome);
        List<Ordine> listaOrdini = new ArrayList<>();
        for (Entity e : ordini.values()) {
            Ordine o = (Ordine) e;
            listaOrdini.add(o);
        }
        return listaOrdini;
    }
}
