package rs.xml.izvestaj.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.xml.izvestaj.model.Izvestaj;

@Repository
public interface IzvestajRepository extends JpaRepository<Izvestaj, Long> {

}
