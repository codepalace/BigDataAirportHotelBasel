package tech.codepalace.model;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import tech.codepalace.controller.AdminCreatorController;
import tech.codepalace.utility.OperatingSystemCheck;
import tech.codepalace.view.frames.AdminCreator;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.ConfigurationDirectory;

public class LogicModelConfigurationDirectory extends LogicModel {
	
	private ConfigurationDirectory configurationDirectory;
	
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	
	private LogicModelStartFrame logicModelStartFrame;

	private JFileChooser jFileChooser;
	
	private FileDialog fileDialog;
	
	protected String urlDataBase = "", urlDataBaseBackup ="";
	
	private UserAHB userAHB;
	
	public JDialog dialogExit;
	
	private ImageIcon imageExit = new ImageIcon(getClass().getResource("/img/prevention.png"));
	
	private JLabel messageExit;
	
	public JButton exitJButton, cancelExitJButton;
	
	private JPanel exitJPanel, buttonsExitJPanel; 
	
	private Object[] optionExit;
	
	
	
	
	
	
	
	public JDialog jDialog;
	
	private ImageIcon imageError = new ImageIcon(getClass().getResource("/img/error.png"));
	
	private JLabel errorMessage; 

	public JButton okJtButton = new JButton("OK");
	
	private Object[] option;
	
