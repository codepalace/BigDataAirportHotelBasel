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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import tech.codepalace.model.Fundgegenstand;
import tech.codepalace.model.UserAHB;
import tech.codepalace.utility.CellTableManager;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.utility.TableFundsachenUtilities;
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
	
	protected Fundgegenstand fundgegenstand;
	
	
	public DaoFundsachenImpl(UserAHB userAHB, DataBaseGUI dataBaseGUI, Loading loading) {
		
		this.userAHB = userAHB;
		this.dataBaseGUI = dataBaseGUI;
		DaoFundsachenImpl.loading = loading;
		DaoFundsachenImpl.dataEncryption = new DataEncryption();
		
		DaoFundsachenImpl.tableChecked = false;
		
		DaoFundsachenImpl.dataEncryption = new DataEncryption();
		
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
							
						
							System.out.println("FUNDSACHEN table exists");
							//Table FUNDSACHEN exists
							//Then we call displayListFundsachen() Method.
							displayListFundsachen();
							
							
						} 
						else { //otherwise table FUNDSACHEN do not exists
							
							System.out.println("FUNDSACHEN table do not exists");
							
							
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
	
	
	
   //This method is not used at the moment in this Class but maybe in future we are going to use it when we want to count cour Rows.
	@Override
	public int getDataRowCounter() throws DaoException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	
	
	/*
	 * LogicModelNewFund sachen debe crear tambien una infor para rueckabe sin texto al comienzo. Texto que va a poder ser 
	 * editado desde la tabla y guardar la informacion en la base de datos cuando se entregue de vuelta el articulo encontrado
	 */

	@Override
	public void addNewFundsachen(Fundgegenstand fundgegenstand, UserAHB userAHB) throws DaoException {
		
		this.fundgegenstand = fundgegenstand;
		this.userAHB = userAHB;
		

		//we initialize the instance
		try {
			
		
			
			DaoFundsachenImpl.urlDB = DaoFundsachenImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBase()) + File.separator + getDBName();
			

			
			setURLToConnectCurrentDataBase();
			
			
			
			DaoFundsachenImpl.daoFactory = new DaoFactory(getDerbyURL());
			//We set the value of connection calling daoFactory to connect(). connect Method return a Connection Object.
		
			DaoFundsachenImpl.connection = DaoFundsachenImpl.daoFactory.connect();

		} catch (Exception e1) {

			
			e1.printStackTrace();
		}
		
		
		
		
		if(connection!=null) {
			
			//Variable with the SQL instruction value to be execute for inserting a new row to the Table FUNDSACHEN in the database. 
			String sql = "INSERT  into FUNDSACHEN(dateItemsWasFound, foundItem, foundPlace, inhaber, kisteNummer, kisteName, rueckgabe, verkaufer) "
					+ "VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
			
			
			try {
				
				//We set the value for the preparedStatement object.
				this.preparedStatement = connection.prepareStatement(sql);
				
				this.preparedStatement.setDate(1, this.fundgegenstand.getDateItemsWasFound());
				this.preparedStatement.setString(2, this.fundgegenstand.getFoundItems());
				this.preparedStatement.setString(3, this.fundgegenstand.getFundort());
				this.preparedStatement.setString(4,  this.fundgegenstand.getInhaber());
				this.preparedStatement.setInt(5,  this.fundgegenstand.getKisteNummer());
				this.preparedStatement.setString(6, this.fundgegenstand.getKisteName());
				this.preparedStatement.setString(7,  this.fundgegenstand.getRueckGabe());
				this.preparedStatement.setString(8, this.fundgegenstand.getAbkuerzungMA());
				
				this.preparedStatement.executeUpdate();
				
				DaoFundsachenImpl.connection.commit();
				
				
			} catch (SQLException e) {
				
				e.printStackTrace();
			
				//Close
			}finally {
				
				try {
					
					this.preparedStatement.close();
					DaoFundsachenImpl.connection.close();
					DaoFundsachenImpl.daoFactory.closeConnection(getDerbyURL());
					
					//We create a new DefaultTableModel Casting DefaultTableModel our instance fundsachenTable.getModel to get the Table model.
					DefaultTableModel model = (DefaultTableModel) dataBaseGUI.fundsachenTable.getModel();
					
					//We set the RowCount to 0 for deleting all the content from the JTable.
					model.setRowCount(0);
					
					
					try {
						//We set the urlDB value again for connecting to display included the new Entry.
						urlDB = DaoFundsachenImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBase()) + File.separator + getDBName();

				
					} catch (Exception e) {
						e.printStackTrace();
					}
					

					if(daoFactory==null) {
						//We instantiate the class DaoFactory with the URL parameter.
						daoFactory = new DaoFactory(getDerbyURL());
					}
					
					DaoFundsachenImpl.connection = daoFactory.connect();
					
					displayListFundsachen();
					
					
				}catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
		
	}

	
	
	
	
	
	@Override
	public void displayListFundsachen() throws DaoException {
		
		setURLToConnectCurrentDataBase();
		
		daoFactory.connect();
		
		String sqlString = "SELECT * From FUNDSACHEN";
			
		try {
			
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(sqlString);
			
			if(resultSet.next()== false) {
				
				this.dataBaseGUI.setVisible(true);
			
			}else {
				
				if(loading !=null) {
					
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {

							DaoFundsachenImpl.loading.setVisible(true);
						}
					});
				}
				
				//resultSet receive the statement select to count all the rows in the Fundsache table
				resultSet = statement.executeQuery("Select count(*) from FUNDSACHEN");
				
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
				 SwingWorker<Void, Fundgegenstand> worker = new SwingWorker<Void, Fundgegenstand>(){

					

					@Override
					protected Void doInBackground() throws Exception {

						/*
//						 * we create an ArrayList type ParkingReservation to add each ParkingReservation object  that we find inside the table Parking in our DataBase
//						 */
						List<Fundgegenstand> fundgegenstands = new ArrayList<Fundgegenstand>();
						
						int progress = 0;
						
						//as long as there are entries in the table
						while (resultSet.next()) {
							
							/*We create the necessary variables of the type we need according to the data stored in the database
							 * 
							 * The values are retrieved from each result found in the table and of course from the corresponding column in the table FUNDSACHEN.
							 */
							
							Date dateItemWasFound = resultSet.getDate("dateItemsWasFound");
							String foundItem = resultSet.getString("foundItem");
							String foundPlace = resultSet.getString("foundPlace");
							String inhaber = resultSet.getString("inhaber");
							int kisteNummer = resultSet.getInt("kisteNummer");
							String kisteName = resultSet.getString("kisteName");
							String rueckGabe = resultSet.getString("rueckGabe");
							String verkaufer = resultSet.getString("verkaufer");
							
							//We create an object type Fundgegenstand
							Fundgegenstand fundgegenstand = new Fundgegenstand();
							
							//We set the values of this Object type Fundgegenstand
							fundgegenstand.setDateItemsWasFound(dateItemWasFound);
							fundgegenstand.setFoundItems(foundItem);
							fundgegenstand.setFundort(foundPlace);
							fundgegenstand.setInhaber(inhaber);
							fundgegenstand.setKisteNummer(kisteNummer);
							fundgegenstand.setKisteName(kisteName);
							fundgegenstand.setRueckGabe(rueckGabe);
							fundgegenstand.setAbkuerzungMA(verkaufer);
							
							fundgegenstands.add(fundgegenstand);
							
							//We send to publish every Object that we are getting by  the interaction inside the while
							publish(fundgegenstand);
							
						
							
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
					protected void process(List<Fundgegenstand> chunks) {
	
						
						//forEach loop to loop through the list Tip ParkingReservation named chunks in the process Method as parameter.
						for(Fundgegenstand chunk: chunks) {

						
							/*
							 * we create an Object array and pass the data contained in our parking table in the Database.
							 * 
							 * by getBetragParking we add the Euro symbol.
							 */
							Object[] row = {chunk.getDateItemsWasFound(), chunk.getFoundItems(), chunk.getFundort(),
									chunk.getInhaber(), chunk.getKisteNummer(), chunk.getKisteName(), 
									chunk.getRueckGabe(), chunk.getAbkuerzungMA()};
							
							
							/*
							 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the parkingTable 
							 * by the AHBParking class.
							 */
							
							DefaultTableModel model = (DefaultTableModel)dataBaseGUI.fundsachenTable.getModel();
						
							
							/*
							 * for the parkingTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableParkingUtilities
							 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
							 * 
							 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
							 * 
							 * If the cell is type number then it will have a different font color. 
							 */
							
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.DATUM).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDSACHEN).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDORT).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.INHABER).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTNUMMER).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTENAME).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.RUECKGABE).setCellRenderer(new CellTableManager("text"));
							
							//aqui tenemos que agregar la constante verkaufer oder mitarbeiter. se queda para la otra aventura.
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KUERSELMA).setCellRenderer(new CellTableManager("text"));
							
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
