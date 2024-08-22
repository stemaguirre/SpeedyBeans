package com.generation.SpeedyBeans.dao;

import java.util.Map;

import com.generation.SpeedyBeans.entities.Entity;

public interface IDAO <E extends Entity>
{
    public int create(E e);

    public Map<Integer, Entity> readAll();

    public E readById(int id);

    public void update(E e);

    public void delete(int id);
}