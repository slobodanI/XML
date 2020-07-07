$(document).ready(function(){
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	
	$(".div-form-edit").hide();		
	$("#btn-prikazForme").click(function() {
		$(".div-form-edit").toggle();		
	});
	
	$(".div-form-editAgent").hide();		
	$("#btn-prikazFormeAgent").click(function() {
		$(".div-form-editAgent").toggle();		
	});
	
	$("#form-add-user").submit(function(event) {
		event.preventDefault();
		updateProfil();
	});
	
	$("#form-update-agent").submit(function(event) {
		event.preventDefault();
		updateProfilAgent();
	});
	
	
});

function getMyPodaci() {
	$.get({
		url: '/auth/user/me',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(user){
			$("#ime").empty();
			$("#prezime").empty();
			$("#email").empty();
			$("#companyName").hide();
			$("#adress").hide();
			$("#pib").hide();
			
			$("#ime").append("Ime: " + user.firstname);
			$("#prezime").append("Prezime: " + user.lastname);
			$("#email").append("Email: " + user.email);
			
			
			$("#input-firstname").val(user.firstname);
			$("#input-lastname").val(user.lastname);
			$("#input-email").val(user.email);
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}


function getMyPodaciAgent() {
	$.get({
		url: '/auth/user/me',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(user){
			$("#ime").empty();
			$("#prezime").empty();
			$("#companyName").empty();
			$("#adress").empty();
			$("#pib").empty();
			$("#email").hide();
			
			$("#ime").append("Ime: " + user.firstname);
			$("#prezime").append("Prezime: " + user.lastname);
			$("#companyName").append("Naziv kompanije: " + user.companyName);
			$("#adress").append("Adresa: " + user.adress);
			$("#pib").append("Pib: " + user.pib);
			
			
			$("#input-firstnameAgent").val(user.firstname);
			$("#input-lastnameAgent").val(user.lastname);
			$("#input-companyName").val(user.companyName);
			$("#input-adress").val(user.adress);
			$("#input-pib").val(user.pib)
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}

function updateProfil() {


	var firstname = $('#input-firstname').val();
	var lastname = $('#input-lastname').val();
	var email = $('#input-email').val();

	var obj = JSON.stringify(
			{firstname, lastname, email}
	);
	
	
	$.ajax({
		url: '/auth/user',
		type: 'PUT',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		data: JSON.stringify(
				{firstname, lastname, email}
				),
		contentType: 'application/json',
		success: function(){
			alert("Uspešno ste izmenili podatke");
			window.location = "./UserProfil.html";
			
		},
		error: function(message) {
			alert("Password not correct!");
            console.log("Error: ", message);
        }
	});
}

function updateProfilAgent() {


	var firstname = $('#input-firstnameAgent').val();
	var lastname = $('#input-lastnameAgent').val();
	var companyName = $('#input-companyName').val();
	var adress = $('#input-adress').val();
	var pib = $('#input-pib').val();

	var obj = JSON.stringify(
			{firstname, lastname, companyName,adress,pib}
	);
	
	
	$.ajax({
		url: '/auth/agent',
		type: 'PUT',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		data: JSON.stringify(
				{firstname, lastname, companyName,adress,pib}
				),
		contentType: 'application/json',
		success: function(){
			alert("Uspešno ste izmenili podatke");
			window.location = "./UserProfil.html";
			
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
			if(ROLES.includes("ROLE_AGENT")){
				getMyPodaciAgent();
				$("#btn-prikazForme").hide();
			}else {
				getMyPodaci();
				$("#btn-prikazFormeAgent").hide();
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

