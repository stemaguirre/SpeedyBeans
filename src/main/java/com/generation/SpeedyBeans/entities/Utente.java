package com.generation.SpeedyBeans.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Utente extends Persona 
{
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
}
