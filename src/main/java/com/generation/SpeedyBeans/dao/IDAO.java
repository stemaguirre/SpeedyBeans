package com.generation.SpeedyBeans.dao;

import java.util.Map;

import com.generation.SpeedyBeans.entities.GenericEntity;

public interface IDAO <E extends GenericEntity>
{
    public int create(E e);

    public Map<Long, GenericEntity> readAll();

    public void update(E e);

    public void delete(Long id);
}
