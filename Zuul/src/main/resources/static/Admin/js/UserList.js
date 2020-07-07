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

		
			var html = '<table id="tabelaUsers" class="display" ><thead><tr><th>Korisnik ID</th><th>Korisničko ime</th><th>E-mail</th><th>Prihvaceni</th><th>Blokirani</th><th>Obrisani</th><th>Prihvati</th><th>Blokiraj</th><th>Dodavanje oglasa</th><th>Slanje zahteva</th><th>Obriši</th><tbody>';
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
				  if(item.accepted == false)
				  {
					  html+='<input type="button" class="prihvatiButton" value="Prihvati">';
				  }	  
				  html+='</td>';
				  
				  html+='<td>';
				  html+='<input type="button" class="blokirajButton" value="Blokiraj/Odblokiraj">';
				  html+='</td>';
				  
				  if(item.blockedPostavljanjeOglasa == false){
					  html+='<td>';
					  html+='<input type="button" class="blokirajOglaseButton" value="Blokiraj">';
					  html+='</td>';
				  }else{
					  html+='<td>';
					  html+='<input type="button" class="odblokirajOglaseButton" value="Odblokiraj">';
					  html+='</td>';
				  }
				  
				  if(item.blockedSlanjeZahteva == false){
					  html+='<td>';
					  html+='<input type="button" class="blokirajZahteveButton" value="Blokiraj">';
					  html+='</td>';
				 }else{
					  html+='<td>';
					  html+='<input type="button" class="odblokirajZahteveButton" value="Odblokiraj">';
					  html+='</td>';
					  }
				  
				  
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
		    			
		    			url:'/auth/user/' + uid,
		    			type:"DELETE",
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
		    
		    $('.blokirajOglaseButton').on("click", function () {
		    	 var data = table.api().row( $(this).parents('tr') ).data();
		    	 var uid = data[0];
		    	 
		    	 $.ajax({
		    			
		    			url:'/auth/user/' + uid + '/blockOglase',
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
		    				alert('Greska pri blokiranju korisnika.');
		    			}
		    	 });	
		    	 
			});
		    
		    $('.odblokirajOglaseButton').on("click", function () {
		    	 var data = table.api().row( $(this).parents('tr') ).data();
		    	 var uid = data[0];
		    	 
		    	 $.ajax({
		    			
		    			url:'/auth/user/' + uid + '/unblockOglase',
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
		    				alert('Greska pri blokiranju korisnika.');
		    			}
		    	 });	
		    	 
			});
		    
		    $('.blokirajZahteveButton').on("click", function () {
		    	 var data = table.api().row( $(this).parents('tr') ).data();
		    	 var uid = data[0];
		    	 
		    	 $.ajax({
		    			
		    			url:'/auth/user/' + uid + '/blockZahteve',
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
		    				alert('Greska pri blokiranju korisnika.');
		    			}
		    	 });	
		    	 
			});
		    
		    $('.odblokirajZahteveButton').on("click", function () {
		    	 var data = table.api().row( $(this).parents('tr') ).data();
		    	 var uid = data[0];
		    	 
		    	 $.ajax({
		    			
		    			url:'/auth/user/' + uid + '/unblockZahteve',
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
		    				alert('Greska pri blokiranju korisnika.');
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
		url: '/auth/whoami',
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

});