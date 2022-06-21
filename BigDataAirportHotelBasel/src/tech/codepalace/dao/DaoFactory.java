package tech.codepalace.dao;


import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import tech.codepalace.model.UserAHB;
import tech.codepalace.view.frames.DataBaseGUI;
import tech.codepalace.view.frames.Loading;

public class DaoFactory {
	
	//Variable for the connection to the DataBase
	private static Connection connection = null;
	
	//Variable for the URL DATABASE.
	private static String url;
	
	//Database Owner and Password
	protected static String dbOwner = "AHTBasel";
	protected static  String dbPass = "2021_%.APH/BaseltonSip$$3";
	
	//Encryption Key for the DataBase. DataBase should be encrypted.
	protected static String encryptionKey = "cdc42a1e38BfCd43";
	
	//Variable for the UserAHB. By the Object UserAHB will be saved the privilege and different Data
	protected static UserAHB userAHB;

	/*
	 * Variable called to create the Encrypted Derby Database in case is not created yet. 
	 * this variable receive the value of the variable url with the URL where the Database should be created, we also pass the argument create=true to
	 * create the Database, dataEncryption= true to encrypt the DataBase, encryptionAlgorithm=DES/CBC/NoPadding, encryptionKey with the value of our 
	 * variable encryptionKey
	 */
	protected static String urlDB = url + ";user=" + dbOwner + ";create=true;dataEncryption=true" + ";encryptionAlgorithm=DES/CBC/NoPadding;" + "encryptionKey=" + encryptionKey;
	

	//Variable Loading to call for the Modal Loading with the JProgressBar. So the user know Program is doing some tasks.
	public static Loading loading;
	
	//Variable to Know which DataBase Table is called. Parking. Lost and Found etc.
	private static String dataBaseApplication;
	
	//Variable to indicate that the table has been created
	private static boolean tableCreated = false;
	
	//Object DataBaseGUI needed to be called after task were finished.
	private static DataBaseGUI dataBaseGUI;
	
	
	//Constructor only with the String url
	public DaoFactory(String url) {

		DaoFactory.url = url;
	
	}
	

	//Constructor with some other parameters in case the dataBase do not exists
	public DaoFactory(String url, Loading loading, String dataBaseApplication, DataBaseGUI dataBaseGUI) {

		DaoFactory.url = url;
		DaoFactory.loading = loading;
		DaoFactory.dataBaseApplication = dataBaseApplication;
		DaoFactory.dataBaseGUI = dataBaseGUI;
		

	
		
		if(DaoFactory.loading!=null) {
			
			//SwingWorker return null to display the Loading Object while the program do his job in the background.
			SwingWorker<Void, Void>worker = new SwingWorker<Void, Void>(){

				@Override
				protected Void doInBackground() throws Exception {
					
					//While the program is working creating the DataBase table it will be execute this loop
					while(!DaoFactory.tableCreated) {
						
						//We set the JProgressbar to indeterminate with a Color.BLUE
						DaoFactory.loading.progressBar.setIndeterminate(true);
						DaoFactory.loading.progressBar.setForeground(Color.BLUE);
						
						
						//New Thread to execute de Loading Class
						SwingUtilities.invokeLater(new Runnable() {
							
							@Override
							public void run() {
								//Loading Object set visible.
								DaoFactory.loading.setVisible(true);
							}
						});
						
						
					    
						//Call to create the Table with the table name value in the dataBaseApplication variable.
						createTable(DaoFactory.dataBaseApplication);
					}
					
					
					
					return null;
				}
				
			


				@Override
				protected void done() {
					//When all is finished we close the Loading Object and call visible the DataBaseGUI Object.
					DaoFactory.loading.dispose();
					DaoFactory.dataBaseGUI.setVisible(true);
					
				}
				
				
				
				
				
			};worker.execute(); //execute the SwingWorker
			
		
			
			
		}
		
	
	
	}

	
	
	//We are going to work on this Method in next commits.
	public void backupDataBase() {
		
	}
	
	
	

	
	/**
	 * Method to create the DataBase in case does not exists
	 */
	public void createDataBase() {
		

		/*
		 * urlDB: URL where the Database should be created. 
		 * 
		 * Database with the userName as dbOwner, we execute also the Derby instruction create=true for creating the dataBase, ecryptionAlgorithm for the Encrypted DataBase
		 * and with pass also the encryptionKey. 
		 * 
		 * The encryptionKey value in this class but we could use an external source where to look for the key for example like an USB Stick.
		 */
		String urlDB = url + ";user=" + dbOwner + ";create=true;dataEncryption=true" + ";encryptionAlgorithm=DES/CBC/NoPadding;" + "encryptionKey=" + encryptionKey;	
		
		
		
		
		try {
			
			//Even if not needed with Java 8 we load the Driver
			loadDriver();
			
			//We get the connection.
			connection = DriverManager.getConnection(urlDB);
		
		
		try {
			
			/* After the Database was created we get a new Connection with the URL of the DataBase but this time to shutdown the DataBase*/
			DriverManager.getConnection(url  
                    + ";user=" + dbOwner +  ";encryptionAlgorithm=DES/CBC/NoPadding;" + "encryptionKey=" + encryptionKey + ";shutdown=true");
			
			//We create BuitInUser 
			turnOnBuiltInUsers(connection);
			
	

            
            //We close the connection to be sure
            connection.close();
            

			
          //Database shutdown throws the 08006 exception to confirm success.
		} catch (SQLException e1) {
			if ( !e1.getSQLState().equals("08006") ) {
            	
                throw e1;
            }
		}		
		
		
//		 System.out.println("Database shut down normally");
		
		} catch (SQLException e2) {
			 errorPrintAndExit(e2);
		}
		
		
	}
	
	
	
	
	
