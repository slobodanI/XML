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



$( document ).ready(function() {
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return; // treba ga vratiti na login.html
	}
	
	$("#noviModel").submit(function( event ) {
		event.preventDefault();
		
		NewModel();
		  
	});
	
});