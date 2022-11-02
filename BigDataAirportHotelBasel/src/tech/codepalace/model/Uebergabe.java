package tech.codepalace.model;

import java.sql.Date;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description HandOver Class for the Shift information transfer
 *
 */
public class Uebergabe {
	
	//Variables for the Uebergabe (HandOver)
	private int id;
	private Date datumUebergabe;
	private String information;
	private String abkuerzungMA;
	
	
 public Uebergabe() {}


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
 * @return the datumUebergabe
 */
public Date getDatumUebergabe() {
	return datumUebergabe;
}


/**
 * @param datumUebergabe the datumUebergabe to set
 */
public void setDatumUebergabe(Date datumUebergabe) {
	this.datumUebergabe = datumUebergabe;
}


/**
 * @return the information
 */
public String getInformation() {
	return information;
}


/**
 * @param information the information to set
 */
public void setInformation(String information) {
	this.information = information;
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
