$(document).ready(function() {	
	
	$("#form-add-user").submit(function(event) {
		event.preventDefault();
		addOglas();
	});
	
});


function addOglas() {

	var username = $('#input-username').val();
	var firstname = $('#input-firstname').val();
	var lastname = $('#input-lastname').val();
	var email = $('#input-email').val();
	var password = $('#input-password').val();

	var obj = JSON.stringify(
			{username, firstname, lastname, email, password}
	);
	
	
	$.post({
		url: '/auth/signup',
		data: JSON.stringify(
				{username, firstname, lastname, email, password}
				),
		contentType: 'application/json',
		success: function(){
			alert("Uspešno ste se registrovali");
		},
		error: function(message) {
			alert("Password not correct!");
            console.log("Error: ", message);
        }
	});
}