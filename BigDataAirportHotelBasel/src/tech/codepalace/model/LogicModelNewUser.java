package tech.codepalace.model;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.PropertiesWriter;
import tech.codepalace.view.frames.NewUser;

/**
 * @description Logic(Model) Class for the NewUser Class
 * @author Antonio Estevez Gonzalez
 *
 */
public class LogicModelNewUser {
	
	private LogicModelUserManager logicModelUserManager;
	
	private NewUser newUser;
	
	//Variables to store temporarily the information entered by the user(Admin).
	private String newUserString="", passwordNewUserString="", userRights, userAbbreviationName;
	
	//Arrays used to delete the whitespace
	private char[] userNameArray, passwordArray;
	
	private boolean newUserEntryCorrect = false, passwordNewUserCorrect = false,
					newUserBlankSpace = false, blankSpacePassword = false;
	
	//Image error JOptionPane
	public ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	private String errorMessage="";
	
	
	//Instance PropertiesWriter to write new Properties
	private PropertiesWriter propertieswriter;
	
	//Instance of DataEncryption to encrypt the data we want to write in the configuration file
	DataEncryption dataEncryption;
	
	
		
	//Variables for the new user properties
	private String userNamePropertieName, userNamePropertieValue, passwordPropertieName, passwordPropertieValue, 
	privilegePropertieName, privilegePropertieValue, abkuerzungPropertieName, abkuerzungPropertieValue;
	
