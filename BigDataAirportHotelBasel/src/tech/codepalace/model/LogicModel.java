package tech.codepalace.model;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
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
	
	//Variable to know which GUI has called.
	private static String guiCaller;
	
	
	//Variable to store TableModel from JTable
	private TableModel tableModel;
	
	
	private int selectedRow;
	private String tableName;


	private String dataBaseStatus="";

	
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
	public void displayParking(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, String guiCaller) {
		
		//We set the value to the bigDataAirportHotelBaselStartFrame variable.
		LogicModel.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		LogicModel.guiCaller = guiCaller;
//		LogicModel.bigDataAirportHotelBaselStartFrame.setEnabled(false);
		
		
try {
	
			
	} catch (Exception e1) {
			e1.printStackTrace();
}
		
		
		//invoke a new Thread 
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				if(LogicModel.dataBaseGUI.isVisible()) {
					LogicModel.dataBaseGUI.dispose();
				}
		         //This Logical Model need the String argument to Know which kind of Application we are calling.
				 LogicModel.dataBaseGUI = new DataBaseGUI("PARKING");
				 
				 //This Logical Model need the String argument to Know which kind of Application we are calling.
				 //The second argument used to know from which GUI we are calling
				 LogicModel.logicModelDataBaseGUI = new LogicModelDataBaseGUI("PARKING", "StartFrame");
				 
				 
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
	public  void displayFundSachen(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, String guiCaller) {
		
		
		LogicModel.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		LogicModel.guiCaller = guiCaller;
		
		//invoke a new Thread 
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						
				         //This Logical Model need the String argument to Know which kind of Application we are calling.
						 LogicModel.dataBaseGUI = new DataBaseGUI("FUNDSACHEN");
						 
						 //This Logical Model need the String argument to Know which kind of Application we are calling.
						 //The second argument used to know from which GUI we are calling
						 LogicModel.logicModelDataBaseGUI = new LogicModelDataBaseGUI("FUNDSACHEN", "StartFrame");
						 
						 
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
			
			
			switch (LogicModel.guiCaller) {
				
				
				case "StartFrame":
					
					//we are going to call for a new instance of Loading Class with the JFrame in Background and the true to blocked
					loading = new Loading(bigDataAirportHotelBaselStartFrame, true);
				
					break;
					

				default:
					break;
			}
			
		
			
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
					
					switch (LogicModel.guiCaller) {
						
						
						case "StartFrame":
							

							
							 loading = new Loading(bigDataAirportHotelBaselStartFrame, true);
							 
							 
							 new DaoFactory(url, loading, dataBaseApplication, dataBaseGUI);
							 
							
							break;

						default:
							break;
					}
	
					

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


	
	
}
