$(document).ready(function(){
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	
	getMyCenovnici();
	
	
});



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


function getMyCenovnici() {
	$.get({
		url: '/cenovnik',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(cenovnici){
			popuniTabelu(cenovnici);
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}


function popuniTabelu(cenovnici) {
	

	
	var tableUDivu = $("<table id='tabelaCenovnika' class='display'> </table>");
	
	var thead = $("<thead> </thead>");
	thead.append("<tr><th>Cenovnik ID</th><th>Naziv</th><th>Cena za dan</th><th>Cena po kilometru</th><th>Cena osiguranja</th><th>Popust</th><th>Za vise od x dana</th><th>Izmeni</th></tr>")
	
	var tbody = $("<tbody id='teloTabele'> </tbody>");
	
	for(var cenovnik of cenovnici){
		var tr = $("<tr> </tr>")
		
		var tdId = $("<td> </td>");
		tdId.append(cenovnik.id);
		
		var tdNaziv = $("<td> </td>");
		tdNaziv.append(cenovnik.name);
		
		var tdCenaZaDan = $("<td> </td>");
		tdCenaZaDan.append(cenovnik.cenaZaDan);
		
		var tdCenaPoKilometru = $("<td> </td>");
		tdCenaPoKilometru.append(cenovnik.cenaPoKilometru);
		
		var tdCenaOsiguranja = $("<td> </td>");
		tdCenaOsiguranja.append(cenovnik.cenaOsiguranja);
		
		var tdPopust = $("<td> </td>");
		tdPopust.append(cenovnik.popust);
		
		var tdzaViseOd = $("<td> </td>");
		tdzaViseOd.append(cenovnik.zaViseOd);
		
		var tdIzmeni = $("<td> </td>");	
			var btnIzmeni = $('<button>Izmeni</button>');
			btnIzmeni.click(izmeniCenovnik(cenovnik.id));
			tdIzmeni.append(btnIzmeni);

		tr.append(tdId).append(tdNaziv).append(tdCenaZaDan).append(tdCenaPoKilometru).append(tdCenaOsiguranja).append(tdPopust).append(tdzaViseOd).append(tdIzmeni);
		tbody.append(tr);
	}
	
	tableUDivu.append(thead).append(tbody);
	$("#divTabela").append(tableUDivu);
	
	var table = $('#tabelaCenovnika').dataTable({
        "pagingType": "full_numbers",
        select: false
    });
	
}


function izmeniCenovnik(cenovnikId){
	
	return function(){
		window.location = "./cenovnik-update.html?cenovnikId=" + cenovnikId;
	}
	
}





