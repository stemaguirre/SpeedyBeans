package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;

@Data
public class Ordine extends Entity{
    int idOrdine;
    double quantita;
    boolean iva;
    double totale;
    Persona persona;
    List<Macchinetta> macchine;  // Modificato per riflettere una lista di Macchinetta
    List<Caffe> caffe;
}
