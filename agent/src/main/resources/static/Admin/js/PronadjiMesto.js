function Pronadji()
{
	
	
 	var Id = $('#sifraTxt').val();

 	
 	
	 $.get({
			
			url:'/mesto/'+Id,
			headers: {
		        'Auth': 'Bearer ' + token
		    },
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
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return; // treba ga vratiti na login.html
	}
    
	$("#pronMesto").submit(function( event ) {
		event.preventDefault();
		
		Pronadji();
		  
	});
	
});