function NewKlasa()
{
	var info = $("#nazivTxt").val();
	

	
	$.post
	({
		url: '/klasa',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
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



$( document ).ready(function() {
    
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return; // treba ga vratiti na login.html
	}
	
	$("#novaKlasa").submit(function( event ) {
		event.preventDefault();
		
		NewKlasa();
		  
	});
	
});