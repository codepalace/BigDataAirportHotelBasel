package tech.codepalace.model;

import tech.codepalace.dao.DAOFundsachen;
import tech.codepalace.dao.DaoException;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;


/**
 * @description DAO Pattern class
 * @author tonimacaroni
 *
 */
public class DaoFundsachenImpl implements DAOFundsachen {
	
	private UserAHB userAHB;
	private DataBaseGUI dataBaseGUI;
	private static Loading loading;
	
	
	public DaoFundsachenImpl(UserAHB userAHB, DataBaseGUI dataBaseGUI, Loading loading) {
		
		this.userAHB = userAHB;
		this.dataBaseGUI = dataBaseGUI;
		DaoFundsachenImpl.loading = loading;
		
		
		
	}
	
	
	

	@Override
	public void checkTableFundsachen() throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getDataRowCounter() throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addNewFundsachen(Fundgegenstand fundgegenstand, UserAHB userAHB) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayListFundsachen() throws DaoException {
		// TODO Auto-generated method stub
		
	}

}
