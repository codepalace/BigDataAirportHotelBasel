package tech.codepalace.model;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import tech.codepalace.controller.ConfigurationDirectoryController;
import tech.codepalace.controller.LoginController;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.LoginDataUser;
import tech.codepalace.utility.PropertiesWriter;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.ConfigurationDirectory;
import tech.codepalace.view.frames.LoginUser;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description This class will contain most of the main frame logic
 *
 */
public class LogicModelStartFrame extends LogicModel {
	
		//Path of the project where the configuration file will be located
		protected String projectDirectoryString = System.getProperty("user.dir");
		
		//File variable that stores the path with the name of the application properties file.
		protected File configurationFile = new File(projectDirectoryString + File.separator + "config.properties");
		
		private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
		

		protected File fileDataBaseFolder = null; 
		protected File fileDataBaseBackupDirectory = null;
		protected JPanel dialogJPanel; //custom dialog box
		protected JButton[] optionButtons;
		protected String urlDataBase;
		protected String urlDataBaseBackup;
		protected ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png")); //image for error dialog box
		
		
		public JTextField userAdminJTextField;
		
		public JTextField kuerselMAJTextField;
		
		public JPasswordField passwordField;


		public JButton okButtonAdmin, abbrechenJButton;
		
		protected String userName = "", password="", abkuerzungMA="";
		protected char[] userAppTextArray, passwordAppTextArray, abkuerzungMATextArray;
		
		
		//Admin privile for the Propertie File
		protected String privilegePropertieValue;
		
		
		
		//Instance DataEncryption
		protected DataEncryption dataEncryptation;
		
		
		//Variables to write the Propertie File
		protected String urlPropertieName, urlPropertieValue, userNamePropertieName, userNamePropertieValue, 
		passwordPropertieName, passwordPropertieValue, privilegePropertieName, 
		abkuerzungPropertieName, abkuerzungPropertieValue;
		
		
		//Instance PropertiesWriter
		protected PropertiesWriter propertiesWriter;
		
		
		protected  LoginController loginController;
		private LoginUser loginUser;
		protected LogicModelLogin logicModelLogin;
		
		//Variables for the login dialog box
		public JDialog dialogLogin;
		protected JButton[] optionButtonsLogin;
		public JButton loginButton, cancelLoginButton;
		protected JTextField userLoginJTextField;
		public JPasswordField passwordLoginJPasswordField; 
		
		//New Instance UserAHB
		private UserAHB userAHB;
		
