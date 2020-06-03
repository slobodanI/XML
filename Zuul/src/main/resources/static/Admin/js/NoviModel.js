function NewModel()
{
	var info = $("#nazivTxt").val();

	$.post
	({
		url: '/sifrarnik/model',
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
    
	$("#noviModel").submit(function( event ) {
		event.preventDefault();
		
		NewModel();
		  
	});
	
});