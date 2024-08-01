package com.generation.SpeedyBeans.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public abstract class Prodotto 
{
    String id_EAN;
    String genere;
    String brand;
    double prezzo;
    int disponibilita;
    double peso;
}
