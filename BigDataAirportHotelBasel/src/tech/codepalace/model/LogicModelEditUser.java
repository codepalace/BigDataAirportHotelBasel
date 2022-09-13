package tech.codepalace.model;

import tech.codepalace.view.frames.EditUserGUI;


/**
 * @description Logic Class for EditUserGUI
 * @author Antonio Estevez Gonzalez
 *
 */
public class LogicModelEditUser {

	private LogicModelUserManager logicModelUserManager;
	private EditUserGUI editUserGUI;
	
	public LogicModelEditUser(LogicModelUserManager logicModelUserManager, EditUserGUI editUserGUI) {
		
		this.logicModelUserManager = logicModelUserManager;
		this.editUserGUI = editUserGUI;
		
	}

}
