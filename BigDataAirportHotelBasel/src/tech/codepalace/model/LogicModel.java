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
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.TableModel;

import tech.codepalace.controller.BigDataAHBStartFrameController;
import tech.codepalace.controller.DataBaseGUIController;
import tech.codepalace.controller.DateChronologyCorrectionController;
import tech.codepalace.dao.DAOFitnessAbo;
import tech.codepalace.dao.DAOFitnessImpl;
import tech.codepalace.dao.DAOFundsachen;
import tech.codepalace.dao.DAOParking;
import tech.codepalace.dao.DAOUebergabe;
import tech.codepalace.dao.DAOUebergabeImpl;
import tech.codepalace.dao.DaoException;
import tech.codepalace.dao.DaoFactory;
import tech.codepalace.dao.DaoFundsachenImpl;
import tech.codepalace.dao.DaoParkingImpl;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.OperatingSystemCheck;
import tech.codepalace.utility.SetIconOperatingSystem;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.DateChronologyCorrection;
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
	
	//Object LogicModelFundSachen
	private static LogicModelFundSachen logicModelFundSachen;
	
	//Object LogicModelFitnessAbo
	private static LogicModelFitnessAbo logicModelFitnessAbo;
	
	//Object LogicModelUebergabe
	private static LogicModelUebergabe logicModelUebergabe;
	
	//Object LogicModelTelefonbuch
	private static LogicModelTelefonbuch logicModelTelefonbuch;
	
	
	
	protected DataEncryption dataEncryption = new DataEncryption();
	
	//Variable where should be stored the URL or Directory where our database is located
	protected String urlDataBase;

	//Variable to store the Database Name
	protected String dbName;
	
	protected File urlDataBaseFile;
	
	
	//Variable to know which kind of DataBase application has called. Or we need to call for Data.
	private static String dataBaseApplication;
	
	//Variable to store TableModel from JTable
	private TableModel tableModel;
	
	
	private static int selectedRow;
	private static String tableName;


	private String dataBaseStatus="";
	
	/* Variables to search in dataBase */
	//Variable to store like what we are going to search in database.
	private String searchSelected = "";
	
	//Variable to store the value what we are going to search in database. 
	private String toSearch = "";
	

	
	// Variables for error Message by Wrong Date Format
	//Date Format should be dd.mm.yyyy
