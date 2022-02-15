package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;

import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;
import tech.codepalace.view.tables.ParkingTable;


/**
 * 
 * @author Antonio Estevez Gonzalez
 * @version v0.1.0 Jan 30.2022 10:40PM
 * 
 * @description Class for the application of parking databases
 *
 */
public class AHBParking extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	
	
	//Variable to get the Display Size
	private Dimension screenSize;
	
	private JPanel topPanel, centerPanel, southPanel;
	
	private JLabel cadillac;
	
	private Image myImage;
	
	public ImageIcon exitParkingImageIcon;
	
	//Dialog Box To use when the user presses on the cross close the application.
	public JDialog exitParkingDialog, noExitParkingDialog;
	
	//Option Buttons
	public JButton exitParking, noExitParking;
	
	//Array to include the options Jbuttons for the custom Dialog Box
	private Object [] options;
	
	//JPanels for the Custom Dialog Box and for the option buttons.
	private JPanel panelContainerMessage, panelButtons; 
	
	//Panel for the Message
	private JPanel panelTextCenter;
	
	//JLabel for the alert message.
	private JLabel message; 
	
	//JPanels for the Menu Buttons
	 private JPanel centerPanelButtons, containerPanelButton;
	
	 //Menu JButtons
	 public JButton btnHome, btnFundsachen, btnFitness, btnPhonebook, btnLogout;
	 
	//Border for the centerPanelButtons
	private Border etchedBorder;
	
	//Jpanel for the user logged
	private JPanel loginPanel;
	
	//JLable for the user logged
	public JLabel loginUserLabel;
	
	//Instance JTable for the parkingTable
	private JTable parkingTable;
	
	private JScrollPane scrollPane;
	
	
	
	
	public AHBParking() {
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		
		this.exitParking = new JButton("Ja");
		this.noExitParking = new JButton("Nein");
	
		// Initialize the JButtons as MyButton
		this.btnHome = new MyButton("/img/btn_home.png");
		this.btnFundsachen = new MyButton("/img/btn_fundsachen.png");
		this.btnFitness = new MyButton("/img/btn_fitness_abo.png");
		this.btnPhonebook = new MyButton("/img/btn_telefonbuch.png");
		this.btnLogout = new MyButton("/img/btn_logout.png");
		
		//We initialize the panel of the buttons
		this.centerPanelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		//Initialize the Border
		this.etchedBorder = BorderFactory.createEtchedBorder();
		
		this.centerPanelButtons.setBorder(etchedBorder);
		
		this.centerPanelButtons.setOpaque(false);
		
		this.containerPanelButton = new JPanel(new BorderLayout());
		
		
		
		setupFrame();
		
		
	}
	
	
	/**
	 * @description method to configure our JFrame
	 */
	private void setupFrame() {
		
		this.setTitle("Langzeit Parking Airport Hotel Basel");
		
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
		 
		
		 closeParking();
		 
		 //add the elements
		 this.addElementsToPanel();
		
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
//			this.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void confirmClose() {
		
		 this.exitParkingImageIcon = new ImageIcon(getClass().getResource("/img/exit_parking.png"));
		 
		 this.options = new Object[] {this.exitParking, this.noExitParking};
			
			this.panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
			
			this.panelButtons.add(this.exitParking);
			this.panelButtons.add(this.noExitParking);
			
			this.panelTextCenter = new JPanel(new FlowLayout(FlowLayout.CENTER));
			this.message = new JLabel("Sind Sie sicher, dass Sie die Langzeitparken schließen möchten?");
			
			this.panelTextCenter.add(message);
			
			this.panelContainerMessage = new JPanel(new BorderLayout());
			
			this.panelContainerMessage.add(this.panelButtons, BorderLayout.NORTH);
			this.panelContainerMessage.add(this.panelTextCenter, BorderLayout.CENTER);
			
			
			this.exitParkingDialog = new JOptionPane(this.panelContainerMessage, JOptionPane.OK_CANCEL_OPTION, JOptionPane.NO_OPTION,
					this.exitParkingImageIcon, this.options, null).createDialog("Langzeit Parken Verlassen!");
			
			
			
			
			//Make noExitParking having the Focus.
			this.noExitParking.requestFocus();
			
			this.exitParkingDialog.setVisible(true);
			this.exitParkingDialog.dispose();
		
	}
	
	
	
	
	
public void addElementsToPanel() {
		
		this.topPanel = new JPanel();
		this.topPanel.setOpaque(false);
		this.topPanel.setLayout(new BorderLayout());
		
		this.cadillac = new JLabel(setImageJLabel("/img/cadillac_oldtimer350x200.png"));
		this.cadillac.setPreferredSize(new Dimension(350,200));
		
		this.topPanel.add(this.cadillac, BorderLayout.WEST);
		
		
		
		this.panelWithBackgroundOption.add(this.topPanel, BorderLayout.NORTH);
		
		this.centerPanel = new JPanel(new BorderLayout());
		
		this.panelWithBackgroundOption.add(this.centerPanel);
		
		
		this.btnHome.setPreferredSize(new Dimension(150,70));
		this.btnFundsachen.setPreferredSize(new Dimension(150,70));
		this.btnFitness.setPreferredSize(new Dimension(150,70));
		this.btnPhonebook.setPreferredSize(new Dimension(150,70));
		this.btnLogout.setPreferredSize(new Dimension(150,70));
		

		
		this.containerPanelButton.setOpaque(false);
		this.containerPanelButton.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.containerPanelButton.add(centerPanelButtons, BorderLayout.SOUTH);
		
		

		this.centerPanelButtons.add(btnHome);
		this.centerPanelButtons.add(btnFundsachen);
		this.centerPanelButtons.add(btnFitness);
		this.centerPanelButtons.add(btnPhonebook);
		this.centerPanelButtons.add(btnLogout);
		
		
		this.topPanel.add(containerPanelButton, BorderLayout.CENTER);
		
		
		this.loginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		this.loginPanel.setBackground(Color.GRAY);
		
		
		this.loginUserLabel = new JLabel("Benutzer: ");
		this.loginUserLabel.setFont(new Font("Verdana", Font.BOLD, 18));
		this.loginUserLabel.setForeground(Color.WHITE);
		
		this.loginPanel.add(this.loginUserLabel);
		
		this.topPanel.add(this.loginPanel, BorderLayout.SOUTH);
		
		
		//the instance JTable parkingTable = new ParkingTable and we get for that the JTable with the getJTable() method.
		this.parkingTable = new ParkingTable().getJTable();
		
		//JScrollPane for our parkingTable
		this.scrollPane = new JScrollPane(this.parkingTable);
		
		//We add the scrollpane to the centerPanel and not the parkingTable(is already by the added by the scrollpane).
		this.centerPanel.add(scrollPane);
		
		
		this.panelWithBackgroundOption.add(this.centerPanel, BorderLayout.CENTER);
		
		
	}





public ImageIcon setImageJLabel(String img) {


	this.myImage = new ImageIcon(getClass().getResource(img)).getImage();


     return new ImageIcon(this.myImage);

}



}
