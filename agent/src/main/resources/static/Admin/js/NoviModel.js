function NewModel()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/model',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		data: info,
		contentType: 'application/json',
		success: function(model)
		{
			if(model==null)
			{
				alert("Neuspešno kreiranje modela");
				
			}
			else
			{
				alert("Model kreiran. Šifra: " + model.id + " Naziv: " + model.name);
			}

		},
		error: function()
		{
			alert("Greska pri kreiranju modela");
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
	
	$("#noviModel").submit(function( event ) {
		event.preventDefault();
		
		NewModel();
		  
	});
	
});