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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import tech.codepalace.controller.NewParkingController;
import tech.codepalace.model.LogicModelNewParking;
import tech.codepalace.model.ParkingReservation;
import tech.codepalace.model.UserAHB;
import tech.codepalace.utility.CellTableManager;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.TableParkingUtilities;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;
import tech.codepalace.view.frames.NewParking;



/**
 * @description DaoParkingImpl DAOParking Pattern
 * @author Antonio Estevez Gonzalez
 * @version v0.1.0 February 12.2022
 *
 */
public class DaoParkingImpl  implements DAOParking {
	
	/* Variable DaoFactory 
	 * DaoFactory Class include all the Method for the connection to the DataBase. Shutdown DataBase Motor, etc.
	 */
	private static DaoFactory daoFactory = null;

 	
 	//Variable used to have Complete information about the database as a whole.
 	protected static DatabaseMetaData metadata = null;
 	
 	//Variable to create Statements for working with the DataBase.
 	protected static Statement statement = null;
 	
 	//Variable for the Table name. in this case Parking.
 	protected String table_name = "Parking";
 	
 	//selected id required for working with our DataBase Data.
 	private int id;
 	
 	//Variable ResultSet required for working with our DataBase Data.
	private static ResultSet resultSet = null;

 	
	//Instance for the UserAHB values
 	private UserAHB userAHB;
 	
 	private DataBaseGUI dataBaseGUI;
 	
 	//Instance NewParking GUI
 	private NewParking newParking;
 	
 	//Variable ParkingReservation type for setting the values to the new Parking-reservation.
 	protected  ParkingReservation parkingReservation;
 	
 	//Instance DataEncryption for decrypt the Data we need to get.
 	private static DataEncryption dataEncryption;
 	
 	//Variables for the Database Url, dbName and the derbyUrl included the jdbc:derby: Driver information.
 	private static String urlDB, dbName, derbyUrl;
 	
 	//Variable to get the number of rows or entries we have in our Database Table.
 	private static int numberOfRowsDataBase = 0;
 	
 	//PreparedStatement to add the new Parking-reservation.
 	private	PreparedStatement preparedStatement = null;

 	//Variable for the Connection
 	private static Connection connection;


 	//LocalDateTime with the Todays Date and the Hours Zone +2
 	private static LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
 	
 	protected String backupDirectory = "";
 	
 	//LocalDate needed for example to delete old backups Database file.
 	private LocalDate nowLocalDate = LocalDate.now();
 	
 	//Variable File for the todays backup
 	private File backupTodayDirectory = null;
 	
 	//Variable File for the day before backup directory.
 	private File backupDirectoryDayBefore = null;
 	
 	
 	
 	//Posiblemente vamos a borrar esto
 	//Variables for JDialog Message DataBase Not Found
 	JDialog dialogDBError;
 	JPanel dialogJPanel;
 	JLabel message; 
 	JButton okJButton;
 	Object [] optionsDBMessage;
 	ImageIcon imgError;
 	
 	private ImageIcon dialogImg = new ImageIcon(getClass().getResource("/img/dialogo.png"));
 	
 	
 
 	
 	
 	private static Loading loading;
 	
	private static boolean tableChecked = false;
	


 
 	
