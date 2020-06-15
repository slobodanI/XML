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
				popuniDiv(zahtev);
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}

function popuniDiv(zahtev) {
	var count = 1;
	
	for(oglas of zahtev.oglasi){
		var form = $('<form id="form-'+count+'"> </form>');
		
		var labelOglas = $('<label>Ocenite oglas: '+oglas.marka+' | '+oglas.model+' | '+oglas.menjac+' | '+oglas.gorivo+' | '+oglas.klasa+' </label>');
		
		var labelOcena = $('<label>Ocena: </label>');
		
		var select = $('<select class="form-control col-sm-6" id="select-ocena-'+count+'" required>');
		var option1 = $('<option value=1>1</option>');
		var option2 = $('<option value=2>2</option>');
		var option3 = $('<option value=3>3</option>');
		var option4 = $('<option value=4>4</option>');
		var option5 = $('<option selected value=5>5</option>');	
		
		select.append(option5).append(option4).append(option3).append(option2).append(option1);
		
		var labelKomentar = $('<label>Komentar(50 karaktera): </label>');
		var inputKomentar = $('<input type="text" class="form-control" id="input-komentar-'+count+'" placeholder="Komentar" required>');
		
		var btnSubmit = $('<button value="Oceni">Oceni </button>');
		btnSubmit.click(oceniOglas(count, oglas.id));
		
		form.append(labelOglas).append('<br>').append(labelOcena).append(select).append(labelKomentar).append(inputKomentar).append('<br>').append(btnSubmit).append('<hr><hr><hr>');
		
		$("#div-za-forme").append(form);
//		var ocena = $('#select-ocena-'+count+' :selected').val();
//		alert("OCENA JE: " + ocena);
//		$('#form-'+count+'').submit(function(event) {
//			event.preventDefault(event);
////			var idOcene = select.attr('id');
////			var idKomentara = inputKomentar.attr('id');
//			oceniOglas(count, oglas.id);
//		});
		
		
		count++;
	}
	
//	console.log(count- 1);
}

function oceniOglas(count, oglasId) {
//	alert("Oceni oglas sa id:"+oglasId+ ", count:"+count+ ", zahtevId:"+zahtevId);
		
	return function() {
		var ocena = $('#select-ocena-'+count+' :selected').val();
		var komentar = $('#input-komentar-'+count+'').val();
//		if(komentar == "") {
//			alert("Komentar ne sme biti prazan!");
//			location.reload();
//		}
//		alert("Ocena:"+ocena+ ", komentar:"+komentar);
		
		$.post({
			url: '/ocena',
			data: JSON.stringify({ocena, komentar, oglasId, zahtevId}),
			headers: {
		        'Auth': 'Bearer ' + token
		    },
			contentType: 'application/json',
			success: function(ocena){
				alert("Uspešno ste ocenili oglas");
				location.reload();
			},
			error: function(message) {
	            alert("Dolo je do greske... "+ message);	   
	            location.reload();
	        }
		});
		
		alert("Obradjivanje zahteva...");
		
	}
	
	
	
}

