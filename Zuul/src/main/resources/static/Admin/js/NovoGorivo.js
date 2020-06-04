function NewGorivo()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/sifrarnik/gorivo',
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
    
	$("#novoGorivo").submit(function( event ) {
		event.preventDefault();
		
		NewGorivo();
		  
	});
	
});