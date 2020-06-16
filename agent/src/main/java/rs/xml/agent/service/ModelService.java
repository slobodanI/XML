package rs.xml.agent.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rs.xml.agent.exceptions.NotFoundException;
import rs.xml.agent.model.Model;
import rs.xml.agent.repository.modelRepository;

@Service
public class ModelService 
{

	
	@Autowired
	modelRepository modelRepo;
		
	
//MODEL	
//----------------------------------------------------------------------------------------------------------
		
	
	public Model findOne(Long id) 
	{
		return modelRepo.findById(id).orElseThrow(() -> new NotFoundException("Model with id:" +id+ " does not exist!"));
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
		List<Model> lista = findAll();
		
		for (Model model : lista) 
		{
			if(model.getName().toLowerCase().equals(info.toLowerCase()))
			{
				return null;
			}
		}
		
		Model m = findOne(id);
		m.setName(info); 
		save(m);
		return m;
	}

	public Model createModel(String info) 
	{
		List<Model> lista = findAll();
		
		for (Model model : lista) 
		{
			if(model.getName().toLowerCase().contentEquals(info.toLowerCase()))
			{
				return null;
			}
		}
		
		Model m = new Model();
		m.setName(info);
		save(m);
		return m;
	}

	public void deleteModel(Long id) 
	{
		findOne(id); // da okine error ako ne postoji model sa prosledjenim Id
		remove(id);
	}
	
	
	
}
//-------------------------------------------------------------------------------------------------------------
