package tech.codepalace.model;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableModel;

import tech.codepalace.controller.BigDataAHBStartFrameController;
import tech.codepalace.controller.DataBaseGUIController;
import tech.codepalace.dao.DAOFundsachen;
import tech.codepalace.dao.DAOParking;
import tech.codepalace.dao.DaoException;
import tech.codepalace.dao.DaoFactory;
import tech.codepalace.dao.DaoFundsachenImpl;
import tech.codepalace.dao.DaoParkingImpl;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.OperatingSystemCheck;
import tech.codepalace.utility.SetIconOperatingSystem;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * 
 * @description super Class LogicModel it's going to have all the methods that classes inheriting from it can call.
 *
 */
public class LogicModel {
	
	//Intance UserAHB needed most of the time.
	private UserAHB userAHB;
	
	//Object BigDataAirportHotelBaselStartFrame declared as static because we need to call it inside some SwingUtilities blocks.
	private static BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;

	//Object DataBaseGUI declared as static because we need to call it inside some SwingUtilities blocks.
	private static DataBaseGUI dataBaseGUI;
	
	//Object Loading declared as static because we need to call it inside some SwingUtilities blocks.
	private static Loading loading;
	
	//Object LogicModelStartFrame declared as static because we need to call it inside some SwingUtilities blocks.
	private static LogicModelStartFrame logicModelStartFrame;
	
	//Object LogicModelDAtaBaseGUI declared as static because we need to call it inside some SwingUtilities blocks.
	private static LogicModelDataBaseGUI logicModelDataBaseGUI;
	
	//Object LogicModelParking declared as static because we need to call it inside some SwingUtilities blocks.
	private static LogicModelParking logicModelParking;
	
	//Opject LogicModelFundSachen
	private static LogicModelFundSachen logicModelFundSachen;
	
	
	protected DataEncryption dataEncryption = new DataEncryption();
	
	//Variable where should be stored the URL or Directory where our database is located
	protected String urlDataBase;
	
	//Variable used to generate the current DataBase name.
	protected LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
	
	//Variable to store the Database Name
	protected String dbName;
	
	protected File urlDataBaseFile;
	
	
	//Variable to know which kind of DataBase application has called. Or we need to call for Data.
	private static String dataBaseApplication;
	
	//Variable to store TableModel from JTable
	private TableModel tableModel;
	
	
	private int selectedRow;
	private String tableName;


	private String dataBaseStatus="";
	
	/* Variables to search in dataBase */
	//Variable to store like what we are going to search in database.
	private String searchSelected = "";
	
	//Variable to store the value what we are going to search in database. 
	private String toSearch = "";
	
	// Variables for error Message by Wrong Date Format
	//Date Format should be dd.mm.yyyy
	private String formatDateRegex = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((?:19|20)[0-9][0-9])$";
	
	public JDialog errorDateFormatJDialog;

	private JLabel messageErrorDateFormat;

	private JButton okButtonErrorDateFormat = new JButton("OK");

	private JPanel panelErrorDateFormat;

	private Object[] optionButtonErrorDateFormat = { this.okButtonErrorDateFormat };

	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	
	//Variables in case Date to search format is correct
	//DateTimeFormatter for the Pattern format dd.MM.yyyy 
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	//LocalDate object to store the Date entered by the user. We use this Object to pass also the dateTimeFormatter as argument. 
	//The format that the date should have.
	LocalDate localDateToSearchInDataBase = null;
	
	//Variable Date to store the Date calling the valueOf Method and as argument the LocalDate variable.
	Date dateToSearchInDataBase = null;
	
	
	
	

	
	//Methods to set and get the UserAHB need it for some classes.
	public void setUserAHB(UserAHB userAHB) {
		
		this.userAHB = userAHB;
	}
	
	public  UserAHB getUserAHB() {
		return this.userAHB;
	}
	
	
	
