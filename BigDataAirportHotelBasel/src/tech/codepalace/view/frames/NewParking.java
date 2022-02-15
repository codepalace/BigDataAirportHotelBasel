package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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

import tech.codepalace.model.UserAHB;
import tech.codepalace.utility.DataEncryption;
import tech.codepalace.view.panels.PanelWithBackgroundOption;

public class NewParking extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected UserAHB userAHB;
	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	
	private JPanel entriesPanel, centerPanel, southPanel;
	
	private JLabel chevy57;
	
	private GridBagLayout gbl;
 	private GridBagConstraints gbc;
 	
private JLabel idParkingJLabel, idParkingGenerated;
 	
 	private JLabel buchungsNameJLabel;
 	private JTextField buchungsNameJTextField;
 	
 	private JLabel autoKFZJLabel;
 	private JTextField autoKFZJTextField;
 	
 	private JLabel anreiseDatumJLabel; 
 	private JTextField anreiseDatumJTextField;
 	
 	private JLabel abreiseDatumJLabel;
 	private JTextField abreiseDatumJTextField;
 	
 	private JLabel tagenJLabel, tagenGeneratedJLabel;
 	
 	private JLabel betragJLabel, betragGeneratedJLabel;
 	
 	private JLabel buchungsKanalJLabel;
 	private JTextField buchunsKanalJTextField;
 	
 	private JLabel bemerkungenJLabel;
 	private JTextField bemerkungenJTextField;
 	
 	private JLabel schluesselJLabel;
 	private JComboBox<String> schluesselBox;
 	private String[] choices;
 	
 	private JLabel abkuerzungMAJLabel, abkuerzungMAGeneratedJLabel;
 	
 	public JButton saveParkingReservation, cancelParkingReservation;
 	
 	private JPanel buttonPanel;
 	
 	private DataEncryption dataEncryption;
 	
	
	
	
	
	
	public NewParking(AHBParking ahbParking, boolean modal, UserAHB userAHB) {
		
		super(ahbParking, modal);
		this.userAHB = userAHB;
		
		setSize(650, 470);
		setTitle("Neue Parkplatzreservierung Erstellen");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);
		
		// addComponentListener to center the window again.
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
			public void componentMoved(java.awt.event.ComponentEvent evt) {
				formComponentMoved(evt);
			}
		});
		
		init();
	}
	
	
	
	
	
	private void init() {
		
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		this.panelWithBackgroundOption.setLayout(new BorderLayout());
		this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		
		setContentPane(this.panelWithBackgroundOption);
		
		setIconImage(new ImageIcon(getClass().getResource("/img/iconoHotel.png")).getImage());
		
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

		
		ImageIcon icon = new ImageIcon(getClass().getResource("/img/dialogo.png"));
		
	
		JFrame frame = new JFrame();
		JOptionPane optionPane = new JOptionPane();
		 optionPane.setMessage("Wollen Sie die neue Parkplatzreservierung abbrechen?");
		 optionPane.setMessageType(JOptionPane.OK_CANCEL_OPTION);
		 optionPane.setIcon(icon);
		 JButton nein = new JButton("Nein");
		 JButton ja = new JButton("Ja");
		 JDialog dialog = optionPane.createDialog(frame, "Parkplatzreservierung Abbrechen?");
		 
		 
		 nein.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//Close JDialog and continue loging GUI
				dialog.dispose();
				
			}
		});
		 
		 ja.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dialog.dispose();
					dispose();
					
				}
			});
		 
		 
		 nein.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				if(e.getKeyCode()==10) {
					dialog.dispose();
				}
				
			}
		});
		  
		  
		  
		 ja.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {}
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			
			@Override
			public void keyPressed(KeyEvent e) {

				if(e.getKeyCode()==10) {
					
					//Call for the confirmation
					confirmClose();
				}
				
			}
		});
		  
		  
		 
		 
		 optionPane.setOptions(new Object[] {nein, ja});
		 
		
		 dialog.setVisible(true);
		 

			
					
			
			
			
	}
	
	
	
	private void addElementsToPanel() {
		
		this.dataEncryption = new DataEncryption();
		
		this.chevy57 = new JLabel(new ImageIcon(getClass().getResource("/img/chevy57up.png")));
		
		this.centerPanel = new JPanel(new BorderLayout());
		
//		this.centerPanel.add(chevy57, BorderLayout.EAST);
		
		this.panelWithBackgroundOption.add(chevy57, BorderLayout.WEST);
		
		this.entriesPanel = new JPanel();
		
		this.gbl = new GridBagLayout();
		this.gbc = new GridBagConstraints();
		
		this.entriesPanel.setLayout(gbl);
		this.entriesPanel.setOpaque(false);
		
		this.idParkingJLabel  = new JLabel("ID-Parking:");
		this.idParkingJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.idParkingJLabel.setPreferredSize(new Dimension(150, 20));
		this.idParkingJLabel.setOpaque(false);
		
		this.idParkingGenerated = new JLabel("J20211"); //Wird automatisch generiert
		this.idParkingGenerated.setPreferredSize(new Dimension(242, 20));
		this.idParkingGenerated.setOpaque(false);
		
		this.buchungsNameJLabel = new JLabel("Buchunsname:");
		this.buchungsNameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.buchungsNameJLabel.setPreferredSize(new Dimension(150, 20));
		this.buchungsNameJLabel.setOpaque(false);

		this.buchungsNameJTextField = new JTextField(20);
		
		
		this.autoKFZJLabel = new JLabel("Auto-KFZ:");
		this.autoKFZJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.autoKFZJLabel.setPreferredSize(new Dimension(150, 20));
		this.autoKFZJLabel.setOpaque(false);
		
		this.autoKFZJTextField = new JTextField(20);
		
		
		this.anreiseDatumJLabel = new JLabel("Anreisedatum:");
		this.anreiseDatumJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.anreiseDatumJLabel.setPreferredSize(new Dimension(150, 20));
		this.anreiseDatumJLabel.setOpaque(false);
		
		this.anreiseDatumJTextField = new JTextField(20);
		
		this.abreiseDatumJLabel = new JLabel("Abreisedatum:");
		this.abreiseDatumJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.abreiseDatumJLabel.setPreferredSize(new Dimension(150, 20));
		this.abreiseDatumJLabel.setOpaque(false);
		
		this.abreiseDatumJTextField = new JTextField(20);
		
		
		this.tagenJLabel = new JLabel("Tagen:"); //Wird automatisch generiert
		this.tagenJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.tagenJLabel.setPreferredSize(new Dimension(150, 20));
		this.tagenJLabel.setOpaque(false);
		
		
		this.tagenGeneratedJLabel = new JLabel("200");
		this.tagenGeneratedJLabel.setPreferredSize(new Dimension(242, 20));
		this.tagenGeneratedJLabel.setOpaque(false);
		
		
		this.betragJLabel = new JLabel("Betrag:"); // Wird automatisch generiert
		this.betragJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.betragJLabel.setPreferredSize(new Dimension(150, 20));
		this.betragJLabel.setOpaque(false);

		this.betragGeneratedJLabel = new JLabel("150.00 EUR");
		this.betragGeneratedJLabel.setPreferredSize(new Dimension(242, 20));
		this.betragGeneratedJLabel.setOpaque(false);
		
		
		this.buchungsKanalJLabel = new JLabel("Buchungskanal:"); 
		this.buchungsKanalJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.buchungsKanalJLabel.setPreferredSize(new Dimension(150, 20));
		this.buchungsKanalJLabel.setOpaque(false);
		
		this.buchunsKanalJTextField = new JTextField(20);
		
		
		this.bemerkungenJLabel = new JLabel("Bemerkungen:"); 
		this.bemerkungenJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.bemerkungenJLabel.setPreferredSize(new Dimension(150, 20));
		this.bemerkungenJLabel.setOpaque(false);
		 
		
		bemerkungenJTextField = new JTextField(20);
		
		
		this.schluesselJLabel = new JLabel("Schlüssel?");
		this.schluesselJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.schluesselJLabel.setPreferredSize(new Dimension(150, 20));
		this.schluesselJLabel.setOpaque(false);
		
		
		this.choices = new String[] {"Ja","Nein"};
		
		this.schluesselBox = new JComboBox<String>(choices);
		
		
		this.abkuerzungMAJLabel = new JLabel("Kürzel MA:"); //Wird automatisch generiert
		this.abkuerzungMAJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		this.abkuerzungMAJLabel.setPreferredSize(new Dimension(150, 20));
		this.abkuerzungMAJLabel.setOpaque(false);
		
	
		
		try {
			this.abkuerzungMAGeneratedJLabel = new JLabel(this.dataEncryption.decryptData(this.userAHB.getAbbkuerzungMA()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.abkuerzungMAGeneratedJLabel.setPreferredSize(new Dimension(242, 20));
		this.abkuerzungMAGeneratedJLabel.setOpaque(false);
		
		
		
		
		
		
		
		
		

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
		gbc.insets = new Insets(5, 0, 0, 0);
		gbl.setConstraints(buchungsNameJLabel, gbc);
		this.entriesPanel.add(buchungsNameJLabel);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(5, 5, 0, 0);
		gbl.setConstraints(buchungsNameJTextField, gbc);
		this.entriesPanel.add(buchungsNameJTextField);
		
		
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbl.setConstraints(this.autoKFZJLabel, gbc);
		this.entriesPanel.add(this.autoKFZJLabel);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.insets = new Insets(5, 5, 0, 0);
		gbl.setConstraints(this.autoKFZJTextField, gbc);
		this.entriesPanel.add(this.autoKFZJTextField);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbl.setConstraints(anreiseDatumJLabel, gbc);
		this.entriesPanel.add(anreiseDatumJLabel);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.insets = new Insets(5, 5, 0, 0);
		gbl.setConstraints(anreiseDatumJTextField, gbc);
		this.entriesPanel.add(anreiseDatumJTextField);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbl.setConstraints(abreiseDatumJLabel, gbc);
		this.entriesPanel.add(abreiseDatumJLabel);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.insets = new Insets(5, 5, 0, 0);
		gbl.setConstraints(abreiseDatumJTextField, gbc);
		this.entriesPanel.add(abreiseDatumJTextField);
		
		
		   
		   
		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbl.setConstraints(tagenJLabel, gbc);
		this.entriesPanel.add(tagenJLabel);


		gbc.gridx = 1;
		gbc.gridy = 5;
		gbc.insets = new Insets(5, 5, 0, 0);
		gbl.setConstraints(this.tagenGeneratedJLabel, gbc);
		this.entriesPanel.add(this.tagenGeneratedJLabel);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbl.setConstraints(betragJLabel, gbc);
		this.entriesPanel.add(betragJLabel);
		
		
		   
		gbc.gridx = 1;
		gbc.gridy = 6;
		gbc.insets = new Insets(5, 5, 0, 0);
		gbl.setConstraints(this.betragGeneratedJLabel, gbc);
		this.entriesPanel.add(betragGeneratedJLabel);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbl.setConstraints(this.buchungsKanalJLabel, gbc);
		this.entriesPanel.add(this.buchungsKanalJLabel);

		gbc.gridx = 1;
		gbc.gridy = 7;
		gbc.insets = new Insets(5, 5, 0, 0);
		gbl.setConstraints(this.buchunsKanalJTextField, gbc);
		this.entriesPanel.add(this.buchunsKanalJTextField);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbl.setConstraints(bemerkungenJLabel, gbc);
		this.entriesPanel.add(bemerkungenJLabel);

		gbc.gridx = 1;
		gbc.gridy = 8;
		gbc.insets = new Insets(5, 5, 0, 0);
		gbl.setConstraints(bemerkungenJTextField, gbc);
		this.entriesPanel.add(bemerkungenJTextField);

		gbc.gridx = 0;
		gbc.gridy = 9;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbl.setConstraints(this.schluesselJLabel, gbc);
		this.entriesPanel.add(this.schluesselJLabel);

		gbc.gridx = 1;
		gbc.gridy = 9;
		gbc.insets = new Insets(5, 5, 0, 0);
		gbl.setConstraints(this.schluesselBox, gbc);
		this.entriesPanel.add(schluesselBox);

		gbc.gridx = 0;
		gbc.gridy = 10;
		gbc.insets = new Insets(5, 0, 0, 0);
		gbl.setConstraints(this.abkuerzungMAJLabel, gbc);
		this.entriesPanel.add(this.abkuerzungMAJLabel);

		gbc.gridx = 1;
		gbc.gridy = 10;
		gbc.insets = new Insets(5, 5, 0, 0);
		gbl.setConstraints(this.abkuerzungMAGeneratedJLabel, gbc);
		this.entriesPanel.add(this.abkuerzungMAGeneratedJLabel);
		   
		
		
		
		this.saveParkingReservation = new JButton("Speichern");
		this.cancelParkingReservation = new JButton("Abbrechen");
		
		this.buttonPanel = new JPanel(new BorderLayout());
		this.buttonPanel.setOpaque(false);
		
		this.buttonPanel.add(this.saveParkingReservation, BorderLayout.EAST);
		this.buttonPanel.add(this.cancelParkingReservation, BorderLayout.WEST);
		
		
		this.panelWithBackgroundOption.add(this.entriesPanel, BorderLayout.CENTER);
		
		this.panelWithBackgroundOption.add(this.buttonPanel, BorderLayout.SOUTH);
		
		
		
		
		
		
	}
	
	

}
