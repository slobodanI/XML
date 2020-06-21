$(document).ready(function(){
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
	} else {
		console.log("No token in session memory...");
		return; // treba ga vratiti na login.html
	}
	
	
});

function myFunction() {
	$.get({
		url: '/sync',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(){
			alert("Uspeh");
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}