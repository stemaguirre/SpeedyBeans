package com.generation.SpeedyBeans.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.generation.SpeedyBeans.entities.Entity;

import lombok.Getter;

@Getter
public abstract class GenericService {
    
    @Autowired // Inietta la dipendenza del repository
    private D repositary;

    
    @Autowired // Inietta la dipendenza UserDAO
    private UserDAO UserDAO;

    // Legge tutte le entità dal repository e le restituisce come lista
    public List<E> readAll(){
        Map<Long, Entity> result = repositary.readAll();
        List<E> ris = new ArrayList<>();

        for (Entity entity : result.values()) {
            ris.add((E) entity);
        }

        return ris;
    }

    // Legge una singola entità dal repository utilizzando l'ID
    public E readById(Long id){
        Map<Long, Entity> result = repositary.readAll();
        E e = (E) result.get(id);
        return e;
    }

    // Aggiorna un'entità nel repository
    public void update(E e){
        repositary.update(e);
    }

    // Crea una nuova entità nel repository
    public Long create(E e){
        repositary.create(e);
    }

    // Crea o aggiorna un utente nel UserDAO
    public void createOrUpdateUser(Long id, String username, String password){
        UserDAO.createOrUpdateUser(id, username, password);
    }
}