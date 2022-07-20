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

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;
import tech.codepalace.view.panels.TopPanelFundSachen;
import tech.codepalace.view.panels.TopPanelParking;
import tech.codepalace.view.tables.FundsachenTable;
import tech.codepalace.view.tables.ParkingTable;


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
	
//	private TopPanelFundSachen topPanelFundSachen;
	
	//Variable to get the Display Size
	private Dimension screenSize;
	
	private JPanel topPanel, centerPanel, southPanel;

	
	
	//JPanels for the Menu Buttons
//	private JPanel centerPanelButtons, containerPanelButton;
	
	 //Menu JButtons
	 public JButton btnHome, btnFundsachen, btnFitness, btnPhonebook, btnLogout, btnNewParking, btnParking,
	 				btnNewFundsachen;

	//Jpanel for the user logged
	private JPanel loginPanel;
	
	//JLable for the user logged
	public JLabel loginUserLabel;
	
	//Variable to know which database table we should call.
	private String dataBaseApplication;
	
	//Instance JTable for the parkingTable
	public JTable parkingTable;
	
	public JTable fundsachenTable;
		
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
	private String[] choices;
	

	
	
	
	
	
	public DataBaseGUI(String dataBaseApplication) {
		
		this.dataBaseApplication = dataBaseApplication;

		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		
		// Initialize the JButtons as MyButton
		this.btnHome = new MyButton("/img/btn_home.png");
		this.btnParking = new MyButton("/img/btn_parking.png");
		this.btnFundsachen = new MyButton("/img/btn_fundsachen2.png");
		this.btnFitness = new MyButton("/img/btn_fitness_abo.png");
		this.btnPhonebook = new MyButton("/img/btn_telefonbuch.png");
		this.btnLogout = new MyButton("/img/btn_logout_125x70.png");
		this.btnNewFundsachen = new MyButton("/img/btn_funsachenplus.png");
		
		
		this.btnNewParking = new MyButton("/img/btn_new_parking.png");
		
		
		this.btnHome.setPreferredSize(new Dimension(125,70));
		this.btnFundsachen.setPreferredSize(new Dimension(150,70));
		this.btnFitness.setPreferredSize(new Dimension(150,70));
		this.btnPhonebook.setPreferredSize(new Dimension(150,70));
		this.btnLogout.setPreferredSize(new Dimension(125,70));
		this.btnNewParking.setPreferredSize(new Dimension(150, 70));
		this.btnNewFundsachen.setPreferredSize(new Dimension(150, 70));
		
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
				
				this.topPanelParking = new TopPanelParking(this.btnHome, this.btnFundsachen, this.btnFitness, this.btnPhonebook, this.btnLogout, this.btnNewParking);
				
				this.topPanel.add(this.topPanelParking, BorderLayout.NORTH);
				
				//the instance JTable parkingTable = new ParkingTable and we get for that the JTable with the getJTable() method.
				this.parkingTable = new ParkingTable().getJTable();
				
				//JScrollPane for our parkingTable
				this.scrollPane = new JScrollPane(this.parkingTable);
				
				 //We add the scrollpane to the centerPanel and not the parkingTable(is already by the added by the scrollpane).
				 this.centerPanel.add(scrollPane);
				
				
				break;

			case "FUNDSACHEN": //In case Fundsachen we modify the view of this GUI
				
				this.setTitle("Fundsachen Airport Hotel Basel");
//				
				this.topPanelFundSachen = new TopPanelFundSachen(this.btnHome, this.btnParking, this.btnFitness, this.btnPhonebook, this.btnLogout, this.btnNewFundsachen);
				
				this.topPanel.add(this.topPanelFundSachen, BorderLayout.NORTH);
				
				this.fundsachenTable = new FundsachenTable().getJTable();
				
				//choices array initializes with the values we need 
				this.choices = new String[] {"Kistenummer wählen","1-Elektro Artikel","2-Schmuck / Brillen", "3-Kleidung",
						 "4-Kosmetik / Badezimmer", "5-Bücher", "6-Briefe / Karten jegliche Art",
						 "7-Sonstiges", "8-Kiste ohne Namen / Angaben"};

				//JComboBox initializes taking the choices values.
				this.kisteNummerJComboBox = new JComboBox<String>(choices);
				
				/* Add the new Cell editor.
				 * 
				 * . for that we call the getColumnModel Method from the fundsachenTable;
				 * . getColumn Method with the argument the column number where we are going to place the new Cell Editor;
				 * . setCellEditor Method with the argument new DefaultCellEditor and also for the argument will be passing the JComboBox we are going to use.
				 */
				this.fundsachenTable.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(kisteNummerJComboBox));
				
				this.scrollPane = new JScrollPane(this.fundsachenTable);
				
				this.centerPanel.add(scrollPane);
				//@ToDo create the fundSachenTable to import in the DataBaseGUI, scrollPane receive the fundaschenTable and we center to the GUI Panel.
				
				
				break;
			default:
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
