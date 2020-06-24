package rs.xml.agent.util;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.web.util.HtmlUtils;

import rs.xml.agent.dto.ChatDTO;
import rs.xml.agent.dto.OcenaDTO;
import rs.xml.agent.dto.OglasDTO;
import rs.xml.agent.dto.OglasDTOsearch;
import rs.xml.agent.dto.PorukaDTO;
import rs.xml.agent.dto.ZahtevDTO;



public class UtilClass {
	
	public String randomString() {
		Random RANDOM = new SecureRandom();
	    byte[] randomString = new byte[32];
	    RANDOM.nextBytes(randomString);
	    return randomString.toString();
	}
	
	public static boolean isHtml(String input) {
		boolean isHtml = false;
		if (input != null) {
			if (!input.equals(HtmlUtils.htmlEscape(input))) {
				isHtml = true;
			}
		}
		return isHtml;
	}

	public String escape(String input) {
		return HtmlUtils.htmlEscape(input);
	}

	public ChatDTO escapeChatDTO(ChatDTO chatDTO) {
		chatDTO.setReceivereUsername(escape(chatDTO.getReceivereUsername()));
		chatDTO.setSenderUsername(escape(chatDTO.getSenderUsername()));
		return chatDTO;
	}

	public PorukaDTO escapePorukaDTO(PorukaDTO porukaDTO) {
		porukaDTO.setBody(escape(porukaDTO.getBody()));
		porukaDTO.setSenderUsername(escape(porukaDTO.getSenderUsername()));
		return porukaDTO;
	}
	
	public OglasDTOsearch escapeOglasDTOsearch(OglasDTOsearch oglasDTO) {
		oglasDTO.setGorivo(escape(oglasDTO.getGorivo()));
		oglasDTO.setKlasa(escape(oglasDTO.getKlasa()));
		oglasDTO.setMarka(escape(oglasDTO.getMarka()));
		oglasDTO.setMenjac(escape(oglasDTO.getMenjac()));
		oglasDTO.setMesto(escape(oglasDTO.getMesto()));
		oglasDTO.setModel(escape(oglasDTO.getModel()));
		return oglasDTO;
	}

	public OglasDTO escapeOglasDTO(OglasDTO oglasDTO) {
		oglasDTO.setGorivo(escape(oglasDTO.getGorivo()));
		oglasDTO.setKlasa(escape(oglasDTO.getKlasa()));
		oglasDTO.setMarka(escape(oglasDTO.getMarka()));
		oglasDTO.setMenjac(escape(oglasDTO.getMenjac()));
		oglasDTO.setMesto(escape(oglasDTO.getMesto()));
		oglasDTO.setModel(escape(oglasDTO.getModel()));
		oglasDTO.setUsername(escape(oglasDTO.getUsername()));

		return oglasDTO;
	}

	public ZahtevDTO escapeZahtevDTO(ZahtevDTO zahtevDTO) {
		zahtevDTO.setUsername(escape(zahtevDTO.getUsername()));
		zahtevDTO.setPodnosilacUsername(escape(zahtevDTO.getPodnosilacUsername()));
		return zahtevDTO;
	}

	public OcenaDTO escapeOcenaDTO(OcenaDTO ocenaDTO) {
		ocenaDTO.setKomentar(escape(ocenaDTO.getKomentar()));
		ocenaDTO.setOdgovor(escape(ocenaDTO.getOdgovor()));
		ocenaDTO.setUsernameKo(escape(ocenaDTO.getUsernameKo()));
		ocenaDTO.setUsernameKoga(escape(ocenaDTO.getUsernameKoga()));
		return ocenaDTO;

	}
	
}
