package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;

@Data
public abstract class Persona extends GenericEntity
{
    int idPersona;
    String nome;
    String cognome;
    String nomeUtente;
    String password;
    List<Ordine> ordini;
}
