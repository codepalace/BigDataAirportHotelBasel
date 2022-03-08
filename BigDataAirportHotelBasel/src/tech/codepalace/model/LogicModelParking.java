package tech.codepalace.model;

import java.awt.HeadlessException;
import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import javax.swing.SwingUtilities;

import tech.codepalace.dao.DAOParking;
import tech.codepalace.dao.DaoException;
import tech.codepalace.dao.DaoParkingImpl;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.AHBParking;
import tech.codepalace.view.frames.BigDataAirportHotelBaselStartFrame;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version 1.0.0 Jan 30.2022 10:40PM
 * @description Class Logic algorithm for the application of parking databases
 *
 */
public class LogicModelParking {
	
	private BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame;
	
	protected UserAHB userAHB;
	
	protected AHBParking ahbParking;
	
	protected DataEncryption dataEncryption;
	
	protected String urlDataBase;
	
	protected LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
	
	protected String dbName;
	
	protected File urlDataBaseFile;
	
	protected ParkingReservation parkingReservation;
	
	
	public LogicModelParking(BigDataAirportHotelBaselStartFrame bigDataAirportHotelBaselStartFrame, UserAHB userAHB, AHBParking ahbParking, ParkingReservation parkingReservation) {
		this.bigDataAirportHotelBaselStartFrame = bigDataAirportHotelBaselStartFrame;
		this.userAHB = userAHB;
		this.ahbParking = ahbParking;
		this.parkingReservation = parkingReservation;
		
		this.dataEncryption = new DataEncryption();
	}

	
	
	
	/**
	 * @description method for checking the existence of the database
	 */
	public void checkParkingDataBase() {
		
		
		try {
			
			//We get the the path where the database should be stored
			this.urlDataBase = this.dataEncryption.decryptData(this.userAHB.getUrlDataBase()); 
			
			//Name of the dataBase for this year. Each year we are going to have a new DataBase, to keep it from getting too big over time.
			this.dbName = "BigDataAHBaselDB" + now.getYear();
			
			//We get the Path ulrDataBase + the dbName and put the value inside a File variable for after checking the existence of the DataBase
			this.urlDataBaseFile = new File(this.urlDataBase + File.separator + dbName);
			
			
			//We call for checkExistsDataBasel as parameters urlDataBaseFile.
			checkExistsDataBase(this.urlDataBaseFile);
			
			
			
		} catch (HeadlessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	/**
	 * @description Method to check if the DataBase exists.
	 * @param urlDataBaseFile
	 */
	protected void checkExistsDataBase(File urlDataBaseFile) {
		
		if(urlDataBaseFile.exists()) {
			
			System.out.println("The Database: " + urlDataBaseFile.getAbsolutePath() + "exists");
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {

					/*
					 * create a new object DAOParking interface = new Class DaoParkingImpl. 
					 * 
					 * DaoParkingImpl extends ConnectionClass for the dataBase connection and implements the DAOParking interface
					 */
					DAOParking daoParking = new DaoParkingImpl(urlDataBase, dbName, userAHB, ahbParking);
				
					
					try {
						//Now we check if the Parking table exists
						daoParking.displayListParking();
					} catch (DaoException e) {
						
						e.printStackTrace();
					}
					
				}
				
			});
			
		}else {
			System.out.println("The Database: " + urlDataBaseFile.getAbsolutePath() + " do not exists");
			
			
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {

					/*
					 * create a new object DAOParking interface = new Class DaoParkingImpl. 
					 * 
					 * DaoParkingImpl extends ConnectionClass for the dataBase connection and implements the DAOParking interface
					 */
					DAOParking daoParking = new DaoParkingImpl(urlDataBase, dbName, userAHB, ahbParking);
					
					System.out.println("The " + dbName  + " database was successfully created in the path: " + urlDataBase);
					
					
					try {
						//Now we check if the Parking table exists
						daoParking.checkTableParking();
					} catch (DaoException e) {
						
						e.printStackTrace();
					}
					
				}
				
			});
		}
	}

}
