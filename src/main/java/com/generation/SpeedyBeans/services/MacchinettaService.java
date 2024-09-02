package com.generation.SpeedyBeans.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.dao.MacchinettaDAO;
import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Macchinetta;


@Service
public class MacchinettaService extends GenericService<Macchinetta, MacchinettaDAO> {

    public List<Macchinetta> findByIdOrdine(int idOrdine) {
        Map<Integer, Entity> macchinette = getRepository().readByIdOrdine(idOrdine);

        return macchinette.values().stream().map(e -> (Macchinetta)e).toList();
    }
    
    public List<Macchinetta> findByFilters(String brand, String utilizzo, String colore) {
        List<Macchinetta> ris = new ArrayList<>();

        Map<Integer, Entity> macchinette = getRepository().findByFilters(brand, utilizzo, colore);

        for (Entity e : macchinette.values()) {
            ris.add((Macchinetta) e);
        }

        return ris;
    }

    public List<Macchinetta> findByFilters(String brand) {
        List<Macchinetta> ris = new ArrayList<>();

        Map<Integer, Entity> macchinette = getRepository().findByFilters(brand);

        for (Entity e : macchinette.values()) {
            ris.add((Macchinetta) e);
        }

        return ris;
    }

     public List<Macchinetta> orderByPrezzo() {
        List<Macchinetta> ris = new ArrayList<>();

        Map<Integer, Entity> macchinette = getRepository().orderByPrezzo();

        for (Entity e : macchinette.values()) {
            ris.add((Macchinetta) e);
        }

        return ris;
    }
}


