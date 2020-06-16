package rs.xml.agent.dto;

import java.util.ArrayList;
import java.util.List;

import rs.xml.agent.model.Chat;
import rs.xml.agent.model.Poruka;

public class ChatPorukeDTO {
	
	private List<PorukaDTO> poruke = new ArrayList<PorukaDTO>();
	
	public ChatPorukeDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public ChatPorukeDTO(Chat chat) {
		for(Poruka p: chat.getPoruke()) {
			PorukaDTO porukaDTO = new PorukaDTO(p);
			poruke.add(porukaDTO);
		}
	}
	
	public List<PorukaDTO> getPoruke() {
		return poruke;
	}

	public void setPoruke(List<PorukaDTO> poruke) {
		this.poruke = poruke;
	}
	
	
	
}
