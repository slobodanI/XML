function Pronadji()
{
	
	
 	var Id = $('#sifraTxt').val();

 	
 	
	 $.get({
			
			url:'/sifrarnik/mesto/'+Id,
			contentType: 'application/json',
			success: function(result)
			{	
				$('#nazivTxt').val(result.name)
			},
			error: function(result)
			{
				alert("Taj indeks ne postoji.");
			}
	 });	
	 
}



$( document ).ready(function() {
    
	$("#pronMesto").submit(function( event ) {
		event.preventDefault();
		
		Pronadji();
		  
	});
	
});