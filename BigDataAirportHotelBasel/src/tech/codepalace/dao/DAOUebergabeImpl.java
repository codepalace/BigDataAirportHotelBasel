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
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import tech.codepalace.model.LogicModelUebergabe;
import tech.codepalace.model.Uebergabe;
import tech.codepalace.model.UserAHB;
import tech.codepalace.utility.CellTableManager;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.TableUebergabeUtilities;
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
	
	//LocalDateTime with the Todays Date and the Hours Zone +2
	private static LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
	
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
	
	private ImageIcon imgSearchDataBase = new ImageIcon(getClass().getResource("/img/dialogo.png"));
	
	private static String monthToShow;
	
	//Variable to search in database by Date(date for the HandOver).
	private Date datumUebergabe;
	
	
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
			 				//then we call displayListUebergabeByMonth() Method to display the current month results or selected month.
			 				
			 				
			 				displayListUebergabeByMonth("CurrentMonth");
			 			
			 			}else { //otherwise table UEBERGABE do not exists.
			 				
			 				//Time to create Table by DAO Factory Object
			 				//We call daoFactory to createTable UEBERGABE passing the table name(UEBERGABE)
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
 	 * @description Method to return the DataBaseName
 	 * @return
 	 */
 	private static String getDBName() {
 	
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
	public void displayUebergabe(String monthToShow) throws DaoException {
		
		daoFactory.connect();
		//ORDER BY date_field ASC | DESC;
		String sqlString = "SELECT * From UEBERGABE ORDER BY datum DESC";
		
		
		//Evaluate the value of monthToShow. Depending of the Month SQL Select statement demand one interval of dates. 
		switch(DAOUebergabeImpl.monthToShow) {
			
			case "Januar":
				
				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-01-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() + "-01-31') ORDER BY datum DESC";
				
				break;
			
			case "Februar":
		
				
				//We evaluate if the Year is LeapYear and depending of the results we set the value for the sqlString for the Request 
				if(now.toLocalDate().isLeapYear()) {
					sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-02-01'" +
							 " And '" + DAOUebergabeImpl.now.getYear() + "-02-29') ORDER BY datum DESC";
				}else {
					sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-02-01'" +
							 " And '" + DAOUebergabeImpl.now.getYear() + "-02-28') ORDER BY datum DESC";
				}
				
				break;
				
			case "März":
				
				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-03-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() + "-03-31') ORDER BY datum DESC";
				
				
				
				break;
				
			case "April":
				
				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-04-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() + "-01-30') ORDER BY datum DESC";
				
				
				break;
				
			case "Mai":
				
				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-05-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() + "-01-31') ORDER BY datum DESC";
				
				
				break;
				
			case "Juni":
				
				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-06-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() + "-06-30') ORDER BY datum DESC";
				
				
				break;
				
			case "Juli":
				
				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-07-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() + "-07-31') ORDER BY datum DESC";
				
				
				break;
				
			case "August":
				
				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-08-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() + "-08-31') ORDER BY datum DESC";
				
				
				break;
				
			case "September":

				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-09-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() + "-09-30') ORDER BY datum DESC";
				
				
				break;
				
			case "Oktober":
				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-10-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() + "-10-31') ORDER BY datum DESC";
				
				break;
				
			case "November":
				
				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-11-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() + "-11-30') ORDER BY datum DESC";
				
				
				break;
				
			case "Dezember":
				
				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-12-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() + "-12-31') ORDER BY datum DESC";
				
				
				break;
				
			
			case "DateToFind":
				
				
				sqlString = "SELECT * from UEBERGABE WHERE datum = '" + datumUebergabe + "'";
			
				
				break;
			
				//In case CurrentMonth value
			case "CurrentMonth":
	
			    //Initialize one instance YearMonth to get how many days has the current Month
				YearMonth month = YearMonth.of(now.getYear(), now.getMonthValue());
				
			    /*
			     * The SQL statement interval Dates = Between this Year-ThisMonth-FirstDayOfThisMonth and ThisYear-ThisMonth-month.lengthOfMonth.
			     * 
			     * We order by datum DESC.
			     */
				sqlString = "SELECT * From UEBERGABE where (datum Between '" + DAOUebergabeImpl.now.getYear() + "-" +
						DAOUebergabeImpl.now.getMonthValue() +"-01'" +
						 " And '" + DAOUebergabeImpl.now.getYear() +  "-" +  DAOUebergabeImpl.now.getMonthValue() 
						  + "-" + month.lengthOfMonth() +  "') ORDER BY datum DESC";
				
				break;
			    
			default: 
				//If not one of the over cases we set default to display all results from Table UEBERGABE from Database.
				sqlString = "SELECT * From UEBERGABE ORDER BY datum DESC";
				
				break;
			
		}
		
		
		
		
		

		
		try {
			
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(sqlString);
			
			//If we do not have any results
			if(resultSet.next()== false) {

				
				
				//First we evaluate the value of monthToShow 
				switch (DAOUebergabeImpl.monthToShow) {
					
					//In case we was requesting for a Date to display the value but do not exists.
					case "DateToFind":
						
						//We invoke a new Thread with the error message.
						SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Mit dem eingegebenen Datum wurde kein Ergebnis gefunden"
								   , "Kein Ergebnis gefunden", JOptionPane.ERROR_MESSAGE, this.imgSearchDataBase));
						
						break;
						
					case "CurrentMonth":
						
						//We invoke a new Thread with the error message.
						SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "für den aktuellen Monat haben wir noch keine Daten in der Datenbank gespeichert"
								   , "Kein Ergebnis gefunden", JOptionPane.ERROR_MESSAGE, this.imgSearchDataBase));
						
						break;

					//In case we want to display per selected Month but we do not have any Entries we display also one alert
					default: 
						
						//We invoke a new Thread with the error message.
						SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Für den gewählten Monat wurden keine Ergebnisse gefunden"
								   , "Kein Ergebnis gefunden", JOptionPane.ERROR_MESSAGE, this.imgSearchDataBase));
						
						break;
				}
				
				this.dataBaseGUI.setVisible(true);
			
				
				
			}else {
				
				if(loading !=null) {
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {

							DAOUebergabeImpl.loading.setVisible(true);
						}
					});
				}
				
				//resultSet receive the statement select to count all the rows in the UEBERGABE table
				resultSet = statement.executeQuery("Select count(*) from UEBERGABE");
				
				//We move the cursor
				resultSet.next();
				
				//numberOfRowsDatabase receive the counted rows
				numberOfRowsDataBase = resultSet.getInt(1);
				
				//After that resultSet execute now the Query select * from Parking. Value of the sqlString variable
				resultSet = statement.executeQuery(sqlString);
				
				
				if(loading !=null) {
					loading.progressBar.setMaximum(numberOfRowsDataBase);
				}
				
				
				//We create a SwingWorker instruction for the new Thread in background
				SwingWorker<Void, Uebergabe> worker = new SwingWorker<Void, Uebergabe>(){

					@Override
					protected Void doInBackground() throws Exception {
						
						/*
//						 * we create an ArrayList type Uebergabe to add each Uebergabe object  that we find inside the table UEBERGABE in our DataBase
//						 */
						List<Uebergabe> uebergabeList = new ArrayList<Uebergabe>();
						
						int progress = 0;

						//as long as there are entries in the table
						while (resultSet.next()) {
							
							/*We create the necessary variables of the type we need according to the data stored in the database
							 * 
							 * The values are retrieved from each result found in the table and of course from the corresponding column in the table UEBERGABE.
							 */
							

							int id = resultSet.getInt("ID");
							Date datum = resultSet.getDate("datum");
							String kuerzelma = resultSet.getString("kuerzelma");
							String information = resultSet.getString("information");
							
							//Instance Uebergabe model
							Uebergabe uebergabe = new Uebergabe();
							
							uebergabe.setId(id);
							uebergabe.setDatumUebergabe(datum);
							uebergabe.setAbkuerzungMA(kuerzelma);
							uebergabe.setInformation(information);
							
					
							
							uebergabeList.add(uebergabe);
							
							//We send to publish every Object that we are getting by  the interaction inside the while
							publish(uebergabe);
							
						
							
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
					protected void process(List<Uebergabe> chunks) {
					

						//forEach loop to loop through the list Tip ParkingReservation named chunks in the process Method as parameter.
						for(Uebergabe chunk: chunks) {

						
							/*
							 * we create an Object array and pass the data contained in our UEBERGABE table in the Database.
							 * 
							 */

							Object[] row = {chunk.getId(), 
											
											//before we get the datum(date for the information) we use the SimpleDateFormat to format in mm.MM.yyyy
											dateFormat.format(chunk.getDatumUebergabe()), 
											chunk.getAbkuerzungMA(),
											chunk.getInformation()
							
											};
											
									
							
							
							/*
							 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the uebergabeTable 
							 * by the AHBParking class.
							 */
							
							DefaultTableModel model = (DefaultTableModel)dataBaseGUI.uebergabeTable.getModel();
						
							
							/*
							 * for the uebergabeTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableUebergabeUtilities
							 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
							 * 
							 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
							 * 
							 * If the cell is type number then it will have a different font color. 
							 * 
							 */
							
							dataBaseGUI.uebergabeTable.getColumnModel().getColumn(TableUebergabeUtilities.ID).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.uebergabeTable.getColumnModel().getColumn(TableUebergabeUtilities.DATUM).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.uebergabeTable.getColumnModel().getColumn(TableUebergabeUtilities.KUERZELMA).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.uebergabeTable.getColumnModel().getColumn(TableUebergabeUtilities.INFORMATION).setCellRenderer(new CellTableManager("important"));
				
							
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
			
		}catch (SQLException e) {
			

		}
		
		this.dataBaseGUI.setVisible(true);
		
	}


	
	/**
	 * @description Method to display list HandOver informations by Month
	 * @param monthToShow
	 */
	private void displayListUebergabeByMonth(String monthToShow) {
		
		
		try {
		if(DAOUebergabeImpl.monthToShow.equalsIgnoreCase("CurrentMonth")) {
			
			
			
				displayUebergabe("CurrentMonth");
			
			
		}else {
			
			switch (DAOUebergabeImpl.now.getMonthValue()) {
			case 1:
				
				displayUebergabe("Januar");
				
				break;
			case 2:
				displayUebergabe("Februar");
				break;
			case 3: displayUebergabe("März");
				break;
			case 4: displayUebergabe("April");
				break;
			case 5: displayUebergabe("Mai");
				break;
			case 6: displayUebergabe("Juni");
				break;
			case 7: displayUebergabe("Juli");
				break;
			case 8: displayUebergabe("August");
				break;
			case 9: displayUebergabe("September");
				break;
			case 10: displayUebergabe("Oktober");
				break;
			case 11: displayUebergabe("November");
				break;
			case 12: displayUebergabe("Dezember");
				break;
		}
			
		}
		
		} catch (DaoException e) {
			e.printStackTrace();
		
		}
		
	}


	@Override
	public void displaySelectedMonth(String monthToShow) throws DaoException {
		
		
	}




	@Override
	public void searchByDateUebergabe(Date datumUebergabe) throws DaoException {
		
		this.datumUebergabe = datumUebergabe;
		
	}

}
