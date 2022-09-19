package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;

@SuppressWarnings("serial")
public class ModifyPasswordUserGUI extends JDialog {
	

	private PanelWithBackgroundOption panelWithBackgroundOption;

	private JPanel mainJPanel, entriesPanel, containerButtonPanel;
	
	public JLabel oldPasswordJLabel, newPasswordJLabel, newPasswordConfirmationJLabel;
	
	
	public JPasswordField oldPasswordField, newPasswordField, newPasswordConfirmationField;
	
	private JLabel imgModal;
	
	public MyButton btnSave, btnCancelSave;
	
	private GridBagLayout mainGBL, gbl;
	
	private GridBagConstraints mainGBC, gbc;
	
	
	
	public ModifyPasswordUserGUI(EditUserGUI editUserGUI, boolean modal) {
		
		//Call super with the arguments JFrame should be blocked in Background and modal(true) for blocked
		super(editUserGUI, modal);
		
		init();
		
	}
	
	
	
	private void init() {
		
		// We set the size according to which the elements fit in the panel  
		setSize(640, 240);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setTitle("Benutzerkennworts ändern");
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		//Add always the GUI to the center even if the user wants to place it somewhere else.
		this.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                formComponentMoved(evt);
            }
        });
		
		//Initialize our PanelWithBackgroundOption image
		this.panelWithBackgroundOption = new PanelWithBackgroundOption();
		
		//Set the Background image for our GUI
		this.panelWithBackgroundOption.setImage("/img/backgroundframe.jpg");
		
		
		//Set the LayoutManager.
		this.panelWithBackgroundOption.setLayout(new BorderLayout());
		
		//Our ContentPane is panelWithBackgroundOption
		setContentPane(panelWithBackgroundOption);
		
		//Set the icon for the GUI
		 setIconImage(new ImageIcon(getClass().getResource("/img/iconoHotel.png")).getImage());
		  
		 
		  this.mainJPanel = new JPanel();
			
			this.imgModal = new JLabel();
			this.imgModal.setOpaque(false);
		  
			this.imgModal.setIcon(new ImageIcon(getClass().getResource("/img/userSingle.png")));
			
			
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
			
			this.oldPasswordJLabel = new JLabel("altes Passwort:");
			this.oldPasswordJLabel.setPreferredSize(new Dimension(160, 20));
			this.oldPasswordJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.oldPasswordJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			
			this.newPasswordJLabel = new JLabel("neues Passwort:");
			this.newPasswordJLabel.setPreferredSize(new Dimension(160, 20));
			this.newPasswordJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.newPasswordJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			
			this.newPasswordConfirmationJLabel = new JLabel("passwort erneut eingeben:");
			this.newPasswordConfirmationJLabel.setPreferredSize(new Dimension(280, 20));
			this.newPasswordConfirmationJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.newPasswordConfirmationJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			
			this.oldPasswordField = new JPasswordField(20);
			this.oldPasswordField.setText("");
			
			this.newPasswordField = new JPasswordField(20);
			this.newPasswordField.setText("");
			
			this.newPasswordConfirmationField = new JPasswordField(20);
			this.newPasswordConfirmationField.setText("");
;
			
			// MyButton and containerButtonPanel
			this.btnSave = new MyButton("/img/btn_speichern_black.png");
			this.btnCancelSave = new MyButton("/img/abbrechen_login_gui.png");
			
			
			this.containerButtonPanel = new JPanel(new BorderLayout());
			this.containerButtonPanel.setOpaque(false);
			
			// Add all elements to the GUI
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
		
		// Setting the elements GridBagConstraints and add to entriesPanel with
		// GridBagLayout
		this.gbc.fill = GridBagConstraints.HORIZONTAL;
		this.gbc.weightx = 0.5;
		this.gbc.insets = new Insets(10, 0, 10, 0);
		this.gbc.gridx = 0;
		this.gbc.gridy = 0;
		this.entriesPanel.add(this.oldPasswordJLabel, gbc);

		this.gbc.fill = GridBagConstraints.HORIZONTAL;
		this.gbc.weightx = 0.5;
		this.gbc.gridx = 1;
		this.gbc.gridy = 0;
		this.gbc.insets = new Insets(0, 5, 0, 0);
		this.entriesPanel.add(this.oldPasswordField, gbc);

		
		
		this.gbc.fill = GridBagConstraints.HORIZONTAL;
		this.gbc.weightx = 0.5;
		this.gbc.gridx = 0;
		this.gbc.gridy = 1;
		this.gbc.insets = new Insets(5, 0, 0, 0);
		this.entriesPanel.add(this.newPasswordJLabel, gbc);
		
		this.gbc.fill = GridBagConstraints.HORIZONTAL;
		this.gbc.weightx = 0.5;
		this.gbc.gridx = 1;
		this.gbc.gridy = 1; 
		this.gbc.insets = new Insets(5, 5, 0, 0);
		this.entriesPanel.add(this.newPasswordField, gbc);
		
		
		this.gbc.fill = GridBagConstraints.HORIZONTAL;
		this.gbc.weightx = 0.5;
		this.gbc.gridx = 0; 
		this.gbc.gridy = 2; 
		this.gbc.insets = new Insets(15, 0, 0, 0);
		this.entriesPanel.add(this.newPasswordConfirmationJLabel, gbc);
		
		this.gbc.fill = GridBagConstraints.HORIZONTAL;
		this.gbc.weightx = 0.5;
		this.gbc.gridx = 1;
		this.gbc.gridy = 2; 
		this.gbc.insets = new Insets(15, 5, 0, 0);
		this.entriesPanel.add(this.newPasswordConfirmationField, gbc);
		
		
		this.mainJPanel.add(this.entriesPanel);
		
		
		
		this.containerButtonPanel.add(this.btnSave, BorderLayout.EAST);
		this.containerButtonPanel.add(this.btnCancelSave, BorderLayout.WEST);
		
		
		// Setting the elements GridBagConstraints and add to mainJPanel with
		// GridBagLayout
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

		// We add the JButton s to the mainJPanel
		this.mainJPanel.add(containerButtonPanel);

		this.panelWithBackgroundOption.add(this.mainJPanel, BorderLayout.NORTH);
		
		
		
		
		
		
	}

}
