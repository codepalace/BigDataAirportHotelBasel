package tech.codepalace.dao;

import java.sql.Connection;
import java.util.List;

import javax.swing.JLabel;

import tech.codepalace.model.ParkingReservation;
import tech.codepalace.model.UserAHB;


/**
 * @description interface with the necessary methods to work with the parking database
 * @author Antonio Estevez Gonzalez
 * @version v0.1.0 February 12.2022
 *
 */
public interface DAOParking {
	

	
	
	
	
	/**
	 * @description Method to check if the parking table exists in the Database.
	 * @throws DaoException
	 */
	void checkTableParking() throws DaoException;
	
	
	int getDataRowCounter() throws DaoException;
	
	
	/**
	 * @author Antonio Estevez Gonzalez
	 * @description Method to create a new Parking Reservation. This method receives a parameter with the length of content that we have in the parking table
	 * 
	 * @throws DaoException
	 */
	
	void createNewParkingReservation(int currentRecord) throws DaoException;
	
	
	
	/** Method to add a new entry in the database
	 *  @author Antonio Estevez Gonzalez
	 **/
	void addNewParkingReservation(ParkingReservation parkingReservation, UserAHB userAHB) throws DaoException;
	
	
	
	
	
	/**
	 * 
	 * @throws DaoException
	 * @author Antonio Estevez Gonzalez
	 * @description Method to display a list of Parking Reservations saved in Database inside Table PARKING
	 */
	void displayListParking() throws DaoException;
	
	
	
	
	
	/**
	 * Method to reload all data from PARKING table in Database and display in JTable by the GUI.
	 * @throws DaoException
	 */
	void reloadParkingData() throws DaoException;
	
	
	/**
	 * Method to search in the Database Table PARKING by idParking
	 * @param idParking
	 * @throws DaoException
	 */
	void searchByIDParking(String idParking) throws DaoException;
	
	
	/**
	 * @description Method search in the Database table Parking by buchungsname(person who made the parking reservation).  
	 * @param buchungsname
	 * @throws DaoException
	 */
	void suchenNachBuchungsName(String buchungsname) throws DaoException;
	
	
	
	/**
	 * @description Method to search by car number, car licence plat
	 * @param autokfz
	 * @throws DaoException
	 */
	void suchenByAutoNr(String autokfz) throws DaoException;
	

	void updateParkingReservation(ParkingReservation parkingReservation) throws DaoException;
	
	List<ParkingReservation> orderby(String orderBy) throws DaoException;
	
	
	
	void createBackUpDataBase(JLabel statusJLabel) throws DaoException;
	
	void restoreDatabaseFromBackup(Connection conn) throws DaoException;
	
	
	
	
}

