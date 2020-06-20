package rs.xml.agent.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.xml.agent.model.Poruka;

@Repository
public interface PorukaRepository extends JpaRepository<Poruka, Long>{
	
	@Query("SELECT p FROM Poruka p WHERE CHAT_ID=:chatId ORDER BY TIMESTAMP")
	Set<Poruka> findPoruke(@Param("chatId") Long chatId);
	
}
