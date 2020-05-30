package rs.xml.sifrarnik.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.xml.sifrarnik.model.Gorivo;
import rs.xml.sifrarnik.repository.gorivoRepository;

@Service
public class GorivoServices 
{

	@Autowired
	gorivoRepository gorivoRepo;
		
	
//GORIVO	
//----------------------------------------------------------------------------------------------------------
		
	
	public Gorivo findOne(Long id) 
	{
		return gorivoRepo.findById(id).orElseGet(null);
	}

	public List<Gorivo> findAll() 
	{
		return gorivoRepo.findAll();
	}
	
	public Gorivo save(Gorivo gorivo) 
	{
		return gorivoRepo.save(gorivo);
	}

	public void remove(Long id) 
	{
		gorivoRepo.deleteById(id);
	}
	
	public List<Gorivo> getAllGorivo() 
	{
		return findAll();
	}

	public Gorivo getGorivoById(Long Id) 
	{
		return findOne(Id);
	}

	public Gorivo updateGorivo(Long id, String info) 
	{
		Gorivo gor = findOne(id);
		gor.setName(info); 
		save(gor);
		return gor;
	}

	public void createGorivo(String info) 
	{
		Gorivo g = new Gorivo();
		g.setName(info);
		save(g);
	}

	public void deleteGorivo(Long id) 
	{
		remove(id);
	}


}

//---------------------------------------------------------------------------------------------------------------