		//New Instance LoginDataUser
		protected LoginDataUser loginDataUser; 
		
		
		public LogicModelStartFrame(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, UserAHB userAHB) {
			
			this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
			this.userAHB = userAHB;
			
			this.loginUser = new LoginUser(this.bigDataAirportHotelBaselStartFrame, true);
			
			this.okButtonAdmin = new JButton("Save Admin");
			this.abbrechenJButton = new JButton("Abbrechen");
			this.kuerselMAJTextField = new JTextField(20);
			this.userAdminJTextField = new JTextField(20);
			this.passwordField = new JPasswordField(20);

			//Initialize DataEncryption
			this.dataEncryptation = new DataEncryption();
			
			
			//Initialize the JButtons and the JPasswordField to call from Controller Class
			this.passwordLoginJPasswordField = new JPasswordField(20);
			this.loginButton = new JButton("Login");
			this.cancelLoginButton = new JButton("Abbrechen");
			

			
		}
		
		
		/**
		 * 
		 * @throws Exception
		 * 
		 * @description Method to check if the configuration file is found created or is
		 *              the first time that we launch our application and we have create
		 *              a new Admin user with your password to the first launch of the
		 *              program and save the file setting.
		 * 
		 *              <p>
		 *              When the configuration file is saved next time we launch the
		 *              application will no longer need to create the configuration
		 *              file. will proceed to call other methods where you will ask to
		 *              enter username and password.
		 *              </p>
		 * 
		 * 
		 */
		public void checkConfigurationFileProperties() throws Exception {


			//We call the method that checks the configuration of the application
			existsConfigurationFile(configurationFile);
		}

		
		
		
		/**
		 * 
		 * @param configurationFile
		 * @throws Exception
		 * @Description 
		 * 
		 * <p>This method will check if the configuration file exists. In case it exists it will call the login method.</p>
		 * 
		 * <p>If the configuration file does not exist it will call the method that sets the local database path.</p>
		 */
		protected void existsConfigurationFile(File configurationFile) throws Exception {

			if (configurationFile.exists()) {

			  //The configuration file exists, we proceed to the login
				
				
				/*
				 * If the logicModelLogin instance has not yet been created, its value is null, then we create it, we also create an instance of the controller class
				 */
				if(logicModelLogin==null) {
							logicModelLogin = new LogicModelLogin(loginUser, userAHB);
							loginController = new LoginController(loginUser, userAHB, bigDataAirportHotelBaselStartFrame, logicModelLogin);
							loginUser.setLocationRelativeTo(bigDataAirportHotelBaselStartFrame);
							loginUser.setVisible(true);

				}else {
					
					/*
					 * On the contrary, if it exists, because we have made a logout of the application to cite an example, 
					 * then we simply reset the values userName, privilege, password.
					 */
					JOptionPane.showMessageDialog(null, "LogicModelLogin no es null: existsconfigurationfile" + this.logicModelLogin);
					userAHB.setUserName("");
					userAHB.setPrivilege("");
					userAHB.setPassword("");
					loginUser.setVisible(true);
				}

			} else {



				
				/*
				 * The configuration file does not exist. we created an instance of ConfigurationDirectory class, This instance inherits from the class JDialog, 
				 * which is going to allow me to lock the GUI BigDAtaAirportHotelBaselStartFrame while the user has not performed the actions he has to do in this instance.
				 * 
				 * The ConfigurationDirectory Class, it receives as a parameter the GUI or JFrame that has to be blocked as long as it does not close completely. 
				 * As a second argument it receives a boolean to indicate that it is going to block as a Modal.	
				 * 
				 * All calls to new instances are made within new Thread independent.
				 * 
				 * 
				 * We also create an instance of the class LogicModelConfigurationDirectory, Inside this class all the logic of the ConfigurationDirectory class will be worked. 
				 * LogicModelConfigurationDirectory Receives as a parameter the configurationDirectory instance and the bigDataAirportHotelBaselStartFrame instance already present in this class where we are.
				 * 
				 * bigDataAirportHotelBAselStartFrame parameter will be used to forward it to Another Class named AdminCreator that will be called after setting up the Database and the BackupDataBase Directories.
				 * 
				 * We also call for a new ConfigurationDirectoryController class for controlling the ConfigurationDirectori samll GUI Class.
				 * 
				 * 
				 * 
				 */
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						ConfigurationDirectory configurationDirectory = new ConfigurationDirectory(bigDataAirportHotelBaselStartFrame, true);
						LogicModelConfigurationDirectory logicModelConfigurationDirectory = new LogicModelConfigurationDirectory(configurationDirectory, bigDataAirportHotelBaselStartFrame, userAHB);
						
						
						 new ConfigurationDirectoryController(configurationDirectory, logicModelConfigurationDirectory);
						
						configurationDirectory.setVisible(true);
					}
				});
			}

		}
		
		
		





		@Override
		public void logoutApplication() {
			
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					bigDataAirportHotelBaselStartFrame.loginUserText.setText("Benutzer: ");

					
					//Disable the admin Buttons and makes them not visible.
					bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.setVisible(false);
					bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.setEnabled(false);
					bigDataAirportHotelBaselStartFrame.btn_createDB.setVisible(false);
					bigDataAirportHotelBaselStartFrame.btn_createDB.setEnabled(false);
					
					/*
					 * In this case as we are calling from the main window we do not need to hide this JFrame(BigaDataAirportHotelBaselStartFrame) 
					 * we just need to make the login window visible and reset the values.
					 */
					
					userAHB.setUserName("");
			        userAHB.setPrivilege("");
			        userAHB.setPassword("");
					loginUser.userLolingJTextField.setText("");
					loginUser.passwordField.setText("");

					
					
					if(logicModelLogin==null) {
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								logicModelLogin = new LogicModelLogin(loginUser, userAHB);
								loginController = new LoginController(loginUser, userAHB, bigDataAirportHotelBaselStartFrame, logicModelLogin);
								loginUser.setLocationRelativeTo(bigDataAirportHotelBaselStartFrame);
								loginUser.setVisible(true);
								
								
							}
						});
					}else {
						
						userAHB.setUserName("");
						userAHB.setPrivilege("");
						userAHB.setPassword("");
						loginUser.setVisible(true);
					}

				}
				
			});
			

			
			
			
		}


		@Override
		public void displayParking(JFrame parkingFrameToVisible, JFrame jframeToClose) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void displayFundSachen(JFrame fundSachenToVisible, JFrame JframeToClose) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void displayFitnessAbo(JFrame fitnessAboToVisible, JFrame JframeToClose) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void displayUebergabe(JFrame uebergabeToVisible, JFrame JframeToClose) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void displayPhoneBook(JFrame phoneBookToVisible, JFrame JframeToClose) {
			// TODO Auto-generated method stub
			
		}


		
		
}