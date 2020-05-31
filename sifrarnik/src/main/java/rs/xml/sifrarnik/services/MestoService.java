package rs.xml.sifrarnik.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.xml.sifrarnik.dto.MestoNewDTO;
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
		return mestoRepo.findById(id).orElseGet(null);
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

	public Mesto updateMesto(Long id, MestoNewDTO info) 
	{
		List<Mesto> lista = findAll();
		
		for (Mesto mesto : lista) 
		{
			if(mesto.getName().toLowerCase().contentEquals(info.getName().toLowerCase()))
			{
				return null;
			}
		}
		
		Mesto m = findOne(id);
		m.setName(info.getName()); 
		save(m);
		return m;
	}

	public Mesto createMesto(MestoNewDTO info) 
	{
		List<Mesto> lista = findAll();
		
		for (Mesto mesto : lista) 
		{
			if(mesto.getName().toLowerCase().contentEquals(info.getName().toLowerCase()))
			{
				return null;
			}
		}
		
		Mesto m = new Mesto();
		m.setName(info.getName());
		save(m);
		return m;
	}

	public void deleteMesto(Long id) 
	{
		remove(id);
	}
	
	
	
}
//-------------------------------------------------------------------------------------------------------------

