var Id;

function Promeni()
{
	var info = $('#nazivTxt').val();
	
	$.ajax({
		
		url:'/menjac/'+Id,
		headers: {
	        'Auth': 'Bearer ' + token
	    },
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
		
		url:'/menjac',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(result)
		{	
		
			var data = result;
			
		
			var html = '<table id="tabelaMenjac" class="display" ><thead><tr><th>Id goriva</th><th>Naziv goriva</th><th>Promeni naziv</th><th>Obriši</th><tbody>';
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

		   
		    var table = $('#tabelaMenjac').dataTable({
		        "pagingType": "full_numbers",
		        select: false
		    });
		    
		    //promena
		    $('.promeniButton').on("click", function () {
		    	 var data = table.api().row( $(this).parents('tr') ).data();
		    	 Id = data[0];
		    	 
		    	 $('#putMenjac').show();
			});
		    
		    //brisanje
		    $('.obrisiButton').on("click", function () {
		    	 var data = table.api().row( $(this).parents('tr') ).data();
		    	 Id = data[0];
		    	 
		    	 $.ajax({
		    			
		    			url:'/menjac/'+Id,
		    			headers: {
		    		        'Auth': 'Bearer ' + token
		    		    },
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


function whoami() {
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return;
	}
	
	$.get({
		url: '/whoami',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(user) {
			var ROLES = "";
			for(var role of user.authorities){					
				ROLES += role.authority+","
			}
			if(ROLES.includes("ROLE_ADMIN")) {
				// 
			} else {
				window.location = "../login.html";
			}
			
		},
		error: function() {
			window.location = "../login.html";
		}
	});
}

$( document ).ready(function() {
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	  
	RenderHtmlOnSuccess();
	$('#putMenjac').hide();
	
	$("#putMenjac").submit(function( event ) {
		event.preventDefault();
		
		Promeni();
		  
	});
	
});