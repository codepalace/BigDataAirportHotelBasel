package tech.codepalace.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import tech.codepalace.model.UserAHB;

public class PropertiesReader extends Properties {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//root directory of our application
	private String projectDirectoryString = System.getProperty("user.dir");
	
	//necessary instances
	private LoginDataUser loginDataUser;
	private UserAHB userAHB;
	
	// Varialbles of the properties with which we have in the configuration file encrypted their name and also encrypted their values.
	private String urlPropertieName,  userNamePropertieName,  passwordPropertieName, privilegePropertieName, abkuerzungPropertieName, passwordUserEntered;
	
	//Variable to check if the entered password is correct
	public boolean passwordIsCorrect = false;
	
	
	
	private String correctPassword;
	
	//Instance Properties
	private Properties prop = new Properties();
	
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	
	public PropertiesReader() {}
	
	
	
	/**
	 * 
	 * @param loginDataUser
	 * @param userAHB
	 * @return userAHB
	 * 
	 * @description 
	 * 
	 * We get the name of the properties with the help of the loginDataUser class. The values of the loginDataUser class were set in the setLoginValue method by the 
	 * LogicModelStartFrame Class.
	 * 
	 *
	 * 
	 */
	public void readPropertiesData(LoginDataUser loginDataUser, UserAHB userAHB) {
		
		this.loginDataUser = loginDataUser;
		this.userAHB = userAHB;
		
		
		
		//We set the different values
		this.urlPropertieName = this.loginDataUser.getUrlPropertieName(); 
		this.userNamePropertieName = this.loginDataUser.getUserNamePropertieName();
		this.passwordPropertieName = this.loginDataUser.getPasswordPropertieName();
		this.passwordUserEntered = this.loginDataUser.getPasswordEnteredByTheUser();
		this.privilegePropertieName = this.loginDataUser.getPrivilegePropertieName();
		this.abkuerzungPropertieName = this.loginDataUser.getAbkuerzungPropertieName();
		
		
		
		
		
		//We proceed to read the configuration file.
		//We load the properties file into an InputStream
		try (InputStream input = new FileInputStream(projectDirectoryString + File.separator + "config.properties")) {

			 //Instantiating the Properties class
             prop = new Properties();

            // load a properties file
            prop.load(input);
            
            //we check the value you have stored in our property file(userName Properties)
            checkUserName(this.prop.getProperty(this.userNamePropertieName));

            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	
	
	
	
	
	/**
	 * 	
	 * @description Method to check the username. 
	 * <p>this method receives a key, which if null then no such user exists</p>
	 * @param key
	 * @throws Exception
	 * 
	 */
	private void   checkUserName(String key) throws Exception {
			
			

			if(key== null) {
				System.out.println("key: " + key + " is not a registered User Name");
				/*
				 * In case the user value entered by the user in the login box does not exist as the property value we call the loginAppError method
				 */
				loginAppError();
			}else {
				System.out.println("User name is valid " + key);
				
				/*
				 *The user is correct, we have to read the value of the password that has the file stored with this user as correct password  
				 */
				
				this.correctPassword = this.prop.getProperty(this.passwordPropertieName);

				
				//If the user exists in the properties file, we call to check the password if it is also correct.
				  checkPassword();
				  
			
			}
			
		}
	
	
	
	
	
	/**
	 * @description This method is used to display an error message in case the login data is not correct
	 * @throws Exception
	 */
	private void loginAppError() throws Exception {
		
		
		 JOptionPane.showMessageDialog(null, "Login ist nicht Korrekt. Bitte einlogen mit einen Richtige Benutzername und Password"
				   , "Wrong User Name Or Password", JOptionPane.ERROR_MESSAGE, this.errorImg);
		 
	}
	
	
	
	

	/**
	 * @description method to check if password match. 
	 * @throws Exception
	 */
	private void checkPassword() throws Exception {

		
			if(this.passwordUserEntered.equals(correctPassword)) {
				System.out.println("La contrasena es correcta. Puedes entrar en el programa\n\n");
				
		
				//The passwords match, then we set the values of userAHB Object
				this.userAHB.setUrlDataBase(this.prop.getProperty(this.urlPropertieName));
				this.userAHB.setUserName(this.prop.getProperty(this.userNamePropertieName));
				this.userAHB.setPrivilege(this.prop.getProperty(this.privilegePropertieName));
				this.userAHB.setAbbkuerzungMA(this.prop.getProperty(this.abkuerzungPropertieName));
				

				//We set PasswordIsCorrect
				setPasswordIsCorrect(true);
				
				System.out.println("Password Match you are almost ready to enter the Application");
				
			}else {
				System.out.println("Password Not Match you are not allowed to enter the program.");
				
				
				loginAppError();
				setPasswordIsCorrect(false);
			
				
			}
			
			
	}
	
	
	

	public boolean isPasswordIsCorrect() {
		return passwordIsCorrect;
	}



	private void setPasswordIsCorrect(boolean passwordIsCorrect) {
		this.passwordIsCorrect = passwordIsCorrect;
	}
	
	
	
	
	

}
