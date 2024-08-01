package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;

@Data
public class Ordine extends GenericEntity{
    int idOrdine;
    double quantita;
    boolean iva;
    double totale;
    List<Prodotto> prodotti;
}
