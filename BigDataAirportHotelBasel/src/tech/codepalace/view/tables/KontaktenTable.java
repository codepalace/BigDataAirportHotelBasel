package tech.codepalace.view.tables;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.JTableHeader;

import tech.codepalace.model.Kontakte;
import tech.codepalace.model.KontaktenTableModel;
import tech.codepalace.utility.CellTableManager;
import tech.codepalace.utility.TableHeaderManager;
import tech.codepalace.utility.TableKontaktenUtilities;

/**
 * 
 * @author Antonio Estevez Gonzalez
 * @description Class KontaktenTable to create contacts Table.
 *
 */
public class KontaktenTable {

	//Instance JTable for our contacts. 
	private JTable kontaktJTable;
	
	//Contact list
	private ArrayList<Kontakte>listKontakten;
	
	
	//Instance KontaktenTableModel
	private KontaktenTableModel kontaktenTableModel;
	
	
	
	//Constructor for our Table
	public KontaktenTable() {
		
		//Call the init Method.
		init();
		
		//Call the method to build the table. 
		buildTable();
	}
	
	
	
	@SuppressWarnings("serial")
	private void init() {
		
		//Initialize our kontakJTable we use tooltiptext with the JTable.
		this.kontaktJTable = new JTable() {
			
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
		
		this.kontaktJTable.setBackground(Color.WHITE);
		this.kontaktJTable.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		this.kontaktJTable.setOpaque(false);
	}
	
	
	
	private void buildTable() {
		
		this.listKontakten = new ArrayList<Kontakte>();
		
		ArrayList<String> tableHeadersList = new ArrayList<>();
		
		tableHeadersList.add("ID");
		tableHeadersList.add("NAME / FIRMA");
		tableHeadersList.add("TELEFON");
		tableHeadersList.add("BEMERKUNGEN");
		tableHeadersList.add("ABTEILUNG");
		
		
	
		// columns are assigned to the array to be sent at the time of table
		// construction
		/*
		 * This list is converted to an Array String to be able to send it to the model,
		 * simply what is done is to tableHeaders[i]=tableHeadersList.get(i)
		 */
		String tableHeaders[] = new String[tableHeadersList.size()];
		for (int i = 0; i < tableHeaders.length; i++) {
			tableHeaders[i] = tableHeadersList.get(i);
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
		
		 we create an information array [listaPersonas.size][listParkingReservations.size] 
		 
		 
		 */
		
		
		String information[][] = new String[listKontakten.size()][tableHeadersList.size()];
		
		for (int x = 0; x < information.length; x++) {
			
			/*
			 *I know that the columns don't change, but the information that is displayed changes, so let's just say that the data that 
			 they will be presented change only by the x, but always keep the same column (title). That's why the TableParkingUtilities class is important, 
			 where the data corresponds to the column where it has to be displayed.

			 */
			
			information[x][TableKontaktenUtilities.ID] = listKontakten.get(x).getId()+ "";
			information[x][TableKontaktenUtilities.NAME] = listKontakten.get(x).getName()+ "";
			information[x][TableKontaktenUtilities.PHONE] = listKontakten.get(x).getPhone()+ "";
			information[x][TableKontaktenUtilities.BEMERKUNGEN] = listKontakten.get(x).getBemerkungen()+ "";
			information[x][TableKontaktenUtilities.ABTEILUNG] = listKontakten.get(x).getAbteilung()+ "";
			
			
			

		}
		
		return information;
	}


	private void buildTable(String[] tableHeaders, Object[][] data) {
		
		/*
		 We assign the model the data and titles, which are sent to the constructor of the Model class(KontaktenTableModel).
		 */
		this.kontaktenTableModel = new KontaktenTableModel(data, tableHeaders);
		
		//After we have the model created above, that model is assigned to the kontaktJTable.
		this.kontaktJTable.setModel(kontaktenTableModel);
		
		
		//the type of data that will have the cells of each column defined respectively is assigned to validate its customization
		kontaktJTable.getColumnModel().getColumn(TableKontaktenUtilities.ID).setCellRenderer(new CellTableManager("number"));
		kontaktJTable.getColumnModel().getColumn(TableKontaktenUtilities.NAME).setCellRenderer(new CellTableManager("text"));
		kontaktJTable.getColumnModel().getColumn(TableKontaktenUtilities.PHONE).setCellRenderer(new CellTableManager("number"));
		kontaktJTable.getColumnModel().getColumn(TableKontaktenUtilities.BEMERKUNGEN).setCellRenderer(new CellTableManager("text"));
		kontaktJTable.getColumnModel().getColumn(TableKontaktenUtilities.ABTEILUNG).setCellRenderer(new CellTableManager("text"));
		
		/*
		 * Sets whether the user can drag column headers to reorder columns.

		Parameters:
		reorderingAllowed true if the table view should allow reordering; otherwise false
		 */
		kontaktJTable.getTableHeader().setReorderingAllowed(false);
		
		kontaktJTable.setRowHeight(25);//cell size
		
		kontaktJTable.setGridColor(new java.awt.Color(0, 0, 0)); 
		
		//Define the length size for each column and its contents
		kontaktJTable.getColumnModel().getColumn(TableKontaktenUtilities.ID).setPreferredWidth(0);
		kontaktJTable.getColumnModel().getColumn(TableKontaktenUtilities.NAME).setPreferredWidth(200);
		kontaktJTable.getColumnModel().getColumn(TableKontaktenUtilities.PHONE).setPreferredWidth(150);
		kontaktJTable.getColumnModel().getColumn(TableKontaktenUtilities.BEMERKUNGEN).setPreferredWidth(550);
		kontaktJTable.getColumnModel().getColumn(TableKontaktenUtilities.ABTEILUNG).setPreferredWidth(50);
		
		
		//We set the ID Column width so it will be not visible but we can still having access to 
		//this column to work with the ID Column. So we can Then retrieve the data.
		kontaktJTable.getColumnModel().getColumn(0).setMinWidth(0);
		kontaktJTable.getColumnModel().getColumn(0).setMaxWidth(0);
		kontaktJTable.getColumnModel().getColumn(0).setWidth(0);
		
		
		//customize the header
		JTableHeader jtableHeader = kontaktJTable.getTableHeader();
	    jtableHeader.setDefaultRenderer(new TableHeaderManager());
	    kontaktJTable.setTableHeader(jtableHeader);
	    
	    
	}
	
	public JTable getJTable() {
		
		return kontaktJTable;
	}
	
	
}
