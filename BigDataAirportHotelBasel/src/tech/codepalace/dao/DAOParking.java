package tech.codepalace.dao;

import java.util.List;

import tech.codepalace.model.ParkingReservation;


/**
 * @description interface with the necessary methods to work with the parking database
 * @author Antonio Estevez Gonzalez
 * @version v0.1.0 February 12.2022
 *
 */
public interface DAOParking {
	
	/**
	 * @description Method to check if the parking database exists.
	 */
	void checkTableParking() throws DaoException;
	
	
	void createTableParkingDataBase(ParkingReservation parkingReservation) throws DaoException;
	
	
	
	/** Method to add a new entry in the database
	 *  @author Antonio Estevez Gonzalez
	 **/
	void addNewParkingReservation(long lenghtParkingTableDataBase) throws DaoException;
	
	
	
	
	List<ParkingReservation>displayListParking() throws DaoException;
	List<ParkingReservation>displayParkingFoundLikeName(String name);
	List<ParkingReservation>displayParkingFoundLikeCarNumber(String carNumer);
	List<ParkingReservation>displayParkingFoundLikeDate(String date);  //Buscar como fecha a trabajarlo segun los argumentos
	List<ParkingReservation>displayParkingFoundLikeAnyEntry(String likeAnyEntry);
	void updateParkingReservation(ParkingReservation parkingReservation) throws DaoException;
	void deleteParkingReservation(int id) throws DaoException;
	List<ParkingReservation> orderby(String orderBy) throws DaoException;
	
	
}

