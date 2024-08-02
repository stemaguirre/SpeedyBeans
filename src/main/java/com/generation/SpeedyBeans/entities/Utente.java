package com.generation.SpeedyBeans.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Utente extends Persona 
{
<<<<<<< HEAD
    String ragioneSociale;
    String partitaIva;
    String codiceSdi;
    String indirizzo;
    int cap;
    String citta;
    String provincia;
    String nazione;
    String telefono;
    String email;
    String stato;
=======
    private String ragioneSociale;
    private String partitaIva;
    private String codiceSdi;
    private String indirizzo;
    private int cap;
    private String citta;
    private String provincia;
    private String nazione;
    private int telefono;
    private String email;
>>>>>>> stefano
}
