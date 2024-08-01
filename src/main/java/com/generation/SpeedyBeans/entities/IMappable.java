package com.generation.SpeedyBeans.entities;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.LinkedHashMap;
import java.util.Map;

public interface IMappable 
{
    //metodo che prende in input una mappa, chiamerà un set per volta e confronterà il
    //nome della proprietà dell'oggetto con il nome di ogni chiave della mappa
    //se c'è corrispondenza allora aasegnarà a quella proprietà il valore assocciato alla chiave con lo stesso nome
    //passare i dati dati di una mmap alle proprietà di un oggetto in modo che siano valorizzate in automatico
    default void fromMap(Map<String,String> map){
        //prendo un set per volta
        //this.getClass().getMethods() -> questa istruzione ritorna un array di oggetti di tipo Method
        //cioè un array che contiene tutti i metodi dichiarati nella classe modello dell'oggetto(this) che sta
        //invocando il metodo fromMap()
        for(Method m : this.getClass().getMethods()){
            //controllo che il metodo preso in considerazione nell'iterazione sia un set
            if(m.getName().startsWith("set") && m.getParameterCount() == 1){
                //setNome
                //dal nome del metodo tolgo il valore set e mettendo in minuscolo il primo carattere ottengo il nome della proprietà
                //setNome -> Nome
                //s[0], e[1], t[2], N[3], o[4], m[5], e[6]
                String nomeProprieta = m.getName().substring(3).toLowerCase(); //in nomeProprietà salvo una stringa partendo dal nome
                //del metodo e togliendo i primi 3 caratteri quindi togliendo set al nome del metodo
                //ad es. con il metodo setNome avrò salvato in nomeProp = "Nome"; N[0], o[1], m[2], e[3];
                //Character.toLowerCase(nomeProprieta.charAt(0)) -> "n"
                //nomeProprieta.substring(1) -> "Nome" -> sottostringa a partire dall'indice 1 -> quindi "ome"
                //nomeProprieta = "n" + "ome"; -> "nome"
                //nomeProprieta = "DataNascita";
                //Character.toLowerCase(nomeProprieta.charAt(0))  -> mette la D in minuscolo
                //nomeProprieta = "d" + "ataNascita"; -> "dataNascita"
                //Infine converto tutto in minuscolo quindi: "dataNascita" -> "datanascita"
                nomeProprieta = Character.toLowerCase(nomeProprieta.charAt(0)) + nomeProprieta.substring(1);
                //dataNascita
                //data_nascita -> 
                //nomeProprieta.substring(0, 4) -> nomeProprieta= "data" +
                //nomeProprieta =nomeProprieta.substring(0, 4) + "_"; -> nomeProprieta= "data_" 
                //Character.toLowerCase(nomeProprieta.charAt(4).toLowerCase) -> prendo la "N" in dataNascita e la metto minuscola
                //nomeProprieta =nomeProprieta.substring(0, 4) + "_" + Character.toLowerCase(nomeProprieta.charAt(4).toLowerCase) -> "data_n"
                // +nomeProprieta.substring(5); -> "dataNascita" prendo tutti i char dall'indice 5 in poi "ascita"
                //nomeProprieta =nomeProprieta.substring(0, 4) + "_" + Character.toLowerCase(nomeProprieta.charAt(4))
                //+nomeProprieta.substring(5); -> nomeProprieta = "data_nascita"

                //confronto il nome della proprietà dell'oggetto con il nome delle chiavi della mappa
                //se esiste una chiave con lo stesso nome del nome della proprietà allora prendo il valore associato e lo passo
                //al metodo che ho preso in considerazione in questa iterazione
                //  String nome = params.containsKey("nome") ? params.get("nome") : "";
                if(map.containsKey(nomeProprieta)){
                    //se c'è una chiave con quel valore prendo il valore associato
                    String valoreAssociato = map.get(nomeProprieta); //in valore associato se uso l'esempio della proprietà nome
                    //sto prendendo il nome di un dipendente dalla mappa letta dal db e lo salvo in una variabile 
                    //questo valore dovrà poi essere passato al metodo setNome(valoreAssociato)
                    if(valoreAssociato == null){
                        continue;
                    }
                    //se c'è un valore associato devo passarlo al set che sto iterando in modo che 
                    //quel valore venga assegnato alla proprietà
                    //controlo il tipo di parametro che questo m che sto iterando prende in input
                    //m.getParameters() restituisce un array con all'interno tutti i parametri presi in input dal metodo
                    //m.getParameters()[0].getType() restituisce il tipo (come classe) del primo parametro dell'array 
                    String tipoParametro = m.getParameters()[0].getType().getSimpleName().toLowerCase(); //string,int,date
                    try {
                        switch(tipoParametro){
                            case "string":
                                //prendo la variabile valoreAssociato e la passo in input al metodo m
                                //setNome(valoreAssociato)
                                m.invoke(this,valoreAssociato); //this.setNome(valoreAssociato)
                                //se tutto va bene alla riga 69 ho preso un valore della mappa e l'ho passato ad un set quindi settato il valore
                                //di una proprietà dell'oggetto che ha invocato il fromMap
                            break;
                            case "int":
                                m.invoke(this, Integer.parseInt(valoreAssociato));
                            break;
                            case "long":
                                m.invoke(this, Long.parseLong(valoreAssociato));
                            break;
                            case "double":
                                m.invoke(this, Double.parseDouble(valoreAssociato));
                            break;
                            case "date":
                                //valoreAssociato conterrà una data ma in formato stringa, per passarla al set che prende in input oggetti di 
                                //tipo Date dobbiamo parsare la stringa
                                m.invoke(this, Date.valueOf(valoreAssociato));
                            break;
                            case "boolean":
                                m.invoke(this, (valoreAssociato.equals("1")?true:false));
                            break;
                        }
                    } catch (Exception ex) {
                       ex.printStackTrace();
                    }

                }
            }
            
        }

    }


    //metodo che trasforma i valori delle proprietà di un oggetto in una mappa
    //dove le chiavi saranno i nomi delle proprietà e i valori i valori ad esse associati
    default Map<String,String> toMap(){
        Map<String,String> result = new LinkedHashMap<>();
        for(Method m : this.getClass().getMethods()){
            if( (m.getName().startsWith("get") || m.getName().startsWith("is"))
                 &&
                !m.getName().equalsIgnoreCase("getClass") && m.getParameterCount() == 0){
                
                //prendo il nome del metodo e tolgo get o is
                //ad es. getNome, g[0], e[1], t[2], N[3], o[4], m[5], e[6]
                //oppure isBonus -> i[0], s[1], B[2], o[3], n[4], u[5], s[6]
                int partenza = m.getName().startsWith("get") ? 3 : 2;
                String nomeProprieta = m.getName().substring(partenza);
                nomeProprieta = Character.toLowerCase(nomeProprieta.charAt(0)) + nomeProprieta.substring(1);
                try {
                    //nella mappa inserisco come chiave il nome della proprietà 
                    //come valore il valore di ritorno del metodo get che sto scorrendo
                    String valore = null;
                    if(partenza == 2){
                        valore = m.invoke(this).toString().equalsIgnoreCase("true")?"1":"0";
                    }else if(partenza == 3){
                        valore = String.valueOf(m.invoke(this));
                    }
                    result.put(nomeProprieta.toLowerCase(), valore);
                } catch (Exception e) { 
                  e.printStackTrace();
                }
            }
        }
        return result;
    }
}
