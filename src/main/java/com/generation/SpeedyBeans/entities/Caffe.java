package com.generation.SpeedyBeans.entities;

import java.sql.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Caffe extends Prodotti
{
    String tipologia;
    Date dataProduzione;
    Date dataScadenza;
    String formato;
}
