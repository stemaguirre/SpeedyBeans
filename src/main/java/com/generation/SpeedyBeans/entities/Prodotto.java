package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;
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
}
