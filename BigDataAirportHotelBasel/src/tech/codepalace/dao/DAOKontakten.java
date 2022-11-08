package tech.codepalace.dao;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description DAO interface for the contacts
 *
 */
public interface DAOKontakten {

	/**
	 * @description Method to check the Table for the contacts.
	 * @throws DaoException
	 */
	void checkTableKontanten() throws DaoException;
	
	
	/**
	 * @description Method to display contacts.
	 * @throws DaoException
	 */
	void displayKontakten() throws DaoException;
	
}
