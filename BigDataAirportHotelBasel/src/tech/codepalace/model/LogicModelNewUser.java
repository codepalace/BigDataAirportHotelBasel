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
		if(validateNewUserEntry(this.newUserString)) {
			JOptionPane.showMessageDialog(null, "User is correct");
		}
		
	}
	
	
	/**
	 * @description Method to validate the new User Entry. Return true or false depending of correct or not.
	 * @param newUserString
	 * @return
	 */
	private boolean validateNewUserEntry(String newUserString) {
		
		this.userNameArray = this.newUserString.toCharArray();
		
		if(this.newUserString.equalsIgnoreCase("")) {
			
			SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Benutzernamen ein"
					   , "Kritischer Fehler Benutzername", JOptionPane.ERROR_MESSAGE, this.errorImg));
			
			
			this.newUser.newUserJTextField.requestFocus();
			
			this.newUserEntryCorrect = false;
			
		}else {
			
			this.newUserEntryCorrect = true;
			
			//we check that no blank spaces are entered
			for(int i=0; i< this.userNameArray.length; i++) {
				
				char currentIndex = this.userNameArray[i];

				if(currentIndex == ' ') {

					this.newUserEntryCorrect = false;

				}
				
				if (i == userNameArray.length-1) {
					
					if (!this.newUserEntryCorrect) {
						
						SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Leerzeichen sind für den Benutzernamen nicht zulässig\n\nVersuchen Sie es erneut mit einem gültigen Benutzernamen "
								   , "Kritischer Fehler Benutzername", JOptionPane.ERROR_MESSAGE, this.errorImg));
						
						this.newUser.newUserJTextField.requestFocus();
					}
						
					}
					
					}
		}
		
		return newUserEntryCorrect;
		
	}

}
