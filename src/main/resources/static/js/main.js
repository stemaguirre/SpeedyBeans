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

function openUpdateOrdine(id, totale, quantita, iva){
    document.getElementById('update-ordine').hidden = false;
    document.getElementById('update-ordine-id').value = id;
    document.getElementById('update-ordine-totale').value = totale;
    document.getElementById('update-ordine-quantita').value = quantita;
    document.getElementById('update-ordine-iva').value = iva ? 'Sì' : 'No';
}