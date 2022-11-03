package tech.codepalace.dao;


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
	void displayUebergabe() throws DaoException;

}
