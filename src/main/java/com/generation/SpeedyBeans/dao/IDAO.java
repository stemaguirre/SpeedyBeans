package com.generation.SpeedyBeans.dao;

import java.util.Map;

import com.generation.SpeedyBeans.entities.Entity;

public interface IDAO<E extends Entity> {
    int create(E e);
    Map<Integer, Entity> readAll();
    void update(E e);
    void delete(int id);

}