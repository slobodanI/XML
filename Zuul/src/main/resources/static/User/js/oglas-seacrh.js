$(document).ready(function() {
//	TODO: popunjavanje selecta za mesto, marku, model,...
	$("#form-search-oglas").submit(function(event) {
		event.preventDefault();
		searchOglas();
	});
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return;
	}
	
	var korpa = sessionStorage.getItem('korpa');
	if(korpa == undefined) {
		var korpa = {};
		var oglasi = [];
		korpa.oglasi = oglasi;
		sessionStorage.setItem('korpa', JSON.stringify(korpa));
	}
	
});

function searchOglas() {
	var mesto =  $('#select-mesto :selected').val();
	var marka = $('#select-marka :selected').val();
	var model = $('#select-model :selected').val();
	var menjac =  $('#select-menjac :selected').val();
	var gorivo = $('#select-gorivo :selected').val();
	var klasa = $('#select-klasa :selected').val();
	
	var cena = $('#input-cena').val();
	var kilometraza = $('#input-kilometraza').val();
	var planiranaKilometraza = $('#input-planirana-kilometraza').val();
	var osiguranje = $('#select-osiguranje :selected').val();
	var brSedistaZaDecu = $('#select-brSedistaZaDecu').val();
	
	
	if(osiguranje == "1") {
		osiguranje = true;
	} else if (osiguranje == "2") {
		osifuranje = false;
	}
	
	
	Od = $('#input-od').val();
	Do = $('#input-do').val();
	
	console.log("mesto:"+mesto+ "\nmarka:" + marka + "\nmodel:"+model+ "\nmenjac:"+menjac+ "\ngorivo:"+gorivo+ "\nklasa:"+klasa);
	console.log("cena:"+cena+ "\nkilometraza:" + kilometraza + "\nplaniranaKilometraza:"+planiranaKilometraza+ "\nosiguranje:"+osiguranje+ "\nbrSedistaZaDecu:"+brSedistaZaDecu);
	console.log("Od:"+Od+ "\nDo:" + Do);
	
	var split1 = Od.split("-");
	var split2 = Do.split("-");
	
	if(split1[0] > split2[0]) {
		alert("OD datum mora biti pre DO datuma!");
		return;
	}
	if(split1[0] == split2[0] && split1[1] > split2[1]) {
		alert("OD datum mora biti pre DO datuma!");
		return;
	}
	if(split1[0] == split2[0] && split1[1] == split2[1] && split1[2] >= split2[2]) {
		alert("OD datum mora biti pre DO datuma!");
		return;
	}
	
	var searchParams = "mesto="+mesto+ "&Od="+Od+ "&Do="+Do+ "&marka="+marka+ "&model="+model+ "&menjac"+menjac+ "&gorivo="+gorivo;
	searchParams += "&klasa="+klasa+ "&predjena="+kilometraza+ "&planirana="+planiranaKilometraza+ "&osiguranje="+osiguranje+ "&brSedZaDecu="+brSedistaZaDecu;
	
	$.get({
		url: '/oglasi/search?' + searchParams,
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(oglasi){
			izlistajOglase(oglasi);
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}

function izlistajOglase(oglasi){
	$("#divTabela").empty();
	if(oglasi != undefined){		
		var tableUDivu = $("<table id='tabelaOglasa' class='display'> </table>");
		
		var thead = $("<thead> </thead>");
		thead.append("<tr><th>ID</th><th>Slika</th><th>Mesto</th><th>Marka</th><th>Model</th><th>Menjac</th><th>Gorivo</th><th>Klasa</th><th>Dodaj</th></tr>")
		
		var tbody = $("<tbody id='teloTabele'> </tbody>");
		
		for(var o of oglasi){ // id oglasa cu ubaciti u click na button
			var tr = $("<tr> </tr>")
			var tdId = $("<td> </td>");
			tdId.append(o.id);
			var tdSlika = $("<td> </td>");
			var imgSlika = $('<img src="'+o.slika+'" width="150" height="80">');
			tdSlika.click(oglasDetalji(o.id));
			tdSlika.append(imgSlika);
			var tdMesto = $("<td> </td>");
			tdMesto.append(o.mesto);
			var tdMarka = $("<td> </td>");
			tdMarka.append(o.marka);
			var tdModel = $("<td> </td>");
			tdModel.append(o.model);
			var tdMenjac = $("<td> </td>");
			tdMenjac.append(o.menjac);
			var tdGorivo = $("<td> </td>");
			tdGorivo.append(o.gorivo);
			var tdKlasa = $("<td> </td>");
			tdKlasa.append(o.klasa);
			
			var tdDodaj = $("<td> </td>");
			var btn = $('<button>Dodaj u korpu</button>');
			btn.click(dodajUKorpu(o.id, Od, Do));
			tdDodaj.append(btn);
			
			tr.append(tdId).append(tdSlika).append(tdMesto).append(tdMarka).append(tdModel).append(tdMenjac).append(tdGorivo).append(tdKlasa).append(tdDodaj);
			tbody.append(tr);
		}
		
		tableUDivu.append(thead).append(tbody);
		$("#divTabela").append(tableUDivu);
		
		var table = $('#tabelaOglasa').dataTable({
	        "pagingType": "full_numbers",
	        select: false
	    });
	}
}

function oglasDetalji(oglasId) {
	
	return function() {
		window.location = "./oglas.html?oglasId=" + oglasId;
	}
	
}

function dodajUKorpu(oglasId, odKad, doKad) {
	
	return function() {
		var korpa = JSON.parse(sessionStorage.korpa);
		
		//provera da li je vec u korpi
		for(o of korpa.oglasi) {
			if(o.oglasId == oglasId){
				alert("Vec ste dodali taj oglas u korpu!");
				return;
			}
		}
		
		//provera za od i do datum
		for(o of korpa.oglasi) {
			if(o.od != odKad || o.do != doKad){
				alert("U korpi se moraju nalaziti oglasi koji sadrze iste OD i DO datume!");
				return;
			}
		}
		
		var oglasUKorpi = {
				"oglasId": oglasId,
				"od": odKad,
				"do": doKad
		};
		korpa.oglasi[korpa.oglasi.length] = oglasUKorpi;
		sessionStorage.setItem('korpa', JSON.stringify(korpa));
		alert("Uspesno dodavanje u korpu!");
	}
	
}


