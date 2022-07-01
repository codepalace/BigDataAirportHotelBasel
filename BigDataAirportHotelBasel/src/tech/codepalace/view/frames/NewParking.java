package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tech.codepalace.utility.PlaceHolderTextField;
import tech.codepalace.view.panels.PanelWithBackgroundOption;


/**
 * 
 * @author tonimacaroni
 * @description NewParking Class extended from JDialog to block the GUI running in background.
 * <p>We use this class to enter a new Parking-Reservation.</p>
 */
public class NewParking extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//Variable use to generate a Parking Id number. J = Year in German, The Year in Number for example 2022, and the number of rows we have in 
	//the table + 1 (J202215) as example.
	private int tableCounter;
	
	//Variable to Know who from the Team has entered the information for the new Parking Reservation.
	private static String abkuerzungMA;
	
	//Our Panel with Background Image
	private PanelWithBackgroundOption panelWithBackgroundOption;
	
	private JPanel entriesPanel, jcomboPanel;
	
	private JLabel chevy57;
	
	//We use a GridBagLayout in this JDialog.
	private GridBagLayout gbl;
 	private GridBagConstraints gbc;
 	
 	//LocalDateTime to get the todays Date. It will be used to generate the Parking ID.
 	private LocalDateTime now = LocalDateTime.now(ZoneId.ofOffset("UTC", ZoneOffset.ofHours(+2)));
 	
 	
 	public JLabel idParkingJLabel, idParkingGenerated;
 	
 	private JLabel buchungsNameJLabel;
 	public JTextField buchungsNameJTextField;
 	
 	private JLabel autoKFZJLabel;
 	public JTextField autoKFZJTextField;
 	
 	private JLabel anreiseDatumJLabel; 
 	public PlaceHolderTextField anreiseDatumPlaceHolderTextField;
 	
 	private JLabel abreiseDatumJLabel;
 	public PlaceHolderTextField abreiseDatumPlaceholderTextField;
 
 	
 	public JLabel tagenJLabel, tagenGeneratedJLabel;
 	
 	public JLabel betragJLabel, betragGeneratedJLabel;
 	
 	private JLabel buchungsKanalJLabel;
 	public JTextField buchunsKanalJTextField;
 	
 	private JLabel bemerkungenJLabel;
 	public JTextField bemerkungenJTextField;
 	
 	private JLabel schluesselJLabel;
 	public JComboBox<String> schluesselBox;
 	private String[] choices;
 	
 	public JLabel abkuerzungMAJLabel, abkuerzungMAGeneratedJLabel;
 	
 	public JButton saveParkingReservation, cancelParkingReservation;
 	
 	private JPanel buttonPanel;
 	

 	
 	
 	public JButton ja = new JButton("Ja");
 	public JButton nein = new JButton("Nein");
 	
 	public JFrame frame = new JFrame();
	private JOptionPane optionPane = new JOptionPane();
	
	private ImageIcon icon = new ImageIcon(getClass().getResource("/img/dialogo.png"));
	
	public JDialog dialog;
	
	
	

	/**
	 * 
	 * @param dataBaseGUI
	 * @param modal
	 * @param tableCounter
	 * @param abkuerzungMA
	 * @description The constructor received the DataBaseGUI to be blocked in Background. 
	 * <p>The boolean modal variable to set blocked the DataBaseGUI or not.</p>
	 * <p>The tableCounter to know how much entries we have up to now.</p>
	 * <p>String abkuerzungMA, to know who from the team is treating the information.</p> 
	 * 
	 */
	public NewParking(DataBaseGUI dataBaseGUI, boolean modal, int tableCounter, String abkuerzungMA) {
		
		//We call super and the DataBaseGUI as argument so we specify that dataBaseGUI is the Object that we send that will be blocked 
		super(dataBaseGUI, modal);
		
		this.tableCounter = tableCounter;
		NewParking.abkuerzungMA = abkuerzungMA;
		
		
		
		setSize(720, 570);
		setTitle("Neue Parkplatzreservierung Erstellen");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		
		// addComponentListener to center the window again.
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentMoved(java.awt.event.ComponentEvent evt) {
				formComponentMoved(evt);
			}
		});
		
		//We call the init(), Method
		init();
	}
	
	
	
	
	
	private void init() {
		
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		this.panelWithBackgroundOption.setLayout(new BorderLayout());
		this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		
		setContentPane(this.panelWithBackgroundOption);
		
		setIconImage(new ImageIcon(getClass().getResource("/img/iconoHotel.png")).getImage());
		
		this.anreiseDatumPlaceHolderTextField = new PlaceHolderTextField();
		
		
		
		//We add all the Elements to the Panel.
		addElementsToPanel();
	}
	
	
	
	/**
	 * 
	 * @param evt
	 * @description method to re-center the window in case the user tries to drag the window
	 */
	private void formComponentMoved(java.awt.event.ComponentEvent evt) {                                    
	       this.setLocationRelativeTo(null);
	    }   
	
	
	
	
	/**
	 * @description Method to confirm application closure
	 */
	public void confirmClose() {


		  
		  
		 
		optionPane.setMessage("Wollen Sie die neue Parkplatzreservierung abbrechen?");
		 optionPane.setMessageType(JOptionPane.OK_CANCEL_OPTION);
		 optionPane.setIcon(icon);
		
		
		 dialog = optionPane.createDialog(frame, "Parkplatzreservierung Abbrechen?");
		 
		 optionPane.setOptions(new Object[] {nein, ja});
		 
		
		 dialog.setVisible(true);
		 

			
					
			
			
			
	}
	
	
	
	private void addElementsToPanel() {
		
		
		
		this.chevy57 = new JLabel(new ImageIcon(getClass().getResource("/img/chevy57up.png")));
		
		
		this.panelWithBackgroundOption.add(chevy57, BorderLayout.WEST);
		
		this.entriesPanel = new JPanel();
		
		this.gbl = new GridBagLayout();
		this.gbc = new GridBagConstraints();
		
		this.entriesPanel.setLayout(gbl);
		this.entriesPanel.setOpaque(false);
		
		this.idParkingJLabel  = new JLabel("ID-Parking:");
		this.idParkingJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.idParkingJLabel.setPreferredSize(new Dimension(180, 20));
		this.idParkingJLabel.setOpaque(false);
		this.idParkingJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.idParkingJLabel.setForeground(Color.WHITE);
		

		this.idParkingGenerated = new JLabel(); //Wird automatisch generiert
		this.idParkingGenerated.setText("J" + now.getYear() + this.tableCounter);
		this.idParkingGenerated.setPreferredSize(new Dimension(300, 20));
		this.idParkingGenerated.setOpaque(false);
		this.idParkingGenerated.setFont(new Font("Verdana", Font.BOLD, 16));
		this.idParkingGenerated.setForeground(Color.BLACK);
		
		this.buchungsNameJLabel = new JLabel("Buchunsname:");
		this.buchungsNameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.buchungsNameJLabel.setPreferredSize(new Dimension(180, 20));
		this.buchungsNameJLabel.setOpaque(false);
		this.buchungsNameJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.buchungsNameJLabel.setForeground(Color.WHITE);

		this.buchungsNameJTextField = new JTextField(20);
		this.buchungsNameJTextField.setFont(new Font("Verdana", Font.BOLD, 14));
		
		
		this.autoKFZJLabel = new JLabel("Auto-KFZ:");
		this.autoKFZJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.autoKFZJLabel.setPreferredSize(new Dimension(180, 20));
		this.autoKFZJLabel.setOpaque(false);
		this.autoKFZJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.autoKFZJLabel.setForeground(Color.WHITE);
		
		this.autoKFZJTextField = new JTextField(20);
		this.autoKFZJTextField.setFont(new Font("Verdana", Font.BOLD, 14));
		
		
		this.anreiseDatumJLabel = new JLabel("Anreisedatum:");
		this.anreiseDatumJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.anreiseDatumJLabel.setPreferredSize(new Dimension(180, 20));
		this.anreiseDatumJLabel.setOpaque(false);
		this.anreiseDatumJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.anreiseDatumJLabel.setForeground(Color.WHITE);
	
		
		
		this.anreiseDatumPlaceHolderTextField.setPreferredSize(new Dimension(310, 30));
		this.anreiseDatumPlaceHolderTextField.setPlaceholder("dd.MM.JJJJ");
		this.anreiseDatumPlaceHolderTextField.setFont(new Font("Verdana", Font.BOLD, 14));
		
		this.abreiseDatumJLabel = new JLabel("Abreisedatum:");
		this.abreiseDatumJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.abreiseDatumJLabel.setPreferredSize(new Dimension(180, 20));
		this.abreiseDatumJLabel.setOpaque(false);
		this.abreiseDatumJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.abreiseDatumJLabel.setForeground(Color.WHITE);
		
		
		
		this.abreiseDatumPlaceholderTextField = new PlaceHolderTextField();
		this.abreiseDatumPlaceholderTextField.setPreferredSize(new Dimension(310, 30));
		this.abreiseDatumPlaceholderTextField.setPlaceholder("dd.MM.JJJJ");
		this.abreiseDatumPlaceholderTextField.setFont(new Font("Verdana", Font.BOLD, 14));
		
		this.tagenJLabel = new JLabel("Tagen:"); //Wird automatisch generiert
		this.tagenJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.tagenJLabel.setPreferredSize(new Dimension(180, 20));
		this.tagenJLabel.setOpaque(false);
		this.tagenJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.tagenJLabel.setForeground(Color.WHITE);
		
		
		this.tagenGeneratedJLabel = new JLabel("0");
		this.tagenGeneratedJLabel.setPreferredSize(new Dimension(300, 20));
		this.tagenGeneratedJLabel.setOpaque(false);
		this.tagenGeneratedJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		
		
		this.betragJLabel = new JLabel("Betrag:"); // Wird automatisch generiert
		this.betragJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.betragJLabel.setPreferredSize(new Dimension(180, 20));
		this.betragJLabel.setOpaque(false);
		this.betragJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.betragJLabel.setForeground(Color.WHITE);

		this.betragGeneratedJLabel = new JLabel("0.00 EUR");
		this.betragGeneratedJLabel.setPreferredSize(new Dimension(300, 20));
		this.betragGeneratedJLabel.setOpaque(false);
		this.betragGeneratedJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		
		
		
		this.buchungsKanalJLabel = new JLabel("Buchungskanal:"); 
		this.buchungsKanalJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.buchungsKanalJLabel.setPreferredSize(new Dimension(180, 20));
		this.buchungsKanalJLabel.setOpaque(false);
		this.buchungsKanalJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.buchungsKanalJLabel.setForeground(Color.WHITE);
		
		this.buchunsKanalJTextField = new JTextField(20);
		this.buchunsKanalJTextField.setFont(new Font("Verdana", Font.BOLD, 14));
		
		
		this.bemerkungenJLabel = new JLabel("Bemerkungen:"); 
		this.bemerkungenJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.bemerkungenJLabel.setPreferredSize(new Dimension(180, 20));
		this.bemerkungenJLabel.setOpaque(false);
		this.bemerkungenJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.bemerkungenJLabel.setForeground(Color.WHITE);
		 
		
		this.bemerkungenJTextField = new JTextField(20);
		this.bemerkungenJTextField.setFont(new Font("Verdana", Font.BOLD, 14));
		
		
		this.schluesselJLabel = new JLabel("Schlüssel?");
		this.schluesselJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.schluesselJLabel.setPreferredSize(new Dimension(180, 20));
		this.schluesselJLabel.setOpaque(false);
		this.schluesselJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.schluesselJLabel.setForeground(Color.WHITE);
		
		this.jcomboPanel = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
		this.jcomboPanel.setPreferredSize(new Dimension(300, 30));
		
		this.choices = new String[] {"Nein","Ja"};
		
		this.schluesselBox = new JComboBox<String>(choices);
		this.schluesselBox.setFont(new Font("Verdana", Font.BOLD, 14));
		
		this.jcomboPanel.add(schluesselBox);
		this.jcomboPanel.setOpaque(false);
		
		
		this.abkuerzungMAJLabel = new JLabel("Kürzel MA:"); //Wird automatisch generiert
		this.abkuerzungMAJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.abkuerzungMAJLabel.setPreferredSize(new Dimension(180, 20));
		this.abkuerzungMAJLabel.setOpaque(false);
		this.abkuerzungMAJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.abkuerzungMAJLabel.setForeground(Color.WHITE);
		
		this.abkuerzungMAGeneratedJLabel = new JLabel();
		

		this.abkuerzungMAGeneratedJLabel.setPreferredSize(new Dimension(300, 20));
		this.abkuerzungMAGeneratedJLabel.setOpaque(false);
		this.abkuerzungMAGeneratedJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
		this.abkuerzungMAGeneratedJLabel.setText(getAbkuerzungMA());
		
		
		
		
		
		
		
		
		

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbl.setConstraints(this.idParkingJLabel, gbc);
		this.entriesPanel.add(this.idParkingJLabel);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.insets = new Insets(0, 5, 0, 0);
		gbl.setConstraints(idParkingGenerated, gbc);
		this.entriesPanel.add(idParkingGenerated);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(buchungsNameJLabel, gbc);
		this.entriesPanel.add(buchungsNameJLabel);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(10, 5, 0, 0);
		gbl.setConstraints(buchungsNameJTextField, gbc);
		this.entriesPanel.add(buchungsNameJTextField);
		
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(this.autoKFZJLabel, gbc);
		this.entriesPanel.add(this.autoKFZJLabel);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(10, 5, 0, 0);
		gbl.setConstraints(this.autoKFZJTextField, gbc);
		this.entriesPanel.add(this.autoKFZJTextField);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(anreiseDatumJLabel, gbc);
		this.entriesPanel.add(anreiseDatumJLabel);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(10, 5, 0, 0);
		gbl.setConstraints(anreiseDatumPlaceHolderTextField, gbc);
		this.entriesPanel.add(anreiseDatumPlaceHolderTextField);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(abreiseDatumJLabel, gbc);
		this.entriesPanel.add(abreiseDatumJLabel);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(10, 5, 0, 0);
		gbl.setConstraints(abreiseDatumPlaceholderTextField, gbc);
		this.entriesPanel.add(abreiseDatumPlaceholderTextField);
		
		
		   
		   
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(tagenJLabel, gbc);
		this.entriesPanel.add(tagenJLabel);


		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.insets = new Insets(10, 5, 0, 0);
		gbl.setConstraints(this.tagenGeneratedJLabel, gbc);
		this.entriesPanel.add(this.tagenGeneratedJLabel);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(betragJLabel, gbc);
		this.entriesPanel.add(betragJLabel);
		
		
		   
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.insets = new Insets(10, 5, 0, 0);
		gbl.setConstraints(this.betragGeneratedJLabel, gbc);
		this.entriesPanel.add(betragGeneratedJLabel);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(this.buchungsKanalJLabel, gbc);
		this.entriesPanel.add(this.buchungsKanalJLabel);

		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.insets = new Insets(10, 5, 0, 0);
		gbl.setConstraints(this.buchunsKanalJTextField, gbc);
		this.entriesPanel.add(this.buchunsKanalJTextField);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(bemerkungenJLabel, gbc);
		this.entriesPanel.add(bemerkungenJLabel);

		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.insets = new Insets(10, 5, 0, 0);
		gbl.setConstraints(bemerkungenJTextField, gbc);
		this.entriesPanel.add(bemerkungenJTextField);

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(this.schluesselJLabel, gbc);
		this.entriesPanel.add(this.schluesselJLabel);

		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.insets = new Insets(15, 0, 0, 0);
		gbl.setConstraints(this.jcomboPanel, gbc);
		this.entriesPanel.add(jcomboPanel);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.insets = new Insets(10, 0, 0, 0);
		gbl.setConstraints(this.abkuerzungMAJLabel, gbc);
		this.entriesPanel.add(this.abkuerzungMAJLabel);

		gbc.gridx = 1;
		gbc.gridy = 10;
		gbc.insets = new Insets(10, 5, 0, 0);
		gbl.setConstraints(this.abkuerzungMAGeneratedJLabel, gbc);
		this.entriesPanel.add(this.abkuerzungMAGeneratedJLabel);
		   
		
		
		
		this.saveParkingReservation = new JButton("Speichern");
		this.saveParkingReservation.setFocusable(true);
		this.cancelParkingReservation = new JButton("Abbrechen");
		
		this.buttonPanel = new JPanel(new BorderLayout());
		this.buttonPanel.setOpaque(false);
		
		this.buttonPanel.add(this.saveParkingReservation, BorderLayout.EAST);
		this.buttonPanel.add(this.cancelParkingReservation, BorderLayout.WEST);
		
		
		this.panelWithBackgroundOption.add(this.entriesPanel, BorderLayout.CENTER);
		
		this.panelWithBackgroundOption.add(this.buttonPanel, BorderLayout.SOUTH);
		
		
		
		
		
		
	}
	
	//Method to get the information who from the team is typing the Data.
	private static String getAbkuerzungMA() {
		return abkuerzungMA;
	}
	
	
	
	
	

}
