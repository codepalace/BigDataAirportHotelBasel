package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EtchedBorder;

import tech.codepalace.utility.RoundJTextField;
import tech.codepalace.utility.SelectCustomJComboBox;
import tech.codepalace.utility.SimpleRoundBorder;
import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;
import tech.codepalace.view.panels.TopPanelFitnessAbo;
import tech.codepalace.view.panels.TopPanelFundSachen;
import tech.codepalace.view.panels.TopPanelParking;
import tech.codepalace.view.panels.TopPanelTelefonbuch;
import tech.codepalace.view.panels.TopPanelUebergabe;
import tech.codepalace.view.tables.FitnessAboTable;
import tech.codepalace.view.tables.FundsachenTable;
import tech.codepalace.view.tables.KontakteTable;
import tech.codepalace.view.tables.ParkingTable;
import tech.codepalace.view.tables.UebergabeTable;


/**
 * 
 * @author tonimacaroni
 * @description class where the table with the data will be displayed, according to the data we want to visualize.
 */
@SuppressWarnings("serial")
public class DataBaseGUI extends JFrame {
	
	//Instance for the JPanel with Background-image.
	private PanelWithBackgroundOption panelWithBackgroundOption;
	
	private TopPanelParking topPanelParking;
	
	//Variable in case FundSachen will be displayed in the GUI Table. 
	private TopPanelFundSachen topPanelFundSachen;
	

	private TopPanelFitnessAbo topPanelFitnessAbo;
	
	private TopPanelUebergabe topPanelUebergabe;
	
	private TopPanelTelefonbuch topPanelTelefonbuch;
	
	
	//Variable to get the Display Size
	private Dimension screenSize;
	
	private JPanel topPanel, centerPanel, southPanel;
	
	 //Menu JButtons
	 public JButton btnHome, btnFundsachen, btnFitness, btnUerbergabe, btnPhonebook, btnLogout, btnNewParking, btnParking,
	 				btnNewFundsachen;

	//Jpanel for the user logged
	private JPanel loginPanel;
	
	//JLable for the user logged
	public JLabel loginUserLabel;
	
	//Variable to know which database table we should call.
	public String dataBaseApplication;
	
	//Instance JTable for the parkingTable
	public JTable parkingTable;
	
	public JTable fundsachenTable;
	
	public JTable fitnessAboTable;
	
	public JTable uebergabeTable;
	
	public JTable kontaktenTable;
		
	private JScrollPane scrollPane;
		
		
	public JLabel processStatusJLabel; 
		
	
	
	
	public ImageIcon exitDataBaseGUIImageIcon;
	
	
	//Array to include the options Jbuttons for the custom Dialog Box
	private Object [] options;
	
	
	//JPanels for the Custom Dialog Box and for the option buttons.
	private JPanel panelContainerMessage, panelButtons; 
		
	//Panel for the Message
	private JPanel panelTextCenter;
		
	//JLabel for the alert message.
	private JLabel message; 
	
	//Dialog Box To use when the user presses on the cross close the application.
	public JDialog exitDBGUI, noExitDBGUI;
	
	public JButton btnExitDBGUI, btnNoExitDBGUI;
	
	/*Variables for the new CellEditor to be inserted in the JTable fundsachenTable
	 * I am going to use a JComboBox with the choices to select so we can modify the
	 * cell content selecting a new choices.
	 * 
	 */
	public JComboBox<String> kisteNummerJComboBox;
	public JComboBox<String> schluesselInHausJComboBox;
	private String[] choices;
	
	//Variables for the Sales Channel. 
	public JComboBox<String> buchungskanalJComboBox; //Sales Channel JComboBox
	private String [] choicesBuchungskanal; //Array with the content for the buchungskanalJComboBox
	
	
	
	
	
	//Object to search results in the database
	//JTextField with rounded Border for the Search Entries
	public RoundJTextField searchText;
	
	//JComboBox to select how we search the result and JComboBox to display in JTable by selected Month
	public JComboBox<String> searchJComboBox, displayMothLostAndFoundJComboBox;
	
	public JPanel containerMonthSelect;
	