	/**
	 * @Method to connect to the Database. Return Connection needed from other Classes.
	 * @return
	 */
	public Connection connect() {
		
		
		
		try {
			    //Driver info with for the new Instance Derby Embedded DataBase Motor.
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
				
				//Get a connection
				try {
					
					//Connection receives the DriverManager Connection value getting the URL for the Database calling the getUrlDB Method.
					connection = DriverManager.getConnection(DaoFactory.getUrlDB());
					
					//setAutoCommit to false so we can saved the entries in the Database manually.
					connection.setAutoCommit(false);
					
				} catch (SQLException e) {
			
					e.printStackTrace();
				}
				
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			return connection;
		
		
	}
	
	
	/**
	 * @description Method to return the URL from the DATABASE.
	 * @return
	 */
	protected static String getUrlDB() {
		urlDB = url + ";user=" + dbOwner + ";create=true;dataEncryption=true" + ";encryptionAlgorithm=DES/CBC/NoPadding;" + "encryptionKey=" + encryptionKey;
		
		
		return DaoFactory.urlDB;
	}
	
	
	

	
	
	/**
	 * @description Method to make a clean close connection and shutdown derby system.
	 * @param urlDataBase
	 */
	public void closeConnection(String urlDataBase) {
		
		
		try {


         
            try {
            	
            	//DriverManager get the Connection values included our encrypted parameters for our encrypted DataBase and of course the shutdown as true.
            	DriverManager.getConnection(urlDataBase  
                        + ";user=" + dbOwner + ";password=" + dbPass +  ";encryptionAlgorithm=DES/CBC/NoPadding;" + "encryptionKey=" + encryptionKey + ";shutdown=true");

            } catch (SQLException se) {
            	
            	//Database shutdown throws the 08006 exception to confirm success.
                if ( !se.getSQLState().equals("08006") ) {
                    throw se;
                }
            }
//            System.out.println("Database shut down normally");

            try {
            	//DriverManager reset URL for shutdown
                DriverManager.getConnection("jdbc:derby:;shutdown=true");
            } catch (SQLException se) {
            	
            	//Derby system shutdown. 
                if ( !se.getSQLState().equals("XJ015") ) {
                    throw se;
                }
            }

            System.out.println("Derby system shut down normally");
            
            //We close the connection
            connection.close();
        } catch (SQLException e) {
        	
        	//print error
            errorPrintAndExit(e);
        }
		
	}
	
	
	
	
	
	
	/**
	 * @description Method to createTable in our DataBase. 
	 * @param tableName will receive the name of the table with want to create
	 */
	public void createTable(String tableName) {
		
		
		String sql="";
		
		/* We use a conditional switch to evaluate tableName.
		 * 
		 * Depending on the name of the table, we will create the table with the necessary values.
		 */
		switch (tableName) {
			
			case "PARKING":
				
				//we proceed to create the table PARKING
				  sql = "CREATE TABLE " + tableName + "( "
						 
						 + "ID INT NOT NULL GENERATED ALWAYS AS IDENTITY,"
						 + "idparking VARCHAR(12) NOT NULL,"
						 + "buchungsname VARCHAR(50) NOT NULL,"
						 + "autokfz VARCHAR(15) NOT NULL,"
						 + "anreisedatum DATE NOT NULL,"
						 + "abreisedatum DATE NOT NULL,"
						 + "anzahltagen INTEGER,"
						 + "betragparking decimal(6,2) NOT NULL,"
						 + "buchungskanal VARCHAR(40) NOT NULL,"
						 + "bemerkungen VARCHAR(255) NOT NULL,"
						 + "schluesselinhaus VARCHAR(5) NOT NULL,"
						 +	"verkaufer VARCHAR(20) NOT NULL,"
						 + "PRIMARY KEY (ID)"
						 + ")";
				break;

			default:
				break;
		}
	  
				
				 
				
				try {
					
					//We set the connection values getting the Connection values through the getUrlDB method.
					 connection = DriverManager.getConnection(DaoFactory.getUrlDB());
					
					 //New Statement object for creating the statement
					 Statement statement = connection.createStatement();
					 
					 //We execute the SQL instruction for creating the table with the values we need.
					 statement.executeUpdate(sql);
					 
					 //close statement.
					 statement.close();
					 
					 
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					
					//Finally close Connection.
					closeConnection(DaoFactory.getUrlDB());
				}
			
				//Tabel was created we set the tableCreated variable value true
				DaoFactory.tableCreated = true;
				
//				System.out.println("The : " + tableName + " table was correctly created");
		
	}
	
	
	
	
	
	/**
	 * Method to load the Database Driver.
	 */
	private static void loadDriver() {
		
		try {
			
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			
		} catch (ClassNotFoundException e) {
			
			System.out.println("Driver was not found");
		}
	}

	
	
	
	 /**
     * Turn on built-in user authentication and SQL authorization.
     *
     * Default connection mode is fullAccess, but SQL authorization
     * restricts access to the owners of database objects.
     * 
     * @param conn a connection to the database
     */
    protected static void turnOnBuiltInUsers(Connection conn) 
            throws SQLException {

        String setProperty = 
            "CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(";
        String getProperty = 
            "VALUES SYSCS_UTIL.SYSCS_GET_DATABASE_PROPERTY(";
        String requireAuth = "'derby.connection.requireAuthentication'";
        String sqlAuthorization = "'derby.database.sqlAuthorization'";
        String defaultConnMode =
            "'derby.database.defaultConnectionMode'";
        String fullAccessUsers = "'derby.database.fullAccessUsers'";
        @SuppressWarnings("unused")
		String readOnlyAccessUsers =
            "'derby.database.readOnlyAccessUsers'";
        String provider = "'derby.authentication.provider'";
        String propertiesOnly = "'derby.database.propertiesOnly'";

        System.out.println(
            "Turning on authentication and SQL authorization.");
        Statement s = conn.createStatement();

        // Set requireAuthentication
        s.executeUpdate(setProperty + requireAuth + ", 'true')");
        // Set sqlAuthorization
        s.executeUpdate(setProperty + sqlAuthorization + ", 'true')");

        // Retrieve and display property values
        ResultSet rs = s.executeQuery(getProperty + requireAuth + ")");
        rs.next();
        System.out.println(
            "Value of requireAuthentication is " + rs.getString(1));

        rs = s.executeQuery(getProperty + sqlAuthorization + ")");
        rs.next();
        System.out.println(
            "Value of sqlAuthorization is " + rs.getString(1));

        // Set authentication scheme to Derby builtin
        s.executeUpdate(setProperty + provider + ", 'BUILTIN')");
    
        // Create some sample users
        s.executeUpdate(
            setProperty + "'derby.user.AHTBasel', '2021_%.APH/BaseltonSip$$3')");
        
        // Define noAccess as default connection mode
        s.executeUpdate(
            setProperty + defaultConnMode + ", 'noAccess')");

        // Confirm default connection mode
        rs = s.executeQuery(getProperty + defaultConnMode + ")");
        rs.next();
        System.out.println("Value of defaultConnectionMode is " +
            rs.getString(1));

        // Define read-write users
        s.executeUpdate(
            setProperty + fullAccessUsers + ", 'AHTBasel')");

       
        
        // Therefore, user sa has no access

        // Confirm full-access users
        rs = s.executeQuery(getProperty + fullAccessUsers + ")");
        rs.next();
        System.out.println(
            "Value of fullAccessUsers is " + rs.getString(1));

        

        // We would set the following property to TRUE only when we were
        // ready to deploy. Setting it to FALSE means that we can always
        // override using system properties if we accidentally paint
        // ourselves into a corner.
        s.executeUpdate(setProperty + propertiesOnly + ", 'false')");
        s.close();
    }
    
    
    
    
    /** 
     * Report exceptions, with special handling of SQLExceptions,
     * and exit.
     *
     * @param e an exception (Throwable)
     */
 protected  static void errorPrintAndExit(Throwable e) {
        if (e instanceof SQLException)
            SQLExceptionPrint((SQLException)e);
        else {
            System.out.println("A non-SQL error occurred.");
            e.printStackTrace();
        }
        System.exit(1);
    }
    
 
 
 /**
  * Iterate through a stack of SQLExceptions.
  *
  * @param sqle a SQLException
  */
protected  static void SQLExceptionPrint(SQLException sqle) {
     while (sqle != null) {
         System.out.println("\n---SQLException Caught---\n");
         System.out.println("SQLState:   " + (sqle).getSQLState());
         System.out.println("Severity: " + (sqle).getErrorCode());
         System.out.println("Message: " + (sqle).getMessage());
         sqle = sqle.getNextException();
     }
 }
 
	
	
	

}
