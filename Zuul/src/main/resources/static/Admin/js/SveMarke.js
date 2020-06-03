var Id;

function Promeni()
{
	var info = $('#nazivTxt').val();
	
	$.ajax({
		
		url:'/sifrarnik/marka/'+Id,
		data:info,
		type:"PUT",
		contentType: 'application/json',
		success: function(result)
		{	
			location.reload(); 
		},
		error: function(result)
		{
			alert('Takav indeks ne postoji.');
		}
 });	
}

function RenderHtmlOnSuccess() {
    
    $.get({
		
		url:'/sifrarnik/marka',
		contentType: 'application/json',
		success: function(result)
		{	
		
			var data = result;
			
		
			var html = '<table id="tabelaMarka" class="display" ><thead><tr><th>Id goriva</th><th>Naziv goriva</th><th>Promeni naziv</th><th>Obriši</th><tbody>';
			data.forEach((item)=>{
				
				
				  html+='<tr>';
				  
				  html+='<td>';
				  html+=item.id;
				  html+='</td>';
				  
				  html+='<td>';
				  html+=item.name;
				  html+='</td>';
				  
				  html+='<td>';
				  html+='<input type="button" class="promeniButton" value="Promeni naziv">';
				  html+='</td>';
				  
				  html+='<td>';
				  html+='<input type="button" class="obrisiButton" value="Obriši">';
				  html+='</td>';
				  
				  html+='</tr>';
				
				  
			});
			
		    html += '</tbody></table>';

			
		    $(html).appendTo('#divTabela');

		   
		    var table = $('#tabelaMarka').dataTable({
		        "pagingType": "full_numbers",
		        select: false
		    });
		    
		    //promena
		    $('.promeniButton').on("click", function () {
		    	 var data = table.api().row( $(this).parents('tr') ).data();
		    	 Id = data[0];
		    	 
		    	 $('#putMarka').show();
			});
		    
		    //brisanje
		    $('.obrisiButton').on("click", function () {
		    	 var data = table.api().row( $(this).parents('tr') ).data();
		    	 Id = data[0];
		    	 
		    	 $.ajax({
		    			
		    			url:'/sifrarnik/marka/'+Id,
		    			type:"DELETE",
		    			contentType: 'application/json',
		    			success: function(result)
		    			{	
		    				location.reload(); 
		    			},
		    			error: function(result)
		    			{
		    				
		    			}
		    	 });	
			});
		    
		    $('.dataTables_length').addClass('bs-select');
		    
		},
		error: function()
		{
			alert('Greška');
		}
		
	});

}

$( document ).ready(function() {
    
	RenderHtmlOnSuccess();
	$('#putMarka').hide();
	
	$("#putMarka").submit(function( event ) {
		event.preventDefault();
		
		Promeni();
		  
	});
	
});