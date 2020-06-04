function NewMenjac()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/sifrarnik/menjac',
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
    
	$("#noviMenjac").submit(function( event ) {
		event.preventDefault();
		
		NewMenjac();
		  
	});
	
});