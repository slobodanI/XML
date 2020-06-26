$(document).ready(function(){
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	
	getAllOcene();
	
	$('#exampleModal').on('show.bs.modal', function (event) {
	  var button = $(event.relatedTarget); // Button that triggered the modal
	  var ocenaId = button.data('ocenaid'); // Extract info from data-* attributes
	  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
	  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
	  var modal = $(this)
	  modal.find('.modal-title').text('Odgovor za oglas sa id:' + ocenaId);
	  //modal.find('.modal-body input').val(ocenaId);
	})
	
	$("#btn-send-odgovor").click(function() {
		sendOdgovor();
	});
	
	
});

function getAllOcene() {
	$.get({
		url: '/ocena/?filter=zaMene',
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
	thead.append("<tr><th>Ocena ID</th><th>Ocena</th><th>Komentar</th><th>Oglas</th><th>Ko</th>><th>Odgovor</th><th>Odgovori</th></tr>")
	
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
		
		var tdOdgovor = $("<td> </td>");
		tdOdgovor.append(ocena.odgovor);
		
		var tdOdgovori = $("<td> </td>");
		if(ocena.odgovor == "nema odgovora..."){
			var btnPrihvati = $('<button type="button" data-toggle="modal" data-target="#exampleModal" data-ocenaid="'+ocena.id+'">Odgovori</button>');
			tdOdgovori.append(btnPrihvati);
		}
			
		tr.append(tdId).append(tdOcena).append(tdKomentar).append(tdOglas).append(tdKo).append(tdOdgovor).append(tdOdgovori);
		tbody.append(tr);
	}
	
	tableUDivu.append(thead).append(tbody);
	$("#divTabela").append(tableUDivu);
	
	var table = $('#tabelaOcena').dataTable({
        "pagingType": "full_numbers",
        select: false
    });
	
}

function sendOdgovor() {
	var title = $("#h5-modal-title").text(); // Odgovor za oglas sa id:1
	var ocenaIdSplit = title.split(":");
	var ocenaId = ocenaIdSplit[1];
	var odgovor = $("#textarea-odgovor").val();
//	console.log("odgovor:" + odgovor);
	if(odgovor.length < 1 || odgovor.length >50) {
		alert("Odgovor mora sadržati izmedju 1 i 50 karaktera!");
	}
	
		$.ajax({
			type:'PUT',
			url: '/ocena/'+ocenaId+'/odgovor',
			headers: {
				'Auth': 'Bearer ' + token
			},
			data: JSON.stringify({odgovor}),
			contentType: 'application/json',
			success: function() {
				alert("Uspešno ste odgovorili!");
				$("#textarea-odgovor").val("");
				location.reload();
			},
			error: function(message) {
				alert("ERROR:" + message);
			}
				
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