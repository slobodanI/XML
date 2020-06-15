package rs.xml.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.xml.agent.model.Poruka;

@Repository
public interface PorukaRepository extends JpaRepository<Poruka, Long>{

}
