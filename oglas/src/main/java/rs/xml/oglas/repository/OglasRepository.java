package rs.xml.oglas.repository;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.xml.oglas.model.Oglas;
import rs.xml.oglas.model.Zahtev;

@Repository
public interface OglasRepository extends JpaRepository<Oglas, Long> {
	
	@Query("SELECT o FROM Oglas o WHERE o.marka = :marka and o.model = :model and "
			+ "o.menjac = :menjac and o.gorivo = :gorivo and o.klasa = :klasa")
	Collection<Oglas> searchStari(@Param("marka") String marka, 
							@Param("model") String model,
							@Param("menjac") String menjac,
							@Param("gorivo") String gorivo,
							@Param("klasa") String klasa);
	
	@Query("SELECT o FROM Oglas o WHERE (o.Od BETWEEN :od AND :do) AND (o.Do BETWEEN :od AND :do)") // dodaj mesto preuzimanja // dodati anotaciju iznad datuma u oglasu
	Collection<Oglas> searchOdDo(@Param("od") Date od, @Param("do") Date dod);
	
	@Query("SELECT o FROM Oglas o WHERE o.username = :username and o.deleted = false") // COUNT(o)
	List<Oglas> findActiveOglaseFromUser(@Param("username") String username);
	
	@Query("SELECT o FROM Oglas o WHERE o.oid = :oid")
	Oglas findOglasByOid(@Param("oid") String oid);

	@Query(value = "SELECT OGLAS_ID FROM OGLAS_ZAHTEV WHERE ZAHTEV_ID = :zahtevId", nativeQuery = true)
	Set<Long> findOglasiIDFromZahtev(@Param("zahtevId") Long zahtevId);

}
