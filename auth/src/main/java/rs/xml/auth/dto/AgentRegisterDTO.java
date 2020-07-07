package rs.xml.auth.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AgentRegisterDTO {
	
	
	@NotNull
	private String username;

	@NotNull
	@Size(min = 10, message = "Password must contain atleast 10 characters")
	private String password;

	private String firstname;

	
	private String lastname;
	

	private String companyName;
	
	@NotNull
	private String adress;
	
	@NotNull
	private int pib;
	
	public AgentRegisterDTO() {
		// TODO Auto-generated constructor stub
	}

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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public int getPib() {
		return pib;
	}

	public void setPib(int pib) {
		this.pib = pib;
	}
	
	
	
}
