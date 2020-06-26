$(document).ready(function() {	
	//whoami();
	
	// izbrisi token
	sessionStorage.removeItem('token');

	$('#btn-whoAmI').click(function(event) {
		
		if(sessionStorage.getItem("token")) {
			token = JSON.parse(sessionStorage.token);
		} else {
			console.log("No token in session memory...");
			return;
		}
		console.log(token);
		
		$.get({
			url: '/whoami',
			headers: {
		        'Auth': 'Bearer ' + token
		    },
			success: function(user) {
				console.log(user);
				var ROLES = "";
				for(var role of user.authorities){					
					ROLES += role.authority+","
				}
				console.log(ROLES);
			},
			error: function() {
				alert("Neuspešno ste se prijavili");
			}
		});
		
		
	}); 
	
	$('#logInForma').submit(function(event) {
		event.preventDefault();
		var username = $('input[name="username"]').val();
		var password = $('input[name="password"]').val();		

		$.post({
			url: '/login',														
			data: JSON.stringify({username, password}),
			contentType: 'application/json',
			success: function(retObject) {
				console.log("Access Token:" + retObject.accessToken);
				console.log("Token expires in:" + retObject.expiresIn + "miliseconds");
				
				sessionStorage.setItem('token', JSON.stringify(retObject.accessToken));
				redirect();
			},
			error: function() {
				alert("Neuspešno ste se prijavili");
			}
		});
	});
	
});

function redirect() {
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
			console.log(ROLES);
			if(ROLES.includes("ROLE_ADMIN")) {
				console.log("INCLUDES ROLE_ADMIN");
				window.location = "/Admin/Index.html"
			}
			if(ROLES.includes("ROLE_USER") || ROLES.includes("ROLE_USER_LIMITED") || ROLES.includes("ROLE_AGENT") ) {
				console.log("INCLUDES ROLE_USER or ROLE_USER_LIMITED or ROLE_AGENT");
				window.location = "/User/oglas-search.html"
			}
			
		},
		error: function() {
			alert("Neuspešno ste se prijavili");
		}
	});
}

