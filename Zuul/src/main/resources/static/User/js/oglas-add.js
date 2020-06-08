$(document).ready(function() {
//	https://github.com/slobodanI/psw-isa/blob/master/src/main/resources/static/js/PretragaLekaraPrekoKlinike.js
//	na linku ti je dodavanje u select
//	popuniMesta();
//	popuniMarke();
//	popuniModele();
//	popuniKLase();
//	popuniGoriva();
	
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
	} else {
		console.log("No token in session memory...");
		return;
	}
	
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
	var osiguranje = $('#select-klasa :selected').val();
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
	
	console.log("Split1[0]:" + split1[0]);
	console.log("Split1[1]:" + split1[1]);
	console.log("Split1[2]:" + split1[2]);
	
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
	$.post({
		url: '/oglasi/oglas',
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
		error: function(jqXhr, textStatus, errorMessage) {
            console.log("Error: ", errorMessage);
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