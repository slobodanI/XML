package rs.xml.sifrarnik.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.xml.sifrarnik.model.Model;
import rs.xml.sifrarnik.repository.modelRepository;

@Service
public class ModelService 
{

	
	@Autowired
	modelRepository modelRepo;
		
	
//MENJAC	
//----------------------------------------------------------------------------------------------------------
		
	
	public Model findOne(Long id) 
	{
		return modelRepo.findById(id).orElseGet(null);
	}

	public List<Model> findAll() 
	{
		return modelRepo.findAll();
	}
	
	public Model save(Model mod) 
	{
		return modelRepo.save(mod);
	}

	public void remove(Long id) 
	{
		modelRepo.deleteById(id);
	}
	
	public List<Model> getAllModel() 
	{
		return findAll();
	}

	public Model getModelById(Long Id) 
	{
		return findOne(Id);
	}

	public Model updateModel(Long id, String info) 
	{
		Model m = findOne(id);
		m.setName(info); 
		save(m);
		return m;
	}

	public void createModel(String info) 
	{
		Model m = new Model();
		m.setName(info);
		save(m);
	}

	public void deleteModel(Long id) 
	{
		remove(id);
	}
	
	
	
}
//-------------------------------------------------------------------------------------------------------------