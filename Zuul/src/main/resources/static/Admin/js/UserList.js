function RenderHtmlOnSuccess() {

    $.get({
		
		url:'/auth/user',
		contentType: 'application/json',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(result)
		{	
			
			var data = result;

		
			var html = '<table id="tabelaUsers" class="display" ><thead><tr><th>Korisnik ID</th><th>Korisničko ime</th><th>E-mail</th><th>Prihvaceni</th><th>Blokirani</th><th>Obrisani</th><th>Prihvati</th><th>Blokiraj</th><th>Obriši</th><tbody>';
			data.forEach((item)=>{
				
				
				html+='<tr>';
				  
				  html+='<td>';
				  html+=item.id;
				  html+='</td>';
				  
				  html+='<td>';
				  html+=item.username;
				  html+='</td>';
				  
				  html+='<td>';
				  html+=item.email;
				  html+='</td>';
				  
				  if(item.accepted == true)
				  {
					  html+='<td>';
					  html+="DA";
					  html+='</td>';
				  }
				  else
				  {
					  html+='<td>';
					  html+="NE";
					  html+='</td>';
				  }
				  
				  if(item.blocked == true)
				  {
					  html+='<td>';
					  html+="DA";
					  html+='</td>';
				  }
				  else
				  {
					  html+='<td>';
					  html+="NE";
					  html+='</td>';
				  }
				  
				  if(item.deleted == true)
				  {
					  html+='<td>';
					  html+="DA";
					  html+='</td>';
				  }
				  else
				  {
					  html+='<td>';
					  html+="NE";
					  html+='</td>';
				  }
				  
				  html+='<td>';
				  html+='<input type="button" class="prihvatiButton" value="Prihvati">';
				  html+='</td>';
				  
				  html+='<td>';
				  html+='<input type="button" class="blokirajButton" value="Blokiraj/Odblokiraj">';
				  html+='</td>';
				  
				  html+='<td>';
				  html+='<input type="button" class="obrisiButton" value="Obriši">';
				  html+='</td>';
				  
				  html+='</tr>';
				
				  
			});
			
		    html += '</tbody></table>';

			
		    $(html).appendTo('#divTabela');

		   
		    var table = $('#tabelaUsers').dataTable({
		        "pagingType": "full_numbers",
		        select: false
		    });
		    
		    
		    //prihvatanje
		    $('.prihvatiButton').on("click", function () {
		    	 var data = table.api().row( $(this).parents('tr') ).data();
		    	 var uid = data[0];
		    	 
		    	 var obrisan = data[5]
		    	 
		    	 if(obrisan == "NE")
		    	 {
		    		 $.ajax({
			    			
			    			url:'/auth/user/' + uid + '/activate',
			    			type:"PUT",
			    			headers: {
			    		        'Auth': 'Bearer ' + token
			    		    },
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
		    	 else
		    	 {
		    		 alert("Ne možete prihvatiti obrisanog korisnika.");
		    	 }
		    	 
			});
		    
		    //blokiranje i odblokiranje
		    $('.blokirajButton').on("click", function () {
		    	 var data = table.api().row( $(this).parents('tr') ).data();
		    	 Id = data[0];
		    	 var status = data[4];
		    	 var obrisan = data[5];
		    	 
		    	 if(obrisan == "NE")
		    	 {
		    		 if(status == "NE")
			    	 {
			    		 $.ajax({
				    			
				    			url:'/auth/user/'+Id+'/block',
				    			type:"PUT",
				    			headers: {
				    		        'Auth': 'Bearer ' + token
				    		    },
				    			contentType: 'application/json',
				    			success: function(result)
				    			{	
				    				location.reload(); 
				    			},
				    			error: function(result)
				    			{
				    				alert("Greska pri blokiranju korisnika.")
				    			}
				    	 });	
			    	 }
			    	 else if(status == "DA")
			    	 {
			    		 $.ajax({
				    			
				    			url:'/auth/user/'+Id+'/unblock',
				    			type:"PUT",
				    			headers: {
				    		        'Auth': 'Bearer ' + token
				    		    },
				    			contentType: 'application/json',
				    			success: function(result)
				    			{	
				    				location.reload(); 
				    			},
				    			error: function(result)
				    			{
				    				alert("Greska pri odblokiranju korisnika.")
				    			}
				    	 });	
			    	 }	 
		    	 }
		    	 else
		    	 {
		    		 alert("Ne možete blokirati/odblokirati obrisanog korisnika.");
		    	 }
		    	 
			});
		    
		    //brisanje
		    $('.obrisiButton').on("click", function () {
		    	 var data = table.api().row( $(this).parents('tr') ).data();
		    	 var uid = data[0];
		    	 
		    	 $.ajax({
		    			
		    			url:'/auth/user/' + uid + '/delete',
		    			type:"PUT",
		    			headers: {
		    		        'Auth': 'Bearer ' + token
		    		    },
		    			contentType: 'application/json',
		    			success: function(result)
		    			{	
		    				location.reload(); 
		    			},
		    			error: function(result)
		    			{
		    				alert('Greska pri brisanju korisnika.');
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
    
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return; // treba ga vratiti na login.html
	}
	
	RenderHtmlOnSuccess();

});