package com.generation.SpeedyBeans.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.dao.AdminDAO;
<<<<<<< HEAD


=======
>>>>>>> sergio
import com.generation.SpeedyBeans.dao.UtenteDAO;
import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.entities.Utente;
<<<<<<< HEAD
=======
import com.generation.SpeedyBeans.entities.GenericEntity;
>>>>>>> sergio

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
public class AdminService extends GenericService<Admin, AdminDAO> {

<<<<<<< HEAD
    @Autowired
    private UtenteDAO utenteDAO;

    // public void approvaRegistrazioneUtente(Long utenteId) {
    //     Utente utente = utenteDAO.readById(utenteId);
    //     if (utente != null) {
    //         utente.setStato("APPROVATO");
    //         utenteDAO.update(utente);
    //     }
    // }

    // public void rifiutaRegistrazioneUtente(Long utenteId) {
    //     Utente utente = utenteDAO.readById(utenteId);
    //     if (utente != null) {
    //         utente.setStato("RIFIUTATO");
    //         utenteDAO.update(utente);
    //     }
    // }

   
=======
    public void approvaRegistrazioneUtente(int utenteId) {
        Utente utente = utenteDAO.readById(utenteId);
        if (utente != null) {
            utente.setStato("APPROVATO");
            utenteDAO.update(utente);
        }
    }

    public void rifiutaRegistrazioneUtente(int utenteId) {
        Utente utente = utenteDAO.readById(utenteId);
        if (utente != null) {
            utente.setStato("RIFIUTATO");
            utenteDAO.update(utente);
        }
    }
>>>>>>> sergio
}