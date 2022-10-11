package tech.codepalace.model;

import java.sql.Date;

public class FitnessAbo {

	//Fitness ID, Name, Eintrittsdatum, Austrittsdatum, Betrag, Firma, Bemerkungen, Kontostatus(abgelaufen in rot, Aktiv en verde).
	//Variables Fitness Subscription 
	private int id;
	private String fitnessID;
	private String name;
	private Date eintrittsDatum;
	private Date austrittsDatum;
	private double betrag;
	private String firma;
	private String bemerkungen;
	private String kontoStatus;
	private String abkuerzungMA;
	
	public FitnessAbo() {}

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
	 * @return the fitnessID
	 */
	public String getFitnessID() {
		return fitnessID;
	}

	/**
	 * @param fitnessID the fitnessID to set
	 */
	public void setFitnessID(String fitnessID) {
		this.fitnessID = fitnessID;
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
	 * @return the eintrittsDatum
	 */
	public Date getEintrittsDatum() {
		return eintrittsDatum;
	}

	/**
	 * @param eintrittsDatum the eintrittsDatum to set
	 */
	public void setEintrittsDatum(Date eintrittsDatum) {
		this.eintrittsDatum = eintrittsDatum;
	}

	/**
	 * @return the austrittsDatum
	 */
	public Date getAustrittsDatum() {
		return austrittsDatum;
	}

	/**
	 * @param austrittsDatum the austrittsDatum to set
	 */
	public void setAustrittsDatum(Date austrittsDatum) {
		this.austrittsDatum = austrittsDatum;
	}

	/**
	 * @return the betrag
	 */
	public double getBetrag() {
		return betrag;
	}

	/**
	 * @param betrag the betrag to set
	 */
	public void setBetrag(double betrag) {
		this.betrag = betrag;
	}

	/**
	 * @return the firma
	 */
	public String getFirma() {
		return firma;
	}

	/**
	 * @param firma the firma to set
	 */
	public void setFirma(String firma) {
		this.firma = firma;
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
	 * @return the kontoStatus
	 */
	public String getKontoStatus() {
		return kontoStatus;
	}

	/**
	 * @param kontoStatus the kontoStatus to set
	 */
	public void setKontoStatus(String kontoStatus) {
		this.kontoStatus = kontoStatus;
	}

	/**
	 * @return the abkuerzungMA
	 */
	public String getAbkuerzungMA() {
		return abkuerzungMA;
	}

	/**
	 * @param abkuerzungMA the abkuerzungMA to set
	 */
	public void setAbkuerzungMA(String abkuerzungMA) {
		this.abkuerzungMA = abkuerzungMA;
	}
	
	
	
	
	
}
