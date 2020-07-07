package rs.xml.oglas.client;

public class UserDTO {
	
	private Long id;
	
	private int owes;
	
	private String firstname;


	private String lastname;
	

	private String email;
	
	private boolean isBlockedSlanjeZahteva;
	
	private boolean isBlockedPostavljanjeOglasa;
	
	public UserDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getOwes() {
		return owes;
	}

	public void setOwes(int owes) {
		this.owes = owes;
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
	
	

}
