package tech.codepalace.model;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description Class Kontakte for contact data.
 *
 */
public class Kontakte {
 
	private int id;
	private String name;
	private String phone;
	private String bemerkungen;
	private String abteilung;
	
	
	public Kontakte() {}


	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}


	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}


	/**
	 * @return the bemerkungen
	 */
	public String getBemerkungen() {
		return bemerkungen;
	}


	/**
	 * @param bemerkungen the bemerkungen to set
	 */
	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}


	/**
	 * @return the abteilung
	 */
	public String getAbteilung() {
		return abteilung;
	}


	/**
	 * @param abteilung the abteilung to set
	 */
	public void setAbteilung(String abteilung) {
		this.abteilung = abteilung;
	}
	
	
	
}


