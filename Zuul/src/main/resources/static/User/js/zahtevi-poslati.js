$(document).ready(function(){
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	
	getMyZahtevi();
	
	
});

function getMyZahtevi() {
	$.get({
		url: '/oglasi/zahtev/?filter=moje',
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
		if(zahtev.status != 'CANCELED'){
			btnOtkazi.click(cancelZahtev(zahtev.id));
			tdOtkazivanje.append(btnOtkazi);
		}
		
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

function cancelZahtev(zahtevId){
	return function(){
	$.ajax({
		url: '/oglasi/zahtev/'+zahtevId+'/cancel',
		type: 'PUT',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(){
			alert("Uspeh");
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
	window.location = "./zahtevi-poslati.html";
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

