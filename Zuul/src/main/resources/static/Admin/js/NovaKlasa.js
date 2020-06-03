function NewKlasa()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/sifrarnik/klasa',
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
    
	$("#novaKlasa").submit(function( event ) {
		event.preventDefault();
		
		NewKlasa();
		  
	});
	
});