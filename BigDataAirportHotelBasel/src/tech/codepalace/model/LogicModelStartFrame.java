package tech.codepalace.model;

import java.io.File;

import javax.swing.SwingUtilities;

import tech.codepalace.controller.ConfigurationDirectoryController;
import tech.codepalace.controller.LoginController;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.ConfigurationDirectory;
import tech.codepalace.view.frames.LoginUser;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description This class will contain most of the main frame logic.
 * <p>some of the methods are in the super class LogicModel.</p>
 *
 */
public class LogicModelStartFrame extends LogicModel {
	
		//Path of the project where the configuration file will be located
		protected String projectDirectoryString = System.getProperty("user.dir");
		

		
		//File variable that stores the path with the name of the application properties file.
		protected File configurationFile = new File(projectDirectoryString + File.separator + "config.properties");
	
		
		private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;

		
		//Variables for the Login
		protected  LoginController loginController;
		private LoginUser loginUser;
		protected LogicModelLogin logicModelLogin;

		
		
		public LogicModelStartFrame(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame) {
			
			this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
			
			this.loginUser = new LoginUser(this.bigDataAirportHotelBaselStartFrame, true);

		}
		
		
		/**
		 * 
		 * @throws Exception
		 * 
		 * @description Method to check if the configuration file is found, created or is
		 *              the first time that we launch our application and we have create
		 *              a new Administrator user with your password to the first launch of the
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
							logicModelLogin = new LogicModelLogin(loginUser);
							loginController = new LoginController(loginUser, bigDataAirportHotelBaselStartFrame, logicModelLogin, this);
							loginUser.setLocationRelativeTo(bigDataAirportHotelBaselStartFrame);
							loginUser.setVisible(true);

				}else {
					
					//logicModelLogin not Null we just call loginUser to visible.
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
				 * We also call for a new ConfigurationDirectoryController class for controlling the ConfigurationDirectori small GUI Class.
				 * 
				 * 
				 * 
				 */
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						
						//Create an instance of ConfigurationDirectory with the needed parameters.
						ConfigurationDirectory configurationDirectory = new ConfigurationDirectory(bigDataAirportHotelBaselStartFrame, true);
						
						//Create an instance of logicModelConfigurationDirectory with the needed parameters.
						LogicModelConfigurationDirectory logicModelConfigurationDirectory = new LogicModelConfigurationDirectory(configurationDirectory, bigDataAirportHotelBaselStartFrame, LogicModelStartFrame.this);
						
						//new ConfigurationDirectoryController with the needed parameters.
						 new ConfigurationDirectoryController(configurationDirectory, logicModelConfigurationDirectory);
						
						 //set Visible our ConfigurationDirectory GUI.
						configurationDirectory.setVisible(true);
					}
				});
			}

		}
		

		
		
		
		
		
}