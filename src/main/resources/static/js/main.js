function openLoginModal() {
    document.querySelector("#login-modal").hidden = false;
}

function toggleInsert(){
    var insert = document.querySelector("#insert");
    insert.hidden = !insert.hidden;
}

function openInsertCaffe() {
    document.querySelector("#insert-caffe").hidden = false;
    document.querySelector("#insert-macchinetta").hidden = true;

}

function openInsertMacchinetta() {
    document.querySelector("#insert-macchinetta").hidden = false;
    document.querySelector("#insert-caffe").hidden = true;
}

function openUpdateProdotto(id, genere, brand, prezzo, disponibilita, peso,
    tipologia, dataProduzione, dataScadenza, formato,
    utilizzo, colore, modello, serbatoio) {
    console.log(genere);
    if (genere === '0') {
        document.getElementById('update-caffe').hidden = false;
        document.getElementById('update-macchinetta').hidden = true;
        document.getElementById('update-caffe-id').value = id;
        document.getElementById('update-caffe-genere').value = 'C';
        document.getElementById('update-caffe-brand').value = brand;
        document.getElementById('update-caffe-prezzo').value = prezzo;
        document.getElementById('update-caffe-disponibilita').value = disponibilita;
        document.getElementById('update-caffe-peso').value = peso;
        document.getElementById('update-caffe-tipologia').value = tipologia;
        document.getElementById('update-caffe-dataproduzione').value = dataProduzione;
        document.getElementById('update-caffe-datascadenza').value = dataScadenza;
        document.getElementById('update-caffe-formato').value = formato;
    } else if (genere === '1') {
        document.getElementById('update-macchinetta').hidden = false;
        document.getElementById('update-caffe').hidden = true;
        document.getElementById('update-macchinetta-id').value = id;
        document.getElementById('update-macchinetta-genere').value = 'M';
        document.getElementById('update-macchinetta-brand').value = brand;
        document.getElementById('update-macchinetta-prezzo').value = prezzo;
        document.getElementById('update-macchinetta-disponibilita').value = disponibilita;
        document.getElementById('update-macchinetta-peso').value = peso;
        document.getElementById('update-macchinetta-utilizzo').value = utilizzo;
        document.getElementById('update-macchinetta-colore').value = colore;
        document.getElementById('update-macchinetta-modello').value = modello;
        document.getElementById('update-macchinetta-serbatoio').value = serbatoio;
    }
    
}

function openUpdateOrdine(id, idPersona, totale, quantita, iva){
    document.getElementById('update-ordine').hidden = false;
    document.getElementById('update-ordine-id').value = id;
    document.getElementById('update-ordine-idpersona').value = idPersona;
    document.getElementById('update-ordine-totale').value = totale;
    document.getElementById('update-ordine-quantita').value = quantita;
    document.getElementById('update-ordine-iva').value = iva ? 'Sì' : 'No';
}

function openUpdateUtente(id, nome, cognome, ragioneSociale, partitaIva, codiceSdi, indirizzo, citta, cap, provincia, nazione, telefono, email){
    document.getElementById('update-utente').hidden = false;
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