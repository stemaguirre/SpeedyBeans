package com.generation.SpeedyBeans.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Utente;

@Configuration
public class EntitiesContext {
    
    @Bean
    public Admin newAdmin() {
        Admin a = new Admin();
        return a;
    }

    @Bean
    public Utente newUtente() {
        Utente u = new Utente();
        return u;
    }

    @Bean
    public Caffe newCaffe() {
        Caffe c = new Caffe();
        return c;
    }

    @Bean
    public Macchinetta newMacchinetta() {
        Macchinetta m = new Macchinetta();
        return m;
    }

    // @Bean
    // public Ordine newOrdine() {
    //     Ordine o = new Ordine();
    //     return o;
    // }

}