	private JPanel dialogJPanel;
	
	
	
	
	public LogicModelConfigurationDirectory(ConfigurationDirectory configurationDirectory, BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, LogicModelStartFrame logicModelStartFrame) {
		
		this.configurationDirectory = configurationDirectory;
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.logicModelStartFrame = logicModelStartFrame;
		
		
		
		
		
	}
	
	
	
	
	/**
	 * @description Method to set the urlDataBase value
	 */
	public void selectPathDataBaseLocal() {
		
		
		switch (OperatingSystemCheck.getOparatingSystem()) {
			
		case MAC:
		
			System.setProperty("apple.awt.fileDialogForDirectories", "true");
			this.fileDialog = new FileDialog(this.configurationDirectory, "Datenbankpfad wählen", FileDialog.LOAD);
			
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					
					
						
						fileDialog.setVisible(true);
						
						if(fileDialog.getDirectory()!=null) {
							File file = new File(fileDialog.getDirectory(), fileDialog.getFile());
							configurationDirectory.getDataBaseSelectedPath().setText(file.getAbsolutePath());
							System.setProperty("apple.awt.fileDialogForDirectories", "false");
							urlDataBase = file.getAbsolutePath();
						}else {
							configurationDirectory.getDataBaseSelectedPath().setText("");
							urlDataBase ="";
						}
						
		
				
				}
				
			});
			
			
		   break;
		   
		case WINDOWS:
			
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					
					
					jFileChooser = new JFileChooser();
					jFileChooser.setCurrentDirectory(new java.io.File("."));
					
					jFileChooser.setDialogTitle("Datenbankpfad wählen");
					jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					
					jFileChooser.setAcceptAllFileFilterUsed(false);
					
					int returnVal = jFileChooser.showOpenDialog(configurationDirectory);
					
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						configurationDirectory.getDataBaseSelectedPath().setText(jFileChooser.getSelectedFile().toString());
						urlDataBase = jFileChooser.getSelectedFile().toString();
						
					}else {
						configurationDirectory.getDataBaseSelectedPath().setText("");
						urlDataBase = "";
						
					}
					
					
					
					
				}
			});
			
			break;
			
		default:
			
			
			
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					
					
					jFileChooser = new JFileChooser();
					jFileChooser.setCurrentDirectory(new java.io.File("."));
					
					jFileChooser.setDialogTitle("Datenbankpfad wählen");
					jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					
					jFileChooser.setAcceptAllFileFilterUsed(false);
					
					int returnVal = jFileChooser.showOpenDialog(configurationDirectory);
					
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						configurationDirectory.getDataBaseSelectedPath().setText(jFileChooser.getSelectedFile().toString());
						urlDataBase = jFileChooser.getSelectedFile().toString();
						
					}else {
						configurationDirectory.getDataBaseSelectedPath().setText("");
						urlDataBase = "";
						
					}
					
					
					
					
				}
			});
				
		
		break;
		
		}
		

		
	}
	
	
	
	
	
	
	
	
	
	/**
	 * @description Method to set the Path for the BackUpDataBase(The url for the backup)
	 */
	public void selectPathBackupDataBaseLocal() {
		
		
		
		
		switch (OperatingSystemCheck.getOparatingSystem()) {
		
		case MAC:
		
			System.setProperty("apple.awt.fileDialogForDirectories", "true");
			this.fileDialog = new FileDialog(this.configurationDirectory, "Backup Datenbankpfad wählen", FileDialog.LOAD);
			
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					fileDialog.setVisible(true);
					if(fileDialog.getDirectory()!=null) {
						
						File file = new File(fileDialog.getDirectory(), fileDialog.getFile());
						configurationDirectory.getBackupDatabaseSelectedPath().setText(file.getAbsolutePath());
						System.setProperty("apple.awt.fileDialogForDirectories", "false");
						urlDataBaseBackup = file.getAbsolutePath();
						
					}else {
						configurationDirectory.getBackupDatabaseSelectedPath().setText("");
						urlDataBaseBackup = "";
					}
				
						
					
					
					
				}
				
			});
			
			
		   break;
		   
		case WINDOWS:
			
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					
					
					jFileChooser = new JFileChooser();
					jFileChooser.setCurrentDirectory(new java.io.File("."));
					
					jFileChooser.setDialogTitle("Backup Datenbankpfad wählen");
					jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					
					jFileChooser.setAcceptAllFileFilterUsed(false);
					
					int returnVal = jFileChooser.showOpenDialog(configurationDirectory);
					
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						configurationDirectory.getBackupDatabaseSelectedPath().setText(jFileChooser.getSelectedFile().toString());
						urlDataBaseBackup = jFileChooser.getSelectedFile().toString();
						
					}else {
						configurationDirectory.getBackupDatabaseSelectedPath().setText("");
						urlDataBaseBackup = "";
						
					}
					
					
					
					
				}
			});
			
			break;
			
		default:
			
			
			
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					
					
					jFileChooser = new JFileChooser();
					jFileChooser.setCurrentDirectory(new java.io.File("."));
					
					jFileChooser.setDialogTitle("Backup Datenbankpfad wählen");
					jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
					
					jFileChooser.setAcceptAllFileFilterUsed(false);
					
					int returnVal = jFileChooser.showOpenDialog(configurationDirectory);
					
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						configurationDirectory.getBackupDatabaseSelectedPath().setText(jFileChooser.getSelectedFile().toString());
						urlDataBaseBackup = jFileChooser.getSelectedFile().toString();
						
					}else {
						configurationDirectory.getBackupDatabaseSelectedPath().setText("");
						urlDataBaseBackup = "";
						
					}
					
					
					
					
				}
			});
				
		
		break;
		
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * @description Method to close the Configuration GUI in case we do not want to continue and prefer to close the application.
	 */
	public void closeConfigurationGUI() {
	
		
		
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
	 * @description Method to checkUrlDataBase
	 * <p>This Method will be call from Controller class after the user press the JButton to save the Url of Database and Backup DataBase.</p>
	 * 
	 * <p>the user's entries will be evaluated to verify that the URLs are not the same for the DataBase and for the Backup and that the URLs are chosen.</p>
	 * 
	 * <p>we must have the backup in a totally different folder to avoid conflicts when recovering a damaged original database.</p>
	 */
	
	public void checkUrlDataBase() {
		

		//If the path for the database has not been selected, but for the backup
		if(this.urlDataBase.equals("") && !this.urlDataBaseBackup.equals("")) {
			
			
SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					okJtButton = new JButton("Ok");
					
					
					
					dialogJPanel = new JPanel(new BorderLayout());
					errorMessage = new JLabel("Sie haben keinen Pfad für die lokale Datenbak ausgewählt");
					
					
					okJtButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							jDialog.dispose();
							
						}
					});
					
					
					option = new Object[] {okJtButton};
					
					dialogJPanel.add(errorMessage, BorderLayout.CENTER);
					dialogJPanel.add(okJtButton, BorderLayout.SOUTH);
					
					jDialog = new JOptionPane(dialogJPanel, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, imageError, option, null). createDialog("Unvollständige Daten");
					
					jDialog.setVisible(true);
					
				
					
					
				}
			});
			
	
			
		
		//If the path for the database has been selected, but not for the backup
		}else if (!this.urlDataBase.equals("") && this.urlDataBaseBackup.equals("")) {
			
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					okJtButton = new JButton("Ok");
					
					
					
					dialogJPanel = new JPanel(new BorderLayout());
					errorMessage = new JLabel("Sie haben keinen Pfad für die Datenbankbackup ausgewählt");
					
					
					okJtButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							jDialog.dispose();
							
						}
					});
					
					
					okJtButton.addKeyListener(new KeyListener() {
						
						@Override
						public void keyTyped(KeyEvent e) {}
						
						@Override
						public void keyReleased(KeyEvent e) {}
						
						@Override
						public void keyPressed(KeyEvent e) {
							// TODO Auto-generated method stub
							jDialog.dispose();
						}
					});
					
					
					option = new Object[] {okJtButton};
					
					dialogJPanel.add(errorMessage, BorderLayout.CENTER);
					dialogJPanel.add(okJtButton, BorderLayout.SOUTH);
					
					jDialog = new JOptionPane(dialogJPanel, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, imageError, option, null). createDialog("Unvollständige Daten");
					
					jDialog.setVisible(true);
					
				
					
					
				}
			});
			

			
		//If the path has not been selected for either the database or the backup
		}else if(this.urlDataBase.equals("") && this.urlDataBaseBackup.equals("")) {
			

			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					okJtButton = new JButton("Ok");
					
					
					
					dialogJPanel = new JPanel(new BorderLayout());
					errorMessage = new JLabel("Sie haben keinen Pfad für die lokale Datenbak und Datenbankbackup ausgewählt");
					
					
					okJtButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							jDialog.dispose();
							
						}
					});
					
					okJtButton.addKeyListener(new KeyListener() {
						
						@Override
						public void keyTyped(KeyEvent e) {}
						
						@Override
						public void keyReleased(KeyEvent e) {}
						
						@Override
						public void keyPressed(KeyEvent e) {
							// TODO Auto-generated method stub
							jDialog.dispose();
						}
					});
					
					
					option = new Object[] {okJtButton};
					
					dialogJPanel.add(errorMessage, BorderLayout.CENTER);
					dialogJPanel.add(okJtButton, BorderLayout.SOUTH);
					
					jDialog = new JOptionPane(dialogJPanel, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, imageError, option, null). createDialog("Unvollständige Daten");
					
					jDialog.setVisible(true);
					
				
					
					
				}
			});
			
			
			
			
		
		//If the two URLs are identical	
		}else if (this.urlDataBase.equals(urlDataBaseBackup)) { 
			
SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					okJtButton = new JButton("Ok");
					
					
					
					dialogJPanel = new JPanel(new BorderLayout());
					errorMessage = new JLabel("Die beiden Url(Pfad) sollten nicht identisch sein");
					
					
					okJtButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							jDialog.dispose();
							
						}
					});
					
					okJtButton.addKeyListener(new KeyListener() {
						
						@Override
						public void keyTyped(KeyEvent e) {}
						
						@Override
						public void keyReleased(KeyEvent e) {}
						
						@Override
						public void keyPressed(KeyEvent e) {
							// TODO Auto-generated method stub
							jDialog.dispose();
						}
					});
					
					
					option = new Object[] {okJtButton};
					
					dialogJPanel.add(errorMessage, BorderLayout.CENTER);
					dialogJPanel.add(okJtButton, BorderLayout.SOUTH);
					
					jDialog = new JOptionPane(dialogJPanel, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, imageError, option, null). createDialog("Unvollständige Daten");
					
					jDialog.setVisible(true);
					
				
					
					
				}
			});
			
		}
		
		
		
		//if everything is different from all the cases above.
		else {
			
			
/*
 * 	The first thing we are going to do in this new thread is send a message that the data has been saved successfully 
 * and that we now proceed to create the first administrator user.	
 * 
 * we have two different threads of execution. One thread for the JDialog
 *  and the other thread for the creation of the instances that will allow us to create the administrator user.
 *  
 *  One thread is inside the block of the other.
 */
SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					okJtButton = new JButton("Ok");
					ImageIcon imageOK = new ImageIcon(getClass().getResource("/img/hack_green.png"));
					
					
					dialogJPanel = new JPanel(new BorderLayout());
				 JLabel	okMessage = new JLabel("Als nächsten Schritt erstellen Sie bitte ein Administratorkonto");
					
					
					okJtButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							
							jDialog.dispose();
							
							
							
							//In a new Thread
							SwingUtilities.invokeLater(new Runnable() {
								
								@Override
								public void run() {
									
									//we create an instance of the class AdminCreator which receives the necessary parameters
									//ThebigDataAirrpotHotelBaselStartFrame and the modal boolean to block the GUI bigDataAirportHOtelBaselStartFrame.
									AdminCreator adminCreator = new AdminCreator(bigDataAirportHotelBaselStartFrame, true);
									
									/* We set the URLs for the DataBase and DataBaseBackup. 
									 * 
									 * We save it in the userAHB instance, because this way we avoid writing a new class just to save the URLs. 
									 * To this instance anyway we have to call often.
									 */
									userAHB = new UserAHB();
									//First we set the userAHB extended from super Class
									setUserAHB(userAHB);
									
									//We set the values to the userAHB
									getUserAHB().setUrlDataBase(urlDataBase);
									getUserAHB().seturlDataBaseBackup(urlDataBaseBackup);
								
									
									//New Instance LogicAdminCreator with the parameters adminCreator and the URLs
									LogicAdminCreator logicAdminCreator = new LogicAdminCreator(adminCreator, bigDataAirportHotelBaselStartFrame, logicModelStartFrame);
									
									logicAdminCreator.setUserAHB(getUserAHB());
									//And the Controller Class.
									new AdminCreatorController(adminCreator, logicAdminCreator);
									
									
									//We close the configurationDirectory
									configurationDirectory.dispose();
									
									
									
									//And setVisible the adminCreator instance GUI
									adminCreator.setVisible(true);
									
									
									
								}
							});
							
						}
					});
					
					
					okJtButton.addKeyListener(new KeyListener() {
						
						@Override
						public void keyTyped(KeyEvent e) {}
						
						@Override
						public void keyReleased(KeyEvent e) {}
						
						@Override
						public void keyPressed(KeyEvent e) {
							jDialog.dispose();
							
							
							SwingUtilities.invokeLater(new Runnable() {
								
								@Override
								public void run() {
									
									//we create an instance of the class AdminCreator which receives the necessary parameters
									//ThebigDataAirrpotHotelBaselStartFrame and the modal boolean to block the GUI bigDataAirportHOtelBaselStartFrame.
									AdminCreator adminCreator = new AdminCreator(bigDataAirportHotelBaselStartFrame, true);
									
									/* We set the URLs for the DataBase and DataBaseBackup. 
									 * 
									 * We save it in the userAHB instance, because this way we avoid writing a new class just to save the URLs. 
									 * To this instance anyway we have to call often.
									 */
									userAHB = new UserAHB();
									
									//First we set the userAHB extended from super Class
									setUserAHB(userAHB);
									
									//We set the values to the userAHB
									getUserAHB().setUrlDataBase(urlDataBase);
									getUserAHB().seturlDataBaseBackup(urlDataBaseBackup);
								
									
									//New Instance LogicAdminCreator with the parameters adminCreator and the URLs
									LogicAdminCreator logicAdminCreator = new LogicAdminCreator(adminCreator, bigDataAirportHotelBaselStartFrame, logicModelStartFrame);
									
									logicAdminCreator.setUserAHB(getUserAHB());
									//And the Controller Class.
									new AdminCreatorController(adminCreator, logicAdminCreator);
									
									
									//We close the configurationDirectory
									configurationDirectory.dispose();
									
									
									
									//And setVisible the adminCreator instance GUI
									adminCreator.setVisible(true);
									
									
									
								}
							});
						}
					});
					
					
					option = new Object[] {okJtButton};
					
					dialogJPanel.add(okMessage, BorderLayout.CENTER);
					dialogJPanel.add(okJtButton, BorderLayout.SOUTH);
					
					jDialog = new JOptionPane(dialogJPanel, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, imageOK, option, null). createDialog("Daten gespeichert");
					
					
					jDialog.addWindowListener(new WindowListener() {
						
						@Override
						public void windowOpened(WindowEvent e) {}
						
						@Override
						public void windowIconified(WindowEvent e) {}
						
						@Override
						public void windowDeiconified(WindowEvent e) {}
						
						@Override
						public void windowDeactivated(WindowEvent e) {}
						
						@Override
						public void windowClosing(WindowEvent e) {
							
							SwingUtilities.invokeLater(new Runnable() {
								
								@Override
								public void run() {
									
									AdminCreator adminCreator = new AdminCreator(bigDataAirportHotelBaselStartFrame, true);
									
									//We set the URLs for the DataBase and DataBaseBackup
									userAHB = new UserAHB();
									userAHB.setUrlDataBase(urlDataBase);
									userAHB.seturlDataBaseBackup(urlDataBaseBackup);
									
									
									LogicAdminCreator logicAdminCreator = new LogicAdminCreator(adminCreator, bigDataAirportHotelBaselStartFrame, logicModelStartFrame);
									
									logicAdminCreator.setUserAHB(getUserAHB());
									
									new AdminCreatorController(adminCreator, logicAdminCreator);
									
									configurationDirectory.dispose();
									adminCreator.setVisible(true);
									
									
									
								}
							});
							
						}
						
						@Override
						public void windowClosed(WindowEvent e) {}
						
						@Override
						public void windowActivated(WindowEvent e) {}
					});
					
					
					jDialog.setVisible(true);
					
				
					
					
				}
			});

	
			
		}


		
	}

}
