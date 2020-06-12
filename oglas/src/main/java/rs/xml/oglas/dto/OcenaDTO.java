package rs.xml.oglas.dto;

import rs.xml.oglas.model.Ocena;

public class OcenaDTO {
	
	 	private Long id;

		private double ocena;

		private String komentar;

		private String odgovor; // od agenta

		private String usernameKo; //username onog koji je dao ocenu
		
		private String usernameKoga; //username onog za koga je ocena
	
		private String oglas; // marka: model
		
		public OcenaDTO() {
			// TODO Auto-generated constructor stub
		}

		public OcenaDTO(Long id, double ocena, String komentar, String odgovor, String usernameKo, String usernameKoga,
				String oglas) {
			this.id = id;
			this.ocena = ocena;
			this.komentar = komentar;
			this.odgovor = odgovor;
			this.usernameKo = usernameKo;
			this.usernameKoga = usernameKoga;
			this.oglas = oglas;
		}
		
		public OcenaDTO(Ocena ocena) {
			this.id = ocena.getId();
			this.ocena = ocena.getOcena();
			this.komentar = ocena.getKomentar();
			
			if(ocena.getOdgovor() == null) {
				this.odgovor = "nema odgovora...";
			} else {
				this.odgovor = ocena.getOdgovor();
			}
			
			this.usernameKo = ocena.getUsernameKo();
			this.usernameKoga = ocena.getUsernameKoga();
			this.oglas = ocena.getOglas().getMarka() + " - " + ocena.getOglas().getModel();
		}
		
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public double getOcena() {
			return ocena;
		}

		public void setOcena(double ocena) {
			this.ocena = ocena;
		}

		public String getKomentar() {
			return komentar;
		}

		public void setKomentar(String komentar) {
			this.komentar = komentar;
		}

		public String getOdgovor() {
			return odgovor;
		}

		public void setOdgovor(String odgovor) {
			this.odgovor = odgovor;
		}

		public String getUsernameKo() {
			return usernameKo;
		}

		public void setUsernameKo(String usernameKo) {
			this.usernameKo = usernameKo;
		}

		public String getUsernameKoga() {
			return usernameKoga;
		}

		public void setUsernameKoga(String usernameKoga) {
			this.usernameKoga = usernameKoga;
		}

		public String getOglas() {
			return oglas;
		}

		public void setOglas(String oglas) {
			this.oglas = oglas;
		}
		
		
		
}
