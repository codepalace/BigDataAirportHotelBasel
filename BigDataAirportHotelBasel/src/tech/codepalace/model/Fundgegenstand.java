package tech.codepalace.model;

import java.sql.Date;

/**
 * 
 * @author tonimacaroni
 * @description Model Class Fundgegenstand for the new Lost and found items.
 *
 */
public class Fundgegenstand {
	
	//Variables for the Lost and found items.
	private Date dateItemsWasFound;
	private String foundItems;
	private String fundort;
	private String inhaber;
	private int kisteNummer;
	private String kisteName;
	
	/*
	 * this variable rueckGabe it will be used to store the information if a found object was delivered to someone back 
	 * and the date it was delivered.
	 */
	private String rueckGabe;
	
	private String abkuerzungMA;
	
	
	
	


	



	public Fundgegenstand() {}



	/**
	 * @return the dateItemsWasFound
	 */
	public Date getDateItemsWasFound() {
		return dateItemsWasFound;
	}



	/**
	 * @param dateItemsWasFound the dateItemsWasFound to set
	 */
	public void setDateItemsWasFound(Date dateItemsWasFound) {
		this.dateItemsWasFound = dateItemsWasFound;
	}



	/**
	 * @return the foundItems
	 */
	public String getFoundItems() {
		return foundItems;
	}



	/**
	 * @param foundItems the foundItems to set
	 */
	public void setFoundItems(String foundItems) {
		this.foundItems = foundItems;
	}



	/**
	 * @return the roomNumber
	 */
	public String getFundort() {
		return fundort;
	}



	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setFundort(String fundort) {
		this.fundort = fundort;
	}



	/**
	 * @return the inhaber
	 */
	public String getInhaber() {
		return inhaber;
	}



	/**
	 * @param inhaber the inhaber to set
	 */
	public void setInhaber(String inhaber) {
		this.inhaber = inhaber;
	}



	/**
	 * @return the kiste
	 */
	public int getKisteNummer() {
		return kisteNummer;
	}



	/**
	 * @param kiste the kiste to set
	 */
	public void setKisteNummer(int kisteNummer) {
		this.kisteNummer = kisteNummer;
	}



	/**
	 * @return the kisteNummer
	 */
	public String getKisteName() {
		return kisteName;
	}



	/**
	 * @param kisteNummer the kisteNummer to set
	 */
	public void setKisteName(String kisteName) {
		this.kisteName = kisteName;
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

	

	/**
	 * @return the rueckGabe
	 */
	public String getRueckGabe() {
		return rueckGabe;
	}



	/**
	 * @param rueckGabe the rueckGabe to set
	 */
	public void setRueckGabe(String rueckGabe) {
		this.rueckGabe = rueckGabe;
	}
}
