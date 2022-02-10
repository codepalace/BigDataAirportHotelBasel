package tech.codepalace.model;

import javax.swing.table.DefaultTableModel;

import tech.codepalace.utility.TableParkingUtilities;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version v0.1.0
 * @description class to build the model of our parking table
 *
 */
public class ParkingTableModel extends DefaultTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	String [] tableHeaders;
	
	//Data contained in the table
	Object[][] data;
	
	/**
	 * Determines the model with which the table is to be built
	 * @param datos
	 * @param titulos
	 */
	
	//Class Constructor receiving the data and titles
	public ParkingTableModel(Object[][] data, String[] tableHeaders) {
		super();
		this.tableHeaders = tableHeaders;
		this.data = data;
		
	
		//We send the parameters to the setDataVector method where the model will be fed.
		setDataVector(data, tableHeaders);
		
	}
	
	

		public ParkingTableModel() {
			
		}
		
		
		/* We overwrite the isCellEditable method
		  Method that receives the row and column where the user is at the moment.

		 */
		public boolean isCellEditable(int row, int column) {
			
			
			/*
			 If columns are different from, BUCHUNGSNAME, AUTOKFZ, and ANREISEDATUM etc within the conditional hey cannot be editable 
			 if they are the ones we have chosen within the conditional if they are editable and we can modify them.
			 
			The Constants have the value of each column where they are inside the table
			 */
			if(column!=TableParkingUtilities.BUCHUNGSNAME && column!=TableParkingUtilities.AUTOKFZ && column!=TableParkingUtilities.ANREISEDATUM
					&& column!=TableParkingUtilities.ABREISEDATUM && column!=TableParkingUtilities.BUCHUNGSKANAL && column!=TableParkingUtilities.BEMERKUNGENG
					&& column!=TableParkingUtilities.SCHLUESSEL) {
				return false;
			}else {
				return true;
			}
			
		}


}