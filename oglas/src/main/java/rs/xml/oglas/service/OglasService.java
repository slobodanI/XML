package rs.xml.oglas.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Base64.Encoder;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import rs.xml.oglas.client.GorivoDTO;
import rs.xml.oglas.client.KlasaDTO;
import rs.xml.oglas.client.MarkaDTO;
import rs.xml.oglas.client.MenjacDTO;
import rs.xml.oglas.client.MestoDTO;
import rs.xml.oglas.client.ModelDTO;
import rs.xml.oglas.client.SifrarnikClient;
import rs.xml.oglas.dto.NewOglasDTO;
import rs.xml.oglas.dto.OglasDTOsearch;
import rs.xml.oglas.exception.NotFoundException;
import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Zahtev;
import rs.xml.oglas.model.ZahtevStatus;
import rs.xml.oglas.repository.IzvestajRepository;
import rs.xml.oglas.repository.OcenaRepository;
import rs.xml.oglas.repository.OglasRepository;
import rs.xml.oglas.repository.ZahtevRepository;
import rs.xml.oglas.util.UtilClass;

@Service
public class OglasService {

	@Autowired
	OglasRepository oglasRepository;
	
	@Autowired
	SifrarnikClient sifrarnikClient;
	
	@Autowired
	ZahtevRepository zahtevRepository;
	
	@Autowired
	OcenaRepository ocenaRepository;
	
	@Autowired
	UtilClass util;
	
	@Autowired
	IzvestajRepository izvestajRepository;
	
	public Oglas findOne(Long id) {
		Oglas oglas = oglasRepository.findById(id).orElseThrow(() -> new NotFoundException("Oglas with id:" +id+ " does not exist!"));
		return oglas;
	}

	public List<Oglas> findAll() {
		return oglasRepository.findAll();
	}

	public Page<Oglas> findAll(Pageable page) {
		return oglasRepository.findAll(page);
	}
	
	public List<Oglas> findMyOglasi(String username){
		return oglasRepository.findActiveOglaseFromUser(username);
		
	}

	public Oglas save(Oglas oglas) {
		oglas.setOid(oglas.getUsername() + "-" + util.randomString());
		return oglasRepository.save(oglas);
	}

	public void remove(Long id) {
		oglasRepository.deleteById(id);
	}
	
	public Oglas findOneByOid(String oid) {
		Oglas oglas = oglasRepository.findOglasByOid(oid);
		return oglas;
	}
	
	
	//vraca prednje kilometre datog oglasa
		public int getKilometri(Long id) {
			return(izvestajRepository.findPredjeniKilometri(id));
		}
		
		public int getSumOcena(Long id) {
			int ocena = ocenaRepository.getSumOcena(id);
			return(ocena);
		}
		
		public int getBrojOcena(Long id) {
			return(ocenaRepository.getBrojOcena(id));
		}
	

	public Collection<OglasDTOsearch> search(String mesto, Date odDate, Date doDate, String marka, String model,
											 String menjac, String gorivo, String klasa, int predjenaInt, int planiranaInt,
											 String osiguranje, int brSedZaDecuInt) {
		
		Collection<OglasDTOsearch> ret = new ArrayList<OglasDTOsearch>();
		Encoder encoder = Base64.getEncoder();
		String imageString = "";
		
		java.sql.Date odDateOVAJ = new java.sql.Date(odDate.getTime());
		java.sql.Date doDateOVAJ = new java.sql.Date(doDate.getTime());
				
		Collection<Oglas> oglasi = oglasRepository.findAll();
//		System.out.println("****** ukupno oglasa: " + oglasi.size());
		
		// za proveru zauzetosti auta
		List<Zahtev> listaZahteva = zahtevRepository.findAll();
		
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
			// ovako ili mozda inner join zahteva i oglas_zahtev tabela ?
			// da li je zauzet auto u zadatom vremenu
			int count = 0;
			for(Zahtev zah: listaZahteva) {
//				System.out.println("*** in for loop, count:" + count);
				count++;
				// ako sadrzi ovaj oglas i ako je rezervisan
				if(zah.getOglasi().contains(oglas) && zah.getStatus().equals(ZahtevStatus.PAID)) {
//					System.out.println("*** contains 'oglas' && is RESERVED");
					// ako postoji presek sa nekim rezervisanim zahtevom
					// deo vremena se preklapa, na pocetku zahteva
					
//					System.out.println("***zah.getOd():" + zah.getOd());
//					System.out.println("***odDateOVAJ:" + odDateOVAJ);
//					System.out.println("***doDateOVAJ:" + doDateOVAJ);
					
					//ovo bi trebalo da radi, clean je
//					if(odDateOVAJ.before(zah.getDo()) && doDateOVAJ.after(zah.getOd()))
					
					if(!zah.getOd().before(odDateOVAJ) && !zah.getOd().after(doDateOVAJ)) {
						flag = false;
//						System.out.println("*** flag = false, 1");
						break;
					}
					// deo vremena se preklapa, na kraju zahteva
					if(!zah.getDo().before(odDateOVAJ) && !zah.getDo().after(doDateOVAJ)) {
						flag = false;
//						System.out.println("*** flag = false, 2");
						break;
					}
					// celo vreme se nalazi u nekom zahtevu
					if(zah.getOd().before(odDateOVAJ) && zah.getDo().after(doDateOVAJ)) {
						flag = false;
//						System.out.println("*** flag = false, 3");
						break;
					}
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
					for(Slika slika : oglas.getSlike()) {
						imageString = encoder.encodeToString(slika.getSlika());
						break;
					}
//					imageString = encoder.encodeToString(oglas.getSlike().get(0).getSlika());
					oglasDTO.setSlika("data:image/jpeg;base64," + imageString);
				}
				
				Double avgOcena = ocenaRepository.getAvgOcenaForOglas(oglas.getId());
				if(avgOcena == null) {
					avgOcena = 5.0;
				}
				
				oglasDTO.setOcena(avgOcena.doubleValue());
				ret.add(oglasDTO);
			}
		}
		
