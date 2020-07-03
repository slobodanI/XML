$(document).ready(function() {	
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	$("#form-add-cenovnik").submit(function(event) {
		event.preventDefault();
		addCenovnik();
	});
	
});

function addCenovnik() {

	var name = $('#input-name').val();
	var cenaZaDan = $('#input-cenaZaDan').val();
	var cenaPoKilometru = $('#input-cenaPoKilometru').val();
	var cenaOsiguranja = $('#input-cenaOsiguranja').val();
	var popust = $('#input-popust').val();
	var zaViseOd = $('#input-zaViseOd').val();
	
	var obj = JSON.stringify(
			{cenaZaDan, cenaPoKilometru, cenaOsiguranja, popust, zaViseOd, name}
	);
	
	
	$.post({
		url: '/cenovnik',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		data: JSON.stringify(
				{cenaZaDan, cenaPoKilometru, cenaOsiguranja, popust, zaViseOd, name}
				),
		contentType: 'application/json',
		success: function(){
			alert("Uspešno ste dodali cenovnik");
		},
		error: function(message) {
			alert("Greska!");
            console.log("Error: ", message);
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
			if(ROLES.includes("ROLE_USER") || ROLES.includes("ROLE_USER_LIMITED") || ROLES.includes("ROLE_AGENT") ) {
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