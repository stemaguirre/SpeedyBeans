package com.generation.SpeedyBeans.dao;

import java.util.Map;

import com.generation.SpeedyBeans.entities.GenericEntity;

public interface IDAO<E extends GenericEntity> {
    int create(E e);
    Map<Integer, GenericEntity> readAll();
    E readById(int id);
    void update(E e);
    void delete(int id);

}