	//String Array for the JComboBox
	private String[] choicesSearchFundsachen, choicesSearchParking, choicesSelectMonthToBeDisplayed, choicesSearchFitnessAbo, 
					 choicesSearchUebergabe, choicesSearchTelefonbuch;
	
	
	//JButton for reload DataBase
	public JButton reloadDdJButton;
	
	//Variable para el popup fo Admin to delete entries from JTable and dataBase Lost and Found.
	public JPopupMenu popupMenu;
	
	//Menu Item for the popup to delete.
	public JMenuItem deleteItem = new JMenuItem("Ausgewählten Eintrag löschen");
	
	
	public DataBaseGUI(String dataBaseApplication) {
		
		this.dataBaseApplication = dataBaseApplication;

		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		
		// Initialize the JButtons as MyButton
		this.btnHome = new MyButton("/img/home.png");
		this.btnParking = new MyButton("/img/parking_btn2.png");
		this.btnFundsachen = new MyButton("/img/btn_fundsachen2.png");
		this.btnFitness = new MyButton("/img/btn_fitness_abo2.png");
		this.btnUerbergabe = new MyButton("/img/btn_uebergabe2.png");
		this.btnPhonebook = new MyButton("/img/btn_telefonbuch2.png");
		this.btnLogout = new MyButton("/img/btn_logout_125x70.png");
		this.btnNewFundsachen = new MyButton("/img/btn_funsachenplus.png");
		
		
		this.btnNewParking = new MyButton("/img/btn_new_parking.png");
		
		this.reloadDdJButton = new MyButton("/img/reload.png");
		
		
		this.btnHome.setPreferredSize(new Dimension(125,70));
		this.btnFundsachen.setPreferredSize(new Dimension(150,70));
		this.btnFitness.setPreferredSize(new Dimension(150,70));
		this.btnUerbergabe.setPreferredSize(new Dimension(150, 70));
		this.btnPhonebook.setPreferredSize(new Dimension(150,70));
		this.btnLogout.setPreferredSize(new Dimension(125,70));
		this.btnNewParking.setPreferredSize(new Dimension(150, 70));
		this.btnNewFundsachen.setPreferredSize(new Dimension(150, 70));
		
		this.reloadDdJButton.setPreferredSize(new Dimension(51, 39));
		
		this.reloadDdJButton.setToolTipText("Datenbank neu laden");
		
		
		// Initialize the searchText object 14 Column
		this.searchText = new RoundJTextField(14);
		this.searchText.setFont(new Font("Verdana", Font.BOLD, 18));
		this.searchText.setBackground(Color.GRAY);
		
		
		setUpFrame();
		
		
	}
	
	
	private void setUpFrame() {
		
		//We get the Display Size
		this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		
		//Set our JFrame 0,0, display-With, display-Heigh
		this.setBounds(0,0,screenSize.width, screenSize.height);
		
		//Frame fully mazimize
		this.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		//Resizable none
		this.setResizable(false);
		
		//Set icon GUI in case Windows Operating System.
		setIconImage (new ImageIcon(getClass().getResource("/img/iconoHotel.png")).getImage());
		
		
		//Background JPanel
		 this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		 
		 
		//setContentPane
		 setContentPane(this.panelWithBackgroundOption);
		 
		//set Layout Manager
		 this.panelWithBackgroundOption.setLayout(new BorderLayout());
		 
		 this.topPanel = new JPanel();
		 this.topPanel.setOpaque(false);
		 this.topPanel.setLayout(new BorderLayout());
		 
		 this.panelWithBackgroundOption.add(this.topPanel, BorderLayout.NORTH);
		 
		 
		 this.centerPanel = new JPanel(new BorderLayout());
			
			
		
		 
		 	this.panelWithBackgroundOption.add(this.centerPanel, BorderLayout.CENTER);
			
			this.southPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
			
			this.processStatusJLabel = new JLabel("Status: ");
	
			
			this.southPanel.add(processStatusJLabel);
			
			this.panelWithBackgroundOption.add(this.southPanel, BorderLayout.SOUTH);
			
			
			this.loginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
			this.loginPanel.setBackground(Color.GRAY);

			this.loginUserLabel = new JLabel("Benutzer: ");
			this.loginUserLabel.setFont(new Font("Verdana", Font.BOLD, 18));
			this.loginUserLabel.setForeground(Color.WHITE);
			
			this.loginPanel.add(this.loginUserLabel);
			
			this.topPanel.add(this.loginPanel, BorderLayout.SOUTH);
			
			this.btnExitDBGUI = new JButton("Ja");
			this.btnNoExitDBGUI = new JButton("Nein");
			
		
		//We evaluate the dataBAseApplication to know which elements we have to create for adding to the DataBaseGUI Class.
		//We also need to know that to display the correct database Table and correct data.
		switch (dataBaseApplication) {
			case "PARKING":
				
				
				this.setTitle("Langzeit Parking Airport Hotel Basel");
				
				//Initialize the choices for the JComboBox
				this.choicesSearchParking = new String[] {"Suchen nach ID-Parking", "Suchen nach Buchungsname", 
													"Suchen nach Auto-KFZ", "Suchen nach Anreisedatum"};
				
				
				//Initialize the JComboBox for the Search options.
				this.searchJComboBox = new JComboBox<String>(choicesSearchParking);
				
				this.topPanelParking = new TopPanelParking(this.btnHome, this.btnFundsachen, this.btnFitness, this.btnUerbergabe, this.btnPhonebook, this.btnLogout, 
						this.btnNewParking, this.searchText, this.searchJComboBox, this.reloadDdJButton);
				
				this.topPanel.add(this.topPanelParking, BorderLayout.NORTH);
				
				//the instance JTable parkingTable = new ParkingTable and we get for that the JTable with the getJTable() method.
				this.parkingTable = new ParkingTable().getJTable();
				
				
				//choices array initializes with the values we need 
				this.choices = new String[] {"Ja","Nein"};
				
				//choices array initializes with the values we need 
				this.schluesselInHausJComboBox = new JComboBox<String>(choices);
				
				/* Add the new Cell editor.
				 * 
				 * . for that we call the getColumnModel Method from the parkingJTable;
				 * . getColumn Method with the argument the column number where we are going to place the new Cell Editor;
				 * . setCellEditor Method with the argument new DefaultCellEditor and also for the argument will be passing the JComboBox we are going to use.
				 */
				this.parkingTable.getColumnModel().getColumn(10).setCellEditor(new DefaultCellEditor(schluesselInHausJComboBox));
				
				
				
				
				//JScrollPane for our parkingTable
				this.scrollPane = new JScrollPane(this.parkingTable);
				
				 //We add the scrollpane to the centerPanel and not the parkingTable(is already by the added by the scrollpane).
				 this.centerPanel.add(scrollPane);
				 
				 
				 //Initialize the choices for the Sales Channel so the user can select to modify the Sales Channel.
				 this.choicesBuchungskanal = new String[] {"Direkt", "Park, Sleep & Fly", "Parken-und-Fliengen.de", "HolidayExtras", "EasyJet", 
							"ParkScanner.de", "Parkandfly.de", "Andere Verkäufer"};
				 
				 	//Initialize the buchungskanalJComboBox with the choicesBuchungskanal values
					this.buchungskanalJComboBox = new JComboBox<String>(choicesBuchungskanal);
					
					//Now we set the CellEditor
					this.parkingTable.getColumnModel().getColumn(8).setCellEditor(new DefaultCellEditor(buchungskanalJComboBox));
				
				break;

			case "FUNDSACHEN": //In case Fundsachen we modify the view of this GUI
				
				this.setTitle("Fundsachen Airport Hotel Basel");
				
				
				//Initialize the choices for the JComboBox
				this.choicesSearchFundsachen = new String[] {"Suchen nach Datum", "Suchen nach Fundsachen", 
													"Suchen nach Namen", "Suchen nach Fundort"};
				
				
				//choices to display selected month
				this.choicesSelectMonthToBeDisplayed = new String[] {"Zeigen Nach Monat", "Januar", "Februar", "März", "April", "Mai", "Juni",
																	 "Juli", "August", "September", "Oktober", "November", 
																	 "Dezember", "Alle"};
				
				
				//Initialize the JComboBox for the Search options.
				this.searchJComboBox = new JComboBox<String>(choicesSearchFundsachen);

				
				
				
				this.containerMonthSelect = new JPanel();
				this.containerMonthSelect.setOpaque(false);
				this.containerMonthSelect.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));


				
				this.displayMothLostAndFoundJComboBox = new JComboBox<String>(this.choicesSelectMonthToBeDisplayed);
				
