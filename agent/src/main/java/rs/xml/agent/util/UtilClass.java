package rs.xml.agent.util;

import java.security.SecureRandom;
import java.util.Random;


public class UtilClass {
	
	public String randomString() {
		Random RANDOM = new SecureRandom();
	    byte[] randomString = new byte[32];
	    RANDOM.nextBytes(randomString);
	    return randomString.toString();
	}
	
}
