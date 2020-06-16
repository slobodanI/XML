package rs.xml.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.xml.agent.model.Mesto;

public interface mestoRepository extends JpaRepository<Mesto, Long>
{

}