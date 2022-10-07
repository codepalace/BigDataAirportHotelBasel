package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;


@SuppressWarnings("serial")
public class DateChronologyCorrection extends JDialog {
	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	
	private JPanel mainJPanel, entriesPanel, topPanel, containerButtonPanel;
	
	private JLabel messageJLabel, imgModal; 
	
	
	private String title, message;
	
	public JTextField dateJTextField;
	
	public MyButton btn_abbrechen, btn_save_changes;
	
	
	
	private GridBagLayout mainGBL, gbl;
	private GridBagConstraints mainGBC, gbc;
	
	
	
	public DateChronologyCorrection(String title, String message, DataBaseGUI dataBaseGUI, boolean modal) {
		
		super(dataBaseGUI, modal);
		
		this.title = title;
		this.message = message;
		
		
		init();
		
		
	}
	
	
	private void init() {
		
		setSize(700, 140);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setTitle(this.title);
		
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });
		
		
		 this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		  this.panelWithBackgroundOption.setLayout(new BorderLayout());
		 setContentPane(panelWithBackgroundOption);
		  
		  setIconImage(new ImageIcon(getClass().getResource("/img/iconoHotel.png")).getImage());
		  
		  this.topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		  this.topPanel.setOpaque(false);
		  
		 
		  
		  

		  this.mainJPanel = new JPanel();
			
			this.imgModal = new JLabel();
			this.imgModal.setOpaque(false);
		  
			this.imgModal.setIcon(new ImageIcon(getClass().getResource("/img/error.png")));
		  
			// GridBagLayout for the mainJPanel
			this.mainGBL = new GridBagLayout();

			// GridBagConstraints for the mainJPanel
			this.mainGBC = new GridBagConstraints();

			this.gbl = new GridBagLayout();
			this.gbc = new GridBagConstraints();

			// setLayout to the mainJPanel the GridBagLayout
			this.mainJPanel.setLayout(mainGBL);
			this.mainJPanel.setOpaque(false);

			this.entriesPanel = new JPanel(gbl);
			this.entriesPanel.setOpaque(false);
			
			this.messageJLabel = new JLabel(this.message);
			this.messageJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.messageJLabel.setFont(new Font("Verdana", Font.BOLD, 14));

			
			this.dateJTextField = new JTextField(10);
			
			this.btn_save_changes =  new MyButton("/img/aenderung_speichern.png");
			this.btn_abbrechen = new MyButton("/img/abbrechen_login_gui.png");
			
			this.containerButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10,10));
			this.containerButtonPanel.setOpaque(false);
			
			
			//Add all elements to the GUI
			addElementsToGUI();
		
		
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

		 this.dispose();
					
			
	}
	
	
	private void addElementsToGUI() {
		
		//Setting the elements GridBagConstraints and add to entriesPanel with GridBagLayout
		
		this.gbc.insets = new Insets(10, 0, 10, 0);
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.entriesPanel.add(this.messageJLabel);
		
		this.gbc.fill = GridBagConstraints.HORIZONTAL;
		this.gbc.weightx = 0.5;
		this.gbc.gridx = 1;
		this.gbc.gridy = 0;
		this.gbc.insets = new Insets(0, 5, 0, 0);
		this.entriesPanel.add(this.dateJTextField, gbc);
		
		//We add the entriesPanel to the mainJPanel
		this.mainJPanel.add(this.entriesPanel);
		
		//Add the Buttons to the containerButtonPanel
		this.containerButtonPanel.add(this.btn_abbrechen);
		this.containerButtonPanel.add(this.btn_save_changes);
		
		
		
		//Setting the elements GridBagConstraints and add to mainJPanel with GridBagLayout
		this.mainGBC.insets = new Insets(30, 0, 0, 0);
		mainGBC.gridx = 0;
		mainGBC.gridy = 0;
		mainGBL.setConstraints(imgModal, mainGBC);
		this.mainJPanel.add(imgModal);
		
		mainGBC.gridx = 1;
		mainGBC.gridy = 0;
		mainGBL.setConstraints(this.entriesPanel, mainGBC);
		
		mainGBC.gridx = 0;
		mainGBC.gridy = 1;
		mainGBC.fill = GridBagConstraints.HORIZONTAL;
		mainGBC.gridwidth = 2;
		mainGBL.setConstraints(this.containerButtonPanel, mainGBC);
		
		//We add the JButton s to the mainJPanel
		this.mainJPanel.add(containerButtonPanel);
		
	
		this.topPanel.add(this.imgModal);
		this.topPanel.add(this.messageJLabel);
		this.topPanel.add(this.dateJTextField);
		
		
		
		this.panelWithBackgroundOption.add(this.topPanel, BorderLayout.NORTH);
		this.panelWithBackgroundOption.add(this.containerButtonPanel, BorderLayout.CENTER);
		
		
		
	}
	
	
}
