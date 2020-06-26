$(document).ready(function(){
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	
	getAllOcene();
	
	
});

function getAllOcene() {
	$.get({
		url: '/ocena/?filter=moje',
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
	thead.append("<tr><th>Ocena ID</th><th>Ocena</th><th>Komentar</th><th>Oglas</th><th>Koga</th><th>Status</th><th>Odgovor</th></tr>")
	
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
	
		var tdKoga = $("<td> </td>");
		tdKoga.append(ocena.usernameKoga);
		
		var tdStatus = $("<td> </td>");
		tdStatus.append(ocena.status)
		
		var tdOdgovor = $("<td> </td>");
		tdOdgovor.append(ocena.odgovor);
		
		tr.append(tdId).append(tdOcena).append(tdKomentar).append(tdOglas).append(tdKoga).append(tdStatus).append(tdOdgovor);
		tbody.append(tr);
	}
	
	tableUDivu.append(thead).append(tbody);
	$("#divTabela").append(tableUDivu);
	
	var table = $('#tabelaOcena').dataTable({
        "pagingType": "full_numbers",
        select: false
    });
	
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