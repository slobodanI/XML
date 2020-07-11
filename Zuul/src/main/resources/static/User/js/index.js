$(document).ready(function() {
//	TODO: popunjavanje selecta za mesto, marku, model,...
	$("#form-search-oglas").submit(function(event) {
		event.preventDefault();
		searchOglas();
	});
	
	
	
	popuniMesta();
	popuniMarke();
	popuniModele();
	popuniKLase();
	popuniGoriva();
	popuniMenjac();
	
});

function searchOglas() {
	var mesto =  $('#select-mesto :selected').val();
	var marka = $('#select-marka :selected').val();
	var model = $('#select-model :selected').val();
	var menjac =  $('#select-menjac :selected').val();
	var gorivo = $('#select-gorivo :selected').val();
	var klasa = $('#select-klasa :selected').val();
	
	var cenaOd = $('#input-cenaOd').val();
	var cenaDo = $('#input-cenaDo').val();
	var kilometraza = $('#input-kilometraza').val();
	var planiranaKilometraza = $('#input-planirana-kilometraza').val();
	var osiguranje = $('#select-osiguranje :selected').val();
	var brSedistaZaDecu = $('#select-brSedistaZaDecu').val();
	
	cenaOd = cenaOd.toString();
	cenaDo = cenaDo.toString();
	
	if(osiguranje == "1") {
		osiguranje = true;
	} else if (osiguranje == "2") {
		osifuranje = false;
	}
	
	
	Od = $('#input-od').val();
	Do = $('#input-do').val();
	
	console.log("mesto:"+mesto+ "\nmarka:" + marka + "\nmodel:"+model+ "\nmenjac:"+menjac+ "\ngorivo:"+gorivo+ "\nklasa:"+klasa);
	console.log("cenaOd:"+cenaOd+"\cenaDo"+cenaDo+ "\nkilometraza:" + kilometraza + "\nplaniranaKilometraza:"+planiranaKilometraza+ "\nosiguranje:"+osiguranje+ "\nbrSedistaZaDecu:"+brSedistaZaDecu);
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
	searchParams += "&klasa="+klasa+ "&predjena="+kilometraza+ "&planirana="+planiranaKilometraza+ "&osiguranje="+osiguranje+ "&brSedZaDecu="+brSedistaZaDecu+ "&cenaOd="+cenaOd+"&cenaDo="+cenaDo;
	
	$.get({
		url: 'oglasi/search?' + searchParams,
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
		thead.append("<tr><th>ID</th><th>Slika</th><th>Cena</th><th>Mesto</th><th>Ocena</th><th>Kilometraža</th><th>Marka</th><th>Model</th><th>Menjac</th><th>Gorivo</th><th>Klasa</th><th>Dečijih sedišta</th><th>Planirani Km</th><th>Osiguranje</th></tr>")
		
		var tbody = $("<tbody id='teloTabele'> </tbody>");
		
		for(var o of oglasi){ // id oglasa cu ubaciti u click na button
			var tr = $("<tr> </tr>")
			var tdId = $("<td> </td>");
			tdId.append(o.id);
			var tdSlika = $("<td> </td>");
			var imgSlika = $('<img src="'+o.slika+'" width="150" height="80">');
			//tdSlika.click(oglasDetalji(o.id));
			tdSlika.append(imgSlika);
			var tdMesto = $("<td> </td>");
			tdMesto.append(o.mesto);
			var tdOcena = $("<td> </td>");
			tdOcena.append(o.ocena)
			var tdKilometraza = $("<td> </td>");
			tdKilometraza.append(o.predjenaInt)
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
			var tdCena = $("<td> </td>");
			tdCena.append(o.cena)
			
			var tdDecijihSedista = $("<td> </td>");
			tdDecijihSedista.append(o.brSedZaDecuInt);
			var tdPlaniraniKm = $("<td> </td>");
			tdPlaniraniKm.append(o.planiranaInt);
			var tdOsiguranje = $("<td> </td>");
			tdOsiguranje.append(o.osiguranjeBool);
			
			var tdDodaj = $("<td> </td>");
			var btn = $('<button>Dodaj u korpu</button>');
			btn.click(dodajUKorpu(o.id, Od, Do));
			tdDodaj.append(btn);
			
			tr.append(tdId).append(tdSlika).append(tdCena).append(tdMesto).append(tdOcena).append(tdKilometraza).append(tdMarka).append(tdModel).append(tdMenjac).append(tdGorivo).append(tdKlasa)
			tr.append(tdDecijihSedista).append(tdPlaniraniKm).append(tdOsiguranje);
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

function popuniMesta() {
	$.get({
		url: '/sifrarnik/mesto',
		contentType: 'application/json',
		success: function(mesta){
			
			var selectMesto = $("#select-mesto");
			
			for(var mesto of mesta){
				selectMesto.append('<option value="'+mesto.name+'">'+mesto.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniMarke() {
	$.get({
		url: '/sifrarnik/marka',
		contentType: 'application/json',
		success: function(marke){
			
			var selectMarka = $("#select-marka");
			
			for(var marka of marke){
				selectMarka.append('<option value="'+marka.name+'">'+marka.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniModele() {
	$.get({
		url: '/sifrarnik/model',
		contentType: 'application/json',
		success: function(modeli){
			
			var selectModel = $("#select-model");
		
			for(var model of modeli){
				selectModel.append('<option value="'+model.name+'">'+model.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniKLase() {
	$.get({
		url: '/sifrarnik/klasa',
		contentType: 'application/json',
		success: function(klase){
			
			var selectKlasa = $("#select-klasa");
		
			for(var klasa of klase){
				selectKlasa.append('<option value="'+klasa.name+'">'+klasa.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniGoriva() {
	$.get({
		url: '/sifrarnik/gorivo',
		contentType: 'application/json',
		success: function(goriva){
			
			var selectGorivo = $("#select-gorivo");
		
			for(var gorivo of goriva){
				selectGorivo.append('<option value="'+gorivo.name+'">'+gorivo.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniMenjac() {
	$.get({
		url: '/sifrarnik/menjac',
		contentType: 'application/json',
		success: function(menjaci){
			
			var selectMenjac = $("#select-menjac");
		
			for(var menjac of menjaci){
				selectMenjac.append('<option value="'+menjac.name+'">'+menjac.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}