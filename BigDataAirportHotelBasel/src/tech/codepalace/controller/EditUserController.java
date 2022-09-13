package tech.codepalace.controller;

import tech.codepalace.model.LogicModelEditUser;
import tech.codepalace.view.frames.EditUserGUI;

/**
 * @description Controller Class for EditUserGUI
 * @author Antonio Estevez Gonzalez
 *
 */
public class EditUserController {
	
	private EditUserGUI editUserGUI;
	private LogicModelEditUser logicModelEditUser;
	
	public EditUserController(EditUserGUI editUserGUI, LogicModelEditUser logicModelEditUser) {
		
		this.editUserGUI = editUserGUI;
		this.logicModelEditUser = logicModelEditUser;
	}

}
