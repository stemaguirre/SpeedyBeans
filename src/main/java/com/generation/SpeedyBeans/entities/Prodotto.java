package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;

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
}