	/**
	 * @description logoutApplication it will be called from any JFrame.
	 */
	public  void logoutApplication() {
		

		
		/*
		 * We create an instance of the class. 
		 * 
		 * This class is useful to set the icon of our JFrame depending on the operating system where we are running our application.
		 * 
		 * This will be useful for us especially when we run the application on the macOS operating system. 
		 * 
		 * In this way we will be able to put the application icon in the Dock. 
		 */
	
		SetIconOperatingSystem setIconOperatingSystem = new SetIconOperatingSystem();
		
		
		//We evaluate which operating system we are running
				switch (OperatingSystemCheck.getOparatingSystem()) {
				
				//In the case of running a macOs operating system
				case MAC: 
					
					
					//we set some properties for our macos operating system
					System.setProperty("apple.laf.useScreenMenuBar", "true");
					System.setProperty("com.apple.mrj.application.apple.menu.about.name", "BigData Airport Hotel Basel");
					
					
					//We start with a new thread
					EventQueue.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							

							try {
								//get Default System LookAndFeel
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
								
							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
									| UnsupportedLookAndFeelException e) {
								e.printStackTrace();
							}	


							//Instantiate the main window(The principal GUI JFrame class)
							BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame = new BigDataAirportHotelBaselStartFrame();
							

							//Instantiate LogicModelStartFrame
						    LogicModelStartFrame logicModelStartFrame = new LogicModelStartFrame(bigDataAirportHotelBaselStartFrame);
						    

						    
							/* We instantiate the controller class and pass the required arguments to it.
							 * 
							 */
						    new BigDataAHBStartFrameController(bigDataAirportHotelBaselStartFrame, logicModelStartFrame);

							
						    //Center the GUI to the Screen.
							bigDataAirportHotelBaselStartFrame.setLocationRelativeTo(null);
							
							
							/*
							 * We call to set the Application Icon depending on the operating system we are running. 
							 * 
							 * The first parameter it receives is the JFrame that is going to set the icon. 
							 * The second parameter the path with the image that is in our resources folder and inside a package named img.
							 */
							setIconOperatingSystem.setIconJFrame(bigDataAirportHotelBaselStartFrame, "/img/iconoHotel.png");
							
							//We setVisible our GUI
							bigDataAirportHotelBaselStartFrame.setVisible(true);
						}
					});

					
					break;
					

			
					//In the case of running a another operating system	
				default:
					
				
					
		EventQueue.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							

							try {
								
								UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
								
							} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
									| UnsupportedLookAndFeelException e) {
								e.printStackTrace();
							}	


							//Instantiate the main window(The principal GUI JFrame class)
							BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame = new BigDataAirportHotelBaselStartFrame();
							
							
							//Instantiate LogicModelStartFrame
						    LogicModelStartFrame logicModelStartFrame = new LogicModelStartFrame(bigDataAirportHotelBaselStartFrame);
						    

							
						    new BigDataAHBStartFrameController(bigDataAirportHotelBaselStartFrame, logicModelStartFrame);
							
							//Set the icon four our JFrame
							bigDataAirportHotelBaselStartFrame.setLocationRelativeTo(null);
							
							//We set the JFrame icon calling the setIconImage Method.
							bigDataAirportHotelBaselStartFrame.setIconImage (new ImageIcon(getClass().getResource("/img/iconoHotel.png")).getImage());
							
							
							bigDataAirportHotelBaselStartFrame.setVisible(true);
						}
					});
					
					
					break;
				
				
				}
		
	}
	
	
	
	
	
	
	
	/**
	 * @description Method for calling to display the Parking Data.
	 * @param bigDataAirportHotelBaselStartFrame
	 */
	public void displayParking(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame) {
		
		//We set the value to the bigDataAirportHotelBaselStartFrame variable.
		LogicModel.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		
		/*
		 * Before we call to open the dataBaseGUI to display inside the JTable the Data from Table Parking located in DataBase we check if 
		 * bigDataAirportHotelBaselStartFrame is not null and isVisible to dispose this GUI Class so we avoid the user could game with the JButtons.
		 */
		if(LogicModel.bigDataAirportHotelBaselStartFrame !=null && LogicModel.bigDataAirportHotelBaselStartFrame.isVisible()) {
			LogicModel.bigDataAirportHotelBaselStartFrame.dispose();
		}

		
		
try {
	
			
	} catch (Exception e1) {
			e1.printStackTrace();
}
		
		
		//invoke a new Thread 
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				
				if(LogicModel.dataBaseGUI !=null && LogicModel.dataBaseGUI.isVisible()) {
					LogicModel.dataBaseGUI.dispose();
				}

		         //This Logical Model need the String argument to Know which kind of Application we are calling.
				 LogicModel.dataBaseGUI = new DataBaseGUI("PARKING");
				 
				 //This Logical Model need the String argument to Know which kind of Application we are calling.
				 //The second argument used to know from which GUI we are calling
				 LogicModel.logicModelDataBaseGUI = new LogicModelDataBaseGUI("PARKING");
				 
				 
				 //We set the UserAHB value
				 LogicModel.logicModelDataBaseGUI.setUserAHB(getUserAHB());
				 
				 LogicModel.logicModelParking = new LogicModelParking();
				 
				 //We set the UserAHB value
				 LogicModel.logicModelParking.setUserAHB(getUserAHB());
				 
				 LogicModel.logicModelFundSachen = new LogicModelFundSachen();
				 
				 //We set the UserAHB value
				 LogicModel.logicModelFundSachen.setUserAHB(getUserAHB());
				 
				
				 //
				 LogicModel.logicModelStartFrame = new LogicModelStartFrame(LogicModel.bigDataAirportHotelBaselStartFrame);
				 LogicModel.logicModelStartFrame.setUserAHB(getUserAHB());
				
				
				 //New Instance of DataBAseGUIController we the arguments we need to pass so we can access from this Object
				new DataBaseGUIController(LogicModel.bigDataAirportHotelBaselStartFrame, dataBaseGUI, LogicModel.logicModelParking, 
						LogicModel.logicModelFundSachen, LogicModel.logicModelStartFrame);
				
				
				try {
					//We set the value of the loginUserLabel GUI using the dataEncryption instance and calling the decryptData method.
					dataBaseGUI.loginUserLabel.setText("Benutzer: " + dataEncryption.decryptData(logicModelDataBaseGUI.getUserAHB().getUserName()));
					
			} catch (Exception e1) {
					e1.printStackTrace();
		}
				
				//We checkDatabase
				logicModelDataBaseGUI.checkDataBase(logicModelDataBaseGUI.getAppCalled());
				

				
			}
		});
		
	
		
	}
	
	

	

	
	/**
	 * @description Method to display the FundSache JFrame
	 * @param fundSachenToVisible
	 */
	public  void displayFundSachen(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame) {
		
		
		LogicModel.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		
		/*
		 * Before we call to open the dataBaseGUI to display inside the JTable the Data from Table Parking located in DataBase we check if 
		 * bigDataAirportHotelBaselStartFrame is not null and isVisible to dispose this GUI Class so we avoid the user could game with the JButtons.
		 */
		if(LogicModel.bigDataAirportHotelBaselStartFrame !=null && LogicModel.bigDataAirportHotelBaselStartFrame.isVisible()) {
			LogicModel.bigDataAirportHotelBaselStartFrame.dispose();
		}
		
		//invoke a new Thread 
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						
						if(LogicModel.dataBaseGUI !=null && LogicModel.dataBaseGUI.isVisible()) {
							LogicModel.dataBaseGUI.dispose();
						}
						
				         //This Logical Model need the String argument to Know which kind of Application we are calling.
						 LogicModel.dataBaseGUI = new DataBaseGUI("FUNDSACHEN");
						 
						 //This Logical Model need the String argument to Know which kind of Application we are calling.
						 //The second argument used to know from which GUI we are calling
						 LogicModel.logicModelDataBaseGUI = new LogicModelDataBaseGUI("FUNDSACHEN");
						 
						 
						 //We set the UserAHB value
						 LogicModel.logicModelDataBaseGUI.setUserAHB(getUserAHB());
						 
						 LogicModel.logicModelFundSachen = new LogicModelFundSachen();
						 
						 //We set the UserAHB value
						 LogicModel.logicModelFundSachen.setUserAHB(getUserAHB());
						 
						 //
						 LogicModel.logicModelStartFrame = new LogicModelStartFrame(LogicModel.bigDataAirportHotelBaselStartFrame);
						 LogicModel.logicModelStartFrame.setUserAHB(getUserAHB());
						
						
						 //New Instance of DataBAseGUIController we the arguments we need to pass so we can access from this Object
						new DataBaseGUIController(LogicModel.bigDataAirportHotelBaselStartFrame, dataBaseGUI, LogicModel.logicModelParking, 
								LogicModel.logicModelFundSachen, LogicModel.logicModelStartFrame);
						
						
						try {
							//We set the value of the loginUserLabel GUI using the dataEncryption instance and calling the decryptData method.
							dataBaseGUI.loginUserLabel.setText("Benutzer: " + dataEncryption.decryptData(logicModelDataBaseGUI.getUserAHB().getUserName()));
							
					} catch (Exception e1) {
							e1.printStackTrace();
				}
						
						//We checkDatabase
						logicModelDataBaseGUI.checkDataBase(logicModelDataBaseGUI.getAppCalled());
						

						
					}
				});

		
	}
	
	
	
	
	
	
	
	
	/**
	 * @description Method to display FitnessAbo JFrame
	 * @param fitnessAboToVisible
	 */
	public  void displayFitnessAbo(JFrame fitnessAboToVisible, JFrame JframeToClose) {
		
	}
	
	
	/**
	 * Method to display Uebergabe JFrame
	 * @param uebergabeToVisible
	 */
	public  void displayUebergabe(JFrame uebergabeToVisible, JFrame JframeToClose) {
		
	}
	
	
	/**
	 * Method to display PhoneBook JFrame
	 * @param phoneBookToVisible
	 */
	public  void displayPhoneBook(JFrame phoneBookToVisible, JFrame JframeToClose) {
		
	}
	

	
	
	/**
	 * @description this Method going to set all the values we need to call later the checkExistsDataBase Method so we can check if DataBase Derby Exists.
	 */
	public void checkDataBase(String dataBaseApplication) {
		
		LogicModel.dataBaseApplication = dataBaseApplication;
		
		
		try {
			
			
			//We get the the path where the database should be stored
			this.urlDataBase = this.dataEncryption.decryptData(getUserAHB().getUrlDataBase()); 
			
			//Name of the dataBase for this year. Each year we are going to have a new DataBase, to keep it from getting too big over time.
			this.dbName = "BigDataAHBaselDB" + now.getYear();
			
			//We get the Path ulrDataBase + the dbName and put the value inside a File variable for after checking the existence of the DataBase
			this.urlDataBaseFile = new File(this.urlDataBase + File.separator + dbName); 

			//We call for checkExistsDataBasel as parameters urlDataBaseFile(File object).
			checkExistsDataBase(this.urlDataBaseFile);
			
			
			
			
			
		} catch (HeadlessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	

	
	/**
	 * @description Method to check if the DataBase exists.
	 * @param urlDataBaseFile
	 */
	protected void checkExistsDataBase(File urlDataBaseFile) {

		
		//We check if the received argument File(urlDataBaseFile exists. 
		if(urlDataBaseFile.exists()) {
				
			
					//we are going to call for a new instance of Loading Class with the JFrame in Background and the true to blocked
					loading = new Loading(bigDataAirportHotelBaselStartFrame, true);
				
			
			
		
			
			/**
			 * We evaluate the dataBaseApplicaiton value to know which kind DAOParking parameters we are going to send to the DAOParking constructor.
			 * Also in future commits to know which kind of DAO object we are going to instantiate.
			 */
			switch (LogicModel.dataBaseApplication) {
				
				//In case of PARKING
				case "PARKING":
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							


							DAOParking daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, loading);
							
							try {
								//Check if we have a Parking Table.
								daoParking.checkTableParking();
							} catch (DaoException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
					
					break;
					
					
				case "FUNDSACHEN": 
					
					//We create a new DAOFundsachen Object passing the DaoFundsachenImpl class with the parameters.
					DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), dataBaseGUI, loading, logicModelFundSachen);
					
					
					
					try {
						
						//now call to check if the Table exists.
						daoFundsachen.checkTableFundsachen();
						
					} catch (DaoException  e) {
						
						e.printStackTrace();
					}

				default:
					break;
			}
			
			
			
			
			
			
			
		

		}else { //the DataBase do not exists 
			
			/*
			 * let's proceed with the instructions for creating the database and subsequently create the connection
			 */

			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					
					
					//Variable with the Database Driver Instruction jdbc:derby our Database Derby Embedded. It receive the urlDataBase included the dbName.
					String url = "jdbc:derby:" + urlDataBase + File.separator + dbName;
					
					
							
							 loading = new Loading(bigDataAirportHotelBaselStartFrame, true);
							 
							 
							 new DaoFactory(url, loading, dataBaseApplication, dataBaseGUI);
							 
					
	
					

				}
			});
			

			

		}
		
		
		
		
		
	}
	
	
	/**
	 * @description Method to delete a row from the database.
	 * <p>This method has 2 parameters:<br/></p>
	 * <ol>
	 * 		<li>int selectedRow(for the selected row;</li>
	 * 		<li>String tableName(with the Table name(containing the name of the table where the row should be deleted in the database);</li>
	 * 		<li>TableModel with the TableModel of the current JTable we want to delete a row.</li>
	 * </ol>
	 * 
	 * <h2>Condition required to be able to delete</h2>
	 * 
	 * <p>- Only admin users can delete entries from the database.</p>
	 * 
	 * <p>With this we avoid the bad manipulation of the data saved in the database by unauthorized persons.</p>
	 * @param selectedRow
	 * @param tableName
	 * @param tableModel
	 */
	public void deleteRowDataBase(int selectedRow, String tableName, TableModel tableModel) {
		
		//We set the values of our variables
		this.selectedRow = selectedRow;
		this.tableName = tableName;
		this.tableModel = tableModel;
	
		int id = (int)this.tableModel.getValueAt(this.selectedRow, 0);
		
		
		
		/*
		 *variable that stores the privileges of the user.
		 *We will know if the user can delete any entry from the database 
		 *or it is forbidden for the current user 
		 */
		String privilege = "";
		
		try {
			/*
			 * we decrypt the value of the current user privilege and store it in our variable.
			 */
			privilege = this.dataEncryption.decryptData(getUserAHB().getPrivilege());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/*
		 *We evaluate what privileges the user has to authorize or not the deletion of the entry.
		 *
		 * if(privilege = ADMIN we can then we can proceed to create the DAO object to call the deleteEntryDataBase.
		 int id = (int)model.getValueAt(selectedRow, 0);
		 */
		
		if(privilege.equals("ADMIN")) {
			
			//We create a new DAOFundsachen Object passing the DaoFundsachenImpl class with the parameters.
			DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), dataBaseGUI, loading, logicModelFundSachen);
			
			//We call the Method to deleteDatabaseEntry
			try {
				daoFundsachen.deleteDatabaseEntry(this.tableName, id);
			} catch (DaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else {
			
			//Other users cannot delete from Database
		}
		
	}

	
	
	public LogicModelFundSachen getLogicModelFundsachen() {
		return LogicModel.logicModelFundSachen;
	}

	/**
	 * @return the dataBaseStatus
	 */
	public String getDataBaseStatus() {
		return dataBaseStatus;
	}

	/**
	 * @param dataBaseStatus the dataBaseStatus to set
	 */
	public void setDataBaseStatus(String dataBaseStatus) {
		this.dataBaseStatus = dataBaseStatus;
	}
	
	
	
	
	public void searchResultsInDataBase(DataBaseGUI dataBaseGUI) {
		
		LogicModel.dataBaseGUI = dataBaseGUI;
		
		searchSelected = LogicModel.dataBaseGUI.searchJComboBox.getSelectedItem().toString();
		
		toSearch = LogicModel.dataBaseGUI.searchText.getText();
		
	}

	/**
	 * @description Method to get the value like what we are searching in database
	 * @return the searchSelected
	 */
	public String getSearchSelected() {
		return searchSelected;
	}

	/**
	 * @description Method to get the value of the keyword we are going to search in Database.
	 * @return the toSearch
	 */
	public String getToSearch() {
		return toSearch;
	}
	
	
	
	/**
	 * @description Method to check if the Date Format is correct before we search in Database for a Date Entry.
	 */
	public boolean checkDateFormatToSearchInDataBase() {
		
		
		
				//Initialize the Message text.
				this.messageErrorDateFormat = new JLabel("Sie haben eine falsches Datumsformat eingegeben. bitte geben Sie ein korrektes Datumsformat ein(dd.mm.yyyy");
				
				//JPanel for the Error Message
				this.panelErrorDateFormat = new JPanel(new BorderLayout());
				
				//We Center the Error Messsage to the JPanel
				this.panelErrorDateFormat.add(messageErrorDateFormat, BorderLayout.CENTER);
				
				
				//To the okButtonErrorDateFormat we add some ActionListener and KeyListener by pressing just close the JDialog.
				
				this.okButtonErrorDateFormat.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						errorDateFormatJDialog.dispose();
						
					}
				});
				
				
				this.okButtonErrorDateFormat.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {}

					@Override
					public void keyPressed(KeyEvent e) {

						errorDateFormatJDialog.dispose();
						
					}

					@Override
					public void keyReleased(KeyEvent e) {}
					
				});
				
				
				
				//if the Date format is not correct we invoke to display errorDateFormatJDialog with the error message and return false
				if(!Pattern.matches(formatDateRegex, dataBaseGUI.searchText.getText())) {
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {

							errorDateFormatJDialog = new JOptionPane(panelErrorDateFormat, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, errorImg,
									optionButtonErrorDateFormat, null).createDialog("Falsches Datumsformat");
							
							errorDateFormatJDialog.setAlwaysOnTop(true);
							errorDateFormatJDialog.setVisible(true);
							
							errorDateFormatJDialog.dispose();
							
							dataBaseGUI.searchText.requestFocus();
							
						}
					});
				
					return false;
				} else {
					
					//The Date format is correct
					
					//We set the values for the Date to search in Database. First the LocalDate format
					localDateToSearchInDataBase = LocalDate.parse(dataBaseGUI.searchText.getText(), dateTimeFormatter);
					
					//We set the value as Date format calling the valueOf(LocalDate variable)
					dateToSearchInDataBase = Date.valueOf(localDateToSearchInDataBase);
					
					//return true to continue by the LogicModel extended classes.
					return true;
				}
				
				
		
	}

	
	
	/**
	 * @description Method to get the Date we want to search in Database Table.
	 * @return the dateToSearchInDataBase
	 */
	public Date getDateToSearchInDataBase() {
		return dateToSearchInDataBase;
	}
	
	
	
	
	
	
	




	
	
	
	
}
