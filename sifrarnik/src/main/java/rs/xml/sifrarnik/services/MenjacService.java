package rs.xml.sifrarnik.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.xml.sifrarnik.dto.MenjacNewDTO;
import rs.xml.sifrarnik.exception.NotFoundException;
import rs.xml.sifrarnik.model.Menjac;
import rs.xml.sifrarnik.repository.menjacRepository;

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

	public Menjac updateMenjac(Long id, MenjacNewDTO info) 
	{
		List<Menjac> lista = findAll();
		
		for (Menjac menjac : lista) 
		{
			if(menjac.getName().toLowerCase().contentEquals(info.getName().toLowerCase()))
			{
				return null;
			}
		}
		
		Menjac m = findOne(id);
		m.setName(info.getName()); 
		save(m);
		return m;
	}

	public Menjac createMenjac(MenjacNewDTO info) 
	{
		List<Menjac> lista = findAll();
		
		for (Menjac menjac : lista) 
		{
			if(menjac.getName().toLowerCase().contentEquals(info.getName().toLowerCase()))
			{
				return null;
			}
		}
		
		Menjac m = new Menjac();
		m.setName(info.getName());
		save(m);
		return m;
	}

	public void deleteMenjac(Long id) 
	{
		findOne(id);// okine error ako ne postoji
		remove(id);
	}
	
	
	
}
//-------------------------------------------------------------------------------------------------------------

