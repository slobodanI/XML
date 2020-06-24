package rs.xml.chat.util;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.web.util.HtmlUtils;

import rs.xml.chat.dto.ChatDTO;
import rs.xml.chat.dto.PorukaDTO;

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

}