				this.displayMothLostAndFoundJComboBox.setPreferredSize(new Dimension(350, 40));
				
				this.displayMothLostAndFoundJComboBox.setFont(new Font("Verdana", Font.BOLD, 16));
				
				this.displayMothLostAndFoundJComboBox.setUI(SelectCustomJComboBox.createUI(this.containerMonthSelect, "calendar_icon.png", "pink_arrow_right.png"));
				
				this.displayMothLostAndFoundJComboBox.setBackground(Color.LIGHT_GRAY);
				
				this.displayMothLostAndFoundJComboBox.setBorder(new SimpleRoundBorder());
				
				
				this.containerMonthSelect.add(this.displayMothLostAndFoundJComboBox);
				
				this.topPanelFundSachen = new TopPanelFundSachen(this.btnHome, this.btnParking, this.btnFitness, this.btnPhonebook, this.btnLogout, this.btnNewFundsachen,
						this.searchText, this.searchJComboBox, this.reloadDdJButton);
				
				this.topPanel.add(this.topPanelFundSachen, BorderLayout.NORTH);
				
				this.fundsachenTable = new FundsachenTable().getJTable();
				
				//choices array initializes with the values we need 
				this.choices = new String[] {"1-Elektro Artikel","2-Schmuck / Brillen", "3-Kleidung",
						 "4-Kosmetik / Badezimmer", "5-Bücher", "6-Briefe / Karten jegliche Art",
						 "7-Sonstiges", "8-Kiste ohne Namen / Angaben"};
				
				

