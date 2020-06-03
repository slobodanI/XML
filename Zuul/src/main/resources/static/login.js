$(document).ready(function() {	
//	koJeUlogovan();
	
	$('#btn-whoAmI').click(function(event) {
		
		if(sessionStorage.getItem("token")) {
			token = JSON.parse(sessionStorage.token);
		} else {
			console.log("No token in session memory...");
			return;
		}
		console.log(token);
		
		$.get({
			url: 'auth/whoami',
			headers: {
		        'Auth': 'Bearer ' + token
		    },
			success: function(retObject) {
				console.log(retObject);
				
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
			url: 'auth/login',														
			data: JSON.stringify({username, password}),
			contentType: 'application/json',
			success: function(retObject) {
				console.log("Access Token:" + retObject.accessToken);
				console.log("Token expires in:" + retObject.expiresIn + "miliseconds");
				
				sessionStorage.setItem('token', JSON.stringify(retObject.accessToken));
			},
			error: function() {
				alert("Neuspešno ste se prijavili");
			}
		});
	});
	
});


function koJeUlogovan() {
	$.get({
		url : 'api/whoIsLoggedIn',
//		contentType : 'application/json',
		success : function(user) {
			if (user != undefined) {
				if (user.uloga == "Pacijent") {
					window.location = "./PacijentHome.html";
				} else if (user.uloga == "AdministratorKlinickogCentra") {
				//	window.location = "./AdminKlinickogCentraHome.html";
					proveriAKC(user.id);
				} else if (user.uloga == "AdministratorKlinike") {
					proveriAK(user.id);
			//		window.location = "./AdministratorKlinikeHome.html";
				} else if (user.uloga == "Lekar") {
					proveriLekara(user.id);
				//	window.location = "./profilLekara.html";
				} else if (user.uloga == "MedicinskaSestra") {
					proveriMS(user.id)
				//	window.location = "./MedicinskaSestraHome.html";
				} else {
					console.log("NIKO NIJE ULOGOVAN");
				//	window.location = "./index.html";
				}
				
			} else {
				console.log("NIKO NIJE ULOGOVAN");
			//	window.location = "./index.html";
			}

		}
	});
}


