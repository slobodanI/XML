package rs.xml.oglas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.xml.oglas.model.Slika;

@Repository
public interface SlikaRepository extends JpaRepository<Slika, Long> {

}
