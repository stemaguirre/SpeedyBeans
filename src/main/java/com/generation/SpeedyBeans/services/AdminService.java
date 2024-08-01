package com.generation.scuola.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.dao.AdminDAO;
import com.generation.SpeedyBeans.dao.OrdineDAO;
import com.generation.SpeedyBeans.dao.ProdottoDAO;
import com.generation.SpeedyBeans.dao.UtenteDAO;
import com.generation.SpeedyBeans.entities.Admin;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.entities.Utente;
import com.generation.SpeedyBeans.entities.Entity;

import java.util.List;
import java.util.Map;
import java.util.ArrayList;

@Service
public class AdminService extends GenericService<Admin, AdminDAO> {

    @Autowired
    private UtenteDAO utenteDAO;

    @Autowired
    private OrdineDAO ordineDAO;

    @Autowired
    private ProdottoDAO prodottoDAO;

    public List<Utente> getAllUtenti() {
        Map<Long, Entity> utenti = utenteDAO.readAll();
        List<Utente> listaUtenti = new ArrayList<>();
        for(Entity e : utenti.values()){
            listaUtenti.add((Utente)e);
        }
        return listaUtenti;
    }

    public void approvaRegistrazioneUtente(Long utenteId) {
        Utente utente = utenteDAO.readById(utenteId);
        if (utente != null) {
            utente.setStato("APPROVATO");
            utenteDAO.update(utente);
        }
    }

    public void rifiutaRegistrazioneUtente(Long utenteId) {
        Utente utente = utenteDAO.readById(utenteId);
        if (utente != null) {
            utente.setStato("RIFIUTATO");
            utenteDAO.update(utente);
        }
    }

    public List<Ordine> getAllOrdini() {
        Map<Long, Entity> ordini = ordineDAO.readAll();
        List<Ordine> listaOrdini = new ArrayList<>();
        for(Entity e : ordini.values()){
            listaOrdini.add((Ordine)e);
        }
        return listaOrdini;
    }

    public void cancellaOrdine(Long ordineId) {
        ordineDAO.delete(ordineId);
    }

    public List<Prodotto> getAllProdotti() {
        Map<Long, Entity> prodotti = prodottoDAO.readAll();
        List<Prodotto> listaProdotti = new ArrayList<>();
        for(Entity e : prodotti.values()){
            listaProdotti.add((Prodotto)e);
        }
        return listaProdotti;
    }

    public void modificaProdotto(Prodotto prodotto) {
        prodottoDAO.update(prodotto);
    }

    public void cancellaProdotto(Long prodottoId) {
        prodottoDAO.delete(prodottoId);
    }

    public void inserisciProdotto(Prodotto prodotto) {
        prodottoDAO.create(prodotto);
    }
}