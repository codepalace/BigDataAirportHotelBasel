package tech.codepalace.model;

import tech.codepalace.view.frames.DataBaseGUI;

public class LogicModelDateChronologyCorrection {
	
	private DataBaseGUI dataBaseGUI;
	
	private String firstDate, secondDate; 
	private int selectedRow, selectedColumn;
	
	public LogicModelDateChronologyCorrection(DataBaseGUI dataBaseGUI, String firstDate, String secondDate, 
			int selectedRow, int selectedColumn) {
		
		this.dataBaseGUI = dataBaseGUI;
		this.firstDate = firstDate;
		this.secondDate = secondDate;
		this.selectedRow = selectedRow;
		this.selectedColumn = selectedColumn;
		
	}
	
	
	
	/**
	 * @description Method to reset the Dates values as String format dd.mm.yyyy in the JTable
	 */
	public void resetDates() {
		
		//If parkingTable is visible, is not null
		if(this.dataBaseGUI.parkingTable!=null) {
			
			//If the selected column is 4(Anreisedatum(Arrival Date))
			if(this.selectedColumn==4) {

				//set value for the column 4, selected row. The old value stored in the firstDate variable
				this.dataBaseGUI.parkingTable.setValueAt(firstDate, selectedRow, 4);
				
			//if column 5(Abreisedatum(Departure date))
			}else if(this.selectedColumn==5) {
				
				//set the value for the column 5 selected row. The old value stored in the secondDate variable
				this.dataBaseGUI.parkingTable.setValueAt(secondDate, selectedRow, 5);
			}
				
		}
		
	}
	
	

}
