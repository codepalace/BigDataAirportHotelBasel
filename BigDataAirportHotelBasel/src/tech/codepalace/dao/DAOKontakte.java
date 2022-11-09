package tech.codepalace.dao;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description DAO interface for the contacts
 *
 */
public interface DAOKontakte {

	/**
	 * @description Method to check the Table for the contacts.
	 * @throws DaoException
	 */
	void checkTableKontante() throws DaoException;
	
	
	/**
	 * @description Method to display contacts.
	 * @throws DaoException
	 */
	void displayKontakte() throws DaoException;
	
}
