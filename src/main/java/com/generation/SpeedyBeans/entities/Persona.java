package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public abstract class Persona extends Entity
{
    private String nome;
    private String cognome;
    private String username;
    private String password;
    private List<Ordine> ordini;
}
