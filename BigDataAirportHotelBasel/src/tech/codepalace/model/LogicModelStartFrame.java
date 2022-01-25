package tech.codepalace.model;

import java.awt.BorderLayout;
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
import javax.swing.UIManager;

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
			
//			createAdminUser();
			

		}
		
		
		
		
		
		
}