	public LogicModelNewUser(LogicModelUserManager logicModelUserManager, NewUser newUser) {
		
		this.logicModelUserManager = logicModelUserManager;
		
		this.newUser = newUser;
		
		
		
	}
	
	
	/**
	 * @description Method for calling to write new User in the configuration file throw PropertiesWriter
	 * @param newUserString
	 * @param passwordNewUserString
	 */
	public void saveNewUser(String newUserString, String passwordNewUserString, String userRights, String userAbbreviationName) {
		this.newUserString = newUserString;
		this.passwordNewUserString = passwordNewUserString;
		this.userAbbreviationName = userAbbreviationName;
		
		this.userRights = userRights;
		
		this.newUserBlankSpace = false;
		
		this.userNameArray = this.newUserString.toCharArray();
		
		this.passwordArray = this.passwordNewUserString.toCharArray();
		
		this.blankSpacePassword = false;
		
		this.passwordNewUserCorrect = false;
		 
		
		switch (this.userRights) {
			case "Manager":
				
				this.userRights = "ADMIN";

				break;
				
			case "Mitarbeiter":
				this.userRights = "STAFF";

				break;
		}
		
		this.userAbbreviationName = userAbbreviationName;
		
		//Check if every entries is OK before call for saving the information.
		if(checkNewUserEmpty() || checkBlankSpaceNewUser() || checkNewPasswordEmpty() || checkBlankSpacePassword()
			||	checkUserRightsWasNotSelected() || checkUserAbbreviationEmpty() || checkUserAbbreviationCorrectLength()) {

			// Invoke a new Thread with the error message.
			SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
			this.errorMessage,
			"Passwort kann nicht festgelegt werden", JOptionPane.ERROR_MESSAGE, this.errorImg));
		
		
		}else {
			
			//Every entries is OK we proceed to save the new user. 
			JOptionPane.showMessageDialog(null, "We can save the user!!");
//			proceedToSaveTheUser();
		}
		
		
	}
	
	
	/**
	 * @description Method to check if the user entered is empty or not.
	 * @return
	 */
	private boolean checkNewUserEmpty() {
		
		//if newwUserString is empty
		if(this.newUserString.equals("")) {
			
			//set the value for the errorMessage
			this.errorMessage = "der neue Benutzer darf nicht leer sein.";
			
			//return the focus to newUsesrJTextField.
			this.newUser.newUserJTextField.requestFocus();
			
			return true;
			
		}else {
			
			return false;
		}
		
	}
	
	
	
	/**
	 * @description Method to check if the new user contains blank spaces.
	 * @return
	 */
	private boolean checkBlankSpaceNewUser() {
		
		//we check that no blank spaces are entered
		for(int i=0; i< this.userNameArray.length; i++) {
			
			//currentIndex has the value from i actual position iterated by the Array.
			char currentIndex = this.userNameArray[i];

			//If we find a blank space
			if(currentIndex == ' ') {

				//Value is false
				this.newUserBlankSpace = true;

			}
			
			//Arrive to the end the Array
			if (i == userNameArray.length-1) {
				
				//If newUserEntryCorrect is not correct
				if (this.newUserBlankSpace) {
					
		
					this.newUserEntryCorrect = false;
					
					//We give back the focus.
//					this.newUser.newUserJTextField.requestFocus();
				}else {
					
					this.newUserEntryCorrect = true;
				}
					
				}
				
				}
		
		
		
				/*
				 * We found blank space also the neUserEntryCorrect is false
				 */
				if(!this.newUserEntryCorrect) {
					
					//Set the value for the error message.
					this.errorMessage = "Leerzeichen sind für Benutzer nicht erlaubt";
					
					//Reset the value for the newUserJTextField
					this.newUser.newUserJTextField.setText("");
					
					//return the focus for the newUserJTextField
					this.newUser.newUserJTextField.requestFocus();
					
					return true;

				}else {
					return false;
				}
				
	}
	
	
	
	
	
	
	/**
	 * @description Method to check if the  Password is empty or not.
	 * @return
	 */
	private boolean checkNewPasswordEmpty() {
		
		//If new password is empty
		if(this.passwordNewUserString.equals("")) {
			
			//Set the value for the errorMessage
			this.errorMessage = "Das Passwort darf nicht leer sein.";
			
			//return focus newPasswordField
			this.newUser.passwordField.requestFocus();
			return true;
		}else {
			return false;
		}
	}
	
	
	
	 /**
	   * @description Method to check if exists blank space by the new entered password.
	   * @return
	   */
	  private boolean checkBlankSpacePassword() {
		  
		//iterate the char Array to check if we have empty spaces by the newPassword. 
			for(int i=0; i< this.passwordArray.length; i++) {
				
				//Variable char store the current iterated index.
				char currentIndex = this.passwordArray[i];
				
				//if currentIdex is empty(space)
				if(currentIndex == ' ') {
					
					//We set the values
					this.blankSpacePassword = true;
		
					
				}
				
				//We arrive to the End the newPasswordCharArray
				if((i == passwordArray.length-1)) {
					
					//Evaluate if blankSpaceNewPassword = true 
					if(this.blankSpacePassword) {
						
						
					//set value password is not correct
					this.passwordNewUserCorrect = false;
					
					}else {
						//set value password is correct
						this.passwordNewUserCorrect = true;
					}
					
					
				}
				
			}
			
			
			
			//If the password is not correct 
			if(!this.passwordNewUserCorrect) {
				//set the value for the errorMessage
				this.errorMessage = "Leerzeichen sind für das Passwort nicht erlaubt";
				
				//Reset the value for the PasswordFields.
				this.newUser.passwordField.setText("");
				
				
				//Return the focus to newPasswordField
				this.newUser.passwordField.requestFocus();
				return true;
			}else {
				return false;
			}
	  }
	
	
	  
	  
	  /**
	   * @description Method to check the User rights if it was selected or not
	   */
	  private boolean checkUserRightsWasNotSelected() {
		  
		  if(this.userRights.equalsIgnoreCase("Benutzerrechte auswählen")) {
			  
			  this.errorMessage = "Sie müssen die Rechte des Benutzers konfigurieren";
			  return true;
			  
		  }else {
			  return false;
		  }
	  }
	  
	  
	  
	  
	  /**
		 * @description Method to check if the  Password is empty or not.
		 * @return
		 */
		private boolean checkUserAbbreviationEmpty() {
			
			if(this.userAbbreviationName.equalsIgnoreCase("")) {
				
				this.errorMessage = "Bitte geben Sie einen Abkürzungsnamen für den neuen Benutzer ein. das Kürzel MA darf nicht leer sein";
				
				this.newUser.abkuerzungMAPlaceHolderTextField.requestFocus();
				
				return true;
				
			}else {
				
				return false;
			}
			
			
		}
	
		
		
		/**
		 * @description Method to check if the user abbreviation name is at least 3 letters Length.
		 * @return
		 */
		private boolean checkUserAbbreviationCorrectLength() {
			
			if(this.userAbbreviationName.length()<3) {
				
				this.errorMessage = "Bitte geben Sie einen Abkürzungsname ein, der mindestens 3 Buchstaben lang ist.";
				
				this.newUser.abkuerzungMAPlaceHolderTextField.requestFocus();
				
				return true;
			
			}else {
				
				return false;
			}
			
		}
	
	
