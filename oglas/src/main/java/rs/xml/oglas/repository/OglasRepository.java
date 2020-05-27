package rs.xml.oglas.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.xml.oglas.model.Oglas;

@Repository
public interface OglasRepository extends JpaRepository<Oglas, Long> {

}
