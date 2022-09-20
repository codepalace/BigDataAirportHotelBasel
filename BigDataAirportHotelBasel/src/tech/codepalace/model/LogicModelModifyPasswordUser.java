package tech.codepalace.model;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.EditUserGUI;
import tech.codepalace.view.frames.ModifyPasswordUserGUI;

public class LogicModelModifyPasswordUser {
	
	private ModifyPasswordUserGUI modifiyPasswordUserGUI;
	
	//Instance of EditUserGUI needed to access to this instance afer modifying the password.
	private EditUserGUI editUserGUI;
	
	protected String oldPassword, newPassword, newPasswordConfirmation, oldPasswordEnteredByTheUser, passwordPropertyName, privilegeWhoEditsUser;
	
	//char Array to evaluate if the newPassword has empty spaces.
	private char[] newPasswordCharArray;
	
	//boolean for the empty spaces newPassword. 
	private boolean blankSpaceNewPassword = false;
	
	//Variable to evaluate if the password match
	private boolean passwordMatch = false;
	
	//Variable to evaluate if the password is empty
	private boolean emptyPassword = false;
	
	//boolean for the new password correct.
	private boolean newPasswordCorrect = false;
	
	
	//Instance DataEncryption
	private DataEncryption dataEncryption;
	
	//Image error JOptionPane
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	public LogicModelModifyPasswordUser(ModifyPasswordUserGUI modifyPasswordUserGUI, String oldPassword, String passwordPropertyName,
											String privilegeWhoEditsUser, EditUserGUI editUserGUI) {
		
		//Set the received values
		this.modifiyPasswordUserGUI = modifyPasswordUserGUI;
		this.oldPassword = oldPassword;
		this.passwordPropertyName = passwordPropertyName;
		this.privilegeWhoEditsUser = privilegeWhoEditsUser;
		this.editUserGUI = editUserGUI;
		
		//Initialize dataEncryption instance
		this.dataEncryption = new DataEncryption();
		
		//Call method to decrypt data.
		decryptData();

		
	}
	
	
	/**
	 * @description Method to decrypt the old password or current user password.
	 */
	protected void decryptData() {
		
		
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
		
		//Set the value for the newPasswordCharArray
		this.newPasswordCharArray = this.newPassword.toCharArray();
		
		this.passwordMatch = false;
		
		this.blankSpaceNewPassword = false;
		
		this.newPasswordCorrect = false;
		
		//Evaluate if the newPassword and newPasswordConfirmation are not identical.
		if(!this.newPassword.equalsIgnoreCase(this.newPasswordConfirmation)) {
			
			//Invoke a new Thread with the error message.
			SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "die eingegebenen passwörter stimmen nicht überein. bitte versuchen Sie es nochmal"
					   , "Passwörter stimmen nicht überein", JOptionPane.ERROR_MESSAGE, this.errorImg));
			
			this.passwordMatch = false;
			this.newPasswordCorrect = false;
			
		}else {
			this.passwordMatch = true;
			this.newPasswordCorrect = true;
			
		}
		
		
		
		//Evaluate if the new password is not empty
		if (this.newPassword.equalsIgnoreCase("")) {

			// Invoke a new Thread with the error message.
			SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
					"Das Passwort darf nicht leer sein. bitte versuchen Sie es nochmal", "Kein Passwort Eingegeben",
					JOptionPane.ERROR_MESSAGE, this.errorImg));
			
			this.emptyPassword = true;
			this.newPasswordCorrect = false;
		
		}else {
			this.emptyPassword = false;
			this.newPasswordCorrect = true;
		}
		
		
		
		//If the password match is not identical and not empty password.
		if(this.passwordMatch && !this.emptyPassword) {
			
			//iterate the char Array to check if we have empty spaces by the newPassword. 
			for(int i=0; i< this.newPasswordCharArray.length; i++) {
				
				//Variable char store the current iterated index.
				char currentIndex = this.newPasswordCharArray[i];
				
				//if currentIdex is empty(space)
				if(currentIndex == ' ') {
					
					//We set the values
					this.blankSpaceNewPassword = true;
					this.newPasswordCorrect = false;
				}
				
				
				//We arrive to the End the newPasswordCharArray
				if((i == newPasswordCharArray.length-1)) {
					
					//Evaluate if blankSpaceNewPassword = true 
					if(this.blankSpaceNewPassword) {
						
						//New Thread to invoke and display an error message
						SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Leerzeichen sind für die Passwort nicht zulässig\n\nVersuchen Sie es erneut mit einem gültigen Passwort "
								   , "Kritischer Fehler Passwort Eingabe", JOptionPane.ERROR_MESSAGE, this.errorImg));	
					
						
					//set value
					this.newPasswordCorrect = false;
					}
					
					
				}
			}
		}
		
		
		//If everything is OK with the password
		if(this.newPasswordCorrect && this.passwordMatch && !this.emptyPassword) {
			
//			System.out.println("New Password: " + this.newPassword);
//			System.out.println("Property to be stored: "+ this.passwordPropertyName );
			
			try {
				
				//encrypt the data
				this.newPassword = this.dataEncryption.encryptData(this.newPassword);
				this.passwordPropertyName = this.dataEncryption.encryptData(this.passwordPropertyName);
				
				System.out.println("New Password: " + this.newPassword);
				System.out.println("Property to be stored: "+ this.passwordPropertyName );
				
				//Now we can call to write in the new value in the Properties inside the configuration File
				
				//Close Both GUIs
				this.modifiyPasswordUserGUI.dispose();
				this.editUserGUI.dispose();
				
				
				
			} catch (Exception e) {
				
				e.printStackTrace();
			}
			
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
