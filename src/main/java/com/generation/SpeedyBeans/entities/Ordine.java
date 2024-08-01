package com.generation.SpeedyBeans.entities;

import lombok.Data;

@Data
public class Ordine {
    int idOrdine;
    double quantita;
    boolean iva;
    double totale;
}
