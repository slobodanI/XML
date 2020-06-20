package rs.xml.chat.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import rs.xml.chat.model.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long>{

	@Query("SELECT c FROM Chat c WHERE RECEIVER_USERNAME=:username OR SENDER_USERNAME=:username")
	List<Chat> findMyChats(@Param("username") String username);
	
	@Query("SELECT c FROM Chat c WHERE c.cid = :cid")
	Chat findChatByCid(@Param("cid") String cid);
}
