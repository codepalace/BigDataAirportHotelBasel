package tech.codepalace.model;

import javax.swing.table.DefaultTableModel;

import tech.codepalace.utility.TableKontakteUtilities;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description Class extended DefaultTableModel for the Table model our contact table.
 *
 */
@SuppressWarnings("serial")
public class KontakteTableModel extends DefaultTableModel {
	
	//String Array for the table headers. 
	String [] tableHeaders;
	
	//Object Array Data for the Data contained in the table
	Object[][] data;
	
	
	//Constructor with the parameter needed to build the TableModel(data and the table headers).
	
	public KontakteTableModel(Object[][] data, String[] tableHeaders) {
		
		//Call super Class
		//Constructs a DefaultTableModel and initializes the table by passing data and columnNames to the setDataVector method.
		super();
		
		this.tableHeaders = tableHeaders;
		this.data = data;
		
		//We send the parameters to the setDataVector method where the model will be fed.
		setDataVector(this.data, this.tableHeaders);
		
	}


	//This method set with column could be editable or not
	@Override
	public boolean isCellEditable(int row, int column) {
		
		//Declare the Column could be editable. 
		if(column!=TableKontakteUtilities.NAME && column!= TableKontakteUtilities.PHONE && 
				column!= TableKontakteUtilities.BEMERKUNGEN && column!=TableKontakteUtilities.ABTEILUNG
				) {
			
			return false;
		}else {
			return true;
		}
	}
	
	
	

}
