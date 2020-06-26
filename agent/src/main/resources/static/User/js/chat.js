var params = new URL(location.href).searchParams;
var chatId = params.get("chatId");

$(document).ready(function(){
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	
	if(chatId == undefined) {
		// window.history.go(-1); // ovo ne radi ako se otvori novi tab bez chatId
		window.location = "../login.html";
	}
	
	getChat();
	
	$("#form-send-message").submit(function(event) {
		event.preventDefault();
		posaljiPoruku();
	});
	
});

function getChat() {
	$.get({
		url: '/chat/'+chatId,
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(chat){
			popuniPoruke(chat);
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
            // nije moj chat, vrati ga na login?
        }
	});
}

function popuniPoruke(chat) {
	
	var dl = $("<dl> </dl>")
	
	for(var poruka of chat.poruke){
		
		var dt = $("<dt> </dt>");
		var date = poruka.timestamp.split("T");
		var time = date[1].split(".");
		dt.append("Datum: " + date[0] + ", Vreme: " + time[0] + ", poslao: " + poruka.senderUsername+":");
		
		var dd = $("<dd> </dd>");
		dd.append("________________________________________________________"+poruka.body);
		
		dl.append(dt).append(dd);
		
		$("#divTabela").append(dl);
	}	
}

function posaljiPoruku() {
	
	var body = $("#input-message").val();
	
	$.post({
		url: '/chat/'+ chatId + '/poruka',
		data: JSON.stringify({body}),
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(){
			window.location = "./chat.html?chatId="+chatId;
		},
		error: function() {
            alert("Poruka mora sadržati između 1 i 50 karaktera!");
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
