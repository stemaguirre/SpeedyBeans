package com.generation.SpeedyBeans.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Macchinetta extends Prodotto
{
    String utilizzo;
    String colore;
    String modello;
    double serbatoio;
}
