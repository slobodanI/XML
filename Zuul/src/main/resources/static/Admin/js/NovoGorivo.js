function NewGorivo()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/sifrarnik/gorivo',
		data: info,
		contentType: 'application/json',
		success: function(gorivo)
		{
			if(gorivo==null)
			{
				alert("Neuspešno kreiranje goriva");
				
			}
			else
			{
				alert("Gorivo kreirano. Šifra: " + gorivo.id + " Naziv: " + gorivo.name);
			}

		},
		error: function()
		{
			alert("Greska pri kreiranju goriva");
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
		url: '/auth/whoami',
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

$( document ).ready(function() {
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}

	$("#novoGorivo").submit(function( event ) {
		event.preventDefault();
		
		NewGorivo();
		  
	});
	
});