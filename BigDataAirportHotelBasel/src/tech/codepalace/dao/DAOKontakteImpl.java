package tech.codepalace.dao;

import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import tech.codepalace.model.Kontakte;
import tech.codepalace.model.LogicModelTelefonbuch;
import tech.codepalace.model.UserAHB;
import tech.codepalace.utility.CellTableManager;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.TableKontakteUtilities;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description DAO impl Class for the Contact.
 *
 */
public class DAOKontakteImpl implements DAOKontakte {
	
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

	public DAOKontakteImpl(UserAHB userAHB, DataBaseGUI dataBaseGUI, Loading loading, LogicModelTelefonbuch logicModelTelefonbuch) {
		
		this.userAHB = userAHB;
		this.dataBaseGUI = dataBaseGUI;
		DAOKontakteImpl.loading = loading;
		DAOKontakteImpl.logicModelTelefonbuch = logicModelTelefonbuch;
		DAOKontakteImpl.dataEncryption = new DataEncryption();
		connection = null;
		
		DAOKontakteImpl.tableChecked = false;
	}
	
	
	
	
	
	
	@Override
	public void checkTableKontante() throws DaoException {
		
		SwingWorker<Void, Void>worker = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				
				while(!DAOKontakteImpl.tableChecked) {
					
					DAOKontakteImpl.loading.progressBar.setIndeterminate(true);
					DAOKontakteImpl.loading.progressBar.setForeground(Color.BLUE);
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
						
							DAOKontakteImpl.loading.setVisible(true);
							
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
				DAOKontakteImpl.connection = daoFactory.connect();
						
						//We get the Metadata from our database.
				DAOKontakteImpl.metadata = connection.getMetaData();
						
						
						//resultSet get the value of metadata.getTables(null catalog, null schemaPattern, our table name(FITNESSABO) as uppperCase and the Type is one Array of TABLE
				DAOKontakteImpl.resultSet = metadata.getTables(null, null, table_name.toUpperCase(), new String[] {"TABLE"});
						
						//If resultSet has results if we have DATA. That means Table FITNESSABO exists.
						if(DAOKontakteImpl.resultSet.next()) {
							
							DAOKontakteImpl.tableChecked = true;
							DAOKontakteImpl.loading.setVisible(false);
							DAOKontakteImpl.loading.progressBar.setIndeterminate(false);
							DAOKontakteImpl.loading.progressBar.repaint();
							
							//Table FITNESSABO exists
							//Then we call displayFitnessAbo() Method.
							

							displayKontakte();						
							
						} 
						else { //otherwise table FITNESSABO do not exists
							
							
							//We call daoFactory to createTable FITNESSABO passing the table name(FITNESSABO)
							DAOKontakteImpl.daoFactory.createTable("TELEFONBUCH");
							
							DAOKontakteImpl.tableChecked = true;
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
	public void displayKontakte() throws DaoException {
		
		setURLToConnectCurrentDataBase();
		
		daoFactory.connect();
		
		String sqlString = "SELECT * From TELEFONBUCH ORDER BY name DESC";
		
try {
			
			//We create one statement
			statement = connection.createStatement();
			
			//resultSet execute the instruction from sqlString value
			resultSet = statement.executeQuery(sqlString);
			
			//If resultSet is empty we have to call for create new Kontakte.
			if(resultSet.next()== false) {
				
				//Table is Empty. We only make visible the dataBaseGUI with empty JTable.

				//No contacts stored in Database  we call to visible the DataBaseGUI so the user can enter at any time a new contact.
			    this.dataBaseGUI.setVisible(true);
				
			}else {
				
			  if(loading !=null) {
				  SwingUtilities.invokeLater(new Runnable() {
		 				
		 				@Override
		 				public void run() {
		 					
		 					DAOKontakteImpl.loading.setVisible(true);
		 				}
		 			});
			  }
				
				
				
				//resultSet receive the statement select to count all the rows in the TELEFONBUCH table
				resultSet = statement.executeQuery("Select count(*) from TELEFONBUCH");
				
				//We move the cursor
				resultSet.next();
				
				//numberOfRowsDatabase receive the counted rows
				numberOfRowsDataBase = resultSet.getInt(1);
				

				//After that resultSet execute now the Query select * from TELEFONBUCH. Value of the sqlString variable
				resultSet = statement.executeQuery(sqlString);
				
	
				
				if(loading !=null) {
					loading.progressBar.setMaximum(numberOfRowsDataBase);
				}
				
				
				
				
				
				//We create a SwingWorker instruction for the new Thread in background
				 SwingWorker<Void, Kontakte> worker = new SwingWorker<Void, Kontakte>(){

					

					@Override
					protected Void doInBackground() throws Exception {

						/*
						 * we create an ArrayList type Kontakte to add each Kontakt object  that we find inside the table TELEFONBUCH in our DataBase
						 * 
					 */
						List<Kontakte> kontakte = new ArrayList<Kontakte>();
						
						int progress = 0;
						
						//as long as there are entries in the table
						while (resultSet.next()) {
							
							/*We create the necessary variables of the type we need according to the data stored in the database
							 * 
							 * The values are retrieved from each result found in the table and of course from the corresponding column in the table TELEFONBUCH.
							 * 
							 * 
							 * 
							 */
							int id = resultSet.getInt("ID");
							String name = resultSet.getString("name");
							String phone = resultSet.getString("phone");
							String bemerkungen = resultSet.getString("bemerkungen");
							String abteilung = resultSet.getString("abteilung");
							
							//New Instance Kontakte
							Kontakte kontakt = new Kontakte();
							
							kontakt.setId(id);
							kontakt.setName(name);
							kontakt.setPhone(phone);
							kontakt.setBemerkungen(bemerkungen);
							kontakt.setAbteilung(abteilung);

							//add the Object kontakt to the ArrayList kontakt
							kontakte.add(kontakt);
							
							//publish our kontakt
							publish(kontakt);
							
							
							//We add 1 to the progress variable. 
							progress +=1;
							/*
							 * you can execute a sleep statement to see the progress bar working.
							 */
//							Thread.sleep(500); 
							
							if(loading !=null) {
								loading.progressBar.setValue(progress);
							}
						    

						}
						
					
				
						return null;
					}
					
					
					
					
					@Override
					protected void process(List<Kontakte> chunks) {
	
						
						//forEach loop to loop through the list Tip Kontakte named chunks in the process Method as parameter.
						for(Kontakte chunk: chunks) {

						
							/*
							 * we create an Object array and pass the data contained in our TELEFONBUCH table in the Database.
							 * 
							 */
							Object[] row = {chunk.getId(), 
									
									chunk.getName(),

									chunk.getPhone(), 
									
									chunk.getBemerkungen(),
									
									chunk.getAbteilung()};
							
							/*
							 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the kontaktenTable 
							 * by the DataBaseGUI class.
							 */
							
							DefaultTableModel model = (DefaultTableModel)dataBaseGUI.kontakteTable.getModel();

						
							
							/*
							 * for the fitnessAboTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableKontakteUtilities
							 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
							 * 
							 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
							 * 
							 * If the cell is type number then it will have a different font color. 

							 */

							dataBaseGUI.kontakteTable.getColumnModel().getColumn(TableKontakteUtilities.ID).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.kontakteTable.getColumnModel().getColumn(TableKontakteUtilities.NAME).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.kontakteTable.getColumnModel().getColumn(TableKontakteUtilities.PHONE).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.kontakteTable.getColumnModel().getColumn(TableKontakteUtilities.BEMERKUNGEN).setCellRenderer(new CellTableManager("important"));
							dataBaseGUI.kontakteTable.getColumnModel().getColumn(TableKontakteUtilities.ABTEILUNG).setCellRenderer(new CellTableManager("text"));
							

							model.addRow(row);
							
							
							
						
							
							
							
						}
						
					
					}
					
					
					
					
					@Override
					protected void done() {
						
						//All the Data are loaded then we setVisible the dataBaseGUI Object.

						dataBaseGUI.setVisible(true);
						
						//If loading object exists we close it.
						if(loading!=null) {
							loading.dispose();
						}
						
	
						
						try {
//							daoFactory.backupDataBase();

							statement.close();
							resultSet.close();
							daoFactory.closeConnection(getDerbyURL());
							connection.close();
							

							
						} catch (SQLException e) {
							e.printStackTrace();
						}
						
					}
					
					
					
				 };worker.execute();
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	/**
 	 * @description method to set the URL of the current database. It will be also created a new DaoFactory Instance.
 	 */
 	protected void setURLToConnectCurrentDataBase() {
 		

 		try {
 			
 			
 			//We set the URL value for connecting to the Database
			urlDB = DAOKontakteImpl.dataEncryption.decryptData(userAHB.getUrlDataBase()) + File.separator + getDBName();


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
 		
 		DAOKontakteImpl.derbyUrl = derbyURL;
 	}
 	
 	
 	/**
 	 * @decription Method to get the Derby URL included the Driver instruction for Derby so we can use this to close the connection correctly and shutdown Derby System.
 	 * @return
 	 */
 	private static String getDerbyURL() {
 		return DAOKontakteImpl.derbyUrl;
 	}


}
