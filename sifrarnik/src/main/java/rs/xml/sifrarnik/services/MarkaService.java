package rs.xml.sifrarnik.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.xml.sifrarnik.dto.MarkaNewDTO;
import rs.xml.sifrarnik.model.Marka;
import rs.xml.sifrarnik.repository.markaRepository;

@Service
public class MarkaService 
{

	
	@Autowired
	markaRepository markaRepo;
		
	
//MARKA	
//----------------------------------------------------------------------------------------------------------
		
	
	public Marka findOne(Long id) 
	{
		return markaRepo.findById(id).orElseGet(null);
	}

	public List<Marka> findAll() 
	{
		return markaRepo.findAll();
	}
	
	public Marka save(Marka marka) 
	{
		return markaRepo.save(marka);
	}

	public void remove(Long id) 
	{
		markaRepo.deleteById(id);
	}
	
	public List<Marka> getAllMarke() 
	{
		return findAll();
	}

	public Marka getMarkaById(Long Id) 
	{
		return findOne(Id);
	}

	public Marka updateMarka(Long id, MarkaNewDTO info) 
	{
		List<Marka> lista = findAll();
		
		for (Marka marka : lista) 
		{
			if(marka.getName().toLowerCase().contentEquals(info.getName().toLowerCase()))
			{
				return null;
			}
		}
		
		Marka m = findOne(id);
		m.setName(info.getName()); 
		save(m);
		return m;
	}

	public Marka createMarka(MarkaNewDTO info) 
	{
		List<Marka> lista = findAll();
		
		for (Marka marka : lista) 
		{
			if(marka.getName().toLowerCase().contentEquals(info.getName().toLowerCase()))
			{
				return null;
			}
		}
		
		Marka m = new Marka();
		m.setName(info.getName());
		save(m);
		return m;
	}

	public void deleteMarka(Long id) 
	{
		remove(id);
	}
	
	
	
}
//-------------------------------------------------------------------------------------------------------------
