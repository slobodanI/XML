$(document).ready(function(){
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return; // treba ga vratiti na login.html
	}
	
	getMyZahtevi();
	
	
});

function getMyZahtevi() {
	$.get({
		url: '/zahtev/?filter=moje',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(zahtevi){
			popuniTabelu(zahtevi);
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}

function popuniTabelu(zahtevi) {
		
	var tableUDivu = $("<table id='tabelaZahteva' class='display'> </table>");
	
	var thead = $("<thead> </thead>");
	thead.append("<tr><th>Zahtev ID</th><th>Status</th><th>Olgasi</th><th>Od</th><th>Do</th><th>Ocenjivanje</th><th>Otkazivanje</th></tr>")
	
	var tbody = $("<tbody id='teloTabele'> </tbody>");
	
	for(var zahtev of zahtevi){
		var tr = $("<tr> </tr>")
		
		var tdId = $("<td> </td>");
		tdId.append(zahtev.id);
		
		var tdStatus = $("<td> </td>");
		tdStatus.append(zahtev.status);
		
		var tdOglasi = $("<td> </td>");
		var pTagOglasi = $("<p> </p>");
		
		for(oglas of zahtev.oglasi) {
			pTagOglasi.append(oglas.id+"-"+oglas.marka+"-"+oglas.model+"<br>");
		}
		tdOglasi.append(pTagOglasi);
		
		var tdOd = $("<td> </td>");
		tdOd.append(zahtev.od);
		
		var tdDo = $("<td> </td>");
		tdDo.append(zahtev.do1);
		
		var tdOceni = $("<td> </td>");
		var btnOceni = $('<button>Oceni oglas/e</button>');
		
		var now = new Date();
		var doDate = new Date(zahtev.do1);
		
		if(zahtev.status == 'PAID' && doDate < now) {
			btnOceni.click(oceniOglaseUZahtevu(zahtev.id));
			tdOceni.append(btnOceni);
		}
		
//		doradi...
		var tdOtkazivanje = $("<td> </td>");
		var btnOtkazi = $('<button>Otkaži</button>');
		
		tr.append(tdId).append(tdStatus).append(tdOglasi).append(tdOd).append(tdDo).append(tdOceni).append(tdOtkazivanje);
		tbody.append(tr);
	}
	
	tableUDivu.append(thead).append(tbody);
	$("#divTabela").append(tableUDivu);
	
	var table = $('#tabelaZahteva').dataTable({
        "pagingType": "full_numbers",
        select: false
    });
	
}

function oceniOglaseUZahtevu(zahtevId) {
	
	return function() {		
		window.location = "./ocenjivanje.html?zahtevId=" + zahtevId;
	}	
}

