package rs.xml.agent.dto;

public class EmailDTO {
	
	private String naslov;
	private String telo;
	private String mail;
	
	public EmailDTO()
	{
		
	}
	
	public EmailDTO(String naslov, String telo, String mail) {
		super();
		this.naslov = naslov;
		this.telo = telo;
		this.mail = mail;
	}

	public String getNaslov() {
		return naslov;
	}

	public void setNaslov(String naslov) {
		this.naslov = naslov;
	}

	public String getTelo() {
		return telo;
	}

	public void setTelo(String telo) {
		this.telo = telo;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	@Override
	public String toString() {
		return "EmailDTO [naslov=" + naslov + ", telo=" + telo + ", mail=" + mail
				+ "]";
	}
	

}
