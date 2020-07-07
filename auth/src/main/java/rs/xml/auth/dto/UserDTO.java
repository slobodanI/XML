package rs.xml.auth.dto;

import rs.xml.auth.model.User;

public class UserDTO {
	
	private Long id;
	
	private String firstname;


	private String lastname;
	
	private int owes;
	
	private String email;
	
	private boolean isBlockedSlanjeZahteva;
	
	private boolean isBlockedPostavljanjeOglasa;
	
	
	public UserDTO(User user) {
		this.id=user.getId();
		this.firstname=user.getFirstName();
		this.lastname=user.getLastName();
		this.email=user.getEmail();
		this.isBlockedPostavljanjeOglasa=user.isBlockedPostavljanjeOglasa();
		this.isBlockedSlanjeZahteva=user.isBlockedSlanjeZahteva();
		this.owes = user.getOwes();
		
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

	public boolean isBlockedSlanjeZahteva() {
		return isBlockedSlanjeZahteva;
	}

	public void setBlockedSlanjeZahteva(boolean isBlockedSlanjeZahteva) {
		this.isBlockedSlanjeZahteva = isBlockedSlanjeZahteva;
	}

	public boolean isBlockedPostavljanjeOglasa() {
		return isBlockedPostavljanjeOglasa;
	}

	public void setBlockedPostavljanjeOglasa(boolean isBlockedPostavljanjeOglasa) {
		this.isBlockedPostavljanjeOglasa = isBlockedPostavljanjeOglasa;
	}

	public int getOwes() {
		return owes;
	}

	public void setOwes(int owes) {
		this.owes = owes;
	}
	
	
	

}
