package com.generation.SpeedyBeans.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.dao.MacchinettaDAO;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Macchinetta;

@Service
public class MacchinettaService extends GenericService<Macchinetta, MacchinettaDAO> {
    
    public List<Macchinetta> findByFilters(String utilizzo, String colore) {
        List<Macchinetta> ris = new ArrayList<>();

        Map<Integer, Entity> macchinette = getRepository().findByFilters(utilizzo, colore);

        for (Entity e : macchinette.values()) {
            ris.add((Macchinetta) e);
        }

        return ris;
    }
}
