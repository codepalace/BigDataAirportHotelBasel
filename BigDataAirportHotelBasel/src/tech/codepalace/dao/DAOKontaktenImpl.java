package tech.codepalace.dao;

import tech.codepalace.model.LogicModelTelefonbuch;
import tech.codepalace.model.UserAHB;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description DAO impl Class for the Contact.
 *
 */
public class DAOKontaktenImpl implements DAOKontakten {
	
	private UserAHB userAHB;
	private DataBaseGUI dataBaseGUI;
	private static Loading loading;
	
	private static LogicModelTelefonbuch logicModelTelefonbuch;

	public DAOKontaktenImpl(UserAHB userAHB, DataBaseGUI dataBaseGUI, Loading loading, LogicModelTelefonbuch logicModelTelefonbuch) {
		
		this.userAHB = userAHB;
		this.dataBaseGUI = dataBaseGUI;
		DAOKontaktenImpl.loading = loading;
		DAOKontaktenImpl.logicModelTelefonbuch = logicModelTelefonbuch;
	}
	
	
	@Override
	public void checkTableKontanten() throws DaoException {
		
	}

	@Override
	public void displayKontakten() throws DaoException {
		
	}

}
