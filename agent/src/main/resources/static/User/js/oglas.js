var params = new URL(location.href).searchParams;
var oglasId = params.get("oglasId");

$(document).ready(function() {
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	$.get({
		url: '/oglas/'+oglasId,
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		success: function(oglas){
			ispisiOglas(oglas);
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", textStatus);
        }
	});
});




function ispisiOglas(o){
	$("#div-podaci").empty();
	if(o != undefined){
		var ulUDivu = $("<ul id='ulOglas' class = 'display'> </ul>");
		
		var liMarka = $("<li> </li>");
		liMarka.append("Marka: " + o.marka);
		var liModel = $("<li> </li>");
		liModel.append("Model :"+o.model);
		var liMesto = $("<li> </li>");
		liMesto.append("Mesto preuzimanja: "+o.mesto);
		var liKlasa = $("<li> </li>");
		liKlasa.append ("Klasa vozila: "+o.klasa);
		var liGorivo = $("<li> </li>");
		liGorivo.append("Gorivo: "+o.gorivo);
		var liMenjac = $("<li> </li>");
		liMenjac.append("Klasa: "+o.klasa);
		var liCena = $("<li> </li>");
		liCena.append("Cena: "+o.cena);
		var liKilometraza = $("<li> </li>");
		liKilometraza.append("Predjena kilometraza: "+o.kilometraza);
		var liOsiguranje = $("<li> </li>");
		if(o.osiguranje){
			liOsiguranje.append("Osiguranje: DA");
		}else{
			liOsiguranje.append("Osiguranje: NE");
		}
		var liSedistaZaDecu = $("<li> </li>");
		liSedistaZaDecu.append("Broj sedista za decu:"+o.brSedistaZaDecu);
		var liOd = $("<li> </li>");
		liOd.append("Iznajmljuje se od: "+o.od);
		var liDo = $("<li> </li>");
		liDo.append("Iznajmljuje se do: "+o.do);
		ulUDivu.append(liMarka).append(liModel).append(liMesto).append(liKlasa).append(liGorivo).append(liMenjac).append(liCena).append(liKilometraza).append(liOsiguranje).append(liSedistaZaDecu).append(liOd).append(liDo);
		var pom = 0;
		for(s of o.slikeString){
			var liSlika = $("<li id='pom"+pom+"'> </li>");
			var imgSlika = $('<img src="'+s+'" width="150" height="80">');
//			liSlika.append(imgSlika);
			ulUDivu.append(liSlika.append(imgSlika));
			$("#pom"+pom+"").append(imgSlika);
			pom++;
		}
		
		$("#div-podaci").append(ulUDivu);
	
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