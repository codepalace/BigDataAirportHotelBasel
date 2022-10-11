package tech.codepalace.model;

import javax.swing.table.DefaultTableModel;

import tech.codepalace.utility.TableFitnessAboUtilities;

@SuppressWarnings("serial")
public class FitnessAboTableModel extends DefaultTableModel {

	
	String [] tableHeaders;
	
	//Data contained in the table
	Object[][] data;
	
 public FitnessAboTableModel(Object[][]data, String[] tableHeaders) {
	 super();
	 
	 	this.tableHeaders = tableHeaders;
		this.data = data;
		
		//We send the parameters to the setDataVector method where the model will be fed.
		setDataVector(data, tableHeaders);
		
		
 }

@Override
public boolean isCellEditable(int row, int column) {

	//Declare the Column could be editable
	if(column!=TableFitnessAboUtilities.NAME && column!=TableFitnessAboUtilities.EINTRITTSDATUM 
			&& column!=TableFitnessAboUtilities.AUSTRITTSDATUM && column!=TableFitnessAboUtilities.FIRMA 
			&& column!=TableFitnessAboUtilities.BEMERKUNGEN) {
		
		return false;
	
	}else {
		
		return true;
	}
	
}
 
	
}
