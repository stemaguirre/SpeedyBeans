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
    
    public List<Caffe> findByFilters(String formato, String tipologia) {
        List<Caffe> ris = new ArrayList<>();

        Map<Integer, Entity> caffes = getRepository().findByFilters(formato, tipologia);

        for (Entity e : caffes.values()) {
            ris.add((Caffe) e);
        }

        return ris;
    }
}
