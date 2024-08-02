package com.generation.SpeedyBeans.entities;

import java.util.List;

import lombok.Data;

@Data
public class Ordine extends GenericEntity{
    int idOrdine;
    double quantita;
    boolean iva;
    double totale;
<<<<<<< HEAD
    List<Prodotto> prodotti;
=======
    Persona persona;
    List<Macchinetta> macchine;  // Modificato per riflettere una lista di Macchinetta
    List<Caffe> caffe;
>>>>>>> sergio
}
