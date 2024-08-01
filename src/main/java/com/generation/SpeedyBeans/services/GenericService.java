package com.generation.SpeedyBeans.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;

@Getter
public abstract class GenericService {
    
    @Autowired
    private D repositary;

    @Autowired
    private UserDAO UserDAO;

    public List<E> readAll(){
        Map<Long, Entity> result = repositary.readAll();
        List<E> ris = new ArrayList<>();

        for (Entity entity : result.values()) {
            ris.add((E) entity);
        }

        return ris;
    }

    public E readById(Long id){
        Map<Long, Entity> result = repositary.readAll();
        E e = (E) result.get(id);
        return e;
    }

    public void update(E e){
        repositary.update(e);
    }

    public Long create(E e){
        repositary.create(e);

    public void createOrUpdateUser(Long id, String username, String password){
        UserDAO.createOrUpdateUser(id, username, password);
    }
}
