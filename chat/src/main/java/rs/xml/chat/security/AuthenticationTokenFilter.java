package rs.xml.chat.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter{
	
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String permissions = httpRequest.getHeader("permissions");
        String username = httpRequest.getHeader("username");

        if (permissions != null) {
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();

            String[] permisije = permissions.split("\\|");
            for (String permisija : permisije) {
                authorities.add(new SimpleGrantedAuthority(permisija));
            }
            
//            System.out.println(">>> CHAT FILTER, PERMISIJE:" + permissions);
            
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }
	
}
