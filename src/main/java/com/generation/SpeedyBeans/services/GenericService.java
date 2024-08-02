package com.generation.SpeedyBeans.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.generation.SpeedyBeans.dao.IDAO;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Utente;

import lombok.Getter;

@Getter
public abstract class GenericService<E extends Entity, D extends IDAO<E>> {
    
    @Autowired 
    private D repository;

    
    @Autowired
    private Utente utenteDAO;

    public int create(E e){
        return repository.create(e);
    }

    public List<E> readAll(){
        Map<Integer, Entity> result = repository.readAll();
        List<E> ris = new ArrayList<>();

        for (Entity e : result.values()) {
            ris.add((E)e);
        }

        return ris;
    }

    public E readById(int id){
        Map<Integer, Entity> result = repository.readAll();
        E e = (E)result.get(id);
        return e;
    }

    public void update(E e){
        repository.update(e);
    }

    public void delete(int id){
        repository.delete(id);
    }

}