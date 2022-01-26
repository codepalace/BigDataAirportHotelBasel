package tech.codepalace.model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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

import tech.codepalace.utility.JTextFieldLimit;

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
//				loginUser();

			} else {

	
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
		
		
		
		
		
		
}