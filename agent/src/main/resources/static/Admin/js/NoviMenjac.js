function NewMenjac()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/menjac',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		data: info,
		contentType: 'application/json',
		success: function(menjac)
		{
			if(menjac==null)
			{
				alert("Neuspešno kreiranje menjača");
				
			}
			else
			{
				alert("Menjač kreiran. Šifra: " + menjac.id + " Naziv: " + menjac.name);
			}

		},
		error: function()
		{
			alert("Greska pri kreiranju menjača");
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
	
    
	$("#noviMenjac").submit(function( event ) {
		event.preventDefault();
		
		NewMenjac();
		  
	});
	
});