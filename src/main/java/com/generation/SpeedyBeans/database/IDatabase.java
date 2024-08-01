package com.generation.SpeedyBeans.database;

import java.util.Map;

public interface IDatabase 
{
     Long executeUpdate(String query,String...params);
    Map<Long,Map<String,String>> executeQuery(String query,String...params);
}
