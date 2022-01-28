package tech.codepalace.model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;


import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.JTextFieldLimit;
import tech.codepalace.utility.LoginDataUser;
import tech.codepalace.utility.PropertiesReader;
import tech.codepalace.utility.PropertiesWriter;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description This class will contain most of the main frame logic
 *
 */
public class LogicModelStartFrame {
	
		//Path of the project where the configuration file will be located
		protected String projectDirectoryString = System.getProperty("user.dir");
		
		//File variable that stores the path with the name of the application properties file.
		protected File configurationFile = new File(projectDirectoryString + File.separator + "config.properties");
		
		

		//Variables to set the database path.
		protected JFileChooser chooser; 
		protected File fileDataBaseFolder = null; 
		protected JPanel dialogJPanel; //custom dialog box
		protected JButton[] optionButtons;
		protected String urlDataBase;
		protected ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png")); //image for error dialog box
		private JLabel errorMessageJLabel; 
		private JButton okButton = new JButton("OK");
		private JDialog exitDialog;
		
		
		
		//Variables to create administrator user
		private JDialog adminDialog;
		
		private JLabel adminJLabel, passwordJLabel, abkuerzungMAJLabel;
		
		public JTextField userAdminJTextField;
		
		public JTextField kuerselMAJTextField;
		
		public JPasswordField passwordField;

		private ImageIcon imgUserRequest;
		private JPanel panelAdmin;
		
		private GridBagLayout gbl;
		private GridBagConstraints gbc;
		
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
		
		
		//Variables for the login dialog box
		protected JDialog dialogLogin;
		protected JButton[] optionButtonsLogin;
		public JButton loginButton, cancelLoginButton;
		private JPanel panelContainerLogin;
		private JLabel userNameJLabel, passwordLoginJLabel;
		protected JTextField userLoginJTextField;
		public JPasswordField passwordLoginJPasswordField; 
		
		//New Instance UserAHB
		private UserAHB userAHB;
		
		//New Instance LoginDataUser
		protected LoginDataUser loginDataUser; 
		
		
		//New Instance PropertiesReader
		private PropertiesReader propertiesReader;
		
		
		//Variable for the user privilege
		protected String privilegeUser;
		

		//We get the user privile
		public String getPrivilegeUser() {
			return privilegeUser;
		}
		
		//Variable to indicate that the password is correct
		protected boolean passwordIsCorrect = false;
		
		
		//get password status
		public boolean isPasswordIsCorrect() {
			return passwordIsCorrect;
		}




