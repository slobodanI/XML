function Pronadji()
{
	
	
 	var Id = $('#sifraTxt').val();

 	
 	
	 $.get({
			
			url:'/klasa/'+Id,
			headers: {
		        'Auth': 'Bearer ' + token
		    },
			contentType: 'application/json',
			success: function(result)
			{	
				$('#nazivTxt').val(result.name)
			},
			error: function(result)
			{
				alert("Taj indeks ne postoji.");
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
	  
	$("#pronKlasu").submit(function( event ) {
		event.preventDefault();
		
		Pronadji();
		  
	});
	
});