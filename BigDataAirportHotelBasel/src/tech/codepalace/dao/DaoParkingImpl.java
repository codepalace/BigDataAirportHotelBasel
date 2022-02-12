package tech.codepalace.dao;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import tech.codepalace.model.ParkingReservation;



/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version v0.1.0 February 12.2022
 *
 */
public class DaoParkingImpl extends ConnectionClass implements DAOParking {
	
	protected ConnectionClass connectionClass;
	
 	protected String[] names = { "TABLE" };
 	protected ResultSet result;
 	protected DatabaseMetaData metadata = null;
 	protected Statement statement = null;
 	protected String table_name = "Parking";
	
	
	
	public DaoParkingImpl(String urlDB, String dbName) {
		super(urlDB, dbName);
		
		
	}

	
	
	@Override
	public void checkTableParking() throws DaoException {

		//We connect to the Database
		try {
			this.conect();
			
			metadata = conn.getMetaData();
			result = metadata.getTables(null, null, table_name.toUpperCase(), new String[] {"TABLE"});
			
			 if(result.next()) {
	            	
	            	System.out.println("The table: " + this.table_name + " exists");
            	 
	            	 this.closeConnection();
	            	 
	            	 displayListParking();
	            }else {
	            	
	            	
	            	System.out.println("The table: " + this.table_name + " does not exist");
	            	
	            	
					 
					
					 //we proceed to create the table
					 String sql = "CREATE TABLE PARKING ( "
							 
							 + "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
							 + "idparking VARCHAR(12) NOT NULL,"
							 + "buchungsname VARCHAR(50) NOT NULL,"
							 + "autokfz VARCHAR(8) NOT NULL,"
							 + "anreisedatum DATE NOT NULL,"
							 + "abreisedatum DATE NOT NULL,"
							 + "anzahltagen INTEGER,"
							 + "betragparking decimal(6,2) NOT NULL,"
							 + "buchungskanal VARCHAR(40) NOT NULL,"
							 + "bemerkungen VARCHAR(255) NOT NULL,"
							 + "schluesselinhaus VARCHAR(5) NOT NULL,"
							 +	"verkaufer VARCHAR(20) NOT NULL,"
							 + "CONSTRAINT primary_key PRIMARY KEY (id)"
							 + ")";
			 
					 
					 statement = conn.createStatement();
					 
					 statement.executeUpdate(sql);
					 
					 System.out.println("The Table: " + this.table_name + " was successfully created");
					 
					 statement.close();
				
	           
	            //Very important close conneciton
	             this.closeConnection();
	             
	             
	           
	            }
			 
			 
			 
			 
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}

	@Override
	public void createTableParkingDataBase(ParkingReservation parkingReservation) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addNewParkingReservation(int lenghtParkingTableDataBase) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ParkingReservation> displayListParking() throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParkingReservation> displayParkingFoundLikeName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParkingReservation> displayParkingFoundLikeCarNumber(String carNumer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParkingReservation> displayParkingFoundLikeDate(String date) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ParkingReservation> displayParkingFoundLikeAnyEntry(String likeAnyEntry) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateParkingReservation(ParkingReservation parkingReservation) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteParkingReservation(int id) throws DaoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ParkingReservation> orderby(String orderBy) throws DaoException {
		// TODO Auto-generated method stub
		return null;
	}

}
