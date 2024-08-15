package com.generation.SpeedyBeans.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Macchinetta extends Prodotto
{
    private String utilizzo;
    private String colore;
    private String modello;
    private double serbatoio;
}
