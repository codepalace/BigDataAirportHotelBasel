package tech.codepalace.model;

import javax.swing.table.DefaultTableModel;

import tech.codepalace.utility.TableFundsachenUtilities;

/**
 * 
 * @author tonimacaroni
 * @description class to build the model Fundsachen table
 *
 */
@SuppressWarnings("serial")
public class FundsachenTableModel extends DefaultTableModel {
	
	
String [] tableHeaders;
	
	//Data contained in the table
	Object[][] data;
	
	/**
	 * Determines the model with which the table is to be built
	 * @param datos
	 * @param titulos
	 */
	
	
	//Class Constructor receiving the data and titles
		public FundsachenTableModel(Object[][] data, String[] tableHeaders) {
			super();
			this.tableHeaders = tableHeaders;
			this.data = data;
			
		
			//We send the parameters to the setDataVector method where the model will be fed.
			setDataVector(data, tableHeaders);
			
		}
		
		

			public FundsachenTableModel() {}
			
			
			/* We overwrite the isCellEditable method
			  Method that receives the row and column where the user is at the moment.

			 */
			public boolean isCellEditable(int row, int column) {
				
				
				/*
				 If columns are different from, BUCHUNGSNAME, AUTOKFZ, and ANREISEDATUM etc within the conditional hey cannot be editable 
				 if they are the ones we have chosen within the conditional if they are editable and we can modify them.
				 
				The Constants have the value of each column where they are inside the table
				 */
				if(column!=TableFundsachenUtilities.FUNDSACHEN && column!=TableFundsachenUtilities.FUNDORT && column!=TableFundsachenUtilities.INHABER
						&& column!=TableFundsachenUtilities.KISTNUMMER  && column!=TableFundsachenUtilities.RUECKGABE) {
					return false;
				}else {
					return true;
				}
				
			}

	

}
