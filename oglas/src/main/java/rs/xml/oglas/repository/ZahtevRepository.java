package rs.xml.oglas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.xml.oglas.model.Zahtev;

@Repository
public interface ZahtevRepository extends JpaRepository<Zahtev,Long> {
	

}
