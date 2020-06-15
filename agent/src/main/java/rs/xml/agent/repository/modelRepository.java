package rs.xml.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.xml.agent.model.Model;

public interface modelRepository extends JpaRepository<Model, Long>
{

}
