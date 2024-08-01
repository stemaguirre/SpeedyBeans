package com.generation.SpeedyBeans.entities;

import lombok.Data;

@Data
public abstract class Persona
{
    int idPersona;
    String nome;
    String cognome;
    String nomeUtente;
    String password;
}
