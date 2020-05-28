package rs.xml.oglas.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.oglas.dto.OglasDTOsearch;
import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.repository.OglasRepository;

@Service
public class OglasService {

	@Autowired
	OglasRepository oglasRepository;

	public Oglas findOne(Long id) {
		Optional<Oglas> oglas = oglasRepository.findById(id);
		return oglas.orElseGet(null);
	}

	public List<Oglas> findAll() {
		return oglasRepository.findAll();
	}

	public Page<Oglas> findAll(Pageable page) {
		return oglasRepository.findAll(page);
	}

	public Oglas save(Oglas oglas) {
		return oglasRepository.save(oglas);
	}

	public void remove(Long id) {
		oglasRepository.deleteById(id);
	}

	public Collection<OglasDTOsearch> search(String mesto, Date odDate, Date doDate, String marka, String model,
											 String menjac, String gorivo, String klasa, int predjenaInt, int planiranaInt,
											 String osiguranje, int brSedZaDecuInt) {
		
		Collection<OglasDTOsearch> ret = new ArrayList<OglasDTOsearch>();
		Encoder encoder = Base64.getEncoder();
		String imageString;
		
		java.sql.Date odDateOVAJ = new java.sql.Date(odDate.getTime());
		java.sql.Date doDateOVAJ = new java.sql.Date(doDate.getTime());
				
		Collection<Oglas> oglasi = oglasRepository.findAll();
//		System.out.println("****** ukupno oglasa: " + oglasi.size());
		
		for(Oglas oglas: oglasi) {
			
			boolean flag = true;
			
			if(!oglas.getMesto().contains(mesto)) {
//				System.out.println("---Mesto nije dobro");
				flag = false;
				continue;
			}
			
			if(odDateOVAJ.before(oglas.getOd()) || doDateOVAJ.before(oglas.getOd()) || odDateOVAJ.after(oglas.getDo()) || doDateOVAJ.after(oglas.getDo())) {				
//				System.out.println("---date nije dobro");
				flag = false;
				continue;
			}
		
			if(!marka.equals("nema")) {
				if(!oglas.getMarka().contains(marka)) {
//					System.out.println("---marka nije dobro");
					flag = false;
					continue;
				}
			}
			
			if(!model.equals("nema")) {
				if(!oglas.getModel().contains(model)) {
//					System.out.println("---model nije dobro");
					flag = false;
					continue;
				}
			}
			
			if(!menjac.equals("nema")) {
				if(!oglas.getMenjac().contains(menjac)) {
//					System.out.println("---menjac nije dobro");
					flag = false;
					continue;
				}
			}
			
			if(!gorivo.equals("nema")) {
				if(!oglas.getGorivo().contains(gorivo)) {
//					System.out.println("---gorivo nije dobro");
					flag = false;
					continue;
				}
			}
			
			if(!klasa.equals("nema")) {
				if(!oglas.getKlasa().contains(klasa)) {
//					System.out.println("---klasa nije dobro");
					flag = false;
					continue;
				}
			}
			
			if(predjenaInt != 0) {
				if(oglas.getKilometraza() > predjenaInt) {
					flag = false;
					continue;
				}
			}
			
			if(planiranaInt != 0) {
				if(oglas.getPlaniranaKilometraza() < planiranaInt) {
					flag = false;
					continue;
				}
			}
			
			if(!osiguranje.equals("nema")) {
				if(oglas.isOsiguranje() != Boolean.parseBoolean(osiguranje)) {
					flag = false;
					continue;
				}
			}
			
			if(brSedZaDecuInt != 0) {
				if(oglas.getSedistaZaDecu() < brSedZaDecuInt) {
					flag = false;
					continue;
				}
			}
			
			if(flag == true){				
				OglasDTOsearch oglasDTO = new OglasDTOsearch();
				oglasDTO.setId(oglas.getId());
				oglasDTO.setMesto(oglas.getMesto());
				oglasDTO.setMarka(oglas.getMarka());
				oglasDTO.setModel(oglas.getModel());
				oglasDTO.setMenjac(oglas.getMenjac());
				oglasDTO.setGorivo(oglas.getGorivo());
				oglasDTO.setKlasa(oglas.getKlasa());
				oglasDTO.setPredjenaInt(oglas.getKilometraza());
				oglasDTO.setPlaniranaInt(oglas.getPlaniranaKilometraza());
				oglasDTO.setOsiguranjeBool(oglas.isOsiguranje());
				oglasDTO.setBrSedZaDecuInt(oglas.getSedistaZaDecu());
				oglasDTO.setOdDate(oglas.getOd());
				oglasDTO.setDoDate(oglas.getDo());
				
				if(oglas.getSlike().isEmpty()) {
					oglasDTO.setSlika(null);
				} else {				
					imageString = encoder.encodeToString(oglas.getSlike().get(0).getSlika());
					oglasDTO.setSlika("data:image/jpeg;base64," + imageString);
				}
				
				ret.add(oglasDTO);
			}
		}
		
		return ret;
	}
}
