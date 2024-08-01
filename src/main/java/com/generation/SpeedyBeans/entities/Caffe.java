package com.generation.SpeedyBeans.entities;

import java.sql.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Caffe extends Prodotti
{
    String tipologia;
    Date data_produzione;
    Date data_scadenza;
    String formato;
}
