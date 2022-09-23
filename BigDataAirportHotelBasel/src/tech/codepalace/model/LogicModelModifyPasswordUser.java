package tech.codepalace.model;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.PropertiesWriter;
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

	private String errorMessage = "";
	
	//Instance DataEncryption
	private DataEncryption dataEncryption;
	
	//Image error JOptionPane
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	//Instance PropertiesWriter to write the modifications properties.
	private PropertiesWriter propertiesWriter;
	
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
	
	
	public LogicModelModifyPasswordUser(ModifyPasswordUserGUI modifyPasswordUserGUI, String oldPassword,
			String passwordPropertyName, String privilegeWhoEditsUser) {

		//Set the received values
		this.modifiyPasswordUserGUI = modifyPasswordUserGUI;
		this.oldPassword = oldPassword;
		this.passwordPropertyName = passwordPropertyName;
		this.privilegeWhoEditsUser = privilegeWhoEditsUser;


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
		
		
		
		//If the password match is identical and not empty password.
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
				
//				System.out.println("New Password: " + this.newPassword);
//				System.out.println("Property to be stored: "+ this.passwordPropertyName );
				
				//Now we can call to write in the new value in the Properties inside the configuration File
				//Initialize the PropertiesWriter instance
				this.propertiesWriter = new PropertiesWriter();
				
				this.propertiesWriter.modifyAndWriteProperties(this.passwordPropertyName, this.newPassword);

				
				//Close Both GUIs
				this.modifiyPasswordUserGUI.dispose();
				
				if(this.editUserGUI!=null) {
					this.editUserGUI.dispose();
				}
				
				
				
				
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

		//Set the value for the passwords entered by the User
		this.oldPasswordEnteredByTheUser = oldPasswordEnteredByTheUser;
		this.newPassword = newPassword;
		this.newPasswordConfirmation = newPasswordConfirmation;
	
		//Initialize value false
		this.newPasswordCorrect = false;
		
		//Initialize value errorMessage
		this.errorMessage = "Fehlerursachen:";
		
		//Set the value for the newPasswordCharArray
		this.newPasswordCharArray = this.newPassword.toCharArray();
		
		this.blankSpaceNewPassword = false;
		
			
			//Call to check all error possibilities and we find errors, error message will be displayed.
			if(checkOldPassEnteredByTheUserEmpty() || checkNewPasswordEmpty() || checkNewPasswordConfirmationEmpty()
												   || checkOldPasswordMatch() || checkNewPasswordAndPasswordConfirmationMatch()
												   || checkBlankSpaceNewPassword()) {
				
				// Invoke a new Thread with the error message.
				SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
							this.errorMessage,
							"Passwort kann nicht festgelegt werden", JOptionPane.ERROR_MESSAGE, this.errorImg));
			}else {
			
				//Everything is OK then we call to modifyNewPsswordInConfigurationFile.
				modifyNewPasswordInConfigurationFile();
				
			
		}
		
		
	}

	
	

	/**
	 * @return the privilegeWhoEditsUser
	 */
	public String getPrivilegeWhoEditsUser() {
		return privilegeWhoEditsUser;
	}
	
	
	
	/**
	 * @description Method to check if the old password Field is empty or not.
	 * @return
	 */
	private boolean checkOldPassEnteredByTheUserEmpty() {
		
		//If old password entered by the user is empty
		if(this.oldPasswordEnteredByTheUser.equals("")) {
			//Set the value for the errorMessage
			this.errorMessage = "Das alte Passwort darf nicht leer sein.";
			//return focus to oldPasswordField.
			this.modifiyPasswordUserGUI.oldPasswordField.requestFocus();
			return true;
		}else {
			return false;
		}
	}
	
	
	
	/**
	 * @description Method to check if the new Password is empty or not.
	 * @return
	 */
	private boolean checkNewPasswordEmpty() {
		
		//If new password is empty
		if(this.newPassword.equals("")) {
			
			//Set the value for the errorMessage
			this.errorMessage = "Das neues Passwort darf nicht leer sein.";
			
			//return focus newPasswordField
			this.modifiyPasswordUserGUI.newPasswordField.requestFocus();
			return true;
		}else {
			return false;
		}
	}
	
	
	
	/**
	 * @description Method to check the password confirmation is empty or not.
	 * @return
	 */
  private boolean checkNewPasswordConfirmationEmpty() {
	  
	//If new password confirmation is empty
	  if (this.newPasswordConfirmation.equalsIgnoreCase("")) {

		  	//Set the value for the errorMessage
			this.errorMessage = "die neue Passwortbestätigung sollte nicht leer sein.";
			
			//return focus to newPasswordConfirmationField
			this.modifiyPasswordUserGUI.newPasswordConfirmationField.requestFocus();
			return true;
		} else {
			return false;
		}
  }
  
  
  
  
  /**
   * @description Method to check is the old password saved in the configuration file match with the old password entered by the user or not.
   * @return
   */
  private boolean checkOldPasswordMatch() {
	
	 //If the old password entered by the user do not match with the old password saved in the configuration file.
	if(!this.oldPassword.equals(this.oldPasswordEnteredByTheUser)) {
		
		//Set the value for the errorMessage
		this.errorMessage = "das eingegebene alte Passwort stimmt nicht überein.";
		
		//reset PasswordField text value
		this.modifiyPasswordUserGUI.oldPasswordField.setText("");
		
		//return focus to oldPasswordField.
		this.modifiyPasswordUserGUI.oldPasswordField.requestFocus();
		return true;
	} else {
		return false;
	}
	
  }
  
  
  
  
  /**
   * @description Method to check if the new password and password confirmation entered by the user match or not.
   * @return
   */
  private boolean checkNewPasswordAndPasswordConfirmationMatch() {
	  
	  //If the new password do not match with the new password confirmation
	  if(!this.newPassword.equals(this.newPasswordConfirmation)) {
		
		  //Set the value for the errorMessage
		  this.errorMessage = "die eingegebenen Passwörter stimmen nicht überein.";
		  
		  //reset PasswordFields text value
		  this.modifiyPasswordUserGUI.newPasswordField.setText("");
		  this.modifiyPasswordUserGUI.newPasswordConfirmationField.setText("");
		  
		  //Return the focus to newPasswordField.
		  this.modifiyPasswordUserGUI.newPasswordField.requestFocus();
			return true;
		} else {
			return false;
		}
	  
  }
  
  
  
  /**
   * @description Method to check if exists blank space by the new entered password.
   * @return
   */
  private boolean checkBlankSpaceNewPassword() {
	  
	//iterate the char Array to check if we have empty spaces by the newPassword. 
		for(int i=0; i< this.newPasswordCharArray.length; i++) {
			
			//Variable char store the current iterated index.
			char currentIndex = this.newPasswordCharArray[i];
			
			//if currentIdex is empty(space)
			if(currentIndex == ' ') {
				
				//We set the values
				this.blankSpaceNewPassword = true;
	
				
			}
			
			//We arrive to the End the newPasswordCharArray
			if((i == newPasswordCharArray.length-1)) {
				
				//Evaluate if blankSpaceNewPassword = true 
				if(this.blankSpaceNewPassword) {
					
					
				//set value password is not correct
				this.newPasswordCorrect = false;
				
				}else {
					//set value password is correct
					this.newPasswordCorrect = true;
				}
				
				
			}
			
		}
		
		
		
		//If the password is not correct 
		if(!this.newPasswordCorrect) {
			//set the value for the errorMessage
			this.errorMessage = "Leerzeichen sind für das Passwort nicht erlaubt";
			
			//Reset the value for the PasswordFields.
			this.modifiyPasswordUserGUI.newPasswordField.setText("");
			this.modifiyPasswordUserGUI.newPasswordConfirmationField.setText("");
			
			//Return the focus to newPasswordField
			this.modifiyPasswordUserGUI.newPasswordField.requestFocus();
			return true;
		}else {
			return false;
		}
  }
  
  
  
  
  
  
  
  
  /**
   * @description Method to modify the new entered password and saving in the configuration File(Properties).
   */
  private void modifyNewPasswordInConfigurationFile() {
	//if the password is correct we save the new value in the configuration file.

  }
	

}
