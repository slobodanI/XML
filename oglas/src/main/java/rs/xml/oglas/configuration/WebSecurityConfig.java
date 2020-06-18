package rs.xml.oglas.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import rs.xml.oglas.security.AuthenticationTokenFilter;
import rs.xml.oglas.security.RestAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	// Neautorizovani pristup zastcenim resursima
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;
	
    @Bean
    public AuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        AuthenticationTokenFilter authenticationTokenFilter = new AuthenticationTokenFilter();
        authenticationTokenFilter.setAuthenticationManager(authenticationManagerBean());
        return authenticationTokenFilter;
    }
    
//    // Definisemo nacin autentifikacije
// 	@Autowired
// 	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
// 		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
// 	}
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//      auth.authenticationProvider(new MyCustomAuthProvider());
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        http.headers().addHeaderWriter(new StaticHeadersWriter("X-Content-Security-Policy","script-src 'self'"));

        http
        
		     // za neautorizovane zahteve posalji 401 gresku
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()
        
                .csrf()
                .disable()
                .exceptionHandling()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/ws/*").permitAll()
        		.antMatchers("/ws").permitAll()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/h2-console/**", "/hello").permitAll()
                .antMatchers("/search").permitAll()
                .antMatchers(HttpMethod.GET, "/oglas", "/oglas/**").permitAll()
                .anyRequest().authenticated();
        
        http.csrf().disable(); //  zbog /h2-console je disabled
		http.headers().frameOptions().disable(); // da se vidi /h2-console
        
        http.addFilterAfter(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }
	
}
