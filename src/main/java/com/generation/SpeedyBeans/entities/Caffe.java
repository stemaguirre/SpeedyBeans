package com.generation.SpeedyBeans.entities;

import java.sql.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class Caffe extends Prodotto
{
    private String tipologia;
    private Date dataProduzione;
    private Date dataScadenza;
    private String formato;
}
