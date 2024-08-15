package com.generation.SpeedyBeans.configurations;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Caffe;
import com.generation.SpeedyBeans.entities.Macchinetta;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Utente;

@Configuration
public class EntitiesContext {
    
    @Bean
    @Scope("prototype")
    public Admin newAdmin(Map<String, String> params) {
        Admin a = new Admin();
        a.fromMap(params);
        return a;
    }

    @Bean
    @Scope("prototype")
    public Utente newUtente(Map<String, String> params) {
        Utente u = new Utente();
        u.fromMap(params);
        return u;
    }

    @Bean
    @Scope("prototype")
    public Caffe newCaffe(Map<String, String> params) {
        Caffe c = new Caffe();
        c.fromMap(params);
        return c;
    }

    @Bean
    @Scope("prototype")
    public Macchinetta newMacchinetta(Map<String, String> params) {
        Macchinetta m = new Macchinetta();
        m.fromMap(params);
        return m;
    }

    @Bean
    @Scope("prototype")
    public Ordine newOrdine(Map<String, String> params) {
        Ordine o = new Ordine();
        o.fromMap(params);
        return o;
    }

}
