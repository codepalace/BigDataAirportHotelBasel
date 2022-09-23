package tech.codepalace.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	
	//UserAHB instance for the logged in user
	private UserAHB userAHB;
	
	// Varialbles of the properties with which we have in the configuration file encrypted their name and also encrypted their values.
	private String urlPropertieName, urlDataBaseBackupPropertieName,  userNamePropertieName,  passwordPropertieName, privilegePropertieName, abkuerzungPropertieName, passwordUserEntered;
	
	//Variable to check if the entered password is correct
	public boolean passwordIsCorrect = false;
	
	
	//Variable for the correct Password
	private String correctPassword;
	
	//Instance Properties
	private Properties prop = new Properties();
	
	//ImageIcon for the Error Dialog Box
	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	
	//Empty Constructor
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
	public void readPropertiesData(LoginDataUser loginDataUser) {
		
		//We set the value of the parameter LoginDataUser
		this.loginDataUser = loginDataUser;


		//We set the different values
		this.urlPropertieName = this.loginDataUser.getUrlPropertieName(); 
		this.urlDataBaseBackupPropertieName = this.loginDataUser.getUrlDataBaseBackupPropertieName();
		this.userNamePropertieName = this.loginDataUser.getUserNamePropertieName();
		this.passwordPropertieName = this.loginDataUser.getPasswordPropertieName();
		this.passwordUserEntered = this.loginDataUser.getPasswordEnteredByTheUser();
		this.privilegePropertieName = this.loginDataUser.getPrivilegePropertieName();
		this.abkuerzungPropertieName = this.loginDataUser.getAbkuerzungPropertieName();
		
		
		
		
		
		//We proceed to read the configuration file. Configuration file is located in the same directory our Application is running.
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
	private void  checkUserName(String key) throws Exception {
			
			

			if(key== null) {
//				System.out.println("key: " + key + " is not a registered User Name");
				/*
				 * In case the user value entered by the user in the login box does not exist as the property value we call the loginAppError method
				 */
				loginAppError();
			}else {
//				System.out.println("User name is valid " + key);
				
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
//				System.out.println("La contrasena es correcta. Puedes entrar en el programa\n\n");
			
				//We create a new Instance of UserAHB so we can set all the information we need for the logged user.
				this.userAHB = new UserAHB();
				
				
				//The passwords match, then we set the values of userAHB Object
				this.userAHB.setUrlDataBase(this.prop.getProperty(this.urlPropertieName));
				this.userAHB.seturlDataBaseBackup(this.prop.getProperty(this.urlDataBaseBackupPropertieName));
				this.userAHB.setUserName(this.prop.getProperty(this.userNamePropertieName));
				this.userAHB.setPrivilege(this.prop.getProperty(this.privilegePropertieName));
				this.userAHB.setAbkuerzungMA(this.prop.getProperty(this.abkuerzungPropertieName));
				this.userAHB.setPassword(this.prop.getProperty(this.passwordPropertieName));
				

				//We set PasswordIsCorrect
				setPasswordIsCorrect(true);
				
//				System.out.println("Password Match you are almost ready to enter the Application");
				
			}else {
//				System.out.println("Password Not Match you are not allowed to enter the program.");
				
				//We call to display the Login Error DialogBox.
				loginAppError();
				
				//We set the value to false
				setPasswordIsCorrect(false);
			
				
			}
			
			
	}
	
	
	

	public boolean isPasswordIsCorrect() {
		return passwordIsCorrect;
	}



	private void setPasswordIsCorrect(boolean passwordIsCorrect) {
		this.passwordIsCorrect = passwordIsCorrect;
	}
	
	
	
	/**
	 * @description Method to get the current UserAHB values.
	 * @return
	 */
	public UserAHB getUserAHB() {
		return this.userAHB;
	}
	
	
	
	
	/**
	 * @description Method to return Properties. It will be return our config.properties
	 * @return
	 */
	public Properties readPropertiesData() {

		//We proceed to read the configuration file. Configuration file is located in the same directory our Application is running.
		//We load the properties file into an InputStream
		try (InputStream input = new FileInputStream(projectDirectoryString + File.separator + "config.properties")) {

			 //Instantiating the Properties class
             prop = new Properties();

            // load a properties file
            prop.load(input);
            
            

            
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
			e.printStackTrace();
		}
		return prop;

		
	}
	
	
	
	
	/**
	 * @description Method to read Properties from user in the configuration files. Will return an userAHB with the data we need.
	 * @param userAHB
	 * @return
	 */
	public UserAHB readPropertiesUserAHBToEdit(UserAHB userAHB) {
		
		//Set the value of the userAHB
		this.userAHB = userAHB;
		
		//Set the value of each properties name
		this.userNamePropertieName = this.userAHB.getUserName();
		this.abkuerzungPropertieName = this.userAHB.getAbkuerzungMA();
		this.privilegePropertieName = this.userAHB.getPrivilege();
		this.passwordPropertieName = this.userAHB.getPassword();
		
		
		//Now is time to read the configuration file and to get every values we need to set to the userAHB to be returned.
		try(InputStream input = new FileInputStream(projectDirectoryString + File.separator + "config.properties")) {
			
			//Initialize the Properties
			prop = new Properties();
			
			//Load the Properties
			prop.load(input);
			
			//Now we set the new values for the userAHB Instance
			this.userAHB.setUserName(this.prop.getProperty(this.userNamePropertieName));
			this.userAHB.setAbkuerzungMA(this.prop.getProperty(this.abkuerzungPropertieName));
			this.userAHB.setPrivilege(this.prop.getProperty(this.privilegePropertieName));
			this.userAHB.setPassword(this.prop.getProperty(this.passwordPropertieName));
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return this.userAHB;
		
	}
	
	
	

}
