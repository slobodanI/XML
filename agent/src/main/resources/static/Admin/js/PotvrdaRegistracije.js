var params = new URL(location.href).searchParams;
var userID = params.get("userID");

$(document).ready(function() {
	
//	if(sessionStorage.getItem("token")) {
//		token = JSON.parse(sessionStorage.token);
//	} else {
//		console.log("No token in session memory...");
//		return; // treba ga vratiti na login.html
//	}
	
	$("#p-pozitivno").hide();
	$("#p-greska").hide();
	
	//ako su svi parametri iz url-a validni
	if(userID != undefined) {
		if(userID != "") {
		
			potvrdaRegistracije();	

		} else {
			$("#p-greska").show();
		}
	} else {
		$("#p-greska").show();
	}
	
});


function potvrdaRegistracije() {
	$.ajax({
		url: '/user/'+ userID + "/mailActivate",
		type: 'PUT',
		success: function() {

				$("#p-pozitivno").show();
			
		},
		error: function(){
			$("#p-greska").show();
		}
	});
} 
