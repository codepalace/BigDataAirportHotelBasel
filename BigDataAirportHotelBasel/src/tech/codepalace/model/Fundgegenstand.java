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
	private int roomNumber;
	private String inhaber;
	private int kiste;
	private String kisteNummer;
	
	/*
	 * this variable rueckGabe it will be used to store the information if a found object was delivered to someone back 
	 * and the date it was delivered.
	 */
	private String rueckGabe;
	
	
	
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
	public int getRoomNumber() {
		return roomNumber;
	}



	/**
	 * @param roomNumber the roomNumber to set
	 */
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
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
	public int getKiste() {
		return kiste;
	}



	/**
	 * @param kiste the kiste to set
	 */
	public void setKiste(int kiste) {
		this.kiste = kiste;
	}



	/**
	 * @return the kisteNummer
	 */
	public String getKisteNummer() {
		return kisteNummer;
	}



	/**
	 * @param kisteNummer the kisteNummer to set
	 */
	public void setKisteNummer(String kisteNummer) {
		this.kisteNummer = kisteNummer;
	}

	
	

}