				//JComboBox initializes taking the choices values.
				this.kisteNummerJComboBox = new JComboBox<String>(choices);
				
				
				this.loginPanel.add(this.containerMonthSelect);
				
				/* Add the new Cell editor.
				 * 
				 * . for that we call the getColumnModel Method from the fundsachenTable;
				 * . getColumn Method with the argument the column number where we are going to place the new Cell Editor;
				 * . setCellEditor Method with the argument new DefaultCellEditor and also for the argument will be passing the JComboBox we are going to use.
				 */
				this.fundsachenTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(kisteNummerJComboBox));
				
				//Initialize the JPopUpMenu
				this.popupMenu = new JPopupMenu();
				
				//add the MenuItem. 
				popupMenu.add(deleteItem);
				
				
				//We set the PopUpMenu to our fundsacheTable object.
//				this.fundsachenTable.setComponentPopupMenu(popupMenu);	
				
				
				this.scrollPane = new JScrollPane(this.fundsachenTable);
				
				this.centerPanel.add(scrollPane);
				
				
				break;
			case "FITNESSABO":
				
				this.setTitle("Fitness Abonnement - Airport Hotel Basel");
			
				
				//Initialize the choices for the JComboBox
				choicesSearchFitnessAbo = new String[] {"suchen nach bla1", "suchen nach bla bla2", "suchen nach bla bla 3 lol"};
				
				//Initialize the JComboBox for the Search options.
				this.searchJComboBox = new JComboBox<String>(choicesSearchFitnessAbo);
				
				this.topPanelFitnessAbo = new TopPanelFitnessAbo(this.btnHome, this.btnFundsachen, this.btnParking, this.btnPhonebook, this.btnLogout, 
						this.searchText, this.searchJComboBox, this.reloadDdJButton);
				
				
				this.topPanel.add(this.topPanelFitnessAbo, BorderLayout.NORTH);
				
				//the instance JTable fitnessAboTable = new FitnessAboTable and we get for that the JTable with the getJTable() method.
				this.fitnessAboTable = new FitnessAboTable().getJTable();
			
				
				
				
				
				//JScrollPane for our fitnessAboTable
				this.scrollPane = new JScrollPane(this.fitnessAboTable);
				
				 //We add the scrollpane to the centerPanel and not the fitnessAboTable(is already by the added by the scrollpane).
				 this.centerPanel.add(scrollPane);
				 
				
				
				
				break;
				
				
			case "UEBERGABE":
				
				this.setTitle("Übergabe - Airport Hotel Basel");
				
				//Initialize the choices for the JComboBox
				this.choicesSearchUebergabe = new String[] {"Suchen nach bla bla uebergabe", "Suchen nach bla, bla 2 uebergaber", 
													"Suchen nach bla, bla 3", "Suchen nach bla bla 4"};
				
				
				//Initialize the JComboBox for the Search options.
				this.searchJComboBox = new JComboBox<String>(choicesSearchUebergabe);
				
				
				//Initialize the TopPanelUebergabe Instance
				this.topPanelUebergabe = new TopPanelUebergabe(this.btnHome, this.btnParking, this.btnFundsachen, this.btnFitness, 
																this.btnPhonebook, this.btnLogout, this.searchText, this.searchJComboBox,
																this.reloadDdJButton);
				
				this.topPanel.add(this.topPanelUebergabe, BorderLayout.NORTH);
				
				//Initialize the JTable calling the instance and getJTable to the our JTable.
				this.uebergabeTable = new UebergabeTable().getJTable();
				
				this.scrollPane = new JScrollPane(this.uebergabeTable);
				
				this.centerPanel.add(scrollPane);
				
				break;
				
				
				
			case "TELEFONBUCH":
				
				this.setTitle("Telefonbuch - Airport Hotel Basel");
				
				this.choicesSearchTelefonbuch = new String[] {"Suchen nach telefonbuch", "Suchen nach telefonbuch2", "Suchen nach telefonbuch3"};
				
				
				//Initialize the JComboBox for the Search options.
				this.searchJComboBox = new JComboBox<String>(choicesSearchTelefonbuch);
				
				//Initialize the topPanelTelefonbuch
				this.topPanelTelefonbuch = new TopPanelTelefonbuch(btnHome, btnParking, btnFundsachen, btnFitness, btnUerbergabe, 
																	btnLogout, searchText, searchJComboBox, reloadDdJButton);
				
				
				this.topPanel.add(this.topPanelTelefonbuch, BorderLayout.NORTH);
				
				//Initialize our JTable
				this.kontaktenTable = new KontakteTable().getJTable();
				
				this.scrollPane = new JScrollPane(this.kontaktenTable);
				
				this.centerPanel.add(scrollPane);
				
				
				break;
				
		}
		
		
		 closeParking(); 
		
	}
	
	
	
	/**
	 * @description method that will be called when the user presses on the cross close window
	 */
	private void closeParking() {
		
		try {
			this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
			addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					confirmClose();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
private void confirmClose() {
		
		
		
		exitDataBaseGUIImageIcon = new ImageIcon(getClass().getResource("/img/dbexit.png"));
		 
		
		
		
		 
		this.options = new Object[] {btnExitDBGUI, btnNoExitDBGUI};
			
		 this.panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
		 this.panelButtons.add(btnExitDBGUI);
		 this.panelButtons.add(btnNoExitDBGUI);
			
		 this.panelTextCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
		 
		 this.message = new JLabel("Sie sind sicher, dass Sie zum Hauptmenü zurückkehren möchten?");
			
		 this.panelTextCenter.add(this.message);
			
		 this.panelContainerMessage = new JPanel(new BorderLayout());
			
		 this.panelContainerMessage.add(this.panelButtons, BorderLayout.NORTH);
		 this.panelContainerMessage.add(this.panelTextCenter, BorderLayout.CENTER);
			
			
			this.exitDBGUI = new JOptionPane(this.panelContainerMessage, JOptionPane.OK_CANCEL_OPTION, JOptionPane.NO_OPTION,
					this.exitDataBaseGUIImageIcon, options, null).createDialog("Langzeit Parken Verlassen!");
			
			
			
			
			//Make noExitParking having the Focus.
			this.btnNoExitDBGUI.requestFocus();
			
			this.exitDBGUI.setVisible(true);
			
			this.exitDBGUI.dispose();
			
			
			
			
		
	}
	
	
	
	
	
		
		
	
	
	

}
