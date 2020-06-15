package rs.xml.agent.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.xml.agent.model.Slika;

@Repository
public interface SlikaRepository extends JpaRepository<Slika, Long> {

}
