package com.generation.SpeedyBeans.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public abstract class Prodotto extends Entity
{
    private String genere;
    private String brand;
    private double prezzo;
    private int disponibilita;
    private double peso;
}
