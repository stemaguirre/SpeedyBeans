package com.generation.SpeedyBeans.entities;

import lombok.Data;

@Data
public abstract class Persona extends Entity
{
    int idPersona;
    String nome;
    String cognome;
    String nomeUtente;
    String password;
}
