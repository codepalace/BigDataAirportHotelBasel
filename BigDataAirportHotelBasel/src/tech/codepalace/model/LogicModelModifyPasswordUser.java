package tech.codepalace.model;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.ModifyPasswordUserGUI;

public class LogicModelModifyPasswordUser {
	
	private ModifyPasswordUserGUI modifiyPasswordUserGUI;
	
	protected String oldPassword, newPassword, newPasswordConfirmation, oldPasswordEnteredByTheUser, passwordPropertyName, privilegeWhoEditsUser;
	
	//Instance DataEncryption
	private DataEncryption dataEncryption;
	
	//Image error JOptionPane
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
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
		
		
		//Evaluate if the newPassword and newPasswordConfirmation are not identical.
		if(!this.newPassword.equalsIgnoreCase(this.newPasswordConfirmation)) {
			
			//Invoke a new Thread with the error message.
			SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "die eingegebenen passwörter stimmen nicht überein. bitte versuchen Sie es nochmal"
					   , "Passwörter stimmen nicht überein", JOptionPane.ERROR_MESSAGE, this.errorImg));
			
		}else {
			
			//Passwords are identical. We have to save the new value for the properties in Configuration File
			
		}

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
