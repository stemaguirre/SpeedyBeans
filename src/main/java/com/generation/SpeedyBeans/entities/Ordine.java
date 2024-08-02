package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;

@Data
public class Ordine extends Entity{

    private int idPersona;
    private double quantita;
    private boolean iva;
    private double totale;
    private Persona persona;
    private List<Prodotto> prodotti;
}
