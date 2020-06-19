package rs.xml.agent.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import rs.xml.agent.security.RestAuthenticationEntryPoint;
import rs.xml.agent.security.TokenAuthenticationFilter;
import rs.xml.agent.security.TokenUtils;
import rs.xml.agent.service.CustomUserDetailsService;


@Configuration
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		prePostEnabled = true
		)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	// Implementacija PasswordEncoder-a koriscenjem BCrypt hashing funkcije.
	// BCrypt po defalt-u radi 10 rundi hesiranja prosledjene vrednosti.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private CustomUserDetailsService jwtUserDetailsService;

	// Neautorizovani pristup zastcenim resursima
	@Autowired
	private RestAuthenticationEntryPoint restAuthenticationEntryPoint;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	// Definisemo nacin autentifikacije
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}

	@Autowired
	TokenUtils tokenUtils;

	// Definisemo prava pristupa odredjenim URL-ovima
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// komunikacija izmedju klijenta i servera je stateless
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

				// za neautorizovane zahteve posalji 401 gresku
				.exceptionHandling().authenticationEntryPoint(restAuthenticationEntryPoint).and()

				// svim korisnicima dopusti da pristupe putanjama
				.authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/signup").permitAll()
				.antMatchers("/h2-console/**").permitAll()
				.antMatchers("/api/foo").permitAll()
				.antMatchers("/check/**").permitAll()
//				.antMatchers("/api/certificates/**").permitAll()
//				.antMatchers("/favicon.ico").permitAll()
				.antMatchers("/images/**").permitAll()
//				.antMatchers("/**").permitAll()
				// svaki zahtev mora biti autorizovan
				.anyRequest().authenticated().and()
				
				.cors().and()

				// presretni svaki zahtev filterom
				.addFilterBefore(new TokenAuthenticationFilter(tokenUtils, jwtUserDetailsService),
						BasicAuthenticationFilter.class);

		http.csrf().disable(); // da li ovo treba da bude enabled??? // zbog /h2-console je disabled
		http.headers().frameOptions().disable(); // da se vidi /h2-console
		
		
	}

	// Generalna bezbednost aplikacije
	@Override
	public void configure(WebSecurity web) throws Exception {
		// TokenAuthenticationFilter ce ignorisati sve ispod navedene putanje
		web.ignoring().antMatchers(HttpMethod.POST, "/authentification/login");
		web.ignoring().antMatchers(HttpMethod.GET, "/", "/webjars/**", "/*.html", "/*.js", "/*.css","/*.png", "/favicon.ico", "/**/*.html",
				"/**/*.css", "/**/*.js", "/**/*.png", "/images/**", "/js/**.map", "/**.map");
	}

}