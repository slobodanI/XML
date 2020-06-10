package rs.xml.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import rs.xml.chat.model.Poruka;

@Repository
public interface PorukaRepository extends JpaRepository<Poruka, Long>{

}
