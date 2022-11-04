package tech.codepalace.dao;

import java.sql.Date;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description DAO interface for the HandOver information. Shift information for the current day or future days.
 *
 */
public interface DAOUebergabe {
	
	/**
	 * @description Method to check if the UEBERGABE table exists
	 * @throws DaoException
	 */
	void checkTableUebergabe() throws DaoException;
	
	/**
	 * @description Method to display the Uebergabe(HandOver - shift informations transfer). 
	 * @throws DaoException
	 */
	void displayUebergabe(String monthToShow) throws DaoException;
	
	
	/**
	 * @description Method to display the selected Month to show from DataBase.
	 * @param monthToShow
	 * @throws DaoException
	 */
	void displaySelectedMonth(String monthToShow) throws DaoException;
	
	
	void searchByDateUebergabe(Date datumUebergabe) throws DaoException;

}
