package tech.codepalace.model;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tech.codepalace.controller.LoginController;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.PropertiesWriter;
import tech.codepalace.view.frames.AdminCreator;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.LoginUser;

public class LogicAdminCreator extends LogicModel {
	
	private AdminCreator adminCreator; 
	
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	
	private LogicModelStartFrame logicModelStartFrame;
	
	
public JDialog dialogExit;
	
	private ImageIcon imageExit = new ImageIcon(getClass().getResource("/img/prevention.png"));
	
	private JLabel messageExit;
	
	public JButton exitJButton, cancelExitJButton;
	
	private JPanel exitJPanel, buttonsExitJPanel; 
	
	private Object[] optionExit;
	

	//Image error JOptionPane
	public ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	//Variables for the creation of administrator user
	private String userName, password, abkuerzungMA, privilege;
	
	//Arrays used to delete the whitespace
	private char[] userNameArray, passwordArray, abkuerzungMAArray;
	
	private boolean blanksUserName = false, blanksPassword = false, blanksAbkuerzung = false;
	
	
	// Variables to write the Propertie File
	protected String urlPropertieName, urlPropertieValue, urlDataBaseBackupPropertieName, urlDataBaseBackupValue,
			userNamePropertieName, userNamePropertieValue, passwordPropertieName, passwordPropertieValue,
			privilegePropertieName, abkuerzungPropertieName, abkuerzungPropertieValue, privilegePropertieValue;
	
	
	//Instance DataEncryption
	protected DataEncryption dataEncryptation;
	
	//Instance PropertiesWriter
	protected PropertiesWriter propertiesWriter;
	
	
	
	
	
	
	
