package tech.codepalace.dao;

import java.sql.Date;

public interface DAOFitnessAbo {
	

	/**
	 * @description Method to check if the 
	 * @throws DaoException
	 */
	void checkTableFitnessAbo() throws DaoException;
	
	
	/**
	 * @description Method to display the Fitness Subscription Data. 
	 * @throws DaoException
	 */
	void displayFitnessAbo() throws DaoException;

}
