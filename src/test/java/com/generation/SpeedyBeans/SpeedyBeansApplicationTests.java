package com.generation.SpeedyBeans;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.services.OrdineService;


@SpringBootTest
class SpeedyBeansApplicationTests {

	
    @Autowired
    private OrdineService ordineService;

    @Test
    public void testFindByIdPersona() {
        int idPersona = 1;
    List<Ordine> ordini = ordineService.findByIdPersona(idPersona);
    if (ordini == null || ordini.isEmpty()) {
        System.out.println("Nessun ordine trovato per idPersona: " + idPersona);
    } else {
        ordini.forEach(ordine -> System.out.println(ordine));
    }
    }

}
