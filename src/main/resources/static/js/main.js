function openLoginModal() {
    document.querySelector("#login-modal").hidden = false;
}

function toggleInsertCaffe(){
    var insert = document.querySelector("#insert-caffe");
    insert.hidden = !insert.hidden;
}

function toggleInsertMacchinetta(){
    var insert = document.querySelector("#insert-macchinetta");
    insert.hidden = !insert.hidden;
}

function toggleUpdateCaffe(){
    var update = document.querySelector("#update-caffe");
    update.hidden = !update.hidden;
}

function toggleUpdateMacchinetta(){
    var update = document.querySelector("#update-macchinetta");
    update.hidden = !update.hidden;
}

function openInsertCaffe() {
    document.querySelector("#insert-caffe").hidden = false;
    document.querySelector("#insert-macchinetta").hidden = true;

}

function openInsertMacchinetta() {
    document.querySelector("#insert-macchinetta").hidden = false;
    document.querySelector("#insert-caffe").hidden = true;
}

function toggleInsertUtente(){
    var insert = document.querySelector("#insert-utente");
    insert.hidden = !insert.hidden;
}

function toggleUpdateCaffe(id, genere, brand, prezzo, disponibilita, peso,
    tipologia, dataProduzione, dataScadenza, formato)
{
    document.getElementById('update-caffe').hidden = false;
    document.getElementById('update-macchinetta').hidden = true;
    document.getElementById('update-caffe-id').value = id;
    document.getElementById('update-caffe-genere').value = genere;
    document.getElementById('update-caffe-brand').value = brand;
    document.getElementById('update-caffe-prezzo').value = prezzo;
    document.getElementById('update-caffe-disponibilita').value = disponibilita;
    document.getElementById('update-caffe-peso').value = peso;
    document.getElementById('update-caffe-tipologia').value = tipologia;
    document.getElementById('update-caffe-dataproduzione').value = dataProduzione;
    document.getElementById('update-caffe-datascadenza').value = dataScadenza;
    document.getElementById('update-caffe-formato').value = formato;
}

function toggleUpdateMacchinetta(id, genere, brand, prezzo, disponibilita, peso,
    utilizzo, colore, modello, serbatoio) {
    
        document.getElementById('update-macchinetta').hidden = false;
        document.getElementById('update-caffe').hidden = true;
        document.getElementById('update-macchinetta-id').value = id;
        document.getElementById('update-macchinetta-genere').value = genere;
        document.getElementById('update-macchinetta-brand').value = brand;
        document.getElementById('update-macchinetta-prezzo').value = prezzo;
        document.getElementById('update-macchinetta-disponibilita').value = disponibilita;
        document.getElementById('update-macchinetta-peso').value = peso;
        document.getElementById('update-macchinetta-utilizzo').value = utilizzo;
        document.getElementById('update-macchinetta-colore').value = colore;
        document.getElementById('update-macchinetta-modello').value = modello;
        document.getElementById('update-macchinetta-serbatoio').value = serbatoio;
    
}

function toggleUpdateUtente(id, nome, cognome, ragioneSociale, partitaIva, codiceSdi, indirizzo, citta, cap, provincia, nazione, telefono, email){
    var update = document.getElementById('update-utente');
    update.hidden = !update.hidden;

    document.getElementById('update-utente-id').value = id;
    document.getElementById('update-utente-nome').value = nome;
    document.getElementById('update-utente-cognome').value = cognome;
    document.getElementById('update-utente-ragionesociale').value = ragioneSociale;
    document.getElementById('update-utente-partitaiva').value = partitaIva;
    document.getElementById('update-utente-codicesdi').value = codiceSdi;
    document.getElementById('update-utente-indirizzo').value = indirizzo;
    document.getElementById('update-utente-citta').value = citta;
    document.getElementById('update-utente-cap').value = cap;
    document.getElementById('update-utente-provincia').value = provincia;
    document.getElementById('update-utente-nazione').value = nazione;
    document.getElementById('update-utente-telefono').value = telefono;
    document.getElementById('update-utente-email').value = email;
}

