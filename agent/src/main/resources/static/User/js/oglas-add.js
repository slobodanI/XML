$(document).ready(function() {
//	https://github.com/slobodanI/psw-isa/blob/master/src/main/resources/static/js/PretragaLekaraPrekoKlinike.js
//	na linku ti je dodavanje u select

	
	$('input[name="image1"]').change(function() { // za sliku
		readImage1(this);
	});
	
	$('input[name="image2"]').change(function() { // za sliku
		readImage2(this);
	});
	
	$('input[name="image3"]').change(function() { // za sliku
		readImage3(this);
	});
	
	$("#form-add-oglas").submit(function(event) {
		event.preventDefault();
		addOglas();
	});
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	
	popuniMesta();
	popuniMarke();
	popuniModele();
	popuniKLase();
	popuniGoriva();
	popuniMenjac();
});

function addOglas() {
	var mesto =  $('#select-mesto :selected').val();
	var marka = $('#select-marka :selected').val();
	var model = $('#select-model :selected').val();
	var menjac =  $('#select-menjac :selected').val();
	var gorivo = $('#select-gorivo :selected').val();
	var klasa = $('#select-klasa :selected').val();
	
	var cena = $('#input-cena').val();
	var kilometraza = $('#input-kilometraza').val();
	var planiranaKilometraza = $('#input-planirana-kilometraza').val();
	var osiguranje = $('#select-osiguranje :selected').val();
	var brSedistaZaDecu = $('#select-brSedistaZaDecu').val();
	
	if(osiguranje === "1") {
		osiguranje = true;
	} else {
		osifuranje = false;
	}
	
	var image1 = image1global;
	var image2 = image2global;
	var image3 = image3global;
	
	var Od = $('#input-od').val();
	var Do = $('#input-do').val();
	
	var split1 = Od.split("-");
	var split2 = Do.split("-");
	
	if(split1[0] > split2[0]) {
		alert("OD datum mora biti pre DO datuma!");
		return;
	}
	if(split1[0] == split2[0] && split1[1] > split2[1]) {
		alert("OD datum mora biti pre DO datuma!");
		return;
	}
	if(split1[0] == split2[0] && split1[1] == split2[1] && split1[2] >= split2[2]) {
		alert("OD datum mora biti pre DO datuma!");
		return;
	}
	
	
	if(image1 == undefined || image2 == undefined || image3 == undefined) {
		return;
	}
	
	if(image1 == "" || image2 == "" || image3 == "") {
		return;
	}
	
	var slike = [];
	var slika = { slika: image1};
	slike.push(slika);
	slika = { slika: image2};
	slike.push(slika);
	slika = { slika: image3};
	slike.push(slika);
	var cenovnik = "neki cenovnik";
	var obj = JSON.stringify(
			{mesto, marka, model, menjac, gorivo, klasa, cena, kilometraza, planiranaKilometraza, osiguranje, brSedistaZaDecu, Od, Do, slike}
	);
	
	console.log("mesto:"+mesto+ "\nmarka:" + marka + "\nmodel:"+model+ "\nmenjac:"+menjac+ "\ngorivo:"+gorivo+ "\nklasa"+klasa);
	console.log("cena:"+cena+ "\nkilometraza:" + kilometraza + "\nplaniranaKilometraza:"+planiranaKilometraza+ "\nosiguranje:"+osiguranje+ "\nbrSedistaZaDecu:"+brSedistaZaDecu);
	
	
	$.post({
		url: '/oglas',
		data: JSON.stringify(
				{mesto, marka, model, menjac, gorivo, klasa, cena, kilometraza, planiranaKilometraza, osiguranje, brSedistaZaDecu, slike,
					cenovnik,
					"od": Od, "do": Do}
				),
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(){
			alert("Uspešno ste postavili oglas");
		},
		error: function(message) {
			alert("Već imate postavljena 3 oglasa!");
            console.log("Error: ", message);
        }
	});
}

function readImage1(input) { // za sliku
	if (input.files && input.files[0]) {
		var reader = new FileReader();
					
		reader.onload = function(e) {
			if(input.files[0].size > 200000) {
				alert("Vaša slika ima:" + input.files[0].size + "B, a dozvoljeno je do 200000B.");
			} else {
				$("#previewImage1").attr('src', e.target.result);
				image1global = e.target.result;
			}			
		}

		reader.readAsDataURL(input.files[0]);
	}
}

function readImage2(input) { // za sliku
	if (input.files && input.files[0]) {
		var reader = new FileReader();

		reader.onload = function(e) {
			if(input.files[0].size > 200000) {
				alert("Vaša slika ima:" + input.files[0].size + "B, a dozvoljeno je do 200000B.");
			} else {
				$("#previewImage2").attr('src', e.target.result);
				image2global = e.target.result;
			}			
		}

		reader.readAsDataURL(input.files[0]);
	}
}

function readImage3(input) { // za sliku
	if (input.files && input.files[0]) {
		var reader = new FileReader();
					
		reader.onload = function(e) {
			if(input.files[0].size > 200000) {
				alert("Vaša slika ima:" + input.files[0].size + "B, a dozvoljeno je do 200000B.");
			} else {
				$("#previewImage3").attr('src', e.target.result);
				image3global = e.target.result;
			}			
		}

		reader.readAsDataURL(input.files[0]);
	}
}


function popuniMesta() {
	$.get({
		url: '/mesto',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(mesta){
			
			var selectMesto = $("#select-mesto");
			
			for(var mesto of mesta){
				selectMesto.append('<option value="'+mesto.id+'">'+mesto.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniMarke() {
	$.get({
		url: '/marka',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(marke){
			
			var selectMarka = $("#select-marka");
			
			for(var marka of marke){
				selectMarka.append('<option value="'+marka.id+'">'+marka.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniModele() {
	$.get({
		url: '/model',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(modeli){
			
			var selectModel = $("#select-model");
		
			for(var model of modeli){
				selectModel.append('<option value="'+model.id+'">'+model.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniKLase() {
	$.get({
		url: '/klasa',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(klase){
			
			var selectKlasa = $("#select-klasa");
		
			for(var klasa of klase){
				selectKlasa.append('<option value="'+klasa.id+'">'+klasa.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniGoriva() {
	$.get({
		url: '/gorivo',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(goriva){
			
			var selectGorivo = $("#select-gorivo");
		
			for(var gorivo of goriva){
				selectGorivo.append('<option value="'+gorivo.id+'">'+gorivo.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniMenjac() {
	$.get({
		url: '/menjac',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(menjaci){
			
			var selectMenjac = $("#select-menjac");
		
			for(var menjac of menjaci){
				selectMenjac.append('<option value="'+menjac.id+'">'+menjac.name+'</option>');
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
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
			alert("Neuspešno ste se prijavili");
		}
	});
}
