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



$( document ).ready(function() {
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return; // treba ga vratiti na login.html
	}
    
	$("#noviMenjac").submit(function( event ) {
		event.preventDefault();
		
		NewMenjac();
		  
	});
	
});