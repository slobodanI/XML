function NewGorivo()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/gorivo',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
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



$( document ).ready(function() {
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return; // treba ga vratiti na login.html
	}
	
    
	$("#novoGorivo").submit(function( event ) {
		event.preventDefault();
		
		NewGorivo();
		  
	});
	
});