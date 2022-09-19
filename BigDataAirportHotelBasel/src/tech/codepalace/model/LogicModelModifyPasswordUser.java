package tech.codepalace.model;

import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.ModifyPasswordUserGUI;

public class LogicModelModifyPasswordUser {
	
	private ModifyPasswordUserGUI modifiyPasswordUserGUI;
	
	protected String oldPassword, newPassword, newPasswordConfirmation, oldPasswordEnteredByTheUser, passwordPropertyName, privilegeWhoEditsUser;
	
	//Instance DataEncryption
	private DataEncryption dataEncryption;
	
	public LogicModelModifyPasswordUser(ModifyPasswordUserGUI modifyPasswordUserGUI, String oldPassword, String passwordPropertyName, String privilegeWhoEditsUser) {
		
		this.modifiyPasswordUserGUI = modifyPasswordUserGUI;
		this.oldPassword = oldPassword;
		this.passwordPropertyName = passwordPropertyName;
		this.privilegeWhoEditsUser = privilegeWhoEditsUser;
		
		decryptData();

		
	}
	
	
	/**
	 * @description Method to decrypt the old password or current user password.
	 */
	protected void decryptData() {
		
		this.dataEncryption = new DataEncryption();
		
		try {
			this.oldPassword = this.dataEncryption.decryptData(this.oldPassword);
			this.passwordPropertyName = this.dataEncryption.decryptData(this.passwordPropertyName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	/**
	 * @description Method to modify the old password for a new one.
	 * <p>This Method will be called only by the ADMIN user.</p>
	 * @param newPassord
	 * @param newPasswordConfirmation
	 */
	public void modifyPasswordUser(String newPassword, String newPasswordConfirmation) {

		this.newPassword = newPassword;
		this.newPasswordConfirmation = newPasswordConfirmation;

	}
	
	
	
	
	/**
	 * @description Method to modify the old password for a new one. 
	 * <p>This Method will be called only when the user who wants to modify the password is one STAFF.</p>
	 * @param oldPasswordEnteredByTheUser
	 * @param newPassword
	 * @param newPasswordConfirmation
	 */
	public void modifyPasswordUser(String oldPasswordEnteredByTheUser, String newPassword, String newPasswordConfirmation) {

		
		this.oldPasswordEnteredByTheUser = oldPasswordEnteredByTheUser;
		this.newPassword = newPassword;
		this.newPasswordConfirmation = newPasswordConfirmation;

		
		
	}


	/**
	 * @return the privilegeWhoEditsUser
	 */
	public String getPrivilegeWhoEditsUser() {
		return privilegeWhoEditsUser;
	}
	
	
	

}
