package com.generation.SpeedyBeans.entities;

import lombok.Data;

@Data
public abstract class Persone extends Entity
{
    int id_persona;
    String nome;
    String cognome;
    String nome_utente;
    String password;
}
