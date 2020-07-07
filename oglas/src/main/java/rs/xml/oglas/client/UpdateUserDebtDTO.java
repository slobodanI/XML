package rs.xml.oglas.client;

public class UpdateUserDebtDTO {
	
	
	private Long id;
	
	private String username;
	
	private int debt;
	
	public UpdateUserDebtDTO() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getDebt() {
		return debt;
	}

	public void setDebt(int debt) {
		this.debt = debt;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	

}