 	public DaoParkingImpl(UserAHB userAHB, DataBaseGUI dataBaseGUI, Loading loading) {
 		this.userAHB = userAHB;
 		this.dataBaseGUI = dataBaseGUI;
 		DaoParkingImpl.loading = loading;
 		
 		//Object DataEncryption required to get some important encrypted values.
 		DaoParkingImpl.dataEncryption = new DataEncryption();
 		
 		connection=null;
 		
 		DaoParkingImpl.tableChecked = false;
 		

 
 		
		
 	}
 	
 	
 	public DaoParkingImpl() {}
 	
 	
 	
 	
 	/**
 	 * @description Method for checking the existence of the parking table within the database.
 	 * <p>If the Parking-Table exists we call displayParking Method.</p>
 	 * <p>If Parking-Table do not exists we call daoFactory Object to create a new Table named Parking.</p> 
 	 * <p>After the Parking-Table is created the connection will be closed in the DaoFactory Object and also the Derby System shutdown.</p>
 	 */
 	@Override
	public void checkTableParking() throws DaoException {
 		
 		
 		SwingWorker<Void, Void>worker = new SwingWorker<Void, Void>(){
 			
 		

			@Override
			protected Void doInBackground() throws Exception {
				
				while(!DaoParkingImpl.tableChecked) {
					
					DaoParkingImpl.loading.progressBar.setIndeterminate(true);
			 		DaoParkingImpl.loading.progressBar.setForeground(Color.BLUE);
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
							DaoParkingImpl.loading.setVisible(true);
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
						DaoParkingImpl.connection = daoFactory.connect();
						
						//We get the Metadata from our database.
						DaoParkingImpl.metadata = connection.getMetaData();
						
						
						//resultSet get the value of metadata.getTables(null catalog, null schemaPattern, our table name(PARKING) as uppperCase and the Type is one Array of TABLE
						DaoParkingImpl.resultSet = metadata.getTables(null, null, table_name.toUpperCase(), new String[] {"TABLE"});
						
						//If resultSet has results if we have DATA. That means Table PARKING exists.
						if(DaoParkingImpl.resultSet.next()) {
							DaoParkingImpl.tableChecked = true;
							
							DaoParkingImpl.loading.setVisible(false);
							DaoParkingImpl.loading.progressBar.setIndeterminate(false);
							DaoParkingImpl.loading.progressBar.repaint();
							
							
							//Table PARKING exists
							//Then we call displayListParking() Method.
							displayListParking();
							
							
						} 
						else { //otherwise table Parking do not exists
							
							
							
							
							//We call daoFactory to createTable Parking passing the table name(PARKING)
							DaoParkingImpl.daoFactory.createTable("PARKING");
							
							//New Status when was created
							dataBaseGUI.processStatusJLabel.setText("Database is ready");
							
							DaoParkingImpl.tableChecked = true;
							loading.dispose();
							dataBaseGUI.setVisible(true);
							
							
						}
						
					
					} catch (SQLException e) {
						e.printStackTrace();
					
					}
					
				}
				
				return null;
			}
 			
 			
 		}; worker.execute();
 		
 		
 		
 		
 		
 		
 		

		
	}

 	
 	
 	
 	
 	
 	/**
 	 * @description method to set the URL of the current database. It will be also created a new DaoFactory Instance.
 	 */
 	protected void setURLToConnectCurrentDataBase() {
 		try {
 			//We set the URL value for connecting to the Database
			urlDB = DaoParkingImpl.dataEncryption.decryptData(userAHB.getUrlDataBase()) + File.separator + getDBName();
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
 	
 	
 	
 	
 	/**
 	 * @description Method to set Derby URL included the jdbc:derby: Driver instruction
 	 * @param derbyURL
 	 */
 	private static void setDerbyURL(String derbyURL) {
 		
 		DaoParkingImpl.derbyUrl = derbyURL;
 	}
 	
 	
 	
 	/**
 	 * @decription Method to get the Derby URL included the Driver instruction for Derby so we can use this to close the connection correctly and shutdown Derby System.
 	 * @return
 	 */
 	private static String getDerbyURL() {
 		return DaoParkingImpl.derbyUrl;
 	}
 	
 	
 	
 	
 	
 	/**
 	 * @description Method to get how many rows we have in our table 
 	 */

	@Override
	public int getDataRowCounter() throws DaoException {
		
		//We set the URL for connecting the Current DataBase.
		setURLToConnectCurrentDataBase();
		
		//Set value connection calling daoFactory.connect() Method. Calling this Method will return an Object type Connection.
		DaoParkingImpl.connection = daoFactory.connect();
		

		//If we have a Connection.
		if(connection != null) {
	
				
		
		
		try {
			//We create a new SQL statement
			DaoParkingImpl.statement = connection.createStatement();
			
			//resultSet value = what statement execute as SQL Instruction. We select counting how many entries we have in the Parking Table.
			DaoParkingImpl.resultSet = statement.executeQuery("Select count(*) from parking");
			
			//Move the cursor
			DaoParkingImpl.resultSet.next();
			
			//Finally numberOFRowsDataBase = resultSet what we have count. How many entries we have in our Parking Table.
			numberOfRowsDataBase = resultSet.getInt(1);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {

				DaoParkingImpl.statement.close();
				DaoParkingImpl.resultSet.close();
				daoFactory.closeConnection(DaoParkingImpl.getDerbyURL());
				connection.close(); //Any way we call close() this connection variable to be more sure because embedded DataBase.
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		
			
		}
		
		/*
		 * We return the numberOfRowsDatabase used to generate the new Parking-ID.
		 */
		return numberOfRowsDataBase;
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
	 * Method to create new Parking Reservation.
	 * 
	 * <p>Even if the name is createNewParkingReservation is called like this because it will be call for creating a new Parking Reservation. </p>
	 * 
	 * <p>It will be called from the AHBParkingController class.</p>
	 * 
	 * <p>This Method will prepare all the instances that we need to display the GUI NewParking which is responsible for display the form where we enter new Parking information.</p>
	 */
	@Override
	public void createNewParkingReservation(int currentRecord) throws DaoException {
		

		
		//We invoke a new Thread.
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				
				try {
					//we instantiate the ahbParking object.
					newParking = new  NewParking(dataBaseGUI, true, currentRecord, DaoParkingImpl.dataEncryption.decryptData(userAHB.getAbkuerzungMA()));
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				//New Instance of LogicModelNewParking
				LogicModelNewParking logicModelNewParking = new LogicModelNewParking(dataBaseGUI, newParking, userAHB, loading);
				
				//New Instance of NewParkingController. The Controller of newParking.
				new NewParkingController(newParking, logicModelNewParking);
				
				//Center to the Screen.
				newParking.setLocationRelativeTo(null);
				
				//And Visible.
				newParking.setVisible(true);
				
				
			}
			
		});
		
	}
	
	
	
	
	
	
	
	/**
	 * Method to add a new Parking Reservation. 
	 * 
	 * <p>This Method will be called from checkAllEntries Method inside the LogicModelNewParking class.</p>
	 * 
	 * <p>If the user has enter all the informations correctly, then this Method will be called to add the new Reservation in the Database-Table(Parking).</p>
	 */
	@Override
	public void addNewParkingReservation(ParkingReservation parkingReservation, UserAHB userAHB) throws DaoException {
		
		//We set the values for the userAHB and ParkingReservation
		this.userAHB = userAHB;
		this.parkingReservation = parkingReservation;
		

		//we initialize the instance
		DaoParkingImpl.daoFactory = new DaoFactory(getDerbyURL());
		
		//We set the value of connection calling daoFactory to connect(). connect Method return a Connection Object.
		DaoParkingImpl.connection = DaoParkingImpl.daoFactory.connect();
		
		
		//If we have a connection.
		if(connection!=null) {
			
			
			//Variable with the SQL instruction value to be execute for inserting a new row to the Table Parking in the database. 
			String sql = "INSERT  into parking(idparking, buchungsname, autokfz, anreisedatum, abreisedatum, anzahltagen, betragparking, "
					+ "buchungskanal, bemerkungen, schluesselinhaus, verkaufer) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			
			
						
			try {

				//We set the value for the preparedStatement object.
				this.preparedStatement = connection.prepareStatement(sql);
				
				//We set all values for each columns for the new Row that will be saved in the Table Parking.
				this.preparedStatement.setString(1, this.parkingReservation.getIdParking());
				this.preparedStatement.setString(2, this.parkingReservation.getBuchungsname());
				this.preparedStatement.setString(3, this.parkingReservation.getAutoKFZ());
				this.preparedStatement.setDate(4, this.parkingReservation.getAnreiseDatum());
				this.preparedStatement.setDate(5, this.parkingReservation.getAbreiseDatum());
				this.preparedStatement.setInt(6, this.parkingReservation.getAnzahlTagen());
				this.preparedStatement.setDouble(7, this.parkingReservation.getBetragParking());
				this.preparedStatement.setString(8, this.parkingReservation.getBuchungsKanal());
				this.preparedStatement.setString(9, this.parkingReservation.getBemerkungen());
				this.preparedStatement.setString(10, this.parkingReservation.getSchluesselInHaus());
				this.preparedStatement.setString(11, this.parkingReservation.getAbkuerzungMA());
				
				//execute one update to save the new information in the database and ofcourse in the table Parking.
				this.preparedStatement.executeUpdate();
				
				//We save all changed permanent.
				DaoParkingImpl.connection.commit();
				
				
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				try {
					
					/* Close preparedStatement, connection and call daoFactory to shutdown the Derby Embedded DataBase Motor so other user can save or display information 
					 * Without any Exception. Derby Embedded do not accept multi user.
					 * 
					 *  The embedded Derby cannot be started (opened) by multiple JVMs, although multiple accesses are allowed from the JVM that started the database.
					 */
					
					this.preparedStatement.close();
					DaoParkingImpl.connection.close();
					DaoParkingImpl.daoFactory.closeConnection(getDerbyURL());
					
				
					/*
					 * After we have added a new entry in the database, inside the parking table, we need all the content to be displayed including the new entry.
					 * even if we could just add to the JTable the new content and the next time the database is called to display we display all from the database. 
					 * 
					 * We don't want only to add to the JTable visually and I'll explain why.
					 * 
					 * 1- We are working with two computers accessing the same local database.
					 * 
					 * 2- The data base is Derby Embedded that means each computer has a copy of this Application with the same Configurations details to access the same 
					 * database directory locally and it may be that two users at the same time are entering information in the parking table. so the data entered would not be updated on both computers.
					 * 
					 * To prevent both computers from having different data. We delete the displayed data from the JTable.
					 * 
					 * To do this, we execute the following instructions below
					 */
					
					//We create a new DefaultTableModel Casting DefaultTableModel our instance ahbParking.parkingTable.getModel to get the Table model.
					DefaultTableModel model = (DefaultTableModel) dataBaseGUI.parkingTable.getModel();
					
					//We set the RowCount to 0 for deleting all the content from the JTable.
					model.setRowCount(0);

	
					try {
						//We set the urlDB value again for connecting to display included the new Entry.
						urlDB = DaoParkingImpl.dataEncryption.decryptData(userAHB.getUrlDataBase()) + File.separator + getDBName();

				
					} catch (Exception e) {
						e.printStackTrace();
					}

					if(daoFactory==null) {
						//We instantiate the class DaoFactory with the URL parameter.
						daoFactory = new DaoFactory(getDerbyURL());
					}
					
				
					
					//Now we call for a Connection
					DaoParkingImpl.connection = daoFactory.connect();
					
					//We display all entries included the new one.
					displayListParking();
				   
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
					
		}
		
	
			
	}
	
	
	
	
	
	
	/**
	 * @description Method to display a list of all Parking Reservations
	 */
	@Override
	public void displayListParking() throws DaoException {
		
		
		setURLToConnectCurrentDataBase();
		//aqui hay que volver a conectar con la base de datos.
		
		daoFactory.connect(); //agregado ultimo
		
		//Local variable to select all items in the Parking Table order by idparking ASC | DESC.
		String sqlString = "SELECT * from PARKING ORDER BY idparking ASC";;
		
		try {
			
			//We create one statement
			statement = connection.createStatement();
			
			//resultSet execute the instruction from sqlString value
			resultSet = statement.executeQuery(sqlString);
			
			//If resultSet is empty we have to call for create new Parking Reservation
			if(resultSet.next()== false) {
				
				//Table is Empty. We only make visible the dataBaseGUI with empty JTable.

				//No Parking-reservation we call to visible the DataBaseGUI so the user can enter at any time a new Reservation.
			    this.dataBaseGUI.setVisible(true);
				
			}else {
				
			  if(loading !=null) {
				  SwingUtilities.invokeLater(new Runnable() {
		 				
		 				@Override
		 				public void run() {
		 					// TODO Auto-generated method stub
		 					DaoParkingImpl.loading.setVisible(true);
		 				}
		 			});
			  }
				
				
				
				//resultSet receive the statement select to count all the rows in the Parking table
				resultSet = statement.executeQuery("Select count(*) from parking");
				
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
				 SwingWorker<Void, ParkingReservation> worker = new SwingWorker<Void, ParkingReservation>(){

					

					@Override
					protected Void doInBackground() throws Exception {

						/*
//						 * we create an ArrayList type ParkingReservation to add each ParkingReservation object  that we find inside the table Parking in our DataBase
//						 */
						List<ParkingReservation> parkingReservation = new ArrayList<ParkingReservation>();
						
						int progress = 0;
						
						//as long as there are entries in the table
						while (resultSet.next()) {
							
							/*We create the necessary variables of the type we need according to the data stored in the database
							 * 
							 * The values are retrieved from each result found in the table and of course from the corresponding column in the table Parking.
							 */
							int id = resultSet.getInt("ID");
							String idParking = resultSet.getString("idparking");
							String buchungsName = resultSet.getString("buchungsname");
							String autokfz = resultSet.getString("autokfz");
							Date anreisedatum = resultSet.getDate("anreisedatum");
							Date abreisedatum = resultSet.getDate("abreisedatum");
							int anzahltagen = resultSet.getInt("anzahltagen");
							double betragparking = resultSet.getDouble("betragparking");
							String buchungskanal = resultSet.getString("buchungskanal");
							String bemerkungen = resultSet.getString("bemerkungen");
							String schluesselinhaus = resultSet.getString("schluesselinhaus");
							String verkaufer = resultSet.getString("verkaufer");
							
							
							
							//We create an object type ParkingReservation
							ParkingReservation parkingReservation2 = new ParkingReservation();
							
							//We set the values of this Object type ParkingReservation 
							parkingReservation2.setId(id);
							parkingReservation2.setIdParking(idParking);
							parkingReservation2.setBuchungsname(buchungsName);
							parkingReservation2.setAutoKFZ(autokfz);
							parkingReservation2.setAnreiseDatum(anreisedatum);
							parkingReservation2.setAbreiseDatum(abreisedatum);
							parkingReservation2.setAnzahlTagen(anzahltagen);
							parkingReservation2.setBetragParking(betragparking);
							parkingReservation2.setBuchungsKanal(buchungskanal);
							parkingReservation2.setBemerkungen(bemerkungen);
							parkingReservation2.setSchluesselInHaus(schluesselinhaus);
							parkingReservation2.setAbkuerzungMA(verkaufer);
							
							
							//add the Object ParkingReservation(parkingReservation2) to the ArrayList parkingReservation
							parkingReservation.add(parkingReservation2);

							publish(parkingReservation2);
							
							
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
					protected void process(List<ParkingReservation> chunks) {
	
						
						//forEach loop to loop through the list Tip ParkingReservation named chunks in the process Method as parameter.
						for(ParkingReservation chunk: chunks) {

						
							/*
							 * we create an Object array and pass the data contained in our parking table in the Database.
							 * 
							 * by getBetragParking we add the Euro symbol.
							 */
							Object[] row = {chunk.getId(), chunk.getIdParking(), chunk.getBuchungsname(), chunk.getAutoKFZ(),
									chunk.getAnreiseDatum(), chunk.getAbreiseDatum(), chunk.getAnzahlTagen(), 
									chunk.getBetragParking() + " €", chunk.getBuchungsKanal(), chunk.getBemerkungen(), 
									chunk.getSchluesselInHaus(), chunk.getAbkuerzungMA()};
							
							
							/*
							 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the parkingTable 
							 * by the AHBParking class.
							 */
							
							DefaultTableModel model = (DefaultTableModel)dataBaseGUI.parkingTable.getModel();
						
							
							/*
							 * for the parkingTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableParkingUtilities
							 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
							 * 
							 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
							 * 
							 * If the cell is type number then it will have a different font color. 
							 */
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.ID).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.IDPARKING).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSNAME).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.AUTOKFZ).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.ANREISEDATUM).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BETRAG).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSKANAL).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BEMERKUNGENG).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.SCHLUESSEL).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.KUERSELMA).setCellRenderer(new CellTableManager("text"));
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
	 * @author Antonio Estevez Gonzalez
	 * @param directory
	 * @description Method to delete directory and all its contents.
	 * <p>We are going to use this method in case we manually deleted by accident or intentionally the contents of the entire database and we need 
	 * to recreate the database path, the database and the database tables.</p>
	 * 
	 * <p>The Derby record may be incomplete because someone deleted some sequential file and we need the program not to crash 
	 * and instead re-create the database structure.</p>
	 * 
	 * <p>So what we are going to do is that within the displayListParking method where it should warn us an error, one Exception and that the program ends up crashing, 
	 * what we will do is call this method to delete all traces of database path, and then call again the method that checks the existence of it and that 
	 * creates the whole structure to begin to be able to enter data without problems.</p>
	 * 
	 * <p><strong>The directory parameter specifies the path we want to delete</strong></p>
	 * deletes contents of the directory
	 */
	protected void deleteDirectoryContents(File directory) {
		
//		
//		//Array File to list all the files that the directory can contain inside
//		File [] files = directory.listFiles();
//		
//		//If the folder is not empty
//		if(files != null) {
//			
//			//loop all files
//			for(File index: files) {
//				
//				//if it is a directory
//				if(index.isDirectory()) {
//					
//					//We delete the files that are inside subfolders by calling the deleteDirectory method again.
//					deleteDirectoryContents(index);
//					
//				}
//				
//				if(index.isFile()) {
//					
//					//If it is a file we delete it
//					index.delete();
//				}
//			}
//			
//			//delete the directory
//			directory.delete();
//			
//			System.out.println("All directories and files have been successfully deleted!!!");
//			
//			try {
//				restoreDatabaseFromBackup(this.conn);
//			} catch (DaoException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	



	@Override
	public void updateParkingReservation(ParkingReservation parkingReservation) throws DaoException {
		// TODO Auto-generated method stub
		
	}


	
	@Override
	public void reloadParkingData() throws DaoException {
		
		//We create a new DefaultTableModel Casting DefaultTableModel our instance dataBaseGUI.parkingTable.getModel to get the Table model.
		DefaultTableModel model = (DefaultTableModel) dataBaseGUI.parkingTable.getModel();
		
		//We set the RowCount to 0 for deleting all the content from the JTable.
		model.setRowCount(0);
		
		try {
			//Set new value to the urlDB variable.
			DaoParkingImpl.urlDB = DaoParkingImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBase()) + File.separator + getDBName();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		
		// We set the URL to connect to the Database.
		setURLToConnectCurrentDataBase();
		
		
		/*SQL Sentence to select all from the FUNDSACHEN table in our Database.
		 */
		String sql = "SELECT * from PARKING ORDER BY idparking ASC";
		
		
		try {
			
			//Initialize daoFactory Object with the DerbyURL value as argument.
			daoFactory = new DaoFactory(getDerbyURL());
			
			//Initialize the connection object calling the daoFactory Object an connect Method to Connect to the URL where the database is located.
			connection = DaoParkingImpl.daoFactory.connect();
			
			//statement createStatement
			DaoParkingImpl.statement = DaoParkingImpl.connection.createStatement();
			
			//resultSet receive value statement.executeQuerey and the SQL sentence
			resultSet = statement.executeQuery(sql);
			

					  SwingUtilities.invokeLater(new Runnable() {
			 				
			 				@Override
			 				public void run() {
			 					DaoParkingImpl.loading.setVisible(true);
			 			
			 				}
			 			});
				
				
				
				//After that resultSet execute now the Query select * from PARKING
				resultSet = statement.executeQuery("Select count(*) from parking");
				
				
				//We move the cursor
				resultSet.next();
				
				//numberOfRowsDatabase receive the counted rows
				numberOfRowsDataBase = resultSet.getInt(1);
				
				resultSet = statement.executeQuery(sql);
				
				 if(loading !=null) {
						loading.progressBar.setMaximum(numberOfRowsDataBase);
				}
				
				 
				 
				//We create a SwingWorker instruction for the new Thread in background
				 SwingWorker<Void, ParkingReservation> worker = new SwingWorker<Void, ParkingReservation>(){

					

					@Override
					protected Void doInBackground() throws Exception {

						/*
//						 * we create an ArrayList type ParkingReservation to add each ParkingReservation object  that we find inside the table Parking in our DataBase
//						 */
						List<ParkingReservation> parkingReservation = new ArrayList<ParkingReservation>();
						
						int progress = 0;
						
						//as long as there are entries in the table
						while (resultSet.next()) {
							
							/*We create the necessary variables of the type we need according to the data stored in the database
							 * 
							 * The values are retrieved from each result found in the table and of course from the corresponding column in the table Parking.
							 */
							int id = resultSet.getInt("ID");
							String idParking = resultSet.getString("idparking");
							String buchungsName = resultSet.getString("buchungsname");
							String autokfz = resultSet.getString("autokfz");
							Date anreisedatum = resultSet.getDate("anreisedatum");
							Date abreisedatum = resultSet.getDate("abreisedatum");
							int anzahltagen = resultSet.getInt("anzahltagen");
							double betragparking = resultSet.getDouble("betragparking");
							String buchungskanal = resultSet.getString("buchungskanal");
							String bemerkungen = resultSet.getString("bemerkungen");
							String schluesselinhaus = resultSet.getString("schluesselinhaus");
							String verkaufer = resultSet.getString("verkaufer");
							
							
							
							//We create an object type ParkingReservation
							ParkingReservation parkingReservation2 = new ParkingReservation();
							
							//We set the values of this Object type ParkingReservation 
							parkingReservation2.setId(id);
							parkingReservation2.setIdParking(idParking);
							parkingReservation2.setBuchungsname(buchungsName);
							parkingReservation2.setAutoKFZ(autokfz);
							parkingReservation2.setAnreiseDatum(anreisedatum);
							parkingReservation2.setAbreiseDatum(abreisedatum);
							parkingReservation2.setAnzahlTagen(anzahltagen);
							parkingReservation2.setBetragParking(betragparking);
							parkingReservation2.setBuchungsKanal(buchungskanal);
							parkingReservation2.setBemerkungen(bemerkungen);
							parkingReservation2.setSchluesselInHaus(schluesselinhaus);
							parkingReservation2.setAbkuerzungMA(verkaufer);
							
							
							//add the Object ParkingReservation(parkingReservation2) to the ArrayList parkingReservation
							parkingReservation.add(parkingReservation2);

							publish(parkingReservation2);
							
							
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
					protected void process(List<ParkingReservation> chunks) {
	
						
						//forEach loop to loop through the list Tip ParkingReservation named chunks in the process Method as parameter.
						for(ParkingReservation chunk: chunks) {

						
							/*
							 * we create an Object array and pass the data contained in our parking table in the Database.
							 * 
							 * by getBetragParking we add the Euro symbol.
							 */
							Object[] row = {chunk.getId(), chunk.getIdParking(), chunk.getBuchungsname(), chunk.getAutoKFZ(),
									chunk.getAnreiseDatum(), chunk.getAbreiseDatum(), chunk.getAnzahlTagen(), 
									chunk.getBetragParking() + " €", chunk.getBuchungsKanal(), chunk.getBemerkungen(), 
									chunk.getSchluesselInHaus(), chunk.getAbkuerzungMA()};
							
							
							/*
							 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the parkingTable 
							 * by the AHBParking class.
							 */
							
							DefaultTableModel model = (DefaultTableModel)dataBaseGUI.parkingTable.getModel();
						
							
							/*
							 * for the parkingTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableParkingUtilities
							 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
							 * 
							 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
							 * 
							 * If the cell is type number then it will have a different font color. 
							 */
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.ID).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.IDPARKING).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSNAME).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.AUTOKFZ).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.ANREISEDATUM).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BETRAG).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSKANAL).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BEMERKUNGENG).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.SCHLUESSEL).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.KUERSELMA).setCellRenderer(new CellTableManager("text"));
							model.addRow(row);
							
							
							
						
							
							
							
						}
						
					
					}
					
					
					
					
					@Override
					protected void done() {
						

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
				
			
			
	
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	
	
	

	@Override
	public List<ParkingReservation> orderby(String orderBy) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}


	
	

	@Override
	public void createBackUpDataBase(JLabel statusJLabel) throws DaoException {
		
//		
//		try {
//		
//			this.conect();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//	    
//	    if(this.conn!=null) {
//	    	
////	    	JOptionPane.showMessageDialog(null, "existe conexion: " + this.conn);
//	    	try {
//	    		
//				this.backupDirectory = this.dataEncryption.decryptData(this.userAHB.getUrlDataBaseBackup()) + File.separator + this.nowLocalDate;
//				
//				/*
//				 * Aqui en este metodo vamos a hacer que borre todos los posibles backups anteriores menos el del dia de hoy.
//				 */
//				
//				File allBackupsDirectory = new File(this.dataEncryption.decryptData(this.userAHB.getUrlDataBaseBackup()));
//				
//
//				
////				 JOptionPane.showMessageDialog(null, "allBackupsDirectory: " + allBackupsDirectory);
//			    
//					/*
//					 * The SYSCS_UTIL.SYSCS_BACKUP_DATABASE procedure will issue an error if there are any unlogged operations in the same transaction as the backup procedure. 
//					 * 
//					 * If any unlogged operations are in progress in other transactions in the system when the backup starts, 
//					 * this procedure will block until those transactions are complete before performing the backup. 
//					 * 
//					 * Derby automatically converts unlogged operations to logged mode if they are started while the backup is in progress 
//					 * (except operations that maintain application jar files in the database). 
//					 * 
//					 * Procedures to install, replace, and remove jar files in a database are blocked while the backup is in progress.
//					 * 
//					 * I don't want backup to block until unlogged operations in other transactions are complete, 
//					 * I use the SYSCS_UTIL.SYSCS_BACKUP_DATABASE_NOWAIT procedure. 
//					 * 
//					 * 
//					 * 
//					 * public static void backUpDatabase(Connection conn)throws SQLException
//					 * 
//					 */
//					
//			     SwingUtilities.invokeLater(new Runnable() {
//					
//					@Override
//					public void run() {
//						CallableStatement cs;
//						try {
//							cs = conn.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
//							cs.setString(1, backupDirectory);
//							cs.execute();
//							cs.close();
//							System.out.println("backed up database to "+ backupDirectory);
//							ahbParking.processStatusJLabel.setText("Status: Backup Database created");
//							ahbParking.processStatusJLabel.setText("Status: Database Loaded");
//						} catch (SQLException e) {
//							e.printStackTrace();
//						}
//						
//						
//					}
//				});
//			
//			     deleteOldBackups(allBackupsDirectory);
//					 
//					 
//					 
//
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
////	    	displayListParking();
//	    }
//	    
	    
		
	}
	
	
	
	
	
	
	
	
	
	protected void deleteOldBackups(File directory) {
		
		
		File myBackupDirectory = null;
			
			try {
				 myBackupDirectory = new File(DaoParkingImpl.dataEncryption.decryptData(userAHB.getUrlDataBaseBackup()));
				this.backupTodayDirectory = new File(DaoParkingImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBaseBackup()) + File.separator + this.nowLocalDate);
				this.backupDirectoryDayBefore =  new File(DaoParkingImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBaseBackup()) + File.separator + this.nowLocalDate.minusDays(1));
			
			} catch (Exception e) {

				e.printStackTrace();	
			}
			
		
				
				File [] files = directory.listFiles();
				
				//directory ist not empty
				if(files!=null) {
					
				
					for(File file: files) {
						
						if(file.isDirectory()) {
							
							if(file.getName().equals(backupDirectoryDayBefore.getName()) || file.getName().equals(backupTodayDirectory.getName())) {
//								
								System.out.println("Nada que borrar");
								
							}else {
								deleteOldBackups(file);
							}

							
						}
						
						if(file.isFile()) {
							file.delete();
						}
					}
					
					if(directory.getName().equals(backupTodayDirectory.getName()) || directory.getName().equals(backupDirectoryDayBefore.getName()) || directory.getName().equals(myBackupDirectory.getName())) {
						
						System.out.println("Nada que borrar");
						
					}else {
						directory.delete();
					}
					
				}
						
	
	}
	

	

	@Override
	public void restoreDatabaseFromBackup(Connection conn) throws DaoException {
		

//		File backupDirectory = null;
//		try {
//			backupDirectory = new File(this.dataEncryption.decryptData(this.userAHB.getUrlDataBaseBackup()) + File.separator + this.nowLocalDate);
////			JOptionPane.showMessageDialog(null, "Directorio backup para restaurar: " + backupDirectory.getAbsolutePath());
////			this.restoreDataBase(backupDirectory);
//			
//			
//		} catch (Exception e) {
//			
//			e.printStackTrace();
//		}
//		
//		try {
//			this.conectToBackUpDataBase(backupDirectory);
//		} catch (SQLException e) {
//			 if(e.getSQLState().equals("XJ040")) {
//				
//			
//			 
//			SwingUtilities.invokeLater(new Runnable() {
//				
//				@Override
//				public void run() {
//					 JOptionPane.showMessageDialog(null, "Backup Directory Not Found");
//					 
//					 /*
//					  * PUdieramos crear una GUI aqui en este caso, que le permita al usuario elegir una carpeta donde se encuentre la base de datos que pueda elegir 
//					  * en caso de que la copia automatizada ya no se encuentre. 
//					  * 
//					  * Tambien File pudiera rebuscar en otras copias de seguridad antiguas y tratar de crear la base de datos desde una fecha anterior a la de hoy. 
//					  * 
//					  * Pero ya que estaremos borrando constantemente las bases de datos con las fechas anteriores no lo encuentro importante de momento, todo es cambiable en un futuro.
//					  * 
//					  * Tambien pudieramos hacer que a la hora de borrar los directorios de backups no solo se borren los de la fecha anterior si no todos los que existan menos el de la 
//					  * fecha de hoy o anterior, con un condicional if-else.
//					  */
//					
//				}
//			});
//			
//			 }
////			e.printStackTrace();
//		}
//		
//		
		
		
	}


	
	
	
	
	@Override
	public void searchByIDParking(String idParking) throws DaoException {
		
		//We create a new DefaultTableModel Casting DefaultTableModel our instance dataBaseGUI.parkingTable.getModel to get the Table model.
		DefaultTableModel model = (DefaultTableModel) dataBaseGUI.parkingTable.getModel();
		
		//We set the RowCount to 0 for deleting all the content from the JTable.
		model.setRowCount(0);
		
		
		try {
			
			//Set new value to the urlDB variable
			DaoParkingImpl.urlDB = DaoParkingImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBase()) + File.separator + getDBName();

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// We set the URL to connect to the Database.
		setURLToConnectCurrentDataBase();

		
		String sql = "SELECT * from PARKING WHERE idParking = '" + idParking + "'";
		
		try {
			
			//Initialize daoFactory Object with the DerbyURL value as argument.
			daoFactory = new DaoFactory(getDerbyURL());
			
			//Initialize the connection object calling the daoFactory Object an connect Method to Connect to the URL where the database is located.
			connection = DaoParkingImpl.daoFactory.connect();
			
			//statement createStatement
			DaoParkingImpl.statement = DaoParkingImpl.connection.createStatement();
			
			//resultSet receive value statement.executeQuerey and the SQL sentence
			resultSet = statement.executeQuery(sql);
			
			
			//Now first we check if resultSet has any results if == false is empty
			if(resultSet.next()==false) {

//				DaoParkingImpl.logicModelParking.displayErrorDataSearch("Mit dem eingegebenen ID-Parking wurde kein Ergebnis gefunden");
				
				JOptionPane.showMessageDialog(null, "Mit dem eingegebenen ID-Parking wurde kein Ergebnis gefunden",  
						"Kein Ergebnis gefunden", JOptionPane.OK_OPTION, this.dialogImg);
				
				//We call to reload o refresh the JTable
				reloadParkingData();
			} //We have results so we show it.
			
			else 
			{
				if(loading !=null) {
					 
					 
					  SwingUtilities.invokeLater(new Runnable() {
			 				
			 				@Override
			 				public void run() {
			 					DaoParkingImpl.loading.setVisible(true);
			 			
			 				}
			 			});
				  }
				
				
				//After that resultSet execute now the Query select * from FUNDSACHEN WHERE dateItemsWasFound. Value of the sql variable
				resultSet = statement.executeQuery("SELECT count(*) from PARKING WHERE idParking = '" + idParking + "'");
				
				
				//We move the cursor
				resultSet.next();
				
				//numberOfRowsDatabase receive the counted rows
				numberOfRowsDataBase = resultSet.getInt(1);
				
				resultSet = statement.executeQuery(sql);
				
				 if(loading !=null) {
						loading.progressBar.setMaximum(numberOfRowsDataBase);
				}
				 
				 
				//We create a SwingWorker instruction for the new Thread in background
				 SwingWorker<Void, ParkingReservation> worker = new SwingWorker<Void, ParkingReservation>(){

					

					@Override
					protected Void doInBackground() throws Exception {

						/*
						 * we create an ArrayList type ParkingReservation to add each ParkingReservation object  that we find inside the table Parking in our DataBase
						 */
						List<ParkingReservation> parkingReservation = new ArrayList<ParkingReservation>();
						
						int progress = 0;
						
						//as long as there are entries in the table
						while (resultSet.next()) {
							
							/*We create the necessary variables of the type we need according to the data stored in the database
							 * 
							 * The values are retrieved from each result found in the table and of course from the corresponding column in the table Parking.
							 */
							int id = resultSet.getInt("ID");
							String idParking = resultSet.getString("idparking");
							String buchungsName = resultSet.getString("buchungsname");
							String autokfz = resultSet.getString("autokfz");
							Date anreisedatum = resultSet.getDate("anreisedatum");
							Date abreisedatum = resultSet.getDate("abreisedatum");
							int anzahltagen = resultSet.getInt("anzahltagen");
							double betragparking = resultSet.getDouble("betragparking");
							String buchungskanal = resultSet.getString("buchungskanal");
							String bemerkungen = resultSet.getString("bemerkungen");
							String schluesselinhaus = resultSet.getString("schluesselinhaus");
							String verkaufer = resultSet.getString("verkaufer");
							
							
							
							//We create an object type ParkingReservation
							ParkingReservation parkingReservation2 = new ParkingReservation();
							
							//We set the values of this Object type ParkingReservation 
							parkingReservation2.setId(id);
							parkingReservation2.setIdParking(idParking);
							parkingReservation2.setBuchungsname(buchungsName);
							parkingReservation2.setAutoKFZ(autokfz);
							parkingReservation2.setAnreiseDatum(anreisedatum);
							parkingReservation2.setAbreiseDatum(abreisedatum);
							parkingReservation2.setAnzahlTagen(anzahltagen);
							parkingReservation2.setBetragParking(betragparking);
							parkingReservation2.setBuchungsKanal(buchungskanal);
							parkingReservation2.setBemerkungen(bemerkungen);
							parkingReservation2.setSchluesselInHaus(schluesselinhaus);
							parkingReservation2.setAbkuerzungMA(verkaufer);
							
							
							//add the Object ParkingReservation(parkingReservation2) to the ArrayList parkingReservation
							parkingReservation.add(parkingReservation2);

							publish(parkingReservation2);
							
							
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
					protected void process(List<ParkingReservation> chunks) {
	
						
						//forEach loop to loop through the list Tip ParkingReservation named chunks in the process Method as parameter.
						for(ParkingReservation chunk: chunks) {

						
							/*
							 * we create an Object array and pass the data contained in our parking table in the Database.
							 * 
							 * by getBetragParking we add the Euro symbol.
							 */
							Object[] row = {chunk.getId(), chunk.getIdParking(), chunk.getBuchungsname(), chunk.getAutoKFZ(),
									chunk.getAnreiseDatum(), chunk.getAbreiseDatum(), chunk.getAnzahlTagen(), 
									chunk.getBetragParking() + " €", chunk.getBuchungsKanal(), chunk.getBemerkungen(), 
									chunk.getSchluesselInHaus(), chunk.getAbkuerzungMA()};
							
							
							/*
							 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the parkingTable 
							 * by the AHBParking class.
							 */
							
							DefaultTableModel model = (DefaultTableModel)dataBaseGUI.parkingTable.getModel();
						
							
							/*
							 * for the parkingTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableParkingUtilities
							 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
							 * 
							 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
							 * 
							 * If the cell is type number then it will have a different font color. 
							 */
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.ID).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.IDPARKING).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSNAME).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.AUTOKFZ).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.ANREISEDATUM).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BETRAG).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSKANAL).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BEMERKUNGENG).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.SCHLUESSEL).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.KUERSELMA).setCellRenderer(new CellTableManager("text"));
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
	
	
	}


	
	
	
	
	
	@Override
	public void suchenNachBuchungsName(String buchungsname) throws DaoException {
		
		//We create a new DefaultTableModel Casting DefaultTableModel our instance dataBaseGUI.parkingTable.getModel to get the Table model.
				DefaultTableModel model = (DefaultTableModel) dataBaseGUI.parkingTable.getModel();
				
				//We set the RowCount to 0 for deleting all the content from the JTable.
				model.setRowCount(0);
				
				
				try {
					
					//Set new value to the urlDB variable
					DaoParkingImpl.urlDB = DaoParkingImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBase()) + File.separator + getDBName();

				}catch (Exception e) {
					e.printStackTrace();
				}
				
				// We set the URL to connect to the Database.
				setURLToConnectCurrentDataBase();

				//We could use LIKE here by the SQL Sentence buchungsname but we want at the Moment to have exact name.
				String sql = "SELECT * from PARKING WHERE buchungsname = '" + buchungsname + "'";
				
				try {
					
					//Initialize daoFactory Object with the DerbyURL value as argument.
					daoFactory = new DaoFactory(getDerbyURL());
					
					//Initialize the connection object calling the daoFactory Object an connect Method to Connect to the URL where the database is located.
					connection = DaoParkingImpl.daoFactory.connect();
					
					//statement createStatement
					DaoParkingImpl.statement = DaoParkingImpl.connection.createStatement();
					
					//resultSet receive value statement.executeQuerey and the SQL sentence
					resultSet = statement.executeQuery(sql);
					
					
					//Now first we check if resultSet has any results if == false is empty
					if(resultSet.next()==false) {

//						DaoParkingImpl.logicModelParking.displayErrorDataSearch("Mit dem eingegebenen ID-Parking wurde kein Ergebnis gefunden");
						
						JOptionPane.showMessageDialog(null, "Mit dem eingegebenen Buchungsname wurde kein Ergebnis gefunden",  
								"Kein Ergebnis gefunden", JOptionPane.OK_OPTION, this.dialogImg);
						
						//We call to reload o refresh the JTable
						reloadParkingData();
					} //We have results so we show it.
					
					else 
					{
						if(loading !=null) {
							 
							 
							  SwingUtilities.invokeLater(new Runnable() {
					 				
					 				@Override
					 				public void run() {
					 					DaoParkingImpl.loading.setVisible(true);
					 			
					 				}
					 			});
						  }
						
						
						//After that resultSet execute now the Query select * from FUNDSACHEN WHERE dateItemsWasFound. Value of the sql variable
						resultSet = statement.executeQuery("SELECT count(*) from PARKING WHERE idParking = '" + buchungsname + "'");
						
						
						//We move the cursor
						resultSet.next();
						
						//numberOfRowsDatabase receive the counted rows
						numberOfRowsDataBase = resultSet.getInt(1);
						
						resultSet = statement.executeQuery(sql);
						
						 if(loading !=null) {
								loading.progressBar.setMaximum(numberOfRowsDataBase);
						}
						 
						 
						//We create a SwingWorker instruction for the new Thread in background
						 SwingWorker<Void, ParkingReservation> worker = new SwingWorker<Void, ParkingReservation>(){

							

							@Override
							protected Void doInBackground() throws Exception {

								/*
								 * we create an ArrayList type ParkingReservation to add each ParkingReservation object  that we find inside the table Parking in our DataBase
								 */
								List<ParkingReservation> parkingReservation = new ArrayList<ParkingReservation>();
								
								int progress = 0;
								
								//as long as there are entries in the table
								while (resultSet.next()) {
									
									/*We create the necessary variables of the type we need according to the data stored in the database
									 * 
									 * The values are retrieved from each result found in the table and of course from the corresponding column in the table Parking.
									 */
									int id = resultSet.getInt("ID");
									String idParking = resultSet.getString("idparking");
									String buchungsName = resultSet.getString("buchungsname");
									String autokfz = resultSet.getString("autokfz");
									Date anreisedatum = resultSet.getDate("anreisedatum");
									Date abreisedatum = resultSet.getDate("abreisedatum");
									int anzahltagen = resultSet.getInt("anzahltagen");
									double betragparking = resultSet.getDouble("betragparking");
									String buchungskanal = resultSet.getString("buchungskanal");
									String bemerkungen = resultSet.getString("bemerkungen");
									String schluesselinhaus = resultSet.getString("schluesselinhaus");
									String verkaufer = resultSet.getString("verkaufer");
									
									
									
									//We create an object type ParkingReservation
									ParkingReservation parkingReservation2 = new ParkingReservation();
									
									//We set the values of this Object type ParkingReservation 
									parkingReservation2.setId(id);
									parkingReservation2.setIdParking(idParking);
									parkingReservation2.setBuchungsname(buchungsName);
									parkingReservation2.setAutoKFZ(autokfz);
									parkingReservation2.setAnreiseDatum(anreisedatum);
									parkingReservation2.setAbreiseDatum(abreisedatum);
									parkingReservation2.setAnzahlTagen(anzahltagen);
									parkingReservation2.setBetragParking(betragparking);
									parkingReservation2.setBuchungsKanal(buchungskanal);
									parkingReservation2.setBemerkungen(bemerkungen);
									parkingReservation2.setSchluesselInHaus(schluesselinhaus);
									parkingReservation2.setAbkuerzungMA(verkaufer);
									
									
									//add the Object ParkingReservation(parkingReservation2) to the ArrayList parkingReservation
									parkingReservation.add(parkingReservation2);

									publish(parkingReservation2);
									
									
									//We add 1 to the progress variable. 
									progress +=1;
									/*
									 * you can execute a sleep statement to see the progress bar working.
									 */
//									Thread.sleep(500); 
									
									if(loading !=null) {
										loading.progressBar.setValue(progress);
									}
								    

								}
								
							
						
								return null;
							}
							
							
							
							
							@Override
							protected void process(List<ParkingReservation> chunks) {
			
								
								//forEach loop to loop through the list Tip ParkingReservation named chunks in the process Method as parameter.
								for(ParkingReservation chunk: chunks) {

								
									/*
									 * we create an Object array and pass the data contained in our parking table in the Database.
									 * 
									 * by getBetragParking we add the Euro symbol.
									 */
									Object[] row = {chunk.getId(), chunk.getIdParking(), chunk.getBuchungsname(), chunk.getAutoKFZ(),
											chunk.getAnreiseDatum(), chunk.getAbreiseDatum(), chunk.getAnzahlTagen(), 
											chunk.getBetragParking() + " €", chunk.getBuchungsKanal(), chunk.getBemerkungen(), 
											chunk.getSchluesselInHaus(), chunk.getAbkuerzungMA()};
									
									
									/*
									 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the parkingTable 
									 * by the AHBParking class.
									 */
									
									DefaultTableModel model = (DefaultTableModel)dataBaseGUI.parkingTable.getModel();
								
									
									/*
									 * for the parkingTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableParkingUtilities
									 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
									 * 
									 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
									 * 
									 * If the cell is type number then it will have a different font color. 
									 */
									dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.ID).setCellRenderer(new CellTableManager("number"));
									dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.IDPARKING).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSNAME).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.AUTOKFZ).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.ANREISEDATUM).setCellRenderer(new CellTableManager("number"));
									dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BETRAG).setCellRenderer(new CellTableManager("number"));
									dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSKANAL).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BEMERKUNGENG).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.SCHLUESSEL).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.KUERSELMA).setCellRenderer(new CellTableManager("text"));
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
	}

	
	
	
	
	
	
	
	
	

	@Override
	public void suchenByAutoNr(String autokfz) throws DaoException {
		
		//We create a new DefaultTableModel Casting DefaultTableModel our instance dataBaseGUI.parkingTable.getModel to get the Table model.
		DefaultTableModel model = (DefaultTableModel) dataBaseGUI.parkingTable.getModel();
		
		//We set the RowCount to 0 for deleting all the content from the JTable.
		model.setRowCount(0);
		
		
		try {
			
			//Set new value to the urlDB variable
			DaoParkingImpl.urlDB = DaoParkingImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBase()) + File.separator + getDBName();

		}catch (Exception e) {
			e.printStackTrace();
		}
		
		// We set the URL to connect to the Database.
		setURLToConnectCurrentDataBase();

		//We could use LIKE here by the SQL Sentence buchungsname but we want at the Moment to have exact name.
		String sql = "SELECT * from PARKING WHERE autokfz = '" + autokfz + "'";
		
		try {
			
			//Initialize daoFactory Object with the DerbyURL value as argument.
			daoFactory = new DaoFactory(getDerbyURL());
			
			//Initialize the connection object calling the daoFactory Object an connect Method to Connect to the URL where the database is located.
			connection = DaoParkingImpl.daoFactory.connect();
			
			//statement createStatement
			DaoParkingImpl.statement = DaoParkingImpl.connection.createStatement();
			
			//resultSet receive value statement.executeQuerey and the SQL sentence
			resultSet = statement.executeQuery(sql);
			
			
			//Now first we check if resultSet has any results if == false is empty
			if(resultSet.next()==false) {

//				DaoParkingImpl.logicModelParking.displayErrorDataSearch("Mit dem eingegebenen ID-Parking wurde kein Ergebnis gefunden");
				
				JOptionPane.showMessageDialog(null, "Mit dem eingegebenen Autokennzeichen wurde kein Ergebnis gefunden",  
						"Kein Ergebnis gefunden", JOptionPane.OK_OPTION, this.dialogImg);
				
				//We call to reload o refresh the JTable
				reloadParkingData();
			} //We have results so we show it.
			
			else 
			{
				if(loading !=null) {
					 
					 
					  SwingUtilities.invokeLater(new Runnable() {
			 				
			 				@Override
			 				public void run() {
			 					DaoParkingImpl.loading.setVisible(true);
			 			
			 				}
			 			});
				  }
				
				
				//After that resultSet execute now the Query select * from FUNDSACHEN WHERE dateItemsWasFound. Value of the sql variable
				resultSet = statement.executeQuery("SELECT count(*) from PARKING WHERE autokfz = '" + autokfz + "'");
				
				
				//We move the cursor
				resultSet.next();
				
				//numberOfRowsDatabase receive the counted rows
				numberOfRowsDataBase = resultSet.getInt(1);
				
				resultSet = statement.executeQuery(sql);
				
				 if(loading !=null) {
						loading.progressBar.setMaximum(numberOfRowsDataBase);
				}
				 
				 
				//We create a SwingWorker instruction for the new Thread in background
				 SwingWorker<Void, ParkingReservation> worker = new SwingWorker<Void, ParkingReservation>(){

					

					@Override
					protected Void doInBackground() throws Exception {

						/*
						 * we create an ArrayList type ParkingReservation to add each ParkingReservation object  that we find inside the table Parking in our DataBase
						 */
						List<ParkingReservation> parkingReservation = new ArrayList<ParkingReservation>();
						
						int progress = 0;
						
						//as long as there are entries in the table
						while (resultSet.next()) {
							
							/*We create the necessary variables of the type we need according to the data stored in the database
							 * 
							 * The values are retrieved from each result found in the table and of course from the corresponding column in the table Parking.
							 */
							int id = resultSet.getInt("ID");
							String idParking = resultSet.getString("idparking");
							String buchungsName = resultSet.getString("buchungsname");
							String autokfz = resultSet.getString("autokfz");
							Date anreisedatum = resultSet.getDate("anreisedatum");
							Date abreisedatum = resultSet.getDate("abreisedatum");
							int anzahltagen = resultSet.getInt("anzahltagen");
							double betragparking = resultSet.getDouble("betragparking");
							String buchungskanal = resultSet.getString("buchungskanal");
							String bemerkungen = resultSet.getString("bemerkungen");
							String schluesselinhaus = resultSet.getString("schluesselinhaus");
							String verkaufer = resultSet.getString("verkaufer");
							
							
							
							//We create an object type ParkingReservation
							ParkingReservation parkingReservation2 = new ParkingReservation();
							
							//We set the values of this Object type ParkingReservation 
							parkingReservation2.setId(id);
							parkingReservation2.setIdParking(idParking);
							parkingReservation2.setBuchungsname(buchungsName);
							parkingReservation2.setAutoKFZ(autokfz);
							parkingReservation2.setAnreiseDatum(anreisedatum);
							parkingReservation2.setAbreiseDatum(abreisedatum);
							parkingReservation2.setAnzahlTagen(anzahltagen);
							parkingReservation2.setBetragParking(betragparking);
							parkingReservation2.setBuchungsKanal(buchungskanal);
							parkingReservation2.setBemerkungen(bemerkungen);
							parkingReservation2.setSchluesselInHaus(schluesselinhaus);
							parkingReservation2.setAbkuerzungMA(verkaufer);
							
							
							//add the Object ParkingReservation(parkingReservation2) to the ArrayList parkingReservation
							parkingReservation.add(parkingReservation2);

							publish(parkingReservation2);
							
							
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
					protected void process(List<ParkingReservation> chunks) {
	
						
						//forEach loop to loop through the list Tip ParkingReservation named chunks in the process Method as parameter.
						for(ParkingReservation chunk: chunks) {

						
							/*
							 * we create an Object array and pass the data contained in our parking table in the Database.
							 * 
							 * by getBetragParking we add the Euro symbol.
							 */
							Object[] row = {chunk.getId(), chunk.getIdParking(), chunk.getBuchungsname(), chunk.getAutoKFZ(),
									chunk.getAnreiseDatum(), chunk.getAbreiseDatum(), chunk.getAnzahlTagen(), 
									chunk.getBetragParking() + " €", chunk.getBuchungsKanal(), chunk.getBemerkungen(), 
									chunk.getSchluesselInHaus(), chunk.getAbkuerzungMA()};
							
							
							/*
							 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the parkingTable 
							 * by the AHBParking class.
							 */
							
							DefaultTableModel model = (DefaultTableModel)dataBaseGUI.parkingTable.getModel();
						
							
							/*
							 * for the parkingTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableParkingUtilities
							 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
							 * 
							 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
							 * 
							 * If the cell is type number then it will have a different font color. 
							 */
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.ID).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.IDPARKING).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSNAME).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.AUTOKFZ).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.ANREISEDATUM).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BETRAG).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSKANAL).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.BEMERKUNGENG).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.SCHLUESSEL).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.parkingTable.getColumnModel().getColumn(TableParkingUtilities.KUERSELMA).setCellRenderer(new CellTableManager("text"));
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
	}



	
	

	
	
	
	
	
	
	
	
	



	

}

