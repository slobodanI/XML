package rs.xml.agent.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserRegisterRequestDTO {
		
	private Long id;
	
	@NotNull
	private String username;

	@NotNull
	@Size(min = 10, message = "Password must contain atleast 10 characters")
	private String password;

	@NotNull
	private String firstname;

	@NotNull
	private String lastname;
	
	@NotNull
	private String email;
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
