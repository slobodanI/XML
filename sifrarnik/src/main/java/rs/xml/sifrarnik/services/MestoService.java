package rs.xml.sifrarnik.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.xml.sifrarnik.exception.NotFoundException;
import rs.xml.sifrarnik.model.Mesto;
import rs.xml.sifrarnik.repository.mestoRepository;

@Service
public class MestoService 
{

	
	@Autowired
	mestoRepository mestoRepo;
		
	
//MESTO	
//----------------------------------------------------------------------------------------------------------
		
	
	public Mesto findOne(Long id) 
	{
		return mestoRepo.findById(id).orElseThrow(() -> new NotFoundException("Mesto with id:" +id+ " does not exist!"));
	}

	public List<Mesto> findAll() 
	{
		return mestoRepo.findAll();
	}
	
	public Mesto save(Mesto mes) 
	{
		return mestoRepo.save(mes);
	}

	public void remove(Long id) 
	{
		mestoRepo.deleteById(id);
	}
	
	public List<Mesto> getAllMesto() 
	{
		return findAll();
	}

	public Mesto getMestoById(Long Id) 
	{
		return findOne(Id);
	}

	public Mesto updateMesto(Long id, String info) 
	{
		List<Mesto> lista = findAll();
		
		for (Mesto mesto : lista) 
		{
			if(mesto.getName().toLowerCase().equals(info.toLowerCase()))
			{
				return null;
			}
		}
		
		Mesto m = findOne(id);
		m.setName(info); 
		save(m);
		return m;
	}

	public Mesto createMesto(String info) 
	{
		List<Mesto> lista = findAll();
		
		for (Mesto mesto : lista) 
		{
			if(mesto.getName().toLowerCase().equals(info.toLowerCase()))
			{
				return null;
			}
		}
		
		Mesto m = new Mesto();
		m.setName(info);
		save(m);
		return m;
	}

	public void deleteMesto(Long id) 
	{
		remove(id);
	}
	
	
	
}
//-------------------------------------------------------------------------------------------------------------

