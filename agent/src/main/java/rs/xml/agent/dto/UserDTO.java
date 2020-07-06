package rs.xml.agent.dto;

import rs.xml.agent.model.User;

public class UserDTO {
	
	private Long id;
	
	private String firstname;


	private String lastname;
	

	private String email;
	
	
	public UserDTO(User user) {
		this.id=user.getId();
		this.firstname=user.getFirstName();
		this.lastname=user.getLastName();
		this.email=user.getEmail();
		
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


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}
	
	
	

}
