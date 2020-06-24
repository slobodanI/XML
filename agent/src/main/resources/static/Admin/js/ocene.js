$(document).ready(function(){
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		return; // treba ga vratiti na login.html
	}
	
	getAllOcene();
	
	
});

function getAllOcene() {
	$.get({
		url: '/ocena/?filter=toBeApproved',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(ocene){
			popuniTabelu(ocene);
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}

function popuniTabelu(ocene) {
		
	var tableUDivu = $("<table id='tabelaOcena' class='display'> </table>");
	
	var thead = $("<thead> </thead>");
	thead.append("<tr><th>Ocena ID</th><th>Ocena</th><th>Komentar</th><th>Oglas</th><th>Ko</th><th>Koga</th><th>Prihvatanje</th><th>Odbijanje</th></tr>")
	
	var tbody = $("<tbody id='teloTabele'> </tbody>");
	
	for(var ocena of ocene){
		var tr = $("<tr> </tr>")
		
		var tdId = $("<td> </td>");
		tdId.append(ocena.id);
		
		var tdOcena = $("<td> </td>");
		tdOcena.append(ocena.ocena);
		
		var tdKomentar = $("<td> </td>");
		tdKomentar.append(ocena.komentar);
		
		var tdOglas = $("<td> </td>");
		tdOglas.append(ocena.oglas);
		
		var tdKo = $("<td> </td>");
		tdKo.append(ocena.usernameKo);
		
		var tdKoga = $("<td> </td>");
		tdKoga.append(ocena.usernameKoga);
		
		var tdPrihvati = $("<td> </td>");	
		var btnPrihvati = $('<button>Prihvati</button>');
		btnPrihvati.click(approveOcena(ocena.id));
		tdPrihvati.append(btnPrihvati);
			
		var tdOdbijanje = $("<td> </td>");
		var btnOdbijanje = $('<button>Odbijanje</button>');
		btnOdbijanje.click(denyOcena(ocena.id));
		tdOdbijanje.append(btnOdbijanje);
	
		tr.append(tdId).append(tdOcena).append(tdKomentar).append(tdOglas).append(tdKo).append(tdKoga).append(tdPrihvati).append(tdOdbijanje);
		tbody.append(tr);
	}
	
	tableUDivu.append(thead).append(tbody);
	$("#divTabela").append(tableUDivu);
	
	var table = $('#tabelaOcena').dataTable({
        "pagingType": "full_numbers",
        select: false
    });
	
}

function approveOcena(ocenaId) {
	
	return function() {		
		$.ajax({
			type:'PUT',
			url: '/ocena/'+ocenaId+'/approve',
			headers: {
				'Auth': 'Bearer ' + token
			},
			contentType: 'application/json',
			success: function() {
				alert("Uspešno ste prihvatili ocenu i komentar!");
			},
			error: function(message) {
				alert("ERROR:" + message);
			}
				
		});
	}	
}

function denyOcena(ocenaId) {
	
	return function() {		
		$.ajax({
			type:'PUT',
			url: '/ocena/'+ocenaId+'/deny',
			headers: {
				'Auth': 'Bearer ' + token
			},
			contentType: 'application/json',
			success: function() {
				alert("Uspešno ste odbili ocenu i komentar!");
			},
			error: function(message) {
				alert("ERROR:" + message);
			}
				
		});
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
		url: '/whoami',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(user) {
			var ROLES = "";
			for(var role of user.authorities){					
				ROLES += role.authority+","
			}
			if(ROLES.includes("ROLE_ADMIN")) {
				// 
			} else {
				window.location = "../login.html";
			}
			
		},
		error: function() {
			alert("Neuspešno ste se prijavili");
		}
	});
}