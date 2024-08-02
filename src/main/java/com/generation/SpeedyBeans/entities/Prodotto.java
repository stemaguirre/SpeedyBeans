package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;
<<<<<<< HEAD

@Data
public abstract class Prodotto extends GenericEntity
{
    String idEAN;
    String genere;
    String brand;
    double prezzo;
    int disponibilita;
    double peso;
    List<Ordine> ordini;
=======
@Data
public abstract class Prodotto extends GenericEntity
{
    private String idEAN;
    private String genere;
    private String brand;
    private double prezzo;
    private int disponibilita;
    private double peso;
    private List<Ordine> ordini;
>>>>>>> sergio
}
