package tech.codepalace.view.tables;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.JTableHeader;

import tech.codepalace.model.Uebergabe;
import tech.codepalace.model.UebergabeTableModel;
import tech.codepalace.utility.CellTableManager;
import tech.codepalace.utility.TableFundsachenUtilities;
import tech.codepalace.utility.TableHeaderManager;
import tech.codepalace.utility.TableUebergabeUtilities;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description Class Table for the HandOver or Shift transfer information
 *
 */
public class UebergabeTable {
	
	private JTable uebergabeJTable;
	
	private ArrayList<Uebergabe>listUebergabe; //HangOver list.
	
	private UebergabeTableModel ubergabeTableModel;
	
	
	
	
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
		
		//columns are assigned to the array to be sent at the time of table construction
				/*
				 This list is converted to an Array String to be able to send it to the model, simply what is done is to 
				 tableHeaders[i]=tableHeadersList.get(i)
				 */
				String tableHeaders[] = new String[tableHeadersList.size()];
				for (int i = 0; i < tableHeaders.length; i++) {
					tableHeaders[i]=tableHeadersList.get(i);
				}
				/*we get the data from the list and save it in the array that is then sent to build the table,
				 * 
				 * Within the Object array, where I choose the information, of the data I'm going to show, 
				 * 
				 */
				
				Object[][] data = getDataArray(tableHeadersList);
				
				/*
				 These values are sent to the buildTable method where the model is filled
				 */
				
				
				buildTable(tableHeaders,data);
		
	}
	
	
	
	
private Object[][] getDataArray(ArrayList<String> tableHeadersList) {
		
		/*the array is created where the rows are dynamic as it corresponds to all parking reservations, while the columns are static corresponding to the columns defined by default
		 */
		
		/*
	
		What this method does is go through all the information and it will start assigning it to each position in the table.
		
		 we create an information array [listUebergabe.size][listParkingReservations.size] 
		 
		 
		 */
		
		
		String information[][] = new String[listUebergabe.size()][tableHeadersList.size()];
		
		for (int x = 0; x < information.length; x++) {
			
			/*
			 *I know that the columns don't change, but the information that is displayed changes, so let's just say that the data that 
			 they will be presented change only by the x, but always keep the same column (title). That's why the TableUebergabeUtilities class is important, 
			 where the data corresponds to the column where it has to be displayed.
			 
			

		
			 
			 In a Row.
			 [0][0] i'll have the ID
			 [0][1] i'll have the DATUM
			 [0][2] i'll have the INFORMATION
			 etc.
			 
			 etc so on with the rest of the information that is being filled in the array with which that information is going to be displayed.
			 
			 
			 */
			
			information[x][TableUebergabeUtilities.ID] = listUebergabe.get(x).getId()+ "";
			information[x][TableUebergabeUtilities.DATUM] = listUebergabe.get(x).getDatumUebergabe()+ "";
			information[x][TableUebergabeUtilities.INFORMATION] = listUebergabe.get(x).getInformation()+ "";
			information[x][TableUebergabeUtilities.KUERZELMA] = listUebergabe.get(x).getAbkuerzungMA()+ "";
			
			
			

		}
		
		return information;
	}



/**
 * @description With the titles and the information to be displayed, the model is created to be able to customize the table, assigning size of cells both in width and height as well as the types of data that it will be able to support.
 * @param titulos
 * @param data
 */
private void buildTable(String[] tableHeaders, Object[][] data) {
	
	/*
	 We assign the model the data and titles, which are sent to the constructor of the Model class(FundsachenTableModel).
	 */
	ubergabeTableModel=new UebergabeTableModel(data, tableHeaders);

	
	/*
	 After we have the model created above, that model is assigned to the fundsachenJTable.
	 */
	uebergabeJTable.setModel(ubergabeTableModel);
	
	
	
	//the type of data that will have the cells of each column defined respectively is assigned to validate its customization
	uebergabeJTable.getColumnModel().getColumn(TableUebergabeUtilities.ID).setCellRenderer(new CellTableManager("number"));
	uebergabeJTable.getColumnModel().getColumn(TableUebergabeUtilities.DATUM).setCellRenderer(new CellTableManager("number"));
	uebergabeJTable.getColumnModel().getColumn(TableUebergabeUtilities.INFORMATION).setCellRenderer(new CellTableManager("text"));
	uebergabeJTable.getColumnModel().getColumn(TableFundsachenUtilities.KUERSELMA).setCellRenderer(new CellTableManager("text"));

	
	uebergabeJTable.getTableHeader().setReorderingAllowed(false);
	uebergabeJTable.setRowHeight(25);//cell size
	uebergabeJTable.setGridColor(new java.awt.Color(0, 0, 0)); 
	//Define the length size for each column and its contents
	uebergabeJTable.getColumnModel().getColumn(TableUebergabeUtilities.ID).setPreferredWidth(10);
	uebergabeJTable.getColumnModel().getColumn(TableUebergabeUtilities.DATUM).setPreferredWidth(70);
	uebergabeJTable.getColumnModel().getColumn(TableUebergabeUtilities.INFORMATION).setPreferredWidth(200);
	uebergabeJTable.getColumnModel().getColumn(TableUebergabeUtilities.KUERZELMA).setPreferredWidth(40);

	
	//We set the ID Column width so it will be not visible but we can still having access to 
	//this column to work with the ID Column. So we can Then retrieve the data.
	uebergabeJTable.getColumnModel().getColumn(0).setMinWidth(0);
	uebergabeJTable.getColumnModel().getColumn(0).setMaxWidth(0);
	uebergabeJTable.getColumnModel().getColumn(0).setWidth(0);
	
	//customize the header
	JTableHeader jtableHeader = uebergabeJTable.getTableHeader();
    jtableHeader.setDefaultRenderer(new TableHeaderManager());
    uebergabeJTable.setTableHeader(jtableHeader);
    
	
}


public JTable getJTable() {
	
	return uebergabeJTable;
	
	
}



}
