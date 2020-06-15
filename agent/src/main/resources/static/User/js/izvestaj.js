var params = new URL(location.href).searchParams;
var zahtevId = params.get("zahtevId");

$(document).ready(function(){
		
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return;
	}
	
	
	if(zahtevId == undefined) {
//		window.location = "../login.html"; // kasnije otkomentarisi
	}
	
	getZahtev();
	
	
});

function getZahtev() {
	$.get({
		url: '/zahtev/' + zahtevId,
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(zahtev){
			
			var now = new Date();
			var doDate = new Date(zahtev.do1);
			if(now < doDate) {
				alert("Koriscenje vozila jos nije proslo!");
			} else if(zahtev.status != "PAID") {
				alert("Zahtev nije PAID!");
			} else {
				ucitajIzvestaje(zahtev);
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}

function ucitajIzvestaje(zahtev){
	$.get({
		url: '/izvestaj/?zahtevId=' + zahtevId,
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(izvestaji){
			
				popuniDiv(izvestaji,zahtev);
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}

function popuniDiv(izvestaji,zahtev) {
	
	var count = 1;
	var count2 = 0;

	for(oglas of zahtev.oglasi){
		
		

		
		var form = $('<form id="form-'+count+'"> </form>');
		
		var labelOglas = $('<label>Izvestaj za oglas: '+oglas.marka+' | '+oglas.model+' | '+oglas.menjac+' | '+oglas.gorivo+' | '+oglas.klasa+' </label>');
		
		var labelKilometri = $('<label>Predjeno kilometara: </label>');
		var inputKilometri = $('<input type="number" class="form-control" id="input-kilometraza-'+count+'" placeholder="Predjena Kilometraza">');

		
		var labelKomentar = $('<label>Komentar(50 karaktera): </label>');
		var inputKomentar = $('<input type="text" class="form-control" id="input-komentar-'+count+'" placeholder="Komentar">');
		
		var btnSubmit = $('<button value="Oceni">Oceni </button>');
		btnSubmit.click(podnesiIzvestaj(count, oglas.id,zahtevId));
		
		form.append(labelOglas).append('<br>').append(labelKilometri).append(inputKilometri).append(labelKomentar).append(inputKomentar).append('<br>').append(btnSubmit).append('<hr><hr><hr>');
		
		var pom = 0;
		if(izvestaji != null){
		for(izvestaj of izvestaji){
			count2++;
			if(izvestaj.oglasId == oglas.id){
				pom = 1;
			}
		}
		}

		if(pom == 0){
		$("#div-za-forme").append(form);
		}
//		var ocena = $('#select-ocena-'+count+' :selected').val();
//		alert("OCENA JE: " + ocena);
//		$('#form-'+count+'').submit(function(event) {
//			event.preventDefault(event);
////			var idOcene = select.attr('id');
////			var idKomentara = inputKomentar.attr('id');
//			oceniOglas(count, oglas.id);
//		});
		
		
		count++;
		if(count == count2){
			alert("Uneli ste sve izvestaje!");
			window.location = "./zahtevi-za-mene.html";
		}
	}
	
//	console.log(count- 1);
}

function podnesiIzvestaj(count, oglasId,zahtevId) {
//	alert("Oceni oglas sa id:"+oglasId+ ", count:"+count+ ", zahtevId:"+zahtevId);
		
	return function(event) {
		event.preventDefault();
		var predjeniKilometri = $('#input-kilometraza-'+count+'').val();
		var tekst = $('#input-komentar-'+count+'').val();
//		if(komentar == "") {
//			alert("Komentar ne sme biti prazan!");
//			location.reload();
//		}
//		alert("Ocena:"+ocena+ ", komentar:"+komentar);
		
		$.post({
			url: '/izvestaj',
			data: JSON.stringify({predjeniKilometri, tekst, oglasId, zahtevId}),
			headers: {
		        'Auth': 'Bearer ' + token
		    },
			contentType: 'application/json',
			success: function(ocena){
				alert("Uspešno ste uneli izvestaj");
				location.reload();
			},
			error: function(message) {
	            alert("Doslo je do greske... "+ message);	   
	            location.reload();
	        }
		});
		
		alert("Obradjivanje zahteva...");
		
	}
	
}
