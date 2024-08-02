package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
<<<<<<< HEAD
public abstract class Persona extends GenericEntity
{
    int idPersona;
    String nome;
    String cognome;
    String username;
    String password;
    List<Ordine> ordini;
=======
@ToString(callSuper = true)
public abstract class Persona extends GenericEntity {
    private int idPersona;
    private String nome;
    private String cognome;
    private String nomeUtente;
    private String password;
    private List<Ordine> ordini;

    
>>>>>>> sergio
}