//	private String formatDateRegex = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((?:19|20)[0-9][2-9])$";
	
	//New Date Format Regex dd.mm.yyyy that aloud until 9999
	private String formatDateRegex = "(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}";
	
	//(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])\.\d{4}
	public JDialog errorDateFormatJDialog;

	private JLabel messageErrorDateFormat;

	private JButton okButtonErrorDateFormat = new JButton("OK");

	private JPanel panelErrorDateFormat;

	private Object[] optionButtonErrorDateFormat = { this.okButtonErrorDateFormat };

	private ImageIcon errorImg = new ImageIcon(getClass().getResource("/img/error.png"));
	
	private ImageIcon preventionImage = new ImageIcon(getClass().getResource("/img/prevention.png"));
	
	
	//Variables in case Date to search format is correct
	//DateTimeFormatter for the Pattern format dd.MM.yyyy 
	private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	
	
	//necesitamos este patron de formato de fecha para poder guardar el valor en las variables de tipo LocalDate
	//Las fechas van a ser introducidas por el usuario en formato dd.MM.yyyy para poder comprobar si son correctas. 
	//Luego si el formato dd.MM.yyyy es correcto pasamos dentro de la misma clase controladora a setLocalDates remplazando el . por /
	//De ahi que necesitmos que esta variable reciba este patron dd/MM/yyyy para darle el valor adecuando que necesitamos a las variables LocalDate
	//Localdate recibe el valor convertido a partir de un String. Es por eso.
	private DateTimeFormatter dateTimeFormatterForSavingDataBase = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	//LocalDate object to store the Date entered by the user. We use this Object to pass also the dateTimeFormatter as argument. 
	//The format that the date should have.
	private LocalDate localDateToSearchInDataBase = null;
	
	//Variable Date to store the Date calling the valueOf Method and as argument the LocalDate variable.
	private Date dateToSearchInDataBase = null;
	
	//Variables to check Date format entered by the user before we are going to save the new Entry in DataBase any Table. 
	private String dateEnteredByUser = "";
	private static String inputTextBoxName = "";
	
	private String componentHadFocus="";

	private boolean dateFormatCorrect = false;
	
	
	//variable to store the calculation of the sum of two dates
	private long totalDaysPlus;
	
	//variables to calculate to LocalDates
	private LocalDate firstDate, secondDate;
	
	private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	private LocalDate localDateToBeModified = null;
	
	private String [] dateAsStringToBeModified = {"",""};
	
	private String dateCorrection = "";
	
	
	//Variables to modify values in JTable
	private static int selectedColumn;

	private	JLabel messageLabelRequestLaterDateCorrection;
	
	private static String message = "";
	
	private JTextField dateTextFieldRequestLaterDateCorrection;
	
	private JPanel panelRequestBox;
	
	
	

	public LogicModel() {
		
		init();
	}
	
	
	private void init() {
		
		
		
		//Initialize the Message text.
		this.messageErrorDateFormat = new JLabel("Sie haben eine falsches Datumsformat eingegeben. bitte geben Sie ein korrektes Datumsformat ein(dd.mm.yyyy)");
		
		//JPanel for the Error Message
		this.panelErrorDateFormat = new JPanel(new BorderLayout());
		
		//We Center the Error Messsage to the JPanel
		this.panelErrorDateFormat.add(messageErrorDateFormat, BorderLayout.CENTER);
		
		
		
	}
	
	
	

	
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
				 
				 LogicModel.logicModelParking = new LogicModelParking(dataBaseGUI, loading);
				 
				 //We set the UserAHB value
				 LogicModel.logicModelParking.setUserAHB(getUserAHB());
				 
				 LogicModel.logicModelFundSachen = new LogicModelFundSachen();
				 
				 //We set the UserAHB value
				 LogicModel.logicModelFundSachen.setUserAHB(getUserAHB());
				 
				 
				 LogicModel.logicModelParking = new LogicModelParking(dataBaseGUI, loading);
				 
				 //We set the UserAHB value
				 LogicModel.logicModelParking.setUserAHB(getUserAHB());
				 
				 LogicModel.logicModelFitnessAbo = new LogicModelFitnessAbo();
				 
				 //We set the UserAHB value
				 LogicModel.logicModelFitnessAbo.setUserAHB(getUserAHB());
				 
				 //Instance and initialize the logicModelUebergabe
				 LogicModel.logicModelUebergabe = new LogicModelUebergabe();
				 
				 
				 //set the UserAHB value
				 LogicModel.logicModelUebergabe.setUserAHB(getUserAHB());
				 
				 
				 //Instance and initialize the LogicModelTelefonbuch
				 LogicModel.logicModelTelefonbuch = new LogicModelTelefonbuch();
				 
				 
				 //set the UserAHB value
				 LogicModel.logicModelTelefonbuch.setUserAHB(getUserAHB());
				 
				 
				 
				
				 //
				 LogicModel.logicModelStartFrame = new LogicModelStartFrame(LogicModel.bigDataAirportHotelBaselStartFrame);
				 LogicModel.logicModelStartFrame.setUserAHB(getUserAHB());
				
				
				 //New Instance of DataBAseGUIController we the arguments we need to pass so we can access from this Object
				new DataBaseGUIController(LogicModel.bigDataAirportHotelBaselStartFrame, dataBaseGUI, LogicModel.logicModelParking, 
						LogicModel.logicModelFundSachen, LogicModel.logicModelFitnessAbo, LogicModel.logicModelUebergabe, LogicModel.logicModelTelefonbuch);
				
				
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
		 * Before we call to open the dataBaseGUI to display inside the JTable the Data from Table fundsachenTable located in DataBase we check if 
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
						 
						 
						 LogicModel.logicModelParking = new LogicModelParking(dataBaseGUI, loading);
						 
						 //We set the UserAHB value
						 LogicModel.logicModelParking.setUserAHB(getUserAHB());
						 
						 LogicModel.logicModelFitnessAbo = new LogicModelFitnessAbo();
						 
						 //We set the UserAHB value
						 LogicModel.logicModelFitnessAbo.setUserAHB(getUserAHB());
					
						 //Instance and initialize the logicModelUebergabe
						 LogicModel.logicModelUebergabe = new LogicModelUebergabe();
						 
						 
						 //set the UserAHB value
						 LogicModel.logicModelUebergabe.setUserAHB(getUserAHB());
						 
						 
						 //Instance and initialize the LogicModelTelefonbuch
						 LogicModel.logicModelTelefonbuch = new LogicModelTelefonbuch();
						 
						 
						 //set the UserAHB value
						 LogicModel.logicModelTelefonbuch.setUserAHB(getUserAHB());
						 
						 //
						 LogicModel.logicModelStartFrame = new LogicModelStartFrame(LogicModel.bigDataAirportHotelBaselStartFrame);
						 LogicModel.logicModelStartFrame.setUserAHB(getUserAHB());
						
						
						 //New Instance of DataBAseGUIController we the arguments we need to pass so we can access from this Object
						new DataBaseGUIController(LogicModel.bigDataAirportHotelBaselStartFrame, dataBaseGUI, LogicModel.logicModelParking, 
								LogicModel.logicModelFundSachen, LogicModel.logicModelFitnessAbo, LogicModel.logicModelUebergabe, LogicModel.logicModelTelefonbuch);
						
						
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
	public  void displayFitnessAbo(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame) {
		
		LogicModel.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		
		/*
		 * Before we call to open the dataBaseGUI to display inside the JTable the Data from Table fitnessAboTable located in DataBase we check if 
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
						 LogicModel.dataBaseGUI = new DataBaseGUI("FITNESSABO");
						 
						 //This Logical Model need the String argument to Know which kind of Application we are calling.
						 //The second argument used to know from which GUI we are calling
						 LogicModel.logicModelDataBaseGUI = new LogicModelDataBaseGUI("FITNESSABO");
						 
						 
						 //We set the UserAHB value
						 LogicModel.logicModelDataBaseGUI.setUserAHB(getUserAHB());
						 
						 LogicModel.logicModelFitnessAbo = new LogicModelFitnessAbo();
						 
						 //We set the UserAHB value
						 LogicModel.logicModelFitnessAbo.setUserAHB(getUserAHB());
						 
						 LogicModel.logicModelFundSachen = new LogicModelFundSachen();
						 
						 LogicModel.logicModelFundSachen.setUserAHB(getUserAHB());
						 
						 LogicModel.logicModelParking = new LogicModelParking(dataBaseGUI, loading);
						 
						 LogicModel.logicModelParking.setUserAHB(getUserAHB());
						 
						 //
						 LogicModel.logicModelStartFrame = new LogicModelStartFrame(LogicModel.bigDataAirportHotelBaselStartFrame);
						 LogicModel.logicModelStartFrame.setUserAHB(getUserAHB());
						
						 //Instance and initialize the logicModelUebergabe
						 LogicModel.logicModelUebergabe = new LogicModelUebergabe();
						 
						 
						 //set the UserAHB value
						 LogicModel.logicModelUebergabe.setUserAHB(getUserAHB());
						 
						 //Instance and initialize the LogicModelTelefonbuch
						 LogicModel.logicModelTelefonbuch = new LogicModelTelefonbuch();
						 
						 
						 //set the UserAHB value
						 LogicModel.logicModelTelefonbuch.setUserAHB(getUserAHB());
						 
						
						 //New Instance of DataBAseGUIController we the arguments we need to pass so we can access from this Object
						new DataBaseGUIController(LogicModel.bigDataAirportHotelBaselStartFrame, 
								LogicModel.dataBaseGUI,  LogicModel.logicModelParking,  LogicModel.logicModelFundSachen, 
								LogicModel.logicModelFitnessAbo, LogicModel.logicModelUebergabe, LogicModel.logicModelTelefonbuch);
						
						
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
	 * Method to display Uebergabe JFrame
	 * @param uebergabeToVisible
	 */
	public  void displayUebergabe(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame) {
		
		LogicModel.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		
		/*
		 * Before we call to open the dataBaseGUI to display inside the JTable the Data from Table fitnessAboTable located in DataBase we check if 
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
						 LogicModel.dataBaseGUI = new DataBaseGUI("UEBERGABE");
						 
						 //This Logical Model need the String argument to Know which kind of Application we are calling.
						 //The second argument used to know from which GUI we are calling
						 LogicModel.logicModelDataBaseGUI = new LogicModelDataBaseGUI("UEBERGABE");
						 
						 
						 //We set the UserAHB value
						 LogicModel.logicModelDataBaseGUI.setUserAHB(getUserAHB());
						 
						 LogicModel.logicModelFitnessAbo = new LogicModelFitnessAbo();
						 
						 //We set the UserAHB value
						 LogicModel.logicModelFitnessAbo.setUserAHB(getUserAHB());
						 
						 LogicModel.logicModelFundSachen = new LogicModelFundSachen();
						 
						 LogicModel.logicModelFundSachen.setUserAHB(getUserAHB());
						 
						 LogicModel.logicModelParking = new LogicModelParking(dataBaseGUI, loading);
						 
						 LogicModel.logicModelParking.setUserAHB(getUserAHB());
						 
						 //
						 LogicModel.logicModelStartFrame = new LogicModelStartFrame(LogicModel.bigDataAirportHotelBaselStartFrame);
						 LogicModel.logicModelStartFrame.setUserAHB(getUserAHB());
						
						 //Instance and initialize the logicModelUebergabe
						 LogicModel.logicModelUebergabe = new LogicModelUebergabe();
						 
						 
						 //set the UserAHB value
						 LogicModel.logicModelUebergabe.setUserAHB(getUserAHB());
						
						 //New Instance of DataBAseGUIController we the arguments we need to pass so we can access from this Object
						new DataBaseGUIController(LogicModel.bigDataAirportHotelBaselStartFrame, 
								LogicModel.dataBaseGUI,  LogicModel.logicModelParking,  LogicModel.logicModelFundSachen, 
								LogicModel.logicModelFitnessAbo, LogicModel.logicModelUebergabe, LogicModel.logicModelTelefonbuch);
						
						
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
	 * Method to display PhoneBook JFrame
	 * @param phoneBookToVisible
	 */
	public  void displayPhoneBook(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame) {
		LogicModel.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		
		/*
		 * Before we call to open the dataBaseGUI to display inside the JTable the Data from Table fitnessAboTable located in DataBase we check if 
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
						 LogicModel.dataBaseGUI = new DataBaseGUI("TELEFONBUCH");
						 
						 //This Logical Model need the String argument to Know which kind of Application we are calling.
						 //The second argument used to know from which GUI we are calling
						 LogicModel.logicModelDataBaseGUI = new LogicModelDataBaseGUI("TELEFONBUCH");
						 
						 
						 //We set the UserAHB value
						 LogicModel.logicModelDataBaseGUI.setUserAHB(getUserAHB());
						 
						 LogicModel.logicModelFitnessAbo = new LogicModelFitnessAbo();
						 
						 //We set the UserAHB value
						 LogicModel.logicModelFitnessAbo.setUserAHB(getUserAHB());
						 
						 LogicModel.logicModelFundSachen = new LogicModelFundSachen();
						 
						 LogicModel.logicModelFundSachen.setUserAHB(getUserAHB());
						 
						 LogicModel.logicModelParking = new LogicModelParking(dataBaseGUI, loading);
						 
						 LogicModel.logicModelParking.setUserAHB(getUserAHB());
						 
						 //
						 LogicModel.logicModelStartFrame = new LogicModelStartFrame(LogicModel.bigDataAirportHotelBaselStartFrame);
						 LogicModel.logicModelStartFrame.setUserAHB(getUserAHB());
						
						 //Instance and initialize the logicModelUebergabe
						 LogicModel.logicModelUebergabe = new LogicModelUebergabe();
						 
						 
						 //set the UserAHB value
						 LogicModel.logicModelUebergabe.setUserAHB(getUserAHB());
						 
						 
						 //Instance and initialize the LogicModelTelefonbuch
						 LogicModel.logicModelTelefonbuch = new LogicModelTelefonbuch();
						 
						 
						 //set the UserAHB value
						 LogicModel.logicModelTelefonbuch.setUserAHB(getUserAHB());
						
						 //New Instance of DataBAseGUIController we the arguments we need to pass so we can access from this Object
						new DataBaseGUIController(LogicModel.bigDataAirportHotelBaselStartFrame, 
								LogicModel.dataBaseGUI,  LogicModel.logicModelParking,  LogicModel.logicModelFundSachen, 
								LogicModel.logicModelFitnessAbo, LogicModel.logicModelUebergabe, LogicModel.logicModelTelefonbuch);
						
						
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
	 * @description this Method going to set all the values we need to call later the checkExistsDataBase Method so we can check if DataBase Derby Exists.
	 */
	public void checkDataBase(String dataBaseApplication) {
		
		LogicModel.dataBaseApplication = dataBaseApplication;
		
		
		try {
			
			
			//We get the the path where the database should be stored
			this.urlDataBase = this.dataEncryption.decryptData(getUserAHB().getUrlDataBase()); 
			
			
			//Name of the dataBase for this year. Each year we are going to have a new DataBase, to keep it from getting too big over time.
			this.dbName = "BigDataAHBaselDB";
			
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


					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							


							//We create a new DAOFundsachen Object passing the DaoFundsachenImpl class with the parameters.
							DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), dataBaseGUI, loading, logicModelFundSachen);
							
							try {
								//now call to check if the Table exists.
								daoFundsachen.checkTableFundsachen();
							} catch (DaoException e) {
								e.printStackTrace();
							}
							
						}
					});

					break;
					
					
				case "FITNESSABO": 
					
					//Time to Display FITNESSABODATABASE DAO Object should be called
					
					SwingUtilities.invokeLater(new Runnable() {
					
						@Override
						public void run() {
							
					//Create a new DAOFitness Object
					DAOFitnessAbo daoFitnessAbo = new DAOFitnessImpl(getUserAHB(), dataBaseGUI, loading, logicModelFitnessAbo);
					
					
					
						try {
							daoFitnessAbo.checkTableFitnessAbo();
						} catch (DaoException e) {
							e.printStackTrace();
						}
					
					
						}
					});

					
					break;
					
					
				case "UEBERGABE":
					
					//Time to display UEBERGABE DAO Object should be called. 
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							
							DAOUebergabe daoUebergabe = new DAOUebergabeImpl(getUserAHB(), dataBaseGUI, loading, logicModelUebergabe);
							
							
							try {
								daoUebergabe.checkTableUebergabe();
							} catch (DaoException e) {
								e.printStackTrace();
							}
						}
					});
					
					
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
	 * <p>- Only ADMIN users can delete entries from the database.</p>
	 * 
	 * <p>With this we avoid the bad manipulation of the data saved in the database by unauthorized persons.</p>
	 * @param selectedRow
	 * @param tableName
	 * @param tableModel
	 */
	public void deleteRowDataBase(int selectedRow, String tableName, TableModel tableModel) {
		
		//We set the values of our variables
		LogicModel.selectedRow = selectedRow;
		LogicModel.tableName = tableName;
		this.tableModel = tableModel;
	
		int id = (int)this.tableModel.getValueAt(LogicModel.selectedRow, 0);
		
		
		
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
		 */
		
		if(privilege.equals("ADMIN")) {
			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					//Variable below int get the value depending what the user decide to do, if press Yes or No to delete.
					int selectedOption = JOptionPane.showConfirmDialog(null, "Möchten Sie den Eintrag wirklich löschen?",
							"Eintrag aus Datenbank löschen", JOptionPane.YES_NO_OPTION, JOptionPane.YES_NO_OPTION, preventionImage);
					
					//if the user is sure he wants to delete the entry. He has pressed Button Yes. 
					if (selectedOption == JOptionPane.YES_OPTION) {
						
							//Evaluate tableName
							switch(LogicModel.tableName) {
								
								//Case Lost And Found
								case "FUNDSACHEN":
									
									//We create a new DAOFundsachen Object passing the DaoFundsachenImpl class with the parameters.
									DAOFundsachen daoFundsachen = new DaoFundsachenImpl(getUserAHB(), dataBaseGUI, loading, logicModelFundSachen);
									
									//We call the Method to deleteDatabaseEntry inside the selected TABLE by the Database(tableName, (id) selected to be deleted).
									try {
										//We call to delete the DataBase Entry. It will be deleted the selected row by the User.
										daoFundsachen.deleteDatabaseEntry(LogicModel.tableName, id);
									} catch (DaoException e) {
										e.printStackTrace();
									}
									
								break;
								
									
							}
							
						}
					
				}
			});
			
			
			
			
			
			
			
			
			
		}
		
	}

	
	
	public LogicModelFundSachen getLogicModelFundsachen() {
		return LogicModel.logicModelFundSachen;
	}
	
	
	public LogicModelParking getLogicModelParking() {
		return LogicModel.logicModelParking;
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
	
	
	
	/**
	 * @description Method to search Results in DataBase Table. 
	 * <p>Receives a DataBaseGUI Parameter so we can access the GUI.</p>
	 * <p>It will be set the value in this Method for the searchSelected variable. This value comes from the GUI Selected item by JComboBox.</p>
	 * <p>toSearch inside the Method receives the value of the searchTextBox Element RoundJTextField Element.</p>
	 * <p>This Method is called from different classes to search any value in Table DataBase depending GUI and as what we are searching in Table inside DataBase.</p>
	 * @param dataBaseGUI
	 */
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
	
	
	
	
	
	/**
	 * @description Method to check if the Date Format is correct before we save any Data in DataBase any Table .
	 */
	public boolean checkDateFormatBeforeSaveData(String dateEnteredByUser, String inputTextBoxName, String componentHadFocus) {
		
		this.dateEnteredByUser = dateEnteredByUser;
		LogicModel.inputTextBoxName = inputTextBoxName;
		
		setComponentHadFocus(componentHadFocus);
		
		//If the date do not matches with the date Expression Regular Format it will be display one JOptionPane with the error message. 
		if(!Pattern.matches(formatDateRegex, this.dateEnteredByUser)) {
			
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
		
			/* This Message will be displayed online if componentHadFocus has a value "" empty. 
			 * This Method will be override and in the case is equals "" it will displayed the Error Message from the Child Class or extended class from this super class.
			 */
			if(getComponentHadFocus().equalsIgnoreCase("")) {
				JOptionPane.showMessageDialog(null, panelErrorDateFormat,  "Kritische Fehler(" + LogicModel.inputTextBoxName + ")", JOptionPane.OK_OPTION, errorImg);
				
			}

			
			//We set the dateFormatCorrect to false.
			//And it will be returned a false value
			setDateFormatCorrect(false);
			return false;
		}else {
			
			/*
			 * It matches with the correct Regex Format then we set dateFormatCorrect to true
			 * it will be returned a true value.
			 */
			setDateFormatCorrect(true);
			return true;
		}
		
		
		
	}

	
	
	/**
	 * @description Method to check if the Date Format is correct before we save any Data in DataBase any Table .
	 */
	public boolean checkDateFormatJTableLostFocus(String dateEnteredByUser) {
		
		this.dateEnteredByUser = dateEnteredByUser;
		
	
		
		//If the date do not matches with the date Expression Regular Format it will be display one JOptionPane with the error message. 
		if(!Pattern.matches(formatDateRegex, this.dateEnteredByUser)) {
			
		

			
			//We set the dateFormatCorrect to false.
			//And it will be returned a false value
			setDateFormatCorrect(false);
			return false;
		}else {
			
			/*
			 * It matches with the correct Regex Format then we set dateFormatCorrect to true
			 * it will be returned a true value.
			 */
			setDateFormatCorrect(true);
			return true;
		}
		
		
		
	}
	
	
	
	

	/**
	 * @return the componentHadFocus
	 */
	public String getComponentHadFocus() {
		return componentHadFocus;
	}


	/**
	 * @param componentHadFocus the componentHadFocus to set
	 */
	public void setComponentHadFocus(String componentHadFocus) {
		this.componentHadFocus = componentHadFocus;
	}
	
	
	
	


	/**
	 * @return the dateEnteredByUser
	 */
	public String getDateEnteredByUser() {
		return dateEnteredByUser;
	}


	/**
	 * @param dateEnteredByUser the dateEnteredByUser to set
	 */
	public void setDateEnteredByUser(String dateEnteredByUser) {
		this.dateEnteredByUser = dateEnteredByUser;
	}


	


	/**
	 * @description Method to check if the Date Format entered by the user is correct.
	 * @return the dateFormatCorrect
	 */
	public boolean isDateFormatCorrect() {
		return dateFormatCorrect;
	}


	/**
	 * @description Method to set dateFormatCorrect Date format correct or not entered by the user.
	 * @param dateFormatCorrect the dateFormatCorrect to set
	 */
	public void setDateFormatCorrect(boolean dateFormatCorrect) {
		this.dateFormatCorrect = dateFormatCorrect;
	}


	/** 
	 * @description Method to return a DateTimeFormatter. 
	 * <p>This Method will return the dateTimeFormatterForSavingDataBase: With the Pattern dd/MM/yyyy.</p>
	 * @return the dateTimeFormatter
	 */
	public DateTimeFormatter getDateTimeFormatterForSavingDataBase() {
		return dateTimeFormatterForSavingDataBase;
	}
	
	
	
	/**
	 * @description Method to calculate the sum of the total days having 2 LocalDates to store a long value of the sum of those Dates(LocalDate).
	 * <p>The Method has 2 LocalDate Parameters, firstDate and secondDate.</p>
	 * <p>The long variable totalDaysPlus store a value using the ChronoUnit Enum Calling DAYS and calling between with the 2 dates to be calculated.</p>
	 * @param firstDate
	 * @param secondDate
	 * @return
	 */
	public long calculateDatesPlus(LocalDate firstDate, LocalDate secondDate) {
		
		this.firstDate = firstDate;
		this.secondDate = secondDate;
		
		this.totalDaysPlus = ChronoUnit.DAYS.between(this.firstDate, this.secondDate);
		
		return this.totalDaysPlus;
		
	}


	/**
	 * @description Method to get the simpleDateFormat(format is dd.MM.yyyy)
	 * @return the simpleDateFormat
	 */
	public SimpleDateFormat getSimpleDateFormat() {
		return simpleDateFormat;
	}


	/**
	 * @description Method to get the LocalDate that we want to use to modify in JTable and TABLE by the Database
	 * @return the localDateToBeModified
	 */
	public LocalDate getLocalDateToBeModified() {
		return localDateToBeModified;
	}


	/**
	 * @description Method to set the LocalDate that we want to use to modify in JTable and TABLE by the Database
	 * @param localDateToBeModified the localDateToBeModified to set
	 */
	public void setLocalDateToBeModified(LocalDate localDateToBeModified) {
		this.localDateToBeModified = localDateToBeModified;
	}

	
	

	/**
	 * @return the dateAsStringToBeModified
	 */
	public String [] getDateAsStringToBeModified() {
		return dateAsStringToBeModified;
	}


	/**
	 * @param dateAsStringToBeModified the dateAsStringToBeModified to set
	 */
	public void setDateAsStringToBeModified(String[] dateAsStringToBeModified) {
		
		this.dateAsStringToBeModified = dateAsStringToBeModified;
		
	}
	
	
	
	
	
	
	/**
	 * @description Method to request the user to modify one of the Dates when he is trying to apply some Date changes or Chronology in a JTable Data.
	 * @param model
	 * @param selectedRow
	 * @param selectedColumn
	 * @param columnToBeModified
	 * @param message
	 * @param dialogTitle
	 * @return
	 */
	public String displayRequestDateCorrection(int selectedRow, int selectedColumn, String message, String dialogTitle) {
		
		LogicModel.selectedRow = selectedRow;
		LogicModel.selectedColumn = selectedColumn;
		LogicModel.message = message;
		
		
		//Initialize the JLable
		messageLabelRequestLaterDateCorrection = new JLabel(LogicModel.message);
		
		// Initialize the JTextField Object
		dateTextFieldRequestLaterDateCorrection = new JTextField(10);

		// Initialize the JPanel
		panelRequestBox = new JPanel();

		// Add the objects to the JPanel
		panelRequestBox.add(messageLabelRequestLaterDateCorrection);
		panelRequestBox.add(dateTextFieldRequestLaterDateCorrection);
		
		
		//invokeLater new Thread for displaying the JDialog DateChronologyCorrection dateChronologyCorrection GUI.
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						
						/* Create and initialize the DateChronologyCorrection instance
						 * 
						 * Argument 1= The title for the JDialog GUI
						 * Argument 2 = Message we want to display by the GUI JDialog
						 * Argument 3 = Instance DataBaseGUI so we can access to this and modify elements by the JTable or dispose also to block in Background. 
						 * Argument 4 = boolean value as true to block the dataBaseGUI instance in background until the user take a decision.
						 */
						DateChronologyCorrection dateChronologyCorrection = new DateChronologyCorrection(dialogTitle, message, dataBaseGUI, true);
						
						
						/*
						 * Create and initialize the LogicModelDateChronologyCorrection Instance
						 * 
						 * Argument 1 = dataBaseGUI instance
						 * Argument 2 = String the first Date
						 * Argument 3 = the Second Date
						 * Argument 4 = the selected row
						 * Argument 5 = the selected column
						 */
						LogicModelDateChronologyCorrection logicModelDateChronologyCorrection = 
								new LogicModelDateChronologyCorrection(dataBaseGUI, getDateAsStringToBeModified()[0], 
										getDateAsStringToBeModified()[1], LogicModel.selectedRow, LogicModel.selectedColumn, dateChronologyCorrection);
						
						
						/*
						 * new Instance DateChronologyCorrectionController with the arguments dateChronologyCorrection, logicModelDateChronologyCorrection
						 */
						new DateChronologyCorrectionController(dateChronologyCorrection, logicModelDateChronologyCorrection);
						
						//set visible the GUI(extended JDialog)
						dateChronologyCorrection.setVisible(true);
						
						

					}
				});
		
		
		return this.dateCorrection;
	}

	
	
}
