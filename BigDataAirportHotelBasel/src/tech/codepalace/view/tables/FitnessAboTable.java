package tech.codepalace.view.tables;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.JTableHeader;

import tech.codepalace.model.FitnessAbo;
import tech.codepalace.model.FitnessAboTableModel;
import tech.codepalace.utility.CellTableManager;
import tech.codepalace.utility.TableFitnessAboUtilities;
import tech.codepalace.utility.TableHeaderManager;


/**
 * @description Class FitnessAboTable to create Fitness subscription Table.
 * @author Antonio Estevez Gonzalez
 *
 */
public class FitnessAboTable {
	
	//Instance JTable for the Fitness subscription.
	private JTable fitnessAboJTable;
	
	//FitnessAbolist
	ArrayList<FitnessAbo>listFitnessAbo;
	
	//Instance FitnessAboTableModel.
	FitnessAboTableModel fitnessAboTableModel;
	
	
	//Constructor
	public FitnessAboTable() {
		
		init();
		
		buildTable();
		
	}
	
	
	
	
	
	@SuppressWarnings("serial")
	private void init() {
		
		//Initialize our fitnessAboJTable
		this.fitnessAboJTable = new JTable() {
			
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
			
	    this.fitnessAboJTable.setBackground(Color.WHITE);
		this.fitnessAboJTable.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		this.fitnessAboJTable.setOpaque(false);
		
		
		
		
	}
	
	
	
	
	
	
	
	/**
	 * @description Method to build our JTable
	 */
	private void buildTable() {
		
		this.listFitnessAbo = new ArrayList<FitnessAbo>();
		
		//Fitness ID, Name, Eintrittsdatum, Austrittsdatum, Betrag, Firma, Bemerkungen, Kontostatus(abgelaufen in rot, Aktiv en verde).
		/*
		 Create the array of the titles, we will add the titles that are the columns
		 */
		ArrayList<String> tableHeadersList=new ArrayList<>();
		
		tableHeadersList.add("ID");
		tableHeadersList.add("Fitness ID");
		tableHeadersList.add("Eintrittsdatum");
		tableHeadersList.add("Austrittsdatum");
		tableHeadersList.add("Betrag");
		tableHeadersList.add("Firma");
		tableHeadersList.add("Bemerkungen");
		tableHeadersList.add("Kontostatus");
		tableHeadersList.add("Kürzel MA");
		
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
		
		
		String information[][] = new String[listFitnessAbo.size()][tableHeadersList.size()];
		
		for (int x = 0; x < information.length; x++) {
			
			/*
			 *I know that the columns don't change, but the information that is displayed changes, so let's just say that the data that 
			 they will be presented change only by the x, but always keep the same column (title). That's why the TableParkingUtilities class is important, 
			 where the data corresponds to the column where it has to be displayed.

			 
			 */
			
			information[x][TableFitnessAboUtilities.ID] = listFitnessAbo.get(x).getId()+ "";
			information[x][TableFitnessAboUtilities.FITNESSID] = listFitnessAbo.get(x).getFitnessID()+ "";
			information[x][TableFitnessAboUtilities.NAME] = listFitnessAbo.get(x).getName()+ "";
			information[x][TableFitnessAboUtilities.EINTRITTSDATUM] = listFitnessAbo.get(x).getEintrittsDatum()+ "";
			information[x][TableFitnessAboUtilities.AUSTRITTSDATUM] = listFitnessAbo.get(x).getAustrittsDatum()+ "";
			information[x][TableFitnessAboUtilities.BETRAG] = listFitnessAbo.get(x).getBetrag()+ "";
			information[x][TableFitnessAboUtilities.FIRMA] = listFitnessAbo.get(x).getFirma()+ "";
			information[x][TableFitnessAboUtilities.BEMERKUNGEN] = listFitnessAbo.get(x).getBemerkungen()+ "";
			information[x][TableFitnessAboUtilities.KONTOSTATUS] = listFitnessAbo.get(x).getKontoStatus()+ "";
			information[x][TableFitnessAboUtilities.KUERZELMA] = listFitnessAbo.get(x).getAbkuerzungMA()+ "";
			
			

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
	fitnessAboTableModel=new FitnessAboTableModel(data, tableHeaders);


	
	/*
	 After we have the model created above, that model is assigned to the fundsachenJTable.
	 */
	fitnessAboJTable.setModel(fitnessAboTableModel);
	
	//Fitness ID, Name, Eintrittsdatum, Austrittsdatum, Betrag, Firma, Bemerkungen, Kontostatus(abgelaufen in rot, Aktiv en verde).
	
	//the type of data that will have the cells of each column defined respectively is assigned to validate its customization
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.ID).setCellRenderer(new CellTableManager("number"));
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.FITNESSID).setCellRenderer(new CellTableManager("text"));
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.NAME).setCellRenderer(new CellTableManager("text"));
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.EINTRITTSDATUM).setCellRenderer(new CellTableManager("number"));
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.AUSTRITTSDATUM).setCellRenderer(new CellTableManager("number"));
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.BETRAG).setCellRenderer(new CellTableManager("number"));
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.FIRMA).setCellRenderer(new CellTableManager("text"));
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.BEMERKUNGEN).setCellRenderer(new CellTableManager("important"));
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.KONTOSTATUS).setCellRenderer(new CellTableManager("text"));
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.KUERZELMA).setCellRenderer(new CellTableManager("text"));

	
	fitnessAboJTable.getTableHeader().setReorderingAllowed(false);
	fitnessAboJTable.setRowHeight(25);//cell size
	fitnessAboJTable.setGridColor(new java.awt.Color(0, 0, 0)); 
	//Define the length size for each column and its contents
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.ID).setPreferredWidth(10); //Nos quedamos aqui
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.FITNESSID).setPreferredWidth(70);
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.NAME).setPreferredWidth(200);
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.EINTRITTSDATUM).setPreferredWidth(120);
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.AUSTRITTSDATUM).setPreferredWidth(140);
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.BETRAG).setPreferredWidth(50);
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.FIRMA).setPreferredWidth(80);
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.BEMERKUNGEN).setPreferredWidth(150);
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.KONTOSTATUS).setPreferredWidth(40);
	fitnessAboJTable.getColumnModel().getColumn(TableFitnessAboUtilities.KUERZELMA).setPreferredWidth(40);

	
	//We set the ID Column width so it will be not visible but we can still having access to 
	//this column to work with the ID Column. So we can Then retrieve the data.
	fitnessAboJTable.getColumnModel().getColumn(0).setMinWidth(0);
	fitnessAboJTable.getColumnModel().getColumn(0).setMaxWidth(0);
	fitnessAboJTable.getColumnModel().getColumn(0).setWidth(0);
	
	//customize the header
	JTableHeader jtableHeader = fitnessAboJTable.getTableHeader();
    jtableHeader.setDefaultRenderer(new TableHeaderManager());
    fitnessAboJTable.setTableHeader(jtableHeader);
    
	
}



public JTable getJTable() {
	
	return fitnessAboJTable;
}
	
	

}
