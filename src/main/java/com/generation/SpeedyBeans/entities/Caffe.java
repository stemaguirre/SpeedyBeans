package com.generation.SpeedyBeans.entities;

import java.sql.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Caffe extends Prodotto

{
    String tipologia;
    Date dataProduzione;
    Date dataScadenza;
    String formato;
}
