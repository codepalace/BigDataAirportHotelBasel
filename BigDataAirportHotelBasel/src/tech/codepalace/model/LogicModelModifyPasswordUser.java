package tech.codepalace.model;

import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.ModifyPasswordUserGUI;

public class LogicModelModifyPasswordUser {
	
	private ModifyPasswordUserGUI modifiyPasswordUserGUI;
	
	protected String oldPassword, newPassword, newPasswordConfirmation;
	
	//Instance DataEncryption
	private DataEncryption dataEncryption;
	
	public LogicModelModifyPasswordUser(ModifyPasswordUserGUI modifyPasswordUserGUI, String oldPassword) {
		
		this.modifiyPasswordUserGUI = modifyPasswordUserGUI;
		this.oldPassword = oldPassword;
		
		
	}

}
