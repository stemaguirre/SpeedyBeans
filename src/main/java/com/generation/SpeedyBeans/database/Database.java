package com.generation.SpeedyBeans.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Database implements IDatabase
{

    @Value("${db.mysql.username}")
    private String username;

    @Value("${db.mysql.password}")
    private String password;

    @Value("${db.mysql.nomedb}")
    private String nomeDb;

    @Value("${db.mysql.timezone}")
    private String timeZone;

    @Value("${db.mysql.path}")
    private String path;

    private Connection connection;

    public Database(){}

    public void openConnection()
    {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(path + nomeDb +timeZone, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection()
    {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() 
    {
        return connection;
    }

    @Override
    public int executeUpdate(String query, String... params) {
        
        openConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String[] colonne = {"id"};
            ps = connection.prepareStatement(query,colonne);
            for(int i = 0; i < params.length;i++){
                ps.setString(i+1, params[i]);
            }
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }finally{
            closeConnection();
        }
        return 1;
    }

    @Override
    public Map<Integer, Map<String, String>> executeQuery(String query, String... params) {
        Logger logger = Logger.getLogger(Database.class.getName());
        openConnection();
        Map<Integer, Map<String,String>> righe = new HashMap<>();

        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            
            ps = connection.prepareStatement(query);
            for(int i = 0;i < params.length;i++){
                
                ps.setString(i+1, params[i]);
            }

            logger.info("Executing query: " + ps.toString());

            
            rs = ps.executeQuery();
            Map<String,String> mappaProprietà;
            while(rs.next()){
                
                mappaProprietà = new HashMap<>();
                for(int i = 1; i <= rs.getMetaData().getColumnCount();i++){
                    // mappaProprietà.put(rs.getMetaData().getColumnName(i).replace("_", "").toLowerCase(), 
                    //                     rs.getString(i));
                    mappaProprietà.put(rs.getMetaData().getColumnLabel(i).replace("_", "").toLowerCase(), 
                                        rs.getString(i));

                }
                righe.put(rs.getInt(1), mappaProprietà);
                logger.info("Row: " + rs.getInt(1) + " " + mappaProprietà);
                logger.info("Nome colonna: " + rs.getMetaData().getColumnName(1));
            }

            ps.close();
            rs.close();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            closeConnection();
        }
       return righe;
    }




}
