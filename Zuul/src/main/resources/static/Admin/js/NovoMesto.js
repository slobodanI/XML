function NewMesto()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/sifrarnik/mesto',
		data: info,
		contentType: 'application/json',
		success: function(mesto)
		{
			if(mesto==null)
			{
				alert("Neuspešno kreiranje mesta");
				
			}
			else
			{
				alert("Mesto kreirano. Šifra: " + mesto.id + " Naziv: " + mesto.name);
			}

		},
		error: function()
		{
			alert("Greska pri kreiranju mesta");
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
		return;
	}
	
	$("#novoMesto").submit(function( event ) {
		event.preventDefault();
		
		NewMesto();
		  
	});
	
});