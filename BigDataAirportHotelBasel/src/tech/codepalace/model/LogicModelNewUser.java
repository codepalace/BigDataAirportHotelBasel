package tech.codepalace.model;

import javax.swing.JOptionPane;

/**
 * @description Logic(Model) Class for the NewUser Class
 * @author Antonio Estevez Gonzalez
 *
 */
public class LogicModelNewUser {
	
	private LogicModelUserManager logicModelUserManager;
	
	//Variables to store temporarily the information entered by the user(Admin).
	private String newUserString="", passwordNewUserString="";
	
	public LogicModelNewUser(LogicModelUserManager logicModelUserManager) {
		
		this.logicModelUserManager = logicModelUserManager;
		
		
		
	}
	
	
	/**
	 * @description Method for calling to write new User in the configuration file throw PropertiesWriter
	 * @param newUserString
	 * @param passwordNewUserString
	 */
	public void saveNewUser(String newUserString, String passwordNewUserString) {
		this.newUserString = newUserString;
		this.passwordNewUserString = passwordNewUserString;
		
		//For the moment it will be only displayed the information entered by the user.  Instructions for this method are not finished jet.
		JOptionPane.showMessageDialog(null, "Ready to save New User: " + this.newUserString + " Password New User: " + this.passwordNewUserString);
		
	}

}
