package com.generation.SpeedyBeans.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.dao.CaffeDAO;
import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.Entity;

@Service
public class CaffeService extends GenericService<Caffe, CaffeDAO> {

    public List<Caffe> findByIdOrdine(int idOrdine) {
        Map<Integer, Entity> caffes = getRepository().readByIdOrdine(idOrdine);

        return caffes.values().stream().map(e -> (Caffe)e).toList();
    }
    
    public List<Caffe> findByFilters(String brand, String formato, String tipologia) {
        List<Caffe> ris = new ArrayList<>();

        Map<Integer, Entity> caffes = getRepository().findByFilters(brand, formato, tipologia);

        for (Entity e : caffes.values()) {
            ris.add((Caffe) e);
        }

        return ris;
    }

    public List<Caffe> findByFilters(String brand) {
        List<Caffe> ris = new ArrayList<>();

        Map<Integer, Entity> caffes = getRepository().findByFilters(brand);

        for (Entity e : caffes.values()) {
            ris.add((Caffe) e);
        }

        return ris;
    }
}
