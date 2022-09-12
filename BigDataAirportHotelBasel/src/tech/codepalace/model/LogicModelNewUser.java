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
	private char[] userNameArray, passwordArray, userAbbreviationNameArray;
	
	private boolean newUserEntryCorrect = false, passwordNewUserCorrect = false, newUserRightsCorrect = false, userAbbreviationNameCorrect;
	
	//Image error JOptionPane
	public ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	
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
		
		this.userRights = userRights;
		
		switch (this.userRights) {
			case "Manager":
				
				this.userRights = "ADMIN";

				break;
				
			case "Mitarbeiter":
				this.userRights = "STAFF";

				break;
		}
		
		this.userAbbreviationName = userAbbreviationName;
		
		//We evaluate if the User Entry is correct in the next commit we validate also the password i go for the ordinary Job.
		if(validateNewUserEntry() && validatePasswordNewUserEntry()
				&& validateNewUserRights() && validateNewUserAbbreviationName()) {
//			JOptionPane.showMessageDialog(null, "User is correct");
			
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
	
	
	/**
	 * @description Method to validate the new User Entry. Return true or false depending of correct or not.
	 * @param newUserString
	 * @return
	 */
	private boolean validateNewUserEntry() {
		
		//We store the value in one array so we can check for empty space.
		this.userNameArray = this.newUserString.toCharArray();
		
		//If the user do not enter any username.
		if(this.newUserString.equalsIgnoreCase("")) {
			
			
			//Invoke a new Thread with the error message.
			SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Benutzernamen ein"
					   , "Kritischer Fehler Benutzername", JOptionPane.ERROR_MESSAGE, this.errorImg));
			
			
			//We set the Focus back to the newUserJTextField.
			this.newUser.newUserJTextField.requestFocus();
			
			//Set the value as false.
			this.newUserEntryCorrect = false;
			
		}else {  //Otherwise
			
			//For the moment newUserEntryCorrect value = true.
			this.newUserEntryCorrect = true;
			
			//we check that no blank spaces are entered
			for(int i=0; i< this.userNameArray.length; i++) {
				
				//currentIndex has the value from i actual position iterated by the Array.
				char currentIndex = this.userNameArray[i];

				//If we find a blank space
				if(currentIndex == ' ') {

					//Value is false
					this.newUserEntryCorrect = false;

				}
				
				//Arrive to the end the Array
				if (i == userNameArray.length-1) {
					
					//If newUserEntryCorrect is not correct
					if (!this.newUserEntryCorrect) {
						
						
						//We invoke a new Thread with the error message.
						SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Leerzeichen sind für den Benutzernamen nicht zulässig\n\nVersuchen Sie es erneut mit einem gültigen Benutzernamen "
								   , "Kritischer Fehler Benutzername", JOptionPane.ERROR_MESSAGE, this.errorImg));
						
						//We give back the focus.
						this.newUser.newUserJTextField.requestFocus();
					}
						
					}
					
					}
		}
		
		//returning the value.
		return newUserEntryCorrect;
		
	}
	
	
	
	
	
	
	
	/**
	 * @description 
	 * @param passwordNewUserString
	 * @return
	 */
	private boolean validatePasswordNewUserEntry() {
		
		//By this method is the same process as the Method validateNewUserEntry only with the different is for checking the password entered by the user.
		
		this.passwordArray = this.passwordNewUserString.toCharArray();
		
		
	if(this.passwordNewUserString.equalsIgnoreCase("")) {
			
			SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Bite geben Sie ein Passwort für den Administrator ein. das Passwortfeld darf nicht leer sein"
					   , "Kritischer Fehler Passwort", JOptionPane.ERROR_MESSAGE, this.errorImg));
			
			
			this.newUser.passwordField.requestFocus();
			
			this.passwordNewUserCorrect = false;
			
		}else {
			
			this.passwordNewUserCorrect = true;
			
			//we check that no blank spaces are entered
			for(int i=0; i< this.passwordArray.length; i++) {
				
				char currentIndex = this.passwordArray[i];

				if(currentIndex == ' ') {

					this.passwordNewUserCorrect = false;

				}
				
				if (i == passwordArray.length-1) {
					
					if (!this.passwordNewUserCorrect) {
						
						SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Leerzeichen sind für das Passwort nicht zulässig\\n\\nVersuchen Sie es erneut mit einem gültigen Passwort "
								   , "Kritischer Fehler Passwort", JOptionPane.ERROR_MESSAGE, this.errorImg));
						
						this.newUser.passwordField.requestFocus();
					}
						
					}
					
					}
		}
		
		
		
		return passwordNewUserCorrect;
		
		
	}
	
	
	
	/**
	 * @description Method to validate if the new User Rights was selected to be modified
	 * @param userRights
	 * @return
	 */
	private boolean validateNewUserRights() {
		
		if(this.userRights.equalsIgnoreCase("Benutzerrechte auswählen")) {
			
			//We invoke a new Thread with the error message.
			SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Sie müssen die Rechte des Benutzers konfigurieren"
					   , "Kritischer Fehler Benutzerrechten", JOptionPane.ERROR_MESSAGE, this.errorImg));
		
		}else {
			this.newUserRightsCorrect = true;
		}
		
		return this.newUserRightsCorrect;
	}
	
	
	
	
	
	
	
	/**
	 * @description Method to validate if the new User Abbreviation name was correct entered.
	 * @param userAbbreviationName
	 * @return
	 */
	private boolean validateNewUserAbbreviationName() {
		
		this.userAbbreviationNameArray = this.userAbbreviationName.toCharArray();
		
		
		if(this.userAbbreviationName.equalsIgnoreCase("")) {
				
				SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Abkürzungsnamen für den neuen Benutzer ein. das Kürzel MA darf nicht leer sein"
						   , "Kritischer Fehler Kürzel MA", JOptionPane.ERROR_MESSAGE, this.errorImg));
				
				
				this.newUser.abkuerzungMAJTextField.requestFocus();
				
				this.userAbbreviationNameCorrect = false;
				
			}else {
				
				this.userAbbreviationNameCorrect = true;
				
				//we check that no blank spaces are entered
				for(int i=0; i< this.userAbbreviationNameArray.length; i++) {
					
					char currentIndex = this.userAbbreviationNameArray[i];

					if(currentIndex == ' ') {

						this.userAbbreviationNameCorrect = false;

					}
					
					if (i == userAbbreviationNameArray.length-1) {
						
						if (!this.userAbbreviationNameCorrect) {
							
							SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Leerzeichen sind für das Abkürzungsnamen nicht zulässig\\n\\nVersuchen Sie es erneut mit einem gültigen Abkürzungsnamen "
									   , "Kritischer Fehler Kürzel MA", JOptionPane.ERROR_MESSAGE, this.errorImg));
							
							this.newUser.abkuerzungMAJTextField.requestFocus();
						}
							
						}
						
						}
			}
		
		return this.userAbbreviationNameCorrect;
	}

}
