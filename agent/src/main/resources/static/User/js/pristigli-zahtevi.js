$(document).ready(function() {
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return;
	}

	$.get({
		url: '/zahtev/pending',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(zahtevi){
			izlistajZahteve(zahtevi);
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
	
});

function izlistajZahteve(zahtevi){
	$("#divTabela").empty();
	if(zahtevi != undefined){
		var tableUDivu = $("<table id='tabelaZahteva' class='display'> </table>")
		
		var thead = $("<thead> </thead>");
		thead.append("<tr><th>ID</th><th>Pocetak rentiranja</th><th>Kraj rentiranja</th><th>Podnosilac zahteva</th><th>Prihvati</th><th>Odbij</th><th>Detaljnije</th></tr>");
		
		var tbody = $("<tbody id='teloTabele'> </tbody>");
		
		for(var z of zahtevi){
			
			var tr = $("<tr> </tr>");
			var tdId = $("<td> </td>");
			tdId.append(z.id);
			var tdPocetak = $("<td> </td>");
			tdPocetak.append(z.od);
			var tdKraj = $("<td> </td>");
			tdKraj.append(z.do1);
			var tdPodnosilac = $("<td> </td>");
			tdPodnosilac.append(z.podnosilacUsername);
			
			var tdPrihvati = $("<td> </td>");
			var btn = $('<button> Prihvati zahtev </button>');
			btn.click(prihvatiZahtev(z.id));
			tdPrihvati.append(btn);
			
			var tdOdbij = $("<td> </td>");
			var btn1 = $('<button> Odbij zahtev </button>');
			btn1.click(declineZahtev(z.id));
			tdOdbij.append(btn1);
			
			var tdDetaljnije = $("<td> </td>");
			var btn2 = $('<button> Detaljnije </button>');
			btn2.click(detaljnije(z.id));
			tdDetaljnije.append(btn2);
			
			tr.append(tdId).append(tdPocetak).append(tdKraj).append(tdPodnosilac).append(tdPrihvati).append(tdOdbij).append(tdDetaljnije);
			tbody.append(tr);
			
		}
		
		tableUDivu.append(thead).append(tbody);
		$("#divTabela").append(tableUDivu);
		
		var table = $('#tabelaZahteva').dataTable({
	        "pagingType": "full_numbers",
	        select: false
	    });
		
	}
	
}


function detaljnije(zahtevId){
	return function() {
		window.location = "./zahtev.html?zahtevID=" + zahtevId;
	}
}

function odbijZahtev(zahtevId){
	
	return function(){
		$.ajax({
			url: "/zahtev/"+zahtevId+"/decline",
			type: 'PUT',
			headers: {
		        'Auth': 'Bearer ' + token
		    },
			success: function(){
				alert("Uspeh");
				window.location = "./pristigli-zahtevi.html";
				
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
			url: "/zahtev/"+zahtevId+"/accept",
			type: 'PUT',
			headers: {
		        'Auth': 'Bearer ' + token
		    },
			success: function(){
				alert("Uspeh");
				window.location = "./pristigli-zahtevi.html";
				
			},
			error: function(jqXhr, textStatus, errorMessage) {
	            console.log("Error: ", textStatus);
	        }
		});
	}
}
function declineZahtev(zahtevId){
	
	return function(){
		$.ajax({
			url: "/zahtev/"+zahtevId+"/decline",
			type: 'PUT',
			headers: {
		        'Auth': 'Bearer ' + token
		    },
			success: function(){
				alert("Uspeh");
				window.location = "./pristigli-zahtevi.html";
				
			},
			error: function(jqXhr, textStatus, errorMessage) {
	            console.log("Error: ", textStatus);
	        }
		});
	}
}



