package com.generation.SpeedyBeans.services;

import java.util.logging.Logger;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.generation.SpeedyBeans.dao.OrdineDAO;
import com.generation.SpeedyBeans.dao.ProdottoDAO;
import com.generation.SpeedyBeans.entities.Entity;
import com.generation.SpeedyBeans.entities.Ordine;
import com.generation.SpeedyBeans.entities.Prodotto;

@Service
public class OrdineService extends GenericService<Ordine, OrdineDAO> {

    @Autowired
    private ProdottoDAO prodottoDAO;

    @Override
    public List<Ordine> readAll() {

        Map<Integer, Entity> ordini = getRepository().readAll();

        List<Ordine> listaOrdini = new ArrayList<>();

        for(Entity e : ordini.values()){
            Ordine o = (Ordine)e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for(Entity p : prodotti.values()){
                listaProdotti.add((Prodotto)p);
            }
            o.setProdotti(listaProdotti);

            listaOrdini.add(o);
        }

        return listaOrdini;
    }

    @Override
    public Ordine readById(int id) {
        
        Map<Integer, Entity> ordini = getRepository().readAll();
        Ordine o = (Ordine)ordini.get(id);

        if(o != null){
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for(Entity p : prodotti.values()){
                listaProdotti.add((Prodotto)p);
            }
            o.setProdotti(listaProdotti);
        }

        return o;

    }

    public List<Ordine> findByIdProdotto(int idProdotto) {
        Map<Integer, Entity> ordini = getRepository().readByIdProdotto(idProdotto);

        List<Ordine> listaOrdini = new ArrayList<>();

        for(Entity e : ordini.values()){
            Ordine o = (Ordine)e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for(Entity p : prodotti.values()){
                listaProdotti.add((Prodotto)p);
            }
            o.setProdotti(listaProdotti);

            listaOrdini.add(o);
        }

        return listaOrdini;
    }


    public List<Ordine> findByRangeTotale(int min, int max) {
        Map<Integer, Entity> ordini = getRepository().readByRangeTotale(min, max);
        List<Ordine> listaOrdini = new ArrayList<>();
        for (Entity e : ordini.values()) {
            Ordine o = (Ordine) e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for (Entity p : prodotti.values()) {
                listaProdotti.add((Prodotto) p);
            }
            o.setProdotti(listaProdotti);
            listaOrdini.add(o);
        }
        return listaOrdini;
    }

    public List<Ordine> findByTotaleMinimo(int min) {
        Map<Integer, Entity> ordini = getRepository().readByTotaleMinimo(min);
        List<Ordine> listaOrdini = new ArrayList<>();
        for (Entity e : ordini.values()) {
            Ordine o = (Ordine) e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for (Entity p : prodotti.values()) {
                listaProdotti.add((Prodotto) p);
            }
            o.setProdotti(listaProdotti);
            listaOrdini.add(o);
        }
        return listaOrdini;
    }

    public List<Ordine> findByTotaleMassimo(int max) {
        Map<Integer, Entity> ordini = getRepository().readByTotaleMassimo(max);
        List<Ordine> listaOrdini = new ArrayList<>();
        for (Entity e : ordini.values()) {
            Ordine o = (Ordine) e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for (Entity p : prodotti.values()) {
                listaProdotti.add((Prodotto) p);
            }
            o.setProdotti(listaProdotti);
            listaOrdini.add(o);
        }
        return listaOrdini;
    }

    public List<Ordine> findByNomeCognomePersona(String nome, String cognome) {
        Map<Integer, Entity> ordini = getRepository().readByNomeCognomePersona(nome, cognome);
        List<Ordine> listaOrdini = new ArrayList<>();
        for (Entity e : ordini.values()) {
            Ordine o = (Ordine) e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for (Entity p : prodotti.values()) {
                listaProdotti.add((Prodotto) p);
            }
            o.setProdotti(listaProdotti);
            listaOrdini.add(o);
        }
        return listaOrdini;
    }

    public List<Ordine> findByIdPersona(int idPersona) {

        Map<Integer, Entity> ordini = getRepository().readByIdPersona(idPersona);
        List<Ordine> listaOrdini = new ArrayList<>();
        for (Entity e : ordini.values()) {
            Ordine o = (Ordine) e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for (Entity p : prodotti.values()) {
                listaProdotti.add((Prodotto) p);
            }
            o.setProdotti(listaProdotti);
            listaOrdini.add(o);
        }
        return listaOrdini;
    }

    public List<Ordine> findByDateRange(Date startDate, Date endDate) {
        Map<Integer, Entity> ordini = getRepository().findByDateRange(startDate, endDate);
        List<Ordine> listaOrdini = new ArrayList<>();
        
        for (Entity e : ordini.values()) {
            Ordine o = (Ordine) e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for (Entity p : prodotti.values()) {
                listaProdotti.add((Prodotto) p);
            }
            o.setProdotti(listaProdotti);
            listaOrdini.add(o);
        }
        
        return listaOrdini;
    }

    public List<Ordine> findByDateInizio(Date startDate) {
        Map<Integer, Entity> ordini = getRepository().findByDateInizio(startDate);
        List<Ordine> listaOrdini = new ArrayList<>();
        
        for (Entity e : ordini.values()) {
            Ordine o = (Ordine) e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for (Entity p : prodotti.values()) {
                listaProdotti.add((Prodotto) p);
            }
            o.setProdotti(listaProdotti);
            listaOrdini.add(o);
        }
        
        return listaOrdini;
    }

    public List<Ordine> findByDateFine(Date endDate) {
        Map<Integer, Entity> ordini = getRepository().findByDateFine(endDate);
        List<Ordine> listaOrdini = new ArrayList<>();
        
        for (Entity e : ordini.values()) {
            Ordine o = (Ordine) e;
            Map<Integer, Entity> prodotti = prodottoDAO.readByIdOrdine(o.getId());
            List<Prodotto> listaProdotti = new ArrayList<>();
            for (Entity p : prodotti.values()) {
                listaProdotti.add((Prodotto) p);
            }
            o.setProdotti(listaProdotti);
            listaOrdini.add(o);
        }
        
        return listaOrdini;
    }
}