	public LogicAdminCreator(AdminCreator adminCreator, BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, LogicModelStartFrame logicModelStartFrame) {
		
		this.adminCreator = adminCreator;
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.logicModelStartFrame = logicModelStartFrame;
	
		
		
		
	}
	
	
	public void closeApp() {
		
		//we proceed to close the application using a separate thread
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				exitJButton = new JButton("Beenden");
				cancelExitJButton= new JButton("Abbrechen");
				
				exitJPanel = new JPanel(new BorderLayout());
				buttonsExitJPanel = new JPanel(new BorderLayout());
				
				messageExit = new JLabel("Wollen Sie das Programm verlassen?");
				
				
				exitJButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						 dialogExit.dispose();
						 System.exit(1);
					}
				});
				
				
				
				exitJButton.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {}
					
					@Override
					public void keyReleased(KeyEvent e) {}
					
					@Override
					public void keyPressed(KeyEvent e) {
						
						if(e.getKeyCode()==10) {
							dialogExit.dispose();
							System.exit(1);
						}
						
					}
				});
				
				
				
				
				
				cancelExitJButton.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						dialogExit.dispose();
						
					}
				});
				
				
				
				cancelExitJButton.addKeyListener(new KeyListener() {
					
					@Override
					public void keyTyped(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyReleased(KeyEvent e) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void keyPressed(KeyEvent e) {
						
						if(e.getKeyCode()==10) {
							dialogExit.dispose();
						}
						
					}
				});
				
				
				
				
				optionExit = new Object[] {exitJButton, cancelExitJButton};
				
				exitJPanel.add(messageExit, BorderLayout.CENTER);
				buttonsExitJPanel.add(exitJButton, BorderLayout.EAST);
				buttonsExitJPanel.add(cancelExitJButton, BorderLayout.WEST);
				
				exitJPanel.add(buttonsExitJPanel, BorderLayout.SOUTH);
				
				dialogExit = new JOptionPane(exitJPanel, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, imageExit, optionExit, 
						null).createDialog("Wollen Sie das Programm verlassen?");
				
				dialogExit.setVisible(true);
				
				
			}
		});
	}

	
	
	/**
	 *
	 * @param userName
	 * @param password
	 * @param abkuerzungMA
	 * 
	 *  @description method for collecting the values entered by the future new Admin-user.
	 *  
	 *  
	 * 
	 * 
	 */
	public void validateAdminEntry(String userName, String password, String abkuerzungMA) {
		this.userName = userName;
		this.password = password;
		this.abkuerzungMA = abkuerzungMA;
		this.privilege = "ADMIN";
		
		
		this.userNameArray = this.userName.toCharArray();
		this.passwordArray = this.password.toCharArray();
		this.abkuerzungMAArray = this.abkuerzungMA.toCharArray();
		
		this.blanksUserName = false;
		this.blanksPassword = false;
		this.blanksAbkuerzung = false;
		
		
		if(this.userName.equals("")) {
			SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Benutzernamen ein"
					   , "Kritischer Fehler Benutzername", JOptionPane.ERROR_MESSAGE, this.errorImg));
			
			this.adminCreator.adminJTextField.requestFocus();
			
		}else {
			
			//we check that no blank spaces are entered
			for(int i=0; i< this.userNameArray.length; i++) {
				
				char currentIndex = this.userNameArray[i];

				if(currentIndex == ' ') {

					this.blanksUserName = true;

				}
				
				if (i == userNameArray.length-1) {
					
					if (this.blanksUserName) {
						SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Leerzeichen sind für den Benutzernamen nicht zulässig\n\nVersuchen Sie es erneut mit einem gültigen Benutzernamen "
								   , "Kritischer Fehler Benutzername", JOptionPane.ERROR_MESSAGE, this.errorImg));	
					}else {
						
						validatePasswordEntry();
					}
		        }
				
			}
			
			
		}
		
		
		
		
	}
	
	
	
	private void validatePasswordEntry() {
		
		if(this.password.equals("")) {
			SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Bite geben Sie ein Passwort für den Administrator ein. das Passwortfeld darf nicht leer sein"
					   , "Kritischer Fehler Passwort", JOptionPane.ERROR_MESSAGE, this.errorImg));
			
			this.adminCreator.passwordField.requestFocus();
			
		}else {
			
			
			//we check that no blank spaces are entered
			for(int i=0; i< this.passwordArray.length; i++) {
				
				char currentIndex = this.passwordArray[i];

				if(currentIndex == ' ') {

					this.blanksPassword = true;

				}
				
				if (i == this.passwordArray.length-1) {
					
					if (this.blanksPassword) {
						SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Leerzeichen sind für das Passwort nicht zulässig\n\nVersuchen Sie es erneut mit einem gültigen Passwort "
								   , "Kritischer Fehler Passwort", JOptionPane.ERROR_MESSAGE, this.errorImg));	
					}else {
						
						validateAbkuerzungEntry();
					}
		        }
		}
		
	
	}
		
	}	
		
	
	
	
	
	
		private void validateAbkuerzungEntry() {
			
			if(this.abkuerzungMA.equals("")) {
				SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Bite geben Sie ein Abkürzung für den Administrator ein. Die Abkürzung darf nicht leer sein"
						   , "Kritischer Fehler Abkürzung", JOptionPane.ERROR_MESSAGE, this.errorImg));
				
				this.adminCreator.abkuerzungJTextField.requestFocus();
				
			}else {
				
				
				//we check that no blank spaces are entered
				for(int i=0; i< this.abkuerzungMAArray.length; i++) {
					
					char currentIndex = this.abkuerzungMAArray[i];

					if(currentIndex == ' ') {

						this.blanksAbkuerzung = true;

					}
					
					if (i == abkuerzungMAArray.length-1) {
						
						if (this.blanksAbkuerzung) {
							SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Leerzeichen sind für die Abkürzung nicht zulässig\n\nVersuchen Sie es erneut mit einer korrekten Abürzung "
									   , "Kritischer Fehler Abkürzung", JOptionPane.ERROR_MESSAGE, this.errorImg));	
						}else {
							
							
							checkManagerAbbreviationCorrectLength();
							
							
						}
			        }
			}
				
			}
			
		}
		
		
		private void checkManagerAbbreviationCorrectLength() {
			
			if(this.adminCreator.abkuerzungJTextField.getText().length()<3) {
				SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Bitte geben Sie einen Abkürzungsname ein, der mindestens 3 Buchstaben lang ist. "
						   , "Kritischer Fehler Abkürzung", JOptionPane.ERROR_MESSAGE, this.errorImg));	
			}else {
				
				/*
				 * All three validations are done. 
				 * All the data entered is as expected. N
				 * Now we proceed with the encryption process and save the data in our configuration file
				 */
				encryptDataUser(this.privilege);
			}
		}
		
		
		
		/**
		 * @throws Exception 
		 * @throws HeadlessException 
		 * @description encryptDataUser, Method to encrypt the Data. This Method receive userPrivilege
		 */
		public void encryptDataUser(String userPrivilege)  {
			

			
			//We set the values to the variables before encrypt them.
			this.urlPropertieName = "db.url";
			this.urlPropertieValue = getUserAHB().getUrlDataBase(); //We get the value from userAHB instance
			this.urlDataBaseBackupPropertieName = "db.url.backup";
			this.urlDataBaseBackupValue = getUserAHB().getUrlDataBaseBackup(); //We get the value from userAHB instance
			
			this.userNamePropertieName = "db.user." + this.userName;
			this.userNamePropertieValue = this.userName;
			this.passwordPropertieName = "db.password.user." + this.userName;
			this.passwordPropertieValue = this.password;
			this.privilegePropertieName = "db.privilege.user." + this.userName;
			this.privilegePropertieValue = userPrivilege;
			this.abkuerzungPropertieName = "db.abkuerzungma.user." + this.userName;
			this.abkuerzungPropertieValue = this.abkuerzungMA;
			
			
			this.dataEncryptation = new DataEncryption();
			
			
			try {
				
			    //We encrypt the values and at the same time we set new values to the variables.
				this.urlPropertieName = dataEncryptation.encryptData(this.urlPropertieName);
				this.urlPropertieValue = dataEncryptation.encryptData(this.urlPropertieValue);

				this.urlDataBaseBackupPropertieName = dataEncryptation.encryptData(this.urlDataBaseBackupPropertieName);
				this.urlDataBaseBackupValue = dataEncryptation.encryptData(this.urlDataBaseBackupValue);
				
				this.userNamePropertieName = dataEncryptation.encryptData(this.userNamePropertieName);
				this.userNamePropertieValue = dataEncryptation.encryptData(this.userNamePropertieValue);
				this.passwordPropertieName = dataEncryptation.encryptData(this.passwordPropertieName);
				this.passwordPropertieValue = dataEncryptation.encryptData(this.passwordPropertieValue);
				this.privilegePropertieName = dataEncryptation.encryptData(this.privilegePropertieName);
				this.privilegePropertieValue = dataEncryptation.encryptData(this.privilegePropertieValue);
				this.abkuerzungPropertieName = dataEncryptation.encryptData(this.abkuerzungPropertieName);
				this.abkuerzungPropertieValue = dataEncryptation.encryptData(this.abkuerzungPropertieValue);
				
				
				
				//Initialize the PropertiesWriter instance
				this.propertiesWriter = new PropertiesWriter();
				
				//We call to write the Properties to the file config.properties in the root folder of the application.
				this.propertiesWriter.writeProperties(this.urlPropertieName, this.urlPropertieValue);
				this.propertiesWriter.writeProperties(this.urlDataBaseBackupPropertieName, this.urlDataBaseBackupValue);
				this.propertiesWriter.writeProperties(this.userNamePropertieName, this.userNamePropertieValue);
				this.propertiesWriter.writeProperties(this.passwordPropertieName, this.passwordPropertieValue);
				this.propertiesWriter.writeProperties(this.privilegePropertieName, this.privilegePropertieValue);
				this.propertiesWriter.writeProperties(this.abkuerzungPropertieName, this.abkuerzungPropertieValue);
				
				
				//After config.properties file is created we have to call loginUser for the first time with the new created user and close adminCreator
				adminCreator.dispose();
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						
						LoginUser loginUser = new LoginUser(bigDataAirportHotelBaselStartFrame, true);
						
					    LogicModelLogin	logicModelLogin = new LogicModelLogin(loginUser);
					    //r(LoginUser loginUser, BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, LogicModelLogin logicModelLogin) {
						new LoginController(loginUser, bigDataAirportHotelBaselStartFrame, logicModelLogin, logicModelStartFrame);
						loginUser.setLocationRelativeTo(bigDataAirportHotelBaselStartFrame);
						loginUser.setVisible(true);
						
						
					}
				});
				
				
				
			} catch (HeadlessException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			
		}
		
		
		
		
		
	
}
