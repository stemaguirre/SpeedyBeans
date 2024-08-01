package com.generation.SpeedyBeans.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Utente extends Persone 
{
    String ragione_sociale;
    String partita_iva;
    String codice_sdi;
    String indirizzo;
    int cap;
    String citta;
    String provincia;
    String nazione;
    String telefono;
    String email;
}
