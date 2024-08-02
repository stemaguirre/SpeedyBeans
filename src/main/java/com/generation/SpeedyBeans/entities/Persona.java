package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;

@Data
public abstract class Persona extends Entity
{
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private List<Ordine> ordini;
}
