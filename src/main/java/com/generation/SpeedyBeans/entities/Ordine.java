package com.generation.SpeedyBeans.entities;

import java.sql.Date;
import java.util.List;

import lombok.Data;
import lombok.ToString;


@Data
@ToString(callSuper = true)
public class Ordine extends Entity{

    private int idPersona;
    private double quantita;
    private boolean iva;
    private double totale;
    private Persona persona;
    private List<Prodotto> prodotti;
    private Date dataOrdine;
}