		return ret;
	}
	
	public Oglas saveAsBasicUser(Oglas oglas, String username) {
		
		List<Oglas> oglasi = oglasRepository.findActiveOglaseFromUser(username);
		
		if(oglasi.size() < 3) {
			return oglasRepository.save(oglas);
		}
		else {
			return null;
		}
	}

	public boolean createOglasWithFeignClient(Oglas oglas, NewOglasDTO oglasDTO) {		
		try {
			MestoDTO mestoDto = sifrarnikClient.getMesto(oglasDTO.getMesto());
//			System.out.println(">>>OglasService > createOglasWithFeignClient MESTO: " + mestoDto.getName());
			ModelDTO modelDto = sifrarnikClient.getModel(oglasDTO.getModel());
//			System.out.println(">>>OglasService > createOglasWithFeignClient MODEL: " + modelDto.getName());
			MarkaDTO markaDto = sifrarnikClient.getMarka(oglasDTO.getMarka());
//			System.out.println(">>>OglasService > createOglasWithFeignClient MARKA: " + markaDto.getName());
			KlasaDTO klasaDto = sifrarnikClient.getKlasa(oglasDTO.getKlasa());
//			System.out.println(">>>OglasService > createOglasWithFeignClient KLASA: " + klasaDto.getName());
			GorivoDTO gorivoDto = sifrarnikClient.getGorivo(oglasDTO.getGorivo());
//			System.out.println(">>>OglasService > createOglasWithFeignClient GORIVO: " + gorivoDto.getName());
			MenjacDTO menjacDto = sifrarnikClient.getMenjac(oglasDTO.getMenjac());
//			System.out.println(">>>OglasService > createOglasWithFeignClient MENJAC: " + menjacDto.getName());
			
			oglas.setMesto(mestoDto.getName());
			oglas.setMarka(markaDto.getName());
			oglas.setModel(modelDto.getName());
			oglas.setKlasa(klasaDto.getName());
			oglas.setGorivo(gorivoDto.getName()); 
			oglas.setMenjac(menjacDto.getName());
			
			return true;
		} catch (Exception e) {
			System.out.println(">>>OglasService > createOglasWithFeignClient: ERROR!");
			throw new NotFoundException("Ne_postoje_proslenjeni_model/marka/klasa/mesto/gorivo/menjac");
//			return false;
		}		
	}

	public Oglas updateOglas(Long oid, NewOglasDTO oglasDTO, String username) {
		
		Oglas ogl = findOne(oid);		
		
		if(!ogl.getUsername().equals(username)) {
			return null;
		}
		
		createOglasWithFeignClient(ogl, oglasDTO);
		
		ogl.setCena(oglasDTO.getCena());
		ogl.setKilometraza(oglasDTO.getKilometraza());
		ogl.setPlaniranaKilometraza(oglasDTO.getPlaniranaKilometraza());
		ogl.setSedistaZaDecu(oglasDTO.getBrSedistaZaDecu());
		ogl.setOsiguranje(oglasDTO.isOsiguranje());
		ogl.setOd(oglasDTO.getOD());
		ogl.setDo(oglasDTO.getDO());
				
		// prvo obrisati stare slike iz baze, ili...
//		ogl.setSlike(new ArrayList<Slika>());
//		for(SlikaDTO slikaDTO: )
		
		ogl = this.save(ogl);
		return ogl;
	}

	public Oglas deleteOglas(Long oid, String username) {
		
		Oglas ogl = findOne(oid);// ako ne postoji okinuce exception
		if(!ogl.getUsername().equals(username)) {
			return null; // nije tvoj oglas
		}
		ogl.setDeleted(true);
		this.save(ogl);
		
		return ogl;
	}
}
