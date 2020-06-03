function NewMesto()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/sifrarnik/mesto',
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
    
	$("#novoMesto").submit(function( event ) {
		event.preventDefault();
		
		NewMesto();
		  
	});
	
});