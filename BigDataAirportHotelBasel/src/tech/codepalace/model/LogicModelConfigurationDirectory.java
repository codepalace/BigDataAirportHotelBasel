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
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	
	
	

	private File sourceFile, destinationFile; 
	
	//Path of the project where the configuration file will be located
	protected String projectDirectoryString = System.getProperty("user.dir");
	
	private ImageIcon errorImage = new ImageIcon(getClass().getResource("/img/error.png"));
	
	
	
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
	 * @description Method to load a configuration file and to copy in the Application Directory
	 * <p>This Method will be called in case the user by the first load of the application the program do not find a configuration file.</p>
	 * <p>It could be possible that some of the Team delete the configuration file from the application directory and we need to save again the configuration file in 
	 * application directory.</p>
	 */
	public void loadConfigFile() {
		
		
		

		
		/*
		 * Now when we load the configuration file, we copy this loaded file to the App Directory. 
		 */
		
	
switch (OperatingSystemCheck.getOparatingSystem()) {
	
	
	/*
	 * 	In case macOs we use FileDialog to load our config.properties file
	 */
		case MAC:
		
			System.setProperty("apple.awt.fileDialogForDirectories", "false");
			
			/* We initialize the FileDialog Object
			 * - First argument the Dialog parent(configurationDirectory GUI).
			 * - Second argument Title. 
			 * Third argument Mode. In this case LOAD Mode. 
			 */
			this.fileDialog = new FileDialog(this.configurationDirectory, "Konfigurationsdatei wählen", FileDialog.LOAD);
			
			//invoke one new event
			EventQueue.invokeLater(new Runnable() {

				@Override
				public void run() {
					
					//We set the Filter files
					fileDialog.setFilenameFilter(new FilenameFilter() {  
						
						/*
						 * this Method accept return receive 2 Arguments File for the File
						 * second argument for the name of the file. 
						 * 
						 * It will be returned the name just ends by the extent of the File(I mean how ends the file .txt, .jpg etc).
						 * 
						 * In our case the configuration file ends by .properties. That means our FileDialog should only aloud to select .properties files. 
						 */
	                    public boolean accept(File dir, String name) {
	                        return  name.endsWith(".properties");
	                    }
	                });
					
					//We set the FileDialog object visible.
					fileDialog.setVisible(true);
					
					   
					//File was selected
					if(fileDialog.getFile()!=null) {
						
						
						
						//We set the value of the sourcFile Variable(File) mix getDirectory + getFile to have the absolute path.
						sourceFile = new File(fileDialog.getDirectory() + fileDialog.getFile());
						
						/*
						 * desstinationFile(File). We set the value projectDirectoryString(Application directory) +
						 * File.separator for any operating system and the name config.properties(name should have our configuration file). 
						 */
						destinationFile = new File(projectDirectoryString +  File.separator + "config.properties");
						
						
						
						 try {
							    
							 /*
							  * We call the copyFileUsingJava7Files Method. 
							  * 
							  * Method to copy the selected file to the destinationFile(application Directory)
							  * 
							  * First argument is the sourceFile(File), Second argument destinationFile(File).
							  * 
							  * 
							  */
								copyFileUsingJava7Files(sourceFile, destinationFile);
							} catch (IOException e) {
								e.printStackTrace();
							}
						 
						 	  /*
						 	   * After having selected our configuration file and is copied to the  application directory.
						 	   * 
						 	   * - We close the bigDataAirportHotelBaselStartFrame GUI;
						 	   * - We close configurationDirectoy GUI;
						 	   * - And call logoutApplication so the bigDataAirportHotelBaselStartFrame will be loaded again and the LoginUser GUI.
						 	   * 
						 	   * Now we have again our configuration file with the user and passwords saved in our Application directory and we can login. 
						 	   * 
						 	   * All this will be used just in the case some one copy the application to another directory and while the application is portable 
						 	   * we do not have the old configuration file in the new directory where the application was copied and while we are using one 
						 	   * Application called from a directory located in a LocalDisk accessible from different computers we 
						 	   * could load the configuration file we have created at the first start to save it in the new Application directory. 
						 	   * 
						 	   * Thats not the Idea to have a Copy of the Application in every computer. The Idea is to launch the application from the same 
						 	   * local disk directory. We can better have shortcut from our application in every Desktop our any other Taskbar from Windows that
						 	   * always have access to call our Application located in the Network Local Disk.
						 	   * 
						 	   * Anyway if somebody make a copy of the application because they have delete the shortcut and instead of make a new shortcut they 
						 	   * just make copy paste of our application we have the choice to load the configuration file and it will be placed beside this new copy. 
						 	   * so we have the same login data. 
						 	   * 
						 	   * i know that this whole way of working of this application sometimes seems a confusing way and that it could be done better, 
						 	   * such as accessing an online file to read the data or modify them so we would avoid erroneous manipulations, but we are restricted at the moment with these options. 
							   *
							   *Let the client decide afterwards if he wants to modify the way of reading the configuration file, from where we read the configuration file, from an online server etc.
						 	   */
							   bigDataAirportHotelBaselStartFrame.dispose();
							   configurationDirectory.dispose();
							   logoutApplication();
						
						
					}else {
						
						//In case we do not select any configuration file we return one error Message
						JOptionPane.showMessageDialog(null, "Sie haben kein Konfigurationsdatei ausgewählt!", "Kein Konfigurationsdatei ausgewählt", JOptionPane.OK_OPTION, errorImage);
		
						
						
					}
					
			
					
				}
				
			});
			
			
		   break;
		   
		   
		   /*
		    * In case WINDOWS we use almost the same instructions with the different we use in this case 
		    * JFileChooser and not FileDialog Object. 
		    */
		case WINDOWS:
			
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					
					//Initialize the JFileChooser Object
					jFileChooser = new JFileChooser();
					
					//We set the current directory
					jFileChooser.setCurrentDirectory(new java.io.File("."));
					
					//Title for the JFileChooser Dialog
					jFileChooser.setDialogTitle("Konfigurationsdatei auswählen!");
					
					//I indicate that only Files could be chosen.
					jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					
					//Instance FileNameExtensionFilter to set the file filter we aloud to select
					FileNameExtensionFilter filter = new FileNameExtensionFilter("CONFIG PROPERTIES FILES", "properties");
					
					//Add the choosable Filter.
					jFileChooser.addChoosableFileFilter(filter);
					
					//set the Filter to aour JFileChooser Object.
					jFileChooser.setFileFilter(filter);

					//Variable to validate if select file or not
					int returnVal = jFileChooser.showOpenDialog(configurationDirectory);
					
					//If returnVale something was selected
					if (returnVal == JFileChooser.APPROVE_OPTION) {

						//Set the value to sourceFile. The selected File
						sourceFile = new File(jFileChooser.getSelectedFile().toString());
					
						
					
					   //destinationFile set value the Application directory and the name of the file
					   destinationFile = new File(projectDirectoryString +  File.separator + "config.properties");


					   
					   try {
							/*
							 * We call the copyFileUsingJava7Files Method.
							 * 
							 * Method to copy the selected file to the destinationFile(application
							 * Directory)
							 * 
							 * First argument is the sourceFile(File), Second argument
							 * destinationFile(File).
							 * 
							 * 
							 */
							copyFileUsingJava7Files(sourceFile, destinationFile);
					} catch (IOException e) {
						
						e.printStackTrace();
					}
					   
					   //The same process that macOs 
					   bigDataAirportHotelBaselStartFrame.dispose(); //Close this GUI
					   configurationDirectory.dispose(); //Close this GUI
					   logoutApplication(); //Call for logout and login again.
						
					}else {
						
						//In case we do not select any configuration file we return one error Message
						JOptionPane.showMessageDialog(null, "Sie haben kein Konfigurationsdatei ausgewählt!", "Kein Konfigurationsdatei ausgewählt", JOptionPane.OK_OPTION, errorImage);
		
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
	
	
	
	/**
	 * @description Method to copy a file from a source directory to a destination directory.
	 * <p>It will be used Files Class from packet java.nio.file(Java7).</p>
	 * @param source
	 * @param dest
	 * @throws IOException
	 */
	private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
	    Files.copy(source.toPath(), dest.toPath());
	}
	
	
	

}
