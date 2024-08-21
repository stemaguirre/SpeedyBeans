package com.generation.SpeedyBeans;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.generation.SpeedyBeans.services.UtenteService;

@SpringBootTest
class SpeedyBeansApplicationTests {

	@Autowired
	private UtenteService utenteService;

	@Test
	void contextLoads() {
		
	}

}
