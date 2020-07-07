var params = new URL(location.href).searchParams;
var oglasId = params.get("oglasId");

image1global = null;
image2global = null;
image3global = null;


$(document).ready(function() {
	
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
		updateOglas();
	});
	
	if(sessionStorage.getItem("token")) {
		token = JSON.parse(sessionStorage.token);
		whoami();
	} else {
		console.log("No token in session memory...");
		window.location = "../login.html";
	}
	$.get({
		url: '/oglasi/oglas/'+oglasId,
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



function updateOglas() {
	
	var mesto =  $('#select-mesto :selected').val();
	var marka = $('#select-marka :selected').val();
	var model = $('#select-model :selected').val();
	var menjac =  $('#select-menjac :selected').val();
	var gorivo = $('#select-gorivo :selected').val();
	var klasa = $('#select-klasa :selected').val();
	var cenovnik = $('#select-cenovnik :selected').val();
	
	var cena = $('#input-cena').val();
	var kilometraza = $('#input-kilometraza').val();
	var planiranaKilometraza = $('#input-planirana-kilometraza').val();
	var osiguranje = false;
	var brSedistaZaDecu = $('#select-brSedistaZaDecu').val();
	
//	if(osiguranje === "1") {
//		osiguranje = true;
//	} else {
//		osifuranje = false;
//	}
	if(image1global != null){
	var image1 = image1global;
	}
	else{ 
	image1 =imageGlobal1;
	}

	if(image2global != null){
		var image2 = image2global;
		}
		else{ 
	image2 =imageGlobal2;
	}
	if(image3global != null){
		var image3 = image3global;
		}
		else{ 
	image3 =imageGlobal3;
	}
	
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
	var obj = JSON.stringify(
			{mesto, marka, model, menjac, gorivo, klasa, cena, kilometraza, planiranaKilometraza, osiguranje, brSedistaZaDecu, Od, Do, slike}
	);
	
	console.log("mesto:"+mesto+ "\nmarka:" + marka + "\nmodel:"+model+ "\nmenjac:"+menjac+ "\ngorivo:"+gorivo+ "\nklasa"+klasa);
	console.log("cena:"+cena+ "\nkilometraza:" + kilometraza + "\nplaniranaKilometraza:"+planiranaKilometraza+ "\nosiguranje:"+osiguranje+ "\nbrSedistaZaDecu:"+brSedistaZaDecu);
	
	
	$.ajax({
		url: '/oglasi/oglas/'+oglasId,
		type: 'PUT',
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
			alert("Uspešno ste izmenili oglas");
		},
		error: function(message) {
			alert("Greska pri izmeni!!");
            console.log("Error: ", message);
        }
	});
}






function ispisiOglas(o){
	
	popuniMesta(o.mesto);
	popuniMarke(o.marka);
	popuniModele(o.model);
	popuniKLase(o.klasa);
	popuniGoriva(o.gorivo);
	popuniMenjac(o.menjac);
	popuniCenovnik(o.cenovnik);
	
	
	$("#previewImage1").attr('src', o.slikeString[0]);
	$("#previewImage2").attr('src', o.slikeString[1]);
	$("#previewImage3").attr('src', o.slikeString[2]);
	imageGlobal1 = o.slikeString[0];
	imageGlobal2 = o.slikeString[1];
	imageGlobal3 = o.slikeString[2];
	$("#input-kilometraza").val(o.kilometraza);
	$("#input-planirana-kilometraza").val(o.planiranaKilometraza);
	$("#select-brSedistaZaDecu").val(o.brSedistaZaDecu);
	$("#input-od").val(o.od);
	$("#input-do").val(o.do);
	
	
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

function popuniMesta(oglasMesto) {
	$.get({
		url: '/sifrarnik/mesto',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(mesta){
			
			var selectMesto = $("#select-mesto");
			
			for(var mesto of mesta){
				if(mesto.name == oglasMesto){
				selectMesto.append('<option value="'+mesto.id+'"selected>'+mesto.name+'</option>');
				}else{
				selectMesto.append('<option value="'+mesto.id+'">'+mesto.name+'</option>');
				}
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniMarke(oglasMarka) {
	$.get({
		url: '/sifrarnik/marka',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(marke){
			
			var selectMarka = $("#select-marka");
			
			for(var marka of marke){
				if(marka.name == oglasMarka){
				selectMarka.append('<option value="'+marka.id+'"selected>'+marka.name+'</option>');	
				}else{
				selectMarka.append('<option value="'+marka.id+'">'+marka.name+'</option>');
				}
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniModele(oglasModel) {
	$.get({
		url: '/sifrarnik/model',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(modeli){
			
			var selectModel = $("#select-model");
		
			for(var model of modeli){
				if(model.name == oglasModel){
				selectModel.append('<option value="'+model.id+'"selected>'+model.name+'</option>');	
				}else{
				selectModel.append('<option value="'+model.id+'">'+model.name+'</option>');
				}
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniKLase(oglasKlasa) {
	$.get({
		url: '/sifrarnik/klasa',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(klase){
			
			var selectKlasa = $("#select-klasa");
		
			for(var klasa of klase){
				if(klasa.name == oglasKlasa){
				selectKlasa.append('<option value="'+klasa.id+'"selected>'+klasa.name+'</option>');	
				}else{
				selectKlasa.append('<option value="'+klasa.id+'">'+klasa.name+'</option>');
				}
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniGoriva(oglasGorivo) {
	$.get({
		url: '/sifrarnik/gorivo',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(goriva){
			
			var selectGorivo = $("#select-gorivo");
		
			for(var gorivo of goriva){
				if(gorivo.name == oglasGorivo){
				selectGorivo.append('<option value="'+gorivo.id+'"selected>'+gorivo.name+'</option>');	
				}else{
				selectGorivo.append('<option value="'+gorivo.id+'">'+gorivo.name+'</option>');
				}
			}
				
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}

function popuniMenjac(oglasMenjac) {
	$.get({
		url: '/sifrarnik/menjac',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(menjaci){
			
			var selectMenjac = $("#select-menjac");
		
			for(var menjac of menjaci){
				if(menjac.name == oglasMenjac){
				selectMenjac.append('<option value="'+menjac.id+'"selected>'+menjac.name+'</option>');
				}else{
				selectMenjac.append('<option value="'+menjac.id+'">'+menjac.name+'</option>');
				}
			}
			
		},
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
        }
	});
}
function popuniCenovnik(oglasCenovnik) {
	$.get({
		url: '/sifrarnik/cenovnik',
		headers: {
	        'Auth': 'Bearer ' + token
	    },
		contentType: 'application/json',
		success: function(cenovnici){
			if(cenovnici.length == 0){
				alert("Nemate unetih cenovnik!Molimo vas da prvo unesete cenovnik");
				window.location = "./cenovnik-add.html";
			}
			var selectCenovnik = $("#select-cenovnik");
		
			for(var cen of cenovnici){
				if(cen.name == oglasCenovnik){
				selectCenovnik.append('<option value="'+cen.id+'"selected>'+cen.name+'</option>');
				}else{
				selectCenovnik.append('<option value="'+cen.id+'">'+cen.name+'</option>');
				}
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