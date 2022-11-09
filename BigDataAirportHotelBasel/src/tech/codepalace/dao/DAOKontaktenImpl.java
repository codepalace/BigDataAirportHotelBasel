package tech.codepalace.dao;

import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import tech.codepalace.model.LogicModelTelefonbuch;
import tech.codepalace.model.UserAHB;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description DAO impl Class for the Contact.
 *
 */
public class DAOKontaktenImpl implements DAOKontakten {
	
	private UserAHB userAHB;
	private DataBaseGUI dataBaseGUI;
	private static Loading loading;
	
	/* Variable DaoFactory 
	 * DaoFactory Class include all the Method for the connection to the DataBase. Shutdown DataBase Motor, etc.
	 */
	private static DaoFactory daoFactory = null;
	
	/* Variable DaoFactory 
	 * DaoFactory Class include all the Method for the connection to the DataBase. Shutdown DataBase Motor, etc.
	 */

	// Variable used to have Complete information about the database as a whole.
	protected static DatabaseMetaData metadata = null;

	// Variable to create Statements for working with the DataBase.
	protected static Statement statement = null;

	// Variable for the Table name. in this case TELEFONBUCH.
	protected String table_name = "TELEFONBUCH";
	
	// Variable ResultSet required for working with our DataBase Data.
	private static ResultSet resultSet = null;
	
	// PreparedStatement to add the new contact.
	private PreparedStatement preparedStatement = null;

	// Variable for the Connection
	private static Connection connection;
	
	
	// Instance DataEncryption for decrypt the Data we need to get.
	private static DataEncryption dataEncryption;
	
	private static boolean tableChecked = false;
	
	// Variables for the Database Url, dbName and the derbyUrl included the
	// jdbc:derby: Driver information.
	private static String urlDB, dbName, derbyUrl;
	
	//Variable to get the number of rows or entries we have in our Database Table.
	 	private static int numberOfRowsDataBase = 0;
	
	
	
	
	private static LogicModelTelefonbuch logicModelTelefonbuch;

	public DAOKontaktenImpl(UserAHB userAHB, DataBaseGUI dataBaseGUI, Loading loading, LogicModelTelefonbuch logicModelTelefonbuch) {
		
		this.userAHB = userAHB;
		this.dataBaseGUI = dataBaseGUI;
		DAOKontaktenImpl.loading = loading;
		DAOKontaktenImpl.logicModelTelefonbuch = logicModelTelefonbuch;
		DAOKontaktenImpl.dataEncryption = new DataEncryption();
		connection = null;
		
		DAOKontaktenImpl.tableChecked = false;
	}
	
	
	
	
	
	
	@Override
	public void checkTableKontanten() throws DaoException {
		
		SwingWorker<Void, Void>worker = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				
				while(!DAOKontaktenImpl.tableChecked) {
					
					DAOKontaktenImpl.loading.progressBar.setIndeterminate(true);
					DAOKontaktenImpl.loading.progressBar.setForeground(Color.BLUE);
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
						
							DAOKontaktenImpl.loading.setVisible(true);
							
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
				DAOKontaktenImpl.connection = daoFactory.connect();
						
						//We get the Metadata from our database.
				DAOKontaktenImpl.metadata = connection.getMetaData();
						
						
						//resultSet get the value of metadata.getTables(null catalog, null schemaPattern, our table name(FITNESSABO) as uppperCase and the Type is one Array of TABLE
				DAOKontaktenImpl.resultSet = metadata.getTables(null, null, table_name.toUpperCase(), new String[] {"TABLE"});
						
						//If resultSet has results if we have DATA. That means Table FITNESSABO exists.
						if(DAOKontaktenImpl.resultSet.next()) {
							
							DAOKontaktenImpl.tableChecked = true;
							DAOKontaktenImpl.loading.setVisible(false);
							DAOKontaktenImpl.loading.progressBar.setIndeterminate(false);
							DAOKontaktenImpl.loading.progressBar.repaint();
							
							//Table FITNESSABO exists
							//Then we call displayFitnessAbo() Method.
							

							displayKontakten();						
							
						} 
						else { //otherwise table FITNESSABO do not exists
							
							
							//We call daoFactory to createTable FITNESSABO passing the table name(FITNESSABO)
							DAOKontaktenImpl.daoFactory.createTable("TELEFONBUCH");
							
							DAOKontaktenImpl.tableChecked = true;
							loading.dispose();
							dataBaseGUI.setVisible(true);
							
							
						}
						
					
					} catch (SQLException e) {
						e.printStackTrace();
					
					}
				}
				
				return null;
			}
			
			
		};worker.execute();
		
	}

	
	
	
	
	@Override
	public void displayKontakten() throws DaoException {
		
		
	}
	
	
	/**
 	 * @description method to set the URL of the current database. It will be also created a new DaoFactory Instance.
 	 */
 	protected void setURLToConnectCurrentDataBase() {
 		

 		try {
 			
 			
 			//We set the URL value for connecting to the Database
			urlDB = DAOKontaktenImpl.dataEncryption.decryptData(userAHB.getUrlDataBase()) + File.separator + getDBName();


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
 		
 		DAOKontaktenImpl.derbyUrl = derbyURL;
 	}
 	
 	
 	/**
 	 * @decription Method to get the Derby URL included the Driver instruction for Derby so we can use this to close the connection correctly and shutdown Derby System.
 	 * @return
 	 */
 	private static String getDerbyURL() {
 		return DAOKontaktenImpl.derbyUrl;
 	}


}
