package com.generation.SpeedyBeans.database;

import java.util.Map;

public interface IDatabase 
{
    int executeUpdate(String query,String...params);
    Map<Integer,Map<String,String>> executeQuery(String query,String...params);
}
