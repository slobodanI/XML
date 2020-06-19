package rs.xml.oglas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.xml.oglas.model.Ocena;


@Repository
public interface OcenaRepository extends JpaRepository<Ocena, Long>{

	@Query("SELECT o FROM Ocena o WHERE o.usernameKoga = :username and o.deleted = false and o.approved = 'APPROVED'")
	List<Ocena> findOceneForMe(@Param("username") String username);
	
	@Query("SELECT o FROM Ocena o WHERE o.usernameKo = :username and o.deleted = false")
	List<Ocena> findMyOcene(@Param("username") String username);
	
	@Query("SELECT o FROM Ocena o WHERE o.zahtevId = :zahtevId and oglas_id = :oglasId")
	Ocena findOcenaIfExists(@Param("zahtevId") Long zahtevId, @Param("oglasId") Long oglasId);
	
	@Query("SELECT o FROM Ocena o WHERE o.approved = 'UNKNOWN' and o.deleted = false")
	List<Ocena> findOceneToBeApproved();
	
	@Query(value = "SELECT AVG(ocena) FROM OCENA WHERE approved = 'APPROVED' and deleted = false and OGLAS_ID = :oglasId", nativeQuery = true)
	Double getAvgOcenaForOglas(@Param("oglasId") Long oglasId);
	
	@Query(value = "SELECT COUNT(ocena) FROM OCENA WHERE approved = 'APPROVED' and deleted = false and OGLAS_ID = :oglasId", nativeQuery = true)
	int getBrojOcena(@Param("oglasId") Long oglasId);
	
	@Query(value = "SELECT SUM(ocena) FROM OCENA WHERE approved = 'APPROVED' and deleted = false and OGLAS_ID = :oglasId", nativeQuery = true)
	int getSumOcena(@Param("oglasId") Long oglasId);
}
