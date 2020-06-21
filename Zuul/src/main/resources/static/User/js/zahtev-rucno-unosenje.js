var params = new URL(location.href).searchParams;
var oglasId = params.get("oglasId");

$(document).ready(function(){
		
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return;
	}
	
	
//	if(zahtevId == undefined) {
////		window.location = "../login.html"; // kasnije otkomentarisi
//	}
	
	getOglas();
	
	
});

function getOglas() {
	$.get({
		url: '/oglasi/oglas/' + oglasId,
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(oglas){
			popuniDiv(oglas);
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}

function popuniDiv(oglas) {
	
		var form = $('<form id="formUnos"> </form>');
		
		var labelOglas = $('<label>Zauzece za oglas: '+oglas.id+ ' | ' +oglas.marka+' | '+oglas.model+' | '+oglas.menjac+' | '+oglas.gorivo+' | '+oglas.klasa+' </label>');
		
		var pocetakRentiranja = $('<label>Pocetak rentiranja: </label>');
		var inputPocetka = $('<input type="date" name="od" id="input-od" required>')
		
		var krajRentiranja = $('<label>Kraj rentiranja: </label>');
		var inputKraja = $('<input type="date" name="do" id="input-do" required>')
		
		
//		var labelKilometri = $('<label>Predjeno kilometara: </label>');
//		var inputKilometri = $('<input type="number" class="form-control" id="input-kilometraza-'+count+'" placeholder="Predjena Kilometraza">');
//
//		
//		var labelKomentar = $('<label>Komentar(50 karaktera): </label>');
//		var inputKomentar = $('<input type="text" class="form-control" id="input-komentar-'+count+'" placeholder="Komentar">');
		
		
		var btnSubmit = $('<button value="Unesi">Unesi zauzece </button>');
		btnSubmit.click(unesiZauzece(oglas.id));
		
		form.append(labelOglas).append('<br>').append(pocetakRentiranja).append(inputPocetka).append(krajRentiranja).append(inputKraja).append('<br>').append(btnSubmit).append('<hr><hr><hr>');
		

		$("#div-za-forme").append(form);


}



function unesiZauzece(oglasId) {
//	alert("Oceni oglas sa id:"+oglasId+ ", count:"+count+ ", zahtevId:"+zahtevId);
		
	return function(event) {
		event.preventDefault();
		var od = $('#input-od').val();
		alert(od);
		var do1 = $('#input-do').val();
		var oglasi = [];
		var oglas = {oglasId:oglasId,od:od,do:do1};
		oglasi.push(oglas);
		var bundle = false;

		
		$.post({
			url: '/oglasi/zahtev',
			data: JSON.stringify({bundle, oglasi}),
			headers: {
		        'Auth': 'Bearer ' + token
		    },
			contentType: 'application/json',
			success: function(ocena){
				alert("Uspešno ste uneli zauzece");
				window.location = "./oglasi-moji.html";
			},
			error: function(message) {
	            alert("Doslo je do greske... "+ message);	   
	            window.location = "./oglasi-moji.html";
	        }
		});
		
		alert("Obradjivanje zahteva...");
		
	}
	
}









