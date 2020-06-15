package rs.xml.agent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.xml.agent.exceptions.NotFoundException;
import rs.xml.agent.model.Menjac;
import rs.xml.agent.repository.menjacRepository;

@Service
public class MenjacService 
{

	
	@Autowired
	menjacRepository menjacRepo;
		
	
//MENJAC	
//----------------------------------------------------------------------------------------------------------
		
	
	public Menjac findOne(Long id) 
	{
		return menjacRepo.findById(id).orElseThrow(() -> new NotFoundException("Menjac with id:" +id+ " does not exist!"));
	}

	public List<Menjac> findAll() 
	{
		return menjacRepo.findAll();
	}
	
	public Menjac save(Menjac menjac) 
	{
		return menjacRepo.save(menjac);
	}

	public void remove(Long id) 
	{
		menjacRepo.deleteById(id);
	}
	
	public List<Menjac> getAllMenjac() 
	{
		return findAll();
	}

	public Menjac getMenjacById(Long Id) 
	{
		return findOne(Id);
	}

	public Menjac updateMenjac(Long id, String info) 
	{
		List<Menjac> lista = findAll();
		
		for (Menjac menjac : lista) 
		{
			if(menjac.getName().toLowerCase().equals(info.toLowerCase()))
			{
				return null;
			}
		}
		
		Menjac m = findOne(id);
		m.setName(info); 
		save(m);
		return m;
	}

	public Menjac createMenjac(String info) 
	{
		List<Menjac> lista = findAll();
		
		for (Menjac menjac : lista) 
		{
			if(menjac.getName().toLowerCase().equals(info.toLowerCase()))
			{
				return null;
			}
		}
		
		Menjac m = new Menjac();
		m.setName(info);
		save(m);
		return m;
	}

	public void deleteMenjac(Long id) 
	{
		findOne(id); // da okine error ako ne postoji menjac sa prosledjenim Id
		remove(id);
	}
	
	
	
}
//-------------------------------------------------------------------------------------------------------------

