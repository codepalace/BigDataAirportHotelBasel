package tech.codepalace.view.tables;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.JTableHeader;

import tech.codepalace.model.Fundgegenstand;
import tech.codepalace.model.FundsachenTableModel;
import tech.codepalace.utility.CellTableManager;
import tech.codepalace.utility.TableFundsachenUtilities;
import tech.codepalace.utility.TableHeaderManager;

/**
 * @description Class to create Fundsachen Table
 * @author tonimacaroni
 *
 */
public class FundsachenTable {
	
	private JTable fundsachenJTable;
	
	ArrayList<Fundgegenstand>listFundsachen; //Fundsachenlist
	
	FundsachenTableModel fundsachenTableModel;
	

	public FundsachenTable() {
		
		init();
		
		buildTable();
	}
	
	@SuppressWarnings("serial")
	private void init() {
		
		this.fundsachenJTable = new JTable(){

		      

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
		
		
		this.fundsachenJTable.setBackground(Color.WHITE);
		this.fundsachenJTable.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		this.fundsachenJTable.setOpaque(false);
		
		
	}
	
	
	private void buildTable() {
		
		this.listFundsachen = new ArrayList<Fundgegenstand>();
		
		/*
		 Create the array of the titles, we will add the titles that are the columns
		 */
		ArrayList<String> tableHeadersList=new ArrayList<>();
		
		tableHeadersList.add("ID");
		tableHeadersList.add("Datum");
		tableHeadersList.add("Fundsachen");
		tableHeadersList.add("Zimmer/Fundort");
		tableHeadersList.add("Name des inhaber");
		tableHeadersList.add("Kiste Nr");
		tableHeadersList.add("Kiste Name");
		tableHeadersList.add("Rückgabe");
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
		
		 we create an information array [listaPersonas.size][listParkingReservations.size] 
		 
		 
		 */
		
		
		String information[][] = new String[listFundsachen.size()][tableHeadersList.size()];
		
		for (int x = 0; x < information.length; x++) {
			
			/*
			 *I know that the columns don't change, but the information that is displayed changes, so let's just say that the data that 
			 they will be presented change only by the x, but always keep the same column (title). That's why the TableParkingUtilities class is important, 
			 where the data corresponds to the column where it has to be displayed.
			 
			

		
			 
			 In a Row.
			 [0][0] i'll have the DATUM
			 [0][1] i'll have the FUNDSACHE
			 [0][2] i'll have the FUNDORT
			 etc.
			 
			 etc so on with the rest of the information that is being filled in the array with which that information is going to be displayed.
			 
			 
			 
			 
			 */
			
			information[x][TableFundsachenUtilities.ID] = listFundsachen.get(x).getId()+ "";
			information[x][TableFundsachenUtilities.DATUM] = listFundsachen.get(x).getDateItemsWasFound()+ "";
			information[x][TableFundsachenUtilities.FUNDSACHEN] = listFundsachen.get(x).getFoundItems()+ "";
			information[x][TableFundsachenUtilities.FUNDORT] = listFundsachen.get(x).getFundort()+ "";
			information[x][TableFundsachenUtilities.INHABER] = listFundsachen.get(x).getInhaber()+ "";
			information[x][TableFundsachenUtilities.KISTNUMMER] = listFundsachen.get(x).getKisteNummer()+ "";
			information[x][TableFundsachenUtilities.KISTENAME] = listFundsachen.get(x).getKisteName()+ "";
			information[x][TableFundsachenUtilities.RUECKGABE] = listFundsachen.get(x).getRueckGabe()+ "";
			information[x][TableFundsachenUtilities.KUERSELMA] = listFundsachen.get(x).getAbkuerzungMA()+ "";
			
			

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
		fundsachenTableModel=new FundsachenTableModel(data, tableHeaders);

		
		/*
		 After we have the model created above, that model is assigned to the fundsachenJTable.
		 */
		fundsachenJTable.setModel(fundsachenTableModel);
		
		
		
		//the type of data that will have the cells of each column defined respectively is assigned to validate its customization
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.ID).setCellRenderer(new CellTableManager("number"));
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.DATUM).setCellRenderer(new CellTableManager("number"));
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDSACHEN).setCellRenderer(new CellTableManager("text"));
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDORT).setCellRenderer(new CellTableManager("text"));
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.INHABER).setCellRenderer(new CellTableManager("text"));
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTNUMMER).setCellRenderer(new CellTableManager("number"));
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTENAME).setCellRenderer(new CellTableManager("text"));
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.RUECKGABE).setCellRenderer(new CellTableManager("important"));
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.KUERSELMA).setCellRenderer(new CellTableManager("text"));

		
		fundsachenJTable.getTableHeader().setReorderingAllowed(false);
		fundsachenJTable.setRowHeight(25);//cell size
		fundsachenJTable.setGridColor(new java.awt.Color(0, 0, 0)); 
		//Define the length size for each column and its contents
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.ID).setPreferredWidth(10);
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.DATUM).setPreferredWidth(70);
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDSACHEN).setPreferredWidth(200);
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.FUNDORT).setPreferredWidth(120);
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.INHABER).setPreferredWidth(140);
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTNUMMER).setPreferredWidth(50);
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.KISTENAME).setPreferredWidth(80);
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.RUECKGABE).setPreferredWidth(150);
		fundsachenJTable.getColumnModel().getColumn(TableFundsachenUtilities.KUERSELMA).setPreferredWidth(40);

		
		//We set the ID Column width so it will be not visible but we can still having access to 
		//this column to work with the ID Column. So we can Then retrieve the data.
		fundsachenJTable.getColumnModel().getColumn(0).setMinWidth(0);
		fundsachenJTable.getColumnModel().getColumn(0).setMaxWidth(0);
		fundsachenJTable.getColumnModel().getColumn(0).setWidth(0);
		
		//customize the header
		JTableHeader jtableHeader = fundsachenJTable.getTableHeader();
	    jtableHeader.setDefaultRenderer(new TableHeaderManager());
	    fundsachenJTable.setTableHeader(jtableHeader);
	    
		
	}



	public JTable getJTable() {
		
		return fundsachenJTable;
		
		
	}
	
	

}
