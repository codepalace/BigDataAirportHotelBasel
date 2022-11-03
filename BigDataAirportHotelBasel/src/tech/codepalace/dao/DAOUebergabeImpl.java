package tech.codepalace.dao;

import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import tech.codepalace.model.LogicModelUebergabe;
import tech.codepalace.model.UserAHB;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description DAO Class for the HandOver or shift information for the day or future days. 
 *
 */
public class DAOUebergabeImpl implements DAOUebergabe{
	
	private UserAHB userAHB;
	private DataBaseGUI dataBaseGUI;
	private static Loading loading;
	
	private static LogicModelUebergabe logicModelUebergabe;
	
	/* Variable DaoFactory 
	 * DaoFactory Class include all the Method for the connection to the DataBase. Shutdown DataBase Motor, etc.
	 */
	private static DaoFactory daoFactory = null;
	

	// Variable used to have Complete information about the database as a whole.
	protected static DatabaseMetaData metadata = null;

	// Variable to create Statements for working with the DataBase.
	protected static Statement statement = null;

	// Variable for the Table name. in this case FUNDSACHEN.
	protected String table_name = "UEBERGABE";

	// Variable ResultSet required for working with our DataBase Data.
	private static ResultSet resultSet = null;

	// PreparedStatement to add the new FitnessAbo.
	private PreparedStatement preparedStatement = null;

	// Variable for the Connection
	private static Connection connection;

	// Instance DataEncryption for decrypt the Data we need to get.
	private static DataEncryption dataEncryption;

	private static boolean tableChecked = false;

	// Variables for the Database Url, dbName and the derbyUrl included the
	// jdbc:derby: Driver information.
	private static String urlDB, dbName, derbyUrl;

	// Variable to get the number of rows or entries we have in our Database Table.
	private static int numberOfRowsDataBase = 0;

	// SimpleDateFormat to format the Date output in format dd.MM.yyyy
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	
	//Constructor with the required parameters. 
	public DAOUebergabeImpl(UserAHB userAHB, DataBaseGUI dataBaseGUI, Loading loading, LogicModelUebergabe logicModelUebergabe) {
		
		this.userAHB = userAHB;
		this.dataBaseGUI = dataBaseGUI;
		DAOUebergabeImpl.loading = loading;
		DAOUebergabeImpl.dataEncryption = new DataEncryption();
		DAOUebergabeImpl.logicModelUebergabe = logicModelUebergabe;
		
		
		
	}

	
	

	@Override
	public void checkTableUebergabe() throws DaoException {
		
		SwingWorker<Void, Void>worker = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {

				while(!DAOUebergabeImpl.tableChecked) {
					
					DAOUebergabeImpl.loading.progressBar.setIndeterminate(true);
					DAOUebergabeImpl.loading.progressBar.setForeground(Color.blue);
					
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							
							DAOUebergabeImpl.loading.setVisible(true);
							
						}
					});
					
					
					/* First of all we call the setURLToConnectCurrentDataBase(), where we set the URL of the current DataBase. 
			 		 * 
			 		 * In the setURLToConnectCurrentDataBase() we create the instance of the Object DaoFactory with the url.
			 		 * 
			 		 */
			 		setURLToConnectCurrentDataBase();
					
			 		
			 		try {
			 			
			 			//Now we call for a Connection
			 			DAOUebergabeImpl.connection = daoFactory.connect();
			 			
			 			//We get the Metadata from our database.
			 			DAOUebergabeImpl.metadata = connection.getMetaData();
			 			
			 			//resultSet get the value of metadata.getTables(null catalog, null schemaPattern, our table name(UEBERGABE) as uppperCase and the Type is one Array of TABLE
			 			DAOUebergabeImpl.resultSet = metadata.getTables(null, null, table_name.toUpperCase(), new String[] {"TABLE"});
			 			
			 			//If resultSet has results if we have DATA. That means Table UEBERGABE exists.
			 			if(DAOUebergabeImpl.resultSet.next()) {
			 				
			 				DAOUebergabeImpl.tableChecked = true;
			 				DAOUebergabeImpl.loading.setVisible(false);
			 				DAOUebergabeImpl.loading.progressBar.setIndeterminate(false);
			 				DAOUebergabeImpl.loading.progressBar.repaint();
			 				
			 				//Table UEBERGABE exists
			 				//then we call displayUebergabe() Method.
			 				
			 				displayUebergabe();
			 			
			 			}else { //otherwise table UEBERGABE do not exists.
			 				
			 				//Time to create Table by DAO Factory Object
			 				//We call daoFactory to createTable Parking passing the table name(UEBERGABE)
			 				DAOUebergabeImpl.daoFactory.createTable("UEBERGABE");
			 				
			 				//table was checked
			 				DAOUebergabeImpl.tableChecked = true;
			 				
			 				loading.dispose();
			 				dataBaseGUI.setVisible(true);
			 			
			 			
			 			
			 			}
			 		
			 		
			 			
			 		}catch (SQLException e) {
						e.printStackTrace();
						
					}
					
				}
				
				return null;
			}
			
			
			
		};worker.execute();
		
	}
	
	
	
	
	/**
 	 * @description method to set the URL of the current database. It will be also created a new DaoFactory Instance.
 	 */
 	protected void setURLToConnectCurrentDataBase() {
 		

 		try {
 			
 			
 			//We set the URL value for connecting to the Database
			urlDB = DAOUebergabeImpl.dataEncryption.decryptData(userAHB.getUrlDataBase()) + File.separator + getDBName();


		} catch (Exception e1) {
			e1.printStackTrace();
		}
 		
 
 		//We set the DerbyURL value
 		setDerbyURL("jdbc:derby:" + urlDB);
 		
 		//We instantiate the variable daoFactory with the parameter url to connect to the Database.
		daoFactory = new DaoFactory(getDerbyURL());
		
		
 	}
 	
 	
	/**
 	 * @description Method to return the DataBaseName from this Year. 
 	 * @return
 	 */
 	private static String getDBName() {
 		
 		//We have a DataBase only for the Fitness ABO
 		dbName = "BigDataAHBaselDB";
 		
 		return dbName;
 	}
 	
 	
 	/**
 	 * @description Method to set Derby URL included the jdbc:derby: Driver instruction
 	 * @param derbyURL
 	 */
 	private static void setDerbyURL(String derbyURL) {
 		
 		DAOUebergabeImpl.derbyUrl = derbyURL;
 	}
 	
 	
 	/**
 	 * @decription Method to get the Derby URL included the Driver instruction for Derby so we can use this to close the connection correctly and shutdown Derby System.
 	 * @return
 	 */
 	private static String getDerbyURL() {
 		return DAOUebergabeImpl.derbyUrl;
 	}

	
	
	

	@Override
	public void displayUebergabe() throws DaoException {
		
		
		
		
		
	}

}
