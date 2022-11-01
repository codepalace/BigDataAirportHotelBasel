package tech.codepalace.view.tables;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.border.BevelBorder;

import tech.codepalace.model.Uebergabe;
import tech.codepalace.model.UebergabeTableModel;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description Class Table for the HandOver or Shift transfer information
 *
 */
public class UebergabeTable {
	
	private JTable uebergabeJTable;
	
	ArrayList<Uebergabe>listUebergabe; //HangOver list.
	
	UebergabeTableModel ubergabeTableModel;
	
	
	
	
	public UebergabeTable() {
		
		init();
		
		buildTable();
	}
	
	
	private void init() {
		
		this.uebergabeJTable = new JTable() {
			
			
			   

			//Implement table cell tool tips.           
	        public String getToolTipText(MouseEvent e) {
	            String tip = null;
	            java.awt.Point p = e.getPoint();
	            int rowIndex = rowAtPoint(p);
	            int colIndex = columnAtPoint(p);

	            try {
	                tip = getValueAt(rowIndex, colIndex).toString();
	            } catch (RuntimeException e1) {
	                //catch null pointer exception if mouse is over an empty line
	            }

	            return tip;
	        }
		};
		
		
		uebergabeJTable.setBackground(Color.WHITE);
		uebergabeJTable.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		uebergabeJTable.setOpaque(false);
		
		
	}
	
	
	
	
	private void buildTable() {
		
		this.listUebergabe = new ArrayList<Uebergabe>();
		
		
		/*
		 Create the array of the titles, we will add the titles that are the columns
		 */
		ArrayList<String> tableHeadersList=new ArrayList<>();
		
		tableHeadersList.add("ID");
		tableHeadersList.add("Datum");
		tableHeadersList.add("Info");
		tableHeadersList.add("Kürzel MA");
	}

}
