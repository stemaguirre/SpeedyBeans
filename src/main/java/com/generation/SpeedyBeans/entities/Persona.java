package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public abstract class Persona extends GenericEntity {
    private int idPersona;
    private String nome;
    private String cognome;
    private String nomeUtente;
    private String password;
    private List<Ordine> ordini;

    
}