package tech.codepalace.model;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

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
	private String newUserString="", passwordNewUserString="";
	
	//Arrays used to delete the whitespace
	private char[] userNameArray, passwordArray;
	
	private boolean newUserEntryCorrect = false, passwordNewUserCorrect = false;
	
	//Image error JOptionPane
	public ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	public LogicModelNewUser(LogicModelUserManager logicModelUserManager, NewUser newUser) {
		
		this.logicModelUserManager = logicModelUserManager;
		
		this.newUser = newUser;
		
		
		
	}
	
	
	/**
	 * @description Method for calling to write new User in the configuration file throw PropertiesWriter
	 * @param newUserString
	 * @param passwordNewUserString
	 */
	public void saveNewUser(String newUserString, String passwordNewUserString) {
		this.newUserString = newUserString;
		this.passwordNewUserString = passwordNewUserString;
		
		//We evaluate if the User Entry is correct in the next commit we validate also the password i go for the ordinary Job.
		if(validateNewUserEntry(this.newUserString) && validatePasswordNewUserEntry(this.passwordNewUserString)) {
			JOptionPane.showMessageDialog(null, "User is correct");
		}
		
	}
	
	
	/**
	 * @description Method to validate the new User Entry. Return true or false depending of correct or not.
	 * @param newUserString
	 * @return
	 */
	private boolean validateNewUserEntry(String newUserString) {
		
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
	private boolean validatePasswordNewUserEntry(String passwordNewUserString) {
		
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

}
