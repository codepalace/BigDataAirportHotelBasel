package tech.codepalace.view.frames;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import tech.codepalace.view.buttons.MyButton;
import tech.codepalace.view.panels.PanelWithBackgroundOption;

/**
 * @description Class to add new User
 * @author Antonio Estevez Gonzalez
 * 
 *
 */
@SuppressWarnings("serial")
public class NewUser extends JDialog {
	
	private PanelWithBackgroundOption panelWithBackgroundOption;
	
	private JPanel mainJPanel, entriesPanel, containerButtonPanel;
	
	private JLabel userNameJLabel, passwordJLabel, benutzerrechten, abkuerzungMAJLabel; 
	
	//Image for the modal Window
		private JLabel imgModal;
		
		public MyButton btnSave, btnCancelSave;
	
		public JTextField newUserJTextField, abkuerzungMAJTextField;
		public JPasswordField passwordField;

		//Object to select the User Rights.
		public JComboBox<String>benutzerRechtenJComboBox;
		
		private String[]choicesBenutzerRechten;
		

		private GridBagLayout mainGBL, gbl;
		private GridBagConstraints mainGBC, gbc;
	
	
	public NewUser(UserManager userManager, boolean modal) {
		
		//Call super with the arguments JFrame should be blocked in Background and modal(true) for blocked
		super(userManager, modal);
		
		init();
	}
	
	
	private void init() {
		
		setSize(530, 280);
		
		setLocationRelativeTo(null);
		
		setResizable(false);
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		setTitle("Neuen Benutzer Eingeben");
		
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
		  
		  this.mainJPanel = new JPanel();
			
			this.imgModal = new JLabel();
			this.imgModal.setOpaque(false);
		  
			this.imgModal.setIcon(new ImageIcon(getClass().getResource("/img/users.png")));
			
			
			// GridBagLayout for the mainJPanel
			this.mainGBL = new GridBagLayout();

			// GridBagConstraints for the mainJPanel
			this.mainGBC = new GridBagConstraints();
			
			
			this.gbl = new GridBagLayout();
			this.gbc = new GridBagConstraints();
			
			// setLayout to the mainJPanel the GridBagLayout
			this.mainJPanel.setLayout(mainGBL);
			this.mainJPanel.setOpaque(false);
			
			this.entriesPanel =  new JPanel(gbl);
			this.entriesPanel.setOpaque(false);
			
			this.userNameJLabel = new JLabel("Benutzer:");
			this.userNameJLabel.setPreferredSize(new Dimension(160,20));
			this.userNameJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.userNameJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			
			this.passwordJLabel = new JLabel("Password:");
			this.passwordJLabel.setPreferredSize(new Dimension(160,20));
			this.passwordJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.passwordJLabel.setFont(new Font("Verdana", Font.BOLD, 16));
			
			
			this.newUserJTextField = new JTextField(20);
			this.newUserJTextField.setText("");
			
			this.passwordField = new JPasswordField(20);

			this.passwordField.setText("");
			
			//Initialize the JLabel for the user rights
			this.benutzerrechten = new JLabel("Benutzerrechten:");
			this.benutzerrechten.setPreferredSize(new Dimension(160,20));
			this.benutzerrechten.setHorizontalAlignment(SwingConstants.RIGHT);
			this.benutzerrechten.setFont(new Font("Verdana", Font.BOLD, 16));
			
			//Initialize the Array with the choices for the User rights.
			this.choicesBenutzerRechten = new String[]{"Benutzerrechte auswählen", "Manager", "Mitarbeiter"};
			
			
			//Initialize the JComboBox element for the User Rights. 
			this.benutzerRechtenJComboBox = new JComboBox<String>(choicesBenutzerRechten);
			this.benutzerRechtenJComboBox.setFont(new Font("Verdana", Font.BOLD, 14));

			//Initialize the JLabel element for the abbreviation name for the Staff or Administrator user. 
			this.abkuerzungMAJLabel = new JLabel("Kürzel MA:");
			this.abkuerzungMAJLabel.setPreferredSize(new Dimension(160,20));
			this.abkuerzungMAJLabel.setHorizontalAlignment(SwingConstants.RIGHT);
			this.abkuerzungMAJLabel.setFont(new Font("Verdana", Font.BOLD, 14));
			
			//Initialize the JText element for the abbreviation name for Staff or Administrator.
			this.abkuerzungMAJTextField = new JTextField(20);
			this.abkuerzungMAJTextField.setText("");
			
			
			
			
			
			
			
			//MyButton and containerButtonPanel
			this.btnSave = new MyButton("/img/btn_speichern_black.png");
			this.btnCancelSave = new MyButton("/img/abbrechen_login_gui.png");
			
			this.containerButtonPanel = new JPanel(new BorderLayout());
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
			this.entriesPanel.add(this.userNameJLabel, gbc);
			
			this.gbc.fill = GridBagConstraints.HORIZONTAL;
			this.gbc.weightx = 0.5;
			this.gbc.gridx = 1;
			this.gbc.gridy = 0;
			this.gbc.insets = new Insets(0, 5, 0, 0);
			this.entriesPanel.add(this.newUserJTextField, gbc);
			

			this.gbc.gridx = 0; 
			this.gbc.gridy = 1; 
			this.gbc.insets = new Insets(5, 0, 0, 0);
			this.entriesPanel.add(this.passwordJLabel, gbc);
			

			this.gbc.fill = GridBagConstraints.HORIZONTAL;
			this.gbc.weightx = 0.5;
			this.gbc.gridx = 1;
			this.gbc.gridy = 1; 
			this.gbc.insets = new Insets(5, 5, 0, 0);
			this.entriesPanel.add(this.passwordField, gbc);
			
			this.gbc.gridx = 0; 
			this.gbc.gridy = 2; 
			this.gbc.insets = new Insets(15, 0, 0, 0);
			this.entriesPanel.add(this.benutzerrechten, gbc);
			
			this.gbc.fill = GridBagConstraints.HORIZONTAL;
			this.gbc.weightx = 0.5;
			this.gbc.gridx = 1;
			this.gbc.gridy = 2; 
			this.gbc.insets = new Insets(15, 5, 0, 0);
			this.entriesPanel.add(this.benutzerRechtenJComboBox, gbc);
			
			this.gbc.gridx = 0; 
			this.gbc.gridy = 3; 
			this.gbc.insets = new Insets(15, 0, 0, 0);
			this.entriesPanel.add(this.abkuerzungMAJLabel, gbc);
			
			this.gbc.fill = GridBagConstraints.HORIZONTAL;
			this.gbc.weightx = 0.5;
			this.gbc.gridx = 1;
			this.gbc.gridy = 3; 
			this.gbc.insets = new Insets(15, 5, 0, 0);
			this.entriesPanel.add(this.abkuerzungMAJTextField, gbc);
			

		
			
			//We add the entriesPanel to the mainJPanel
			this.mainJPanel.add(this.entriesPanel);
			
			
			//Add the Buttons to the containerButtonPanel
			this.containerButtonPanel.add(this.btnSave, BorderLayout.EAST);
			this.containerButtonPanel.add(this.btnCancelSave, BorderLayout.WEST);
			
			
			
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
			
			this.panelWithBackgroundOption.add(this.mainJPanel, BorderLayout.NORTH);
			
			
		}
		
		

}