		//set password status
		public void setPasswordIsCorrect(boolean passwordIsCorrect) {
			this.passwordIsCorrect = passwordIsCorrect;
		}
		
		
		
		
		public LogicModelStartFrame() {
			
			this.imgUserRequest = new ImageIcon(getClass().getResource("/img/face90x90.png"));
			this.okButtonAdmin = new JButton("Save Admin");
			this.abbrechenJButton = new JButton("Abbrechen");
			this.kuerselMAJTextField = new JTextField(20);
			this.userAdminJTextField = new JTextField(20);
			this.passwordField = new JPasswordField(20);
			
			this.gbl = new GridBagLayout();
			this.gbc = new GridBagConstraints();
			
			//Initialize DataEncryption
			this.dataEncryptation = new DataEncryption();
			
			
			//Initialize the JButtons and the JPasswordField to call from Controller Class
			this.passwordLoginJPasswordField = new JPasswordField(20);
			this.loginButton = new JButton("Login");
			this.cancelLoginButton = new JButton("Abbrechen");
			
			//Initialize UserAHB
			this.userAHB = new UserAHB();
			
			
			
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
				loginUser();

//				System.out.println("Configuration file exist!");
			} else {

	
//				System.out.println("Configuration file does not exist!");
				//The configuration file does not exist. Set the local database path.
				setDataBasePath();

			}

		}
		
		
		
		
		/**
		 * @descriptionMethod that deals with setting the local database path
		 */
		protected void setDataBasePath() {
			
			
			//set The native LookAndFeel
			try {
		       UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		   } catch (Exception e) {e.printStackTrace();     }
			
			
			//Instantiate our JFileChoose to choose the Path for the dataBase.
			chooser = new JFileChooser();
			chooser.setCurrentDirectory(new java.io.File("."));
			
			chooser.setDialogTitle("Setting Path Local Database");
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			

			
			//disable the All files option
			chooser.setAcceptAllFileFilterUsed(false);
			
			
			
			
			
			do {
				int returnVal = chooser.showOpenDialog(chooser);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					this.fileDataBaseFolder = chooser.getSelectedFile();
					
					
					
					/*
					 A destination was selected then we give the value to urlDataBase of that selected destination to save it in the configuration file.
					 The next time we start the Application. The Configuration File will be read and the Application will knows where is located or will be located 
					 the future DataBases.
					
					 */
					this.urlDataBase = fileDataBaseFolder.getAbsolutePath();
					System.out.println("Path urlDataBase: " + this.urlDataBase);
					
	

				}else {
					
					
					/*
					 * No destination was selected urlDatabase = "". 
					 * 
					 * Promgram will exit and the next time we start we have to select again the Path for the DataBase.
					 */

					
					this.dialogJPanel = new JPanel(new BorderLayout());
					errorMessageJLabel = new JLabel("Es wurde Kein Pfad für Datenbank festgelegt");
					
					
					
					okButton.addKeyListener(new KeyListener() {
						
						@Override
						public void keyTyped(KeyEvent e) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void keyReleased(KeyEvent e) {}
						
						@Override
						public void keyPressed(KeyEvent e) {

							if(e.getKeyCode()==10) {
								exitDialog.dispose();
								System.exit(0);
							}
						}
					});
					
					
					okButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {

							exitDialog.dispose();
							System.exit(0);
						}
					});
					
					
					
					this.optionButtons = new JButton[]{okButton};
					
					dialogJPanel.add(errorMessageJLabel, BorderLayout.CENTER);
					dialogJPanel.add(okButton, BorderLayout.SOUTH);
					
					exitDialog = new JOptionPane(dialogJPanel, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, this.errorImg, optionButtons, null). createDialog("Programm verlassen");
					
					exitDialog.setVisible(true);
					exitDialog.dispose();
					

					
					
				}
			}while(this.urlDataBase.equals(""));
			
			
			

			/*
			 * Now that we have selected a path for the database. We call the createAdmin method.
			 * 
			 * The administrator user will have absolute privileges. 
			 * 
			 * He can create or delete users and grant privileges to them.
			 * 
			 * At least one administrator user is required for the application.
			 * 
			 * Only one administrator user can delete other admin users.
			 */
			
			createAdminUser();
			

		}
		
		
		
		
		/**
		 * @description
		 * Method to create administrator user
		 */
		protected void createAdminUser() {
			
			this.adminJLabel = new JLabel("Admin:");
			this.adminJLabel.setPreferredSize(new Dimension(110, 20));
			this.adminJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			
			
			
			this.passwordJLabel = new JLabel("Password:");
			this.passwordJLabel.setPreferredSize(new Dimension(110, 20));
			this.passwordJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			
			
			this.abkuerzungMAJLabel = new JLabel("Abkürzung MA:");
			this.abkuerzungMAJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.abkuerzungMAJLabel.setPreferredSize(new Dimension(110, 20));
			this.abkuerzungMAJLabel.setOpaque(true);
			
			
			this.kuerselMAJTextField.setDocument(new JTextFieldLimit(4)); //We limit the number of characters that can be entered to 4
			
			
			this.panelAdmin = new JPanel();

			this.gbl = new GridBagLayout();
			
			this.panelAdmin.setLayout(gbl);
			
			this.gbc = new GridBagConstraints();
			
			
			//add the elements to the panel GridBagLayout
			this.gbc.gridx = 0; 
			this.gbc.gridy = 0; 
			this.gbl.setConstraints(adminJLabel, gbc);
			this.panelAdmin.add(adminJLabel);
			
			this.gbc.gridx = 1;
			this.gbc.gridy = 0;
			this.gbc.insets = new Insets(0, 5, 0, 0);
			this.gbl.setConstraints(userAdminJTextField, gbc);
			this.panelAdmin.add(userAdminJTextField);
			
			
			
			this.gbc.gridx = 0; 
			this.gbc.gridy = 1; 
			this.gbc.insets = new Insets(5, 0, 0, 0);
			this.gbl.setConstraints(passwordJLabel, gbc);
			this.panelAdmin.add(passwordJLabel);
			
			this.gbc.gridx = 1;
			this.gbc.gridy = 1; 
			this.gbc.insets = new Insets(5, 5, 0, 0);
			this.gbl.setConstraints(passwordField, gbc);
			this.panelAdmin.add(passwordField);
			
			
			this.gbc.gridx = 0;
			this.gbc.gridy = 2; 
			this.gbc.insets = new Insets(5, 0, 0, 0);
			this.gbl.setConstraints(abkuerzungMAJLabel, gbc);
			this.panelAdmin.add(abkuerzungMAJLabel);
			   
			   
			this.gbc.gridx = 1;
			this.gbc.gridy = 2; 
			this.gbc.insets = new Insets(5, 5, 0, 0);
			this.gbl.setConstraints(kuerselMAJTextField, gbc);
			this.panelAdmin.add(kuerselMAJTextField);
			
			
			
			
			this.optionButtons = new JButton[]{okButtonAdmin, this.abbrechenJButton};
			
			
			
			this.adminDialog = new JOptionPane(this.panelAdmin, JOptionPane.OK_CANCEL_OPTION, JOptionPane.NO_OPTION, imgUserRequest, optionButtons, 
					null).createDialog("Please Enter Username and Password for the new Admin User");


			this.adminDialog.setVisible(true);
			this.adminDialog.dispose();

			//No data was saved for the administrator we left the program.
			if(!this.adminDialog.isVisible() && this.userName.equals("")) {
				System.exit(0);
			}

		}



		
		

		/**
		 * @description
		 * Method that will check the input of the data for the administrator user in the dialog box that we have created for it.
		 * 
		 * <p>This method will be called from the corresponding controller class when interacting with the user
		 * 
		 * 
		 * </p>
		 */
		public void checkEntryAdmin() {
			
			
			//Set the values of the variables
			
			
			this.userName = this.userAdminJTextField.getText();
			this.userAppTextArray = this.userName.toCharArray();
			
			
			String pwd = new String(this.passwordField.getPassword()); 
			this.password = pwd;
			this.passwordAppTextArray = this.password.toCharArray();
			
			
			this.abkuerzungMA = this.kuerselMAJTextField.getText();
			this.abkuerzungMATextArray = this.abkuerzungMA.toCharArray();
			
		
			//Set value to privilege as ADMIN
			this.privilegePropertieValue = "ADMIN";
			

			
			//We call the Method to validate the Admin Entry
			validateAdminEntry();
			
			//We call to check the other entries.
			checkPasswordAdminEntry();
			checkAbkuerzungMAAdminEntry();
			
		
		
			
		}
		
		
		
		/**
		 *@description 
		 *
		 *In this method after having set the data in the variables, collected from the different input boxes, we will check that the user has not entered blank spaces. 
		 *Blanks spaces are not allowed for the user name.
		 *
		 *
		 *We use an array to walk through the array for checking the data entered by the user and check if white spaces are found in the username or not.
		 */
		protected void validateAdminEntry() {
			
			
			
			
			
			   for(int i = 0; i < this.userAppTextArray.length; i++) {
				   
				   //We create a variable of type char so that it constantly receives the index that is traversed in the array
				   char currentIndex = this.userAppTextArray[i];
				   
				  
				   
				   //We evaluate if an empty space is found
				   if(currentIndex == ' ') {
					   
					   JOptionPane.showMessageDialog(null, "Leerzeichen sind für den Benutzernamen nicht zulässig\n\nVersuchen Sie es erneut mit einem gültigen Benutzernamen "
							   , "Kritischer Fehler Benutzername", JOptionPane.ERROR_MESSAGE, this.errorImg);
					   
					   try {
						   //Critical Error(Blan spaces) we call again createAdminUser method.
						   createAdminUser();
						   
					} catch (Exception e) {
						e.printStackTrace();
					}
					   
				   }
			
		}
			   
			   
			   
			   //We evaluate to recover the focus on the corresponding component
			   if(this.userName.equals("")) {
			 	  
			 	  JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Benutzernamen ein "
			 			   , "Kritischer Fehler Benutzername", JOptionPane.ERROR_MESSAGE, this.errorImg);
			 	  
			 	  
			 	  if(this.userName.equals("")) {
			 		  this.userAdminJTextField.requestFocus();
			 	  }else if (this.password.equals("")) {
			 		this.passwordField.requestFocus();
			 	}else {
			 		this.kuerselMAJTextField.requestFocus();
			 	}
			   
			   }
			   
		}
		
		
		
		/**
		 * @description Method to check the data entered for the password
		 * 
		 * 
		 */
		protected void checkPasswordAdminEntry(){
			
			
			//procedure almost identical to the method to check the user
			
			for(int i = 0; i < this.passwordAppTextArray.length; i++) {
				   
				
				   
				   char currentIndex = this.passwordAppTextArray[i];
				   

				   if(currentIndex == ' ') {
					   
					   JOptionPane.showMessageDialog(null, "Leerzeichen sind für das Passwort nicht zulässig. \n\nVersuchen Sie es erneut mit einem gültigen Passwort. "
							   , "Kritischer Fehler Password", JOptionPane.ERROR_MESSAGE, this.errorImg);
					   
					   
					   
					   try {
						   createAdminUser();
					} catch (Exception e) {

						e.printStackTrace();
					}
					   
				   }
			   }
			
			
			
			
			if(this.password.equals("")){
				  
				  JOptionPane.showMessageDialog(null, "Bitte geben Sie ein Passwort ein "
						   , "Kritischer Fehler Password", JOptionPane.ERROR_MESSAGE, this.errorImg);
				  
				  if(this.userName.equals("")) {
					  this.userAdminJTextField.requestFocus();
				  }else if (this.password.equals("")) {
					this.passwordField.requestFocus();
				}else {
					this.kuerselMAJTextField.requestFocus();
				}
			  }
			
		}
		
		
		
		
		
		
		/**
		 * @description Method to check the data entered for the Abkürzung MA(Employee abbreviation)
		 */
		protected void checkAbkuerzungMAAdminEntry() {
			
			
			
			
			for(int i = 0; i < this.abkuerzungMATextArray.length; i++) {
				   
				  
				   char currentIndex = this.abkuerzungMATextArray[i];
				   
				  
				   
				   if(currentIndex == ' ') {
					   
					   JOptionPane.showMessageDialog(null, "Leerzeichen sind für das Abkürzung nicht zulässig. \n\nVersuchen Sie es erneut mit einem gültigen Abkürzung."
							   , "Kritischer Fehler Abkürzung", JOptionPane.ERROR_MESSAGE, this.errorImg);
					   
					   try {
						createAdminUser();
					} catch (Exception e) {
						e.printStackTrace();
					}
					   
				   }
			   }
			
			
			
			
			
			
			if(this.abkuerzungMA.equals("")) {
				  
				  JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Abkürzung MA ein. maximal 4 Buchstaben und Minimum 2 Buchstaben "
						   , "Kritischer Fehler Abkürzung Mitarbeiter", JOptionPane.ERROR_MESSAGE, this.errorImg);

				  
				  
				
			}
			
			if(this.abkuerzungMA.length() < 2) {
				JOptionPane.showMessageDialog(null, "Bitte geben Sie eine Abkürzung MA ein. maximal 4 Buchstaben und Minimum 2 Buchstaben"
						   , "Kritischer Fehler Abkürzung Mitarbeiter", JOptionPane.ERROR_MESSAGE, this.errorImg);
				
				
			}
			
			if(this.userName.equals("")) {
				  this.userAdminJTextField.requestFocus();
			  }else if (this.password.equals("")) {
				this.passwordField.requestFocus();
			}else {
				this.kuerselMAJTextField.requestFocus();
			}
			
			
			
		
			if(!this.userName.equals("") && !this.password.equals("") && !this.abkuerzungMA.equals("")) {
				this.adminDialog.dispose();
				
				/*
				 *here we have to call a new method that encrypts the data entered before saving the information in the configuration file.
				 *
				 *
				 *All the data is correct, there are correct entries we go to call the method that encrypts the data to save it in an encrypted way with the help 
				 *of another method.
				 */
				
				
				
				/*
				 * we call the method and pass the value of the privilege that the administrator user has.
				 */
				 encryptDataUser(privilegePropertieValue);
			}
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		/**
		 * @throws Exception 
		 * @throws HeadlessException 
		 * @description encryptDataUser, Method to encrypt the Data. This Method receive userPrivilege
		 */
		protected void encryptDataUser(String userPrivilege)  {
			

			
			//We set the values to the variables before encrypt them.
			this.urlPropertieName = "db.url";
			this.urlPropertieValue = this.urlDataBase;
			this.userNamePropertieName = "db.user." + this.userName;
			this.userNamePropertieValue = this.userName;
			this.passwordPropertieName = "db.password.user." + this.userName;
			this.passwordPropertieValue = this.password;
			this.privilegePropertieName = "db.privilege.user." + this.userName;
			this.privilegePropertieValue = userPrivilege;
			this.abkuerzungPropertieName = "db.abkuerzungma.user" + this.userName;
			this.abkuerzungPropertieValue = this.abkuerzungMA;
			
			
			
			
			
			try {
				
			    //We encrypt the values and at the same time we set new values to the variables.
				this.urlPropertieName = dataEncryptation.encryptData(this.urlPropertieName);
				this.urlPropertieValue = dataEncryptation.encryptData(this.urlPropertieValue);
				this.userNamePropertieName = dataEncryptation.encryptData(this.userNamePropertieName);
				this.userNamePropertieValue = dataEncryptation.encryptData(this.userNamePropertieValue);
				this.passwordPropertieName = dataEncryptation.encryptData(this.passwordPropertieName);
				this.passwordPropertieValue = dataEncryptation.encryptData(this.passwordPropertieValue);
				this.privilegePropertieName = dataEncryptation.encryptData(this.privilegePropertieName);
				this.privilegePropertieValue = dataEncryptation.encryptData(this.privilegePropertieValue);
				this.abkuerzungPropertieName = dataEncryptation.encryptData(this.abkuerzungPropertieName);
				this.abkuerzungPropertieValue = dataEncryptation.encryptData(this.abkuerzungPropertieValue);
				
//				System.out.println("urlPropertieName: " + this.urlPropertieName);
//				System.out.println("urlPropertieValue: " + this.urlPropertieValue);
//				System.out.println("userNamePropertieName: " + this.userNamePropertieName);
//				System.out.println("userNamePropertieValue: " + this.userNamePropertieValue);
//				System.out.println("passwordPropertieName: " + this.passwordPropertieName);
//				System.out.println("passwordPropertieValue: " + this.passwordPropertieValue);
//				System.out.println("privilegePropertieName: " + this.privilegePropertieName);
//				System.out.println("privilegePropertieValue: " + this.privilegePropertieValue);
//				System.out.println("abkuerzungPropertieName: " + this.abkuerzungPropertieName);
//				System.out.println("abkuerzungPropertieValue: " + this.abkuerzungPropertieValue);
				
				
				//Initialize the PropertiesWriter instance
				this.propertiesWriter = new PropertiesWriter();
				
				//We call to write the Properties to the file config.properties in the root folder of the application.
				this.propertiesWriter.writeProperties(this.urlPropertieName, this.urlPropertieValue);
				this.propertiesWriter.writeProperties(this.userNamePropertieName, this.userNamePropertieValue);
				this.propertiesWriter.writeProperties(this.passwordPropertieName, this.passwordPropertieValue);
				this.propertiesWriter.writeProperties(this.privilegePropertieName, this.privilegePropertieValue);
				this.propertiesWriter.writeProperties(this.abkuerzungPropertieName, this.abkuerzungPropertieValue);
				
				
				//After config.properties file is created we have to call loginUser for the first time with the new created user
				loginUser();
				
			} catch (HeadlessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			
		}
		
		
		
		
		
		/**
		 * @description Method for login. 
		 * 
		 * <p>this method will be called if the properties file exists.</p>
		 */
		public void loginUser() {
		

			//Create our array that will have the login buttons
			this.optionButtonsLogin = new JButton[] {this.loginButton, this.cancelLoginButton};
			
			this.userNameJLabel = new JLabel("User:");
			this.userNameJLabel.setPreferredSize(new Dimension(110, 20));
			this.userNameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			
			this.passwordLoginJLabel = new JLabel("Password:");
			this.passwordLoginJLabel.setPreferredSize(new Dimension(110, 20));
			this.passwordLoginJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			
			this.userLoginJTextField = new JTextField(20);
			
			//JTextFields = ""
			this.userLoginJTextField.setText("");
			this.passwordLoginJPasswordField.setText("");
			
			
			this.panelContainerLogin = new JPanel();
			this.gbl = new GridBagLayout();
			
			this.panelContainerLogin.setLayout(gbl);


			this.gbc.gridx = 0;
			this.gbc.gridy = 0;
			this.panelContainerLogin.add(this.userNameJLabel, gbc);
			

			this.gbc.gridx = 1;
			this.gbc.gridy = 0;
			this.gbc.insets = new Insets(0, 5, 0, 0);
			this.panelContainerLogin.add(userLoginJTextField, gbc);
			

			this.gbc.gridx = 0; 
			this.gbc.gridy = 1; 
			this.gbc.insets = new Insets(5, 0, 0, 0);
			this.panelContainerLogin.add(passwordLoginJLabel, gbc);
			

			this.gbc.gridx = 1;
			this.gbc.gridy = 1; 
			this.gbc.insets = new Insets(5, 5, 0, 0);
			this.panelContainerLogin.add(passwordLoginJPasswordField, gbc);
			
			
			
			
			this.dialogLogin = new JOptionPane(this.panelContainerLogin, JOptionPane.OK_CANCEL_OPTION, JOptionPane.NO_OPTION, imgUserRequest, optionButtonsLogin, 
					null).createDialog("Bitte geben Sie Ihren Benutzernamen und Ihr Passwort ein.");


			this.dialogLogin.setVisible(true);
			this.dialogLogin.dispose();
			
			
			//If the dialog box is visible and the username is empty, we exit the program. The user does not want to continue.
			if(!this.dialogLogin.isVisible() && this.userName.equals("")) {
				System.exit(0);
			}

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
			this.userName = this.userLoginJTextField.getText();
			String pwd = new String(this.passwordLoginJPasswordField.getPassword()); 
			this.password = pwd;
			
			//we check that the data is not empty
			if(this.userName.equals("") || this.password.equals("")) {
				
				
				this.dialogLogin.dispose();
				JOptionPane.showMessageDialog(null, "Benutzername oder Passwort dürfen nicht leer sein"
						   , "Geben Sie einen gültigen Benutzernamen und ein gültiges Passwort ein", JOptionPane.ERROR_MESSAGE, this.errorImg);
				
				//Data is empty then we call loginUser method.
				loginUser();
		
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
				
				
				//We test
//				System.out.println("urlPropertieName: " + this.loginDataUser.getUrlPropertieName());
//				System.out.println("userNamePropertieName: " + this.loginDataUser.getUserNamePropertieName());
//				System.out.println("passwordPropertieName: " + this.loginDataUser.getPasswordPropertieName());
//				System.out.println("password: " + this.loginDataUser.getPasswordEnteredByTheUser());
//				System.out.println("privilegePropertieName: " + this.loginDataUser.getPrivilegePropertieName());
//				System.out.println("abkuerzungPropertieName: " + this.loginDataUser.getAbkuerzungPropertieName());		
				
				
				//We call the readPropertiesData method by passing the loginDataUser and userAHB instances as argument
				this.propertiesReader.readPropertiesData(this.loginDataUser, this.userAHB);
				
				
				//We check if the Password entered by the User is correct
				if (this.propertiesReader.isPasswordIsCorrect()) {
					

					//set the value of the privilege variable, first decrypt that value. We can after use it to grant more or less privilege to the user.
					this.privilegeUser = this.dataEncryptation.decryptData(this.userAHB.getPrivilege());
					
					
					//We set passwordIsCorrect as true
					this.passwordIsCorrect = true;
					
					
					//Close the Dialog Box
					dialogLogin.dispose();
					
					
					//Test
					System.out.println("Users and passwords are correct");
				}else {
					
					//Password is not correct
					this.passwordIsCorrect = false;
					
					//reset values
					this.userName = "";
					this.userLoginJTextField.setText("");
					this.passwordLoginJPasswordField.setText("");
					
					//request focus
					this.userLoginJTextField.requestFocus();
					
					//Test
					System.out.println("Users and passwords are not correct");
					
					
				}
				

			} catch (Exception e) {
				e.printStackTrace();
			}

			
		}
			
			
			
		}
			
			
		
		
		
		
		
		
		
}