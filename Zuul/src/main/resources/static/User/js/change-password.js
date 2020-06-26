$(document).ready(function() {	
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	
	$("#form-change-password").submit(function(event) {
		event.preventDefault();
		changePass();
	});
	
});


function changePass() {


	var oldPassword = $('#input-oldpassword').val();
	var newPassword = $('#input-newpassword').val();

	var obj = JSON.stringify(
			{oldPassword, newPassword}
	);
	
	
	$.post({
		url: '/auth/change-password',
		data: JSON.stringify(
				{oldPassword, newPassword}
				),
		headers: {
			        'Auth': 'Bearer ' + token
			    },
		contentType: 'application/json',
		success: function(){
			alert("Uspešno ste promenili sifru");
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