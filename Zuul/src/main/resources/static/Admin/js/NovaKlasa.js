function NewKlasa()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/sifrarnik/klasa',
		data: info,
		contentType: 'application/json',
		success: function(klasa)
		{
			if(klasa==null)
			{
				alert("Neuspešno kreiranje klase");
				
			}
			else
			{
				alert("Klasa kreirana. Šifra: " + klasa.id + " Naziv: " + klasa.name);
			}

		},
		error: function()
		{
			alert("Greska pri kreiranju klase");
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
			window.location = "../login.html";
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

	$("#novaKlasa").submit(function( event ) {
		event.preventDefault();
		
		NewKlasa();
		  
	});
	
});

