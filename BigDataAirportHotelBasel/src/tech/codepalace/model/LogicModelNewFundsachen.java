package tech.codepalace.model;

import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewFundsachen;


/**
 * @description Logic Class for the NewFundsachen GUI.
 * @author Antonio Estevez Gonzalez
 *
 */
public class LogicModelNewFundsachen {
	
	private DataBaseGUI dataBaseGUI;
	private NewFundsachen newFundsachen; 
	private UserAHB userAHB;
	private Loading loading;
	
	

	public LogicModelNewFundsachen(DataBaseGUI dataBaseGUI, NewFundsachen newFundsachen, UserAHB userAHB, Loading loading) {
		
		this.dataBaseGUI = dataBaseGUI;
		this.newFundsachen = newFundsachen;
		this.userAHB = userAHB;
		this.loading = loading;
		
		
	}

}
