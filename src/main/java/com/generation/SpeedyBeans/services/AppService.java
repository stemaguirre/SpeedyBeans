package com.generation.SpeedyBeans.services;

import org.springframework.stereotype.Service;

import lombok.Data;

//Questo servizio lo utilizziamo solo per passarci i messagi da una richiesta all'altra, e eventualmente portarli in frontend
//il messaggio viene settato da una parte e dall'altra viene effetuto un controllo se il messaggio è presente oppure no se c'è viene preso
//viene inserito nel model e viene settato null su AppService per "svuotare" e pulire per i prossimi messaggi
@Service
@Data
public class AppService {
    private String message;
}
