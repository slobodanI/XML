// greska, ovo mi ovde ne treba....
var params = new URL(location.href).searchParams;
var klinikaID = params.get("klinikaID");


$(document).ready(function(){
	
	var korpa = sessionStorage.getItem('korpa');
	if(korpa == undefined) {
		var korpa = {};
		var oglasi = [];
		korpa.oglasi = oglasi;
		sessionStorage.setItem('korpa', JSON.stringify(korpa));		
	}
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return;
	}
	
	popuniTabelu();
	
	$("#form-zahtev-create").submit(function(event) {
		event.preventDefault();
		posaljiZahtev();
	});
	
});

function popuniTabelu() {
	
	var korpa = JSON.parse(sessionStorage.korpa);
		
	var tableUDivu = $("<table id='tabelaOglasa' class='display'> </table>");
	
	var thead = $("<thead> </thead>");
	thead.append("<tr><th>Oglas ID</th><th>OD</th><th>DO</th><th>Ukloni iz korpe</th></tr>")
	
	var tbody = $("<tbody id='teloTabele'> </tbody>");
	
	for(var o of korpa.oglasi){
		var tr = $("<tr> </tr>")
		var tdId = $("<td> </td>");
		tdId.append(o.oglasId);
		var tdOD = $("<td> </td>");
		tdOD.append(o.od);
		var tdDO = $("<td> </td>");
		tdDO.append(o.do);
		
		var tdUkloni = $("<td> </td>");
		var btn = $('<button>Ukloni iz korpe</button>');
		btn.click(ukloniIzKorpe(o.oglasId));
		tdUkloni.append(btn);
		
		tr.append(tdId).append(tdOD).append(tdDO).append(tdUkloni);
		tbody.append(tr);
	}
	
	tableUDivu.append(thead).append(tbody);
	$("#divTabela").append(tableUDivu);
	
	var table = $('#tabelaOglasa').dataTable({
        "pagingType": "full_numbers",
        select: false
    });
	
}

function ukloniIzKorpe(oglasId) {
	
	return function() {
		var korpa = JSON.parse(sessionStorage.korpa);
		var count = 0;
		for(o of korpa.oglasi){	
			//console.log(o);
			if(o.oglasId == oglasId){
				korpa.oglasi.splice(count, count + 1);
				break;
			}
			count++;
		}
		sessionStorage.setItem('korpa', JSON.stringify(korpa));
		
		window.location = "./korpa.html";
	}	
}

function posaljiZahtev() {
	
	var korpa = JSON.parse(sessionStorage.korpa);
	if(korpa.oglasi.length == 0) {
		alert("Korpa je prazna!");
		return;
	}
	
	var bundle = $('#select-bundle :selected').val();
	if(bundle == "1") {
		bundle = false;
	} else if (bundle == "2") {
		bundle = true;
	}
	
	korpa.bundle = bundle;
	//console.log(korpa);
	$.post({
		url: '/oglasi/zahtev',
		data: JSON.stringify(korpa),
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(){
			//isprazni korpu
			korpa.oglasi = [];
			sessionStorage.setItem('korpa', JSON.stringify(korpa));
			alert("Uspešno ste poslali zahtev");
			window.location = "./korpa.html";
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error jqXhr: " +jqXhr);
            console.log("Error textStatus: " +textStatus);
            console.log("Error errorMessage: " +errorMessage);
        }
	});
}