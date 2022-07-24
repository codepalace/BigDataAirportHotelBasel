package tech.codepalace.dao;

import java.sql.Date;

import tech.codepalace.model.Fundgegenstand;
import tech.codepalace.model.UserAHB;


/**
 * 
 * @author tonimacaroni
 * @description interface with the necessary methods to work with the Database(Table-Data FUNDSACHEN)
 */
public interface DAOFundsachen {
	
	/**
	 * @description Method to check if the FUNDSACHEN table exists in the Database.
	 * @throws DaoException
	 */
	void checkTableFundsachen() throws DaoException;
	
	/**
	 * @descritpion Method to count how much entries do we have in the FUNDSACHEN table.
	 * @return
	 * @throws DaoException
	 */
	int getDataRowCounter() throws DaoException;
	
	
	/**
	 * @description Method to add a new Lost and found article to the FUNDSACHEN table in the Database. 
	 * @param fundgegenstand
	 * @param userAHB
	 * @throws DaoException
	 */
	void addNewFundsachen(Fundgegenstand fundgegenstand, UserAHB userAHB) throws DaoException;
	
	
	/**
	 * @description Method to display a list of the Lost and found articles.
	 * @throws DaoException
	 */
	void displayListFundsachen() throws DaoException;
	
	
	/**
	 * @description Method to update the modified data in a selected row inside the JTable 
	 * @param fundgegenstand
	 * @throws DaoException
	 */
	void updateFundsachen(Fundgegenstand fundgegenstand) throws DaoException;
	
	
	/**
	 * Method to search entries in the DataBase where Date = date giving by the user.
	 * @throws DaoException
	 */
	void searchByDateFundsachen(Date dateItemsWasFound) throws DaoException;
	
	

}
