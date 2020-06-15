package rs.xml.agent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.xml.agent.exceptions.NotFoundException;
import rs.xml.agent.model.Gorivo;
import rs.xml.agent.repository.gorivoRepository;

@Service
public class GorivoServices 
{

	@Autowired
	gorivoRepository gorivoRepo;
		
	
//GORIVO	
//----------------------------------------------------------------------------------------------------------
		
	
	public Gorivo findOne(Long id) 
	{
		return gorivoRepo.findById(id).orElseThrow(() -> new NotFoundException("Gorivo with id:" +id+ " does not exist!"));
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

	public Gorivo updateGorivo(Long id, String info) 
	{
		List<Gorivo> lista = findAll();
		
		for (Gorivo gorivo : lista) 
		{
			if(gorivo.getName().toLowerCase().equals(info.toLowerCase()))
			{
				return null;
			}
		}
		
		Gorivo gor = findOne(id);
		gor.setName(info); 
		save(gor);
		return gor;
	}

	public Gorivo createGorivo(String info) 
	{
		
		List<Gorivo> lista = findAll();
		
		for (Gorivo gorivo : lista) 
		{
			if(gorivo.getName().toLowerCase().equals(info.toLowerCase()))
			{
				return null;
			}
		}
		
		Gorivo g = new Gorivo();
		g.setName(info);
		save(g);
		return g;
	}

	public void deleteGorivo(Long Id) 
	{	
		findOne(Id); // da okine error ako ne postoji gorivo sa prosledjenim Id
		remove(Id);
	}


}

//---------------------------------------------------------------------------------------------------------------