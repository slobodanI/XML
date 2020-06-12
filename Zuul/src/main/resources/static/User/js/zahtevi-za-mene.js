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
		url: '/oglasi/zahtev/?filter=zaMene',
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
	thead.append("<tr><th>Zahtev ID</th><th>Status</th><th>Olgasi</th><th>Od</th><th>Do</th><th>Prihvatanje</th><th>Otkazivanje</th></tr>")
	
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
			pTagOglasi.append(oglas.marka+"-"+oglas.model+"<br>");
		}
		tdOglasi.append(pTagOglasi);
		
		var tdOd = $("<td> </td>");
		tdOd.append(zahtev.od);
		
		var tdDo = $("<td> </td>");
		tdDo.append(zahtev.do1);
		
		var tdPrihvati = $("<td> </td>");
		var tdOtkazivanje = $("<td> </td>");
		if(zahtev.status == 'PENDING') {
//			doradi...			
			var btnPrihvati = $('<button>Prihvati</button>');
			btnPrihvati.click(prihvatiZahtev(zahtev.id));
			tdPrihvati.append(btnPrihvati);
//			doradi...			
			var btnOtkazi = $('<button>Otkaži</button>');
			btnOtkazi.click(odbijZahtev(zahtev.id))
			tdOtkazivanje.append(btnOtkazi);
		}
	
		tr.append(tdId).append(tdStatus).append(tdOglasi).append(tdOd).append(tdDo).append(tdPrihvati).append(tdOtkazivanje);
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


function odbijZahtev(zahtevId){
	
	return function(){
		$.ajax({
			url: "/oglasi/zahtev/"+zahtevId+"/decline",
			type: 'PUT',
			headers: {
		        'Auth': 'Bearer ' + token
		    },
			success: function(){
				alert("Uspeh");
				window.location = "./zahtevi-za-mene.html";
				
			},
			error: function(jqXhr, textStatus, errorMessage) {
	            console.log("Error: ", textStatus);
	        }
		});
		

	}
}

function prihvatiZahtev(zahtevId){
	
	return function(){
		$.ajax({
			url: "/oglasi/zahtev/"+zahtevId+"/accept",
			type: 'PUT',
			headers: {
		        'Auth': 'Bearer ' + token
		    },
			success: function(){
				alert("Uspeh");
				window.location = "./zahtevi-za-mene.html";
				
			},
			error: function(jqXhr, textStatus, errorMessage) {
	            console.log("Error: ", textStatus);
	        }
		});
	}
}

