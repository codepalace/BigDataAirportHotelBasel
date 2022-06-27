package tech.codepalace.model;

import java.awt.HeadlessException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import tech.codepalace.controller.DataBaseGUIController;
import tech.codepalace.controller.LoginController;
import tech.codepalace.dao.DAOFundsachen;
import tech.codepalace.dao.DAOParking;
import tech.codepalace.dao.DaoException;
import tech.codepalace.dao.DaoFactory;
import tech.codepalace.dao.DaoParkingImpl;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.LoginUser;

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
	
	
	//Variables for Login and Logout
	private static LoginUser loginUser;
	private static LogicModelLogin logicModelLogin;
	
	
	



	
	//Methods to set and get the UserAHB need it for some classes.
	public void setUserAHB(UserAHB userAHB) {
		
		this.userAHB = userAHB;
	}
	
	public  UserAHB getUserAHB() {
		return this.userAHB;
	}
	
	
	
	/**
	 * @description logoutApplication it will be called from any JFrame. The first argument is the JFrame we have to close.
	 * <p/>The second argument is the JFrame we have to make visible to makes possible the login again.
	 * @param jframeToDispose
	 * @param jframeToVisible
	 */
	public  void logoutApplication(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, LogicModelStartFrame logicModelStartFrame) {
		

		LogicModel.logicModelStartFrame = logicModelStartFrame;
		LogicModel.loginUser = new LoginUser(LogicModel.bigDataAirportHotelBaselStartFrame, true);
		
		
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {

				//Clean the value loginUserText
				bigDataAirportHotelBaselStartFrame.loginUserText.setText("Benutzer: ");

				
				//Disable the admin Buttons and makes them not visible.
				bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.setVisible(false);
				bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.setEnabled(false);
				bigDataAirportHotelBaselStartFrame.btn_createDB.setVisible(false);
				bigDataAirportHotelBaselStartFrame.btn_createDB.setEnabled(false);
				
			

				//Clean the Textboxes
				LogicModel.loginUser.userLolingJTextField.setText("");
				LogicModel.loginUser.passwordField.setText("");
				
				//We hide the Buttons
				bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.setVisible(false);
				bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.setEnabled(false);
				bigDataAirportHotelBaselStartFrame.btn_createDB.setVisible(false);
				bigDataAirportHotelBaselStartFrame.btn_createDB.setEnabled(false);
				
				
				
				//if else to call for Login again.
				try {
					if(LogicModel.logicModelLogin==null) {

						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								
								LogicModel.logicModelLogin = new LogicModelLogin(LogicModel.loginUser);
								new LoginController(LogicModel.loginUser, bigDataAirportHotelBaselStartFrame, LogicModel.logicModelLogin, LogicModel.logicModelStartFrame);
								
								LogicModel.loginUser.setLocationRelativeTo(bigDataAirportHotelBaselStartFrame);
								LogicModel.loginUser.setVisible(true);
								
								
							}
						});
					}else {
						
						bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.setVisible(false);
						bigDataAirportHotelBaselStartFrame.btn_benutzerVerwalten.setEnabled(false);
						bigDataAirportHotelBaselStartFrame.btn_createDB.setVisible(false);
						bigDataAirportHotelBaselStartFrame.btn_createDB.setEnabled(false);
						
						LogicModel.loginUser.setVisible(true);
					}
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
			
		});
		
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
				
				
				 //New Instance of DataBAseGUIController we the arguments we need to pass so we can access from this Object
				new DataBaseGUIController(LogicModel.bigDataAirportHotelBaselStartFrame, dataBaseGUI, LogicModel.logicModelParking, 
						LogicModel.logicModelFundSachen);
				
				
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
						
						
						 //New Instance of DataBAseGUIController we the arguments we need to pass so we can access from this Object
						new DataBaseGUIController(LogicModel.bigDataAirportHotelBaselStartFrame, dataBaseGUI, LogicModel.logicModelParking, 
								LogicModel.logicModelFundSachen);
						
						
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
					DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), dataBaseGUI, loading);
					
					
					
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

	


}
