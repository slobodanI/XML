function NewMarka()
{
	var info = $("#nazivTxt").val();
	

	
	$.post
	({
		url: '/marka',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
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



$( document ).ready(function() {
    
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return; // treba ga vratiti na login.html
	}
	
	$("#novaMarka").submit(function( event ) {
		event.preventDefault();
		
		NewMarka();
		  
	});
	
});