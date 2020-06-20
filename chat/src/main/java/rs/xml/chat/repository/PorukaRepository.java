package rs.xml.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.xml.chat.model.Poruka;

@Repository
public interface PorukaRepository extends JpaRepository<Poruka, Long>{
	
	@Query("SELECT p FROM Poruka p WHERE CHAT_ID=:chatId ORDER BY TIMESTAMP")
	List<Poruka> findPoruke(@Param("chatId") Long chatId);
	
}
