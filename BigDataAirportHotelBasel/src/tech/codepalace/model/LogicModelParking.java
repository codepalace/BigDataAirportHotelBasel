package tech.codepalace.model;

import java.awt.EventQueue;
import java.awt.HeadlessException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import javax.swing.SwingUtilities;

import tech.codepalace.controller.NewParkingController;
import tech.codepalace.dao.DAOParking;
import tech.codepalace.dao.DaoException;
import tech.codepalace.dao.DaoFactory;
import tech.codepalace.dao.DaoParkingImpl;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewParking;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version 1.0.0 Jan 30.2022 10:40PM
 * @description Class Logic algorithm for the application of parking databases
 *
 */
public class LogicModelParking extends LogicModel {

	
	
	private DataBaseGUI dataBaseGUI;
	private Loading loading;
	
	protected DataEncryption dataEncryption;
	
	//Variable where should be stored the URL or Directory where our database is located
	protected String urlDataBase;
	
	//Variable used to generate the current DataBase name.
	protected LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
	
	//Variable to store the Database Name
	protected String dbName;
	
	
	protected File urlDataBaseFile;
	
	protected ParkingReservation parkingReservation;
	
	private String  abkuerzungMA = "";
	
	private int tableCounter=0;
	
	private DAOParking daoParking = null;
	
	
	public LogicModelParking(DataBaseGUI dataBaseGUI, Loading loading) {
		

		this.dataBaseGUI = dataBaseGUI;
		this.loading = loading;

		//We create a new Instance fo DataEncryption, needed to decrypt some Data we need.
		this.dataEncryption = new DataEncryption();
		
		
	}
	
	public LogicModelParking() {
		this.dataEncryption = new DataEncryption();
	}
	

	
	/**
	 * @description method for checking the existence of the database
	 */
	public void checkParkingDataBase(DataBaseGUI dataBaseGUI) {
		
		this.dataBaseGUI = dataBaseGUI;
		
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
		
		
		
		//We check if the received parameter File(urlDataBaseFile eixsts 
		if(urlDataBaseFile.exists()) {
			
			//If exists we invoke a new Thread 
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					
					if(daoParking==null) {
						//Create an Instance of DAOParking interface = DaoParkingImpl(Class implements the DAOParking interface) 
						//with the parameter userAHB needed, dataBaseGUI, loading 
						daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, loading);
					
					}
		
					
					try {
						//Check if we have a Parking Table.
						daoParking.checkTableParking();
					} catch (DaoException e) {
						e.printStackTrace();
					}
					
				}
				
			});
			
			
		}else { //the DataBase do not exists 
			
			/*
			 * let's proceed with the instructions for creating the database and subsequently create the connection
			 */

			
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					
					//Variable with the Database Driver Instruction jdbc:derby our Database Derby Embedded. It receive the urlDataBase included the dbName.
					String url = "jdbc:derby:" + urlDataBase + File.separator + dbName;

					//We create an instance of DaoFactroy 
					DaoFactory daoFactory = new DaoFactory(url);
				
					//We call daoFactory to create the non-existent database
					daoFactory.createDataBase();
					
					if(daoParking==null) {
						//We create now a new Instance of DAOParking with the needed parameters.
//						 daoParking = new DaoParkingImpl(getUserAHB(), ahbParking);
						daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, loading);
					}
					
					//Database was created now we call to checkTableParking
					try {
						daoParking.checkTableParking();
					} catch (DaoException e) {
						e.printStackTrace();
					}

				}
			});
			

			

		}
	}

	
	

	/**
	 * @description method that calls the GUI NewParking to create a new parking reservation entry in the database
	 */
	public void createNewParkingReservation(DataBaseGUI dataBaseGUI) {
		
		this.dataBaseGUI = dataBaseGUI;

	
		try {
			/*
			 * we give value to the variable calling the method getDataRowCounter belonging to Class DaoParkingImpl.
			 * 
			 * it will count how many entries we have in the database. Exactly within the table parking present in the Database. 
			 * 
			 * After counting and get the value we add +1 so we can generate with this value a new ParkingID.
			 */
			

			
			this.daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, loading);
			tableCounter = daoParking.getDataRowCounter() + 1;
			
		} catch (DaoException e) {
			
			e.printStackTrace();
		}

		
		
		try {
			/* With the help of the dataEncryption object we set the value to the variable abkuerzungMA, calling to decryptData and for that we 
			 * also call getUserAHB().getAbbkuerzungMA() to get the value we need. 
			 * 
			 * AbbkuerzungMA is the Team user who is typing the new entries in the database.
			 *
			 * 
			 */
		    abkuerzungMA = this.dataEncryption.decryptData(getUserAHB().getAbkuerzungMA());
		    
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
		
		//Invoke a new Thread
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				
				/*
				 * We create a new NewParking object. we pass the required parameters. Object AHBParking, false to block the GUI below the NewParking GUI, 
				 * tableCouter(used to set the value IDParking in the NewParking GUI and also to save this value to the Database, abkuerzungMA the user who is typing 
				 * the new Parking.
				 */
				
				NewParking newparking = new NewParking(dataBaseGUI, true, tableCounter, abkuerzungMA);
				
				
				/*
				 * We create a LogicModelNewParking and passing also the required parameters. Nothing special to explain in this part.
				 */
				LogicModelNewParking logicModelNewParking = new LogicModelNewParking(dataBaseGUI, newparking, getUserAHB(), loading);

					
				//New Instance of NewParkingController to control the newParking GUI.
				new NewParkingController(newparking, logicModelNewParking);
				
				//Center the GUI to the Screen
				newparking.setLocationRelativeTo(null);
				
				//And finally we setVisible.
				newparking.setVisible(true);
				
			}
		});
		
		
		
	}


}
