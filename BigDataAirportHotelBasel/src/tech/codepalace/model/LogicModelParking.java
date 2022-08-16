package tech.codepalace.model;

import java.awt.EventQueue;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import tech.codepalace.controller.NewParkingController;
import tech.codepalace.dao.DAOParking;
import tech.codepalace.dao.DaoException;
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

	
	
	private static DataBaseGUI dataBaseGUI;
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
		

		LogicModelParking.dataBaseGUI = dataBaseGUI;
		this.loading = loading;

		//We create a new Instance fo DataEncryption, needed to decrypt some Data we need.
		this.dataEncryption = new DataEncryption();
		
		
	}
	
	public LogicModelParking() {
		this.dataEncryption = new DataEncryption();
	}
	

	
	

	/**
	 * @description method that calls the GUI NewParking to create a new parking reservation entry in the database
	 */
	public void createNewParkingReservation(DataBaseGUI dataBaseGUI) {
		
		LogicModelParking.dataBaseGUI = dataBaseGUI;

	
		try {
			/*
			 * we give value to the variable calling the method getDataRowCounter belonging to Class DaoParkingImpl.
			 * 
			 * it will count how many entries we have in the database. Exactly within the table parking present in the Database. 
			 * 
			 * After counting and get the value we add +1 so we can generate with this value a new ParkingID.
			 */
			

			
			this.daoParking = new DaoParkingImpl(getUserAHB(), LogicModelParking.dataBaseGUI, loading);
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
				
				NewParking newparking = new NewParking(LogicModelParking.dataBaseGUI, true, tableCounter, abkuerzungMA);
				
				
				/*
				 * We create a LogicModelNewParking and passing also the required parameters. Nothing special to explain in this part.
				 */
				LogicModelNewParking logicModelNewParking = new LogicModelNewParking(LogicModelParking.dataBaseGUI, newparking, getUserAHB(), loading);

					
				//New Instance of NewParkingController to control the newParking GUI.
				new NewParkingController(newparking, logicModelNewParking);
				
				//Center the GUI to the Screen
				newparking.setLocationRelativeTo(null);
				
				//And finally we setVisible.
				newparking.setVisible(true);
				
			}
		});
		
		
		
	}

	
	
	
	
	
	
	@Override
	public void searchResultsInDataBase(DataBaseGUI dataBaseGUI) {
		//we call the super Method
		super.searchResultsInDataBase(dataBaseGUI);
		
		LogicModelParking.dataBaseGUI = dataBaseGUI;
		
		//Block switch conditional to evaluate what we are going to search in database
		switch (getSearchSelected()) {
			
			case "Suchen nach ID-Parking":
				
				suchenNachIDParking(getToSearch());
				
				break;
				
			case "Suchen nach Buchungsname":
				
				suchenNachBuchungsName(getToSearch());
				
				break;
				
			case "Suchen nach Auto-KFZ":
				
				suchenByAutoNr(getToSearch());
				break;
				
			case "Suchen nach Anreisedatum":
				
				break;
		}
		
	}
	
	
	
	
	/**
	 * @description Method to call to search in DataBase as ID-Parking(idParking Column in Table Parking).
	 * @param IDParking
	 */
	private void suchenNachIDParking(String IDParking) {
		
		
		DAOParking daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, new Loading(dataBaseGUI, true));
		
		try {
			daoParking.searchByIDParking(IDParking);
		} catch (DaoException e) {
			e.printStackTrace();
			
		}
		
	}
	
	
	
	
	private void suchenNachBuchungsName(String buchungsname) {
		
		DAOParking daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, new Loading(dataBaseGUI, true));
		
		try {
			daoParking.suchenNachBuchungsName(buchungsname);
		} catch (DaoException e) {
			e.printStackTrace();
			
		}
		
	}
	
	
	
	
	
	private void suchenByAutoNr(String autokfz) {
		
		DAOParking daoParking = new DaoParkingImpl(getUserAHB(), dataBaseGUI, new Loading(dataBaseGUI, true));
		
		try {
			daoParking.suchenByAutoNr(autokfz);
		} catch (DaoException e) {
			e.printStackTrace();
			
		}
	}
	
	
	


}
