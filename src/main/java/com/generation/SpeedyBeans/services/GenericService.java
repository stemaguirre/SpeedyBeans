package com.generation.SpeedyBeans.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.generation.SpeedyBeans.dao.IDAO;
import com.generation.SpeedyBeans.entities.GenericEntity;
import com.generation.SpeedyBeans.entities.Utente;
<<<<<<< HEAD
=======
import com.generation.SpeedyBeans.dao.UtenteDAO;
>>>>>>> sergio

import lombok.Getter;

@Getter
public abstract class GenericService<E extends GenericEntity, D extends IDAO<E>> {
<<<<<<< HEAD
    
    @Autowired 
    private D repository;

    
=======

    @Autowired 
    private D repository;


>>>>>>> sergio
    @Autowired
    private Utente utenteDAO;

    public int create(E e){
        return repository.create(e);
    }

    public List<E> readAll(){
        Map<Integer, GenericEntity> result = repository.readAll();
        List<E> ris = new ArrayList<>();

        for (GenericEntity e : result.values()) {
            ris.add((E)e);
        }

        return ris;
    }

<<<<<<< HEAD
    public E readById(int id){
        Map<Integer, GenericEntity> result = repository.readAll();
        E e = (E)result.get(id);
        return e;
=======
    public E readById(int id) {
        return repository.readById(id);
>>>>>>> sergio
    }

    public void update(E e){
        repository.update(e);
    }

    public void delete(int id){
        repository.delete(id);
    }

<<<<<<< HEAD
=======
    public void createOrUpdateUser(Long id, String username, String password){
        utenteDAO.updateUsernameAndPassword(id, username, password);  
    }
>>>>>>> sergio
}