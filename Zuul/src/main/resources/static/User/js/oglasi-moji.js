$(document).ready(function() {
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	getMyOglasi();
	
});


function getMyOglasi() {
	$.get({
		url: '/oglasi/oglas/?filter=moje',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
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
		thead.append("<tr><th>ID</th><th>Slika</th><th>Mesto</th><th>Marka</th><th>Model</th><th>Menjac</th><th>Gorivo</th><th>Klasa</th><th>Unesi zauzece</th><th>Izmeni</th><th>Obrisi</th></tr>")
		
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
			
			var tdUnos = $("<td> </td>");
			var btnUnos = $("<button>Unesi zauzece </button>");
			btnUnos.click(unosZauzeca(o.id));
			tdUnos.append(btnUnos);
			
			var tdIzmeni = $("<td> </td>");
			var btnIzmeni = $("<button>Izmeni</button>");
			btnIzmeni.click(izmeniOglas(o.id));
			tdIzmeni.append(btnIzmeni);
			
			var tdObrisi = $("<td> </td>");
			var btnObrisi = $("<button>Obrisi</button>");
			btnObrisi.click(obrisiOglas(o.id));
			tdObrisi.append(btnObrisi);
			
			
			tr.append(tdId).append(tdSlika).append(tdMesto).append(tdMarka).append(tdModel).append(tdMenjac).append(tdGorivo).append(tdKlasa).append(tdUnos).append(tdIzmeni).append(tdObrisi);
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

function izmeniOglas(oglasId) {
	
	return function() {
		window.location = "./oglas-update.html?oglasId=" + oglasId;
	}
	
}
function obrisiOglas(oglasId) {
	return function(){
	$.ajax({
		url: '/oglasi/oglas/'+oglasId,
		type: 'DELETE',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(oglasi){
			window.location = "./oglasi-moji.html";
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
	
	}
}

function oglasDetalji(oglasId) {
	
	return function() {
		window.location = "./oglas.html?oglasId=" + oglasId;
	}
	
}
function unosZauzeca(oglasId) {
	
	return function() {
		window.location = "./zahtev-rucno-unosenje.html?oglasId=" + oglasId;
	}
	
}

function whoami() {
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return;
	}
	
	$.get({
		url: '/auth/whoami',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(user) {
			var ROLES = "";
			for(var role of user.authorities){					
				ROLES += role.authority+","
			}
			if(ROLES.includes("ROLE_USER") || ROLES.includes("ROLE_USER_LIMITED") || ROLES.includes("ROLE_AGENT") ) {
				// 
			} else {
				window.location = "../login.html";
			}
			
		},
		error: function() {
			window.location = "../login.html";
		}
	});
}
