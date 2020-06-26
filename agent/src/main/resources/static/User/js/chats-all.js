$(document).ready(function(){
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	
	getMyChats();
	
	
});

function getMyChats() {
	$.get({
		url: '/chat',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(chats){
			popuniTabelu(chats);
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
}

function popuniTabelu(chats) {
		
	var tableUDivu = $("<table id='tabelaChatova' class='display'> </table>");
	
	var thead = $("<thead> </thead>");
	thead.append("<tr><th>Chat ID</th><th>Prvi učesnik</th><th>Drugi učesnik</th><th>Prikaži chat</th></tr>")
	
	var tbody = $("<tbody id='teloTabele'> </tbody>");
	
	for(var chat of chats){
		var tr = $("<tr> </tr>")
		var tdId = $("<td> </td>");
		tdId.append(chat.id);
		var tdPrvi = $("<td> </td>");
		tdPrvi.append(chat.senderUsername);
		var tdDrugi = $("<td> </td>");
		tdDrugi.append(chat.receivereUsername);
		
		var tdPrikazi = $("<td> </td>");
		var btn = $('<button>Prikaži chat</button>');
		btn.click(prikaziChat(chat.id));
		tdPrikazi.append(btn);
		
		tr.append(tdId).append(tdPrvi).append(tdDrugi).append(tdPrikazi);
		tbody.append(tr);
	}
	
	tableUDivu.append(thead).append(tbody);
	$("#divTabela").append(tableUDivu);
	
	var table = $('#tabelaChatova').dataTable({
        "pagingType": "full_numbers",
        select: false
    });
	
}

function prikaziChat(chatId) {
	
	return function() {		
		window.location = "./chat.html?chatId=" + chatId;
	}	
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

