package tech.codepalace.view.tables;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.border.BevelBorder;
import javax.swing.table.JTableHeader;

import tech.codepalace.model.ParkingReservation;
import tech.codepalace.model.ParkingTableModel;
import tech.codepalace.utility.CellTableManager;
import tech.codepalace.utility.TableHeaderManager;
import tech.codepalace.utility.TableParkingUtilities;

/**
 * @version v0.1.0
 * @author Antonio Estevez Gonzalez
 * @description class to create parking tables
 *
 */
public class ParkingTable {

	private JTable parkingJTable;
	
	ArrayList<ParkingReservation> listParkingReservations; //Parking reservations list
	
	ParkingTableModel parkingTableModel; //model MyTableModel
	
	
	
public ParkingTable() {
		
		init();
		
		buildTable();
	}



@SuppressWarnings("serial")
private void init() {
	
	parkingJTable = new JTable(){

      

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
	
	
	
	
	
	
	
	parkingJTable.setBackground(Color.WHITE);
	parkingJTable.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
	
	
	parkingJTable.setOpaque(false);
	
	
}


private void buildTable() {
	
	/*for the moment we create an empty arraylist.
	 * 
	 * we have not called the database to fill in the table with the data. 
	 * 
	 * we will create an empty table to add to our application, later when the data is read from the database, the recovered data will be imported into the table.
	 * 
	 * 
	 * 
	 */
	listParkingReservations = new ArrayList<ParkingReservation>();
	

	
	/*
	 Create the array of the titles, we will add the titles that are the columns
	 */
	ArrayList<String> tableHeadersList=new ArrayList<>();
	
	tableHeadersList.add("ID-Parking");
	tableHeadersList.add("Buchungsname");
	tableHeadersList.add("Auto-KFZ");
	tableHeadersList.add("Anreisedatum");
	tableHeadersList.add("Abreisedatum");
	tableHeadersList.add("Tagen");
	tableHeadersList.add("Betrag");
	tableHeadersList.add("Buchungskanal");
	tableHeadersList.add("Bemerkungen");
	tableHeadersList.add("Schlüssel?");
	tableHeadersList.add("Kürsel MA");
	
	
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
	 toni: 
	 Este metodo lo que hace es recorrer toda la informacion y va a comenzar a asignarla en cada posicion de la tabla. 
	
	 we create an information array [listaPersonas.size][listParkingReservations.size] 
	 
	 
	 */
	
	
	String information[][] = new String[listParkingReservations.size()][tableHeadersList.size()];
	
	for (int x = 0; x < information.length; x++) {
		
		/*
		 *I know that the columns don't change, but the information that is displayed changes, so let's just say that the data that 
		 they will be presented change only by the x, but always keep the same column (title). That's why the TableParkingUtilities class is important, 
		 where the data corresponds to the column where it has to be displayed.
		 
		

		 ya se que corresponde a 0, .NOMBRE a 1, etc.
		 
		 In a Row.
		 [0][0] i'll have the IDPARKING
		 [0][1] i'll have the BUCHUNGSNAME
		 [0][2] i'll have the AUTOKFZ
		 etc.
		 
		 etc asi sucesivamente con el resto de informacion que se va rellenando en el array con el que se va a mostrar esa informacion.
		 
		 
		 
		 
		 */
		
		information[x][TableParkingUtilities.IDPARKING] = listParkingReservations.get(x).getIdParking()+ "";
		information[x][TableParkingUtilities.BUCHUNGSNAME] = listParkingReservations.get(x).getBuchungsname()+ "";
		information[x][TableParkingUtilities.AUTOKFZ] = listParkingReservations.get(x).getAutoKFZ()+ "";
		information[x][TableParkingUtilities.ANREISEDATUM] = listParkingReservations.get(x).getAnreiseDatum()+ "";
		information[x][TableParkingUtilities.ABREISEDATUM] = listParkingReservations.get(x).getAbreiseDatum()+ "";
		information[x][TableParkingUtilities.ANZAHLDESTAGES] = listParkingReservations.get(x).getAnzahlTagen()+ "";
		information[x][TableParkingUtilities.BETRAG] = listParkingReservations.get(x).getBetragParking()+ "";
		information[x][TableParkingUtilities.BUCHUNGSKANAL] = listParkingReservations.get(x).getBuchungsKanal()+ "";
		information[x][TableParkingUtilities.BEMERKUNGENG] = listParkingReservations.get(x).getBemerkungen()+ "";
		information[x][TableParkingUtilities.SCHLUESSEL] = listParkingReservations.get(x).getSchluesselInHaus()+ "";
		information[x][TableParkingUtilities.KUERSELMA] = listParkingReservations.get(x).getAbkuerzungMA()+ "";
		

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
	 We assign the model the data and titles, which are sent to the constructor of the Model class(ParkingTableModel).
	 */
	parkingTableModel=new ParkingTableModel(data, tableHeaders);

	
	/*
	 After we have the model created above, that model is assigned to the parkingJTable.
	 */
	parkingJTable.setModel(parkingTableModel);
	
	
	
	//the type of data that will have the cells of each column defined respectively is assigned to validate its customization
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.IDPARKING).setCellRenderer(new CellTableManager("texto"));
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSNAME).setCellRenderer(new CellTableManager("texto"));
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.AUTOKFZ).setCellRenderer(new CellTableManager("texto"));
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.ANREISEDATUM).setCellRenderer(new CellTableManager("number"));
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.ABREISEDATUM).setCellRenderer(new CellTableManager("number"));
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.ANZAHLDESTAGES).setCellRenderer(new CellTableManager("number"));
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.BETRAG).setCellRenderer(new CellTableManager("number"));
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSKANAL).setCellRenderer(new CellTableManager("texto"));
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.BEMERKUNGENG).setCellRenderer(new CellTableManager("texto"));
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.SCHLUESSEL).setCellRenderer(new CellTableManager("texto"));
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.KUERSELMA).setCellRenderer(new CellTableManager("texto"));

	
	parkingJTable.getTableHeader().setReorderingAllowed(false);
	parkingJTable.setRowHeight(25);//cell size
	parkingJTable.setGridColor(new java.awt.Color(0, 0, 0)); 
	//Define the length size for each column and its contents
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.IDPARKING).setPreferredWidth(100);
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSNAME).setPreferredWidth(150);
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.AUTOKFZ).setPreferredWidth(120);
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.ANREISEDATUM).setPreferredWidth(125);
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.ABREISEDATUM).setPreferredWidth(130);
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.ANZAHLDESTAGES).setPreferredWidth(80);
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.BETRAG).setPreferredWidth(100);
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.BUCHUNGSKANAL).setPreferredWidth(150);
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.BEMERKUNGENG).setPreferredWidth(120);
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.SCHLUESSEL).setPreferredWidth(80);
	parkingJTable.getColumnModel().getColumn(TableParkingUtilities.KUERSELMA).setPreferredWidth(80);
	
	
	//customize the header
	JTableHeader jtableHeader = parkingJTable.getTableHeader();
    jtableHeader.setDefaultRenderer(new TableHeaderManager());
    parkingJTable.setTableHeader(jtableHeader);
    
	
}



public JTable getJTable() {
	
	return parkingJTable;
	
	
}

	
	
}
