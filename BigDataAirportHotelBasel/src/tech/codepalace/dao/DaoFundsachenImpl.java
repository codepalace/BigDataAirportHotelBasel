package tech.codepalace.dao;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableModel;

import tech.codepalace.model.Fundgegenstand;
import tech.codepalace.model.LogicModelFundSachen;
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

	// Variable for the Table name. in this case FUNDSACHEN.
	protected String table_name = "FUNDSACHEN";
	
	//id from selected Entry in Database Table
	private int id;

	// Variable ResultSet required for working with our DataBase Data.
	private static ResultSet resultSet = null;
	
	
	
	private UserAHB userAHB;
	private DataBaseGUI dataBaseGUI;
	private static Loading loading;
	
	private  static LogicModelFundSachen logicModelFundSachen;
	
	
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
	
	//Variables objects to display Message in case we did not find any results in DataBase.
	private JPanel messagePanelSearchDataBase;
	private JLabel messageLabelSearchDataBase;
	private JButton okButtonSearchDataBase = new JButton("OK");
	private JDialog dialogSearchDatabase;
	private Object[]optionSearchDatabase = {okButtonSearchDataBase};
	private ImageIcon imgSearchDataBase = new ImageIcon(getClass().getResource("/img/dialogo.png"));
	
	//Variable to evaluate the Moth we want to display Data
	private static String monthToShow="ALL";
	
	//Variable to search in database by Date (date items was found).
	private Date dateItemsWasFound;

	
	
	public DaoFundsachenImpl(UserAHB userAHB, DataBaseGUI dataBaseGUI, Loading loading, LogicModelFundSachen logicModelFundSachen) {
		
		this.userAHB = userAHB;
		this.dataBaseGUI = dataBaseGUI;
		DaoFundsachenImpl.loading = loading;
		DaoFundsachenImpl.dataEncryption = new DataEncryption();
		
		DaoFundsachenImpl.tableChecked = false;
		
		DaoFundsachenImpl.dataEncryption = new DataEncryption();
		
		DaoFundsachenImpl.logicModelFundSachen = logicModelFundSachen;
		
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
							
						
//							System.out.println("FUNDSACHEN table exists");
							//Table FUNDSACHEN exists
							//Then we call displayListFundsachen() Method.
							
							/*
							 * We call the method below to display List of Lost and found depending with month we are now. 
							 */
							displayListfundsachenByMonth();
					
							
							
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
					
					/*
					 * We call the method below to display List of Lost and found depending with month we are now. 
					 */
					displayListfundsachenByMonth();
					
					
					
				}catch (SQLException e) {
					// TODO: handle exception
				}
			}
		}
		
	}

	
	
	
	
	
	@Override
	public void displayListFundsachen(String  monthToShow) throws DaoException {
		
		DaoFundsachenImpl.monthToShow = monthToShow;
		
		
		setURLToConnectCurrentDataBase();
		
		daoFactory.connect();
		//ORDER BY date_field ASC | DESC;
		String sqlString = "SELECT * From FUNDSACHEN ORDER BY dateItemsWasFound ASC";
		
		switch(DaoFundsachenImpl.monthToShow) {
			
			case "Januar":
				
				sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-01-01'" +
						 " And '" + DaoFundsachenImpl.now.getYear() + "-01-31') ORDER BY dateItemsWasFound ASC";
				
				break;
			
			case "Februar":
		
				
				//We evaluate if the Year is LeapYear and depending of the results we set the value for the sqlString for the Request 
				if(now.toLocalDate().isLeapYear()) {
					sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-02-01'" +
							 " And '" + DaoFundsachenImpl.now.getYear() + "-02-29') ORDER BY dateItemsWasFound ASC";
				}else {
					sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-02-01'" +
							 " And '" + DaoFundsachenImpl.now.getYear() + "-02-28') ORDER BY dateItemsWasFound ASC";
				}
				
				break;
				
			case "März":
				
				sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-03-01'" +
						 " And '" + DaoFundsachenImpl.now.getYear() + "-03-31') ORDER BY dateItemsWasFound ASC";
				
				
				
				break;
				
			case "April":
				
				sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-04-01'" +
						 " And '" + DaoFundsachenImpl.now.getYear() + "-01-30') ORDER BY dateItemsWasFound ASC";
				
				
				break;
				
			case "Mai":
				
				sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-05-01'" +
						 " And '" + DaoFundsachenImpl.now.getYear() + "-01-31') ORDER BY dateItemsWasFound ASC";
				
				
				break;
				
			case "Juni":
				
				sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-06-01'" +
						 " And '" + DaoFundsachenImpl.now.getYear() + "-06-30') ORDER BY dateItemsWasFound ASC";
				
				
				break;
				
			case "Juli":
				
				sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-07-01'" +
						 " And '" + DaoFundsachenImpl.now.getYear() + "-07-31') ORDER BY dateItemsWasFound ASC";
				
				
				break;
				
			case "August":
				
				sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-08-01'" +
						 " And '" + DaoFundsachenImpl.now.getYear() + "-08-31') ORDER BY dateItemsWasFound ASC";
				
				
				break;
				
			case "September":
				
				sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-09-01'" +
						 " And '" + DaoFundsachenImpl.now.getYear() + "-0-30') ORDER BY dateItemsWasFound ASC";
				
				
				break;
				
			case "Oktober":
				sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-10-01'" +
						 " And '" + DaoFundsachenImpl.now.getYear() + "-10-31') ORDER BY dateItemsWasFound ASC";
				
				break;
				
			case "November":
				
				sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-11-01'" +
						 " And '" + DaoFundsachenImpl.now.getYear() + "-11-30') ORDER BY dateItemsWasFound ASC";
				
				
				break;
				
			case "Dezember":
				
				sqlString = "SELECT * From FUNDSACHEN where (dateItemsWasFound Between '" + DaoFundsachenImpl.now.getYear() + "-12-01'" +
						 " And '" + DaoFundsachenImpl.now.getYear() + "-12-31') ORDER BY dateItemsWasFound ASC";
				
				
				break;
				
			
			case "DateToFind":
				
				
				sqlString = "SELECT * from FUNDSACHEN WHERE dateItemsWasFound = '" + dateItemsWasFound + "'";
			
				
				break;
				
			
		}
		
			
		try {
			
			statement = connection.createStatement();
			
			resultSet = statement.executeQuery(sqlString);
			
			//If we do not have any results
			if(resultSet.next()== false) {

				//First we evaluate the value of monthToShow 
				switch (DaoFundsachenImpl.monthToShow) {
					
					//In case we was requesting for a Date to display the value but do not exists.
					case "DateToFind":
						
						//We invoke a new Thread with the error message.
						SwingUtilities.invokeLater( () ->  JOptionPane.showMessageDialog(null, "Mit dem eingegebenen Datum wurde kein Ergebnis gefunden"
								   , "Kein Ergebnis gefunden", JOptionPane.ERROR_MESSAGE, this.imgSearchDataBase));
						
						break;

				
				}
				
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
							
							int id = resultSet.getInt("ID");
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
							fundgegenstand.setId(id);
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
							Object[] row = {chunk.getId(), chunk.getDateItemsWasFound(), chunk.getFoundItems(), chunk.getFundort(),
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
							
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.ID).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.DATUM).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDSACHEN).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDORT).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.INHABER).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTNUMMER).setCellRenderer(new CellTableManager("number"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTENAME).setCellRenderer(new CellTableManager("text"));
							dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.RUECKGABE).setCellRenderer(new CellTableManager("important"));
							
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
	
	
	
	
	private void displayListfundsachenByMonth() {
		
		try {
		switch (DaoFundsachenImpl.now.getMonthValue()) {
			case 1:
				
					displayListFundsachen("Januar");
				
				break;
			case 2:
				displayListFundsachen("Februar");
				break;
			case 3: displayListFundsachen("März");
				break;
			case 4: displayListFundsachen("April");
				break;
			case 5: displayListFundsachen("Mai");
				break;
			case 6: displayListFundsachen("Juni");
				break;
			case 7: displayListFundsachen("Juli");
				break;
			case 8: displayListFundsachen("August");
				break;
			case 9: displayListFundsachen("September");
				break;
			case 10: displayListFundsachen("Oktober");
				break;
			case 11: displayListFundsachen("November");
				break;
			case 12: displayListFundsachen("Dezember");
				break;
		}
		
		} catch (DaoException e) {
			e.printStackTrace();
		}
		
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




	@Override
	public void updateFundsachen(Fundgegenstand fundgegenstand) throws DaoException {

		//fundgegenstand receives the new value from the parameter. 
		this.fundgegenstand = fundgegenstand;
		
		
		try {

			//We set the URL to connect to the Database.
           setURLToConnectCurrentDataBase();
			
			
			//Initialize daoFactory Object with the URL value.
			DaoFundsachenImpl.daoFactory = new DaoFactory(getDerbyURL());
			
			//We set the value of connection calling daoFactory to connect(). connect Method return a Connection Object.
			DaoFundsachenImpl.connection = DaoFundsachenImpl.daoFactory.connect();

			/*SQL Sentence to update the FUNDSACHEN table in our Database only where the id= got it from the 
			 * fundgegenstand Object call getId() Method.
			 */
			String sql = "Update FUNDSACHEN set dateItemsWasFound=?, foundItem=?, foundPlace=?, "
												+ "inhaber=?, kisteNummer=?, kisteName=?, rueckGabe=?,"
												+ "verkaufer=? where id=" + this.fundgegenstand.getId() +"";
				
			//Initialize value preparedStatement giving the connection the sql sentence.
			this.preparedStatement = connection.prepareStatement(sql);

			//We set the new values to the selected row i mean where we have the id number to be modified.
			this.preparedStatement.setDate(1, this.fundgegenstand.getDateItemsWasFound());
			this.preparedStatement.setString(2, this.fundgegenstand.getFoundItems());
			this.preparedStatement.setString(3, this.fundgegenstand.getFundort());
			this.preparedStatement.setString(4,  this.fundgegenstand.getInhaber());
			this.preparedStatement.setInt(5,  this.fundgegenstand.getKisteNummer());
			this.preparedStatement.setString(6, this.fundgegenstand.getKisteName());
			this.preparedStatement.setString(7,  this.fundgegenstand.getRueckGabe());
			this.preparedStatement.setString(8, this.fundgegenstand.getAbkuerzungMA());
			
			//Ready to update the values
			this.preparedStatement.executeUpdate();
			
			//We write the data definitively
			DaoFundsachenImpl.connection.commit();
			
			
			
		
		} catch (Exception e) {

			e.printStackTrace();
		}finally {
			
			try {
				//Close the connection.
				this.preparedStatement.close();
				DaoFundsachenImpl.connection.close();
				DaoFundsachenImpl.daoFactory.closeConnection(getDerbyURL());

				
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}




	@Override
	public void searchByDateFundsachen(Date dateItemsWasFound) throws DaoException {
		
		this.dateItemsWasFound = dateItemsWasFound;
		
		//We create a new DefaultTableModel Casting DefaultTableModel our instance dataBaseGUI.fundsachenTable.getModel to get the Table model.
		DefaultTableModel model = (DefaultTableModel) dataBaseGUI.fundsachenTable.getModel();
		
		//We set the RowCount to 0 for deleting all the content from the JTable.
		model.setRowCount(0);
		
		//Now we call for a Connection
		DaoFundsachenImpl.connection = daoFactory.connect();
		
		//Call the Method dispalyListFundsachen with the argument the Date we want to display in the JTable.
		displayListFundsachen("DateToFind");
		
	
		
	}



	
	
	
	

	@Override
	public void searchByLostAndFound(String lostAndFoundItems) throws DaoException {

		//We create a new DefaultTableModel Casting DefaultTableModel our instance dataBaseGUI.fundsachenTable.getModel to get the Table model.
		DefaultTableModel model = (DefaultTableModel) dataBaseGUI.fundsachenTable.getModel();
				
		//We set the RowCount to 0 for deleting all the content from the JTable.
		model.setRowCount(0);
				

				try {
					//Set new value to the urlDB variable.
					DaoFundsachenImpl.urlDB = DaoFundsachenImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBase()) + File.separator + getDBName();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				

				// We set the URL to connect to the Database.
				setURLToConnectCurrentDataBase();


				/*SQL Sentence to select all from the FUNDSACHEN table in our Database only where the foundItem(Column in Table) = lostAndFoundItems value
				 */
//				String sql = "SELECT * from FUNDSACHEN WHERE foundItem = '" + lostAndFoundItems + "'";
				String sql = "SELECT * from FUNDSACHEN WHERE foundItem LIKE '%" + lostAndFoundItems + "%'";
		
				
				try {
					
					//Initialize daoFactory Object with the DerbyURL value as argument.
					daoFactory = new DaoFactory(getDerbyURL());
					
					//Initialize the connection object calling the daoFactory Object an connect Method to Connect to the URL where the database is located.
					connection = DaoFundsachenImpl.daoFactory.connect();
					
					//statement createStatement
					DaoFundsachenImpl.statement = DaoFundsachenImpl.connection.createStatement();
					
					//resultSet receive value statement.executeQuerey and the SQL sentence
					resultSet = statement.executeQuery(sql);
					
					
					//Now first we check if resultSet has any results if == false is empty
					if(resultSet.next()==false) {

						
						//Time to display Entries not found.
						this.messageLabelSearchDataBase = new JLabel("Mit dem eingegebenen Fundsachen wurde kein Ergebnis gefunden");
						
						this.messagePanelSearchDataBase = new JPanel(new BorderLayout());
						
						this.messagePanelSearchDataBase.add(this.messageLabelSearchDataBase, BorderLayout.CENTER);
						
						this.okButtonSearchDataBase.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								try {
									dialogSearchDatabase.dispose();
									displayListFundsachen("byLostAndFound");
								} catch (DaoException e1) {
									e1.printStackTrace();
								}
								
							}
						});
						
						this.okButtonSearchDataBase.addKeyListener(new KeyListener() {

							@Override
							public void keyTyped(KeyEvent e) {}

							@Override
							public void keyPressed(KeyEvent e) {

								dialogSearchDatabase.dispose();
								try {
									displayListFundsachen("byLostAndFound");
								} catch (DaoException e1) {
									e1.printStackTrace();
								}
							}

							@Override
							public void keyReleased(KeyEvent e) {}
							
						});
						
						
						dialogSearchDatabase = new JOptionPane(messagePanelSearchDataBase, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, imgSearchDataBase,
								optionSearchDatabase, null).createDialog("Kein Ergebnis gefunden");
						
						
						dialogSearchDatabase.setAlwaysOnTop(true);
						dialogSearchDatabase.setVisible(true);
						
						dialogSearchDatabase.dispose();
						
						
						
					} else { //we have results so we show it.
						
						
						
						if(loading !=null) {
							 
							 
							  SwingUtilities.invokeLater(new Runnable() {
					 				
					 				@Override
					 				public void run() {
					 					DaoFundsachenImpl.loading.setVisible(true);
					 			
					 				}
					 			});
						  }
						
						
						//After that resultSet execute now the Query select * from FUNDSACHEN WHERE dateItemsWasFound. Value of the sql variable
						resultSet = statement.executeQuery("SELECT count(*) from FUNDSACHEN WHERE foundItem LIKE '%" + lostAndFoundItems + "%'");
						
						
						//We move the cursor
						resultSet.next();
						
						//numberOfRowsDatabase receive the counted rows
						numberOfRowsDataBase = resultSet.getInt(1);
						
						resultSet = statement.executeQuery(sql);
						
						 if(loading !=null) {
								loading.progressBar.setMaximum(numberOfRowsDataBase);
						}
						
						 
						 
						//We create a SwingWorker instruction for the new Thread in background
						 SwingWorker<Void, Fundgegenstand> worker = new SwingWorker<Void, Fundgegenstand>(){

							

							@Override
							protected Void doInBackground() throws Exception {

								/*
								 * we create an ArrayList type Fundgegenstand to add each Fundgegenstand object  that we find inside the table FUNDSACHEN in our DataBase
								 */
								List<Fundgegenstand> fundgegenstands = new ArrayList<Fundgegenstand>();
								
								int progress = 0;
								
								//as long as there are entries in the table
								while (resultSet.next()) {
									
									
									/*We create the necessary variables of the type we need according to the data stored in the database
									 * 
									 * The values are retrieved from each result found in the table and of course from the corresponding column in the table FUNDSACHEN.
									 */
									
									int id = resultSet.getInt("ID");
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
									fundgegenstand.setId(id);
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
//									Thread.sleep(500); 
									
									if(loading !=null) {
										loading.progressBar.setValue(progress);
									}
								    

								}
								
							
						
								return null;
							}
							
							
							
							
							@Override
							protected void process(List<Fundgegenstand> chunks) {

								
								//forEach loop to loop through the list Tip Fundgegenstand named chunks in the process Method as parameter.
								for(Fundgegenstand chunk: chunks) {

								
									/*
									 * we create an Object array and pass the data contained in our FUNDSACHEN table in the Database.
									 * 
									 * 
									 */
									Object[] row = {chunk.getId(), chunk.getDateItemsWasFound(), chunk.getFoundItems(), chunk.getFundort(),
											chunk.getInhaber(), chunk.getKisteNummer(), chunk.getKisteName(), 
											chunk.getRueckGabe(), chunk.getAbkuerzungMA()};
									
									
									/*
									 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the FUNDSACHEN 
									 *
									 */
									
									DefaultTableModel model = (DefaultTableModel)dataBaseGUI.fundsachenTable.getModel();
								
									
									/*
									 * for the fundsachenTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableFundsachenUtilities
									 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
									 * 
									 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
									 * 
									 * If the cell is type number then it will have a different font color. 
									 */
									
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.ID).setCellRenderer(new CellTableManager("number"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.DATUM).setCellRenderer(new CellTableManager("number"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDSACHEN).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDORT).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.INHABER).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTNUMMER).setCellRenderer(new CellTableManager("number"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTENAME).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.RUECKGABE).setCellRenderer(new CellTableManager("important"));

									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KUERSELMA).setCellRenderer(new CellTableManager("text"));
									
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




	@Override
	public void searchByGuestName(String guestName) throws DaoException {

		//We create a new DefaultTableModel Casting DefaultTableModel our instance dataBaseGUI.fundsachenTable.getModel to get the Table model.
				DefaultTableModel model = (DefaultTableModel) dataBaseGUI.fundsachenTable.getModel();
						
				//We set the RowCount to 0 for deleting all the content from the JTable.
				model.setRowCount(0);
						

						try {
							//Set new value to the urlDB variable.
							DaoFundsachenImpl.urlDB = DaoFundsachenImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBase()) + File.separator + getDBName();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						

						// We set the URL to connect to the Database.
						setURLToConnectCurrentDataBase();


						/*SQL Sentence to select all from the FUNDSACHEN table in our Database only where the inhaber(Column in Table) = guestName value
						 */
						String sql = "SELECT * from FUNDSACHEN WHERE inhaber = '" + guestName + "'";
						
						
						try {
							
							//Initialize daoFactory Object with the DerbyURL value as argument.
							daoFactory = new DaoFactory(getDerbyURL());
							
							//Initialize the connection object calling the daoFactory Object an connect Method to Connect to the URL where the database is located.
							connection = DaoFundsachenImpl.daoFactory.connect();
							
							//statement createStatement
							DaoFundsachenImpl.statement = DaoFundsachenImpl.connection.createStatement();
							
							//resultSet receive value statement.executeQuerey and the SQL sentence
							resultSet = statement.executeQuery(sql);
							
							
							//Now first we check if resultSet has any results if == false is empty
							if(resultSet.next()==false) {

								
								//Time to display Entries not found.
								this.messageLabelSearchDataBase = new JLabel("Mit dem eingegebenen Namen wurde kein Ergebnis gefunden");
								
								this.messagePanelSearchDataBase = new JPanel(new BorderLayout());
								
								this.messagePanelSearchDataBase.add(this.messageLabelSearchDataBase, BorderLayout.CENTER);
								
								this.okButtonSearchDataBase.addActionListener(new ActionListener() {
									
									@Override
									public void actionPerformed(ActionEvent e) {
										
										dialogSearchDatabase.dispose();
										displayListfundsachenByMonth();
										
									}
								});
								
								this.okButtonSearchDataBase.addKeyListener(new KeyListener() {

									@Override
									public void keyTyped(KeyEvent e) {}

									@Override
									public void keyPressed(KeyEvent e) {

										dialogSearchDatabase.dispose();
										displayListfundsachenByMonth();
									}

									@Override
									public void keyReleased(KeyEvent e) {}
									
								});
								
								
								dialogSearchDatabase = new JOptionPane(messagePanelSearchDataBase, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, imgSearchDataBase,
										optionSearchDatabase, null).createDialog("Kein Ergebnis gefunden");
								
								
								dialogSearchDatabase.setAlwaysOnTop(true);
								dialogSearchDatabase.setVisible(true);
								
								dialogSearchDatabase.dispose();
								
								
								
							} else { //we have results so we show it.
								
								
								
								if(loading !=null) {
									 
									 
									  SwingUtilities.invokeLater(new Runnable() {
							 				
							 				@Override
							 				public void run() {
							 					DaoFundsachenImpl.loading.setVisible(true);
							 			
							 				}
							 			});
								  }
								
								
								//After that resultSet execute now the Query select * from FUNDSACHEN WHERE inhaber. Value of the sql variable
								resultSet = statement.executeQuery("SELECT count(*) from FUNDSACHEN WHERE inhaber = '" + guestName + "'");
								
								
								//We move the cursor
								resultSet.next();
								
								//numberOfRowsDatabase receive the counted rows
								numberOfRowsDataBase = resultSet.getInt(1);
								
								resultSet = statement.executeQuery(sql);
								
								 if(loading !=null) {
										loading.progressBar.setMaximum(numberOfRowsDataBase);
								}
								
								 
								 
								//We create a SwingWorker instruction for the new Thread in background
								 SwingWorker<Void, Fundgegenstand> worker = new SwingWorker<Void, Fundgegenstand>(){

									

									@Override
									protected Void doInBackground() throws Exception {

										/*
										 * we create an ArrayList type Fundgegenstand to add each Fundgegenstand object  that we find inside the table FUNDSACHEN in our DataBase
										 */
										List<Fundgegenstand> fundgegenstands = new ArrayList<Fundgegenstand>();
										
										int progress = 0;
										
										//as long as there are entries in the table
										while (resultSet.next()) {
											
											
											/*We create the necessary variables of the type we need according to the data stored in the database
											 * 
											 * The values are retrieved from each result found in the table and of course from the corresponding column in the table FUNDSACHEN.
											 */
											
											int id = resultSet.getInt("ID");
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
											fundgegenstand.setId(id);
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
//											Thread.sleep(500); 
											
											if(loading !=null) {
												loading.progressBar.setValue(progress);
											}
										    

										}
										
									
								
										return null;
									}
									
									
									
									
									@Override
									protected void process(List<Fundgegenstand> chunks) {

										
										//forEach loop to loop through the list Tip Fundgegenstand named chunks in the process Method as parameter.
										for(Fundgegenstand chunk: chunks) {

										
											/*
											 * we create an Object array and pass the data contained in our FUNDSACHEN table in the Database.
											 * 
											 * 
											 */
											Object[] row = {chunk.getId(), chunk.getDateItemsWasFound(), chunk.getFoundItems(), chunk.getFundort(),
													chunk.getInhaber(), chunk.getKisteNummer(), chunk.getKisteName(), 
													chunk.getRueckGabe(), chunk.getAbkuerzungMA()};
											
											
											/*
											 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the FUNDSACHEN 
											 *
											 */
											
											DefaultTableModel model = (DefaultTableModel)dataBaseGUI.fundsachenTable.getModel();
										
											
											/*
											 * for the fundsachenTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableFundsachenUtilities
											 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
											 * 
											 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
											 * 
											 * If the cell is type number then it will have a different font color. 
											 */
											
											dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.ID).setCellRenderer(new CellTableManager("number"));
											dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.DATUM).setCellRenderer(new CellTableManager("number"));
											dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDSACHEN).setCellRenderer(new CellTableManager("text"));
											dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDORT).setCellRenderer(new CellTableManager("text"));
											dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.INHABER).setCellRenderer(new CellTableManager("text"));
											dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTNUMMER).setCellRenderer(new CellTableManager("number"));
											dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTENAME).setCellRenderer(new CellTableManager("text"));
											dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.RUECKGABE).setCellRenderer(new CellTableManager("important"));

											dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KUERSELMA).setCellRenderer(new CellTableManager("text"));
											
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



	
	
	
	
	

	@Override
	public void suchenNachFundort(String fundOrt) throws DaoException {
		
		//We create a new DefaultTableModel Casting DefaultTableModel our instance dataBaseGUI.fundsachenTable.getModel to get the Table model.
		DefaultTableModel model = (DefaultTableModel) dataBaseGUI.fundsachenTable.getModel();
				
		//We set the RowCount to 0 for deleting all the content from the JTable.
		model.setRowCount(0);
				

				try {
					//Set new value to the urlDB variable.
					DaoFundsachenImpl.urlDB = DaoFundsachenImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBase()) + File.separator + getDBName();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				

				// We set the URL to connect to the Database.
				setURLToConnectCurrentDataBase();


				/*SQL Sentence to select all from the FUNDSACHEN table in our Database only where the inhaber(Column in Table) = guestName value
				 */
				String sql = "SELECT * from FUNDSACHEN WHERE foundPlace = '" + fundOrt + "'";
				
				
				try {
					
					//Initialize daoFactory Object with the DerbyURL value as argument.
					daoFactory = new DaoFactory(getDerbyURL());
					
					//Initialize the connection object calling the daoFactory Object an connect Method to Connect to the URL where the database is located.
					connection = DaoFundsachenImpl.daoFactory.connect();
					
					//statement createStatement
					DaoFundsachenImpl.statement = DaoFundsachenImpl.connection.createStatement();
					
					//resultSet receive value statement.executeQuerey and the SQL sentence
					resultSet = statement.executeQuery(sql);
					
					
					//Now first we check if resultSet has any results if == false is empty
					if(resultSet.next()==false) {

						
						//Time to display Entries not found.
						this.messageLabelSearchDataBase = new JLabel("Mit dem eingegebenen Fundort wurde kein Ergebnis gefunden");
						
						this.messagePanelSearchDataBase = new JPanel(new BorderLayout());
						
						this.messagePanelSearchDataBase.add(this.messageLabelSearchDataBase, BorderLayout.CENTER);
						
						this.okButtonSearchDataBase.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								
								dialogSearchDatabase.dispose();
								displayListfundsachenByMonth();
								
							}
						});
						
						this.okButtonSearchDataBase.addKeyListener(new KeyListener() {

							@Override
							public void keyTyped(KeyEvent e) {}

							@Override
							public void keyPressed(KeyEvent e) {

								dialogSearchDatabase.dispose();
								displayListfundsachenByMonth();
							}

							@Override
							public void keyReleased(KeyEvent e) {}
							
						});
						
						
						dialogSearchDatabase = new JOptionPane(messagePanelSearchDataBase, JOptionPane.OK_OPTION, JOptionPane.NO_OPTION, imgSearchDataBase,
								optionSearchDatabase, null).createDialog("Kein Ergebnis gefunden");
						
						
						dialogSearchDatabase.setAlwaysOnTop(true);
						dialogSearchDatabase.setVisible(true);
						
						dialogSearchDatabase.dispose();
						
						
						
					} else { //we have results so we show it.
						
						
						
						if(loading !=null) {
							 
							 
							  SwingUtilities.invokeLater(new Runnable() {
					 				
					 				@Override
					 				public void run() {
					 					DaoFundsachenImpl.loading.setVisible(true);
					 			
					 				}
					 			});
						  }
						
						
						//After that resultSet execute now the Query select * from FUNDSACHEN WHERE inhaber. Value of the sql variable
						resultSet = statement.executeQuery("SELECT count(*) from FUNDSACHEN WHERE foundPlace = '" + fundOrt + "'");
						
						
						//We move the cursor
						resultSet.next();
						
						//numberOfRowsDatabase receive the counted rows
						numberOfRowsDataBase = resultSet.getInt(1);
						
						resultSet = statement.executeQuery(sql);
						
						 if(loading !=null) {
								loading.progressBar.setMaximum(numberOfRowsDataBase);
						}
						
						 
						 
						//We create a SwingWorker instruction for the new Thread in background
						 SwingWorker<Void, Fundgegenstand> worker = new SwingWorker<Void, Fundgegenstand>(){

							

							@Override
							protected Void doInBackground() throws Exception {

								/*
								 * we create an ArrayList type Fundgegenstand to add each Fundgegenstand object  that we find inside the table FUNDSACHEN in our DataBase
								 */
								List<Fundgegenstand> fundgegenstands = new ArrayList<Fundgegenstand>();
								
								int progress = 0;
								
								//as long as there are entries in the table
								while (resultSet.next()) {
									
									
									/*We create the necessary variables of the type we need according to the data stored in the database
									 * 
									 * The values are retrieved from each result found in the table and of course from the corresponding column in the table FUNDSACHEN.
									 */
									
									int id = resultSet.getInt("ID");
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
									fundgegenstand.setId(id);
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
//									Thread.sleep(500); 
									
									if(loading !=null) {
										loading.progressBar.setValue(progress);
									}
								    

								}
								
							
						
								return null;
							}
							
							
							
							
							@Override
							protected void process(List<Fundgegenstand> chunks) {

								
								//forEach loop to loop through the list Tip Fundgegenstand named chunks in the process Method as parameter.
								for(Fundgegenstand chunk: chunks) {

								
									/*
									 * we create an Object array and pass the data contained in our FUNDSACHEN table in the Database.
									 * 
									 * 
									 */
									Object[] row = {chunk.getId(), chunk.getDateItemsWasFound(), chunk.getFoundItems(), chunk.getFundort(),
											chunk.getInhaber(), chunk.getKisteNummer(), chunk.getKisteName(), 
											chunk.getRueckGabe(), chunk.getAbkuerzungMA()};
									
									
									/*
									 * We create one instance DefaultTableModel and we give the value Casting (DefaultTableModel) and we get the defined TableModel for the FUNDSACHEN 
									 *
									 */
									
									DefaultTableModel model = (DefaultTableModel)dataBaseGUI.fundsachenTable.getModel();
								
									
									/*
									 * for the fundsachenTable we retrieve the column where we want to write the data, using getColumnModel and getColumn for the Column and we also call the TableFundsachenUtilities
									 * to get the correct Column using the corresponding constant where is defined the column number where it belongs.
									 * 
									 * for each column we also call setCellRenderer Method and as argument we pass a new CellTableManager and we specify the type of value that the cell is going to have.
									 * 
									 * If the cell is type number then it will have a different font color. 
									 */
									
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.ID).setCellRenderer(new CellTableManager("number"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.DATUM).setCellRenderer(new CellTableManager("number"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDSACHEN).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDORT).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.INHABER).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTNUMMER).setCellRenderer(new CellTableManager("number"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTENAME).setCellRenderer(new CellTableManager("text"));
									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.RUECKGABE).setCellRenderer(new CellTableManager("important"));

									dataBaseGUI.fundsachenTable.getColumnModel().getColumn(TableFundsachenUtilities.KUERSELMA).setCellRenderer(new CellTableManager("text"));
									
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




	@Override
	public void reloadFundsachenData() throws DaoException {
		
				//We create a new DefaultTableModel Casting DefaultTableModel our instance dataBaseGUI.fundsachenTable.getModel to get the Table model.
				DefaultTableModel model = (DefaultTableModel) dataBaseGUI.fundsachenTable.getModel();
						
				//We set the RowCount to 0 for deleting all the content from the JTable.
				model.setRowCount(0);

				//Now we call for a Connection
				DaoFundsachenImpl.connection = daoFactory.connect();

				//Now we call the Method below to display the results from the Data Base. It will be displayed only the current Month.	
				displayListfundsachenByMonth();
				

	}



	
	
	

	@Override
	public void deleteDatabaseEntry(String tableName, int id) {

		this.table_name = tableName;
		this.id = id;
		
		//SQL Query instruction
	String sql = "delete from " + this.table_name + " where id=" + this.id + "";
	
	
	try {
		//Set new value to the urlDB variable.
		DaoFundsachenImpl.urlDB = DaoFundsachenImpl.dataEncryption.decryptData(this.userAHB.getUrlDataBase()) + File.separator + getDBName();
	} catch (Exception e1) {
		e1.printStackTrace();
	}
	
	// We set the URL to connect to the Database.
	setURLToConnectCurrentDataBase();
	
		
	
	//Initialize daoFactory Object with the DerbyURL value as argument.
	daoFactory = new DaoFactory(getDerbyURL());
			
	//Initialize the connection object calling the daoFactory Object an connect Method to Connect to the URL where the database is located.
	connection = DaoFundsachenImpl.daoFactory.connect();
	
	try {
		
	
		//statement createStatement
		DaoFundsachenImpl.statement = DaoFundsachenImpl.connection.createStatement();
		
		//We set the value for the id got from variable id.
//		this.preparedStatement.setInt(1, this.id);
		
		//Now execute the statement.executeUpdate
		DaoFundsachenImpl.statement.executeUpdate(sql);
		
		//And now write in Database permanently
		DaoFundsachenImpl.connection.commit();
		
		
	}catch (SQLException e) {

		e.printStackTrace();
	}finally {
		
		try {
			//Close the connection.
			DaoFundsachenImpl.statement.close();
			DaoFundsachenImpl.connection.close();
			DaoFundsachenImpl.daoFactory.closeConnection(getDerbyURL());
			
			
			
//			displayListFundsachen();
			logicModelFundSachen.setDataBaseStatus("RELOAD");
			reloadFundsachenData();
			logicModelFundSachen.setDataBaseStatus("");

			
		}catch (SQLException | DaoException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	}
 	
 	
 	

	
	
	
	
	
	
}
