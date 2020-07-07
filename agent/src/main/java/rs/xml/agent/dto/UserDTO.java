package rs.xml.agent.dto;

import rs.xml.agent.model.User;

public class UserDTO {
	
	private Long id;
	
	private String firstname;


	private String lastname;
	

	private String email;
	
	private String companyName;
	
	private String adress;
	
	private int pib;
	
	
	public UserDTO(User user) {
		this.id=user.getId();
		this.firstname=user.getFirstName();
		this.lastname=user.getLastName();
		if(user.getEmail() != null) {
		this.email=user.getEmail();
		}else {
			this.email = "";
		}
		if(user.getAdress() != null) {
		this.adress=user.getAdress();
		}else {
			this.adress = "";
		}
		if(user.getCompanyName() != null) {
		this.companyName=user.getCompanyName();
		}else {
			this.companyName = "";
		}
		if(user.getPib() != 0) {
			this.pib=user.getPib();
		}else this.pib = 0;
		
		
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
