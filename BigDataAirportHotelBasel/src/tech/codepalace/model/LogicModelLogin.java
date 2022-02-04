package tech.codepalace.model;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.LoginDataUser;
import tech.codepalace.utility.PropertiesReader;
import tech.codepalace.view.frames.LoginUser;

public class LogicModelLogin {
	
	protected UserAHB userAHB;
	protected LoginUser loginUser;
	
	
	//Variables Login
	protected String userName = "", password="", abkuerzungMA="";
	protected char[] userAppTextArray, passwordAppTextArray, abkuerzungMATextArray;
	protected ImageIcon errorImg; 
	
	// Variables to write the Propertie File
	protected String urlPropertieName, urlPropertieValue, userNamePropertieName, userNamePropertieValue,
			passwordPropertieName, passwordPropertieValue, privilegePropertieName, abkuerzungPropertieName,
			abkuerzungPropertieValue;
	
	//Instance DataEncryption
	protected DataEncryption dataEncryptation;
	
	//New Instance PropertiesReader
	private PropertiesReader propertiesReader;
	
	//New Instance LoginDataUser
	protected LoginDataUser loginDataUser; 
	
	//Variable for the user privilege
	protected String privilegeUser;
	
	//Variable to indicate that the password is correct
	protected boolean passwordIsCorrect = false;
	
	
	
	
	
	
	
	public LogicModelLogin(LoginUser loginUser,  UserAHB userAHB) {
		this.loginUser = loginUser;
		this.userAHB = userAHB;
		
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
				
				//Data is empty then we call loginUser method.
//				loginUser();
		
		}else {
			
			//Set the values of the properties
			this.urlPropertieName = "db.url";
			this.privilegePropertieName = "db.privilege.user." + this.userName;
			this.abkuerzungPropertieName = "db.abkuerzungma.user" + this.userName;
			
			this.userNamePropertieName = "db.user." + this.userName;
			this.passwordPropertieName = "db.password.user." + this.userName;
			
			
			
			
			//we encrypt the data
			try {
				

				this.urlPropertieName = dataEncryptation.encryptData(this.urlPropertieName);
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
				this.loginDataUser.setUserNamePropertieName(this.userNamePropertieName);
				this.loginDataUser.setPasswordPropertieName(this.passwordPropertieName);
				this.loginDataUser.setPasswordEnteredByTheUser(this.password);
				this.loginDataUser.setPrivilegePropertieName(this.privilegePropertieName);
				this.loginDataUser.setAbkuerzungPropertieName(this.abkuerzungPropertieName);
				
				
				//We call the readPropertiesData method by passing the loginDataUser and userAHB instances as argument
				this.propertiesReader.readPropertiesData(this.loginDataUser, this.userAHB);
				
				
				//We check if the Password entered by the User is correct
				if (this.propertiesReader.isPasswordIsCorrect()) {
					

					//set the value of the privilege variable, first decrypt that value. We can after use it to grant more or less privilege to the user.
					this.privilegeUser = this.dataEncryptation.decryptData(this.userAHB.getPrivilege());
					
					
					//We set passwordIsCorrect as true
					this.passwordIsCorrect = true;
					
					
					//Close the Dialog Box
//					dialogLogin.dispose();
					this.loginUser.dispose();
					
					
					//Test
//					System.out.println("Users and passwords are correct");
				}else {
					
					//Password is not correct
					this.passwordIsCorrect = false;
					
					//reset values
					this.userName = "";
					this.loginUser.userLolingJTextField.setText("");
					this.loginUser.passwordField.setText("");
					
					//request focus
					this.loginUser.userLolingJTextField.requestFocus();
					
					//Test
//					System.out.println("Users and passwords are not correct");
					
					
				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}

			
		}
			
			
			
		
		
	}
	
	
	
	// We get the user privile
	public String getPrivilegeUser() {
		return privilegeUser;
	}
	
	// get password status
	public boolean isPasswordIsCorrect() {
		return passwordIsCorrect;
	}

	// set password status
	public void setPasswordIsCorrect(boolean passwordIsCorrect) {
		this.passwordIsCorrect = passwordIsCorrect;
	}
	
	
	
	/**
	 * return UserAHB
	 */
	public UserAHB getUserAHB() {
		
		return userAHB;
	}


}
