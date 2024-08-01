package com.generation.SpeedyBeans.entities;

import lombok.Data;

@Data
public abstract class Persona 
{
    int id_persona;
    String nome;
    String cognome;
    String nomeUtente;
    String password;
}
