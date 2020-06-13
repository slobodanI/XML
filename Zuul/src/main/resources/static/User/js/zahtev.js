var params = new URL(location.href).searchParams;
var zahtevID = params.get("zahtevID");

$(document).ready(function() {
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return;
	}
	$.get({
		url: '/oglasi/zahtev/' + zahtevID,
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(z){
			$("#zId").append("Id: " + z.id);
			$("#zOd").append("Pocetak rentiranja: " + z.od);
			$("#zDo").append("Kraj rentiranja: " + z.do1);
			$("#zPodnosilacZahteva").append("Podnosilac zahteva: " + z.podnosilacUsername);
			dobaviOglase(z.id);
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});	
	
});

function dobaviOglase(zahtevId){
	$.get({
		url: '/oglasi/zahtev/'+ zahtevID +'/oglasi',
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
		thead.append("<tr><th>ID</th><th>Slika</th><th>Mesto</th><th>Marka</th><th>Model</th><th>Menjac</th><th>Gorivo</th><th>Klasa</th></tr>")
		
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
			
			
			tr.append(tdId).append(tdSlika).append(tdMesto).append(tdMarka).append(tdModel).append(tdMenjac).append(tdGorivo).append(tdKlasa);
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
	
