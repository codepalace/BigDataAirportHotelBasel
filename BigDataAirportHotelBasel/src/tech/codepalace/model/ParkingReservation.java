package tech.codepalace.model;

import java.sql.Date;

/**
 * 
 * @author Antonio Estevez Gonazez
 * @version 0.1.0
 * @description Model Class ParkingReservation
 *
 */
public class ParkingReservation {

	private int id;
	private String idParking;
	private String buchungsname;
	private String autoKFZ;
	private Date anreiseDatum;
	private Date abreiseDatum;
	private int anzahlTagen;
	private double betragParking;
	private String buchungsKanal;
	private String bemerkungen;
	private String schluesselInHaus;
	private String abkuerzungMA;
	
	
 
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getIdParking() {
		return idParking;
	}
	public void setIdParking(String idParking) {
		this.idParking = idParking;
	}
	public String getBuchungsname() {
		return buchungsname;
	}
	public void setBuchungsname(String buchungsname) {
		this.buchungsname = buchungsname;
	}
	public String getAutoKFZ() {
		return autoKFZ;
	}
	public void setAutoKFZ(String autoKFZ) {
		this.autoKFZ = autoKFZ;
	}
	public Date getAnreiseDatum() {
		return anreiseDatum;
	}
	public void setAnreiseDatum(Date anreisDatum) {
		this.anreiseDatum = anreisDatum;
	}
	public Date getAbreiseDatum() {
		return abreiseDatum;
	}
	public void setAbreiseDatum(Date abreisDatum) {
		this.abreiseDatum = abreisDatum;
	}
	public int getAnzahlTagen() {
		return anzahlTagen;
	}
	public void setAnzahlTagen(int anzahlTagen) {
		this.anzahlTagen = anzahlTagen;
	}
	public double getBetragParking() {
		return betragParking;
	}
	public void setBetragParking(double betragParking) {
		this.betragParking = betragParking;
	}
	public String getBuchungsKanal() {
		return buchungsKanal;
	}
	public void setBuchungsKanal(String buchungsKanal) {
		this.buchungsKanal = buchungsKanal;
	}
	public String getBemerkungen() {
		return bemerkungen;
	}
	public void setBemerkungen(String bemerkungen) {
		this.bemerkungen = bemerkungen;
	}
	public String getSchluesselInHaus() {
		return schluesselInHaus;
	}
	public void setSchluesselInHaus(String schluesselInHaus) {
		this.schluesselInHaus = schluesselInHaus;
	}
	public String getAbkuerzungMA() {
		return abkuerzungMA;
	}
	public void setAbkuerzungMA(String kuerzel_MA) {
		this.abkuerzungMA = kuerzel_MA;
	}
}
