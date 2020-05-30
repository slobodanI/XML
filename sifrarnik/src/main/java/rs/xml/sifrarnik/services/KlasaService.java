package rs.xml.sifrarnik.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.xml.sifrarnik.model.Klasa;
import rs.xml.sifrarnik.repository.klasaRepository;

@Service
public class KlasaService 
{

	@Autowired
	klasaRepository klasaRepo;
		
	
//GORIVO	
//----------------------------------------------------------------------------------------------------------
		
	
	public Klasa findOne(Long id) 
	{
		return klasaRepo.findById(id).orElseGet(null);
	}

	public List<Klasa> findAll() 
	{
		return klasaRepo.findAll();
	}
	
	public Klasa save(Klasa klasa) 
	{
		return klasaRepo.save(klasa);
	}

	public void remove(Long id) 
	{
		klasaRepo.deleteById(id);
	}
	
	public List<Klasa> getAllKlase() 
	{
		return findAll();
	}

	public Klasa getKlasaById(Long Id) 
	{
		return findOne(Id);
	}

	public Klasa updateKlasa(Long id, String info) 
	{
		Klasa k = findOne(id);
		k.setName(info); 
		save(k);
		return k;
	}

	public void createKlasa(String info) 
	{
		Klasa k = new Klasa();
		k.setName(info);
		save(k);
	}

	public void deleteKlasa(Long id) 
	{
		remove(id);
	}

	
	
}
//---------------------------------------------------------------------------------------------------------------------