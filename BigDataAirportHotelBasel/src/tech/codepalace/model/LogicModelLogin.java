package tech.codepalace.model;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.LoginDataUser;
import tech.codepalace.utility.PropertiesReader;
import tech.codepalace.view.frames.LoginUser;

public class LogicModelLogin extends LogicModel {
	
	protected UserAHB userAHB;
	protected LoginUser loginUser;
	

	//Variables Login
	protected String userName = "", password="", abkuerzungMA="";
	protected char[] userAppTextArray, passwordAppTextArray, abkuerzungMATextArray;
	protected ImageIcon errorImg; 
	
	// Variables to write the Propertie File
	protected String urlPropertieName, urlPropertieValue, urlDataBaseBackupPropertieName, urlDataBaseBackupValue, userNamePropertieName, userNamePropertieValue,
			passwordPropertieName, passwordPropertieValue, privilegePropertieName, abkuerzungPropertieName,
			abkuerzungPropertieValue;
	
	//Instance DataEncryption
	protected DataEncryption dataEncryptation;
	
	//New Instance PropertiesReader
	private PropertiesReader propertiesReader;
	
	//New Instance LoginDataUser
	protected LoginDataUser loginDataUser; 
	

	//Variable to indicate that the password is correct
	protected boolean passwordIsCorrect = false;
	
	
	
	
	
	
	/**
	 * 
	 * @param loginUser
	 * @param logicModelStartFrame
	 * @description This constructor receive LoginUser and also a LogicModelStartFrame so we can use this instance to set the value UserAHB
	 */
	public LogicModelLogin(LoginUser loginUser) {
		this.loginUser = loginUser;

	
		
		this.errorImg= new ImageIcon(getClass().getResource("/img/error.png"));
		
		//Initialize DataEncryption
		this.dataEncryptation = new DataEncryption();
		
		
	}
	
	
	
	
	
	
	
	/**
	 * @description setLoginValue, this method will set the values, username and password entered by the user in the login dialog box.
	 * when this method is called, we must set the values for the properties that must be read from the configuration file.(property names and property values).
	 * 
	 * <br/><br/>
	 * <ul><li>The values we collect from the user that will be used for reading the properties(user name and password).</li>
	 * 
	 * <li>We have to use a class to temporarily set this login data.</li>
	 * 
	 * <li>We call the class that reads the properties from the configuration file. The class checks that the user exists in the 
	 * configuration file and that the passwords match, and if the passwords match then set the data in the users class.</li>
	 * 
	 * </ul>
	 */
	public void setLoginValue() {

	
		
			
			//We set the entered Username and Password
			this.userName = this.loginUser.getUserNameEntered();
			this.password = this.loginUser.getPasswordEntered();
			
			
			//we check that the data is not empty
			if(this.userName.equals("") || this.password.equals("")) {
				
				
				
				JOptionPane.showMessageDialog(null, "Benutzername oder Passwort dürfen nicht leer sein"
						   , "Geben Sie einen gültigen Benutzernamen und ein gültiges Passwort ein", JOptionPane.ERROR_MESSAGE, this.errorImg);
				
		
		}else {
			
			//Set the values of the properties
			this.urlPropertieName = "db.url";
			this.urlDataBaseBackupPropertieName = "db.url.backup";
			this.privilegePropertieName = "db.privilege.user." + this.userName;
			this.abkuerzungPropertieName = "db.abkuerzungma.user." + this.userName;
			
			this.userNamePropertieName = "db.user." + this.userName;
			this.passwordPropertieName = "db.password.user." + this.userName;
			
			
			
			
			//we encrypt the data
			try {
				

				this.urlPropertieName = dataEncryptation.encryptData(this.urlPropertieName);
				this.urlDataBaseBackupPropertieName = dataEncryptation.encryptData(this.urlDataBaseBackupPropertieName);
				this.privilegePropertieName = dataEncryptation.encryptData(this.privilegePropertieName);
				this.abkuerzungPropertieName = dataEncryptation.encryptData(this.abkuerzungPropertieName);
				this.userNamePropertieName = this.dataEncryptation.encryptData(userNamePropertieName);
				this.passwordPropertieName = this.dataEncryptation.encryptData(passwordPropertieName);
				this.password = this.dataEncryptation.encryptData(this.password);
				
				//Initialize the instance PropertiesReader
				this.propertiesReader = new PropertiesReader();
				
				
				//Initialize the instance LoginDataUser
				this.loginDataUser = new LoginDataUser();
				
				//Set the values
				this.loginDataUser.setUrlPropertieName(this.urlPropertieName);
				this.loginDataUser.setUrlDataBaseBackupPropertieName(this.urlDataBaseBackupPropertieName);
				this.loginDataUser.setUserNamePropertieName(this.userNamePropertieName);
				this.loginDataUser.setPasswordPropertieName(this.passwordPropertieName);
				this.loginDataUser.setPasswordEnteredByTheUser(this.password);
				this.loginDataUser.setPrivilegePropertieName(this.privilegePropertieName);
				this.loginDataUser.setAbkuerzungPropertieName(this.abkuerzungPropertieName);
				
				
				//We call the readPropertiesData method by passing the loginDataUser as argument
				this.propertiesReader.readPropertiesData(this.loginDataUser);
				
				
				//We check if the Password entered by the User is correct calling the Method isPasswordIsCorret by the propertiesReader Instance.
				if (this.propertiesReader.isPasswordIsCorrect()) {
					

					
					//We set passwordIsCorrect as true
					this.passwordIsCorrect = true;
					
					//We get the userAHB value from propertiesReader instance
//					this.userAHB = this.propertiesReader.getUserAHB();
					
					//We call super class for setting the UserAHB value.
					setUserAHB(this.propertiesReader.getUserAHB());
					
//					JOptionPane.showMessageDialog(null, "Usuario setLoginValue: " + getUserAHB().getUserName());
					
					//We set the value of userAHB at Class LogicModelStartFrame so we can get the values what we need to work from the Principal GUI.
//					this.logicModelStartFrame.setUserAHB(this.userAHB);
					
					//Close the Dialog Box
					this.loginUser.dispose();
					

				}else {
					
					//Password is not correct set the value to false.
					this.passwordIsCorrect = false;
					
					//reset values
					this.userName = "";
					this.loginUser.userLolingJTextField.setText("");
					this.loginUser.passwordField.setText("");
					
					//request focus
					this.loginUser.userLolingJTextField.requestFocus();
					
					
				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}

			
		}
			
			
			
		
		
	}
	
	
	// get password status
	public boolean isPasswordIsCorrect() {
		return passwordIsCorrect;
	}

	// set password status
	public void setPasswordIsCorrect(boolean passwordIsCorrect) {
		this.passwordIsCorrect = passwordIsCorrect;
	}
	
	

}
