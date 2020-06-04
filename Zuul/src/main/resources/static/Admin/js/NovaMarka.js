function NewMarka()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/sifrarnik/marka',
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
    
	$("#novaMarka").submit(function( event ) {
		event.preventDefault();
		
		NewMarka();
		  
	});
	
});