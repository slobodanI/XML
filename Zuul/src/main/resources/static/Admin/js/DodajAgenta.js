$(document).ready(function() {	
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	
	$("#form-add-user").submit(function(event) {
		event.preventDefault();
		addAgent();
	});
	
});


function addAgent() {

	var username = $('#input-username').val();
	var firstname = $('#input-firstname').val();
	var lastname = $('#input-lastname').val();
	var companyName = $('#input-companyName').val();
	var password = $('#input-password').val();
	var adress = $('#input-adress').val();
	var pib = $('#input-pib').val();
	
	var obj = JSON.stringify(
			{username, firstname, lastname, companyName, password, adress, pib}
	);
	
	
	$.post({
		url: '/auth/agent',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		data: JSON.stringify(
				{username, firstname, lastname, companyName, password, adress, pib}
				),
		contentType: 'application/json',
		success: function(){
			alert("Uspešno ste registrovali novog agenta!");
		},
		error: function(message) {
			alert("Password not correct!");
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
