package com.generation.SpeedyBeans.services;

import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.dao.OrdineDAO;
import com.generation.SpeedyBeans.dao.ProdottoDAO;
import com.generation.SpeedyBeans.dao.UtenteDAO;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Prodotto;
import com.generation.SpeedyBeans.entities.Utente;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service // Segna questa classe come un componente di servizio di Spring
public class UtenteService extends GenericService<Utente, UtenteDAO> {

    @Autowired // Inietta la dipendenza OrdineDAO
    private OrdineDAO ordineDAO;

    @Autowired // Inietta la dipendenza ProdottoDAO
    private ProdottoDAO prodottoDAO;

    // Recupera una lista di entità Ordine associate a un ID Utente specifico
    public List<Ordine> getOrdiniByUtenteId(Long utenteId) {
        Map<Long, Entity> ordini = ordineDAO.readAll();
        List<Ordine> listaOrdini = new ArrayList<>();
        for(Entity e : ordini.values()){
            Ordine o = (Ordine)e;
            if(o.getUtenteId().equals(utenteId)) {
                listaOrdini.add(o);
            }
        }
        return listaOrdini;
    }

    // Recupera una lista di tutte le entità Prodotto
    public List<Prodotto> getAllProdotti() {
        Map<Long, Entity> prodotti = prodottoDAO.readAll();
        List<Prodotto> listaProdotti = new ArrayList<>();
        for(Entity e : prodotti.values()){
            listaProdotti.add((Prodotto)e);
        }
        return listaProdotti;
    }

    // Recupera i dati dell'entità Utente per un ID Utente specifico
    public Utente getDatiUtente(Long utenteId) {
        return readById(utenteId);
    }
}