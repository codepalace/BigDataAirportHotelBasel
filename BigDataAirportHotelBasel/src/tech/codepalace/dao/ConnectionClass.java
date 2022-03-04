package tech.codepalace.dao;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version v.1.0.0 February.12.20022
 * @description Connection Class to manage the connection to the database
 *
 */
public class ConnectionClass {
	
	protected Connection conn;
	private final String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	
	//Info: Database URL depends on the year we are.
	private String urlDB;
	
	//String Name Database
	private String dbName;
	
	//Username and Password
	private static String dbOwner = "AHTBasel";
	private static String dbPass = "2021_%.APH/BaseltonSip$$3";
	
	private String connectionURL;
	
	//Encrzption Key for the DataBase
	protected String encryptionKey = "cdc42a1e38BfCd43";
	
	

		/**
		 * Constructor method receives the URL and the Database name of the database where we want to connect.
		 * @param urlDB
		 * @param dbName
		 */
		public ConnectionClass(String urlDB, String dbName) {
			
			
			//set the values
			this.urlDB = urlDB;
			this.dbName = dbName;
			
		
		/*
		 We check if the database does not exist calling the getUrlDataBase method for checking inside the getUrlDataBase method.
		
		 */
		 getUrlDataBase();
		
		}
		
		
		
		/**
		 * @description method to get the database url
		 */
		protected String getUrlDataBase() {
			
			File urlDataBaseFile = new File(this.urlDB + File.separator + this.dbName);
			
			if(urlDataBaseFile.exists()) {
		
				//The database exists now what we do is load it
//				JOptionPane.showMessageDialog(null, "URL DB STring: "+ urlDBCString);
				return urlDataBaseFile.getAbsolutePath();
			
			}else {
//				
				 
				 //The database does not exist, we call the createDataBase method to create the database 
				createDataBase();
			}
			return urlDataBaseFile.getAbsolutePath();
		}
		
		
		protected String getDbName() {
			return this.dbName;
		}
		
		
		
		
		
		
		/**
		 * @description method to create the database
		 */
		protected  void createDataBase() {
			 
			 try { 
				 
				 //We load the Database Driver
				 loadDriverDataBase();
				 
			
				/*
				 * connectionUrl receives jdbc:derby database motor + the urlDB(path where the database should be), the name of the database, 
				 * user, then we create an encrypted database with the selected encryption algorithm and very important our encryptionKey. 
				 */
				connectionURL = "jdbc:derby:" + this.urlDB + File.separator + this.dbName  
	                    + ";user=" + dbOwner + ";create=true;dataEncryption=true" + ";encryptionAlgorithm=DES/CBC/NoPadding;" + "encryptionKey=" + encryptionKey;
			
				
			//We get the connection inside the conn variable.	
			conn = DriverManager.getConnection(connectionURL);
	             
			
		          
		            try {
		             
		                DriverManager.getConnection("jdbc:derby:" + this.urlDB + File.separator + this.dbName   
			                    + ";user=" + dbOwner +  ";encryptionAlgorithm=DES/CBC/NoPadding;" + "encryptionKey=" + encryptionKey + ";shutdown=true");
		                
		                
		                turnOnBuiltInUsers(conn);
		                
		                System.out.println("\n\n-------------Clase Conexion---------");
		                System.out.println("Se creo la base de datos " + this.dbName + " con exito\n");
		                conn.close();
		                System.out.println("Closed connection");
		                
		                
		               
		                
		               
		                
		            } catch (SQLException se) {
		                if ( !se.getSQLState().equals("08006") ) {
		                	
		                    throw se;
		                }
		            }
				 

			
				 System.out.println("Database shut down normally");
			 }catch (SQLException e) {
				 
				 errorPrintAndExit(e);
				 
			} //fin de try catch de conexion
			 
			 
			 
			 // Restart database and confirm that unauthorized users cannot
		        //  access it
		       
		        connectionURL = "jdbc:derby:" + this.urlDB; 
		        System.out.println("Actual connectionURL: " + connectionURL);

		        
		 }
		
		
		
		
		
		
		
		/**
		 * @description loadDriverDataBase Method for loading the Database Driver.
		 */
		protected void loadDriverDataBase() {
			
			try {
				Class.forName(this.driver);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	    
	    

	  
	 
	  
	 /**
	  * @description this method will always be called whenever we are going to establish a connection with the database 
	  * @throws SQLException
	  */
	  public void conect() throws SQLException {
	    	
	 
		  //Load Driver.
	    	DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
	    	
	    	
	    	//connectionURL  uses a username and password to set the connection path. An encryption algorithm is specified, the connection passes through an encryption key.
	    	this.connectionURL = "jdbc:derby:" + this.urlDB + File.separator + this.dbName  
                  + ";user=" + dbOwner + ";password=" + dbPass  + ";encryptionAlgorithm=DES/CBC/NoPadding;" + "encryptionKey=" + encryptionKey;
		
	    //Connection get the DriverManager with the connectionURL
		conn = DriverManager.getConnection(connectionURL);
		
		  System.out.println("We are conected to " + this.connectionURL);
           
	    	
	    }
	  
	  
	  
	  
	  
	  
	  /**
	   * @description method used to close the connection to the database
	   * @throws SQLException
	   */
	  public void closeConnection() throws SQLException{
	    	
		  	/*exactly as in the above method we set the value of the variable connectionURL with the difference we call (shutdown=true) to shutdown the database connection.
		  that way we can give another user the opportunity to connect.
		  
		  Whenever we open a connection we close it at the same time as it is not allowed with derby multiple connections.
		  */
	    	this.connectionURL = "jdbc:derby:" + this.urlDB + File.separator + dbName + ";" 
	                   + ";user=" + dbOwner + ";password=" + dbPass + ";encryptionAlgorithm=AES/CBC/NoPadding;" + "encryptionKey=" + encryptionKey + ";shutdown=true";
	    	
	    	
	    	
	    	try {
		        
	            conn.close();
	            System.out.println("Closed connection");

	            
	            try {
	            	

	            	
	                String newURL = connectionURL;
	                DriverManager.getConnection(newURL);
	            } catch (SQLException se) {
	                if ( !se.getSQLState().equals("08006") ) {
	                    throw se;
	                }
	            }
	            System.out.println("Database shut down normally");

	            try {
	                DriverManager.getConnection("jdbc:derby:;shutdown=true");
	            } catch (SQLException se) {
	                if ( !se.getSQLState().equals("XJ015") ) {
	                    throw se;
	                }
	            }

	            System.out.println("Derby system shut down normally");
	        } catch (SQLException e) {
	            errorPrintAndExit(e);
	        }
	    	
	    	
	    }
	
	
	
	
	

}
