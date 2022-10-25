package tech.codepalace.dao;

import java.awt.Color;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import tech.codepalace.model.FitnessAbo;
import tech.codepalace.model.LogicModelFitnessAbo;
import tech.codepalace.model.UserAHB;
import tech.codepalace.utility.CellTableManager;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.TableFitnessAboUtilities;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description DAO Pattern Class
 *
 */
public class DAOFitnessImpl implements DAOFitnessAbo {
	
	private UserAHB userAHB;
	private DataBaseGUI dataBaseGUI;
	private static Loading loading;
	
	private static LogicModelFitnessAbo logicModelFitnessAbo;
	
	//LocalDateTime with the Todays Date and the Hours Zone +2
	private static LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
	
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

	// Variable for the Table name. in this case FUNDSACHEN.
	protected String table_name = "FITNESSABO";
	
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
	
 	//Variable to get the number of rows or entries we have in our Database Table.
 	private static int numberOfRowsDataBase = 0;
 	
 	//SimpleDateFormat to format the Date output in format dd.MM.yyyy
 	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	

	public DAOFitnessImpl(UserAHB userAHB, DataBaseGUI dataBaseGUI, Loading loading, LogicModelFitnessAbo logicModelFitnessAbo) {
		
		this.userAHB = userAHB;
		this.dataBaseGUI = dataBaseGUI;
		DAOFitnessImpl.loading = loading;
		DAOFitnessImpl.dataEncryption = new DataEncryption();
		DAOFitnessImpl.logicModelFitnessAbo = logicModelFitnessAbo;
		
		
	}

	
	@Override
	public void checkTableFitnessAbo() throws DaoException {
		
		SwingWorker<Void, Void>worker = new SwingWorker<Void, Void>(){

			@Override
			protected Void doInBackground() throws Exception {
				
				while(!DAOFitnessImpl.tableChecked) {
					
					DAOFitnessImpl.loading.progressBar.setIndeterminate(true);
					DAOFitnessImpl.loading.progressBar.setForeground(Color.BLUE);
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
						
							DAOFitnessImpl.loading.setVisible(true);
							
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
						DAOFitnessImpl.connection = daoFactory.connect();
						
						//We get the Metadata from our database.
						DAOFitnessImpl.metadata = connection.getMetaData();
						
						
						//resultSet get the value of metadata.getTables(null catalog, null schemaPattern, our table name(FITNESSABO) as uppperCase and the Type is one Array of TABLE
						DAOFitnessImpl.resultSet = metadata.getTables(null, null, table_name.toUpperCase(), new String[] {"TABLE"});
						
						//If resultSet has results if we have DATA. That means Table FITNESSABO exists.
						if(DAOFitnessImpl.resultSet.next()) {
							
							DAOFitnessImpl.tableChecked = true;
							DAOFitnessImpl.loading.setVisible(false);
							DAOFitnessImpl.loading.progressBar.setIndeterminate(false);
							DAOFitnessImpl.loading.progressBar.repaint();
							
							//Table FITNESSABO exists
							//Then we call displayFitnessAbo() Method.
							

							displayFitnessAbo();						
							
						} 
						else { //otherwise table FITNESSABO do not exists
							
							
							//We call daoFactory to createTable FITNESSABO passing the table name(FITNESSABO)
							DAOFitnessImpl.daoFactory.createTable("FITNESSABO");
							
							DAOFitnessImpl.tableChecked = true;
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
			urlDB = DAOFitnessImpl.dataEncryption.decryptData(userAHB.getUrlDataBase()) + File.separator + getDBName();
			
//			JOptionPane.showMessageDialog(null, urlDB);

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
 		
 		DAOFitnessImpl.derbyUrl = derbyURL;
 	}
 	
 	
 	/**
 	 * @decription Method to get the Derby URL included the Driver instruction for Derby so we can use this to close the connection correctly and shutdown Derby System.
 	 * @return
 	 */
 	private static String getDerbyURL() {
 		return DAOFitnessImpl.derbyUrl;
 	}


 	
 	
 	
	@Override
	public void displayFitnessAbo() throws DaoException {
		
		setURLToConnectCurrentDataBase();
		
		daoFactory.connect();
		
		String sqlString = "SELECT * From FITNESSABO ORDER BY eintrittsDatum DESC";
		
try {
			
			//We create one statement
			statement = connection.createStatement();
			
			//resultSet execute the instruction from sqlString value
			resultSet = statement.executeQuery(sqlString);
			
			//If resultSet is empty we have to call for create new FitnessAbo.
			if(resultSet.next()== false) {
				
				//Table is Empty. We only make visible the dataBaseGUI with empty JTable.

				//No Fitness subscription we call to visible the DataBaseGUI so the user can enter at any time a new Fitness Subscription.
			    this.dataBaseGUI.setVisible(true);
				
			}else {
				
			  if(loading !=null) {
				  SwingUtilities.invokeLater(new Runnable() {
		 				
		 				@Override
		 				public void run() {
		 					
		 					DAOFitnessImpl.loading.setVisible(true);
		 				}
		 			});
			  }
				
				
				
				//resultSet receive the statement select to count all the rows in the FitnessAbo table
				resultSet = statement.executeQuery("Select count(*) from FITNESSABO");
				
				//We move the cursor
				resultSet.next();
				
				//numberOfRowsDatabase receive the counted rows
				numberOfRowsDataBase = resultSet.getInt(1);
				

				//After that resultSet execute now the Query select * from FITNESSABO. Value of the sqlString variable
				resultSet = statement.executeQuery(sqlString);
				
	
				
				if(loading !=null) {
					loading.progressBar.setMaximum(numberOfRowsDataBase);
				}
				
				
				
				
				
				//We create a SwingWorker instruction for the new Thread in background
				 SwingWorker<Void, FitnessAbo> worker = new SwingWorker<Void, FitnessAbo>(){

					

					@Override
					protected Void doInBackground() throws Exception {

						/*
//						 * we create an ArrayList type FitnessAbo to add each FitnessAbo object  that we find inside the table FITNESSABO in our DataBase
//						 */
						List<FitnessAbo> fitnessAbo = new ArrayList<FitnessAbo>();
						
						int progress = 0;
						
						//as long as there are entries in the table
						while (resultSet.next()) {
							
							/*We create the necessary variables of the type we need according to the data stored in the database
							 * 
							 * The values are retrieved from each result found in the table and of course from the corresponding column in the table FITNESSABO.
							 */
							int id = resultSet.getInt("ID");
							String fitnessID = resultSet.getString("fitnessID");
							String name = resultSet.getString("name");
							Date eintrittsDatum = resultSet.getDate("eintrittsDatum");
							Date austrittsDatum = resultSet.getDate("austrittsDatum");
							double betrag = resultSet.getDouble("betrag");
							String firma = resultSet.getString("firma");
							String bemerkungen = resultSet.getString("bemerkungen");
							String kontoStatus = resultSet.getString("kontoStatus");
							String abkuerzungMA = resultSet.getString("abkuerzungMA");
							
							//New instance FitnessAbo
							FitnessAbo fitnessAbo2 = new FitnessAbo();
							
							fitnessAbo2.setId(id);
							fitnessAbo2.setFitnessID(fitnessID);
							fitnessAbo2.setName(name);
							fitnessAbo2.setEintrittsDatum(eintrittsDatum);
							fitnessAbo2.setAustrittsDatum(austrittsDatum);
							fitnessAbo2.setBetrag(betrag);
							fitnessAbo2.setFirma(firma);
							fitnessAbo2.setBemerkungen(bemerkungen);
							fitnessAbo2.setKontoStatus(kontoStatus);
							fitnessAbo2.setAbkuerzungMA(abkuerzungMA);
							

							
							
							//add the Object FitnessAbo(fitnessAbo2) to the ArrayList fitnessAbo
							fitnessAbo.add(fitnessAbo2);

							publish(fitnessAbo2);
							
							
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
					protected void process(List<FitnessAbo> chunks) {
	
						
						//forEach loop to loop through the list Tip FitnessAbo named chunks in the process Method as parameter.
						for(FitnessAbo chunk: chunks) {

						
							/*
							 * we create an Object array and pass the data contained in our FITNESSABO table in the Database.
							 * 
							 * by getBetragParking we add the Euro symbol.
							 */
							Object[] row = {chunk.getId(), chunk.getFitnessID(), chunk.getName(),
									
									/* before we get the Arrival Date we use the Simple Date Format to Format in mm.MM.yyyy  */
									dateFormat.format(chunk.getEintrittsDatum()), 
									
									/* before we get the Arrival Date we use the Simple Date Format to Format in mm.MM.yyyy  */
									dateFormat.format(chunk.getAustrittsDatum()), 
									
									chunk.getBetrag() +  " €",
									
									chunk.getFirma(), 
									
									chunk.getBemerkungen(),
									
									chunk.getKontoStatus(),
									
									chunk.getAbkuerzungMA()};
							
							/*
							 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the fitnessAboTable 
							 * by the DataBaseGUI class.
							 */
							
							DefaultTableModel model = (DefaultTableModel)dataBaseGUI.fitnessAboTable.getModel();
						
							
							/*
							 * for the fitnessAboTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableFitnessAboUtilities
							 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
							 * 
							 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
							 * 
							 * If the cell is type number then it will have a different font color. 

							 */
							dataBaseGUI.fitnessAboTable.getColumnModel().getColumn(TableFitnessAboUtilities.ID).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.fitnessAboTable.getColumnModel().getColumn(TableFitnessAboUtilities.FITNESSID).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fitnessAboTable.getColumnModel().getColumn(TableFitnessAboUtilities.NAME).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fitnessAboTable.getColumnModel().getColumn(TableFitnessAboUtilities.EINTRITTSDATUM).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.fitnessAboTable.getColumnModel().getColumn(TableFitnessAboUtilities.AUSTRITTSDATUM).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.fitnessAboTable.getColumnModel().getColumn(TableFitnessAboUtilities.BETRAG).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.fitnessAboTable.getColumnModel().getColumn(TableFitnessAboUtilities.FIRMA).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fitnessAboTable.getColumnModel().getColumn(TableFitnessAboUtilities.BEMERKUNGEN).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fitnessAboTable.getColumnModel().getColumn(TableFitnessAboUtilities.KONTOSTATUS).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fitnessAboTable.getColumnModel().getColumn(TableFitnessAboUtilities.KUERZELMA).setCellRenderer(new CellTableManager("text"));

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
 	
 	
 	





	
	

}