function toggleInsertOrdine(){
    var insert = document.querySelector("#insert-ordine");
    insert.hidden = !insert.hidden;
} 

function toggleUpdateOrdine(id, idPersona, totale, quantita, iva, dataOrdine){
    var update = document.querySelector("#update-ordine");
    update.hidden = !update.hidden;
    document.getElementById('update-ordine').hidden = false;
    document.getElementById('update-ordine-id').value = id;
    document.getElementById('update-ordine-idpersona').value = idPersona;
    document.getElementById('update-ordine-totale').value = totale;
    document.getElementById('update-ordine-quantita').value = quantita;
    document.getElementById('update-ordine-iva').value = iva ? 'Sì' : 'No';
    document.getElementById('update-ordine-dataOrdine').value = dataOrdine;
} 

// Questa porzione di codice implementa i controlli nel form di registrazione
document.getElementById('signup-form').addEventListener('submit', function(event) {
    let partitaIVA = document.getElementById('partita-iva').value;
    let codiceSDI = document.getElementById('codice-sdi').value;
    let numeroTelefono = document.getElementById('numero-telefono').value;
    let password = document.getElementById('password').value;


    // Validazione Partita IVA
    let partitaIVAPattern = /^\d{10}$/;
    if (!partitaIVAPattern.test(partitaIVA)) {
        alert("La Partita IVA deve essere un numero di 10 cifre.");
        event.preventDefault();
        return;
    }

    // Validazione Codice SDI
    let codiceSDIPattern = /^[A-Za-z0-9]{10}$/;
    if (!codiceSDIPattern.test(codiceSDI)) {
        alert("Il Codice SDI deve essere lungo esattamente 10 caratteri e contenere solo lettere e numeri.");
        event.preventDefault();
        return;
    }

    // Validazione Numero di Telefono
    let numeroTelefonoPattern = /^\d{10}$/;
    if (!numeroTelefonoPattern.test(numeroTelefono)) {
        alert("Il Numero di Telefono deve essere lungo esattamente 10 cifre.");
        event.preventDefault();
        return;
    }

    // Validazione Password
    if (password.length < 6) {
        alert("La password deve contenere almeno 6 caratteri.");
        event.preventDefault();
        return;
    }



    // Se tutte le condizioni sono soddisfatte, mostra il messaggio di successo e reindirizza
    document.getElementById('message-modal').style.display = 'block';
    setTimeout(function() {
        window.location.href = '/'; // Cambia l'URL se la homepage si trova in un'altra posizione
    }, 2000); // 2000 millisecondi = 2 secondi
});

function apriRicerca(){
    
    var filtri = document.querySelector("#filtri-caffes");
    filtri.hidden = !filtri.hidden;
    var filtri = document.querySelector("#filtri-macchinette");
    filtri.hidden = !filtri.hidden;
    
    var applica = document.querySelector("#applica-filtri");
    applica.hidden = !applica.hidden;
    
    
}

function apriRicercaMacchinette(){
    
    var ricerca = document.querySelector("#tabella-macchinette");
    ricerca.hidden = !ricerca.hidden;
    var filtri = document.querySelector("#filtri-macchinette");
    filtri.hidden = !filtri.hidden;
    
    var applica = document.querySelector("#applica-filtri");
    applica.hidden = !applica.hidden;
    var insert = document.querySelector("#insert");
    insert.hidden = !insert.hidden;
}

function mostraRisultati(){
    var tabellaCaffes = document.querySelector("#tabella-caffes");
    var tabellaMacchinette = document.querySelector("#tabella-macchinette");
    
    tabellaCaffes.hidden = false;
    tabellaMacchinette.hidden  = false;
}

$(document).ready(function() {
    if($('#tabella-caffes > tbody').innerHTML == ""){
        $('#tabella-caffes > tbody').hide();
    }
    
    $('#tabella-macchinette > tbody > tr').has('td:empty').hide();
  });