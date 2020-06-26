package rs.xml.auth.security;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import rs.xml.auth.model.User;

@Component
public class TokenUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(TokenUtils.class.getName());
	
	@Value("auth")
	private String APP_NAME;

	@Value("secret") // encodovano base64 "c2VjcmV0"
	public String SECRET;

	@Value("36000000") // 1000 * 60 * 60 * 10: 10h
	private int EXPIRES_IN;

	@Value("Auth")
	private String AUTH_HEADER;

	static final String AUDIENCE_UNKNOWN = "unknown";
	static final String AUDIENCE_WEB = "web";
	static final String AUDIENCE_MOBILE = "mobile";
	static final String AUDIENCE_TABLET = "tablet";

	@Autowired
	TimeProvider timeProvider;

	private SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	// Funkcija za generisanje JWT token
	public String generateToken(String username, String perimisije) {
		return Jwts.builder()
				.setIssuer(APP_NAME)
				.setSubject(username)
				.setAudience(generateAudience())
				.setIssuedAt(timeProvider.now())
				.setExpiration(generateExpirationDate())
				.claim("permisije", perimisije) //postavljanje proizvoljnih podataka u telo JWT tokena
				.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
	}

	private String generateAudience() {
//		Moze se iskoristiti org.springframework.mobile.device.Device objekat za odredjivanje tipa uredjaja sa kojeg je zahtev stigao.
		
//		String audience = AUDIENCE_UNKNOWN;
//		if (device.isNormal()) {
//			audience = AUDIENCE_WEB;
//		} else if (device.isTablet()) {
//			audience = AUDIENCE_TABLET;
//		} else if (device.isMobile()) {
//			audience = AUDIENCE_MOBILE;
//		}
		return AUDIENCE_WEB;
	}

	private Date generateExpirationDate() {
		return new Date(timeProvider.now().getTime() + EXPIRES_IN);
	}

	// Funkcija za refresh JWT tokena
	public String refreshToken(String token) {
		String refreshedToken;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			claims.setIssuedAt(timeProvider.now());
			refreshedToken = Jwts.builder()
					.setClaims(claims)
					.setExpiration(generateExpirationDate())
					.signWith(SIGNATURE_ALGORITHM, SECRET).compact();
		} catch (Exception e) {
			refreshedToken = null;
		}
		return refreshedToken;
	}

	public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
		final Date created = this.getIssuedAtDateFromToken(token);
		return (!(this.isCreatedBeforeLastPasswordReset(created, lastPasswordReset))
				&& (!(this.isTokenExpired(token)) || this.ignoreTokenExpiration(token)));
	}

	// Funkcija za validaciju JWT tokena
	public Boolean validateToken(String token, UserDetails userDetails) {
		User user = (User) userDetails;
		final String username = getUsernameFromToken(token);
		final Date created = getIssuedAtDateFromToken(token);
//		if(username != null && username.equals(userDetails.getUsername())
//				&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate())) {
//			System.out.println("***TOKEN JE VALIDAN");
//		} else {
//			System.out.println("***TOKEN NIJE VALIDAn");
//		}
		
		return (username != null && username.equals(userDetails.getUsername())
				&& !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
	}

	public String getUsernameFromToken(String token) {
		String username;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			username = claims.getSubject();
			
		} catch (Exception e) {
			username = null;
		}
		return username;
	}
	
	public String getPermissionFromToken(String token) {
		String per;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			per = claims.get("permisije", String.class);			
		} catch (Exception e) {
			per = null;
		}
		return per;
	}

	public Date getIssuedAtDateFromToken(String token) {
		Date issueAt;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			issueAt = claims.getIssuedAt();
		} catch (Exception e) {
			issueAt = null;
		}
		return issueAt;
	}

	public String getAudienceFromToken(String token) {
		String audience;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			audience = claims.getAudience();
		} catch (Exception e) {
			audience = null;
		}
		return audience;
	}

	public Date getExpirationDateFromToken(String token) {
		Date expiration;
		try {
			final Claims claims = this.getAllClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		return expiration;
	}

	public int getExpiredIn() {
		return EXPIRES_IN;
	}

	// Funkcija za preuzimanje JWT tokena iz zahteva
	public String getToken(HttpServletRequest request) {
		String authHeader = getAuthHeaderFromHeader(request);
		
//		System.out.println("***getToken(), authHeader: " + authHeader);
		
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		}

		return null;
	}

	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(AUTH_HEADER);
	}
	
	private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
		return (lastPasswordReset != null && created.before(lastPasswordReset));
	}

	private Boolean isTokenExpired(String token) {
		final Date expiration = this.getExpirationDateFromToken(token);
		return expiration.before(timeProvider.now());
	}

	private Boolean ignoreTokenExpiration(String token) {
		String audience = this.getAudienceFromToken(token);
		return (audience.equals(AUDIENCE_TABLET) || audience.equals(AUDIENCE_MOBILE));
	}

	// Funkcija za citanje svih podataka iz JWT tokena
	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		try {
			claims = Jwts.parser()
					.setSigningKey(SECRET)
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			claims = null;
		}
		return claims;
	}
	
	public boolean validateTokenForGateway(String token, String ip) {
        try {
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            logger.error("SR, Invalid JWT, SignatureException, IP: " + ip);
        } catch (MalformedJwtException e) {
            logger.error("SR, Invalid JWT, MalformedJwtException, IP: " + ip);
        } catch (ExpiredJwtException e) {
        	logger.error("SR, Invalid JWT,ExpiredJwtException, IP: " + ip);
        } catch (UnsupportedJwtException e) {
            logger.error("SR, Invalid JWT,UnsupportedJwtException, IP: " + ip);
        } catch (IllegalArgumentException e) {
            logger.error("SR, JWT claims string is empty, IllegalArgumentException, IP: " + ip);
        }
        
        return false;
    }
}