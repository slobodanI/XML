function NewMesto()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/mesto',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
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



$( document ).ready(function() {
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return; // treba ga vratiti na login.html
	}
	
    
	$("#novoMesto").submit(function( event ) {
		event.preventDefault();
		
		NewMesto();
		  
	});
	
});