/**
 * @description Method to write in the configuration File the new User when everything is OK by entering the information required. 
 */
private void proceedToSaveTheUser() {	
	//Hora de guardar el nuevo usuario Propertieswriter archivo de configuracion.
	//Initialize the PropertiesWriter Instance
	this.propertieswriter = new PropertiesWriter();
	
	//Initialize the variables for the new User Properties. 
	this.userNamePropertieName = "db.user." + this.newUserString;
	
	//Set the userNamePropertieValue
	this.userNamePropertieValue = this.newUserString;
	
	//set also the value for passwordPropertieName
	this.passwordPropertieName = "db.password.user." + this.newUserString;
	
	//set the value for the passwordPropertieValue
	this.passwordPropertieValue = this.passwordNewUserString;
	
	//set the value for the privilegePropertieName
	this.privilegePropertieName = "db.privilege.user." + this.newUserString;
	
	//set the value for the privilegePropertieValue(User Rights).
	this.privilegePropertieValue = this.userRights;
	
	//set the abkuerzungPropertieName value
	this.abkuerzungPropertieName = "db.abkuerzungma.user" + this.newUserString;
	
	//set the value of abkuerzungPropertieValue(User Abbreviation Name).
	this.abkuerzungPropertieValue = this.userAbbreviationName;
	
	
	
	
	
	
	//Now is time to encrypt the values before calling the propertieswriter instance for writing in the configuration file.
	
	//Initialize the PropertiesWriter Instance.
	this.propertieswriter = new PropertiesWriter();
	
	//Initialize the DataEncryption instance.
	this.dataEncryption = new DataEncryption();
	
	
	//We add the New user to the list calling the addNewUserToTheList Method von logicModelUserManager
	this.logicModelUserManager.addNewUserToTheList(this.newUserString);
	
	//We encrypt the data and store them in the variables
	try {
		this.userNamePropertieName = this.dataEncryption.encryptData(this.userNamePropertieName);
		this.userNamePropertieValue = this.dataEncryption.encryptData(this.userNamePropertieValue);
		
		this.passwordPropertieName = this.dataEncryption.encryptData(this.passwordPropertieName);
		this.passwordPropertieValue = this.dataEncryption.encryptData(this.passwordPropertieValue);
		
		this.privilegePropertieName = this.dataEncryption.encryptData(this.privilegePropertieName);
		this.privilegePropertieValue = this.dataEncryption.encryptData(this.privilegePropertieValue);
		
		this.abkuerzungPropertieName = this.dataEncryption.encryptData(this.abkuerzungPropertieName);
		this.abkuerzungPropertieValue = this.dataEncryption.encryptData(this.abkuerzungPropertieValue);
		
	
		//We write the Properties in the configuration file
		this.propertieswriter.writeProperties(this.userNamePropertieName, this.userNamePropertieValue);
		this.propertieswriter.writeProperties(this.passwordPropertieName, this.passwordPropertieValue);
		this.propertieswriter.writeProperties(this.privilegePropertieName, this.privilegePropertieValue);
		this.propertieswriter.writeProperties(this.abkuerzungPropertieName, this.abkuerzungPropertieValue);


		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	
	
	this.newUser.dispose();
	
	
}
	
	

	
	
	


}
