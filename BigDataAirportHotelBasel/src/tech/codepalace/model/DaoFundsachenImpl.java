package tech.codepalace.model;

import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import tech.codepalace.dao.DAOFundsachen;
import tech.codepalace.dao.DaoException;
import tech.codepalace.dao.DaoFactory;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;


/**
 * @description DAO Pattern class
 * @author tonimacaroni
 *
 */
public class DaoFundsachenImpl implements DAOFundsachen {
	
	/* Variable DaoFactory 
	 * DaoFactory Class include all the Method for the connection to the DataBase. Shutdown DataBase Motor, etc.
	 */
	private static DaoFactory daoFactory = null;
	
	
	// Variable used to have Complete information about the database as a whole.
	protected static DatabaseMetaData metadata = null;

	// Variable to create Statements for working with the DataBase.
	protected static Statement statement = null;

	// Variable for the Table name. in this case Parking.
	protected String table_name = "FUNDSACHEN";

	// Variable ResultSet required for working with our DataBase Data.
	private static ResultSet resultSet = null;
	
	
	
	private UserAHB userAHB;
	private DataBaseGUI dataBaseGUI;
	private static Loading loading;
	
	
	// Instance DataEncryption for decrypt the Data we need to get.
	private static DataEncryption dataEncryption;

	// Variables for the Database Url, dbName and the derbyUrl included the
	// jdbc:derby: Driver information.
	private static String urlDB, dbName, derbyUrl;
	
	// Variable to get the number of rows or entries we have in our Database Table.
	private static int numberOfRowsDataBase = 0;

	// PreparedStatement to add the new Parking-reservation.
	private PreparedStatement preparedStatement = null;

	// Variable for the Connection
	private static Connection connection;
	
	//LocalDateTime with the Todays Date and the Hours Zone +2
	private static LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
	
	private static boolean tableChecked = false;
	
	
	public DaoFundsachenImpl(UserAHB userAHB, DataBaseGUI dataBaseGUI, Loading loading) {
		
		this.userAHB = userAHB;
		this.dataBaseGUI = dataBaseGUI;
		DaoFundsachenImpl.loading = loading;
		DaoFundsachenImpl.dataEncryption = new DataEncryption();
		
		DaoFundsachenImpl.tableChecked = false;
		
	}
	
	
	

	@Override
	public void checkTableFundsachen() throws DaoException {
		
		SwingWorker<Void, Void>worker = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				
				while(!DaoFundsachenImpl.tableChecked) {
					
					DaoFundsachenImpl.loading.progressBar.setIndeterminate(true);
					DaoFundsachenImpl.loading.progressBar.setForeground(Color.BLUE);
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
						
							DaoFundsachenImpl.loading.setVisible(true);
							
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
						DaoFundsachenImpl.connection = daoFactory.connect();
						
						//We get the Metadata from our database.
						DaoFundsachenImpl.metadata = connection.getMetaData();
						
						
						//resultSet get the value of metadata.getTables(null catalog, null schemaPattern, our table name(PARKING) as uppperCase and the Type is one Array of TABLE
						DaoFundsachenImpl.resultSet = metadata.getTables(null, null, table_name.toUpperCase(), new String[] {"TABLE"});
						
						//If resultSet has results if we have DATA. That means Table PARKING exists.
						if(DaoFundsachenImpl.resultSet.next()) {
							
							DaoFundsachenImpl.tableChecked = true;
							DaoFundsachenImpl.loading.setVisible(false);
							DaoFundsachenImpl.loading.progressBar.setIndeterminate(false);
							DaoFundsachenImpl.loading.progressBar.repaint();
							
						
							System.out.println("FUNDSACHEN table do not exists");
							//Table FUNDSACHEN exists
							//Then we call displayListFundsachen() Method.
							displayListFundsachen();
							
							
						} 
						else { //otherwise table FUNDSACHEN do not exists
							
							System.out.println("FUNDSACHEN table exists");
							
							
							//We call daoFactory to createTable Parking passing the table name(PARKING)
							DaoFundsachenImpl.daoFactory.createTable("FUNDSACHEN");
							
							DaoFundsachenImpl.tableChecked = true;
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
	
	
	
	
	
	
	
	
	/**
 	 * @description method to set the URL of the current database. It will be also created a new DaoFactory Instance.
 	 */
 	protected void setURLToConnectCurrentDataBase() {
 		

 		try {
 			
 			
 			//We set the URL value for connecting to the Database
			urlDB = DaoFundsachenImpl.dataEncryption.decryptData(userAHB.getUrlDataBase()) + File.separator + getDBName();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
 		
 		//Variable to pass the URL of DataBase to the DaoFactory Object or Instance.
// 		String url = "jdbc:derby:" + urlDB;
 		
 		//We set the DerbyURL value
 		setDerbyURL("jdbc:derby:" + urlDB);
 		
 		//We instantiate the variable daoFactory with the parameter url to connect to the Database.
		daoFactory = new DaoFactory(getDerbyURL());
		
		
 	}
	
	
	

	@Override
	public int getDataRowCounter() throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addNewFundsachen(Fundgegenstand fundgegenstand, UserAHB userAHB) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	
	
	
	
	
	@Override
	public void displayListFundsachen() throws DaoException {
		
		System.out.println("The elements of the table FUNDSACHEN will be displayed");
		
		this.dataBaseGUI.setVisible(true);
		
		
		
	}
	
	
	
	
	
	
	
	/**
 	 * @description Method to return the DataBaseName from this Year. 
 	 * @return
 	 */
 	private static String getDBName() {
 		
 		dbName = "BigDataAHBaselDB" + now.getYear();
 		
 		return dbName;
 	}
 	
 	
 	/**
 	 * @description Method to set Derby URL included the jdbc:derby: Driver instruction
 	 * @param derbyURL
 	 */
 	private static void setDerbyURL(String derbyURL) {
 		
 		DaoFundsachenImpl.derbyUrl = derbyURL;
 	}
 	
 	
 	
 	/**
 	 * @decription Method to get the Derby URL included the Driver instruction for Derby so we can use this to close the connection correctly and shutdown Derby System.
 	 * @return
 	 */
 	private static String getDerbyURL() {
 		return DaoFundsachenImpl.derbyUrl;
 	}

}
