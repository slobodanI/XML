function NewMarka()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/sifrarnik/marka',
		data: info,
		contentType: 'application/json',
		success: function(marka)
		{
			if(marka==null)
			{
				alert("Neuspešno kreiranje marke");
				
			}
			else
			{
				alert("Marka kreirana. Šifra: " + marka.id + " Naziv: " + marka.name);
			}

		},
		error: function()
		{
			alert("Greska pri kreiranju marke");
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

	$("#novaMarka").submit(function( event ) {
		event.preventDefault();
		
		NewMarka();
		  
	});
	
});